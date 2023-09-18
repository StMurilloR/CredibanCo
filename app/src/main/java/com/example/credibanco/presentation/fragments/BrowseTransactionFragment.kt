package com.example.credibanco.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.credibanco.R
import com.example.credibanco.data.database.entity.TransactionEntity
import com.example.credibanco.databinding.FragmentBrowseTransactionBinding
import com.example.credibanco.presentation.Adapter.AdapterTransactionList
import com.example.credibanco.presentation.viewModel.TransactionViewModel
import com.example.credibanco.utils.TransactionInflater
import com.example.credibanco.utils.concatString
import com.example.credibanco.utils.convertToDecimal
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.OptionalInject

@OptionalInject
@AndroidEntryPoint
class BrowseTransactionFragment : Fragment() {

    private var _binding: FragmentBrowseTransactionBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterTransactionList<TransactionEntity>
    private val transactionViewModel: TransactionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrowseTransactionBinding.inflate(inflater, container, false)
        return  binding.root
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
                    val transactionApproved = list.filter {
                        it.annulation == false
                    }
                    initBenefitAdapter(transactionApproved)
                    binding.filterTransaction.addTextChangedListener { filter ->
                        val listFilterTransaction =
                            transactionApproved.filter { listTransaction ->
                                listTransaction.receiptId!!.lowercase().contains(filter.toString())
                            }
                        adapter.updateListTransactions(listFilterTransaction)
                        if (listFilterTransaction.isEmpty()) {
                            binding.emptyItemsTextFilter.visibility = View.VISIBLE
                            binding.transactionRecyclerFilter.visibility = View.GONE
                        } else{
                            binding.emptyItemsTextFilter.visibility = View.GONE
                            binding.transactionRecyclerFilter.visibility = View.VISIBLE
                        }
                    }
                }
                error?.let {
                    binding.transactionRecyclerFilter.visibility = View.GONE
                    binding.emptyItemsTextFilter.visibility = View.VISIBLE
                    Toast.makeText(context, "no hay datos ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Funcion que inicializa el adaptador de los beneficios
     */
    private fun initBenefitAdapter(transactions: List<TransactionEntity>) {
        adapter = AdapterTransactionList(
            dataset = transactions,
            itemViewFactory = {
                TransactionInflater(this.requireContext())
            }
        ) { view, data, _ ->
            setData(view as TransactionInflater, data)
        }
        binding.transactionRecyclerFilter.adapter = adapter
        binding.transactionRecyclerFilter.layoutManager = GridLayoutManager(this.context, 1)
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

            transactionItemInflater.setOnClickListener {
                transactionViewModel.transactionIdSelected = data.id
                //Se asegura que realmente se haya asignado un beneficio antes de lanzar el fragmento que muestra el detalle
                transactionViewModel.transactionIdSelected?.let {
                    findNavController().navigate(R.id.action_browseTransactionFragment_to_detailTransactionFragment)
                }
            }
        }
    }
}