package com.example.amazingcardgame

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.logging.Handler

class Adaptador(var cartas: List<Carta>, private val icl: OnItemClickListener) :
    RecyclerView.Adapter<Adaptador.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.cartasingirar)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cartica, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carta = cartas[position]

        holder.itemView.setOnClickListener {
            icl.onItemClick(position)
        }

        if (carta.destapada) {
            holder.image.setImageResource(carta.item.imgid)
        } else {
            holder.image.setImageResource(R.drawable.reversecard)
        }
    }

    override fun getItemCount(): Int {
        return cartas.size
    }
}