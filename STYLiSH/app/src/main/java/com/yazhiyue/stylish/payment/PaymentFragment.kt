package com.yazhiyue.stylish.payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.yazhiyue.stylish.R
import com.yazhiyue.stylish.data.source.local.StylishDatabase
import com.yazhiyue.stylish.databinding.FragmentPaymentBinding
import com.yazhiyue.stylish.dialog.MessageDialog
import com.yazhiyue.stylish.factory.PaymentViewModelFactory

class PaymentFragment : Fragment() {
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentPaymentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_payment, container, false
        )

        binding.lifecycleOwner = this

        val application = requireNotNull(activity).application

        val databaseDao = StylishDatabase.getInstance(application).stylishDatabaseDao

        val viewModelFactory = PaymentViewModelFactory(databaseDao, application)

        val viewModel = ViewModelProvider(this, viewModelFactory)[PaymentViewModel::class.java]

        val adapter = PaymentAdapter(viewModel)

        binding.recyclerPayment.adapter = adapter
        binding.recyclerPayment.adapter?.notifyDataSetChanged()


        viewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.recyclerPayment.adapter?.notifyDataSetChanged()
            }
        })

        viewModel.invalidCheckout.observe(
            viewLifecycleOwner,
            Observer {
                if (it == null) {
                    viewModel.navigateToCheckoutSuccess()
                }
            }
        )

        viewModel.navigateToCheckoutSuccess.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(
                        PaymentFragmentDirections.actionPaymentFragmentToMessageDialog(
                            MessageDialog.MessageType.CHECKOUT_SUCCESS
                        )
                    )
                    viewModel.onCheckoutSuccessNavigated()
                }
            }
        )



        return binding.root
    }


}