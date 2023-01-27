package com.itsanilg.petsapp.ui.pets

import android.os.Build
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
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters


class PetListViewModel : ViewModel() {
    val petList = MutableLiveData<List<PetModel>>()
    val loadError = MutableLiveData<Boolean>()
    val isValidTime = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    lateinit var navigator: MainNavigator

    fun setMainNavigator(mainNavigator: MainNavigator) {
        navigator = mainNavigator
    }

    fun fetchData(context: Fragment) {
        viewModelScope.launch(Dispatchers.Main) {
            loading.value = true

            val now: LocalDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.now(ZoneId.systemDefault())
            } else {
                TODO("VERSION.SDK_INT < O")
            } // current time

            val start = now.withHour(9).withMinute(0).withSecond(0)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            val end = now.withHour(18).withMinute(0).withSecond(0)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY))


            if ((now.isAfter(start) && now.isBefore(end)) || (now.isEqual(start) || now.isEqual(end))) {

                try {
                    val jsonString: String? = jsonFromAsset(context)
                    if (jsonString != null) {
                        val result: PetsModel = Gson().fromJson(jsonString, PetsModel::class.java)
                        petList.value = result.pets
                        loadError.value = false
                        loading.value = false
                    } else {
                        loadError.value = true
                        loading.value = false
                    }
                } catch (e: Exception) {
                    loadError.value = true
                    loading.value = false
                }
            } else {
                isValidTime.value = true
                loading.value = false
            }
        }


    }


    private fun jsonFromAsset(context: Fragment): String? {
        val json: String? = try {
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