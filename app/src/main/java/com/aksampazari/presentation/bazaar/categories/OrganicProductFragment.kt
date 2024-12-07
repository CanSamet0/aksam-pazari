package com.aksampazari.presentation.bazaar.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aksampazari.R
import com.aksampazari.databinding.FragmentOrganicProductBinding

class OrganicProductFragment : Fragment() {

    private var _biding: FragmentOrganicProductBinding ?= null
    private val binding get() = _biding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _biding = FragmentOrganicProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ibBackArrowOrganicFragment.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}