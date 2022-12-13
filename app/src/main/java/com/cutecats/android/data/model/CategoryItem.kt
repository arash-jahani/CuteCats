package com.cutecats.android.data.model

import com.google.gson.annotations.SerializedName

data class CategoryItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
