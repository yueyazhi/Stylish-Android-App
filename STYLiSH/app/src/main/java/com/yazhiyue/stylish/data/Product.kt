package com.yazhiyue.stylish.data


class Product (
    val id: Long,
    val title: String,
    val description :String,
    val image_one: Int,
    val image_two: Int? = null,
    val image_three: Int? = null,
    val image_four: Int? = null

)