package com.applemandi.android.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.applemandi.android.R
import com.applemandi.android.databinding.FragmentConfirmationBinding


class ConfirmationFragment : Fragment() {

    private lateinit var binding: FragmentConfirmationBinding

    private val args: ConfirmationFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(inflater.context),
            R.layout.fragment_confirmation,
            container,
            false
        )
        return binding.apply {
            sellOrder = args.sellOrder
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSellMore.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}