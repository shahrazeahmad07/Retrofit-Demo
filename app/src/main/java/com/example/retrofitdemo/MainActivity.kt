package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitdemo.adapter.UsersRvAdapter
import com.example.retrofitdemo.apiinterface.UsersApiInterface
import com.example.retrofitdemo.apiinterface.UsersRetrofitUtilities
import com.example.retrofitdemo.databinding.ActivityMainBinding
import com.example.retrofitdemo.model.Data
import com.example.retrofitdemo.model.Users
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersApi: UsersApiInterface
    private lateinit var usersRvAdapter: UsersRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //! recycler view
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(this)

        //! setting retrofit object.
        usersApi = UsersRetrofitUtilities.getRetrofitInstance().create(UsersApiInterface::class.java)

        val mutableLiveDataList = MutableLiveData<Users>()
        var liveDataListOfUsers : LiveData<Users>


        //! Get All Users
        lifecycleScope.launch {
            val result = usersApi.getAllUsers()

            if (result.body() != null) {
                mutableLiveDataList.postValue(result.body())
                liveDataListOfUsers = mutableLiveDataList
                liveDataListOfUsers.observe(this@MainActivity) {
                    list ->
                    showAllUsers(list.data)
                }
            }
        }

        //! Update User
        lifecycleScope.launch {
            val body = JsonObject().apply {
                addProperty("name", "Shahzaib")
                addProperty("job", "Android Developer")
            }
            val result = usersApi.updateUser("7", body)
            if (result.isSuccessful) {
                Log.d("Updated User", result.body().toString())
            } else {
                Log.d("Single User", result.message())
            }

        }

        //! Delete User
        lifecycleScope.launch {
            val result = usersApi.deleteUserById("3")
            if (result.isSuccessful) {
                Log.d("Deleted User", result.body().toString())
            } else {
                Log.d("Single User", result.message())
            }
        }
    }

    private fun showAllUsers(list: List<Data>?) {
        if (list?.isNotEmpty()!!) {
            binding.usersRecyclerView.visibility = View.VISIBLE
            binding.tvNoRecords.visibility = View.GONE
            usersRvAdapter = UsersRvAdapter(list)
            binding.usersRecyclerView.adapter = usersRvAdapter
        } else {
            binding.usersRecyclerView.visibility = View.GONE
            binding.tvNoRecords.visibility = View.VISIBLE
        }
    }
}