package com.itsanilg.petsapp.ui.pets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.itsanilg.petsapp.R
import com.itsanilg.petsapp.databinding.AdapterPetBinding
import com.itsanilg.petsapp.model.PetModel


class PetAdapter(var petList: ArrayList<PetModel>) :
    RecyclerView.Adapter<PetAdapter.PetViewHolder>() {
    lateinit var listViewModel: PetListViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding: AdapterPetBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_pet,
            parent,
            false
        )

        return PetViewHolder(binding)

    }

    override fun getItemCount() = petList.size

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(petList[position])
        holder.itemView.setOnClickListener {
            listViewModel.onItemClick(petList.get(position))
        }
    }


    class PetViewHolder(val binding: AdapterPetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pet: PetModel) {
            binding.pet = pet
            pet.loadImage(binding.ivPet, pet.image_url)


        }
    }

    fun updatePets(petList: List<PetModel>, viewModel: PetListViewModel) {
        petList.addAll(petList)
        listViewModel = viewModel
        notifyDataSetChanged()
    }


}