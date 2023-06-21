package com.example.retrofitdemo.apiinterface

import com.example.retrofitdemo.model.User
import com.example.retrofitdemo.model.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersApiInterface {

    @GET("/api/users?page=2")
    suspend fun getAllUsers(): Response<Users>
    // in 2 videos I have seen them returning type call

    @GET("/api/users/{id}")
    suspend fun getUserById(@Path("id") id: String) : Response<User>
}