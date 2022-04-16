package com.example.dicodingstoryapp1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingstoryapp1.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setupViewModel()

        activityMainBinding.btnLogin.setOnClickListener {
            val inputEmail = activityMainBinding.etEmailLogin.text.toString()
            val inputPassword = activityMainBinding.etPasswordLogin.text.toString()

            login(inputEmail, inputPassword)
        }

        activityMainBinding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) { user ->
            if(user.isLogin) {
                val intent = Intent(this, StoryActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun login(inputEmail: String, inputPassword: String) {
        val client = ApiConfig.getApiService().login(inputEmail, inputPassword)
        client.enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null) {
                    Log.d(TAG, "onResponse: $responseBody")
                    mainViewModel.saveUser(UserAuth(responseBody.loginResult.token, true))
                    val intent = Intent(this@MainActivity, StoryActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "Main Activity"
    }
}