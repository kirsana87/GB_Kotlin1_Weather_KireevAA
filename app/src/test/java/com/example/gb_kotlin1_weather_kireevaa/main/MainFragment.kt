package com.example.gb_kotlin1_weather_kireevaa.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gb_kotlin1_weather_kireevaa.R


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}