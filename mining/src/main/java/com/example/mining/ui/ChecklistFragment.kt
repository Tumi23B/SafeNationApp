package com.example.mining.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.safenation.mining.databinding.FragmentChecklistBinding


class ChecklistFragment : Fragment() {
    private var _binding: FragmentChecklistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentChecklistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveChecklist.setOnClickListener {
            saveChecklist()
        }

        binding.btnSubmitChecklist.setOnClickListener {
            submitChecklist()
        }
    }

    private fun saveChecklist() {
        // Save checklist progress locally
        Toast.makeText(requireContext(), "Checklist progress saved", Toast.LENGTH_SHORT).show()
    }

    private fun submitChecklist() {
        val notes = binding.etChecklistNotes.text.toString()
        val itemsCompleted = listOf(
            binding.cbItem1.isChecked,
            binding.cbItem2.isChecked,
            binding.cbItem3.isChecked,
            binding.cbItem4.isChecked,
            binding.cbItem5.isChecked
        )

        // Here you would submit the checklist to your server
        Toast.makeText(requireContext(), "Checklist submitted successfully!", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}