package com.yazhiyue.stylish.catalog.item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yazhiyue.stylish.catalog.CatalogTypeFilter
import com.yazhiyue.stylish.databinding.FragmentCatalogItemBinding
import com.yazhiyue.stylish.factory.CatalogItemViewModelFactory

class CatalogItemFragment(private val catalogType: CatalogTypeFilter) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCatalogItemBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        Log.i("catalogType", "$catalogType")

        val factory = CatalogItemViewModelFactory(catalogType)

        val viewModel =
            ViewModelProvider(this, factory)[CatalogItemViewModel::class.java]

        val adapter = CatalogItemAdapter()
        binding.recyclerCatalogItem.adapter = adapter

        viewModel.productList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })


        return binding.root
    }
}