package com.example.credibanco.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.credibanco.data.database.entity.TransactionEntity
import com.example.credibanco.databinding.FragmentListTransactionBinding
import com.example.credibanco.presentation.viewModel.TransactionViewModel
import com.example.credibanco.utils.AdapterTransactionList
import com.example.credibanco.utils.TransactionInflater
import com.example.credibanco.utils.concatString
import com.example.credibanco.utils.convertToDecimal
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.OptionalInject

@OptionalInject
@AndroidEntryPoint
class ListTransactionFragment : Fragment() {

    private var _binding: FragmentListTransactionBinding? = null
    private val binding get() = _binding!!
    private val transactionViewModel: TransactionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListTransactionBinding.inflate(inflater, container, false)
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
                this.data?.let {
                    initBenefitAdapter(it)
                    binding.emptyItemsText.visibility = View.GONE
                }
                error?.let {
                    binding.transactionRecycler.visibility = View.GONE
                    binding.emptyItemsText.visibility = View.VISIBLE
                }
            }
        }
    }

    /**
     * Funcion que inicializa el adaptador de los beneficios
     */
    private fun initBenefitAdapter(transactions: List<TransactionEntity>) {
        binding.transactionRecycler.adapter = AdapterTransactionList(
            dataset = transactions,
            itemViewFactory = {
                TransactionInflater(this.requireContext())
            }
        ) { view, data, _ ->
            setData(view as TransactionInflater, data)
        }
        binding.transactionRecycler.layoutManager = GridLayoutManager(this.context, 1)
    }

    /**
     * Asigna los datos a la vista
     */
    private fun setData(transactionItemInflater: TransactionInflater, data: TransactionEntity){
        with((transactionItemInflater).binding) {
            idTransaction.text = concatString("Transacción ", data.id.toString())
            cardNumber.text = concatString("Card: ", data.card.toString())
            commerceNumber.text = concatString("Commerce: ", data.commerceCode.toString())
            terminalNumber.text = concatString("Terminal: ", data.terminalCode.toString())
            amountNumber.text = concatString("$ ", convertToDecimal(data.amount!!))
            if(data.annulation == true){
                approved.text = "ANULADA"
            }else {
                approved.text = "APROBADA"
            }
        }
    }


}