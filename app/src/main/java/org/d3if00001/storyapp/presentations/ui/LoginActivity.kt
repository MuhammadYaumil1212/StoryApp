package org.d3if00001.storyapp.presentations.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import org.d3if00001.storyapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var backPressedTime = 0L
    private val backPressedInterval = 2000L

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

        binding.loginButton

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