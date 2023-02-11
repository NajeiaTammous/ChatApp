package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type
class ListUsersActivity : AppCompatActivity() {

    lateinit var app: socketCreation
    private var mysocket: Socket? = null

    private val useradapter = userAdapter(arrayListOf(), object : userAdapter.onClickItemListener{
        override fun onClick(user: UsersModel) {
         val intent =Intent(applicationContext, ChatActivity::class.java)
        //   i.putExtra("idApp", user.id)
            startActivity(intent)
        }

    });

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_users)

        app = application as socketCreation
        mysocket = app.getSocket()

        val UserRecycler = findViewById<RecyclerView>(R.id.rcDataUser)

        UserRecycler.apply {
            UserRecycler.layoutManager = LinearLayoutManager(applicationContext)
             UserRecycler.adapter = useradapter


        }
        mysocket!!.emit("allUser", true)
        mysocket!!.on("allUser"){ ars ->
            runOnUiThread {
                val usersList:Type = object : TypeToken<List<UsersModel>>(){}.type
                val userList = Gson().fromJson<List<UsersModel>>(ars[0].toString(), usersList)
                useradapter.apply {
                     data.clear()
                     data.addAll(userList)
                     notifyDataSetChanged()
                 }
            }
        }

        mysocket!!.emit("Online", JSONObject().apply {
            put("id", LoginPage.users.id )
            put("isOnline", true )
        })

    }

    override fun onDestroy() {
        super.onDestroy()


        mysocket!!.emit("Online", JSONObject().apply {
            put("id", LoginPage.users.id )
            put("isOnline", false )
        })
    }
}