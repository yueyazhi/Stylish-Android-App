package com.yazhiyue.stylish.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.yazhiyue.stylish.R
import com.yazhiyue.stylish.data.HomeItem
import com.yazhiyue.stylish.data.Product
import com.yazhiyue.stylish.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        val adapter = HomeAdapter()

        val dataList = mutableListOf<HomeItem>()

        val item1 = HomeItem.Title("2018 冬裝搶先看")

        dataList.add(item1)

        val item2 = HomeItem.FullProduct(Product(1, "厚實毛呢格子外套",
            "保暖又不厚重，輕柔質感新美學", R.drawable.image_placeholder))

        dataList.add(item2)

        val item3 = HomeItem.CollageProduct(
            Product(2, "羊毛粗織長版毛衣",
            "保暖又不厚重，輕柔質感新美學", R.drawable.image_placeholder, R.drawable.image_placeholder,
            R.drawable.image_placeholder, R.drawable.image_placeholder)
        )

        dataList.add(item3)


        binding.recyclerHome.adapter = adapter

        adapter.submitList(dataList)

        return binding.root
    }

}