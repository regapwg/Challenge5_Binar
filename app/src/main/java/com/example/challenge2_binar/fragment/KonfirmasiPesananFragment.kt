package com.example.challenge2_binar.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge2_binar.R
import com.example.challenge2_binar.adapter.KeranjangAdapter
import com.example.challenge2_binar.databinding.FragmentDetailMenuBinding
import com.example.challenge2_binar.databinding.FragmentKonfirmasiPesananBinding
import com.example.challenge2_binar.viewModel.KeranjangViewModel
import com.example.challenge2_binar.viewModel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class KonfirmasiPesananFragment : Fragment() {

    private var _binding: FragmentKonfirmasiPesananBinding? = null
    private val binding get() = _binding!!
    private lateinit var keranjangViewModel: KeranjangViewModel
    private lateinit var keranjangAdapter: KeranjangAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentKonfirmasiPesananBinding.inflate(inflater, container, false)
        val viewModelFactory = ViewModelFactory(requireActivity().application)
        keranjangViewModel = ViewModelProvider(this, viewModelFactory)[KeranjangViewModel::class.java]

        setupRecyclerView()
        buttonUpBack()

        binding.btnPesanFix.setOnClickListener {
            val dialog = DialogFragment()
            dialog.show(childFragmentManager, "dialog")
        }

        return binding.root
    }
    private fun setupRecyclerView() {
        keranjangAdapter = KeranjangAdapter(keranjangViewModel)
        binding.rvKeranjang.setHasFixedSize(true)
        binding.rvKeranjang.layoutManager = LinearLayoutManager(requireContext())
        binding.rvKeranjang.adapter = keranjangAdapter

        keranjangViewModel.getAllitems.observe(viewLifecycleOwner) {
            keranjangAdapter.data(it)
        }

        keranjangViewModel.itemLiveData.observe(viewLifecycleOwner) {

        }

        keranjangViewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.tvTotallPembayaran.text = it.toString()
        }
    }


    private fun buttonUpBack() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}