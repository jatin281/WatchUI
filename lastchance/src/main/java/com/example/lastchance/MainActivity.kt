package com.example.lastchance

import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.lastchance.navigation.RootNavigationGraph
import com.example.lastchance.theme.LastChanceTheme
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity(), CapabilityClient.OnCapabilityChangedListener {

    private val dataClient by lazy { Wearable.getDataClient(this) }
    private val messageClient by lazy { Wearable.getMessageClient(this) }
    private val capabilityClient by lazy { Wearable.getCapabilityClient(this) }

    private var androidPhoneNodeWithApp: Node? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LastChanceTheme {

                val navController = rememberNavController()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    verticalArrangement = Arrangement.Center
                ) {
                    RootNavigationGraph(navController = navController)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Wearable.getCapabilityClient(this).removeListener(this, CAPABILITY_PHONE_APP)
    }

    override fun onResume() {
        super.onResume()
        Wearable.getCapabilityClient(this).addListener(this, CAPABILITY_PHONE_APP)
        lifecycleScope.launch {
            checkIfPhoneHasApp()
        }
    }

    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {
        Log.d(TAG, "onCapabilityChanged(): $capabilityInfo")
        // There should only ever be one phone in a node set (much less w/ the correct
        // capability), so I am just grabbing the first one (which should be the only one).
        androidPhoneNodeWithApp = capabilityInfo.nodes.firstOrNull()
    }

    private fun onQueryOtherDevicesClicked() {
        lifecycleScope.launch {
            try {
                val nodes = getCapabilitiesForReachableNodes()
                    .filterValues { CAPABILITY_PHONE_APP in it || CAPABILITY_WEAR_APP in it }.keys
                displayNodes(nodes)
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                Log.d(TAG, "Querying nodes failed: $exception")
            }
        }
    }

    private suspend fun getCapabilitiesForReachableNodes(): Map<Node, Set<String>> =
        capabilityClient.getAllCapabilities(CapabilityClient.FILTER_REACHABLE)
            .await()
            // Pair the list of all reachable nodes with their capabilities
            .flatMap { (capability, capabilityInfo) ->
                capabilityInfo.nodes.map { it to capability }
            }
            // Group the pairs by the nodes
            .groupBy(
                keySelector = { it.first },
                valueTransform = { it.second }
            )
            // Transform the capability list for each node into a set
            .mapValues { it.value.toSet() }

    private fun displayNodes(nodes: Set<Node>) {
        val message = if (nodes.isEmpty()) {
            getString(R.string.no_device)
        } else {
            getString(R.string.connected_nodes, nodes.joinToString(", ") { it.displayName })
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private suspend fun checkIfPhoneHasApp() {
        Log.d(TAG, "checkIfPhoneHasApp()")

        try {
            val capabilityInfo = capabilityClient
                .getCapability(CAPABILITY_PHONE_APP, CapabilityClient.FILTER_ALL)
                .await()

            Log.d(TAG, "Capability request succeeded.")

            withContext(Dispatchers.Main) {
                // There should only ever be one phone in a node set (much less w/ the correct
                // capability), so I am just grabbing the first one (which should be the only one).
                androidPhoneNodeWithApp = capabilityInfo.nodes.firstOrNull()

            }
        } catch (cancellationException: CancellationException) {
            // Request was cancelled normally
        } catch (throwable: Throwable) {
            Log.d(TAG, "Capability request failed to return any results.")
        }
    }

    companion object {
        private const val TAG = "MainWearActivity"

        // Name of capability listed in Phone app's wear.xml.
        // IMPORTANT NOTE: This should be named differently than your Wear app's capability.
        private const val CAPABILITY_PHONE_APP = "verify_remote_example_phone_app"
        private const val CAPABILITY_WEAR_APP = "verify_remote_example_wear_app"

        // Links to install mobile app for both Android (Play Store) and iOS.
        // TODO: Replace with your links/packages.
        private const val ANDROID_MARKET_APP_URI =
            "market://details?id=com.example.android.wearable.wear.wearverifyremoteapp"

        // TODO: Replace with your links/packages.
        private const val APP_STORE_APP_URI =
            "https://itunes.apple.com/us/app/android-wear/id986496028?mt=8"
    }
}


@Composable
fun AppUi(greetingName: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.hello_world, greetingName)
    )

    Button(onClick = { /*TODO*/ }) {
        Text(text = "Button")
    }
}