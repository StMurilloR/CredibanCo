package com.example.credibanco.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.RadioGroup
import com.example.credibanco.databinding.TransactionListAdapterLayoutBinding

/**
 * Genera una instancia de tipo binding para el view que se le pase
 */
class TransactionInflater constructor(
    context: Context
): RadioGroup(context){
    val binding: TransactionListAdapterLayoutBinding = TransactionListAdapterLayoutBinding.inflate(
        LayoutInflater.from(context), this, true
    )
}