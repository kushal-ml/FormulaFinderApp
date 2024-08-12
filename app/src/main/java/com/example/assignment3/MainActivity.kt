package com.example.assignment3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.topicSpinner)
        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val selectedTopic = spinner.selectedItem.toString()
            val intent = Intent(this, FormulaActivity::class.java)
            intent.putExtra("SELECTED_TOPIC", selectedTopic)
            startActivity(intent)
        }
    }
}
