package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.udacity.shoestore.databinding.FragmentScrollingBinding
import com.udacity.shoestore.models.SHOE_LIST
import kotlinx.android.synthetic.main.snippet_shoe.view.*
import timber.log.Timber

class ScrollingFragment : Fragment() {

    lateinit var binding: FragmentScrollingBinding
    private var l_container: ViewGroup? = null
    private lateinit var l_inflater: LayoutInflater

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Timber.i("Create Scroll View and inflating it")

        l_inflater = inflater
        l_container = container

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_scrolling, container, false)

        // walk through the list of shoes and fill the table within the scroll view each
        // with the content of one shoe
        Timber.i("shoelist size: ${SHOE_LIST.size}")

        for (i in SHOE_LIST.indices){
            Timber.i("${SHOE_LIST[i].name}")
            Timber.i("${SHOE_LIST[i].size}")
            Timber.i("${SHOE_LIST[i].description}")
            Timber.i("${SHOE_LIST[i].company}")
        }

        for (i in SHOE_LIST.indices){
            addShoe(i)
        }

        // add onclick listener to fab button
        val fab: View = binding.fab
        fab.setOnClickListener { view ->
            // if the fab button is clicked we want to create a new show
            // we link to the detail fragment with the attribute create = true
            view.findNavController().navigate(
                ScrollingFragmentDirections.actionScrollingFragmentToShoeDetailFragment(0, true))

        }

        return binding.root
    }

    // for debugging
    /*
    override fun onPause() {
        super.onPause()
        Timber.i("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("onDestroyView")

    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy")

    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart")

    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume")

    }

     */

    fun addShoe(id: Int){
        // inflate a new shoe snippet to be filled
        val shoe_binding = l_inflater.inflate(R.layout.snippet_shoe, l_container, false)

        Timber.i("addShoe Tag: $id")
        // set a tag to the list shoe list index
        shoe_binding.tag = id

        // fill the binding with content of that index
        shoe_binding.row_shoe_title.text = SHOE_LIST[id].name
        var desc = ""
        if (SHOE_LIST[id].description.length > 60){
            desc = SHOE_LIST[id].description.subSequence(0..60).toString()
            var desc_list = desc.split(' ')
            desc = desc_list.joinToString(" ",limit = -1)
            desc += " ..."
        } else {
            desc = SHOE_LIST[id].description
        }
        shoe_binding.row_shoe_short_desc.text = desc

        // if images available take the first one for the scroll view overview
        if (SHOE_LIST[id].images.size > 0 ) {
            // get the resource identifier
            val img_res = this.resources.getIdentifier(
                SHOE_LIST[id].images[0],
                "drawable",
                this.activity?.packageName
            )
            // set the views image resource
            shoe_binding.row_shoe_image.setImageResource(img_res)
        }


        // for every shoe item set an onclick listener, so the whole view response on a touch
        // and opens the detail view
        // as argument for the detail view the index of the listitem is provided
        shoe_binding.setOnClickListener { v: View ->
            v.findNavController().navigate(
                ScrollingFragmentDirections.actionScrollingFragmentToShoeDetailFragment(id,false))
        }


        // add the finished and filled shoe snippet to the table within the scrollview
        binding.scrollViewTable.addView(shoe_binding)
    }
}