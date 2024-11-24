package com.example.basiccompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basiccompose.ui.theme.BasicComposeTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.animation.core.animateDpAsState
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.res.stringResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicComposeTheme {
                // basic UI element
//                MyApp(modifier = Modifier.fillMaxSize())
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )*/
                // UI for element with button
                // MyAppFor()
                // UI with state
                MyAppState()
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        // sample multiple modifiers to an element, you simply chain them
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Hello",
            )
            Text(
                text = "$name!",
            )
        }
    }
}

// samepl set up dark mode
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    BasicComposeTheme {
        //Greeting("Android") // sample view hello world
        //MyAppFor()          // sample view with for
        GreetingsList()       // sample view with list
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Greeting("Android")
    }
}

@Composable
fun MyAppFor(
    modifier: Modifier = Modifier, names: List<String> = listOf("World", "Compose")
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier) {
            // sample for
            for (name in names) {
                //Greeting(name = name)
                GreetingWithButton(name = name)
            }
        }

    }
}

@Composable
fun GreetingWithButton(name: String, modifier: Modifier = Modifier) {
    // remember, used to guard against recomposition
    // mutableStateOf, to trigger UI updates
    //val expanded = remember { mutableStateOf(false) }
    // save each state surviving configuration changes (rotations and process death.)
    var expanded by rememberSaveable { mutableStateOf(false) }

    // to expand an item
    //val extraPadding = if (expanded.value) 48.dp else 0.dp
    // to animate the expand
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp
    )
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)//to apply expand component
            ) {
                Text(text = "Hello ")
                Text(text = name)
            }
            // sample button
            ElevatedButton(
                onClick = { expanded = !expanded }
            ) {
                // change text button based on expanded value after click
                Text(if (expanded) {
                // sample use strings
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                })
            }
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            GreetingWithButton(name = name)
        }
    }
}

@Composable
private fun GreetingsList(
    modifier: Modifier = Modifier,
    names: List<String> = List(5) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            GreetingWithButton(name = name)
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BasicComposeTheme {
        MyAppState(Modifier.fillMaxSize())
    }
}

@Composable
fun MyAppState(modifier: Modifier = Modifier) {
    // var shouldShowOnboarding by remember { mutableStateOf(true) }
    // save each state surviving configuration changes (rotations and process death.)
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            // pass callback down returned when clicked setting var to false
            OnboardingScreen(
                onContinueClicked = { shouldShowOnboarding = false },
                message = "continue"
            )
        } else {
            Column {
                //Greetings() list with hard values
                GreetingsList()
                OnboardingScreen(
                    onContinueClicked = { shouldShowOnboarding = true },
                    message = "return main"
                )
            }
        }
    }
}


@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier,
    message: String
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier
                .padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text(message)
        }
    }

}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicComposeTheme {
        OnboardingScreen(onContinueClicked = {}, message = "Continue")
    }
}

