package com.yazhiyue.stylish.add2cart

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yazhiyue.stylish.R
import com.yazhiyue.stylish.data.source.local.StylishDatabase
import com.yazhiyue.stylish.databinding.DialogAdd2cartBinding
import com.yazhiyue.stylish.dialog.MessageDialog
import com.yazhiyue.stylish.factory.Add2cartViewModelFactory

class Add2cartDialog : BottomSheetDialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DialogAdd2cartBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        val application = requireNotNull(activity).application

        val product = Add2cartDialogArgs.fromBundle(requireArguments()).productKey

        val databaseDao = StylishDatabase.getInstance(application).stylishDatabaseDao

        val viewModelFactory = Add2cartViewModelFactory(product, databaseDao)

        val viewModel = ViewModelProvider(
            this, viewModelFactory
        )[Add2cartViewModel::class.java]

        binding.viewModel = viewModel

        val sizeAdapter = Add2cartSizeAdapter(Add2cartSizeAdapter.OnClickListener {
            viewModel.selectSize(it)
        })

        binding.recyclerSizeSelector.adapter = sizeAdapter

        val colorAdapter = Add2cartColorAdapter(Add2cartColorAdapter.OnClickListener {
            viewModel.selectColor(it)
            sizeAdapter.submitList(viewModel.variantsBySelectedColor.value)
            sizeAdapter.selectedPos = -1
        })

        binding.recyclerColorSelector.adapter = colorAdapter


        viewModel.product.observe(viewLifecycleOwner, Observer {
            it?.let {
                colorAdapter.submitList(it.colors)
            }
        })

        // Handle navigation to Added Success
        viewModel.navigateToAddedSuccess.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    this.findNavController()
                        .navigate(
                            Add2cartDialogDirections.actionAdd2cartDialogToMessageDialog(
                                MessageDialog.MessageType.ADDED_SUCCESS
                            )
                        )
                    viewModel.onAddedSuccessNavigated()
                }
            }
        )

        // Handle navigation to Added Fail
        viewModel.navigateToAddedFail.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    this.findNavController()
                        .navigate(
                            Add2cartDialogDirections.actionAdd2cartDialogToMessageDialog(
                                MessageDialog.MessageType.ADDED_FAIL
                            )
                        )
                    viewModel.onAddedFailNavigated()
                }
            }
        )


        return binding.root
    }
}