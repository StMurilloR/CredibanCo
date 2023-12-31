package com.example.credibanco.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.credibanco.R
import com.example.credibanco.data.dto.AuthorizationVO
import com.example.credibanco.data.dto.TransactionVO
import com.example.credibanco.databinding.FragmentAuthorizationBinding
import com.example.credibanco.presentation.viewModel.TransactionViewModel
import com.example.credibanco.utils.concatString
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.Base64

@AndroidEntryPoint
class AuthorizationFragment : Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    private val transactionViewModel: TransactionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonLayer()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initButtonLayer(){
        with(binding){
            approvedButton.setOnClickListener {
                if (commerceCodeInput.text.isNotEmpty() && terminalCodeInput.text.isNotEmpty() &&
                        amountInput.text.isNotEmpty() && cardInput.text.isNotEmpty()){
                    val concatString = "000123000ABC"
                    val encoder: Base64.Encoder = Base64.getEncoder()
                    val encoderString: String = encoder.encodeToString(concatString.encodeToByteArray())
                    val concatKey = concatString("Basic ", encoderString)
                    val myUuid = UUID.randomUUID()
                    val myUuidAsString = myUuid.toString()
                    val amountNumber = amountInput.text.toString()
                    val newAmount = String.format(amountNumber).replace(".","")
                    val authorizationVO = AuthorizationVO(id = myUuidAsString, commerceCode = commerceCodeInput.text.toString(),
                        terminalCode = terminalCodeInput.text.toString() ,amount = newAmount, card = cardInput.text.toString())
                    transactionViewModel.getAuthorization(concatKey, authorizationVO)
                    transactionViewModel.setTransactionRoom( TransactionVO(idTransaction = myUuidAsString, commerceCode = commerceCodeInput.text.toString(),
                        terminalCode = terminalCodeInput.text.toString() ,amount = newAmount, card = cardInput.text.toString(), authorization = true,
                        annulation = false, receiptId = null, rrn = null))
                }else{
                    Toast.makeText(context, "Falta información en el Formulario", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Funcion que inicializa el observador
     */
    private fun initObservers(){
        transactionViewModel.authorization.observe(viewLifecycleOwner) { data ->
            data?.run {
                this.data?.let {
                    if (transactionViewModel.transactionVO.authorization){
                        transactionViewModel.transactionVO.receiptId = it.receiptId
                        transactionViewModel.transactionVO.rrn = it.rrn
                        val transactionRoom = transactionViewModel.transactionVO
                        transactionViewModel.setTransactionInsert(transactionRoom)
                        Toast.makeText(context, "Transacción Autorizada", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
                        transactionViewModel.transactionVO = TransactionVO(idTransaction = "", commerceCode = "",
                            terminalCode = "" ,amount = "", card = "", authorization = false,
                            annulation = false, receiptId = null, rrn = null)
                    }
                }
                error?.let {
                    Toast.makeText(context, "Transacción No Autorizada - Revise los datos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}