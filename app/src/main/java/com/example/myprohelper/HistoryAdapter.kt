package com.example.myprohelper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myprohelper.HistoryAdapter.*
import com.example.myprohelper.Model.MyProhelper

class HistoryAdapter(
    private val models: ArrayList<MyProhelper>,
    private val context: Context
) : androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.list_history, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mod = models[position]
        holder.tv_title.setText(mod.title)
        holder.tv_description.setText(""+mod.description)

    }

    override fun getItemCount(): Int {
       return models.size
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        itemView
    ) {

        var tv_title: TextView
        var tv_description: TextView
        init {
            tv_title = itemView.findViewById(R.id.tv_title)
            tv_description = itemView.findViewById(R.id.tv_description)
        }
    }


}
