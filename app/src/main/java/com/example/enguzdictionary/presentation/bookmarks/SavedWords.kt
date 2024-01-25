package com.example.enguzdictionary.presentation.bookmarks

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.enguzdictionary.R
import com.example.enguzdictionary.data.local.MySharedPref
import com.example.enguzdictionary.data.models.WordData
import com.example.enguzdictionary.databinding.FragmentBookmarksBinding
import com.example.enguzdictionary.presentation.adapter.ItemNimadir
import com.example.enguzdictionary.presentation.adapter.SavedWordsAdapter
import com.example.enguzdictionary.presentation.adapter.SavedWordsUzAdapter
import java.util.Locale

class SavedWords : Fragment(R.layout.fragment_bookmarks), SavedContract.View {
    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    private val list = mutableListOf<WordData>()
    private lateinit var adapter: ItemNimadir
    private lateinit var presenter: SavedContract.Presenter
    private lateinit var dialog: Dialog
    var tts: TextToSpeech? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SavedPresenter(this)
        dialog = Dialog(requireContext())
        val shared = MySharedPref
        val pos = shared.getOpenScreen()

        presenter.loadCursor()

        when (pos) {
            0 -> {
                presenter.loadCursor()
                adapter = SavedWordsAdapter(list)
            }

            1 -> {
                presenter.loadUzList()
                adapter = SavedWordsUzAdapter(list)
            }
        }

        binding.recyclerBookmarks.adapter = when (pos) {
            0 -> {
                adapter as SavedWordsAdapter
            }

            else -> {
                adapter as SavedWordsUzAdapter
            }
        }

        binding.recyclerBookmarks.layoutManager = LinearLayoutManager(requireContext())


        when (pos) {
            0 -> {
                val adapterEng = adapter as SavedWordsAdapter
                adapterEng.setItemTouchListener {
                    showBottomSheetDialog(it)
                }
            }


            else -> {
                val adapterUz = adapter as SavedWordsUzAdapter
                adapterUz.setItemTouchListener {
                    showBottomSheetDialog(it)
                }
            }

        }

    }

    override fun Cursor(cursor: List<WordData>) {
        list.clear()
        list.addAll(cursor)
        if (cursor.isEmpty()) {
            binding.placeholder.visibility = View.VISIBLE
        } else {
            binding.placeholder.visibility = View.GONE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun showBottomSheetDialog(wordData: WordData) {
        when (MySharedPref.getOpenScreen()) {
            0 -> dialog.setContentView(R.layout.dialog_edit_delete)
            else -> dialog.setContentView(R.layout.dialog_edit_delete_uz)
        }


        val english = dialog.findViewById<TextView>(R.id.english)
        val type = dialog.findViewById<TextView>(R.id.type)
        val transcript = dialog.findViewById<TextView>(R.id.transcript)
        val uzbek = dialog.findViewById<TextView>(R.id.uzbek)
        val favourite = dialog.findViewById<ImageView>(R.id.favourite)

        english.text = wordData.english
        type.text = wordData.type
        uzbek.text = wordData.uzbek
        transcript.text = wordData.transcript
        when (wordData.is_favourite) {
            0 -> {
                favourite.setImageResource(R.drawable.bookmark_unchecked)
            }

            else -> {
                favourite.setImageResource(R.drawable.bookmark)
            }
        }
        dialog.findViewById<ImageView>(R.id.volume).setOnClickListener {
            tts = TextToSpeech(requireContext()) {
                if (it === TextToSpeech.SUCCESS) {
                    val result: Int? = tts?.setLanguage(Locale.US)
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED
                    ) {
                        Log.e("error", "This Language is not supported")
                    } else {
                        ConvertTextToSpeech(wordData.english)
                    }
                } else Log.e("error", "Initilization Failed!")
            }
            tts!!.setLanguage(Locale.US)
            tts!!.speak(wordData.english, TextToSpeech.QUEUE_ADD, null)
        }

        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)

    }

    private fun ConvertTextToSpeech(string: String) {
        if ("" == string) {
            tts?.speak(string, TextToSpeech.QUEUE_ADD, null, "0.45")
        } else tts?.speak(string, TextToSpeech.QUEUE_ADD, null, "0.45")
    }
}