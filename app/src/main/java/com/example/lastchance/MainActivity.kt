package com.example.lastchance

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.lastchance.ui.theme.LastChanceTheme
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity(), CapabilityClient.OnCapabilityChangedListener {

    private val dataClient by lazy { Wearable.getDataClient(this) }
    private val messageClient by lazy { Wearable.getMessageClient(this) }
    private val capabilityClient by lazy { Wearable.getCapabilityClient(this) }

    private var wearNodesWithApp: Set<Node>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LastChanceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android", onStartWearableActivityClick = ::startWearableActivity)
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    // Initial request for devices with our capability, aka, our Wear app installed.
                    findWearDevicesWithApp()
                }
            }
        }

    }

    override fun onPause() {
        Log.d(TAG, "onPause()")
        super.onPause()
        capabilityClient.removeListener(this, CAPABILITY_WEAR_APP)
    }

    override fun onResume() {
        Log.d(TAG, "onResume()")
        super.onResume()
        capabilityClient.addListener(this, CAPABILITY_WEAR_APP)
    }


    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {
        Log.d(TAG, "onCapabilityChanged(): $capabilityInfo")
        wearNodesWithApp = capabilityInfo.nodes
    }

    private fun startWearableActivity() {
        lifecycleScope.launch {
            try {
                val nodes = capabilityClient
                    .getCapability(CAPABILITY_WEAR_APP, CapabilityClient.FILTER_REACHABLE)
                    .await()
                    .nodes

                // Send a message to all nodes in parallel
                nodes.map { node ->
                    async {
                        messageClient.sendMessage(node.id, START_ACTIVITY_PATH, byteArrayOf())
                            .await()
                    }
                }.awaitAll()

                Log.d(TAG, "Starting activity requests sent successfully")
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                Log.d(TAG, "Starting activity failed: $exception")
            }
        }
    }

    private suspend fun findWearDevicesWithApp() {
        Log.d(TAG, "findWearDevicesWithApp()")

        try {
            val capabilityInfo = capabilityClient
                .getCapability(CAPABILITY_WEAR_APP, CapabilityClient.FILTER_ALL)
                .await()

            withContext(Dispatchers.Main) {
                Log.d(TAG, "Capability request succeeded.")
                wearNodesWithApp = capabilityInfo.nodes
                Log.d(TAG, "Capable Nodes: $wearNodesWithApp")
            }
        } catch (cancellationException: CancellationException) {
            // Request was cancelled normally
            throw cancellationException
        } catch (throwable: Throwable) {
            Log.d(TAG, "Capability request failed to return any results.")
        }
    }

    companion object {
        private const val TAG = "MainMobileActivity"

        // Name of capability listed in Wear app's wear.xml.
        // IMPORTANT NOTE: This should be named differently than your Phone app's capability.
        private const val CAPABILITY_WEAR_APP = "verify_remote_example_wear_app"

        private const val START_ACTIVITY_PATH = "/start-activity"

        // Links to Wear app (Play Store).
        // TODO: Replace with your links/packages.
        private const val PLAY_STORE_APP_URI =
            "market://details?id=com.example.android.wearable.wear.wearverifyremoteapp"
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier,   onStartWearableActivityClick: () -> Unit) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )

    Button(onClick = onStartWearableActivityClick) {
        Text(text = "Button")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LastChanceTheme {
//        Greeting("Android")
    }
}