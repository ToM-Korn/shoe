package com.udacity.shoestore.detail

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.models.SharedShoeData
import timber.log.Timber


class ShoeDetailFragment : Fragment() {

    private lateinit var shoeData: SharedShoeData

    private lateinit var viewModel: ShoeDetailViewModel
    private lateinit var viewFactory: ShoeDetailFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //@BindingAdapter("android:src")
    //fun setImageViewResource(imageView: ImageView, resource: Int) {
    //    Timber.i("set imageview resource $resource")
    //    imageView.setImageResource(resource)
    //}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentShoeDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_detail,
            container,
            false
        )

        // get the sharedShoeData froom the activity
        shoeData =
            ViewModelProvider(ViewModelStoreOwner { this.requireActivity().viewModelStore }).get(
                SharedShoeData::class.java
            )

        val shoeDetailArgs by navArgs<ShoeDetailFragmentArgs>()

        viewFactory = ShoeDetailFactory(shoeDetailArgs.id, shoeDetailArgs.create)
        viewModel = ViewModelProvider(this, viewFactory)
            .get(ShoeDetailViewModel::class.java)


        binding.shoeDetailViewModel = viewModel
        binding.shoeDetailData = shoeData

        // Specify the current activity as the lifecycle owner of the binding. This is used so that
        // the binding can observe LiveData updates
        binding.setLifecycleOwner(this)


        shoeData.photo.observe(this, Observer { photo ->

            if (photo != null) {
                val img_res = this.resources.getIdentifier(
                    photo,
                    "drawable",
                    this.activity?.packageName
                )
                binding.shoeDetailImage.setImageResource(img_res)
            }
        }
        )

        viewModel.cancel.observe( this, Observer { cancel ->
            // only activated if shoe is to be created and cancel is pressed
            // clear values in shoe
            shoeData.cleanData()
            // navigate to the scrollview
            this.findNavController().navigateUp()

        })
        viewModel.shoe_id.observe(this, Observer { id ->
            Timber.i("Shoe id changed: $id")
            shoeData.loadData(id)
        })

        viewModel.edit_done.observe(this, Observer { edit_done ->
            // save data in shoeData
            if (edit_done == true) {
                if (viewModel.create) {
                    shoeData.createShoe()
                } else {
                    shoeData.saveShoe()
                }
                Timber.i("Edit Done")

                // clear values in shoe
                shoeData.cleanData()
                // navigate to the scrollview
                this.findNavController().navigateUp()
            }
        })


        viewModel.edit.observe(this, Observer { edit ->
            // layout changes from viewing-mode to edit-mode if the fab is pressed or
            // if we add a new element
            //
            // i found that the visibility can be changed directly in the layout as well
            // with this:
            // android:visibility="@{visible ? android.view.View.VISIBLE: android.view.View.GONE}" />
            // but as it works this way as well, i thought i leave it for now like this.
            if (edit) {
                // chonge the text view to edit
                binding.shoeDetailDescription.visibility = View.GONE
                binding.shoeDetailTitle.visibility = View.GONE
                binding.shoeDetailSize.visibility = View.GONE
                binding.shoeDetailSizeInline.visibility = View.GONE
                binding.shoeDetailCompany.visibility = View.GONE
                binding.floatingSizeUnderlay.visibility = View.GONE

                binding.shoeDetailButtonCancel.visibility = View.VISIBLE
                binding.shoeDetailDescriptionEdit.visibility = View.VISIBLE
                binding.shoeDetailTitleEdit.visibility = View.VISIBLE
                binding.shoeDetailCompanyEdit.visibility = View.VISIBLE
                binding.shoeDetailSizeEdit.visibility = View.VISIBLE
                // change the fab icon to save
                binding.floatingActionButton.setImageResource(R.drawable.ic_baseline_save_24)
            } else {
                // chonge from edit to text view elements
                binding.shoeDetailDescription.visibility = View.VISIBLE
                binding.shoeDetailTitle.visibility = View.VISIBLE
                binding.shoeDetailCompany.visibility = View.VISIBLE
                binding.shoeDetailSize.visibility = View.VISIBLE
                binding.shoeDetailSizeInline.visibility = View.VISIBLE


                binding.shoeDetailButtonCancel.visibility = View.GONE
                binding.shoeDetailDescriptionEdit.visibility = View.GONE
                binding.shoeDetailTitleEdit.visibility = View.GONE
                binding.shoeDetailCompanyEdit.visibility = View.GONE
                binding.shoeDetailSizeEdit.visibility = View.GONE

                // chonge the icon on the fab to edit
                binding.floatingActionButton.setImageResource(R.drawable.ic_baseline_edit_24)
                // Hide the keyboard.
                val imm =
                    this.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            }
        })

            
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("ShoeDetail created, Shoe ID ${viewModel.shoe_id}")
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