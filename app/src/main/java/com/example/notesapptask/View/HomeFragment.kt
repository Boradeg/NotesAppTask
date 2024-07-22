package com.example.notesapptask.View

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapptask.Adapter.NotesAdapter
import com.example.notesapptask.Adapter.OnItemClickListener
import com.example.notesapptask.Database.Note
import com.example.notesapptask.R
import com.example.notesapptask.ViewModel.MainViewModel
import com.example.notesapptask.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), OnItemClickListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var bgButton: Drawable
    private lateinit var bgButton2: Drawable
    private lateinit var adapter: NotesAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        bgButton = ContextCompat.getDrawable(requireContext(), R.drawable.bg_button)!!
        bgButton2 = ContextCompat.getDrawable(requireContext(), R.drawable.bg_button_selector)!!

        setupRecyclerView()
        setupObservers()
        setupClickListeners()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = NotesAdapter(ArrayList(), this)
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewNotes.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getAllNotes()
        viewModel._notes.observe(viewLifecycleOwner) {
            adapter.setNotes(it)
        }
    }

    private fun setupClickListeners() {
        onCardClick(binding.cardLayout1, binding.textViewAllNotes)

        binding.cardLayout1.setOnClickListener {
            onCardClick(it, binding.textViewAllNotes)
        }
        binding.cardLayout2.setOnClickListener {
            onCardClick(it, binding.textViewFavNotes)
        }
        binding.cardLayout3.setOnClickListener {
            onCardClick(it, binding.textViewHighlightNotes)
        }
        binding.idAddNotesFabButton.setOnClickListener {
            replaceFragment(AddNotesFragment())
        }
    }

    private fun onCardClick(view: View, view2: TextView) {
        resetCardSelection()
        view.background = bgButton2
        view2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    private fun resetCardSelection() {
        binding.cardLayout1.background = bgButton
        binding.cardLayout2.background = bgButton
        binding.cardLayout3.background = bgButton
        binding.textViewFavNotes.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.black
        ))
        binding.textViewHighlightNotes.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.black
        ))
        binding.textViewAllNotes.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.black
        ))
    }

    fun replaceFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frag, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun editNotes(obj: Note) {
        val fragment = AddNotesFragment.newInstance(
            obj.id,
            obj.notesTitle,
            obj.notesDesc
        )
        replaceFragment(fragment)
    }

    override fun deleteNotes(obj: Note) {
        AlertDialog.Builder(requireContext())
            .setTitle("Alert!")
            .setMessage("Do you want to delete this note?")
            .setPositiveButton("Yes") { dialog, _ ->
                viewModel.deleteNotes(obj)
                dialog.dismiss()
                Toast.makeText(requireContext(), "Note Deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
