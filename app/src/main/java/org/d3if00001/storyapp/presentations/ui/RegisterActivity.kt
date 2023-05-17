package org.d3if00001.storyapp.presentations.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Switch
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import org.d3if00001.storyapp.R
import org.d3if00001.storyapp.data.local.room.entity.User
import org.d3if00001.storyapp.databinding.ActivityRegisterBinding
import org.d3if00001.storyapp.presentations.viewmodels.AuthenticationViewModel
import org.d3if00001.storyapp.presentations.viewmodels.HomeViewModel

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private val authenticationViewModel : AuthenticationViewModel by viewModels()
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.registerButton.setOnClickListener {
            sanityCheck(
                name=binding.edRegisterName.text.toString(),
                email = binding.edRegisterEmail.text.toString(),
                password = binding.edRegisterPassword.text.toString()
            )
        }

    }
        private fun sanityCheck(email : String, name:String, password:String){
            if(email.isEmpty()){
                Toast.makeText(this,"Email harus di isi",Toast.LENGTH_SHORT).show()
            }else if(name.isEmpty()){
                Toast.makeText(this,"Name harus di isi",Toast.LENGTH_SHORT).show()
            }else if(password.isEmpty()){
                Toast.makeText(this,"Password harus di isi",Toast.LENGTH_SHORT).show()
            }else{
               authenticationViewModel.registerAccount(
                    User(
                        name = name,
                        email = email,
                        password = password
                    )
                )
                startActivity(Intent(this,LoginActivity::class.java))
                Toast.makeText(this,"Berhasil! Silahkan Melakukan Login!",Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
        }
}