package com.applemandi.android.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.applemandi.android.R
import com.applemandi.android.data.model.SellOrder
import com.applemandi.android.data.model.Village
import com.applemandi.android.databinding.FragmentSellBinding
import com.applemandi.android.view.util.showToast
import com.applemandi.android.viewModel.SellViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SellFragment : Fragment() {

    private lateinit var bindings: FragmentSellBinding
    private val args: SellFragmentArgs by navArgs()
    private val sellViewModel: SellViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bindings = DataBindingUtil.inflate(
            LayoutInflater.from(inflater.context),
            R.layout.fragment_sell,
            container,
            false
        )
        return bindings.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sellViewModel
            seller = args.seller
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleViewActions()
        handleObservers()

        sellViewModel.loadVillages()
        sellViewModel.setSellerData(args.seller)
    }

    private fun handleViewActions() {
        bindings.btnSell.setOnClickListener {
            val sellOrder = SellOrder(
                sellerName = args.seller.name!!,
                grossWeight = sellViewModel.grossWeight.value,
                grossPrice = sellViewModel.grossPrice.value
            )
            findNavController().navigate(
                SellFragmentDirections.actionSellFragmentToConfirmationFragment(
                    sellOrder
                )
            )
        }

        bindings.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val village = bindings.spinner.adapter.getItem(position) as Village
                sellViewModel.updateVillageRate(village.rate)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun handleObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sellViewModel.errorMessage.collect {
                    requireContext().showToast(R.string.errorMessage, Toast.LENGTH_LONG)
                }
            }
        }
    }


}