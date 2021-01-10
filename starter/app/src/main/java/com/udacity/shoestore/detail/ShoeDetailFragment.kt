package com.udacity.shoestore.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.udacity.shoestore.R
import com.udacity.shoestore.ScrollingFragmentDirections
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.models.SHOE_LIST
import timber.log.Timber
import java.io.ObjectInputValidation


class ShoeDetailFragment : Fragment() {

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

        val binding : FragmentShoeDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_detail,
            container,
            false
        )

        val shoeDetailArgs by navArgs<ShoeDetailFragmentArgs>()

        viewFactory = ShoeDetailFactory(shoeDetailArgs.id, shoeDetailArgs.create)
        viewModel = ViewModelProvider(this, viewFactory)
            .get(ShoeDetailViewModel::class.java)


        binding.shoeDetailViewModel = viewModel

        // Specify the current activity as the lifecycle owner of the binding. This is used so that
        // the binding can observe LiveData updates
        binding.setLifecycleOwner(this)

        viewModel.photo.observe( this, Observer { photo ->

                val img_res = this.resources.getIdentifier(
                        photo,
                "drawable",
                this.activity?.packageName
                )
                binding.imageView3.setImageResource(img_res)
            }
        )

        viewModel.edit.observe( this, Observer { edit ->
            // layout changes from viewing-mode to edit-mode if the fab is pressed or
            // if we add a new element
            //
            // i found that the visibility can be changed directly in the layout as well
            // with this:
            // android:visibility="@{visible ? android.view.View.VISIBLE: android.view.View.GONE}" />
            // but as it works this way as well, i thought i leave it for now like this.
            if (edit) {
                binding.shoeDetailDescription.visibility = View.GONE
                binding.shoeDetailTitle.visibility = View.GONE
                binding.shoeDetailDescriptionEdit.visibility = View.VISIBLE
                binding.shoeDetailTitleEdit.visibility = View.VISIBLE
                binding.floatingActionButton.setImageResource(R.drawable.ic_baseline_save_24)
            }   else {
                binding.shoeDetailDescription.visibility = View.VISIBLE
                binding.shoeDetailTitle.visibility = View.VISIBLE
                binding.shoeDetailDescriptionEdit.visibility = View.GONE
                binding.shoeDetailTitleEdit.visibility = View.GONE
                binding.floatingActionButton.setImageResource(R.drawable.ic_baseline_edit_24)
            }
        })

        return binding.root

    }
}