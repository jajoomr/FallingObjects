package com.jajoomr.fallingobjects

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.jajoomr.fallingobjects.compose.GameScore
import com.jajoomr.fallingobjects.compose.GameScreen
import com.jajoomr.fallingobjects.compose.GameView
import com.jajoomr.fallingobjects.ui.theme.FallingObjectsTheme
import com.jajoomr.fallingobjects.viewModel.GameViewModel
import com.jajoomr.fallingobjects.viewModel.GameViewModelFactory


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val density = TypedValue.TYPE_FLOAT
            val height = resources.displayMetrics.heightPixels.toFloat() / density;
            val width = resources.displayMetrics.widthPixels.toFloat() / density;
            val gameViewModelFactory = GameViewModelFactory(height, width)

            val viewModel: GameViewModel = ViewModelProvider(this, gameViewModelFactory)[GameViewModel::class.java]
            FallingObjectsTheme {
                GameScreen(modifier = Modifier, viewModel = viewModel)
//                Scaffold(modifier = Modifier,
//                    topBar = { GameScore(viewModel.score.intValue) }
//                ) {
//                    GameScreen(modifier = Modifier, viewModel = viewModel)
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FallingObjectsTheme {
        Greeting("Android")
    }
}