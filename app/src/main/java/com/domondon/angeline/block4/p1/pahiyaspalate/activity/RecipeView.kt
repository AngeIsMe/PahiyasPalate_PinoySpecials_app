package com.domondon.angeline.block4.p1.pahiyaspalate.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class RecipeView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_view)

        //steps
        val stepsArrayString = intent.getStringExtra("steps")

        try {
            val stepsArray = JSONArray(stepsArrayString)

            val stepsList = mutableListOf<String>()
            for (i in 0 until stepsArray.length()) {
                val stepObject = stepsArray.getJSONObject(i)
                val instruction = stepObject.getString("instruction")
                stepsList.add(instruction)
            }

            val stepsListView: ListView = findViewById(R.id.stepsListView)

            // Use the custom layout for each step item
            val adapter = ArrayAdapter(this, R.layout.item_holder, R.id.ListTextView, stepsList)

            stepsListView.adapter = adapter
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        //ingredients
        val ingredientsArrayString = intent.getStringExtra("ingredients")

        try {
            val ingredientsArray = JSONArray(ingredientsArrayString)

            val stepsList = mutableListOf<String>()
            for (i in 0 until ingredientsArray.length()) {
                val stepObject = ingredientsArray.getJSONObject(i)
                val instruction = stepObject.getString("ingredient_name")
                stepsList.add(instruction)
            }

            val stepsListView: ListView = findViewById(R.id.ingredientsListView)

            // Use the custom layout for each step item
            val adapter = ArrayAdapter(this, R.layout.item_holder, R.id.ListTextView, stepsList)

            stepsListView.adapter = adapter
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val recipeID = intent.getStringExtra("id")
        val recipeName = intent.getStringExtra("recipe_name")
        val recipeCategory = intent.getStringExtra("category")
        val recipeDescription = intent.getStringExtra("recipe_description")
        val recipeViewsCount = intent.getStringExtra("views_count")
        val author = intent.getStringExtra("username")
        val imageUrl = intent.getStringExtra("recipe_image")

        val recipeNameTextView: TextView = findViewById(R.id.showRecipeName)
        val recipeCategoryTextView: TextView = findViewById(R.id.showRecipeCategory)
        val recipeDescriptionView: TextView = findViewById(R.id.showDescription)
        val recipeViewsView: TextView = findViewById(R.id.showViews)
        val authorView: TextView = findViewById(R.id.showUsername)
        val imageView: ImageView = findViewById(R.id.showImage)

        recipeNameTextView.text = recipeName
        recipeCategoryTextView.text = recipeCategory
        recipeDescriptionView.text = recipeDescription
        recipeViewsView.text = recipeViewsCount
        authorView.text = author
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.baseline_image_not_supported_24) // Optional placeholder image while loading
            .error(R.drawable.baseline_error_outline_24) // Optional error image if loading fails
            .into(imageView)
    }
}
