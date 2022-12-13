package com.cutecats.android.data

import com.cutecats.android.data.model.CatItem
import com.cutecats.android.data.model.CategoryItem
import com.cutecats.android.data.network.CatsApiService
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val apiService: CatsApiService
) : DataRepository {

    override suspend fun getCatsCategories(): List<CategoryItem> {
        return apiService.getCatsCategories()
    }

    override suspend fun getCatsListByCategoryId(
        categoryId: String,
        page: Int,
        limit: Int
    ): List<CatItem> {
        return apiService.getCatsListByCategoryId(categoryId, page, limit)
    }
}