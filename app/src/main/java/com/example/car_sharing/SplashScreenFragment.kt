package com.example.car_sharing

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    @Inject
    lateinit var supabaseClient: SupabaseClient

    private fun isUserLoggedIn(): Boolean {
        if (::supabaseClient.isInitialized) {
            val session = supabaseClient.auth.currentSessionOrNull()
            return session != null
        }
        Log.e("SplashScreenFragment", "SupabaseClient is not initialized.")
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            if(!isOnline(requireContext())){
                findNavController().navigate(R.id.action_splashScreenFragment_to_noConnectionFragment)
            }
            else if(isUserLoggedIn()){
                findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
            }
            else if(onBoardingFinished()){
                findNavController().navigate(R.id.action_splashScreenFragment_to_previewRegFragment)
            }
            else{
                findNavController().navigate(R.id.action_splashScreenFragment_to_viewPagerFragment)
            }
        },2000)
    }
    private fun onBoardingFinished(): Boolean {
        val sharedPref=requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished",false)
    }
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }


}