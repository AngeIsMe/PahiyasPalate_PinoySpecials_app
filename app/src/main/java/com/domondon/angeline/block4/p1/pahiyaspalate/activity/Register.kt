package com.domondon.angeline.block4.p1.pahiyaspalate.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import org.json.JSONException
import org.json.JSONObject

class Register : AppCompatActivity() {
    private lateinit var usernameET: EditText
    private lateinit var emailET: EditText
    private lateinit var passwordET: EditText
    private lateinit var confirmPassET: EditText
    private lateinit var loginTV: TextView
    private lateinit var registerBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameET = findViewById(R.id.usernameET)
        emailET = findViewById(R.id.emailET)
        passwordET = findViewById(R.id.passwordET)
        confirmPassET = findViewById(R.id.confirmPassET)
        registerBTN = findViewById(R.id.registerBTN)
        loginTV = findViewById(R.id.loginTV)

        loginTV.setOnClickListener {
            val intent = Intent(this@Register, Login::class.java)
            startActivity(intent)
        }

        registerBTN.setOnClickListener {
            val username = usernameET.text.toString().trim()
            val email = emailET.text.toString().trim()
            val password = passwordET.text.toString().trim()
            val confirmPassword = confirmPassET.text.toString().trim()

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(this@Register, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this@Register, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            addUserToDatabase(username, email, password)

        }
    }

    private fun addUserToDatabase(username: String, email: String, password: String) {
        val url = "https://pinoyspecials-app.pinoyspecialsrecipe.online/api/create"
        val queue: RequestQueue = Volley.newRequestQueue(this)

        val request = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                Log.e("TAG", "Response is $response")
                try {
                    val jsonObject = JSONObject(response)
                    Toast.makeText(this@Register, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                usernameET.setText("")
                emailET.setText("")
                passwordET.setText("")
                confirmPassET.setText("")
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@Register, "Fail to get response = $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }

            override fun getParams(): MutableMap<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["username"] = username
                params["email"] = email
                params["password"] = password
                return params
            }
        }
        queue.add(request)
        val intent = Intent(this@Register, Login::class.java)
        startActivity(intent)
        finish()
    }
}
