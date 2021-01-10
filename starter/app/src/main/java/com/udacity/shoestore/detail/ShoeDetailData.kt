package com.udacity.shoestore.detail

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.udacity.shoestore.BR
import timber.log.Timber


class ShoeDetailData : BaseObservable() {
    var title: String
        @Bindable get() {
            Timber.i("getTitle called: returning $title")
            return title
        }
        set(value: String) {
            Timber.i("setTitle called: title $title, value $value")
            if (title != value) {
                title = value
            }
            notifyPropertyChanged(BR.title)
        }

}