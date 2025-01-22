package com.jajoomr.fallingobjects.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jajoomr.fallingobjects.model.GameObject
import com.jajoomr.fallingobjects.viewModel.GameViewModel

@Composable
fun GameScreen(modifier: Modifier, viewModel: GameViewModel) {
    val isGameOver by viewModel.isGameOver.collectAsState()

    if (isGameOver) {
        GameOverScreen(modifier = modifier, viewModel = viewModel)
    } else {
        GameView(modifier = Modifier, viewModel = viewModel)
    }
}

@Composable
fun GameView(modifier: Modifier, viewModel: GameViewModel) {

    val gameObjects by viewModel.gameObjects.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameScore(viewModel.score.intValue)
        Box(
            modifier = modifier.weight(1f).fillMaxWidth()
        ) {
            gameObjects.forEach { gameObject ->
                GameObjectView(gameObject = gameObject, onClick = {
                    viewModel.onGameObjectTapped(gameObject)
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScore(score: Int) {
    val SCORE: String = "Score: "
    TopAppBar(
        title = {
            Text(
                text = "$SCORE $score"
            )
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )

}

@Composable
fun GameObjectView(gameObject: GameObject, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = gameObject.imageResource),
        contentScale = ContentScale.Crop,
        contentDescription = "",
        modifier = Modifier
            .absoluteOffset(x = gameObject.x.dp, y = gameObject.y.dp)
            .size(50.dp)
            .clickable { onClick() }
    )

}

@Composable
fun GameOverScreen(modifier: Modifier, viewModel: GameViewModel) {

    Column(modifier = modifier.fillMaxSize()
        .padding(16.dp)
        .background(color = MaterialTheme.colorScheme.inverseOnSurface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            modifier = modifier,
            text = "Game Over",
            color = MaterialTheme.colorScheme.error
        )

        Text(
            modifier = modifier.padding(16.dp)
                .background(color = MaterialTheme.colorScheme.background),
            text = "Score: " + viewModel.score.intValue,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Button(modifier = modifier,
            onClick =  {
            viewModel.resetGame()
        }) {
            Text(text = "Play Again")
        }
    }
}