package com.example.myprohelper.Model

import android.content.Context
import android.widget.Toast

class Message {
    companion object{
        fun message(context: Context, message: String){
            Toast.makeText(context,message,Toast.LENGTH_LONG).show()
        }
    }
}