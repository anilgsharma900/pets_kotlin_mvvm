package com.itsanilg.petsapp.ui.pets

import com.itsanilg.petsapp.model.PetModel

interface MainNavigator {

    fun onItemClick(petModel: PetModel);
}