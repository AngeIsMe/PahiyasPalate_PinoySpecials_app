package com.domondon.angeline.block4.p1.pahiyaspalate.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.domondon.angeline.block4.p1.pahiyaspalate.adapter.topTenAdapter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class AddRecipeFragment : Fragment() {

    private lateinit var Input_recipe_name: EditText
    private lateinit var Input_recipe_category: EditText
    private lateinit var Input_recipe_description: EditText
    private lateinit var Input_recipe_steps: EditText
    private lateinit var Input_recipe_ingredients: EditText
    private lateinit var submit: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_recipe, container, false)

        // Initialize input fields
        Input_recipe_name = view.findViewById(R.id.recipe_name)
        Input_recipe_category = view.findViewById(R.id.category)
        Input_recipe_description = view.findViewById(R.id.recipe_description)
        Input_recipe_steps = view.findViewById(R.id.steps)
        Input_recipe_ingredients = view.findViewById(R.id.ingredients)
        submit = view.findViewById(R.id.submit)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submit.setOnClickListener {
            // Retrieve input values
            val recipe_name = Input_recipe_name.text.toString().trim()
            val category = Input_recipe_category.text.toString().trim()
            val recipe_description = Input_recipe_description.text.toString().trim()
            val steps = Input_recipe_steps.text.toString().trim()
            val ingredients = Input_recipe_ingredients.text.toString().trim()

            // Check if any field is empty
            if (TextUtils.isEmpty(recipe_name) || TextUtils.isEmpty(category) ||
                TextUtils.isEmpty(recipe_description) || TextUtils.isEmpty(steps) ||
                TextUtils.isEmpty(ingredients)) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Add recipe
            addRecipe(recipe_name, category, recipe_description, steps, ingredients)
        }
    }

    private fun addRecipe(
        recipe_name: String,
        category: String,
        recipe_description: String,
        steps: String,
        ingredients: String
    ) {
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val url = "https://pinoyspecials-app.pinoyspecialsrecipe.online/api/create-recipe"

        val request = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                Log.e("TAG", "Response is $response")
                try {
                    val jsonObject = JSONObject(response)
                    Toast.makeText(requireContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                // Clear input fields after successfully adding recipe
                Input_recipe_name.setText("")
                Input_recipe_category.setText("")
                Input_recipe_description.setText("")
                Input_recipe_steps.setText("")
                Input_recipe_ingredients.setText("")
            },
            Response.ErrorListener { error ->
                Toast.makeText(requireContext(), "Fail to get response = $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }

            override fun getParams(): MutableMap<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["recipe_name"] = recipe_name
                params["category"] = category
                params["recipe_description"] = recipe_description

                // Convert steps and ingredients to JSON arrays
                params["steps"] = steps
                params["ingredients"] = ingredients
                return params
            }
        }
        queue.add(request)
    }
}