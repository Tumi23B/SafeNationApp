// This file displays the content for the Pest and Disease Management topic.
package com.example.agricultureagritech.features.training.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.safenation.agriculture.databinding.FragmentTrainingPdmBinding

class TrainingPdmFragment : Fragment() {

    companion object {
        fun newInstance() = TrainingPdmFragment()
    }

    /*
    * The binding variable for the fragment's layout and the ViewModel for data handling.
    */
    private var _binding: FragmentTrainingPdmBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TrainingDetailViewModel by viewModels()

    /*
    * This function inflates the layout for the fragment.
    */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingPdmBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*
    * This function is called after the view is created. It sets up the toolbar,
    * starts observing the ViewModel, and fetches the training data.
    */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        observeViewModel()
        viewModel.fetchTrainingData("pest_and_disease_management")
    }

    /*
    * This function sets up observers on the ViewModel's LiveData properties
    * to update the UI based on data changes, loading states, or errors.
    */
    private fun observeViewModel() {
        viewModel.trainingConcept.observe(viewLifecycleOwner) { concept ->
            binding.btnLearningMaterial.isEnabled = true
            binding.btnQuiz.isEnabled = true

            binding.btnLearningMaterial.setOnClickListener {
                openPdf(concept.learningMaterialUrl)
            }
            binding.btnQuiz.setOnClickListener {
                openQuiz(concept.quizUrl)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Log.e("TrainingPdmFragment", error)
            Toast.makeText(requireContext(), "Failed to load content", Toast.LENGTH_SHORT).show()
        }
    }

    /*
    * This function creates an Intent to open a URL, used for viewing PDF files.
    */
    private fun openPdf(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    /*
    * This function creates an Intent to start the QuizActivity and passes the quiz URL.
    */
    private fun openQuiz(url: String) {
        val intent = Intent(requireActivity(), QuizActivity::class.java).apply {
            putExtra("QUIZ_URL", url)
        }
        startActivity(intent)
    }

    /*
    * This function configures the toolbar's back button to navigate up the stack.
    */
    private fun setupToolbar() {
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    /*
    * This function cleans up the binding when the fragment's view is destroyed.
    */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}