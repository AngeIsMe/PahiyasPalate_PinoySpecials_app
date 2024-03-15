package com.domondon.angeline.block4.p1.pahiyaspalate.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import org.json.JSONException
import org.json.JSONObject

class Login : AppCompatActivity() {

    private lateinit var unameLogin: EditText
    private lateinit var passwordLogin: EditText
    private lateinit var registerActivity: TextView
    private lateinit var loginBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
        val url = "https://pinoyspecials-app.pinoyspecialsrecipe.online/api/login"
        val queue: RequestQueue = Volley.newRequestQueue(this)

        val request = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val error = jsonObject.optBoolean("error", false) // Default value in case key is missing or not a boolean
                    val message = jsonObject.getString("message")

                    if (!error) {
                        // Authentication successful, navigate to home activity
                        val intent = Intent(this@Login, BottomNavBar::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Authentication failed, display error message
                        Toast.makeText(this@Login, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this@Login, "JSON parsing error", Toast.LENGTH_SHORT).show()
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
        queue.add(request)
    }
}
