package com.yazhiyue.stylish.dialog

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.yazhiyue.stylish.R
import com.yazhiyue.stylish.databinding.DialogMessageBinding

class MessageDialog : DialogFragment() {

    var iconRes: Drawable? = null
    var message: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val messageType = MessageDialogArgs.fromBundle(requireArguments()).messageTypeKey

        val binding = DialogMessageBinding.inflate(inflater, container, false)

        init(binding.root.context, messageType)

        binding.dialog = this

        return binding.root
    }


    private fun init(context: Context, messageType: MessageType) {
        message = context.resources.getString(messageType.message)
        messageType.iconRes?.let {
            iconRes = ContextCompat.getDrawable(context, it)
        }
    }


    enum class MessageType(val message: Int, val iconRes: Int? = null) {
        ADDED_SUCCESS(R.string.add_success, R.drawable.icons_44px_success01),
        ADDED_FAIL(R.string.add_fail)
    }


}