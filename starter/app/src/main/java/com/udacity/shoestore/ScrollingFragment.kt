package com.udacity.shoestore

import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.udacity.shoestore.databinding.FragmentScrollingBinding
import com.udacity.shoestore.databinding.SnippetShoeBinding
import com.udacity.shoestore.detail.ShoeDetailViewModel
import com.udacity.shoestore.models.SharedShoeData
import kotlinx.android.synthetic.main.snippet_shoe.view.*
import timber.log.Timber

class ScrollingFragment : Fragment() {

    lateinit var binding: FragmentScrollingBinding
    private var l_container: ViewGroup? = null
    private lateinit var l_inflater: LayoutInflater

    private lateinit var shoeData: SharedShoeData

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
            inflater, R.layout.fragment_scrolling, container, false
        )

        // getting the sharedShoeData from activity
        shoeData = ViewModelProvider(ViewModelStoreOwner { this.requireActivity().viewModelStore }).get(SharedShoeData::class.java)

        // walk through the list of shoes and fill the table within the scroll view each
        // with the content of one shoe
        for (i in 0..shoeData.num_indices.value!!){
            addShoe(i)
        }


        // for debugging
        /*
        Timber.i("shoelist size: ${SHOE_LIST.size}")

        for (i in SHOE_LIST.indices){
            Timber.i("${SHOE_LIST[i].name}")
            Timber.i("${SHOE_LIST[i].size}")
            Timber.i("${SHOE_LIST[i].description}")
            Timber.i("${SHOE_LIST[i].company}")
        }

         */

        // add onclick listener to fab button
        val fab: View = binding.fab
        fab.setOnClickListener { view ->
            // to get an empty detail view for editing we first have to clear the data in
            // shared shoe data model
            //shoeData.cleanData()

            // if the fab button is clicked we want to create a new show
            // we link to the detail fragment with the attribute create = true
            view.findNavController().navigate(
                ScrollingFragmentDirections.actionScrollingFragmentToShoeDetailFragment(0, true)
            )

        }

        setHasOptionsMenu(true)

        return binding.root
    }

    fun addShoe(id: Int){
        // inflate a new shoe snippet to be filled
        val shoe_binding : SnippetShoeBinding = DataBindingUtil.inflate(l_inflater, R.layout.snippet_shoe, l_container, false)

        Timber.i("addShoe Tag: $id")
        // set a tag to the list shoe list index
        shoe_binding.root.tag = id

        shoeData.loadData(id)

        // fill the binding with content of that index
        shoe_binding.rowShoeTitle.text = shoeData.title.value

        var desc = ""

        if (shoeData.desc.value != null){
            desc = shoeData.desc.value.toString()
            if (desc.length > 60) {
                desc = desc.subSequence(0..60).toString()
                var desc_list = desc.split(' ')
                desc = desc_list.joinToString(" ",limit = -1)
                desc += " ..."
            }
        }

        shoe_binding.rowShoeShortDesc.text = desc

        shoe_binding.rowShoeSize.text = "Size: "+ shoeData.size_str.value

        if (shoeData.photo.value != null){
            Timber.i("attaching photo")
            val img_res = this.resources.getIdentifier(
            shoeData.photo.value,
            "drawable",
            this.activity?.packageName
            )
            // set the views image resource
            shoe_binding.rowShoeImage.setImageResource(img_res)
        }
        // for every shoe item set an onclick listener, so the whole view response on a touch
        // and opens the detail view
        // as argument for the detail view the index of the listitem is provided
        shoe_binding.root.setOnClickListener { v: View ->
            shoeData.loadData(id)
            v.findNavController().navigate(
                ScrollingFragmentDirections.actionScrollingFragmentToShoeDetailFragment(id,false))
        }

        shoeData.cleanData()


        // add the finished and filled shoe snippet to the table within the scrollview
        binding.scrollViewTable.addView(shoe_binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_buttons, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.loginFragment -> logout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout(){
        this.findNavController().navigate(R.id.loginFragment)
    }
}


