package com.example.myprohelper.TabFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myprohelper.Db.MyProhelperDatabaseAdapter
import com.example.myprohelper.HistoryAdapter
import com.example.myprohelper.Model.MyProhelper
import com.example.myprohelper.R

class HistoryFragment : Fragment(){
    private lateinit var myProhelperDatabaseAdapter: MyProhelperDatabaseAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private var listHistory = ArrayList<MyProhelper>()
    private lateinit var recycler_view: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView =  inflater.inflate(R.layout.fragment_history,container,false)
        recycler_view = rootView.findViewById(R.id.recycler_view)
        myProhelperDatabaseAdapter = MyProhelperDatabaseAdapter(activity!!)
        listHistory = myProhelperDatabaseAdapter.getAllData()
        setRecycler()

        return rootView
    }

    private fun setRecycler() {
        historyAdapter = HistoryAdapter(listHistory,activity!!)
        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recycler_view.layoutManager = manager
        recycler_view.adapter = historyAdapter
    }
}