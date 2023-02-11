package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import org.json.JSONObject

class ChatActivity : AppCompatActivity() {

    lateinit var app: socketCreation
    private var mysocket: Socket? = null
    private val messageAdapterr = MessageAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        app = application as socketCreation
        mysocket = app.getSocket();
        val MessageRecycler = findViewById<RecyclerView>(R.id.rcDataMes)
        val btnSend = findViewById<Button>(R.id.btnSend)

        MessageRecycler.apply {
            MessageRecycler.adapter = messageAdapterr
            MessageRecycler.layoutManager = LinearLayoutManager(applicationContext)


        }
        btnSend.setOnClickListener {
            sendMessage()
            val textMsg= findViewById<EditText>(R.id.ed_messege)
            textMsg.text.clear()
        }


        mysocket!!.on("message") { arg ->
            runOnUiThread {
                val message = Gson().fromJson<MessageModel>(arg[0].toString(), MessageModel::class.java)
                Toast.makeText(applicationContext,arg[0].toString(), Toast.LENGTH_LONG).show()
              //  Log.d("MessageAdapter", "dataMessages: $message")

                messageAdapterr.apply {
                    dataMessages.add(message)
                    notifyDataSetChanged()
                }
            }
        }
    }
    private fun sendMessage() {
        val message = JSONObject()
        val your_message :EditText = findViewById(R.id.ed_messege)
        message.put("username", LoginPage.users.username)
        message.put("id", LoginPage.users.id)
        message.put("message", your_message.text.toString())

        mysocket!!.emit("message", message)
    }
}