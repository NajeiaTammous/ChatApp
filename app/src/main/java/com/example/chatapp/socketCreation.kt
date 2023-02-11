package com.example.chatapp

import android.app.Application
import android.util.Log
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.lang.RuntimeException
import java.net.URISyntaxException
class socketCreation :Application() {

    private var mSocket: Socket? = IO.socket("http://192.168.137.85:4000")


    fun getSocket(): Socket? {
        return mSocket
    }

}