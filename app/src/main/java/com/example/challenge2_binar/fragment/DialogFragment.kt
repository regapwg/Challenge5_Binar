package com.example.challenge2_binar.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.challenge2_binar.R
import com.example.challenge2_binar.databinding.FragmentDialogBinding
import com.example.challenge2_binar.viewModel.KeranjangViewModel
import com.example.challenge2_binar.viewModel.ViewModelFactory


class DialogFragment : DialogFragment() {

    private lateinit var binding : FragmentDialogBinding
    private lateinit var keranjangViewModel: KeranjangViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDialogBinding.inflate(inflater, container, false)
        val viewModelFactory = ViewModelFactory(requireActivity().application)
        keranjangViewModel = ViewModelProvider(this, viewModelFactory)[KeranjangViewModel::class.java]

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        keranjangViewModel.itemLiveData.observe(viewLifecycleOwner) {}
        keranjangViewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.tvTotalPembayaran.text = it.toString()
        }

        binding.btnHome.setOnClickListener {
            keranjangViewModel.deleteAllItem()
            Toast.makeText(context, "Kembali ke Halaman Home", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_konfirmasiPesananFragment_to_homeFragment)

        }
    }

    companion object{
        fun show(fm: FragmentManager)= DialogFragment().show(fm, DialogFragment::class.java.simpleName)
    }

}