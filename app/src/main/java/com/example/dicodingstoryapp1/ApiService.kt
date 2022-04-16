package com.example.dicodingstoryapp1

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun createAccount(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    @GET("stories")
    fun getStories(
        @Header("Authorization") header: String
    ) : Call<StoriesResponse>

    @Multipart
    @POST("stories")
    //@Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXFnSUFGNC15dUZZZVNVelkiLCJpYXQiOjE2NTAwNzc1ODV9.ho6Y8cBCp55Y6vr-HHoZ9r4e0DAnmjdql4goR7qd5AM")
    fun uploadImage(
        @Header("Authorization") header: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<FileUploadResponse>
}