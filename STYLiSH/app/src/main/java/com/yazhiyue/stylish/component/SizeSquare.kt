package com.yazhiyue.stylish.component

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable

class SizeSquare(context: Context, isSelected: Boolean = false): ShapeDrawable(object : android.graphics.drawable.shapes.Shape() {
    override fun draw(canvas: android.graphics.Canvas, paint: android.graphics.Paint) {
        paint.color = android.graphics.Color.parseColor("#f0f0f0")
        paint.style = Paint.Style.FILL
        canvas.drawRect(0f, 0f, this.width, this.height, paint)

        if (isSelected) {
            paint.color = android.graphics.Color.WHITE
            paint.style = android.graphics.Paint.Style.STROKE
            paint.strokeWidth = context.resources
                .getDimensionPixelSize(com.yazhiyue.stylish.R.dimen.edge_add2cart_color_white).toFloat()
            canvas.drawRect(0f, 0f, this.width, this.height, paint)

            paint.color = android.graphics.Color.BLACK
            paint.style = android.graphics.Paint.Style.STROKE
            paint.strokeWidth = context.resources
                .getDimensionPixelSize(com.yazhiyue.stylish.R.dimen.edge_add2cart_color_black).toFloat()
            canvas.drawRect(0f, 0f, this.width, this.height, paint)
        }
    }
})
