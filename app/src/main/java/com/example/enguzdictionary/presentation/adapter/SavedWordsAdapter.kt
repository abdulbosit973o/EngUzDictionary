package com.example.enguzdictionary.presentation.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.enguzdictionary.R
import com.example.enguzdictionary.data.models.WordData

class SavedWordsAdapter(var list: List<WordData>) : Adapter<SavedWordsAdapter.WordViewHolder>(),
    ItemNimadir {
    private var itemTouchListener: ((WordData) -> Unit)? = null
    private var saveTouchListener: ((WordData) -> Unit)? = null
    private var listEmptyListener: ((String) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    inner class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val english = view.findViewById<TextView>(R.id.english)
        private val containerView = view.findViewById<ConstraintLayout>(R.id.containerItem)
        private val saved = view.findViewById<ImageView>(R.id.save)
        private val uzbek = view.findViewById<TextView>(R.id.uzbek)
        private val save = view.findViewById<ImageView>(R.id.save)


        init {
            saved.setOnClickListener {
                val copy = list[adapterPosition].copy(is_favourite = 0)
                val mutableList = list.toMutableList()
                mutableList.removeAt(adapterPosition)
                list = mutableList
                if (list.isEmpty()) {
                    listEmptyListener?.invoke("English is Empty")
                }
                notifyDataSetChanged()
                saveTouchListener?.invoke(copy)
            }

            containerView.setOnClickListener {
                itemTouchListener?.invoke(list[adapterPosition])
            }
        }


        @SuppressLint("NotifyDataSetChanged")
        fun bind() {
            val item = list[adapterPosition]
            if (item.is_favourite == 1) save.setImageResource(R.drawable.bookmark)
            else save.setImageResource(R.drawable.bookmark_unchecked)
            english.text = item.english
            uzbek.text = item.uzbek
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind()
    }

    fun setItemTouchListener(block: (WordData) -> Unit) {
        this.itemTouchListener = block
    }

    fun setSaveTouchListener(block: (WordData) -> Unit) {
        this.saveTouchListener = block
    }

    fun setListEmptyListener(block: (String) -> Unit) {
        this.listEmptyListener = block
    }
}