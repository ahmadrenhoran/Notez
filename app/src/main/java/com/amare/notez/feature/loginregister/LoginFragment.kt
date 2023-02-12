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
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.amare.notez.R
import com.amare.notez.core.domain.model.Response
import com.amare.notez.databinding.FragmentLoginBinding
import com.amare.notez.feature.homescreen.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginRegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
                signIn()
            }

            registerTextView.setOnClickListener {
                val parentFragmentManager = this@LoginFragment.parentFragmentManager
                val mRegisterFragment = RegisterFragment()
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.login_register_frame,
                        mRegisterFragment,
                        RegisterFragment::class.java.simpleName
                    )
                    .addToBackStack(null).commit()
            }
        }
    }

    private fun signIn() {
        try {
            val signInIntent = viewModel.signInClient.signInIntent
            launcher.launch(signInIntent)
        } catch (e: Exception) {
            Log.d(TAG, "signIn: " + e.message)
        }
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
                    when (viewModel.googleResponse.value) {
                        is Response.Loading -> Toast.makeText(
                            requireContext(),
                            "Loading",
                            Toast.LENGTH_LONG
                        ).show()
                        is Response.Success -> {
                            Toast.makeText(requireContext(), viewModel.auth.currentUser?.email, Toast.LENGTH_LONG).show()
                            startActivity(
                                Intent(
                                    requireContext(),
                                    HomeActivity::class.java
                                )
                            )
                        }
                        is Response.Error -> Toast.makeText(
                            requireContext(),
                            "Error",
                            Toast.LENGTH_LONG
                        ).show()

                        else -> {

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
        private const val RC_SIGN_IN = 1001
    }

}