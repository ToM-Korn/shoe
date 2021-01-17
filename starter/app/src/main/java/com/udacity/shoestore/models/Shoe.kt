package com.udacity.shoestore.models

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize
import timber.log.Timber


@Parcelize
data class Shoe(var name: String, var size: Double, var company: String, var description: String,
                val images: List<String> = mutableListOf()) : Parcelable

class SharedShoeData : ViewModel() {


    private var SHOE_LIST = mutableListOf<Shoe>(
        Shoe("New Style",38.0,"Prada","Some nice Words about this wonderful Shoe! ",
            mutableListOf("shoe1")
        ),
        Shoe("Defender", 39.0, "Nike","Soluta uae consectetur molestias totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores.",
            mutableListOf("shoe2")),
        Shoe("Remember", 42.0, "Puma","Totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores.",
            mutableListOf("shoe3")),
        Shoe("Blender", 27.0, "Adidas","Sicta. Quae consectetur molestias totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores.",
            mutableListOf("shoe4")),
        Shoe("Obscure", 32.0, "Bergans","Ipsum dicta. Quae consectetur molestias totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores.",
            mutableListOf("shoe5")),
        Shoe("Banana", 34.0, "Xped","Quae consectetur molestias totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores.",
            mutableListOf("shoe6")),
        Shoe("Foo", 43.0, "Diesel","Consectetur molestias totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores.",
            mutableListOf("shoe11")),
        Shoe("Bar", 36.0, "Waldviertler","Molestias totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores.",
            mutableListOf("shoe8")),
        Shoe("Neverlast", 37.0, "Adidas","Totam corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores.",
            mutableListOf("shoe10")),
        Shoe("Happydust", 32.0, "Luis Viton","Corrupti praesentium eligendi tempore laborum. Est autem saepe vel dolores.")
    )

    // Fields and Data used te be like this
    // but because I wanted to use two way data binding
    // i needed to change them to the open MutableLiveData:
    // private val _title = MutableLiveData<String>()
    // val title: LiveData<String>
    //    get() = _title

    // this is the new form
    val title = MutableLiveData<String>()

    val desc = MutableLiveData<String>()

    val size = MutableLiveData<Double>()
    val size_str = MutableLiveData<String>()

    val company = MutableLiveData<String>()

    private val _photo = MutableLiveData<String>()
    val photo: LiveData<String>
        get() = _photo

    private var current_id : Int = 0

    val num_indices = MutableLiveData<Int>()


    init {
        Timber.i("Init Shared Shoe Data")
        num_indices.value = SHOE_LIST.size-1
    }


    fun cleanData() {
        Timber.i("Clear Shoe Data")
        title.value = null
        desc.value = null
        size.value = null
        size_str.value = "0.0"
        company.value = null
        _photo.value = null
        current_id = 0
    }


    fun loadData(shoeId: Int) {
        Timber.i("setting title, desc for shoe id: $shoeId")
        current_id = shoeId
        title.value = SHOE_LIST[shoeId].name
        desc.value = SHOE_LIST[shoeId].description
        size.value = SHOE_LIST[shoeId].size
        size_str.value = SHOE_LIST[shoeId].size.toString()
        company.value = SHOE_LIST[shoeId].company

        if (SHOE_LIST[shoeId].images.size > 0) {
            _photo.value = SHOE_LIST[shoeId].images[0]
        } else {
            _photo.value = null
        }
    }

    fun createShoe() {
        convertSize()
        SHOE_LIST.add(SHOE_LIST.size, Shoe(
            title.value.toString(),
            size.value!!,
            company.value.toString(),
            desc.value.toString()
        ))
        num_indices.value = SHOE_LIST.size-1
    }

    fun saveShoe() {
        convertSize()
        val shoe = SHOE_LIST[current_id]
        shoe.name = title.value.toString()
        shoe.description = desc.value.toString()
        shoe.company = company.value.toString()
        shoe.size = size.value!!
    }

    private fun convertSize() {
        if (size_str.value.toString() != "") {
            size.value = size_str.value.toString().toDouble()
        } else {
            size.value = 0.0
        }
    }

}