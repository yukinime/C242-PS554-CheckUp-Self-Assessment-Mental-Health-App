package com.aruel.checkupapp.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null
)
