package com.yazhiyue.stylish.util

import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.os.Build
import android.text.TextUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputLayout
import com.yazhiyue.stylish.R
import com.yazhiyue.stylish.component.ColorSquare
import com.yazhiyue.stylish.component.SizeSquare
import com.yazhiyue.stylish.data.Color
import com.yazhiyue.stylish.data.HomeItem
import com.yazhiyue.stylish.home.HomeAdapter
import com.yazhiyue.stylish.payment.PaymentViewModel


@BindingAdapter("homeItems")
fun bindRecyclerViewWithHomeItems(recyclerView: RecyclerView, homeItems: List<HomeItem>?) {
    homeItems?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is HomeAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
            )
            .into(imgView)
    }
}

@BindingAdapter("color")
fun drawColorSquare(imageView: ImageView, color: Color?) {
    color?.let {
        imageView.background = ColorSquare("#${it.code}", imageView.context)
    }
}

@BindingAdapter("color", "selected")
fun drawColorSquareBySelected(imageView: ImageView, color: Color?, isSelected: Boolean = false) {
    color?.let {
        imageView.background = ColorSquare("#${it.code}", imageView.context, isSelected)
    }
}


@BindingAdapter("selected")
fun drawAdd2cartSizeSelectedSquare(textView: TextView, isSelected: Boolean = false) {
    textView.background = SizeSquare(textView.context, isSelected)
}

@BindingAdapter("amount", "stock")
fun bindEditorStatus(textView: TextView, amount: Long, stock: Int) {
    textView.apply {
        background = ShapeDrawable(object : Shape() {
            override fun draw(canvas: Canvas, paint: Paint) {

                paint.color = android.graphics.Color.BLACK
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = textView.context.resources
                    .getDimensionPixelSize(R.dimen.edge_cart_select).toFloat()
                canvas.drawRect(0f, 0f, this.width, this.height, paint)
            }
        })
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("editorControllerStatus")
fun bindEditorControllerStatus(imageButton: ImageButton, enabled: Boolean) {

    imageButton.apply {
        foreground = ShapeDrawable(object : Shape() {
            override fun draw(canvas: Canvas, paint: Paint) {

                paint.color = android.graphics.Color.BLACK
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = imageButton.context.resources
                    .getDimensionPixelSize(R.dimen.edge_add2cart_select).toFloat()
                canvas.drawRect(0f, 0f, this.width, this.height, paint)
            }
        })
        isClickable = enabled
        backgroundTintList = ColorStateList.valueOf(
            imageButton.context.getColor(
                when (enabled) {
                    true -> R.color.black_3f3a3a
                    false -> R.color.gray_999999
                }
            )
        )
        foregroundTintList = ColorStateList.valueOf(
            imageButton.context.getColor(
                when (enabled) {
                    true -> R.color.black_3f3a3a
                    false -> R.color.gray_999999
                }
            )
        )
    }
}

@BindingAdapter("colorCode")
fun bindColorByColorCode(imageView: ImageView, colorCode: String?) {
    colorCode?.let {
        imageView.background = ColorSquare("#$colorCode", imageView.context)
    }
}

@BindingAdapter("isEmpty")
fun setEmptyErrorMessage(view: TextInputLayout, isEmpty: Boolean) {
    if (isEmpty) {
        view.error = "此欄位不能空白"
    } else {
        view.error = null
    }
}
