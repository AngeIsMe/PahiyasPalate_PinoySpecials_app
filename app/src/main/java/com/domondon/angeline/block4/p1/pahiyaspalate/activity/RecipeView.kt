package com.domondon.angeline.block4.p1.pahiyaspalate.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.topTenDomain
import org.json.JSONArray

class RecipeView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_view)


        val recipeID = intent.getStringExtra("id")
        val recipeName = intent.getStringExtra("recipe_name")
        val recipeCategory = intent.getStringExtra("category")
        val recipeDescription = intent.getStringExtra("recipe_description")
        val recipeSteps = intent.getStringExtra("steps")
        val recipeIngredients = intent.getStringExtra("ingredients")
        val recipeViewsCount = intent.getStringExtra("views")

        val recipeNameTextView: TextView = findViewById(R.id.showRecipeName)
        val recipeCategoryTextView: TextView = findViewById(R.id.showRecipeCategory)
        val recipeDescriptionView: TextView = findViewById(R.id.showDescription)
        val recipeStepsView: TextView = findViewById(R.id.showSteps)
        val recipeIngredientView: TextView = findViewById(R.id.showIngredients)
        val recipeViewsView: TextView = findViewById(R.id.showViews)

        recipeNameTextView.text = recipeName
        recipeCategoryTextView.text = recipeCategory
        recipeDescriptionView.text = recipeDescription
        recipeStepsView.text = recipeSteps
        recipeIngredientView.text = recipeIngredients
        recipeViewsView.text = recipeViewsCount

    }
}
