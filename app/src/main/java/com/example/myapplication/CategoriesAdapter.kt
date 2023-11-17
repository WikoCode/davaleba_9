package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CategoriesButtonsBinding

class CategoriesAdapter(
    private var categories: List<Categories>,
    private val onItemClickListener: (Categories) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>(){

    class CategoriesViewHolder(private val binding: CategoriesButtonsBinding) : RecyclerView.ViewHolder(binding.root) {
        val btnCategory = binding.btnCategory
        val tvCategoryName = binding.tvCategoryName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding = CategoriesButtonsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = categories[position]
        holder.tvCategoryName.text = category.name

        val drawableResId = when (position) {
            0 -> R.drawable.green_bg
            1 -> R.drawable.grey_bg
            else -> R.drawable.grey_bg
        }
        holder.btnCategory.setBackgroundResource(drawableResId)

        holder.tvCategoryName.setTextColor(ContextCompat.getColor(holder.itemView.context, category.textColorResId))

        holder.btnCategory.setOnClickListener {
            onItemClickListener.invoke(category)
        }
    }


    override fun getItemCount(): Int {
        return categories.size
    }

    fun updateCategories(newCategories: List<Categories>) {
        val diffResult = DiffUtil.calculateDiff(CategoriesDiffCallback(categories, newCategories))
        categories = newCategories
        diffResult.dispatchUpdatesTo(this)
    }

    private class CategoriesDiffCallback(
        private val oldList: List<Categories>,
        private val newList: List<Categories>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].name == newList[newItemPosition].name
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

}

