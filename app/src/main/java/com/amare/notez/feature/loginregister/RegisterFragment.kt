package com.amare.notez.feature.loginregister

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.amare.notez.R
import com.amare.notez.core.domain.model.Response
import com.amare.notez.core.domain.model.User
import com.amare.notez.databinding.FragmentRegisterBinding
import com.amare.notez.feature.homescreen.HomeActivity
import com.amare.notez.util.Utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginRegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        formValidation()

    }

    private fun setupAction() {
        binding.apply {
            googleLoginBtn.setOnClickListener {
                signUpWithGoogle()
            }
            registerButton.setOnClickListener {
                signUpWithEmail()
            }

            loginTextView.setOnClickListener {
                val parentFragmentManager = this@RegisterFragment.parentFragmentManager
                val mLoginFragment = LoginFragment()
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.login_register_frame,
                        mLoginFragment,
                        LoginFragment::class.java.simpleName
                    )
                    .commit()

            }
        }
    }

    private fun signUpWithEmail() {
        binding.apply {
            val user = User(name = nameEditText.text.toString(), email = emailEditText.text.toString())
            viewModel.signUpWithEmail(user, passwordEditText.text.toString())
        }
        viewModel.emailResponse.observe(requireActivity()) { value ->
            when (value) {
                is Response.Loading -> {
                    binding.apply {
                        Utils.showLoading(loadingView, registerLayout, true)
                    }
                }
                is Response.Success -> {
                    binding.apply {
                        Utils.showLoading(loadingView, registerLayout, false)
                    }
                    startActivity(
                        Intent(
                            requireContext(), HomeActivity::class.java
                        )
                    )
                    activity?.finish()
                }
                is Response.Error -> {
                    binding.apply {
                        Utils.showLoading(loadingView, registerLayout, false)
                    }
                    Utils.showToastText(requireContext(), value.e.toString(), Toast.LENGTH_LONG)
                }
                else -> {}
            }
        }

    }

    private fun signUpWithGoogle() {
        val signInIntent = viewModel.signInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val intent = result.data

            if (result.resultCode == Activity.RESULT_OK) {

                try {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
                    // Google Sign In was successful, authenticate with Firebase
                    val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    viewModel.signInWithGoogle(credential)
                    viewModel.googleResponse.observe(requireActivity()) { value ->
                        when (value) {
                            is Response.Loading -> {
                                binding.apply {
                                    Utils.showLoading(loadingView, registerLayout, true)
                                }
                            }
                            is Response.Success -> {
                                binding.apply {
                                    Utils.showLoading(loadingView, registerLayout, false)
                                }
                                startActivity(
                                    Intent(
                                        requireContext(), HomeActivity::class.java
                                    )
                                )
                                activity?.finish()
                            }
                            is Response.Error -> {
                                binding.apply {
                                    Utils.showLoading(loadingView, registerLayout, false)
                                }
                                Utils.showToastText(requireContext(), "Error", Toast.LENGTH_LONG)
                            }
                            else -> {}
                        }
                    }
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e)
                }
            }
        }

    private fun formValidation() {
        var isNameValid = false
        var isEmailValid = false
        var isPasswordValid = false
        viewModel.isFormValid.observe(requireActivity()) {
            binding.registerButton.isEnabled = it
        }

        binding.apply {
            nameEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                    viewModel.setUserName(p0.toString())
                    if (p0.length < 8) {
                        nameEditText.error = "Nama tidak boleh kosong dan setidaknya terdiri dari 8 karakter"
                        isNameValid = false
                    } else {
                        nameEditText.error = null
                        isNameValid = true
                    }
                    viewModel.setFormValid(isNameValid&&isEmailValid&&isPasswordValid)
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            emailEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                    viewModel.setEmailUser(p0.toString())
                    if (!Utils.isEmailValid(p0.toString())) {
                        emailEditText.error = "Email tidak valid"
                        isEmailValid = false
                    } else {
                        nameEditText.error = null
                        isEmailValid = true
                    }
                    viewModel.setFormValid(isNameValid&&isEmailValid&&isPasswordValid)
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            passwordEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.setPasswordUser(p0.toString())
                    if (!Utils.isPasswordValid(p0.toString())) {
                        passwordEditText.error = "Password terdiri setidaknya 8 karakter dengan minimal 1 huruf besar dan 1 angka"
                        isPasswordValid = false
                    } else {
                        passwordEditText.error = null
                        isPasswordValid = true
                    }
                    viewModel.setFormValid(isNameValid&&isEmailValid&&isPasswordValid)
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
        }

    }

    companion object {
        private const val TAG = "RegisterFragment"
    }

}