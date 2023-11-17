package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemClothesBinding

class ClothesAdapter(
    private var clothes: List<String>,
    private var imageResIds: List<Int>,
    private var categoryList: List<Categories>

) : RecyclerView.Adapter<ClothesAdapter.ClothesViewHolder>(){

    class ClothesViewHolder(private val binding: ItemClothesBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgClothes = binding.imgClothes
        val tvName = binding.tvName
        val tvPrice = binding.tvPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothesViewHolder {
        val binding = ItemClothesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClothesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClothesViewHolder, position: Int) {
        val context = holder.itemView.context

        if (imageResIds.isNotEmpty()) {
            val imageUrl = imageResIds[position % imageResIds.size]
            holder.imgClothes.setImageResource(imageUrl)
        }

        holder.tvName.text = "Belt Suit Blazer"
        holder.tvPrice.text = "$120"
    }

    fun updateClothesAndImages(filteredClothes: List<String>, filteredImageResIds: List<Int>, filteredCategoryList: List<Categories>) {
        val diffCallback = ClothesDiffCallback(clothes, filteredClothes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.categoryList = filteredCategoryList // Update categoryList with the filtered list
        this.clothes = filteredClothes
        this.imageResIds = filteredImageResIds

        diffResult.dispatchUpdatesTo(this)
    }




    private fun getCategoryListFromNames(names: List<String>): List<Categories> {
        return categoryList.filter { it.name in names }
    }

    private class ClothesDiffCallback(
        private val oldList: List<String>,
        private val newList: List<String>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    override fun getItemCount(): Int {
        return clothes.size
    }

}
