package com.domondon.angeline.block4.p1.pahiyaspalate.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.domondon.angeline.block4.p1.pahiyaspalate.adapter.BreakfastAdapter
import com.domondon.angeline.block4.p1.pahiyaspalate.adapter.DinnerAdapter
import com.domondon.angeline.block4.p1.pahiyaspalate.adapter.LunchAdapter
import com.domondon.angeline.block4.p1.pahiyaspalate.adapter.SnacksAdapter
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.BreakfastDomain
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.DinnerDomain
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.LunchDomain
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.SnacksDomain
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.topTenDomain
import com.domondon.angeline.block4.p1.pahiyaspalate.view_model.BreakfastViewModel
import com.domondon.angeline.block4.p1.pahiyaspalate.view_model.DinnerViewModel
import com.domondon.angeline.block4.p1.pahiyaspalate.view_model.LunchViewModel
import com.domondon.angeline.block4.p1.pahiyaspalate.view_model.RecipeViewModel
import com.domondon.angeline.block4.p1.pahiyaspalate.view_model.SnacksViewModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesFragment : Fragment() {

    private lateinit var breakfastRecycler: RecyclerView
    private lateinit var breakfastAdapter: BreakfastAdapter
    private lateinit var lunchRecycler: RecyclerView
    private lateinit var lunchAdapter: LunchAdapter
    private lateinit var dinnerRecycler: RecyclerView
    private lateinit var dinnerAdapter: DinnerAdapter
    private lateinit var snacksRecycler: RecyclerView
    private lateinit var snacksAdapter: SnacksAdapter
    private lateinit var breakfastViewModel: BreakfastViewModel
    private lateinit var lunchViewModel: LunchViewModel
    private lateinit var dinnerViewModel: DinnerViewModel
    private lateinit var snacksViewModel: SnacksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        breakfastViewModel = ViewModelProvider(this).get(BreakfastViewModel::class.java)
        lunchViewModel = ViewModelProvider(this).get(LunchViewModel::class.java)
        dinnerViewModel = ViewModelProvider(this).get(DinnerViewModel::class.java)
        snacksViewModel = ViewModelProvider(this).get(SnacksViewModel::class.java)
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        breakfastRecycler = view.findViewById(R.id.breakfast_rc)
        breakfastAdapter = BreakfastAdapter(requireContext(), breakfastViewModel)
        breakfastRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        breakfastRecycler.adapter = breakfastAdapter

        lunchRecycler = view.findViewById(R.id.lunch_rc)
        lunchAdapter = LunchAdapter(requireContext(),lunchViewModel)
        lunchRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        lunchRecycler.adapter = lunchAdapter

        dinnerRecycler = view.findViewById(R.id.dinner_rc)
        dinnerAdapter = DinnerAdapter(requireContext(),dinnerViewModel)
        dinnerRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dinnerRecycler.adapter = dinnerAdapter

        snacksRecycler = view.findViewById(R.id.snacks_rc)
        snacksAdapter = SnacksAdapter(requireContext(),snacksViewModel)
        snacksRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        snacksRecycler.adapter = snacksAdapter

        getBreakfastCategory()
        getLunchCategory()
        getDinnerCategory()
        getSnacksCategory()
    }

    private fun getBreakfastCategory() {
        val url = "https://pinoyspecials.pinoyspecialsrecipe.online/api/breakfast"
        val queue: RequestQueue = Volley.newRequestQueue(context)

        val request = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("Response", "Response: $response")
                try {
                    // Parse the response as a JSONObject
                    val jsonObject = JSONObject(response)

                    // Check if the "status" key is true
                    if (jsonObject.getBoolean("status")) {
                        // Get the "recipe" array from the JSON object
                        val recipeArray = jsonObject.getJSONArray("recipe")

                        // Data retrieval successful
                        val recipes = mutableListOf<BreakfastDomain>()

                        // Iterate over the JSONArray to extract data for each recipe
                        for (i in 0 until recipeArray.length()) {
                            val recipeObject = recipeArray.getJSONObject(i)
                            val id = recipeObject.getString("id")
                            val author = recipeObject.getString("username")
                            val name = recipeObject.getString("recipe_name")
                            val imageUrl = recipeObject.getString("recipe_image")
                            val category = recipeObject.getString("category")
                            val recipeDescription = recipeObject.getString("recipe_description")
                            val level = recipeObject.getString("level")
                            val steps = recipeObject.getString("steps")
                            val ingredients = recipeObject.getString("ingredients")
                            val views = recipeObject.getString("views_count")

                            val recipe = BreakfastDomain(id, name, category, recipeDescription, level, steps, ingredients, views,author,imageUrl)
                            recipes.add(recipe)
                        }

                        updateAdapterWithDataBreakfast(recipes)
                    } else {
                        Log.e("TAG", "Status is false")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_SHORT).show()
                Log.e("Error", "Volley error: $error")
            }) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }
        }
        queue.add(request)
    }

    private fun updateAdapterWithDataBreakfast(recipes: MutableList<BreakfastDomain>)
    {
        breakfastAdapter.setData(recipes)
        breakfastAdapter.notifyDataSetChanged()
    }
    private fun getLunchCategory()
    {
        val url = "https://pinoyspecials.pinoyspecialsrecipe.online/api/lunch"
        val queue: RequestQueue = Volley.newRequestQueue(context)

        val request = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("Response", "Response: $response")
                try {
                    // Parse the response as a JSONObject
                    val jsonObject = JSONObject(response)

                    // Check if the "status" key is true
                    if (jsonObject.getBoolean("status")) {
                        // Get the "recipe" array from the JSON object
                        val recipeArray = jsonObject.getJSONArray("recipe")

                        // Data retrieval successful
                        val recipes = mutableListOf<LunchDomain>()

                        // Iterate over the JSONArray to extract data for each recipe
                        for (i in 0 until recipeArray.length()) {
                            val recipeObject = recipeArray.getJSONObject(i)
                            val id = recipeObject.getString("id")
                            val author = recipeObject.getString("username")
                            val name = recipeObject.getString("recipe_name")
                            val imageUrl = recipeObject.getString("recipe_image")
                            val category = recipeObject.getString("category")
                            val recipeDescription = recipeObject.getString("recipe_description")
                            val level = recipeObject.getString("level")
                            val steps = recipeObject.getString("steps")
                            val ingredients = recipeObject.getString("ingredients")
                            val views = recipeObject.getString("views_count")

                            val recipe = LunchDomain(id, name, category, recipeDescription, level, steps, ingredients, views,author,imageUrl)
                            recipes.add(recipe)
                        }
                        updateAdapterWithDataLunch(recipes)
                    } else {
                        // Handle the case where the "status" key is false
                        Log.e("Error", "Status is false")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_SHORT).show()
                Log.e("Error", "Volley error: $error")
            }) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }
        }
        queue.add(request)
    }
    private fun updateAdapterWithDataLunch(recipes: MutableList<LunchDomain>)
    {
        lunchAdapter.setData(recipes)
        lunchAdapter.notifyDataSetChanged()
    }

    private fun getDinnerCategory()
    {
        val url = "https://pinoyspecials.pinoyspecialsrecipe.online/api/dinner"
        val queue: RequestQueue = Volley.newRequestQueue(context)

        val request = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("Response", "Response: $response")
                try {
                    // Parse the response as a JSONObject
                    val jsonObject = JSONObject(response)

                    // Check if the "status" key is true
                    if (jsonObject.getBoolean("status")) {
                        // Get the "recipe" array from the JSON object
                        val recipeArray = jsonObject.getJSONArray("recipe")

                        // Data retrieval successful
                        val recipes = mutableListOf<DinnerDomain>()

                        // Iterate over the JSONArray to extract data for each recipe
                        for (i in 0 until recipeArray.length()) {
                            val recipeObject = recipeArray.getJSONObject(i)
                            val id = recipeObject.getString("id")
                            val author = recipeObject.getString("username")
                            val name = recipeObject.getString("recipe_name")
                            val imageUrl = recipeObject.getString("recipe_image")
                            val category = recipeObject.getString("category")
                            val recipeDescription = recipeObject.getString("recipe_description")
                            val level = recipeObject.getString("level")
                            val steps = recipeObject.getString("steps")
                            val ingredients = recipeObject.getString("ingredients")
                            val views = recipeObject.getString("views_count")

                            val recipe = DinnerDomain(id, name, category, recipeDescription, level, steps, ingredients, views,author,imageUrl)
                            recipes.add(recipe)
                        }
                        updateAdapterWithDataDinner(recipes)
                    } else {
                        // Handle the case where the "status" key is false
                        Log.e("Error", "Status is false")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_SHORT).show()
                Log.e("Error", "Volley error: $error")
            }) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }
        }
        queue.add(request)
    }
    private fun updateAdapterWithDataDinner(recipes: MutableList<DinnerDomain>)
    {
        dinnerAdapter.setData(recipes)
        dinnerAdapter.notifyDataSetChanged()
    }
    private fun getSnacksCategory()
    {
        val url = "https://pinoyspecials.pinoyspecialsrecipe.online/api/snacks"
        val queue: RequestQueue = Volley.newRequestQueue(context)

        val request = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("Response", "Response: $response")
                try {
                    // Parse the response as a JSONObject
                    val jsonObject = JSONObject(response)

                    // Check if the "status" key is true
                    if (jsonObject.getBoolean("status")) {
                        // Get the "recipe" array from the JSON object
                        val recipeArray = jsonObject.getJSONArray("recipe")

                        // Data retrieval successful
                        val recipes = mutableListOf<SnacksDomain>()

                        // Iterate over the JSONArray to extract data for each recipe
                        for (i in 0 until recipeArray.length()) {
                            val recipeObject = recipeArray.getJSONObject(i)
                            val id = recipeObject.getString("id")
                            val author = recipeObject.getString("username")
                            val name = recipeObject.getString("recipe_name")
                            val imageUrl = recipeObject.getString("recipe_image")
                            val category = recipeObject.getString("category")
                            val recipeDescription = recipeObject.getString("recipe_description")
                            val level = recipeObject.getString("level")
                            val steps = recipeObject.getString("steps")
                            val ingredients = recipeObject.getString("ingredients")
                            val views = recipeObject.getString("views_count")

                            val recipe = SnacksDomain(id, name, category, recipeDescription, level, steps, ingredients, views,author,imageUrl)
                            recipes.add(recipe)
                        }
                        updateAdapterWithDataSnacks(recipes)
                    } else {
                        // Handle the case where the "status" key is false
                        Log.e("Error", "Status is false")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_SHORT).show()
                Log.e("Error", "Volley error: $error")
            }) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }
        }
        queue.add(request)
    }
    private fun updateAdapterWithDataSnacks(recipes: MutableList<SnacksDomain>)
    {
        snacksAdapter.setData(recipes)
        snacksAdapter.notifyDataSetChanged()
    }
}
