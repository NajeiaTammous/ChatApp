package com.example.chatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(val dataMessages:ArrayList<MessageModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class SenderViewHolder(item: View) : RecyclerView.ViewHolder(item.rootView){

        fun bind(message : MessageModel){

            itemView.rootView.findViewById<TextView>(R.id.txtMes).text = message.message
        }
    }

    inner class RecieverViewHolder(item: View) : RecyclerView.ViewHolder(item.rootView) {
        fun bind(message: MessageModel) {
            itemView.rootView.findViewById<TextView>(R.id.txtMes).text = message.message
        }

}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 ->{
                SenderViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.sender_item, parent, false)
                )

            }else ->{
                RecieverViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.reciever_item, parent, false)
                )

            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      if (holder is SenderViewHolder){
          holder.bind(dataMessages[position])
      }else if (holder is RecieverViewHolder){
          holder.bind(dataMessages[position])
      }
    }

    override fun getItemCount() :Int = dataMessages.size


    override fun getItemViewType(position: Int): Int {
       val message = dataMessages[position]

        return when (message.id) {
            LoginPage.users.id -> {
                0
            }
            else -> {
                1
            }
        }
    }
}