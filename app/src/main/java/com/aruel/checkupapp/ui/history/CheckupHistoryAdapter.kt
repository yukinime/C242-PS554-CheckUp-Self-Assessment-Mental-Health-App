//package com.aruel.checkupapp.ui.history
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aruel.checkupapp.R
import com.aruel.checkupapp.data.room.CheckupHistory

class CheckupHistoryAdapter : RecyclerView.Adapter<CheckupHistoryAdapter.HistoryViewHolder>() {

    private val historyList = mutableListOf<CheckupHistory>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<CheckupHistory>) {
        historyList.clear()
        historyList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList[position]
        holder.bind(history)
    }

    override fun getItemCount(): Int = historyList.size

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tvCheckupDate)
        private val tvClass: TextView = itemView.findViewById(R.id.tvCheckupPrediction)
        private val tvSeveriti: TextView = itemView.findViewById(R.id.severiti)
        private val tvcategory: TextView = itemView.findViewById(R.id.category)
        private val tvmotiv: TextView = itemView.findViewById(R.id.motiv)
        private val tvmotiv2: TextView = itemView.findViewById(R.id.motiv2)



        fun bind(history: CheckupHistory) {
            tvDate.text = history.date
            tvClass.text = history.predictedClass
            tvSeveriti.text = history.severity
            tvcategory.text = history.category
            tvmotiv.text = history.definition
            tvmotiv2.text = history.suggestion
        }
    }
}