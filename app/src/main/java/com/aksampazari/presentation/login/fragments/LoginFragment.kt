package com.aksampazari.presentation.login.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.text.SpannableString
import android.text.Spanned
import android.text.method.PasswordTransformationMethod
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.aksampazari.R
import com.aksampazari.databinding.FragmentLoginBinding
import com.aksampazari.presentation.MainActivity
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
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spannable = SpannableString("Henüz kayıtlı değilseniz buraya tıklayın.")
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
        val loginEmail = binding.etLoginEmail

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
            Log.i("INFO", "$email $password")
            if (email.isEmpty() || password.isEmpty()){
                Toasty.warning(
                    requireContext(),
                    "Emial veya şifreniz boş olamaz!",
                    Toast.LENGTH_SHORT,
                    true).show()
                return@setOnClickListener
            }
            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toasty.success(
                        requireContext(),
                        "Giriş başarılı.",
                        Toast.LENGTH_SHORT,
                        true).show()

                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    Toasty.error(
                        requireContext(),
                        "Emial veya şifreniz hatalı!",
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