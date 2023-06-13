package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitdemo.adapter.UsersRvAdapter
import com.example.retrofitdemo.apiinterface.UsersApiInterface
import com.example.retrofitdemo.apiinterface.UsersRetrofitUtilities
import com.example.retrofitdemo.databinding.ActivityMainBinding
import com.example.retrofitdemo.model.UsersModel
import kotlinx.coroutines.GlobalScope
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


        lifecycleScope.launch {
            val result = usersApi.getUsers()

            if (result.body() != null) {
                showAllUsers(result.body())
            }
        }
    }

    private fun showAllUsers(list: List<UsersModel>?) {
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