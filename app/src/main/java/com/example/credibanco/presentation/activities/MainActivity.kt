package com.example.credibanco.presentation.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.credibanco.databinding.CredibancoLandingHomeBinding
import com.example.credibanco.presentation.viewModel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: CredibancoLandingHomeBinding
    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CredibancoLandingHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}