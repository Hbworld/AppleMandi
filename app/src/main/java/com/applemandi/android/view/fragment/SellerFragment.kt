package com.applemandi.android.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.applemandi.android.R
import com.applemandi.android.databinding.FragmentSellerBinding
import com.applemandi.android.view.util.showToast
import com.applemandi.android.viewModel.SellerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SellerFragment : Fragment() {

    private lateinit var bindings: FragmentSellerBinding
    private val sellerViewModel: SellerViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bindings = DataBindingUtil.inflate(
            LayoutInflater.from(inflater.context),
            R.layout.fragment_seller,
            container,
            false
        )
        return bindings.apply {
            viewModel = sellerViewModel
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleViewActions()
        handleObservers()
    }

    private fun handleViewActions() {
        bindings.btnNext.setOnClickListener {
            findNavController().navigate(
                SellerFragmentDirections.actionSellerFragmentToSellFragment(
                    sellerViewModel.seller.value
                )
            )
        }
    }

    private fun handleObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sellerViewModel.errorMessage.collect {
                    requireContext().showToast(R.string.errorMessage, Toast.LENGTH_LONG)
                }
            }
        }
    }
}