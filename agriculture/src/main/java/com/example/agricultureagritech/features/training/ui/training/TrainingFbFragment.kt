// This file displays the content for the Farm Business Management topic.
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
import com.safenation.agriculture.databinding.FragmentTrainingFbBinding

class TrainingFbFragment : Fragment() {

    companion object {
        fun newInstance() = TrainingFbFragment()
    }

    private var _binding: FragmentTrainingFbBinding? = null
    private val binding get() = _binding!!

    /*
    * The ViewModel for the detail screen, which will fetch the API data.
    */
    private val viewModel: TrainingDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingFbBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        * Set up the toolbar, observe the ViewModel for data changes, and
        * trigger the initial data fetch.
        */
        setupToolbar()
        observeViewModel()
        viewModel.fetchTrainingData("farm_business_management")
    }

    private fun observeViewModel() {
        /*
        * This observer waits for the training concept data to be successfully fetched.
        * Once the data arrives, it enables the buttons and sets their click actions.
        */
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

        /*
        * This observer shows or hides the progress bar based on the loading state.
        */
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        /*
        * This observer listens for any errors during the API call and shows a message.
        */
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Log.e("TrainingFbFragment", error)
            Toast.makeText(requireContext(), "Failed to load content", Toast.LENGTH_SHORT).show()
        }
    }

    /*
    * This function opens the learning material URL in an external app like a browser or PDF viewer.
    */
    private fun openPdf(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    /*
    * This function starts the new QuizActivity to display the quiz URL in a WebView.
    */
    private fun openQuiz(url: String) {
        val intent = Intent(requireActivity(), QuizActivity::class.java).apply {
            putExtra("QUIZ_URL", url)
        }
        startActivity(intent)
    }

    private fun setupToolbar() {
        /*
        * This sets up the navigation for the toolbar, allowing the user to go back.
        */
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}