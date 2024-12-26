package com.aksampazari.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aksampazari.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfileBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.item_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navigateActionId = arguments?.getInt("navigateActionId")
        navigateActionId?.let {
            val navController = childFragmentManager.
            findFragmentById(R.id.nav_bottom_sheet_fragment)?.findNavController()
            navController?.navigate(it)
        }
    }

    companion object {
        fun newInstance(navigateActionId: Int): ProfileBottomSheet {
            return ProfileBottomSheet().apply {
                arguments = Bundle().apply {
                    putInt("navigateActionId", navigateActionId)
                }
            }
        }
    }
}
