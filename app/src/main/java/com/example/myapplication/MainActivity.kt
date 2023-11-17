package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var clothesAdapter: ClothesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rvCategories: RecyclerView = binding.rvCategories
        val rvClothes: RecyclerView = binding.rvClothes

        val imageResIds = listOf(
            R.raw.girl1,
            R.raw.girl2,
            R.raw.girl3,
            R.raw.girl4,
            R.raw.girl5
        )

        imageResIds.map { getCategoryForImage(it) }

        clothesAdapter = ClothesAdapter(getFilteredClothes(getSampleCategories()[0], 2), imageResIds, getSampleCategories())
        rvClothes.adapter = clothesAdapter

        categoriesAdapter = CategoriesAdapter(getSampleCategories()) { selectedCategory ->
            val filteredClothes = getFilteredClothes(selectedCategory, itemCount = 2)
            val filteredImageResIds = getImagesForCategory(selectedCategory, imageResIds)
            val filteredCategoryList = getSampleCategories().filter { it.name == selectedCategory.name }
            clothesAdapter.updateClothesAndImages(filteredClothes, filteredImageResIds, filteredCategoryList)
        }

        rvCategories.adapter = categoriesAdapter

        rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvClothes.layoutManager = GridLayoutManager(this, 2)
    }

    private fun getCategoryForImage(imageResId: Int): String {

        return when (imageResId) {
            R.raw.girl1 -> "\uD83C\uDF89 Party"
            R.raw.girl2 -> "\uD83D\uDC54 Formal"
            R.raw.girl3 -> "❄️ Winter"
            R.raw.girl4 -> "\uD83D\uDC57 Trendy"
            R.raw.girl5 -> "\uD83C\uDFD5 Camping"
            else -> "Unknown"
        }
    }
    private fun getImagesForCategory(selectedCategory: Categories, imageResIds: List<Int>): List<Int> {
        return imageResIds.filterIndexed { index, _ ->
            getCategoryForImage(imageResIds[index]) == selectedCategory.name
        }
    }

    private fun getSampleCategories(): List<Categories> {
        return listOf(
            Categories("All", R.color.white, R.drawable.green_bg),
            Categories("\uD83C\uDF89 Party", R.color.lightGrey, R.drawable.grey_bg),
            Categories("\uD83C\uDFD5 Camping", R.color.lightGrey, R.drawable.grey_bg),
            Categories("\uD83D\uDC54 Formal", R.color.lightGrey, R.drawable.grey_bg),
            Categories("\uD83D\uDC57 Trendy", R.color.lightGrey, R.drawable.grey_bg),
            Categories("❄️ Winter", R.color.lightGrey, R.drawable.grey_bg)
        )
    }

    private fun getCategoryClothes(category: Categories): List<String> {
        return when (category.name) {
            "\uD83C\uDF89 Party" -> listOf("Clothes 1 for Party")
            "\uD83D\uDC54 Formal" -> listOf("Formal Clothes 1" )
            "❄️ Winter" -> listOf("Winter Clothes 1" )
            "\uD83C\uDFD5 Camping" -> listOf("Camping Clothes 1")
            "\uD83D\uDC57 Trendy" -> listOf("Trendy Clothes 1")

            else -> emptyList()
        }
    }

    private fun getFilteredClothes(selectedCategory: Categories, itemCount: Int): List<String> {
        return when (selectedCategory.name) {
            "All" -> {
                getSampleCategories().flatMap { getCategoryClothes(it) }
            }
            else -> {
                getCategoryClothes(selectedCategory)
            }
        }
    }
}


