package com.domondon.angeline.block4.p1.pahiyaspalate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.BreakfastDomain

@Suppress("UNREACHABLE_CODE")
class BreakfastAdapter : RecyclerView.Adapter<BreakfastAdapter.RecipeViewHolder>() {

    private var category: List<BreakfastDomain> = emptyList()

    fun setData(category: MutableList<BreakfastDomain>) {
        this.category = category
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_holder, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentItem = category[position]
        holder.recipeName.text = currentItem.name
        holder.recipeCategory.text = currentItem.category
    }

    override fun getItemCount(): Int {
        return category.size
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeName: TextView = itemView.findViewById(R.id.tv_recipename)
        val recipeCategory: TextView = itemView.findViewById(R.id.tv_category)
    }
}
