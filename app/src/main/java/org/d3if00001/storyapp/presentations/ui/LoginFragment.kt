package org.d3if00001.storyapp.presentations.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.d3if00001.storyapp.R
import org.d3if00001.storyapp.data.local.room.entity.User
import org.d3if00001.storyapp.databinding.FragmentLoginBinding
import org.d3if00001.storyapp.presentations.viewmodels.AuthenticationViewModel
import org.d3if00001.storyapp.presentations.viewmodels.HomeViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val authenticationViewModel: AuthenticationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(authenticationViewModel.getLoggedIn() == true){
            view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
        binding.loginButton.setOnClickListener {view->
            authenticationViewModel.loginAccount(binding.edLoginEmail.text.toString())
            view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.registerButton.setOnClickListener { view->
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
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