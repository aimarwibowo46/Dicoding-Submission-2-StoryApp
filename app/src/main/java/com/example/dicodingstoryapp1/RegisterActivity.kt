package com.example.dicodingstoryapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        activityRegisterBinding.etEmailRegister.type = "email"
        activityRegisterBinding.etPasswordRegister.type = "password"

        activityRegisterBinding.btnRegister.setOnClickListener {
            val inputName = activityRegisterBinding.etNameRegister.text.toString()
            val inputEmail = activityRegisterBinding.etEmailRegister.text.toString()
            val inputPassword = activityRegisterBinding.etPasswordRegister.text.toString()

            var checked = true

            if(checked) createAccount(inputName, inputEmail, inputPassword)
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
                if(response.isSuccessful && responseBody != null) {
                    Log.d(TAG, "onResponse: $responseBody")
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "Register Activity"
    }

}