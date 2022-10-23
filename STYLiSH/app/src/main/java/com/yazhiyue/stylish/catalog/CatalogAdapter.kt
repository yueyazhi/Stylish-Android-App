package com.yazhiyue.stylish.catalog

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yazhiyue.stylish.catalog.item.CatalogItemFragment


class CatalogPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return CatalogTypeFilter.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return CatalogItemFragment(CatalogTypeFilter.values()[position])
    }

}

