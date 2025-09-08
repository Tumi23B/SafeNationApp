package com.safenation.mining.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.safenation.mining.databinding.FragmentTrainingBinding

class TrainingFragment : Fragment() {
    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnViewPdf.setOnClickListener {
            viewPdf()
        }

        binding.btnPlayVideo.setOnClickListener {
            playVideo()
        }
    }

    private fun viewPdf() {
        Toast.makeText(requireContext(), "Opening PDF viewer...", Toast.LENGTH_SHORT).show()
    }

    private fun playVideo() {
        Toast.makeText(requireContext(), "Playing training video...", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}