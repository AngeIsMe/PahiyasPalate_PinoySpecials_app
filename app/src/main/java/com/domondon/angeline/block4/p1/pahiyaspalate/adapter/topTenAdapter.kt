package com.domondon.angeline.block4.p1.pahiyaspalate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.domondon.angeline.block4.p1.pahiyaspalate.classes.Recipe
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.topTenDomain

class topTenAdapter : RecyclerView.Adapter<topTenAdapter.RecipeViewHolder>() {

    var recipes: List<topTenDomain> = emptyList()
    fun setData(topten: MutableList<topTenDomain>) {
        this.recipes = topten
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_holder, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentRecipe = recipes[position]

        holder.recipe_name.text = currentRecipe.name
        holder.recipe_category.text = currentRecipe.category
        holder.recipe_views.text = "${currentRecipe.views_count}"
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipe_name: TextView = itemView.findViewById(R.id.tv_recipename)
        val recipe_category: TextView = itemView.findViewById(R.id.tv_category)
        val recipe_views: TextView = itemView.findViewById(R.id.tv_viewsCount)
    }
}
