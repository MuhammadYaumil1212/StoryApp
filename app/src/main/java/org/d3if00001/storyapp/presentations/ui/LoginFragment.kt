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
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import org.d3if00001.storyapp.R
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.databinding.FragmentLoginBinding
import org.d3if00001.storyapp.presentations.viewmodels.AuthenticationViewModel

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
            authenticationViewModel.getToken()
            authenticationViewModel.getDataToken.observe(viewLifecycleOwner){
                token->if(token?.isNotEmpty() == true) findNavController()
                .navigate(R.id.action_loginFragment_to_homeFragment)
            }
            binding.loginButton.setOnClickListener {
                sanityCheck(
                    email = binding.edLoginEmail.text.toString(),
                    password = binding.edLoginPassword.text.toString()
                )
                authenticationViewModel.getStatus().observe(viewLifecycleOwner){
                    updateProgress(it)
                }
                authenticationViewModel.authentication(
                    email=binding.edLoginEmail.text.toString(),
                    password=binding.edLoginPassword.text.toString()
                )
            }

        binding.registerButton.setOnClickListener { views->
            views.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        playAnimate()
    }
    private fun sanityCheck(email : String,password:String){
        if(email.isEmpty()){
            Toast.makeText(requireContext(),"Email harus di isi",Toast.LENGTH_SHORT).show()
        }else if(password.isEmpty()){
            Toast.makeText(requireContext(),"Password harus di isi",Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateProgress(status: APIService.ApiStatus?) {
        when(status){
            APIService.ApiStatus.SUCCESS->{
                binding.progressBar.visibility =View.GONE
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
            APIService.ApiStatus.LOADING->binding.progressBar.visibility = View.VISIBLE
            APIService.ApiStatus.FAILED->{
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(),
                    "Gagal! Silahkan daftarkan akun anda / coba lagi",
                    Toast.LENGTH_SHORT).show()
            }
            else ->binding.progressBar.visibility = View.GONE
        }
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