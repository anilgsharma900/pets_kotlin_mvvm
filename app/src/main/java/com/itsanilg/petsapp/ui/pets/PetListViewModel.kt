package com.itsanilg.petsapp.ui.pets

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.itsanilg.petsapp.model.PetModel
import com.itsanilg.petsapp.model.PetsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream


class PetListViewModel : ViewModel() {
    val petList = MutableLiveData<List<PetModel>>()
    val articalsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    lateinit var navigator: MainNavigator

    fun setMainNavigator(mainNavigator: MainNavigator) {
        navigator = mainNavigator
    }

    fun fetchData(context: Fragment) {
        viewModelScope.launch(Dispatchers.Main) {
            loading.value = true
            try {
                val jsonString: String? = jsonFromAsset(context);
                if (jsonString != null) {
                    val result: PetsModel = Gson().fromJson(jsonString, PetsModel::class.java)
                    petList.value = result.pets
                    articalsLoadError.value = false
                    loading.value = false
                } else {
                    articalsLoadError.value = true
                    loading.value = false
                }
            } catch (e: Exception) {
                articalsLoadError.value = true
                loading.value = false
            }
        }


    }


    private fun jsonFromAsset(context: Fragment): String? {
        var json: String? = null
        json = try {
            val input: InputStream = context.resources.assets.open("pets_list.json")
            val size: Int = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            String(buffer)
        } catch (ex: IOException) {
            return null
        }
        return json
    }


    fun onItemClick(petModel: PetModel) {
        navigator.onItemClick(petModel)
    }
}