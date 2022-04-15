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
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXFnSUFGNC15dUZZZVNVelkiLCJpYXQiOjE2NDk4MzYyMzV9.0OYl1CMCn62wdiIhEY6GNZvopL5mT3d6CewDwoKa0C8")
    fun getStories() : Call<StoriesResponse>

    @Multipart
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXFnSUFGNC15dUZZZVNVelkiLCJpYXQiOjE2NDk4MzYyMzV9.0OYl1CMCn62wdiIhEY6GNZvopL5mT3d6CewDwoKa0C8")
    @POST("stories")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<FileUploadResponse>
}