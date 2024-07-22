package com.example.notesapptask.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.notesapptask.R
import com.example.notesapptask.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private val RC_SIGN_IN = 9001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        val window = requireActivity().window
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.isAppearanceLightStatusBars = true // optional for light status bar text
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.blue)
        // or use a color resource:

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("272644784825-1mhmaqnnrp5jv8cjur0q1b866ravbv81.apps.googleusercontent.com") // Your server client ID from Firebase Console
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)

        // Set up the Google Sign-In button
        binding.signInButton.setOnClickListener {
            signIn()
        }

        return binding.root
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {


                val account = task.getResult(ApiException::class.java)
                Log.e("GoogleSignIn", account.email.toString())
                        requireContext().setSignInStatus(true)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frag, HomeFragment())
                    ?.commit()

            } catch (e: ApiException) {
                Log.w("GoogleSignIn", "Google sign in failed", e)
            }
        }
    }
    fun Context.setSignInStatus(isSignedIn: Boolean) {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("is_signed_in", isSignedIn).apply()
    }





}

