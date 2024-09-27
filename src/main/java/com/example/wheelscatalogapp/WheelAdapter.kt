package com.example.wheelscatalogapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WheelAdapter(private val listener: OnWheelItemLongClick)
    : RecyclerView.Adapter<WheelAdapter.WheelViewHolder>() {

    private val wheelList = ArrayList<Wheel>()
    private fun bindData(holder: WheelViewHolder) {
        val producent = holder.itemView.findViewById<TextView>(R.id.producentList)
        val rozstaw = holder.itemView.findViewById<TextView>(R.id.rozstawList)
        val osadzenie = holder.itemView.findViewById<TextView>(R.id.etList)
        val rozmiar = holder.itemView.findViewById<TextView>(R.id.rozmiarList)
        val cena = holder.itemView.findViewById<TextView>(R.id.cenaList)
        val image = holder.itemView.findViewById<ImageView>(R.id.imageWheel)

        producent.text = wheelList[holder.absoluteAdapterPosition].producent
        rozstaw.text = wheelList[holder.absoluteAdapterPosition].rozstaw
        osadzenie.text = wheelList[holder.absoluteAdapterPosition].et
        rozmiar.text = wheelList[holder.absoluteAdapterPosition].rozmiar
        cena.text = wheelList[holder.absoluteAdapterPosition].cena
        Glide.with(holder.itemView)
                .load(wheelList[holder.absoluteAdapterPosition].image)
                .into(image)
    }
    fun setWheel(list: List<Wheel>){
        wheelList.clear()
        wheelList.addAll(list)
        notifyDataSetChanged()
    }
    fun removeWheel(wheel: Wheel, position: Int){
        wheelList.remove(wheel)
        notifyItemRemoved(position)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WheelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_row, parent, false)
        return WheelViewHolder(view)
    }
    override fun onBindViewHolder(holder: WheelViewHolder, position: Int) {
        bindData(holder)
    }
    override fun getItemCount(): Int {
        return wheelList.size
    }
    inner class WheelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnLongClickListener() {
                listener.onWheelLongClick(wheelList[absoluteAdapterPosition],absoluteAdapterPosition)
                true
            }
        }
    }
interface OnWheelItemLongClick {
        fun onWheelLongClick (wheel: Wheel, position: Int)
    }
}