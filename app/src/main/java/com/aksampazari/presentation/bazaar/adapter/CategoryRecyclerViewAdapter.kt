package com.aksampazari.presentation.bazaar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aksampazari.data.local.Category
import com.aksampazari.databinding.ItemCategoryBinding

class CategoryRecyclerViewAdapter(
    private val categories: List<Category>,
    private val navigateToFragment: (Int) -> Unit):
    RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>()
{
    class ViewHolder(private val binding: ItemCategoryBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(
                categoryName: String,
                categoryImage: Int,
                navigateActionId: Int,
                navigateToFragment: (Int) -> Unit){
                binding.tvCategory.text = categoryName
                binding.ibCategory.setImageResource(categoryImage)
                binding.ibCategory.setOnClickListener {
                    navigateToFragment(navigateActionId)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryName = categories[position].categoryName
        val categoryImage= categories[position].categoryImage
        val categoryNavigationId = categories[position]

        holder.bind(
            categoryName,
            categoryImage,
            categoryNavigationId.navigateActionId,
            navigateToFragment)
    }
}