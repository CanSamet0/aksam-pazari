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
    ): View{
        _binding = FragmentBazaarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvCategory
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)

        fun generateCategoryList(): List<Category> {
            return listOf(
                Category(
                    getText(R.string.fresh_products).toString(), R.drawable.fresh_image,
                    R.id.action_bazaarFragment_to_freshProductFragment),
                Category(
                    getText(R.string.baker_product).toString(), R.drawable.baker_image,
                    R.id.action_bazaarFragment_to_bakerProductFragment),
                Category(
                    getText(R.string.dairy_product).toString(), R.drawable.dairy_image,
                    R.id.action_bazaarFragment_to_dairyProductFragment),
                Category(
                    getText(R.string.delicatessen_product).toString(), R.drawable.delicatessen_image,
                    R.id.action_bazaarFragment_to_delicatessenProductFragment),
                Category(
                    getText(R.string.organic_product).toString(), R.drawable.organic_image,
                    R.id.action_bazaarFragment_to_organicProductFragment),
                Category(
                    getText(R.string.seafood_product).toString(), R.drawable.seafood_image,
                    R.id.action_bazaarFragment_to_seafoodProductFragment),
                Category(
                    getText(R.string.ready_food_product).toString(), R.drawable.ready_meals
                    , R.id.action_bazaarFragment_to_readyFoodProductFragment),
                Category(
                    getText(R.string.drinks_product).toString(), R.drawable.drinks_image,
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