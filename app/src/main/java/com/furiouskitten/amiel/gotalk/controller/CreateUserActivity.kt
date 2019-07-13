package com.furiouskitten.amiel.gotalk.controller

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.furiouskitten.amiel.gotalk.R
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlin.random.Random

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun generateUserAvatar(view: View) {
        val random = Random
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        if (color == 0) {
            userAvatar = "light$avatar"
        } else {
            userAvatar = "dark$avatar"
        }

        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        createAvatarImageView.setImageResource(resourceId)
    }

    fun generateColorClicked(view: View) {
        val random = Random
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)


        createAvatarImageView.setBackgroundColor(Color.rgb(r,g,b))
        val savedRValue = r.toDouble() / 255
        val savedGValue = g.toDouble() / 255
        val savedBValue = b.toDouble() / 255

        avatarColor = "[$savedRValue,$savedGValue,$savedBValue, 1]"
    }

    fun createUserButtonClicked(view: View) {

    }

}
