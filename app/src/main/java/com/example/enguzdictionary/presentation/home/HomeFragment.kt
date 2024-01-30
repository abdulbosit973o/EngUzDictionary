package com.example.enguzdictionary.presentation.home

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.enguzdictionary.R
import com.example.enguzdictionary.data.local.MySharedPref
import com.example.enguzdictionary.data.models.WordData
import com.example.enguzdictionary.databinding.FragmentHomeBinding
import com.example.enguzdictionary.presentation.adapter.WordAdapter
import java.util.Locale


class HomeFragment : Fragment(R.layout.fragment_home), HomeContract.View {

    private val REQ_CODE_SPEECH_INPUT = 100
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var adapter: WordAdapter
    private lateinit var presenter: HomeContract.Presenter
    private var currentQuery: String? = null
    var tts: TextToSpeech? = null
    private lateinit var dialog : Dialog


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val shared = MySharedPref
        shared.saveOpenScreen(0)
        presenter = HomePresenter(this)
        adapter = WordAdapter()
        dialog = Dialog(requireContext())



        adapter.setFavouriteTouchListener {
            presenter.updateData(it)
        }

        adapter.setItemTouchListener {
            showBottomSheetDialog(it)
        }

//        requireActivity().runOnUiThread{
//            binding.searchVoiceBtn.setOnClickListener {
//                Toast.makeText(requireContext(), "Salom", Toast.LENGTH_SHORT).show()
//            }
//        }


        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

//        val fastScroller = FastScroller(binding)
//        fastScroller.setThumbSize(20, 20) // Set the width and height of the thumb
//
//        recyclerView.setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT) // Set scrollbar position


        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                currentQuery = newText
                if (currentQuery?.isEmpty() == true) presenter.loadCursor()
                else presenter.loadCursorByQuery(currentQuery!!)
                return true
            }
        })




        val close =
            binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView

        close.setOnClickListener {
            binding.searchView.setQuery(null, false)
            binding.searchView.clearFocus()
        }

        binding.searchvoicebtn.setOnClickListener {
           promptSpeechInput()
        }




        presenter.loadCursor()
    }



    override fun Cursor(cursor: Cursor) {
        requireActivity().runOnUiThread {
            if (cursor.count == 0) {
                binding.placeholder.visibility = View.VISIBLE
                binding.none.visibility = View.VISIBLE
                binding.recycler.visibility = View.INVISIBLE
                return@runOnUiThread
            }
            else {
                binding.placeholder.visibility = View.GONE
                binding.none.visibility = View.GONE
                binding.recycler.visibility = View.VISIBLE
            }
            adapter.setCursor(cursor, currentQuery)

        }

    }
    fun showBottomSheetDialog(wordData: WordData) {

        dialog.setContentView(R.layout.dialog_edit_delete)

        val english = dialog.findViewById<TextView>(R.id.english)
        val type = dialog.findViewById<TextView>(R.id.type)
        val transcript = dialog.findViewById<TextView>(R.id.transcript)
        val uzbek = dialog.findViewById<TextView>(R.id.uzbek)
        val countable = dialog.findViewById<TextView>(R.id.countable)
        val favourite = dialog.findViewById<ImageView>(R.id.favourite)
        if (wordData.countable != "") {
            countable.text = wordData.countable
        }

        english.text = wordData.english
        type.text = wordData.type
        uzbek.text = wordData.uzbek
        transcript.text = wordData.transcript
        when(wordData.is_favourite) {
            0 -> {
                favourite.setImageResource(R.drawable.bookmark_unchecked)
            }
            else -> {
                favourite.setImageResource(R.drawable.bookmark)
            }
        }
        dialog.findViewById<ImageView>(R.id.volume).setOnClickListener{
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

        dialog.findViewById<ImageView>(R.id.share).setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            val shareBody = "English: ${wordData.english} ${wordData.countable}  ${wordData.transcript}\n\nUzbek: ${wordData.uzbek}\n\nDownload on google play\n\nhttps://play.google.com/store/apps/details?id=org.telegram.messenger"
            intent.setType("text/plain")
            intent.putExtra(
                Intent.EXTRA_SUBJECT,
                getString(androidx.appcompat.R.string.abc_action_bar_home_description)
            )
            intent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(intent, getString(R.string.app_name)))
        }


        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable( Color.TRANSPARENT))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)

    }
    fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.menu_home))

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(), "Sorry! Your device doesn\\'t support speech input",
                Toast.LENGTH_SHORT
            ).show()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val message = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
               updateResults( message!![0].lowercase())
            }
        }
    }
    fun updateResults(s: String) {
        var str = s
        if (s.contains(' ')) {
            str = s.split(" ")[0]
        }
        binding.searchView.setQuery(str, true)
    }
    private fun ConvertTextToSpeech(string: String) {
        if ("" == string) {
            tts?.speak(string, TextToSpeech.QUEUE_ADD, null, "0.45")
        } else tts?.speak(string, TextToSpeech.QUEUE_ADD, null,"0.45")
    }

    override fun onPause() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onPause()
    }

}