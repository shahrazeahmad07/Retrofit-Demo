package com.example.retrofitdemo.apiinterface

import com.example.retrofitdemo.model.UsersModel
import retrofit2.Response
import retrofit2.http.GET

interface UsersApiInterface {

    @GET(value = "/users")
    suspend fun getUsers() : Response<List<UsersModel>>
    // here I have seen people using call return type as well. which I have to try next
}