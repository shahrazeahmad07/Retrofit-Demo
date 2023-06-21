package com.example.retrofitdemo.apiinterface

import com.example.retrofitdemo.model.Data2
import com.example.retrofitdemo.model.User
import com.example.retrofitdemo.model.Users
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsersApiInterface {

    @GET("/api/users?page=2")
    suspend fun getAllUsers(): Response<Users>
    // in 2 videos I have seen them returning type call

    @GET("/api/users/{id}")
    suspend fun getUserById(@Path("id") id: String) : Response<User>

    @PUT("/api/users/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body body: JsonObject) : Response<Data2>
}