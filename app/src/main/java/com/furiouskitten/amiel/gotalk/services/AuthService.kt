package com.furiouskitten.amiel.gotalk.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.furiouskitten.amiel.gotalk.utils.URL_CREATE_USER
import com.furiouskitten.amiel.gotalk.utils.URL_LOGIN
import com.furiouskitten.amiel.gotalk.utils.URL_REGISTER
import org.json.JSONException
import org.json.JSONObject

object AuthService {

    var isLoggedIn = false
    var userEmail = ""
    var authToken = ""

    fun registerUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit) {

        //JSON object
        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)

        //Request
        val requestBody = jsonBody.toString()
        val registerRequest = object : StringRequest(Method.POST, URL_REGISTER, Response.Listener { response ->
            println(response)
            complete(true)
        }, Response.ErrorListener { error ->
            println(error)
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(registerRequest)

    }

    fun loginUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit) {

        //JSON object
        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)


        val requestBody = jsonBody.toString()
        val loginRequest = object : JsonObjectRequest(Method.POST, URL_LOGIN, null, Response.Listener {response ->

//            try{
//
//            }catch(e: JSONException){
//
//            }


            userEmail = response.getString("user")
            authToken = response.getString("token")
            isLoggedIn = true
            complete(true)
            //this is where we parse the JSON object
        }, Response.ErrorListener {error ->
            println(error)
            complete(false)
             //this is where we show our error
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(loginRequest)
    }


    fun createUser(context :Context, name :String, email :String, avatarColor:String, avatarName:String, complete: (Boolean) -> Unit){

        //JSON object
        val jsonBody = JSONObject()
        jsonBody.put("name", name)
        jsonBody.put("email", email)
        jsonBody.put("avatarColor", avatarColor)
        jsonBody.put("avatarName", avatarName)

        val requestBody = jsonBody.toString()
        val createRequest = object : JsonObjectRequest(Method.POST, URL_CREATE_USER, null, Response.Listener { response ->
            UserDataService.name = response.getString("name")
            UserDataService.email = response.getString("email")
            UserDataService.avatarName = response.getString("avatarName")
            UserDataService.avatarColor = response.getString("avatarColor")
            UserDataService.id = response.getString("_id")
            complete(true)

        }, Response.ErrorListener { error ->
            println(error)
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer $authToken")
                return headers
            }
        }

        Volley.newRequestQueue(context).add(createRequest)

    }
}