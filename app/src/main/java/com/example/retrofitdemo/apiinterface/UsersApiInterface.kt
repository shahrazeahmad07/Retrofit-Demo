package com.example.retrofitdemo.apiinterface

import com.example.retrofitdemo.model.UsersModelItem
import retrofit2.Response
import retrofit2.http.GET

interface UsersApiInterface {

    @GET(value = "/users")
    suspend fun getUsers() : Response<List<UsersModelItem>>
}