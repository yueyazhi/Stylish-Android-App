package com.yazhiyue.stylish.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.yazhiyue.stylish.databinding.FragmentDetailBinding
import com.yazhiyue.stylish.factory.DetailViewModelFactory

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        val product = DetailFragmentArgs.fromBundle(requireArguments()).productKey

        val viewModelFactory = DetailViewModelFactory(product)

        val viewModel = ViewModelProvider(
            this, viewModelFactory
        )[DetailViewModel::class.java]

        binding.viewModel = viewModel

        //  Set gallery recycler view adapter
        val galleryAdapter = DetailGalleryAdapter()
        binding.recyclerDetailGallery.adapter = galleryAdapter

        // Set gallery to snap in the middle when scrolling
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerDetailGallery)

        //  Set color recycler view adapter
        val colorsAdapter = DetailColorAdapter()
        binding.recyclerDetailColor.adapter = colorsAdapter

        viewModel.product.observe(viewLifecycleOwner, Observer {
            it?.let {
                galleryAdapter.submitImages(it.images)
                colorsAdapter.submitList(it.colors)
            }
        })

        // Handle leave detail
        viewModel.leaveDetail.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    if (it) findNavController().navigateUp()
                }
            }
        )

        // Handle navigation to Add2cart
        viewModel.navigateToAdd2cart.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(
                        DetailFragmentDirections.actionDetailFragmentToAdd2cartDialog(
                            it
                        )
                    )
                    viewModel.onAdd2cartNavigated()
                }
            }
        )


        return binding.root
    }
}