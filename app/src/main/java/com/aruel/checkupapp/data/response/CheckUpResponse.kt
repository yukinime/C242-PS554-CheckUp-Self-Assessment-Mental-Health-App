package com.aruel.checkupapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CheckUpResponse(

	@field:SerializedName("severity")
	val severity: String? = null,

	@field:SerializedName("suggestion")
	val suggestion: String? = null,

	@field:SerializedName("predictedClass")
	val predictedClass: String? = null,

	@field:SerializedName("definition")
	val definition: String? = null,

	@field:SerializedName("category")
	val category: String? = null
): Parcelable
