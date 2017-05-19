@file:JvmName("ExtensionUtils")
package com.jamesmwangi.keddit.commons.extensions

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jamesmwangi.keddit.R
import com.squareup.picasso.Picasso
import org.mockito.Mockito

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false) : View {
    return LayoutInflater.from(context).inflate(layoutId,this,attachToRoot)
}

fun ImageView.loadImage(imageUrl: String){
    if(TextUtils.isEmpty(imageUrl)){
        Picasso.with(context).load(R.mipmap.ic_launcher).into(this)
    } else {
        Picasso.with(context).load(imageUrl).into(this)
    }
}

inline fun <reified T: Parcelable> createParcel(
        crossinline  createFromParcel: (Parcel) -> T?) : Parcelable.Creator<T> =
            object : Parcelable.Creator<T> {
                override fun createFromParcel(source: Parcel): T? = createFromParcel(source)
                override fun newArray(size: Int): Array<T?> = arrayOfNulls(size)
            }

inline fun<reified T: Any> mock(): T = Mockito.mock(T::class.java)