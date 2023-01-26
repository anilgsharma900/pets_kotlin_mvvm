package com.itsanilg.petsapp.ui.pets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsanilg.petsapp.R
import com.itsanilg.petsapp.databinding.FragmentPetListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PetListFragment : Fragment() {
    private var _binding: FragmentPetListBinding? = null

    private val binding get() = _binding!!
    private var myJob: Job? = null
    var layout = LinearLayoutManager(activity);
    private var petAdapter = PetAdapter(arrayListOf())
    private lateinit var viewModel: PetListViewModel

    companion object {
        fun newInstance() = PetListFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[PetListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetListBinding.inflate(inflater, container, false)


        fecthData(this)

        binding.rvPetList.apply {
            layoutManager = layout
            adapter = petAdapter
        }

        observeViewModel()

        return binding.root
    }

    private fun fecthData(context: Fragment) {
        myJob = CoroutineScope(Dispatchers.IO).launch {
            viewModel.fetchData(context)
        }
    }

    fun observeViewModel() {
        viewModel.petList.observe(viewLifecycleOwner, Observer { pets ->
            pets?.let {
                binding.rvPetList.visibility = View.VISIBLE
                petAdapter.updatePets(pets, viewModel)
            }
        })

        viewModel.articalsLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                binding.listError.text = resources.getString(R.string.txt_loading_error)
                binding.listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.listError.visibility = View.GONE
                    binding.rvPetList.visibility = View.GONE
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}