package com.example.paradox.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.paradox.R
import com.example.paradox.databinding.FragmentAboutBinding
import com.example.paradox.databinding.FragmentSeeProfProfileBBinding
import com.example.paradox.ui.profiles.EditProfProfile

class About : Fragment() {
    private lateinit var binding: FragmentAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAboutBinding.bind(view)
        binding.tvTermsAndCond.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.freeprivacypolicy.com/live/2c6b0958-129b-439c-85a8-b7fcee37d5ba"))
            startActivity(browserIntent)
        }

        binding.tvPrivacyPolicy.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.freeprivacypolicy.com/live/2c2a1df4-19b1-4855-af6b-49e08d470236"))
            startActivity(browserIntent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            About().apply {
                arguments = Bundle().apply {
                }
            }
    }
}