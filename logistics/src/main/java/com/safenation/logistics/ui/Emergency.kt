package com.safenation.logistics.ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.safenation.logistics.R

class Emergency : AppCompatActivity() {

    // First aid details for various emergencies
    private val bleedingDetails = """ 
        1. 🩸 Apply direct pressure to the wound with a clean cloth or bandage 
        2. 📈 Elevate the injured area above heart level if possible 
        3. 🔄 If bleeding soaks through, add more dressing but don't remove the first layer 
        4. 🏥 Seek immediate medical attention for serious wounds 
        5. 💊 Keep the person calm and still until help arrives 
    """.trimIndent()

    private val burnsDetails = """ 
        1. 💦 Cool the burn under cool (not cold) running water for 10-15 minutes 
        2. 📞 Call for emergency help if burn is larger than your palm 
        3. 🚫 Don't apply ice, creams, or adhesive bandages 
        4. 🧴 Cover with cling film or a clean plastic bag 
        5. 👕 Remove tight clothing before swelling begins 
    """.trimIndent()

    private val chokingDetails = """ 
        1. ❓ Ask "Are you choking?" - if they can cough, encourage coughing 
        2. 🫂 Perform Heimlich maneuver: Make a fist with one hand, grab it with the other. 
           Place hands just above the belly button and pull inward/upward. Repeat up to 5 times. 
        3. 📞 Call emergency services if obstruction doesn't clear 
        4. 🛌 For unconscious person, begin CPR with chest compressions 
        5. 👶 Use back blows and chest thrusts for infants 
    """.trimIndent()

    private val fracturesDetails = """ 
        1. 🛑 Keep the injured area still and supported 
        2. 🧊 Apply ice wrapped in cloth to reduce swelling 
        3. 📏 Immobilize with a splint if medical help is delayed 
        4. 🚑 Seek immediate medical attention 
        5. 💧 Don't try to realign bones or push protruding bones back 
    """.trimIndent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)

        // Setup emergency contact cards
        setupEmergencyCall(R.id.policeCard, "10111")
        setupEmergencyCall(R.id.ambulanceCard, "10177")
        setupEmergencyCall(R.id.fireCard, "10177")
        setupEmergencyCall(R.id.childlineCard, "0800055555")
        setupEmergencyCall(R.id.disasterCard, "0800020111")

        // Setup first aid cards
        setupFirstAidCard(R.id.bleedingCard, "Bleeding Control", bleedingDetails)
        setupFirstAidCard(R.id.burnsCard, "Burns Treatment", burnsDetails)
        setupFirstAidCard(R.id.chokingCard, "Choking Response", chokingDetails)
        setupFirstAidCard(R.id.fracturesCard, "Fracture Management", fracturesDetails)
    }

    // Helper: Set up emergency contact click listener
    private fun setupEmergencyCall(cardId: Int, number: String) {
        findViewById<CardView>(cardId).setOnClickListener {
            dialEmergencyNumber(number)
        }
    }

    // Helper: Set up first aid card click listener
    private fun setupFirstAidCard(cardId: Int, title: String, details: String) {
        findViewById<CardView>(cardId).setOnClickListener {
            showFirstAidDialog(title, details)
        }
    }

    // Open dialer with given number (ACTION_DIAL is safer than CALL_PHONE)
    private fun dialEmergencyNumber(number: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$number")
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to make call", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    // Show first aid details in a clean dialog
    private fun showFirstAidDialog(title: String, details: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_first_aid)
        dialog.setCancelable(true)

        val titleTextView = dialog.findViewById<TextView>(R.id.titleTextView)
        val detailsTextView = dialog.findViewById<TextView>(R.id.detailsTextView)
        val closeButton = dialog.findViewById<Button>(R.id.closeButton)

        titleTextView.text = title
        detailsTextView.text = details

        closeButton.setOnClickListener { dialog.dismiss() }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }
}

