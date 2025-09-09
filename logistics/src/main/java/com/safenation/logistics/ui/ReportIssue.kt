package com.safenation.logistics.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.safenation.logistics.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText

class ReportIssue : AppCompatActivity() {

    private var imageUri: Uri? = null

    // Launcher for selecting image from gallery
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                imageUri = uri
                val previewImage = findViewById<ImageView>(R.id.previewImage)
                val previewCard = findViewById<CardView>(R.id.previewCard)
                previewImage.setImageURI(uri)
                previewCard.visibility = View.VISIBLE
                Toast.makeText(this, "Image selected successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
        val previewCard = findViewById<CardView>(R.id.previewCard)
        val removeImageButton = findViewById<ImageButton>(R.id.removeImageButton)
        val submitButton = findViewById<MaterialButton>(R.id.submitReportButton)

        // Open gallery to select an image
        uploadCard.setOnClickListener { openGallery() }

        // Remove selected image
        removeImageButton.setOnClickListener {
            imageUri = null
            previewCard.visibility = View.GONE
            findViewById<ImageView>(R.id.previewImage).setImageURI(null)
            Toast.makeText(this, "Image removed", Toast.LENGTH_SHORT).show()
        }

        // Submit report
        submitButton.setOnClickListener {
            if (validateForm()) submitReport()
        }
    }

    /** Open gallery to pick an image */
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        imagePickerLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }

    /** Validate user inputs */
    private fun validateForm(): Boolean {
        val title = findViewById<TextInputEditText>(R.id.issueTitleInput).text.toString().trim()
        val description = findViewById<TextInputEditText>(R.id.issueDescInput).text.toString().trim()

        if (title.isEmpty()) {
            findViewById<TextInputEditText>(R.id.issueTitleInput).error = "Please enter an issue title"
            return false
        }
        if (title.length < 5) {
            findViewById<TextInputEditText>(R.id.issueTitleInput).error = "Title must be at least 5 characters"
            return false
        }
        if (description.isEmpty()) {
            findViewById<TextInputEditText>(R.id.issueDescInput).error = "Please describe the issue"
            return false
        }
        if (description.length < 10) {
            findViewById<TextInputEditText>(R.id.issueDescInput).error = "Description must be at least 10 characters"
            return false
        }

        return true
    }

    /** Submit the report (simulate network/database call) */
    private fun submitReport() {
        val title = findViewById<TextInputEditText>(R.id.issueTitleInput).text.toString().trim()
        val description = findViewById<TextInputEditText>(R.id.issueDescInput).text.toString().trim()
        val notes = findViewById<TextInputEditText>(R.id.notesInput).text.toString().trim()

        Toast.makeText(this, "Submitting report...", Toast.LENGTH_SHORT).show()

        // Simulate network delay
        Handler().postDelayed({
            val message = if (imageUri != null) {
                "Report submitted successfully with image!"
            } else {
                "Report submitted successfully!"
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()

            // Clear the form after submission
            clearForm()
        }, 1500)
    }

    /** Clear all inputs and reset preview */
    private fun clearForm() {
        val titleInput = findViewById<TextInputEditText>(R.id.issueTitleInput)
        val descInput = findViewById<TextInputEditText>(R.id.issueDescInput)
        val notesInput = findViewById<TextInputEditText>(R.id.notesInput)
        val previewCard = findViewById<CardView>(R.id.previewCard)
        val previewImage = findViewById<ImageView>(R.id.previewImage)

        titleInput.text?.clear()
        descInput.text?.clear()
        notesInput.text?.clear()

        titleInput.error = null
        descInput.error = null

        imageUri = null
        previewCard.visibility = View.GONE
        previewImage.setImageURI(null)
    }
}


