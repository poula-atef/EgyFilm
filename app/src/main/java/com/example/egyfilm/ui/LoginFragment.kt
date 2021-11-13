package com.example.egyfilm.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.egyfilm.R
import com.example.egyfilm.databinding.FragmentLoginBinding
import com.example.egyfilm.pojo.classes.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@SuppressLint("ClickableViewAccessibility")
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var login = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentLoginBinding.inflate(inflater, container, false)


        loginButton()

        SwitchButton()

        loginAsAGuest()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().uid != null){
            Navigation.findNavController(requireView()).navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            )
        }
    }

    private fun loginButton() {
        binding.logFab.setOnClickListener {
            binding.loading.alpha = 1f
            val auth = FirebaseAuth.getInstance()
            if (login) {
                if (binding.email.text.isNotEmpty() && binding.password.text.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(
                        binding.email.text.toString(),
                        binding.password.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Navigation.findNavController(binding.root).navigate(
                                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                            )
                        } else {
                            val message: CharSequence =
                                it.exception?.message!!
                            Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
                            binding.loading.alpha = 0f
                        }
                    }
                } else {
                    val message: CharSequence =
                        context?.getString(R.string.all_fields_are_required).toString()
                    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
                    binding.loading.alpha = 0f
                }
            } else {
                if (binding.email.text.isNotEmpty() && binding.password.text.isNotEmpty()
                    && binding.repassword.text.isNotEmpty() && binding.username.text.isNotEmpty()
                ) {
                    if (binding.password.text.toString().equals(binding.repassword.text.toString())) {
                        auth.createUserWithEmailAndPassword(
                            binding.email.text.toString(),
                            binding.password.text.toString()
                        ).addOnCompleteListener {
                            if (it.isSuccessful) {
                                FirebaseDatabase.getInstance().getReference("users")
                                    .child(auth.uid!!)
                                    .setValue(
                                        User(
                                            binding.username.text.toString(),
                                            null
                                        )
                                    )
                                    .addOnCompleteListener {
                                        Navigation.findNavController(binding.root).navigate(
                                            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                                        )
                                    }
                            } else {
                                val message: CharSequence =
                                    it.exception?.message!!
                                Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
                                binding.loading.alpha = 0f
                            }
                        }
                    } else {
                        val message: CharSequence =
                            context?.getString(R.string.make_sure_of_password).toString()
                        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
                        binding.loading.alpha = 0f
                    }
                } else {
                    val message: CharSequence =
                        context?.getString(R.string.all_fields_are_required).toString()
                    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
                    binding.loading.alpha = 0f
                }
            }
        }
    }

    private fun loginAsAGuest() {
        binding.guestLogin.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            )
        }
    }

    private fun SwitchButton() {
        binding.switchTv.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (!login) {
                    binding.textView5.animate().alpha(0f).setDuration(300).start()
                    binding.switchTv.animate().alpha(0f).setDuration(300).start()


                    Handler().postDelayed(Runnable {
                        activity?.runOnUiThread {
                            binding.textView5.text = getString(R.string.login)
                            binding.switchTv.text = getString(R.string.dont_have_account)
                            binding.textView5.animate().alpha(1f).setDuration(300)
                            binding.switchTv.animate().alpha(1f).setDuration(300)
                        }
                    }, 301)

                    login = true
                    Log.d("TAG", "change to login")
                } else {
                    binding.textView5.animate().alpha(0f).setDuration(300).start()
                    binding.switchTv.animate().alpha(0f).setDuration(300).start()


                    Handler().postDelayed(Runnable {
                        activity?.runOnUiThread {
                            binding.textView5.text = getString(R.string.signup)
                            binding.switchTv.text = getString(R.string.already_have_account)
                            binding.textView5.animate().alpha(1f).setDuration(300)
                            binding.switchTv.animate().alpha(1f).setDuration(300)
                        }
                    }, 301)

                    login = false
                    Log.d("TAG", "change to signup")
                }
            }
            false
        }
    }


}