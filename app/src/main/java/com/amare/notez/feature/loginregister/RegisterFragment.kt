package com.amare.notez.feature.loginregister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amare.notez.R
import com.amare.notez.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun setupAction() {
        binding.apply {
            registerButton.setOnClickListener {

            }

            loginTextView.setOnClickListener {
                val parentFragmentManager = this@RegisterFragment.parentFragmentManager
                val mLoginFragment = LoginFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.login_register_frame, mLoginFragment, LoginFragment::class.java.simpleName)
                    .commit()

            }
        }
    }

}