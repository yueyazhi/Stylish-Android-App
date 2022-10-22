package com.yazhiyue.stylish.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yazhiyue.stylish.R
import com.yazhiyue.stylish.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        binding.lifecycleOwner = this

        val viewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        binding.viewModel = viewModel

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.refreshStatus.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    binding.layoutRefresh.isRefreshing = it
                }
            }
        )

        binding.recyclerHome.adapter = HomeAdapter()


        return binding.root
    }

}