package com.example.android.pick.model

import com.google.gson.annotations.SerializedName

data class FullDetailsResponse
    (
    @SerializedName("first_name") var first_name: String,
    @SerializedName("last_name") var last_name: String,
    @SerializedName("email_id") var email_id: String,
    @SerializedName("contact_number") var contact_number: String,
    @SerializedName("password") var password: String,

    @SerializedName("status") var status: String,
    @SerializedName("result_code") var result_code: Int
            )
