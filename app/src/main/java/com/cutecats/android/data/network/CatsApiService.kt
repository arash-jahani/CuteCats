package com.cutecats.android.data.network

import com.cutecats.android.data.model.CatItem
import com.cutecats.android.data.model.CategoryItem
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApiService {

    @GET("v1/categories")
    suspend fun getCatsCategories(): List<CategoryItem>

    @GET("v1/images/search")
    suspend fun getCatsListByCategoryId(
        @Query("category_ids") categoryId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<CatItem>

}