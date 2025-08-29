// Fragment to display Pest and Disease Management content
package com.example.agricultureagritech.features.training.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.safenation.agriculture.R
import com.safenation.agriculture.databinding.FragmentTrainingPdmBinding

class TrainingPdmFragment : Fragment() {

    companion object {
        fun newInstance() = TrainingPdmFragment()
    }

    private var _binding: FragmentTrainingPdmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingPdmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(view)
    }

    private fun setupToolbar(root: View) {
        val toolbar = root.findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}