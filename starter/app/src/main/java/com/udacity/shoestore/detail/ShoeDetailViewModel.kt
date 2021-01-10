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



class ShoeDetailViewModel(private val shoeId: Int, private var create: Boolean) : ViewModel() {

    // Fields and Data used te be like this
    // but because I wanted to use two way data binding
    // i needed to change them to the open MutableLiveData:
    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    // this is the new form
    //private val title = MutableLiveData<String>()

    private val _desc = MutableLiveData<String>()
    val desc: LiveData<String>
        get() = _desc

    private val _title_edit = MutableLiveData<String>()
    val title_edit: LiveData<String>
        get() = _title_edit

    private val _desc_edit = MutableLiveData<String>()
    val desc_edit: LiveData<String>
        get() = _desc_edit

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
            _title.value = SHOE_LIST[shoeId].name
            _desc.value = SHOE_LIST[shoeId].description
            if (SHOE_LIST[shoeId].images.size > 0) {
                _photo.value = SHOE_LIST[shoeId].images[0]
            }
            // we do not edit
            _edit.value = false

        } else {
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
                Timber.i("Save Data from newly created item ${_title.value}")
                _title.value = _title_edit.value
                _desc.value = desc_edit.value
                SHOE_LIST += Shoe(
                    _title.value.toString(),
                    0.0, "", desc.value.toString()
                )
                _edit.value = false
                create = false
            } else {
                // we edited a existing element
                // we save the data back to the original id
                // todo add all fields
                    Timber.i("Save existing Shoe id: $shoeId")
                Timber.i("_title_edit ${_title_edit.value}, title_edit ${title_edit.value}, _desc_edit ${_desc_edit.value}, desc_edit ${desc_edit.value}")
                _desc.value = _desc_edit.value
                SHOE_LIST[shoeId].name = _title.value.toString()
                SHOE_LIST[shoeId].description = _desc.value.toString()
                _edit.value = false


            }
        } else {
            // we are in viewing mode and want to edit
            // so lets copy the data to the edit fields
            // then enable the edit fields
                Timber.i("Edit existing Shoe id: $shoeId")
            _title_edit.value = _title.value
            _desc_edit.value = _desc.value
            _edit.value = true
        }
    }
}