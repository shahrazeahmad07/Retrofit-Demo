package com.example.retrofitdemo

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.res.Resources.Theme
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.retrofitdemo.apiinterface.UsersApiInterface
import com.example.retrofitdemo.apiinterface.UsersRetrofitUtilities
import com.example.retrofitdemo.databinding.ActivityMain1Binding
import com.example.retrofitdemo.databinding.DialogCreateUserBinding
import com.example.retrofitdemo.databinding.DialogShowSingleUserBinding
import com.example.retrofitdemo.databinding.DialogUserIdBinding
import com.example.retrofitdemo.model.Data
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.create

class MainActivity1 : AppCompatActivity() {

    private var binding: ActivityMain1Binding? = null
    private lateinit var usersApi: UsersApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain1Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        //! getting Retrofit API Instance
        usersApi =
            UsersRetrofitUtilities.getRetrofitInstance().create(UsersApiInterface::class.java)

        //! Create User Button
        binding?.btnCreateUser?.setOnClickListener {
            showCreateUserDialog()
        }

        //! Get All users Button
        binding?.btnGetAllUsers?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding?.btnGetSingleUser?.setOnClickListener {
            showIdDialog()
        }
    }

    //! get user ID
    private fun showIdDialog() {
        val dialog = Dialog(this)
        val dialogBinding = DialogUserIdBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.btnOkay.setOnClickListener {
            val userId = dialogBinding.etDialogUserId.text.toString()
            if (userId.isNotEmpty()) {
                getSingleUser(userId)
                dialog.dismiss()
            } else {
                Toast.makeText(this@MainActivity1, "Enter User ID!!", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

    private fun getSingleUser(id: String) {
        //! Single User Data
        lifecycleScope.launch {
            val result = usersApi.getUserById(id)
            if (result.isSuccessful && result.body() != null) {
                showDetails(result.body()!!.data)
            } else {
                if (result.body() == null) {
                    Toast.makeText(
                        this@MainActivity1,
                        "No User Found",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@MainActivity1,
                        "Something Went Wrong: ${result.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    //! Show Single User details in Dialog
    private fun showDetails(data: Data) {
        val dialog = Dialog(this, R.style.ThemeDialog)
        val dialogBinding = DialogShowSingleUserBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        Picasso.get().load(data.avatar).into(dialogBinding.ivUserAvatar)
        val details = "ID: ${data.id}\n" +
                "Name: ${data.first_name} ${data.last_name}\n" +
                "Email: ${data.email}"
        dialogBinding.tvUserName.text = details
        dialog.show()
    }

    //! Show Create User dialog to get the user details
    private fun showCreateUserDialog() {
        val dialog = Dialog(this, R.style.ThemeDialog)
        val dialogBinding = DialogCreateUserBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.btnDialogCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnCreateUser.setOnClickListener {
            val name = dialogBinding.etDialogName.text
            val job = dialogBinding.etDialogJob.text
            if (name.isNotEmpty() && job.isNotEmpty()) {
                createUser(name.toString(), job.toString())
            } else {
                Toast.makeText(this, "Enter both fields!!", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    //! Create User Function
    private fun createUser(name: String, job: String) {
        //! Create User
        lifecycleScope.launch {
            val body = JsonObject().apply {
                addProperty("name", name)
                addProperty("job", job)
            }
            val result = usersApi.createUser(body)
            if (result.isSuccessful) {

                val builder = AlertDialog.Builder(this@MainActivity1)
                builder.setTitle("User Created")
                builder.setMessage(
                    "Name: ${result.body()?.name}\n" +
                            "ID: ${result.body()?.id}\n" +
                            "Job: ${result.body()?.job}\n" +
                            "Date Created: ${result.body()?.createdAt}"
                )
                builder.setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.setCancelable(false)
                builder.create().show()
            } else {
                Toast.makeText(
                    this@MainActivity1,
                    "There was some error: ${result.message()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //! overriding on Destroy
    override fun onDestroy() {
        super.onDestroy()
        if (binding != null) {
            binding = null
        }
    }
}