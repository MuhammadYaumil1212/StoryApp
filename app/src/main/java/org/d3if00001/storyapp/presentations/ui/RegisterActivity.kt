package org.d3if00001.storyapp.presentations.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import org.d3if00001.storyapp.R
import org.d3if00001.storyapp.databinding.ActivityRegisterBinding
import org.d3if00001.storyapp.presentations.viewmodels.HomeViewModel

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var email : Editable
    private lateinit var passwordEditText: Editable
    private lateinit var name: Editable
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = binding.edRegisterEmail.text!!
        passwordEditText = binding.edRegisterPassword.text!!
        name = binding.edRegisterName.text!!

        homeViewModel.getAuthToken()
        homeViewModel.isLoggedIn.observe(this){isLoggedIn ->
            if (isLoggedIn){
                startActivity(Intent(this,HomeActivity::class.java))
            }
        }

        binding.registerButton.setOnClickListener {
            homeViewModel.setAuthToken(resources.getString(R.string.token))
            val goToHome = Intent(this@RegisterActivity,HomeActivity::class.java)
            startActivity(goToHome)
        }
    }
}