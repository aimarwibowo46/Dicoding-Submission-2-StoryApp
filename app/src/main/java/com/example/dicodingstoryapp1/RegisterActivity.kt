package com.example.dicodingstoryapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingstoryapp1.database.User
import com.example.dicodingstoryapp1.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var activityRegisterBinding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

        registerViewModel = obtainViewModel(this)

        activityRegisterBinding.btnRegister.setOnClickListener {
            val inputName = activityRegisterBinding.etNameRegister.text.toString()
            val inputEmail = activityRegisterBinding.etEmailRegister.text.toString()
            val inputPassword = activityRegisterBinding.etPasswordRegister.text.toString()

            var checked = true
            if(inputName.isEmpty()) {
                activityRegisterBinding.etNameRegister.error = getString(R.string.warning_name_field)
                checked = false
            }
            if(inputEmail.isEmpty()) {
                activityRegisterBinding.etEmailRegister.error = getString(R.string.warning_email_field)
                checked = false
            }
            if(inputPassword.isEmpty()) {
                activityRegisterBinding.etPasswordRegister.error = getString(R.string.warning_password_field)
                checked = false
            }

            registerViewModel.isEmailListed(inputEmail).observe(this) {checkedEmail ->
                if(checkedEmail > 0) {
                    Log.d(TAG, "onCreate1: $checkedEmail")
                    activityRegisterBinding.etEmailRegister.error = getString(R.string.warning_email_registered)
                    checked = false
                }
            }

//            Log.d(TAG, "onCreate2: ${registerViewModel.isEmailListed(inputEmail)}")
//            if(registerViewModel.isEmailListed(inputEmail) > 0) {
//                activityRegisterBinding.etEmailRegister.error = getString(R.string.warning_email_registered)
//                checked = false
//            }

            if(checked) {
                registerViewModel.insert(
                    User(
                        name = inputName,
                        email = inputEmail,
                        password = inputPassword
                    )
                )
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
            }

        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): RegisterViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[RegisterViewModel::class.java]
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}