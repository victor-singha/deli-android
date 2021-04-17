package com.example.android.pick.model

import com.google.gson.annotations.SerializedName

data class DefaultResponse (
    @SerializedName("status") var status: String,
    @SerializedName("result_code") var result_code: Int
        )
