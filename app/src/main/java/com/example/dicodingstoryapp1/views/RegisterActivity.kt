package com.example.dicodingstoryapp1.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dicodingstoryapp1.ApiConfig
import com.example.dicodingstoryapp1.MainActivity
import com.example.dicodingstoryapp1.R
import com.example.dicodingstoryapp1.RegisterResponse
import com.example.dicodingstoryapp1.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var activityRegisterBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

        activityRegisterBinding.etNameRegister.type = "name"
        activityRegisterBinding.etEmailRegister.type = "email"
        activityRegisterBinding.etPasswordRegister.type = "password"

        activityRegisterBinding.btnRegister.setOnClickListener {
            val inputName = activityRegisterBinding.etNameRegister.text.toString()
            val inputEmail = activityRegisterBinding.etEmailRegister.text.toString()
            val inputPassword = activityRegisterBinding.etPasswordRegister.text.toString()

            createAccount(inputName, inputEmail, inputPassword)
        }
    }

    private fun createAccount(inputName: String, inputEmail: String, inputPassword: String) {
        val client = ApiConfig.getApiService().createAccount(inputName, inputEmail, inputPassword)
        client.enqueue(object: Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val responseBody = response.body()
                Log.d(TAG, "onResponse: $responseBody")
                if(response.isSuccessful && responseBody?.message == "User created") {
                    Toast.makeText(this@RegisterActivity, getString(R.string.register_success), Toast.LENGTH_LONG).show()
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")
                    Toast.makeText(this@RegisterActivity, getString(R.string.register_fail), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG, "onFailure2: ${t.message}")
                Toast.makeText(this@RegisterActivity, getString(R.string.register_fail), Toast.LENGTH_LONG).show()
            }

        })
    }

    companion object {
        private const val TAG = "Register Activity"
    }

}