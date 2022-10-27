package com.yazhiyue.stylish.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yazhiyue.stylish.data.Product

@Dao
interface StylishDatabaseDao {

    @Insert
    suspend fun insert(product: Product)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param product: [Product]
     */
    @Update
    suspend fun update(product: Product)


    @Query("SELECT * FROM products_in_cart_table ORDER BY product_id ASC")
    fun getAllProduct(): LiveData<List<Product>>


    /**
     * Deletes the [Product] with given id, colorCode and size
     * @param id: [Product.id]
     * @param colorCode: [Product.selectedVariant] [Variant.colorCode]
     * @param size: [Product.selectedVariant] [Variant.size]
     */
    @Query("DELETE from products_in_cart_table WHERE product_id = :id AND product_selected_color_code = :colorCode AND product_selected_size = :size")
    suspend fun delete(id: Long, colorCode: String, size: String)

    /**
     * Selects and return the [Product] with given id, colorCode and size
     * @param id: [Product.id]
     * @param colorCode: [Product.selectedVariant] [Variant.colorCode]
     * @param size: [Product.selectedVariant] [Variant.size]
     */
    @Query("SELECT * from products_in_cart_table WHERE product_id = :id AND product_selected_color_code = :colorCode AND product_selected_size = :size")
    suspend fun get(id: Long, colorCode: String, size: String): Product?


    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM products_in_cart_table")
    fun clear()


    @Query("SELECT COUNT(*) FROM products_in_cart_table")
    fun getProductCount(): LiveData<Int>

}