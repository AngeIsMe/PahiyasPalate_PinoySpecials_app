package com.domondon.angeline.block4.p1.pahiyaspalate.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.domondon.angeline.block4.p1.pahiyaspalate.activity.RecipeView
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.DinnerDomain
import com.domondon.angeline.block4.p1.pahiyaspalate.view_model.DinnerViewModel
import org.json.JSONException
import org.json.JSONObject

class DinnerAdapter(private val context: Context, private val viewModel: DinnerViewModel) : RecyclerView.Adapter<DinnerAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipe_name: TextView = itemView.findViewById(R.id.tv_recipename)
        val recipe_category: TextView = itemView.findViewById(R.id.tv_category)
        val views: TextView = itemView.findViewById(R.id.tv_viewsCount)
        val recipedescription: TextView = itemView.findViewById(R.id.tv_description)
    }

    init {
        viewModel.recipes.observeForever { recipes ->
            recipes?.let {
                setData(it)
            }
        }
    }

    private var recipes: List<DinnerDomain> = emptyList()

    fun setData(Dinner: List<DinnerDomain>) {
        this.recipes = Dinner
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_holder, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentRecipe = recipes[position]

        holder.recipe_name.text = currentRecipe.name
        holder.recipe_category.text = currentRecipe.category
        holder.views.text = currentRecipe.views
        holder.recipedescription.text = currentRecipe.recipe_description

        // Inside onBindViewHolder of your adapter
        holder.itemView.setOnClickListener {
            // Get the clicked item position
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                // Get the corresponding recipe object
                val recipe = recipes[adapterPosition]

                // Start the RecipeView activity and pass necessary data
                val intent = Intent(context, RecipeView::class.java).apply {
                    putExtra("id", recipe.id)
                    putExtra("recipe_name", recipe.name)
                    putExtra("category", recipe.category)
                    putExtra("recipe_description", recipe.recipe_description)
                    putExtra("views_count", recipe.views)
                    putExtra("username",recipe.author)
                    putExtra("steps",recipe.steps)
                    putExtra("ingredients",recipe.ingredients)
                    putExtra("recipe_image", recipe.imageUrl)
                }
                context.startActivity(intent)

                // Increment views_count for the clicked recipe
                incrementViewsCount(recipe.id)
            }
        }
    }

    private fun incrementViewsCount(recipeId: String) {
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val url = "https://pinoyspecials.pinoyspecialsrecipe.online/api/show/$recipeId"

        val request = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Handle the response if needed
                Log.d("TAG", "Response is $response")
                try {
                    val jsonObject = JSONObject(response)
                    Toast.makeText(context, jsonObject.getString("success"), Toast.LENGTH_SHORT).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                // Handle errors if any
                Toast.makeText(context, "Failed to view Recipe: $error", Toast.LENGTH_SHORT).show()
            }) {
            // Override the getBodyContentType method to set the content type
            override fun getBodyContentType(): String {
                return "application/json"
            }
        }

        // Add the request to the request queue
        queue.add(request)
    }
}
