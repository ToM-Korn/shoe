package com.udacity.shoestore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentInstructionsBinding
import timber.log.Timber

class InstructionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment unsing DataBinding

        val binding: FragmentInstructionsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_instructions,
            container,
            false
        )
        // create listener for main button
        binding.buttonInstructions.setOnClickListener { v: View ->
            Timber.i("pressed shoe me shoes button!")
            v.findNavController().navigate(
                InstructionsFragmentDirections.actionInstructionsFragmentToScrollingFragment()
            )

        }


        return binding.root
    }
}