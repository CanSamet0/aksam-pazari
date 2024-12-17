package com.aksampazari.presentation.bazaar.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aksampazari.databinding.FragmentReadyFoodProductBinding

class ReadyFoodProductFragment : Fragment() {

    private var _binding: FragmentReadyFoodProductBinding ?= null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentReadyFoodProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ibBackArrowReadyFoodFragment.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}