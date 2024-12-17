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
import com.aksampazari.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.dmoral.toasty.Toasty

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding ?= null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spannable = SpannableString(getString(R.string.not_yet_register))
        spannable.setSpan(
            ForegroundColorSpan(Color.GREEN),
            25,
            31,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvGoRegister.text = spannable

        binding.tvGoRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        val eyeClose = binding.ibEyeClose
        val eyeOpen = binding.ibEyeOpen
        val loginPassword = binding.etLoginPassword

        eyeClose.setOnClickListener {
            eyeClose.visibility = View.INVISIBLE
            eyeOpen.visibility = View.VISIBLE
            loginPassword.transformationMethod = null
            loginPassword.setSelection(loginPassword.text?.length ?: 0)
        }

        eyeOpen.setOnClickListener {
            eyeOpen.visibility = View.INVISIBLE
            eyeClose.visibility = View.VISIBLE
            loginPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            loginPassword.setSelection(loginPassword.text?.length ?: 0)
        }
        auth = Firebase.auth
        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString().trim()
            val password = binding.etLoginPassword.text.toString().trim()

            if(!isItConnected()){
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
                    true).show()
                return@setOnClickListener
            }
            loginUser(email, password)
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

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val sharedPreferences = requireActivity().
                    getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

                    Toasty.success(
                        requireContext(),
                        getText(R.string.login_successful),
                        Toast.LENGTH_SHORT,
                        true).show()

                    findNavController().navigate(R.id.action_loginFragment_to_bazaarFragment)

                } else {
                    Toasty.error(
                        requireContext(),
                        getText(R.string.email_password_error),
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
