// Purpose: Fragment that renders the Training grid, wires toolbar navigation, and handles tile clicks
package com.example.agricultureagritech.features.training.ui

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager

import com.example.agricultureagritech.core.ui.GridSpacingItemDecoration
import com.google.android.material.appbar.MaterialToolbar
import com.safenation.agriculture.R
import com.safenation.agriculture.databinding.FragmentTrainingModuleBinding

class TrainingFragment : Fragment() {

    companion object {
        fun newInstance() = TrainingFragment()
    }

    private var _binding: FragmentTrainingModuleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TrainingViewModel by viewModels()
    private lateinit var adapter: TrainingTileAdapter

    /*
     * This function inflates the layout for the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingModuleBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*
     * This function is called after the view is created and sets up the toolbar, RecyclerView, and data binding.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar(view)
        setupRecycler()
        bindData()
    }

    /*
     * This function finds the toolbar and sets a click listener to finish the activity.
     */
    private fun setupToolbar(root: View) {
        val toolbar = root.findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { activity?.finish() }
    }

    /*
     * This function configures the RecyclerView, including the adapter and navigation logic for tile clicks.
     */
    private fun setupRecycler() {
        adapter = TrainingTileAdapter { tile ->
            val fragment = when (tile.title) {
                "Farm Business Management" -> TrainingFbFragment.newInstance()
                "Production Management" -> TrainingPmFragment.newInstance()
                "Farm Automation" -> TrainingFaFragment.newInstance()
                "Pest and disease management" -> TrainingPdmFragment.newInstance()
                else -> null
            }

            fragment?.let {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, it)
                    .addToBackStack(null)
                    .commit()
            }
        }
        val span = 2
        binding.recyclerTiles.layoutManager = GridLayoutManager(requireContext(), span)
        binding.recyclerTiles.adapter = adapter
        binding.recyclerTiles.addItemDecoration(
            GridSpacingItemDecoration(span, dpToPx(16))
        )
    }

    /*
     * This function submits the list of training tiles from the ViewModel to the adapter.
     */
    private fun bindData() {
        adapter.submitList(viewModel.tiles)
    }

    /*
     * This function converts density-independent pixels (dp) to pixels (px).
     */
    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    /*
     * This function cleans up the binding when the view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}