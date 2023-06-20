package com.example.retrofitdemo.apiinterface

import com.example.retrofitdemo.model.Users
import retrofit2.Response
import retrofit2.http.GET

interface UsersApiInterface {

    @GET("/api/users?page=2")
    suspend fun getUsers(): Response<Users>
    // in 2 videos I have seen them returning type call
}