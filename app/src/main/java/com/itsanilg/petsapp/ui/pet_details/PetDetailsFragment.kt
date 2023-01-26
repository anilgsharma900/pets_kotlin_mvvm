package com.itsanilg.petsapp.ui.pet_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.itsanilg.petsapp.R

class PetDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = PetDetailsFragment()
    }

    private lateinit var viewModel: PetDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pet_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PetDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}