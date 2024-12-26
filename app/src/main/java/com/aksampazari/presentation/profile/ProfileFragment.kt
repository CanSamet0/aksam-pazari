package com.aksampazari.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aksampazari.R
import com.aksampazari.data.local.ProfileSettings
import com.aksampazari.databinding.FragmentProfileBinding
import com.aksampazari.presentation.profile.adapter.SettingsRecyclerViewAdapter

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SettingsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvProfileSettings
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false)

        binding.ibEditProfile.setOnClickListener {
            val bottomSheet = ProfileBottomSheet()
            bottomSheet.show(parentFragmentManager, "ProfileBottomSheet")
        }

        fun generateSettingsList(): List<ProfileSettings>{
            return listOf(
                ProfileSettings(
                    getString(R.string.balance),
                    getString(R.string.default_balance),
                    R.id.action_settingsEditProfileFragment_to_settingsEditBalanceFragment
                ),
                ProfileSettings(
                    getString(R.string.order_history),
                    "",
                    R.id.action_settingsEditProfileFragment_to_settingsOrderHistoryFragment
                ),
                ProfileSettings(
                    getString(R.string.communication),
                    getString(R.string.default_communication),
                    R.id.action_settingsEditProfileFragment_to_settingsComminicationFragment
                ),
                ProfileSettings(
                    getString(R.string.address),
                    getString(R.string.default_address),
                    R.id.action_settingsEditProfileFragment_to_settingsAddressFragment
                ),
                ProfileSettings(
                    getString(R.string.notification),
                    "",
                    R.id.action_settingsEditProfileFragment_to_settingsNotificationFragment
                ),
                ProfileSettings(
                    getString(R.string.settings_password),
                    getString(R.string.default_settings_password),
                    R.id.action_settingsEditProfileFragment_to_settingsPasswordFragment
                ),
                ProfileSettings(
                    getString(R.string.delete_account),
                    "",
                    R.id.action_settingsEditProfileFragment_to_settingsDeleteAccountFragment
                ),
                ProfileSettings(
                    getString(R.string.exit_app),
                    "",
                    R.id.action_settingsEditProfileFragment_to_settingsExitAppFragment
                )

            )
        }

        val settings = generateSettingsList()
        adapter = SettingsRecyclerViewAdapter(settings) { actionId ->
            val bottomSheet = ProfileBottomSheet.newInstance(actionId)
            bottomSheet.show(parentFragmentManager, "ProfileBottomSheet")
        }
        recyclerView.adapter = adapter
    }
}