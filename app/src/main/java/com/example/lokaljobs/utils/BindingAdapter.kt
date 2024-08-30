package com.example.lokaljobs.utils

import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.lokaljobs.R
import com.example.lokaljobs.network.model.ContentV3


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(it)
            .apply(RequestOptions().error(R.drawable.broken_img))
            .into(imgView)
    } ?: run {
        imgView.setImageResource(R.drawable.broken_img)
    }
}

@BindingAdapter("whatsappChatLink")
fun bindWhatsAppChatLink(button: Button, chatLink: String?) {
    button.setOnClickListener {
        chatLink?.let {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(chatLink)
            button.context.startActivity(intent)
        }
    }
}

@BindingAdapter("dialerLink")
fun bindDialerLink(button: Button, phoneNumber: String?) {
    button.setOnClickListener {
        phoneNumber?.let {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(phoneNumber)
            button.context.startActivity(intent)
        }
    }
}

@BindingAdapter("gender")
fun bindGender(view: TextView, contentV3: ContentV3?) {
    val gender = contentV3?.V3?.find { it.field_key == "Gender" }?.field_value
    view.text = gender ?: "Not specified"
}

@BindingAdapter("shiftTiming")
fun bindShiftTiming(view: TextView, contentV3: ContentV3?) {
    val shiftTiming = contentV3?.V3?.find { it.field_key == "Shift timing" }?.field_value
    view.text = shiftTiming ?: "Not specified"
}