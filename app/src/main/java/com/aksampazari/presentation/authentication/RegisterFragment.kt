package com.aksampazari.presentation.authentication

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.PasswordTransformationMethod
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.aksampazari.R
import com.aksampazari.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.dmoral.toasty.Toasty

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding ?= null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spannable = SpannableString(getString(R.string.already_registered))
        spannable.setSpan(
            ForegroundColorSpan(Color.GREEN),
            20,
            26,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvGoLogin.text = spannable

        binding.tvGoLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        val eyeClose = binding.ibEyeClose
        val eyeOpen = binding.ibEyeOpen
        val eyeCloseVerification = binding.ibEyeCloseVerification
        val eyeOpenVerification = binding.ibEyeOpenVerification
        val registerPassword = binding.etRegisterPassword
        val registerPasswordVerification= binding.etRegisterPasswordVerification

        eyeClose.setOnClickListener {
            eyeClose.visibility = View.INVISIBLE
            eyeOpen.visibility = View.VISIBLE
            registerPassword.transformationMethod = null
            registerPassword.setSelection(registerPassword.text?.length ?: 0)
        }

        eyeOpen.setOnClickListener {
            eyeOpen.visibility = View.INVISIBLE
            eyeClose.visibility = View.VISIBLE
            registerPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            registerPassword.setSelection(registerPassword.text?.length ?: 0)
        }

        eyeCloseVerification.setOnClickListener {
            eyeCloseVerification.visibility = View.INVISIBLE
            eyeOpenVerification.visibility = View.VISIBLE
            registerPasswordVerification.transformationMethod = null
            registerPasswordVerification.setSelection(registerPasswordVerification.text?.length ?: 0)
        }

        eyeOpenVerification.setOnClickListener {
            eyeOpenVerification.visibility = View.INVISIBLE
            eyeCloseVerification.visibility = View.VISIBLE
            registerPasswordVerification.transformationMethod = PasswordTransformationMethod.getInstance()
            registerPasswordVerification.setSelection(registerPasswordVerification.text?.length ?: 0)
        }

        auth = Firebase.auth
        binding.btnRegister.setOnClickListener {
            val email = binding.etRegisterEmail.text.toString()
            val password = binding.etRegisterPassword.text.toString().trim()
            val passwordVerification = binding.etRegisterPasswordVerification.text.toString().trim()

            if (!isItConnected()){
                Toasty.warning(
                    requireContext(),
                    getText(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT,
                    true
                ).show()
                return@setOnClickListener
            }

            if (email.isEmpty() || password.isEmpty()){
                Toasty.warning(
                    requireContext(),
                    getText(R.string.email_password_not_blank),
                    Toast.LENGTH_SHORT,
                    true
                ).show()
                return@setOnClickListener
            }

            if (password == passwordVerification){
                registerUser(email, password)
            }else{
                Toasty.warning(
                    requireContext(),
                    getText(R.string.password_not_confirmed),
                    Toast.LENGTH_SHORT,
                    true
                ).show()
                return@setOnClickListener
            }
        }
    }

    private fun isItConnected(): Boolean {
        val connectivityManager = requireContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Toasty.success(
                        requireContext(),
                        getText(R.string.register_successful),
                        Toast.LENGTH_SHORT,
                        true).show()
                    findNavController().navigate(R.id.action_registerFragment_to_bazaarFragment)

                }else {
                    Toasty.error(
                        requireContext(),
                        getText(R.string.register_failed),
                        Toast.LENGTH_SHORT,
                        true).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
