package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import org.json.JSONObject

class LoginPage : AppCompatActivity() {

    lateinit var app: socketCreation
    private var mysocket: Socket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val Usernametxt = findViewById<EditText>(R.id.UsernameTextLogin)
        val Passwordtxt = findViewById<EditText>(R.id.PasswordTextLogin)
        val LoginButton = findViewById<Button>(R.id.loginBtn)
        val SignUpButton = findViewById<Button>(R.id.sign)

        val idApp = intent.getStringExtra("idApp")
        Log.e("ttttttt",idApp.toString())

        app = application as socketCreation
        mysocket = app.getSocket()


        mysocket!!.connect()


        mysocket!!.on(Socket.EVENT_CONNECT) {
            Log.e("onConnect", "Socket Connected!")
        }
        mysocket!!.on(Socket.EVENT_DISCONNECT, Emitter.Listener {
            runOnUiThread {
                Log.e("onDisconnect", "Socket onDisconnect!")
            }
        })


        mysocket!!.on("Login") { args ->
            Log.e("ttttttttt", "${args[0]}")
            runOnUiThread {
                if (args[0].toString() == idApp)
                    if (args[1].toString().toBoolean()) {
                        Toast.makeText(this, "arr ${args[1]}", Toast.LENGTH_LONG).show()
                        users = Gson().fromJson(args[2].toString(), UsersModel::class.java)
                        startActivity(Intent(applicationContext, ListUsersActivity::class.java))
                    } else {
                        Toast.makeText(this, "${args[0]}", Toast.LENGTH_LONG).show()
                    }
            }
        }

        LoginButton.setOnClickListener {
            val username = Usernametxt.text.toString()
            val password = Passwordtxt.text.toString()
            val jsonObject = JSONObject()
            jsonObject.put("user", username)
            jsonObject.put("pass", password)
            mysocket!!.emit("Login", idApp, jsonObject)
        }

        SignUpButton.setOnClickListener {
            val intent = Intent(this, SignupPage::class.java)
            startActivity(intent)
        }
    }

    companion object {
        lateinit var users: UsersModel
    }
}