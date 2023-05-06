package org.d3if00001.storyapp.presentations.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import org.d3if00001.storyapp.databinding.ActivityLoginBinding
import org.d3if00001.storyapp.presentations.viewmodels.HomeViewModel

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var email : String
    private lateinit var passwordEditText: String
    private var backPressedTime = 0L
    private val backPressedInterval = 2000L
    private val homeViewModel: HomeViewModel by viewModels()
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (backPressedTime + backPressedInterval > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            Toast.makeText(this, "Tekan kembali untuk keluar", Toast.LENGTH_SHORT).show()
            backPressedTime = System.currentTimeMillis()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        email = binding.edLoginEmail.text.toString()
        passwordEditText = binding.edLoginPassword.text.toString()

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        playAnimate()
    }
    private fun playAnimate(){

        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA,1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA,1f).setDuration(500)
        val emailTextField = ObjectAnimator.ofFloat(binding.edLoginEmail,View.ALPHA,1f).setDuration(500)
        val passwordTextField = ObjectAnimator.ofFloat(binding.registerButton,View.ALPHA,1f).setDuration(500)

        val showTogether = AnimatorSet().apply {
            playTogether(login,register)
        }

        AnimatorSet().apply {
            playSequentially(emailTextField,passwordTextField,showTogether)
            start()
        }

    }
}