package com.example.challenge2_binar.fragment



import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge2_binar.R
import com.example.challenge2_binar.SharedPreference
import com.example.challenge2_binar.adapter.MenuAdapterHorizontal
import com.example.challenge2_binar.adapter.NewAdapter
import com.example.challenge2_binar.api.APIClient
import com.example.challenge2_binar.databinding.FragmentHomeBinding
import com.example.challenge2_binar.modelCategory.KategoriMenu
import com.example.challenge2_binar.produk.ListData
import com.example.challenge2_binar.produk.ListMenu
import com.example.challenge2_binar.user.User
import com.example.challenge2_binar.util.SimpleViewModelFactory
import com.example.challenge2_binar.util.Status
import com.example.challenge2_binar.viewModel.HomeViewModel
import com.example.challenge2_binar.viewModel.SimpleViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Objects


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var kategoriMenuAdapter : MenuAdapterHorizontal
    private lateinit var menuAdapter: NewAdapter
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedPreference: SharedPreference
    private lateinit var  auth: FirebaseAuth
    private lateinit var  database: DatabaseReference
    private lateinit var uid : String
    private lateinit var user : User
    private lateinit var viewModel: SimpleViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        kategoriMenuAdapter = MenuAdapterHorizontal(this@HomeFragment, arrayListOf())
        binding.rvMenuKategori.setHasFixedSize(true)
        binding.rvMenuKategori.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMenuKategori.adapter = kategoriMenuAdapter
        remoteGetCategory()


        viewModel = ViewModelProvider(this, SimpleViewModelFactory(APIClient.instance))[SimpleViewModel::class.java]
        remoteGetList()


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

    fun remoteGetCategory(){
        APIClient.instance.getCategory()
            .enqueue(object : Callback<KategoriMenu> {
                override fun onResponse(
                    call: Call<KategoriMenu>,
                    response: Response<KategoriMenu>
                ) {
                    val body = response.body()
                    Log.e("SimpleNetworking", Gson().toJson(body))
                    body?.let {
                        val data = body.data
                        val status = if (body.status != null) true else false
                        if (status) {
                            if (!data.isNullOrEmpty()) {
                                kategoriMenuAdapter.setData(data)
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<KategoriMenu>, t: Throwable) {
                    Log.e("SimpleNetworking", t.message.toString())
                }
            })
    }

    @SuppressLint("FragmentLiveDataObserve")
    fun remoteGetList(){
        viewModel.getAllCategory().observe(this){

            when (it.status){
                Status.SUCCESS -> {
                    Log.e("SimpleNetworking", Gson().toJson(it.data))
                    it.data?.data?.let { it1 -> menuAdapter.setData(it1) }
                }
                Status.ERROR -> {
                    Log.e("SimpleNetworking", it.message.toString())
                }
                Status.LOADING -> {
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        database = FirebaseDatabase.getInstance().getReference("user")

        if(uid.isNotEmpty()){
            getDataUser()
        }
    }

    private fun getDataUser() {
        database.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                binding.tvUsername.text = user.username
            }
            override fun onCancelled(error: DatabaseError) {}
        })
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
        menuAdapter = NewAdapter(this@HomeFragment,arrayListOf(), homeViewModel.isGrid.value ?: false, listener = { pickItem ->
                val bundle = bundleOf("pickItem" to pickItem)
                findNavController().navigate(R.id.action_homeFragment_to_detailMenuFragment, bundle)
        })
        binding.rvMenu.adapter = menuAdapter
        remoteGetList()
    }


    private fun grid() {
        binding.rvMenu.layoutManager = GridLayoutManager(requireActivity(), 2)
        menuAdapter = NewAdapter(this@HomeFragment,arrayListOf(), homeViewModel.isGrid.value ?: false, listener = { pickItem ->
            val bundle = bundleOf("pickItem" to pickItem)
            findNavController().navigate(R.id.action_homeFragment_to_detailMenuFragment, bundle)
        })
        binding.rvMenu.adapter = menuAdapter
        remoteGetList()
    }

}