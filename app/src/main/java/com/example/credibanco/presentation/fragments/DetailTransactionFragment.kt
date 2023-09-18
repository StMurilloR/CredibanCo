package com.example.credibanco.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.credibanco.R
import com.example.credibanco.data.dto.AnnulationVO
import com.example.credibanco.databinding.FragmentDetailTransactionBinding
import com.example.credibanco.presentation.viewModel.TransactionViewModel
import com.example.credibanco.utils.concatString
import com.example.credibanco.utils.convertToDecimal
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.OptionalInject
import java.util.*

@OptionalInject
@AndroidEntryPoint
class DetailTransactionFragment : Fragment() {

    private var _binding: FragmentDetailTransactionBinding? = null
    private val binding get() = _binding!!
    private val transactionViewModel: TransactionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Funcion que inicializa el observador
     */
    private fun initObservers(){
        transactionViewModel.getTransactionsList()
        transactionViewModel.transactions.observe(viewLifecycleOwner) {data ->
            data?.run {
                this.data?.let { list ->
                    val transaction = list.find { transactionSelect ->
                        transactionSelect.id == transactionViewModel.transactionIdSelected
                    }
                    with(binding){
                        transaction?.let { select ->
                            idTransaction.text = concatString("Transacción ", select.id.toString())
                            cardNumber.text = concatString("Card: ", select.card.toString())
                            commerceNumber.text = concatString("Commerce: ", select.commerceCode.toString())
                            terminalNumber.text = concatString("Terminal: ", select.terminalCode.toString())
                            amountNumber.text = concatString("$ ", convertToDecimal(select.amount!!))
                            receiptNumber.text = concatString("Receipt: ", select.receiptId.toString())
                            rrnNumber.text = concatString("Rrn: ", select.rrn.toString())

                            val annulationVO = AnnulationVO(select.receiptId.toString(), select.rrn.toString())
                            annulationButton.setOnClickListener {
                                val concatString = "000123000ABC"
                                val encoder: Base64.Encoder = Base64.getEncoder()
                                val encoderString: String = encoder.encodeToString(concatString.encodeToByteArray())
                                val concatKey = concatString("Basic ", encoderString)
                                transactionViewModel.getAnnulation(concatKey, annulationVO)
                            }
                        }
                    }
                }
            }
        }

        transactionViewModel.annulation.observe(viewLifecycleOwner){ data ->
            data?.run {
                this.data?.let {
                    transactionViewModel.transactions.observe(viewLifecycleOwner) { data ->
                        data?.run {
                            this.data?.let { list ->
                                val transaction = list.find { transactionSelect ->
                                    transactionSelect.id == transactionViewModel.transactionIdSelected
                                }
                                transaction?.let {
                                    it.annulation = true
                                    transactionViewModel.setTransactionUpdate(it)
                                }
                            }
                        }
                        Toast.makeText(context, "Anulación Correcta", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_detailTransactionFragment_to_homeFragment)
                    }
                    error?.let {
                        Toast.makeText(context, "Anulación Incorrecta", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}