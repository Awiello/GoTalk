package com.furiouskitten.amiel.gotalk.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.furiouskitten.amiel.gotalk.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginLoginClicked(view: View){
    }

    fun loginCreateUserButtonClicked(view: View){
        var loginCreateUserIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(loginCreateUserIntent)
    }
}
