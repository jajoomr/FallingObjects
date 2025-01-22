package com.jajoomr.fallingobjects.model

import com.jajoomr.fallingobjects.R

data class GameObject(
    val id: Int,
    var x: Float,
    var y: Float,
    val isCoin: Boolean
) {
    val imageResource: Int
        get() = if (isCoin) R.drawable.coin else R.drawable.ball
}
