package org.d3if00001.storyapp.presentations.ui

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
import org.d3if00001.storyapp.R
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.databinding.FragmentRegisterBinding
import org.d3if00001.storyapp.presentations.viewmodels.AuthenticationViewModel

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val authenticationViewModel : AuthenticationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerButton.setOnClickListener {
            sanityCheck(
                name=binding.edRegisterName.text.toString(),
                email = binding.edRegisterEmail.text.toString(),
                password = binding.edRegisterPassword.text.toString()
            )
            authenticationViewModel.getStatus().observe(viewLifecycleOwner) {
                updateProgress(it)
            }
            authenticationViewModel.register(
                name = binding.edRegisterName.text.toString(),
                email = binding.edRegisterEmail.text.toString(),
                password = binding.edRegisterPassword.text.toString()
            )

        }
    }

    private fun updateProgress(status: APIService.ApiStatus?) {
        when(status){
            APIService.ApiStatus.SUCCESS->{
                binding.progressBar.visibility =View.GONE
                Toast.makeText(requireContext(),"Berhasil! Silahkan Login",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
            APIService.ApiStatus.LOADING->binding.progressBar.visibility = View.VISIBLE
            APIService.ApiStatus.FAILED->{
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(),
                    "Gagal! Silahkan coba lagi",
                    Toast.LENGTH_SHORT).show()
            }
            else -> binding.progressBar.visibility = View.GONE
        }
    }

    private fun sanityCheck(email : String, name:String, password:String){
            if(email.isEmpty()){
                Toast.makeText(requireContext(),"Email harus di isi",Toast.LENGTH_SHORT).show()
            }else if(name.isEmpty()){
                Toast.makeText(requireContext(),"Name harus di isi",Toast.LENGTH_SHORT).show()
            }else if(password.isEmpty()){
                Toast.makeText(requireContext(),"Password harus di isi",Toast.LENGTH_SHORT).show()
            }
        }
}