// This file contains the logic for displaying a quiz in a WebView.
package com.example.agricultureagritech.features.training.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.safenation.agriculture.R

/*
* This activity hosts a WebView to display the online quiz.
* It enables JavaScript, which is required for Google Forms to work correctly.
*/
class QuizActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val webView: WebView = findViewById(R.id.quizWebView)
        val quizUrl = intent.getStringExtra("QUIZ_URL")

        if (quizUrl != null) {
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(quizUrl)
        }
    }
}