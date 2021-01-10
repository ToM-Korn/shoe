package com.udacity.shoestore.detail

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.SHOE_LIST
import com.udacity.shoestore.models.Shoe
import timber.log.Timber



class ShoeDetailViewModel(private var shoeId: Int, private var create: Boolean) : ViewModel() {

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

    private val _edit = MutableLiveData<Boolean>()
    val edit: LiveData<Boolean>
        get() = _edit

    init {
        Timber.i("Create: $create")
        if (create == false) {
            // just display the shoe with the given id
            Timber.i("setting title, desc for shoe id: $shoeId")
            title.value = SHOE_LIST[shoeId].name
            desc.value = SHOE_LIST[shoeId].description
            size.value = SHOE_LIST[shoeId].size
            size_str.value = size.value.toString()
            company.value = SHOE_LIST[shoeId].company

            if (SHOE_LIST[shoeId].images.size > 0) {
                _photo.value = SHOE_LIST[shoeId].images[0]
            }
            // we do not edit
            _edit.value = false

        } else {
            // to get rid op possible nullable double problems we set size
            size.value = 0.0
            size_str.value = size.value.toString()
            // we create so we hide the text and show up the the edit fields
            _edit.value = true
        }
        // could not make it work to get images with databinding and live data
        //val d = R.drawable.ic_high_heels
        //Timber.i("$d")
        //_photo.value = d

    }


    fun onFABClick() {
        Timber.i("Fab Button Clicked! edit: ${edit.value} ${_edit.value}")
        if (_edit.value == true) {
            if (create == true) {
                // we create a new item and we are in edit mode...
                // save the data
                Timber.i("Save Data from newly created item ${title.value}")

                size.value = size_str.value?.toDoubleOrNull()

                SHOE_LIST.add(SHOE_LIST.size,
                    Shoe(title.value.toString(),
                        size.value!!,
                        company.value.toString(),
                        desc.value.toString()))
                shoeId = SHOE_LIST.size - 1
                _edit.value = false
                create = false

                // for debugging
                /*
                for (i in SHOE_LIST.indices){
                    Timber.i("${SHOE_LIST[i].name}")
                    Timber.i("${SHOE_LIST[i].size}")
                    Timber.i("${SHOE_LIST[i].description}")
                    Timber.i("${SHOE_LIST[i].company}")
                }
                Timber.i("shoelist size: ${SHOE_LIST.size}")
                */

            } else {
                // we edited a existing element
                // we save the data back to the original id
                // todo add all fields
                    Timber.i("Save existing Shoe id: $shoeId")
                Timber.i("title ${title.value}, desc ${desc.value}")
                size.value = size_str.value?.toDoubleOrNull()

                SHOE_LIST[shoeId].name = title.value.toString()
                SHOE_LIST[shoeId].description = desc.value.toString()
                SHOE_LIST[shoeId].size = size.value!!
                SHOE_LIST[shoeId].company = company.value.toString()
                _edit.value = false

            }
        } else {
            // we are in viewing mode and want to edit
            // so lets copy the data to the edit fields
            // then enable the edit fields
                Timber.i("Edit existing Shoe id: $shoeId")
            _edit.value = true
        }
    }
}