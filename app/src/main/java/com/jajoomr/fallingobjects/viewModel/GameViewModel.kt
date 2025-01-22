package com.jajoomr.fallingobjects.viewModel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jajoomr.fallingobjects.model.GameObject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(
    private val screenHeight: Float,
    private val screenWidth: Float
) : ViewModel() {
    private val _gameObjects = MutableStateFlow<List<GameObject>>(emptyList())
    val gameObjects
        get() = _gameObjects

    val score = mutableIntStateOf(0)

    val isOver = MutableStateFlow(false)

    val isGameOver = MutableStateFlow(false)
    private var nextId = 0;
    private var yPositionIncrement = 2f;

    companion object {
        const val START_Y = 0f;
        const val COIN_BONUS = 10;
        const val OBJECT_BONUS = 5;
        const val SMALL_DELAY = 500L
        const val GAME_SPEED_INCREMENT = 0.2f
    }


    init {
        startGame()
    }

    private fun startGame() {
        //add game objects
        viewModelScope.launch {
            while (!isGameOver.value) {
                addGameObject()
                delay(SMALL_DELAY)
            }
        }

        //update game objects
        viewModelScope.launch {
            while (!isGameOver.value) {
                updateGameObjects()
                delay(16)
            }
        }

        //update game speed
        viewModelScope.launch {
            while (!isGameOver.value) {
                updateGameSpeed()
                delay(1000)
            }
        }
    }

    private fun addGameObject() {
        val isCoin: Boolean = Random.nextInt() % 3 == 0 // for lesser probability of coins
        val xPosition = Random.nextFloat() * screenWidth
        val gameObject = GameObject(nextId++, xPosition, START_Y, isCoin)
        _gameObjects.value += gameObject
    }

    private fun updateGameObjects() {
        val newGameObject = _gameObjects.value.map { gameObject ->
            gameObject.copy(
                y = gameObject.y + yPositionIncrement
            )
        }

        if (newGameObject.any() { gameObject ->
                (!gameObject.isCoin) && (gameObject.y >= screenHeight)
            }) {
            endGame()
        } else {
            _gameObjects.value = newGameObject.filter { gameObject ->
                gameObject.y < screenHeight
            }
        }
    }

    private fun updateGameSpeed() {
        yPositionIncrement+= yPositionIncrement/50;
    }

    private fun endGame() {
        isGameOver.value = true
        _gameObjects.value = emptyList()
    }

    fun onGameObjectTapped(tappedGameObject: GameObject) {
        _gameObjects.value = _gameObjects.value.filter { gameObject ->
            //delete tapped object
            gameObject.id != tappedGameObject.id
        }

        //update score
        score.intValue += if (tappedGameObject.isCoin) COIN_BONUS else OBJECT_BONUS
    }

    fun resetGame() {
        _gameObjects.value = emptyList()
        score.intValue = 0;
        isGameOver.value = false
        startGame()
    }

}