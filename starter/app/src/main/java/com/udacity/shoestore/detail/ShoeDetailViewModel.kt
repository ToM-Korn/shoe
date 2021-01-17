package com.udacity.shoestore.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber



class ShoeDetailViewModel(private var shoeId: Int, private var _create: Boolean) : ViewModel() {

    private val _edit = MutableLiveData<Boolean>()
    val edit: LiveData<Boolean>
        get() = _edit

    private val _edit_done = MutableLiveData<Boolean>()
    val edit_done: LiveData<Boolean>
        get() = _edit_done


    private val _cancel = MutableLiveData<Boolean>()
    val cancel: LiveData<Boolean>
        get() = _cancel

    private val _shoe_id = MutableLiveData<Int>()
    val shoe_id: LiveData<Int>
        get() = _shoe_id

    val create: Boolean
        get() = _create

    init {
        if (_create == false) {
            // just display the shoe with the given id
            Timber.i("setting title, desc for shoe id: $shoeId")

            _shoe_id.value = shoeId
            // we do not edit
            _edit.value = false
            _edit_done.value = false
        } else {
            Timber.i("Createing new item - create: $_create")
            // to get rid op possible nullable double problems we set size
            // we create so we hide the text and show up the the edit fields
            _edit.value = true
            _edit_done.value = false
        }
        // could not make it work to get images with databinding and live data
        //val d = R.drawable.ic_high_heels
        //Timber.i("$d")
        //_photo.value = d

    }

    fun onFABClick() {
        Timber.i("Fab Button Clicked! edit: ${edit.value} ${_edit.value}")
        if (_edit.value == true) {
            if (_create == true) {
                // save the newly created item
                // create = false
                _edit_done.value = true
            } else {
                // save the existing item
                _edit.value = false
                _edit_done.value = true
            }
        } else {
            // we are in viewing mode and want to edit
            // so lets copy the data to the edit fields
            // then enable the edit fields
                Timber.i("Edit existing Shoe id: $shoeId")
            _edit.value = true
        }
    }

    fun onCancelClick(){
        // user pressed cancel button, we dont want to save data
        // just go back to view mode
        if (_create == true) {
            _cancel.value = true
        } else {
            _edit.value = false
            _shoe_id.value = shoeId
        }

    }
}