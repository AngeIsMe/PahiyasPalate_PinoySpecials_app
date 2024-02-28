import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.domondon.angeline.block4.p1.pahiyaspalate.adapter.topTenAdapter
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.topTenDomain
import org.json.JSONException
import org.json.JSONObject

class HomeFragment : Fragment() {
    private lateinit var toptenRecyclerView: RecyclerView
    private lateinit var toptenAdapter: topTenAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        toptenAdapter = topTenAdapter()
        toptenRecyclerView = view.findViewById(R.id.rc_top)
        toptenRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        toptenRecyclerView.adapter = toptenAdapter

        // Fetch data from the database
        getDataFromDatabase()
    }

    private fun getDataFromDatabase() {
        val url = "http://192.168.1.16/TestApp/getRecipe.php"
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())

        val request = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.e("TAG", "RESPONSE IS $response")
                try {
                    val jsonObject = JSONObject(response)

                    // Check if there is an error
                    if (jsonObject.getBoolean("error")) {
                        Toast.makeText(requireContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                    } else {
                        // Data retrieval successful
                        val recipes = mutableListOf<topTenDomain>()
                        val recipesArray = jsonObject.getJSONArray("recipes")

                        for (i in 0 until recipesArray.length()) {
                            val recipeObject = recipesArray.getJSONObject(i)
                            val name = recipeObject.getString("name")
                            val category = recipeObject.getString("category")
                            val views_count = recipeObject.getInt("views_count")

                            // Create a topTenDomain object
                            val recipe = topTenDomain(name, category, views_count)
                            recipes.add(recipe)
                        }

                        // Update the adapter with the fetched data
                        updateAdapterWithData(recipes)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(requireContext(), "Fail to get response = $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }
        }

        queue.add(request)
    }

    private fun updateAdapterWithData(recipes: MutableList<topTenDomain>) {
        toptenAdapter.setData(recipes)
        toptenAdapter.notifyDataSetChanged()
    }
}
