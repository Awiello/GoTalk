package com.furiouskitten.amiel.gotalk.services

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.furiouskitten.amiel.gotalk.utils.URL_REGISTER
import org.json.JSONObject

object AuthService {

    fun registerUser(context: Context, email:String, password :String, complete:(Boolean)->Unit){

        //JSON object
        val jsonBody = JSONObject()
        jsonBody.put("email",email)
        jsonBody.put("password",password)

        //Request
        val requestBody = jsonBody.toString()
        val registerRequest = object : StringRequest(Method.POST, URL_REGISTER, Response.Listener { response -> println(response)
            complete(true)
        }, Response.ErrorListener {
           error -> println(error)
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(registerRequest)

    }

}