package com.egoriku.radiotok.extension

import android.graphics.*

fun Bitmap.roundWithBorder(
    borderColor: Int = Color.parseColor("#FFEE894F"),
    backgroundColor: Int = Color.parseColor("#FF393939"),
    borderWidth: Float = 15f,
    scaleFactor: Int = 2
): Bitmap {
    val newWidth = width * scaleFactor
    val newHeight = height * scaleFactor

    val bitmap = Bitmap.createBitmap(
        newWidth,
        newHeight,
        Bitmap.Config.ARGB_8888
    )

    val paint = Paint().apply {
        isAntiAlias = true
        color = backgroundColor
    }

    Canvas(bitmap).apply {
        drawOval(RectF(0f, 0f, newWidth.toFloat(), newHeight.toFloat()), paint)
        drawBitmap(
            this@roundWithBorder,
            width / scaleFactor / 2f,
            height / scaleFactor / 2f,
            paint
        )

        drawCircle(
            width / 2f,
            width / 2f,
            width / 2f - borderWidth / 2,
            paint.apply {
                color = borderColor
                style = Paint.Style.STROKE
                strokeWidth = borderWidth
            }
        )
    }
    recycle()

    return bitmap
}