package com.domondon.angeline.block4.p1.pahiyaspalate.activity

import SharedPrefManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.domondon.angeline.block4.p1.pahiyaspalate.model.User
import com.domondon.angeline.block4.p1.pahiyaspalate.volley.SingletonVolley
import org.json.JSONException
import org.json.JSONObject

class Login : AppCompatActivity() {

    private lateinit var unameLogin: EditText
    private lateinit var passwordLogin: EditText
    private lateinit var registerActivity: TextView
    private lateinit var loginBTN: AppCompatButton
    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPrefManager = SharedPrefManager(this)
        requestQueue = SingletonVolley.getRequestQueue(this)

        registerActivity = findViewById(R.id.registerTV)
        unameLogin = findViewById(R.id.unameLogET)
        passwordLogin = findViewById(R.id.passLogET)
        loginBTN = findViewById(R.id.logBTN)

        registerActivity.setOnClickListener {
            val intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
            finish()
        }

        loginBTN.setOnClickListener {
            val username = unameLogin.text.toString().trim()
            val password = passwordLogin.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@Login, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                authenticateUser(username, password)
            }
        }
    }

    private fun authenticateUser(username: String, password: String) {
        val url = "https://pinoyspecials.pinoyspecialsrecipe.online/api/auth"

        val request = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val error = jsonObject.optBoolean("error", false)
                    val message = jsonObject.optString("message", "Authentication failed")

                    if (!error) {
                        val userJson = jsonObject.getJSONObject("data")
                        val userId = userJson.getInt("id")
                        val userName = userJson.getString("username")
                        val email = userJson.getString("email")
                        val user = User(userId, userName, email)
                        SharedPrefManager.getInstance(applicationContext).userLogin(user)
                        navigateToHome()
                    } else {
                        displayErrorMessage(message)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    displayErrorMessage("JSON parsing error")
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@Login, "Volley error: $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }

            override fun getParams(): MutableMap<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        requestQueue.add(request)
    }

    private fun navigateToHome() {
        val intent = Intent(this@Login, BottomNavBar::class.java)
        startActivity(intent)
        finish()
    }

    private fun displayErrorMessage(message: String) {
        Toast.makeText(this@Login, message, Toast.LENGTH_SHORT).show()
    }
}
