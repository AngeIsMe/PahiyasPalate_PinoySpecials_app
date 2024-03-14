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
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.ViewsDomain
import com.domondon.angeline.block4.p1.pahiyaspalate.view_model.RecipeViewModel
import org.json.JSONException
import org.json.JSONObject

class mostViewsAdapter(private val context: Context, private val viewModel: RecipeViewModel) : RecyclerView.Adapter<mostViewsAdapter.RecipeViewHolder>()  {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipe_name: TextView = itemView.findViewById(R.id.tv_recipename)
        val recipe_category: TextView = itemView.findViewById(R.id.tv_category)
        val views: TextView = itemView.findViewById(R.id.tv_viewsCount)
    }

    private var viewsList: List<ViewsDomain> = emptyList()

    fun setData(viewsList: List<ViewsDomain>) {
        this.viewsList = viewsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_holder, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return viewsList.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentView = viewsList[position]

        holder.recipe_name.text = currentView.name
        holder.recipe_category.text = currentView.category
        holder.views.text = currentView.views

        // Inside onBindViewHolder of your adapter
        holder.itemView.setOnClickListener {
            // Get the clicked item position
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                // Get the corresponding view object
                val view = viewsList[position]

                incrementViewsCount(view.id)

                // Start the RecipeView activity and pass necessary data
                val intent = Intent(context, RecipeView::class.java).apply {
                    putExtra("id", view.id)
                    putExtra("recipe_name", view.name)
                    putExtra("category", view.category)
                    putExtra("recipe_description", view.recipe_description)
                    putExtra("steps", view.steps)
                    putExtra("ingredients", view.ingredients)
                    putExtra("views", view.views)
                }
                context.startActivity(intent)
            }
        }
    }

    private fun incrementViewsCount(recipeId: String) {
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val url = "https://pinoyspecials-app.pinoyspecialsrecipe.online/api/recipe/$recipeId/increment-views"

        val request = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                // Handle the response if needed
                Log.d("TAG", "Response is $response")
                try {
                    val jsonObject = JSONObject(response)
                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                // Handle errors if any
                Toast.makeText(context, "Failed to increment views: $error", Toast.LENGTH_SHORT).show()
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
