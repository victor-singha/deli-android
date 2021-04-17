package com.example.android.pick.api

import com.example.android.pick.model.DefaultResponse
import com.example.android.pick.model.ForgotPasswordResponse
import com.example.android.pick.model.FullDetailsResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiInterface {
    @FormUrlEncoded
    @POST("register_user.php")
    fun createUser(
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("email_id") email_id: String,
        @Field("contact_number") contact_number: String,
        @Field("password") password: String
    ):Call<DefaultResponse>

    @FormUrlEncoded
    @POST("login_user.php")
    fun login(
        @Field("email_id") email_id: String,
        @Field("password") password: String
    ):Call<FullDetailsResponse>

    @FormUrlEncoded
    @POST("new_order.php")
    fun insertOrder(
        @Field("pick_location") pick_location: String,
        @Field("drop_location") drop_location: String,
        @Field("pick_date") pick_date: String,
        @Field("vehicle") vehicle: String,
        @Field("amount") amount: String,
        @Field("full_name") full_name: String,
        @Field("email_id") email_id: String,
        @Field("contact_number") contact_number: String,
        @Field("parcel") parcel: String

    ):Call<DefaultResponse>

    @FormUrlEncoded
    @POST("forgot_password.php")
    fun sendPassword(
        @Field("email_id") email_id: String
    ):Call<ForgotPasswordResponse>


    @FormUrlEncoded
    @POST("mail_order_details.php")
    fun mailOrderDetails(
        @Field("pick_location") pick_location: String,
        @Field("drop_location") drop_location: String,
        @Field("pick_date") pick_date: String,
        @Field("vehicle") vehicle: String,
        @Field("amount") amount: String,
        @Field("full_name") full_name: String,
        @Field("email_id") email_id: String,
        @Field("contact_number") contact_number: String,
        @Field("parcel") parcel: String
    ):Call<DefaultResponse>








}