package com.aksampazari.presentation.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.aksampazari.data.local.ProfileSettings
import com.aksampazari.databinding.ItemProfileSettingsBinding
import com.aksampazari.presentation.profile.ProfileBottomSheet

class SettingsRecyclerViewAdapter(
    private val profileSettingsList: List<ProfileSettings>,
    private val navigateToFragment: (Int) -> Unit):
    RecyclerView.Adapter<SettingsRecyclerViewAdapter.ViewHolder>(){

    class ViewHolder(private val binding: ItemProfileSettingsBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(
            settingsName: String,
            settingsDescription: String,
            navigateActionId: Int,
            showBottomSheet: (Int) -> Unit){
            binding.defaultSettingsName.text = settingsName
            binding.defaultSettingsDescription.text = settingsDescription
            binding.ibDetail.setOnClickListener {
                showBottomSheet(navigateActionId)
            }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding = ItemProfileSettingsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val settings = profileSettingsList[position]
        holder.bind(
            settings.settingsName,
            settings.settingsDescription,
            settings.navigateActionId
        ) { actionId ->
            val bottomSheet = ProfileBottomSheet.newInstance(actionId)
            bottomSheet.show((holder.itemView.context as FragmentActivity)
                .supportFragmentManager, "ProfileBottomSheet")
        }
    }

    override fun getItemCount(): Int {
        return profileSettingsList.size
    }
}