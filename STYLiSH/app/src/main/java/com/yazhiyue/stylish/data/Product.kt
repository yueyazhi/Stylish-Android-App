package com.yazhiyue.stylish.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import com.yazhiyue.stylish.data.source.local.StylishConverters
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity(
    tableName = "products_in_cart_table",
    primaryKeys = ["product_id", "product_selected_color_code", "product_selected_size"]
)
@TypeConverters(StylishConverters::class)
@Parcelize
data class Product(
    @ColumnInfo(name = "product_id")
    val id: Long,
    @ColumnInfo(name = "product_category")
    val category: String,
    @ColumnInfo(name = "product_title")
    val title: String,
    @ColumnInfo(name = "product_description")
    val description: String,
    @ColumnInfo(name = "product_price")
    val price: Int,
    @ColumnInfo(name = "product_texture")
    val texture: String,
    @ColumnInfo(name = "product_wash")
    val wash: String,
    @ColumnInfo(name = "product_place")
    val place: String,
    @ColumnInfo(name = "product_note")
    val note: String,
    @ColumnInfo(name = "product_story")
    val story: String,
    @ColumnInfo(name = "product_colors")
    val colors: @RawValue List<Color>,
    @ColumnInfo(name = "product_sizes")
    val sizes: @RawValue List<String>,
    @ColumnInfo(name = "product_variants")
    val variants: @RawValue List<Variant>,
    @ColumnInfo(name = "product_main_image")
    @Json(name = "main_image") val mainImage: String,
    @ColumnInfo(name = "product_images")
    val images: List<String>
) : Parcelable {
    @Embedded
    var selectedVariant: Variant = Variant("", "", -1)
    var quantity: Int? = null
}