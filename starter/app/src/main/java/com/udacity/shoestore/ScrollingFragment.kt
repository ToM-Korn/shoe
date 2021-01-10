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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Timber.i("Create Scroll View and inflating in")

        // Inflate the layout for this fragment
        val binding : FragmentScrollingBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_scrolling, container, false)

        // walk through the list of shoes and fill the table within the scroll view each
        // with the content of one shoe
        for (i in 0..SHOE_LIST.size-1){
            // inflate a new shoe snippet to be filled
            val shoe_binding = inflater.inflate(R.layout.snippet_shoe, container, false)

            // set a tag to the list shoe list index
            shoe_binding.tag = i

            // fill the binding with content of that index
            shoe_binding.row_shoe_title.text = SHOE_LIST[i].name
            shoe_binding.row_shoe_short_desc.text = SHOE_LIST[i].description.subSequence(0..40)

            // if images available take the first one for the scroll view overview
            if (SHOE_LIST[i].images.size > 0 ) {
                // get the resource identifier
                val img_res = this.resources.getIdentifier(
                    SHOE_LIST[i].images[0],
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
                    ScrollingFragmentDirections.actionScrollingFragmentToShoeDetailFragment(i,false))
            }


            // add the finished and filled shoe snippet to the table within the scrollview
            binding.scrollViewTable.addView(shoe_binding)
        }

        // add onclick listener to fab button
        val fab: View = binding.fab
        fab.setOnClickListener { view ->
            // if the fab button is clicked we want to create a new show
            // we link to the detail fragment with the attribute create = true
            view.findNavController().navigate(
                ScrollingFragmentDirections.actionScrollingFragmentToShoeDetailFragment(0, true))

            // todo remove when done
            // Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
            //    .setAction("Action", null)
            //    .show()
        }

        return binding.root
    }
}