package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONObject
import java.util.*


class SignupPage : AppCompatActivity() {

    lateinit var app: socketCreation
    private var mysocket: Socket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)

        val SignUp = findViewById<Button>(R.id.SignUpBtn)
        val UsernameText = findViewById<EditText>(R.id.UsernameTextLogin)
        val EmailText = findViewById<EditText>(R.id.EmailText)
        val login = findViewById<Button>(R.id.login)
        val PasswordText = findViewById<EditText>(R.id.PasswordTextLogin)

        app = application as socketCreation
        mysocket = app.getSocket();

        mysocket!!.on(Socket.EVENT_CONNECT_ERROR) {
                Log.e("EVENT_CONNECT_ERROR", "EVENT_CONNECT_ERROR: ${it[0]}")

        };
        mysocket!!.on(Socket.EVENT_CONNECT_TIMEOUT, Emitter.Listener {
                Log.e("EVENT_CONNECT_TIMEOUT", "EVENT_CONNECT_TIMEOUT: ")

        })
        mysocket!!.on(
            Socket.EVENT_CONNECT) { Log.e("onConnect", "Socket Connected!") };

        mysocket!!.on(Socket.EVENT_DISCONNECT, Emitter.Listener {
                Log.e("onDisconnect", "Socket onDisconnect!")
        })

        mysocket!!.connect()

        val idApp = UUID.randomUUID().toString()

        mysocket!!.on("SignUp") { args ->
            Log.e("ttttttttt", "${args[0]}")
                if (args[0].toString() == idApp)
                    runOnUiThread {
                        Toast.makeText(this, "${args[1]}", Toast.LENGTH_LONG).show()
                    }
        }

        SignUp.setOnClickListener {
            val username = UsernameText.text.toString()
            val email = EmailText.text.toString()
            val pass = PasswordText.text.toString()
            val jsonObject = JSONObject()
            jsonObject.put("username", username)
            jsonObject.put("id", UUID.randomUUID().toString())
            jsonObject.put("email", email)
            jsonObject.put("password", pass)
            jsonObject.put("isOnline", false)
            mysocket!!.emit("SignUp", idApp, jsonObject)

            val intent = Intent(this, LoginPage::class.java)
            intent.putExtra("idApp", idApp)
            startActivity(intent)
        }

        login.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
    }
}


