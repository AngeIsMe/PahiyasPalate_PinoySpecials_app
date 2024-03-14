package com.domondon.angeline.block4.p1.pahiyaspalate.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.domondon.angeline.block4.p1.pahiyaspalate.activity.BottomNavBar
import com.domondon.angeline.block4.p1.pahiyaspalate.adapter.BreakfastAdapter
import com.domondon.angeline.block4.p1.pahiyaspalate.adapter.DinnerAdapter
import com.domondon.angeline.block4.p1.pahiyaspalate.adapter.LunchAdapter
import com.domondon.angeline.block4.p1.pahiyaspalate.adapter.SnacksAdapter
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.BreakfastDomain
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.DinnerDomain
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.LunchDomain
import org.json.JSONArray
import org.json.JSONException


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        breakfastRecycler = view.findViewById(R.id.breakfast_rc)
        breakfastAdapter = BreakfastAdapter()
        breakfastRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        breakfastRecycler.adapter = breakfastAdapter

        lunchRecycler = view.findViewById(R.id.lunch_rc)
        lunchAdapter = LunchAdapter()
        lunchRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        lunchRecycler.adapter = lunchAdapter

        dinnerRecycler = view.findViewById(R.id.dinner_rc)
        dinnerAdapter = DinnerAdapter()
        dinnerRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dinnerRecycler.adapter = dinnerAdapter

        getBreakfastCategory()
        getLunchCategory()
        getDinnerCategory()
    }

    private fun getBreakfastCategory()
    {
        val url = "https://pinoyspecials-app.pinoyspecialsrecipe.online/api/breakfast"
        val queue: RequestQueue = Volley.newRequestQueue(context)

        val request = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("Response", "Response: $response")
                try {
                    val jsonArray = JSONArray(response)
                    val recipes = mutableListOf<BreakfastDomain>()

                    for (i in 0 until jsonArray.length()) {
                        val recipeObject = jsonArray.getJSONObject(i)
                        val id = recipeObject.getString("id")
                        val name = recipeObject.getString("recipe_name")
                        val category = recipeObject.getString("category")
                        val recipeDescription = recipeObject.getString("recipe_description")
                        val steps = recipeObject.getString("steps")
                        val ingredients = recipeObject.getString("ingredients")
                        val views = recipeObject.getString("views")

                        val recipe = BreakfastDomain(id, name, category, recipeDescription, steps, ingredients, views)
                        recipes.add(recipe)
                    }
                    updateAdapterWithDataBreakfast(recipes)
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
        val url = "https://pinoyspecials-app.pinoyspecialsrecipe.online/api/lunch"
        val queue: RequestQueue = Volley.newRequestQueue(context)

        val request = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("Response", "Response: $response")
                try {
                    val jsonArray = JSONArray(response)
                    val recipes = mutableListOf<LunchDomain>()

                    for (i in 0 until jsonArray.length()) {
                        val recipeObject = jsonArray.getJSONObject(i)
                        val id = recipeObject.getString("id")
                        val name = recipeObject.getString("recipe_name")
                        val category = recipeObject.getString("category")
                        val recipeDescription = recipeObject.getString("recipe_description")
                        val steps = recipeObject.getString("steps")
                        val ingredients = recipeObject.getString("ingredients")
                        val views = recipeObject.getString("views")

                        val recipe = LunchDomain(id, name, category, recipeDescription, steps, ingredients, views)
                        recipes.add(recipe)
                    }
                    updateAdapterWithDataLunch(recipes)
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
        val url = "https://pinoyspecials-app.pinoyspecialsrecipe.online/api/dinner"
        val queue: RequestQueue = Volley.newRequestQueue(context)

        val request = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("Response", "Response: $response")
                try {
                    val jsonArray = JSONArray(response)
                    val recipes = mutableListOf<DinnerDomain>()

                    for (i in 0 until jsonArray.length()) {
                        val recipeObject = jsonArray.getJSONObject(i)
                        val id = recipeObject.getString("id")
                        val name = recipeObject.getString("recipe_name")
                        val category = recipeObject.getString("category")
                        val recipeDescription = recipeObject.getString("recipe_description")
                        val steps = recipeObject.getString("steps")
                        val ingredients = recipeObject.getString("ingredients")
                        val views = recipeObject.getString("views")

                        val recipe = DinnerDomain(id, name, category, recipeDescription, steps, ingredients, views)
                        recipes.add(recipe)
                    }
                    updateAdapterWithDataDinner(recipes)
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
}
