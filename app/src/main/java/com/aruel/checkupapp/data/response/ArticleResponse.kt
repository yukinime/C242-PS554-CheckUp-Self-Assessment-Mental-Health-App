package com.aruel.checkupapp.data.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

data class DataItem(

	@field:SerializedName("snippet")
	val snippet: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
