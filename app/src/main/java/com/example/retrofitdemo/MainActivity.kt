package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.retrofitdemo.apiinterface.UsersApiInterface
import com.example.retrofitdemo.apiinterface.UsersRetrofitUtilities
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var usersApi: UsersApiInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersApi = UsersRetrofitUtilities.getRetrofitInstance().create(UsersApiInterface::class.java)

        lifecycleScope.launch {
            val result = usersApi.getUsers()

            if (result.body() != null) {
                Log.d("data", "${result.body()!![0]}")
            }
        }
    }
}