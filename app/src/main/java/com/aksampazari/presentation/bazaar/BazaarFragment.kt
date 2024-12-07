package com.aksampazari.presentation.bazaar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aksampazari.R
import com.aksampazari.data.local.Category
import com.aksampazari.databinding.FragmentBazaarBinding
import com.aksampazari.presentation.bazaar.adapter.CategoryRecyclerViewAdapter

class BazaarFragment : Fragment() {

    private var _binding: FragmentBazaarBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBazaarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_category)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)

        fun generateCategoryList(): List<Category> {
            return listOf(
                Category("Taze Ürünler", R.drawable.fresh_image,
                    R.id.action_bazaarFragment_to_freshProductFragment),
                Category("Fırın Ürünleri", R.drawable.baker_image,
                    R.id.action_bazaarFragment_to_bakerProductFragment),
                Category("Süt ve Süt Ürünleri", R.drawable.dairy_image,
                    R.id.action_bazaarFragment_to_dairyProductFragment),
                Category("Şarküteri Ürünleri", R.drawable.delicatessen_image,
                    R.id.action_bazaarFragment_to_delicatessenProductFragment),
                Category("Organik Ürünler", R.drawable.organic_image,
                    R.id.action_bazaarFragment_to_organicProductFragment),
                Category("Deniz Ürünleri", R.drawable.seafood_image,
                    R.id.action_bazaarFragment_to_seafoodProductFragment),
                Category("Hazır Yemekler", R.drawable.ready_meals
                    , R.id.action_bazaarFragment_to_readyFoodProductFragment),
                Category("İçecekler", R.drawable.drinks_image,
                    R.id.action_bazaarFragment_to_drinksProductFragment)
            )
        }

        val categories = generateCategoryList()
        adapter = CategoryRecyclerViewAdapter(categories) { actionId ->
            findNavController().navigate(actionId)
        }
        recyclerView.adapter = adapter
    }
}