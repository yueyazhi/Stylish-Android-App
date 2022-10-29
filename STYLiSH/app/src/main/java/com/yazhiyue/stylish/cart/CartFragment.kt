package com.yazhiyue.stylish.cart

import android.os.Bundle
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
import com.yazhiyue.stylish.databinding.FragmentCartBinding
import com.yazhiyue.stylish.factory.ViewModelFactory

class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCartBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cart, container, false
        )

        binding.lifecycleOwner = this

        val application = requireNotNull(activity).application

        val databaseDao = StylishDatabase.getInstance(application).stylishDatabaseDao

        val viewModelFactory = ViewModelFactory(databaseDao)

        val viewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[CartViewModel::class.java]

        binding.viewModel = viewModel

        val cartAdapter = CartAdapter(viewModel)

        binding.recyclerCart.adapter = cartAdapter


        viewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                cartAdapter.submitList(it)
                binding.recyclerCart.adapter?.notifyDataSetChanged()
            }
        })

        viewModel.navigateToPayment.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(CartFragmentDirections.actionCartFragmentToPaymentFragment())
                    viewModel.onPaymentNavigated()
                }
            }
        )


        return binding.root
    }

}