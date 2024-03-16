import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.domondon.angeline.block4.p1.pahiyaspalate.adapter.mostViewsAdapter
import com.domondon.angeline.block4.p1.pahiyaspalate.adapter.topTenAdapter
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.BreakfastDomain
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.ViewsDomain
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.topTenDomain
import com.domondon.angeline.block4.p1.pahiyaspalate.view_model.RecipeViewModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class HomeFragment : Fragment() {
    private lateinit var toptenRecyclerView: RecyclerView
    private lateinit var mostViews: RecyclerView
    private lateinit var toptenAdapter: topTenAdapter
    private lateinit var mostViewsAdapter: mostViewsAdapter
    private lateinit var viewModel: RecipeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Set up RecyclerView
        viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        toptenAdapter = topTenAdapter(requireContext(), viewModel)
        mostViewsAdapter = mostViewsAdapter(requireContext(), viewModel)

        toptenRecyclerView = view.findViewById(R.id.rc_top)
        toptenRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        toptenRecyclerView.adapter = toptenAdapter

        mostViews = view.findViewById(R.id.rc_mostview)
        mostViews.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mostViews.adapter = mostViewsAdapter

        // Fetch data from the database
        getDataFromDatabaseRecent()
        mostViewedData()
    }

    private fun getDataFromDatabaseRecent() {
        val url = "https://pinoyspecials.pinoyspecialsrecipe.online/api/recent"
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())

        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.e("TAG", "RESPONSE IS $response")
                try {
                    val jsonObject = JSONObject(response)

                    if (jsonObject.getBoolean("status")) {
                        val recipeArray = jsonObject.getJSONArray("recipe")

                        // Data retrieval successful
                        val recipes = mutableListOf<topTenDomain>()

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

                            val recipe = topTenDomain(id, name, category, recipeDescription, level, steps, ingredients, views,author,imageUrl)
                            recipes.add(recipe)
                        }

                        updateRecent(recipes)
                    } else {
                        Log.e("TAG", "Status is false")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Fail to get response = $error", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }


    private fun mostViewedData() {
        val url = "https://pinoyspecials.pinoyspecialsrecipe.online/api/views"
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())

        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.e("TAG", "RESPONSE IS $response")
                try {
                    val jsonObject = JSONObject(response)

                    if (jsonObject.getBoolean("status")) {
                        val recipeArray = jsonObject.getJSONArray("recipe")

                        // Data retrieval successful
                        val recipes = mutableListOf<ViewsDomain>()

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

                            val recipe = ViewsDomain(id, name, category, recipeDescription, level, steps, ingredients, views,author,imageUrl)
                            recipes.add(recipe)
                        }

                        updateViews(recipes)
                    } else {
                        Log.e("TAG", "Status is false")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Fail to get response = $error", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }

    private fun updateRecent(recipes: List<topTenDomain>) {
        toptenAdapter.setData(recipes)
        toptenAdapter.notifyDataSetChanged()
    }
    private fun updateViews(recipes: List<ViewsDomain>){
        mostViewsAdapter.setData(recipes)
        mostViewsAdapter.notifyDataSetChanged()
    }
}
