package com.pinkcloud.simpleposts.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.pinkcloud.simpleposts.R

@BindingAdapter("name", "email")
fun setCommentTitle(textView: TextView, name: String, email: String) {

    textView.text = String.format(
        textView.resources.getString(R.string.comment_user),
        name,
        email
    )
}