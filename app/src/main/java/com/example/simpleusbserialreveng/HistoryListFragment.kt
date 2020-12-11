package com.example.simpleusbserialreveng

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "HistoryListFragment"

class HistoryListFragment: Fragment()
{

    private lateinit var historyRecyclerView: RecyclerView
    private var adapter: TempAdapter? = TempAdapter(emptyList())



    //get instance of the TempList ViewModel
    private val tempListViewModel: TempListViewModel by activityViewModels()




    //here we inflate the views and get saved data from onSavedInctance if we want
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historylist, container, false)
        historyRecyclerView = view.findViewById(R.id.history_recycler_view) as RecyclerView
        //give the recycler view a layout manager, otherwise it will crash
        historyRecyclerView.layoutManager = LinearLayoutManager(context)
        historyRecyclerView.adapter = adapter


        UpdateUI()

        return view

    }


    //here we observe the live data
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    //method to update the UI with the items in the list
    private fun UpdateUI()
    {
        val tempList = tempListViewModel.tempReadings
        adapter = TempAdapter(tempList)
        historyRecyclerView.adapter = adapter
    }


    //inner class used to wrap a view list item with a ViewHolder so that it can be used by the RecyclerView
    private inner class TempViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener
    {

        private lateinit var tempItem: TempHistoryData
        //get instance of the temp item title, tempreture value, date
        val titleTextView: TextView = itemView.findViewById(R.id.temp_title)
        val tempValueTextView: TextView = itemView.findViewById(R.id.temp_degree)
        val tempDateTextView: TextView = itemView.findViewById(R.id.temp_date)

        //add on click listener to every ViewHolder
        init {
            itemView.setOnClickListener(this)
        }

        fun Bind(tempItem: TempHistoryData)
        {
            this.tempItem = tempItem
            titleTextView.text = this.tempItem.title
            tempValueTextView.text = this.tempItem.tempreture
            tempDateTextView.text = this.tempItem.date.toString()

        }

        //handle what happens when an item in the list is pressed
        override fun onClick(v: View?) {
            Toast.makeText(context, "${tempItem.title} pressed!", Toast.LENGTH_SHORT).show()
        }

    }

    //inner class used to create an adapter that binds the ViewHolders to the RecyclerView
    private inner class TempAdapter(var tempList: List<TempHistoryData>) : RecyclerView.Adapter<TempViewHolder>()
    {

        //inflate the views of the item in the list
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempViewHolder {
            val view = layoutInflater.inflate(R.layout.list_item_temp, parent, false)
            return TempViewHolder(view)
        }

        //get the total items in the list
        override fun getItemCount(): Int {
            return tempList.size
        }

        //populate the items in the list with the data from the tempreture data objects
        override fun onBindViewHolder(holder: TempViewHolder, position: Int) {
            val temp = tempList[position]

            holder.Bind(temp)

        }



    }



    //static method create in instance of this fragment: Good practice
    companion object {
        fun newInstance(): HistoryListFragment {
            return HistoryListFragment()
        }
    }
}