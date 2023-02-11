package com.example.chatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView

class userAdapter( val data:ArrayList<UsersModel>, val onClick: onClickItemListener):RecyclerView.Adapter<userAdapter.MyViewHolder>() {

inner class MyViewHolder(item : View):RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.activity_item_user,parent,false
        )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
         val user = data[position]
        holder.itemView.apply {
            val username: TextView = findViewById(R.id.username)
            username.text = user.username
                setOnClickListener {
                    onClick.onClick(user)
                }
            val imageOnline = findViewById<ImageView>(R.id.imageOnline)
            imageOnline.setBackgroundColor(
                ResourcesCompat.getColor(context.resources,
                if(user.isOnline) R.color.green else R.color.gray, null)
            )
        }
    }

    override fun getItemCount() = data.size


    interface onClickItemListener{
        fun onClick(user:UsersModel)
    }

    }


