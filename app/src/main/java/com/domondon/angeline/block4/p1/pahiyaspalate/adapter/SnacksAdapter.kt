package com.domondon.angeline.block4.p1.pahiyaspalate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.LunchDomain
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.SnacksDomain

class SnacksAdapter : RecyclerView.Adapter<SnacksAdapter.RecipeViewHolder>() {

    private var category: List<SnacksDomain> = emptyList()

    fun setData(category: MutableList<SnacksDomain>) {
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
        holder.views.text = currentItem.views
    }

    override fun getItemCount(): Int {
        return category.size
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var recipeName: TextView
        lateinit var recipeCategory: TextView
        lateinit var views:TextView

        init {
            recipeName = itemView.findViewById(R.id.tv_recipename)
            recipeCategory = itemView.findViewById(R.id.tv_category)
            views = itemView.findViewById(R.id.tv_viewsCount)
        }
    }
}
