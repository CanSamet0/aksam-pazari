package com.aksampazari.presentation.login.fragments

import android.content.Intent
import android.graphics.Color
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
import com.aksampazari.presentation.MainActivity
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
    ): View? {
         _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spannable = SpannableString("Zaten kayıtlıysanız buraya tıklayın.")
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
        val registerPassword = binding.etRegisterPassword
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

        auth = Firebase.auth
        binding.btnRegister.setOnClickListener {
            val name = binding.etRegisterName.text.toString()
            val surname = binding.etRegisterSurname.text.toString()
            val email = binding.etRegisterEmail.text.toString()
            val password = binding.etRegisterPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(
                    requireContext(),
                    "E-mail veya şifre boş bırakılamaz!",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            registerUser(email, password, name ,surname)
        }
    }

    private fun registerUser(email: String, password: String, name: String, surname:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Toasty.success(
                        requireContext(),
                        "Kayıt başarılı.",
                        Toast.LENGTH_SHORT,
                        true).show()
                    val intent = Intent(requireContext(), MainActivity::class.java).apply {
                        putExtra("USER_EMAIL", email)
                        putExtra("USER_PASSWORD", password)
                        putExtra("USER_NAME", name)
                        putExtra("USER_SURNAME", surname)
                    }
                    startActivity(intent)
                    requireActivity().finish()
                }else {
                    Toasty.error(
                        requireContext(),
                        "Kayıt başarısız",
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