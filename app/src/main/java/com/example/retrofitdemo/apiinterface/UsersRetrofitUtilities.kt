package com.example.retrofitdemo.apiinterface

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UsersRetrofitUtilities {
    private val BASE_URL = "https://reqres.in/"

    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}