package com.yazhiyue.stylish.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.yazhiyue.stylish.databinding.DialogAddSuccessfullyBinding

class AddSuccessfullyDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DialogAddSuccessfullyBinding.inflate(inflater)

        return binding.root
    }
}