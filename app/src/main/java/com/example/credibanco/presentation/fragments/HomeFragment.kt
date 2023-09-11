package com.example.credibanco.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.credibanco.R
import com.example.credibanco.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.OptionalInject

@OptionalInject
@AndroidEntryPoint
class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initButtonListeners(){
        with(binding){
            authorizationButton.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_authorizationFragment)
            }
            browseTransactionsButton.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_browseTransactionFragment)
            }
            listTransactionsButton.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_listTransactionFragment)
            }
        }
    }
}



