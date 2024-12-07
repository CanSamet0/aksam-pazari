package com.aksampazari.presentation.bazaar.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aksampazari.R
import com.aksampazari.databinding.FragmentDelicatessenProductBinding

class DelicatessenProductFragment : Fragment() {

    private var _binding: FragmentDelicatessenProductBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDelicatessenProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ibBackArrowDelicatessenFragment.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}