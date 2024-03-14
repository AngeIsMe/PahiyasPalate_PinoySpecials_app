package com.domondon.angeline.block4.p1.pahiyaspalate

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    lateinit var usernameEdit: EditText
    lateinit var nameEdit: EditText

    lateinit var usernameView: TextView
    lateinit var nameView: TextView

    lateinit var validUser: EditText
    lateinit var validName: EditText

    var username: String = ""
    var name:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameEdit = findViewById(R.id.userEdit)
        nameEdit = findViewById(R.id.nameEdit)

        nameView = findViewById(R.id.nameView)
        usernameView = findViewById(R.id.usernameView)

        validUser= findViewById(R.id.validateUsername)
        validName = findViewById(R.id.validateName)

        var getBtn = findViewById<Button>(R.id.getdata)
        var submitBtn = findViewById<Button>(R.id.load)
        var checkbtn = findViewById<Button>(R.id.checkButton)

        submitBtn.setOnClickListener {
            username = usernameEdit.text.toString()
            name = nameEdit.text.toString()

            if (TextUtils.isEmpty(username) && TextUtils.isEmpty(name)) {
                usernameEdit.error = "Please enter username"
                nameEdit.error ="Please enter name"
            } else {
                addDataToDatabase(username,name)
            }
        }

        getBtn.setOnClickListener{
            getDataFromDatabase()
        }

        checkbtn.setOnClickListener{
            authenticateUser()
        }

    }

    private fun addDataToDatabase(username: String,name: String) {
        val url = "http://192.168.1.16/TestApp/add.php"
        val queue: RequestQueue = Volley.newRequestQueue(this)

        val request = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                Log.e("TAG", "RESPONSE IS $response")
                try {
                    val jsonObject = JSONObject(response)
                    Toast.makeText(this@MainActivity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                usernameEdit.setText("")
                nameEdit.setText("")
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@MainActivity, "Fail to get response = $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }

            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["username"] = username
                params["name"] = name
                return params
            }
        }

        queue.add(request)
    }

    private fun getDataFromDatabase() {
        val url = "http://192.168.1.16/Final-PS/get.php"
        val queue: RequestQueue = Volley.newRequestQueue(this)

        val request = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.e("TAG", "RESPONSE IS $response")
                try {
                    val jsonObject = JSONObject(response)

                    // Check if there is an error
                    if (jsonObject.getBoolean("error")) {
                        Toast.makeText(this@MainActivity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                    } else {
                        // Data retrieval successful
                        val usersArray = jsonObject.getJSONArray("users")
                        for (i in 0 until usersArray.length()) {
                            val userObject = usersArray.getJSONObject(i)
                            val username = userObject.getString("username")
                            val name = userObject.getString("name")

                            // Process the retrieved data as needed (e.g., display in UI)
                            usernameView.text = "$username"
                            nameView.text = "$name"
                            // You can use username and name as needed
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@MainActivity, "Fail to get response = $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }
        }

        queue.add(request)
    }

    private fun authenticateUser() {
        // Get values from EditText fields
        val username = validUser.text.toString()
        val name = validName.text.toString()

        // URL for the authentication PHP script
        val url = "http://192.168.1.16/TestApp/authenticate.php?username=$username&name=$name"

        // Create a new request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)

        // Define a StringRequest to make a GET request
        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    // Parse JSON response
                    val jsonObject = JSONObject(response)

                    // Check if there is an error
                    if (jsonObject.getBoolean("error")) {
                        Toast.makeText(this@MainActivity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                    } else {
                        // Authentication successful
                        val user = jsonObject.getJSONObject("user")
                        val authenticatedUsername = user.getString("username")
                        val authenticatedName = user.getString("name")

                        // Display the authenticated user information

                        nameView.text = authenticatedName
                        usernameView.text = authenticatedUsername
                        Toast.makeText(
                            this@MainActivity,
                            "Authentication successful!\nUsername: $authenticatedUsername\nName: $authenticatedName",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                // Handle errors
                Toast.makeText(this@MainActivity, "Fail to get response = $error", Toast.LENGTH_SHORT).show()
            })

        // Add the request to the request queue
        queue.add(request)
    }

}
