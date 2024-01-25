package com.example.enguzdictionary.presentation.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.util.Log
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
import com.example.enguzdictionary.utils.extensions.string_extensions.createSpannableText

class WordUzbekAdapter() : Adapter<WordUzbekAdapter.WordViewHolder>() , ItemNimadir{
    private var cursor: Cursor? = null
    private var query: String? = null
    private var itemTouchListener: ((WordData) -> Unit)? = null
    private var favouriteTouchListener: ((WordData) -> Unit)? = null

    inner class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val english = view.findViewById<TextView>(R.id.english)
        private val containerView = view.findViewById<ConstraintLayout>(R.id.containerItem)
        private val uzbek = view.findViewById<TextView>(R.id.uzbek)
        private val type = view.findViewById<TextView>(R.id.type)
        private val transcript = view.findViewById<TextView>(R.id.transcript)
        private val save = view.findViewById<ImageView>(R.id.save)
        private val countable = view.findViewById<TextView>(R.id.countable)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(item: WordData) {
            Log.d("TTT", "${item.id}")

            if (item.is_favouriteUz == 1) save.setImageResource(R.drawable.bookmark)
            else save.setImageResource(R.drawable.bookmark_unchecked)

            if (query != null) {
                english.text = item.uzbek.createSpannableText(query!!)
            } else {
                english.text = item.uzbek
            }

            uzbek.text = item.english

            containerView.setOnClickListener {
                itemTouchListener?.invoke(item)
            }

            save.setOnClickListener {
                if (item.is_favouriteUz == 0) {
                    favouriteTouchListener?.invoke(
                        WordData(
                            id = item.id,
                            english = item.english,
                            type = item.type,
                            transcript = item.transcript,
                            uzbek = item.uzbek,
                            countable = item.countable,
                            is_favourite = item.is_favourite,
                            is_favouriteUz = 1
                        )
                    )
                } else {
                    favouriteTouchListener?.invoke(
                        WordData(
                            id = item.id,
                            english = item.english,
                            type = item.type,
                            transcript = item.transcript,
                            uzbek = item.uzbek,
                            countable = item.countable,
                            is_favourite = item.is_favourite,
                            is_favouriteUz = 0
                        )
                    )
                }

                notifyDataSetChanged()
            }


        }


    }


    @SuppressLint("NotifyDataSetChanged")
    fun setCursor(cursor: Cursor, query: String? = null) {
        this.cursor?.close()
        this.cursor = cursor
        this.query = query
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        )
    }

    override fun getItemCount(): Int = this.cursor?.count ?: 0

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
//        holder.save.setOnClickListener {
//
//            if (checkBox == 0) {
//                holder.save.setImageResource(R.drawable.bookmark)
//                notifyItemChanged(position)
//                checkBox = 1
//                return@setOnClickListener
//            }
//            if (checkBox == 1) {
//                holder.save.setImageResource(R.drawable.bookmark_unchecked)
//                notifyItemChanged(position)
//                checkBox = 0
//                return@setOnClickListener
//            }
//        }


        // if (!dataIsValid) return
        this.cursor?.let {
            it.moveToPosition(position)

            val english = it.getString(it.getColumnIndex("english"))
            val id = it.getLong(it.getColumnIndex("id"))
            val uzbek = it.getString(it.getColumnIndex("uzbek"))
            val transcript = it.getString(it.getColumnIndex("transcript"))
            val is_favourite = it.getInt(it.getColumnIndex("is_favourite"))
            val is_favouriteUz = it.getInt(it.getColumnIndex("is_favouriteUz"))
            val countable = it.getString(it.getColumnIndex("countable"))
            val type = it.getString(it.getColumnIndex("type"))

            val item = WordData(
                id,
                english.toString(),
                type,
                transcript,
                uzbek.toString(),
                countable,
                is_favourite,
                is_favouriteUz
            )
            holder.bind(item)
        }
    }

    fun setFavouriteTouchListener(block: (WordData) -> Unit) {
        this.favouriteTouchListener = block
    }

    fun setItemTouchListener(block: (WordData) -> Unit) {
        this.itemTouchListener = block
    }
}