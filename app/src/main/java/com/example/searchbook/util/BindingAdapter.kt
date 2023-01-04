package com.example.searchbook.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("bookAuthor")
fun TextView.bindBookAuthor(authorList : List<String>){
    val author = authorList.toString().removeSurrounding("[", "]")
    this.text = author
}

@BindingAdapter("bookDiscount")
fun TextView.bindBookDiscount(discount : String){
    if (discount.equals("0")){
        this.text = "Sold Out"
    } else{
        this.text = discount
    }
}

@BindingAdapter("naverBookAuthor")
fun TextView.bindBookAuthor(authors : String){
    this.text = authors
}


@BindingAdapter("bookMakeTime")
fun TextView.bindBookMakeTime(dateTime : String){
    val formatDateTime = if (dateTime.isNotEmpty()) dateTime.substring(0, 10) else ""
    this.text = formatDateTime
}

@BindingAdapter("glide_default")
fun bindGlideDefault(view : ImageView, url : String){
    Glide.with(view.context).load(url).into(view)
}