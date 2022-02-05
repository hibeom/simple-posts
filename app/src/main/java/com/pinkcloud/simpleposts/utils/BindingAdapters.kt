package com.pinkcloud.simpleposts.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("name", "email")
fun setCommentTitle(textView: TextView, name: String, email: String) {
    textView.text = "$name \u2022 $email"
}