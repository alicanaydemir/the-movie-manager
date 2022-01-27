package com.kuka.app.tmm.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.squareup.picasso.Picasso

inline fun EditText.onQueryTextChanged(crossinline listener: (String?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            listener.invoke(p0.toString())
        }
    })
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun ImageView.loadImage(url: String?) {
    Picasso.get()
        .load("https://image.tmdb.org/t/p/w500/$url")
        .into(this)
}