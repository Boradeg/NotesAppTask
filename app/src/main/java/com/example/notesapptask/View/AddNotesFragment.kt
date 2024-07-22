package com.example.notesapptask.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.notesapptask.Database.Note
import com.example.notesapptask.ViewModel.MainViewModel
import com.example.notesapptask.databinding.FragmentAddNotesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddNotesFragment : Fragment() {
    companion object {
        private const val ARG_ID = "id"
        private const val ARG_TITLE = "title"
        private const val ARG_DESC = "desc"

        fun newInstance(id: Int, title: String, desc: String): AddNotesFragment {
            val fragment = AddNotesFragment()
            val args = Bundle().apply {
                putInt(ARG_ID, id)
                putString(ARG_TITLE, title)
                putString(ARG_DESC, desc)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: MainViewModel
    private var noteId: Int = -1
    private var noteTitle: String? = null
    private var noteDesc: String? = null
    private lateinit var binding: FragmentAddNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId = it.getInt(ARG_ID)
            noteTitle = it.getString(ARG_TITLE)
            noteDesc = it.getString(ARG_DESC)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNotesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.apply {
            idNotesDesc.setText(noteDesc)
            idNotesName.setText(noteTitle)

            idSaveNotesBtn.setOnClickListener {
                val title = idNotesName.text.toString()
                val desc = idNotesDesc.text.toString()

                if (title.isEmpty()) idNotesName.error = "Enter Title"
                if (desc.isEmpty()) idNotesDesc.error = "Enter Desc"

                if (title.isNotEmpty() && desc.isNotEmpty()) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            val note = Note(
                                if (noteId != -1) noteId else 0,
                                title,
                                desc
                            )
                            if (noteId != -1) {
                                viewModel.updateNotes(note)
                            } else {
                                viewModel.insertDataInRoom(note)
                            }
                        }
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                            idNotesDesc.setText("")
                            idNotesName.setText("")
                        }
                    }
                }
            }
        }

        return binding.root
    }
}
