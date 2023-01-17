package co.tsw.mlkit.app.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ListItemInteractionListener {
        fun onInteraction(view: View, model: Any, position: Int)
    }
}