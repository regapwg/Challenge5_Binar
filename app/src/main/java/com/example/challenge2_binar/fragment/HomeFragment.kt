package com.example.challenge2_binar.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge2_binar.R
import com.example.challenge2_binar.SharedPreference
import com.example.challenge2_binar.adapter.MenuAdapterHorizontal
import com.example.challenge2_binar.adapter.NewAdapter
import com.example.challenge2_binar.databinding.FragmentHomeBinding
import com.example.challenge2_binar.produk.Kategori
import com.example.challenge2_binar.produk.Menu
import com.example.challenge2_binar.viewModel.HomeViewModel
import com.google.firebase.crashlytics.FirebaseCrashlytics


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val menuData = Menu.menu
    private val kategoriData = Kategori.kategori
    private val kategoriMenuAdapter = MenuAdapterHorizontal(kategoriData)
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedPreference: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        binding.rvMenuKategori.setHasFixedSize(true)
        binding.rvMenuKategori.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMenuKategori.adapter = kategoriMenuAdapter

        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        sharedPreference = SharedPreference(requireContext())
        homeViewModel.isGrid.value = sharedPreference.getPreferences()
        homeViewModel.isGrid.observe(viewLifecycleOwner) {
            setPrefLayout()
        }

        binding.rvMenu.setHasFixedSize(true)
        grid()
        setPrefLayout()

        return binding.root
    }


    private fun viewLayout(isGrid: Boolean){
        if (isGrid) {
            grid()
            binding.imageList.setImageResource( R.drawable.ic_linear)
        } else {
            linear()
            binding.imageList.setImageResource( R.drawable.ic_grid)
        }
    }

    private fun setPrefLayout() {
        val buttonLayout = binding.imageList
        val setLayout = homeViewModel.isGrid.value?: sharedPreference.getPreferences()

        viewLayout(setLayout)
        buttonLayout.setOnClickListener {
            val updateLayout = !setLayout
            homeViewModel.isGrid.value = updateLayout
            sharedPreference.setPreferences(updateLayout)
        }
    }


    private fun linear() {
        binding.rvMenu.layoutManager = LinearLayoutManager(requireActivity())
        val menuAdapter = NewAdapter(menuData, homeViewModel.isGrid.value ?: false, listener = { pickItem ->
            val bundle = bundleOf("pickItem" to pickItem)
            FirebaseCrashlytics.getInstance().recordException(
                RuntimeException("item $bundle")
            )
            findNavController().navigate(R.id.action_homeFragment_to_detailMenuFragment, bundle)
        })
        binding.rvMenu.adapter = menuAdapter
    }


    private fun grid() {
        binding.rvMenu.layoutManager = GridLayoutManager(requireActivity(), 2)
        val menuAdapter = NewAdapter(menuData, homeViewModel.isGrid.value ?: true, listener = { pickItem ->
            val bundle = bundleOf("pickItem" to pickItem)
            findNavController().navigate(R.id.action_homeFragment_to_detailMenuFragment, bundle)
        })
        binding.rvMenu.adapter = menuAdapter
    }

}