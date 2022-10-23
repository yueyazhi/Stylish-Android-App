package com.yazhiyue.stylish.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.yazhiyue.stylish.databinding.FragmentCatalogBinding


class CatalogFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCatalogBinding.inflate(inflater, container, false)

        //set tab layout
        val tabLayout = binding.tabLayout

        //set view pager
        val viewPager = binding.viewpager

        val adapter = this.activity?.let { CatalogPagerAdapter(it) }

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tabNames = listOf("女裝", "男裝", "配件")
            tab.text = tabNames[position]
        }.attach()


        return binding.root

    }

}