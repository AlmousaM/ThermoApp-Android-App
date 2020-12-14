package com.example.simpleusbserialreveng


import android.app.AlertDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File


private const val TAG = "HistoryListFragment"

class HistoryListFragment: Fragment()
{

    private lateinit var historyRecyclerView: RecyclerView

    //initially the list is empty until we get the data from the viewmodel live data
    private var adapter: TempAdapter? = TempAdapter(emptyList())



    private lateinit var safeContext: Context




    //get instance of the TempList ViewModel
    private val tempListViewModel: TempListViewModel by activityViewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
    }


    //function to get images from the data folder
    fun getSavedTempReadings() : MutableList<TempHistoryData>
    {
        //create a mutable list of the for temp data objects
        val readings = mutableListOf<TempHistoryData>()

        //get the location of the image folders
        val folder: File = getOutputDirectory()

        //get list of files in the folder
        val files = folder.listFiles()

        //loop thru the files to get the name and the image URI
        for (i in files.indices)
        {
            val imageFile = files[i]
            var tempDataObject = TempHistoryData()
            tempDataObject.title = imageFile.name
            tempDataObject.photoPath = imageFile.path
            readings += tempDataObject
        }

        return readings

    }




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

        //call viewmodel function to populate the live data list TempReadingsLiveData
        tempListViewModel.getTempReadings(getOutputDirectory())


        return view

    }


    //here we observe the live data
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //observe the when the live data get the images
        tempListViewModel.TempReadingsLiveData.observe(viewLifecycleOwner, Observer { tempItemList -> tempItemList?.let {
            Log.i(TAG, "Got temp List of size ${tempItemList.size}")
            UpdateUI(tempItemList)

        } })


    }

    //method to update the UI with the items in the list
    private fun UpdateUI(tempList: List<TempHistoryData>)
    {
        adapter = TempAdapter(emptyList())
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
        val tempImage: ImageView = itemView.findViewById(R.id.tempImageView)

        //add on click listener to every ViewHolder
        init {
            itemView.setOnClickListener(this)
        }

        fun Bind(tempItem: TempHistoryData)
        {
            this.tempItem = tempItem
            titleTextView.append(this.tempItem.title)
            tempValueTextView.append(this.tempItem.tempreture)
            tempDateTextView.append(this.tempItem.date)
            val bitmap = GetScaledBitMap(tempItem.photoPath, requireActivity())
            tempImage.setImageBitmap(bitmap)
        }

        //handle what happens when an item in the list is pressed
        override fun onClick(v: View?) {
            Toast.makeText(context, "${tempItem.title} pressed!", Toast.LENGTH_SHORT).show()
            ShowImageDialog(tempItem.photoPath)
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


    fun getOutputDirectory(): File {
        val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else activity?.filesDir!!
    }


    private fun ShowImageDialog(imagePath: String)
    {
        val builder = AlertDialog.Builder(safeContext)
        builder.setPositiveButton(
            "Close"
        ) { dialog, which -> }
        val dialog = builder.create()
        val inflater = layoutInflater
        val dialogLayout: View = inflater.inflate(R.layout.image_viewer, null)
        dialog.setView(dialogLayout)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)


        dialog.show()

        val bitmap = GetScaledBitMap(imagePath, requireActivity())

        val image = dialog.findViewById<ImageView>(R.id.imageEnlarged)
        image.setImageBitmap(bitmap)

    }






    //static method create in instance of this fragment: Good practice
    companion object {
        fun newInstance(): HistoryListFragment {
            return HistoryListFragment()
        }
    }
}