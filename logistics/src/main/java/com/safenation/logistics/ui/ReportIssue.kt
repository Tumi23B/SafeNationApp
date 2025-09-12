package com.safenation.logistics.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.safenation.logistics.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText

class ReportIssue : AppCompatActivity() {

    private val selectedImageUris = mutableListOf<Uri>()
    private lateinit var imagesContainer: LinearLayout
    private lateinit var imagesScrollView: HorizontalScrollView
    private lateinit var selectedImagesLabel: TextView

    // Enhanced launcher for selecting multiple images from gallery
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            var imagesAdded = 0

            // Handle multiple image selection first
            data?.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    if (selectedImageUris.size >= 5) break
                    val uri = clipData.getItemAt(i).uri
                    if (!selectedImageUris.contains(uri)) { // Avoid duplicates
                        selectedImageUris.add(uri)
                        addImageToContainer(uri)
                        imagesAdded++
                    }
                }
            }

            // Handle single image selection (fallback if no clipData)
            if (imagesAdded == 0) {
                data?.data?.let { uri ->
                    if (selectedImageUris.size < 5 && !selectedImageUris.contains(uri)) {
                        selectedImageUris.add(uri)
                        addImageToContainer(uri)
                        imagesAdded = 1
                    }
                }
            }

            // Show appropriate message
            when {
                imagesAdded > 0 -> {
                    Toast.makeText(this, "$imagesAdded image(s) added (${selectedImageUris.size}/5)", Toast.LENGTH_SHORT).show()
                }
                selectedImageUris.size >= 5 -> {
                    Toast.makeText(this, "Maximum 5 images reached", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "No images selected", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (result.resultCode == RESULT_CANCELED) {
            // User cancelled the image selection
            Toast.makeText(this, "Image selection cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_report_issue)

        // Apply edge-to-edge padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        val uploadCard = findViewById<MaterialCardView>(R.id.uploadCard)
        val submitButton = findViewById<MaterialButton>(R.id.submitReportButton)

        imagesContainer = findViewById(R.id.imagesContainer)
        imagesScrollView = findViewById(R.id.imagesScrollView)
        selectedImagesLabel = findViewById(R.id.selectedImagesLabel)

        // Open gallery to select images
        uploadCard.setOnClickListener { openGallery() }

        // Submit report
        submitButton.setOnClickListener {
            if (validateForm()) submitReport()
        }
    }

    /** Open gallery to pick images */
    private fun openGallery() {
        try {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                addCategory(Intent.CATEGORY_OPENABLE)
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                putExtra(Intent.EXTRA_LOCAL_ONLY, true) // Only local files
            }

            // Create a chooser intent to give user option to use gallery or file manager
            val chooserIntent = Intent.createChooser(intent, "Select Pictures")

            // Check if there's an app that can handle this intent
            if (intent.resolveActivity(packageManager) != null) {
                imagePickerLauncher.launch(chooserIntent)
            } else {
                Toast.makeText(this, "No gallery app found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error opening gallery: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /** Add image preview to container */
    private fun addImageToContainer(uri: Uri) {
        val inflater = LayoutInflater.from(this)
        val imageView = inflater.inflate(R.layout.item_image_preview, imagesContainer, false)

        val previewImage = imageView.findViewById<ImageView>(R.id.previewImage)
        val removeButton = imageView.findViewById<ImageButton>(R.id.removeImageButton)

        previewImage.setImageURI(uri)

        // Set tag to identify which image to remove
        imageView.tag = uri
        removeButton.tag = uri

        removeButton.setOnClickListener { view ->
            val imageUri = view.tag as? Uri
            imageUri?.let { uriToRemove ->
                imagesContainer.removeView(imageView)
                selectedImageUris.remove(uriToRemove)

                // Hide the scroll view if no images left
                if (imagesContainer.childCount == 0) {
                    imagesScrollView.visibility = View.GONE
                    selectedImagesLabel.visibility = View.GONE
                }

                Toast.makeText(this, "Image removed", Toast.LENGTH_SHORT).show()
            }
        }

        imagesContainer.addView(imageView)
        imagesScrollView.visibility = View.VISIBLE
        selectedImagesLabel.visibility = View.VISIBLE
    }

    /** Validate user inputs */
    private fun validateForm(): Boolean {
        val title = findViewById<TextInputEditText>(R.id.issueTitleInput).text.toString().trim()
        val description = findViewById<TextInputEditText>(R.id.issueDescInput).text.toString().trim()

        var isValid = true

        if (title.isEmpty()) {
            findViewById<TextInputEditText>(R.id.issueTitleInput).error = "Please enter an issue title"
            isValid = false
        } else if (title.length < 5) {
            findViewById<TextInputEditText>(R.id.issueTitleInput).error = "Title must be at least 5 characters"
            isValid = false
        } else {
            findViewById<TextInputEditText>(R.id.issueTitleInput).error = null
        }

        if (description.isEmpty()) {
            findViewById<TextInputEditText>(R.id.issueDescInput).error = "Please describe the issue"
            isValid = false
        } else if (description.length < 10) {
            findViewById<TextInputEditText>(R.id.issueDescInput).error = "Description must be at least 10 characters"
            isValid = false
        } else {
            findViewById<TextInputEditText>(R.id.issueDescInput).error = null
        }

        return isValid
    }

    /** Submit the report */
    private fun submitReport() {
        val title = findViewById<TextInputEditText>(R.id.issueTitleInput).text.toString().trim()
        val description = findViewById<TextInputEditText>(R.id.issueDescInput).text.toString().trim()
        val notes = findViewById<TextInputEditText>(R.id.notesInput).text.toString().trim()

        // Show loading state
        val submitButton = findViewById<MaterialButton>(R.id.submitReportButton)
        submitButton.isEnabled = false
        submitButton.text = "Submitting..."

        Toast.makeText(this, "Submitting report...", Toast.LENGTH_SHORT).show()

        // Simulate network delay
        Handler().postDelayed({
            val imageCount = selectedImageUris.size
            val message = if (imageCount > 0) {
                "Report submitted successfully with $imageCount image(s)!"
            } else {
                "Report submitted successfully!"
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()

            // Clear the form after submission
            clearForm()

            // Restore button state
            submitButton.isEnabled = true
            submitButton.text = "Submit Report"
        }, 1500)
    }

    /** Clear all inputs and reset preview */
    private fun clearForm() {
        val titleInput = findViewById<TextInputEditText>(R.id.issueTitleInput)
        val descInput = findViewById<TextInputEditText>(R.id.issueDescInput)
        val notesInput = findViewById<TextInputEditText>(R.id.notesInput)

        titleInput.text?.clear()
        descInput.text?.clear()
        notesInput.text?.clear()

        titleInput.error = null
        descInput.error = null

        // Clear all images
        selectedImageUris.clear()
        imagesContainer.removeAllViews()
        imagesScrollView.visibility = View.GONE
        selectedImagesLabel.visibility = View.GONE
    }
}