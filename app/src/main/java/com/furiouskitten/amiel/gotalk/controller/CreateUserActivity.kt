package com.furiouskitten.amiel.gotalk.controller

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Toast
import com.furiouskitten.amiel.gotalk.R
import com.furiouskitten.amiel.gotalk.services.AuthService
import com.furiouskitten.amiel.gotalk.services.UserDataService
import com.furiouskitten.amiel.gotalk.utils.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlin.random.Random

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        createSpinner.visibility = View.INVISIBLE
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
        enableSpinner(true)
        val eMail = createEmailText.text.toString()
        val passWord = createPasswordText.text.toString()
        val userName = createUserNameText.text.toString()

        if(userName.isNotEmpty() && eMail.isNotEmpty() && passWord.isNotEmpty()){

            AuthService.registerUser(this,eMail,passWord){ registerSuccess ->
                if(registerSuccess){
                    AuthService.loginUser(this, eMail, passWord){loginSuccess ->
                        if(loginSuccess){
                            AuthService.createUser(this, userName, eMail, avatarColor, userAvatar) {
                                    createSuccess -> if (createSuccess){
                                val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                enableSpinner(false)
                                finish()
                            }else{
                                errorToast()
                            }
                            }
                        }else{
                            errorToast()
                        }
                    }
                }else{
                    errorToast()
                }
            }
        } else{
            errorTypoToast()
        }
    }

    fun errorToast(){
      Toast.makeText(this, "Wait... Something went wrong, please try again.", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun errorTypoToast(){
        Toast.makeText(this, "Please fill-up all fields, okay?", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }


    fun enableSpinner(enable:Boolean){

        if(enable){
            createSpinner.visibility = View.VISIBLE
            createUserButton.isEnabled = false
            createAvatarImageView.isEnabled = false
            backgroundColorButton.isEnabled = false
        }else {
            createSpinner.visibility = View.INVISIBLE
            createUserButton.isEnabled = true
            createAvatarImageView.isEnabled = true
            backgroundColorButton.isEnabled = true
        }

    }

}
