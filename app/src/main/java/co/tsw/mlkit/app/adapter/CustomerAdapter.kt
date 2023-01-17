package co.tsw.mlkit.app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import co.tsw.mlkit.app.R
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.layout_item_customer.view.*


class CustomAdapter(private val mContext: Context, private var mListData: ArrayList<JsonObject>?) :
        AbstractAdapter(), Filterable {

    private var mListDataFull: ArrayList<JsonObject>? = null
    private var mItemInteractionListener: AbstractAdapter.ListItemInteractionListener? = null


    /**
     * @param listener
     */
    fun setItemInteractionListener(listener: AbstractAdapter.ListItemInteractionListener) {
        this.mItemInteractionListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mListDataFull = ArrayList()
        mListDataFull?.addAll(mListData!!)
        return CustomerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_item_customer, parent, false))
    }

    override fun getItemCount(): Int {
        return mListData!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getFilter(): Filter {
        return objFilter
    }

    class CustomerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val objFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<JsonObject>()

            if (constraint.isNullOrEmpty()) {
                filteredList.addAll(mListDataFull!!)
            } else {
                val filterPattern = constraint.toString().lowercase().trim()

                for (item in mListDataFull!!) {
                   //TODO implement filter condition
                }
            }
            val results = FilterResults()
            results.values = filteredList

            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results!!.values == null) return
            mListData?.clear()
            mListData?.addAll(results.values as ArrayList<JsonObject>)
            notifyDataSetChanged()
        }
    }
}