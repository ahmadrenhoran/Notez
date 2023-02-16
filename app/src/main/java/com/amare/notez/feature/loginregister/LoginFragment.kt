package com.amare.notez.feature.loginregister

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amare.notez.R
import com.amare.notez.core.domain.model.Response
import com.amare.notez.core.domain.model.User
import com.amare.notez.databinding.FragmentLoginBinding
import com.amare.notez.feature.homescreen.HomeActivity
import com.amare.notez.util.Utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginRegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()

    }

    private fun setupAction() {
        binding.apply {
            googleLoginBtn.setOnClickListener {
                signInWithGoogle()
            }

            loginButton.setOnClickListener {
                signInWithEmail()
            }

            registerTextView.setOnClickListener {
                val parentFragmentManager = this@LoginFragment.parentFragmentManager
                val mRegisterFragment = RegisterFragment()
                parentFragmentManager.beginTransaction().replace(
                        R.id.login_register_frame,
                        mRegisterFragment,
                        RegisterFragment::class.java.simpleName
                    ).addToBackStack(null).commit()
            }
        }
    }

    private fun signInWithEmail() {
        binding.apply {
            val user = User(email = emailEditText.text.toString())
            viewModel.signInWithEmail(user, passwordEditText.text.toString())
        }
        viewModel.emailResponse.observe(requireActivity()) { value ->
            when (value) {
                is Response.Loading -> {
                    binding.apply {
                        Utils.showLoading(loadingView, loginLayout, true)
                    }
                }
                is Response.Success -> {
                    binding.apply {
                        Utils.showLoading(loadingView, loginLayout, false)
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
                        Utils.showLoading(loadingView, loginLayout, false)
                    }
                    Utils.showToastText(requireContext(), value.e.toString(), Toast.LENGTH_LONG)
                }
                else -> {}
            }
        }
    }

    private fun signInWithGoogle() {
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
                                    Utils.showLoading(loadingView, loginLayout, true)
                                }
                            }
                            is Response.Success -> {
                                binding.apply {
                                    Utils.showLoading(loadingView, loginLayout, false)
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
                                    Utils.showLoading(loadingView, loginLayout, false)
                                }
                                Utils.showToastText(requireContext(), value.e.toString(), Toast.LENGTH_LONG)
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


    companion object {
        private const val TAG = "LoginFragment"
    }

}