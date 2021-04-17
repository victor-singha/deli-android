package com.example.android.pick.model

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse (
    @SerializedName("status") var status: String,
    @SerializedName("result_code") var result_code: Int,
    @SerializedName("email_id") var email_id: String,
    @SerializedName("password") var password: String
)