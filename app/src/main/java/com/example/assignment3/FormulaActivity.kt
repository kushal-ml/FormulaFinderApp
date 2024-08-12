package com.example.assignment3

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class FormulaActivity : AppCompatActivity() {

    private val CHANNEL_ID = "formula_channel"
    private val NOTIFICATION_PERMISSION_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formula)

        val topic = intent.getStringExtra("SELECTED_TOPIC")
        val formulaTextView = findViewById<TextView>(R.id.formulaTextView)
        val topicTextView = findViewById<TextView>(R.id.topicTextView)

        topicTextView.text = "Selected Topic: $topic"
        formulaTextView.text = getFormulaForTopic(topic)

        createNotificationChannel()
        checkNotificationPermissionAndSendNotification(topic)
    }

    private fun checkNotificationPermissionAndSendNotification(topic: String?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            sendNotification(topic)
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), NOTIFICATION_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val topic = intent.getStringExtra("SELECTED_TOPIC")
                sendNotification(topic)
            } else {
                showPermissionDeniedMessage()
            }
        }
    }

    private fun showPermissionDeniedMessage() {
        Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
    }

    private fun getFormulaForTopic(topic: String?): String {
        return when (topic) {
            "Rectangle" -> "Rectangle: Area = Length x Width \n The formula accurately represents the relationship between the dimensions and the area of a rectangle. So, the formula essentially states that the area of a rectangle is calculated by multiplying its length and its width."
            "Triangle" -> "Triangle: Area = ½ Base x Height  \n The area of a triangle can be calculated using the formula 1/2 Base x Height, where base refers to any side of the triangle and height is the perpendicular distance from that base to the opposite vertex. This formula holds true for all types of triangles, whether they are right-angled, isosceles, scalene, or equilateral. "
            "Circle" -> "Circle: Area = πr² Circumference = 2πr (π ≈ 3.14)  \n  This formula explains the relationship between two key properties of a circle: its area and its circumference. The area of the circle is calculated by multiplying π by the radius squared."
            "Cylinder" -> "Cylinder: Volume = πr²h  \n The formula Volume = πr²h tells you that the volume of a cylinder is found by multiplying the area of its base (πr²) by its height (h). "
            "Sphere" -> "Sphere: Volume = (43)πr³  \n This formula essentially tells you that the volume of a sphere is proportional to the cube of its radius, multiplied by a constant factor of 43π."
            "Parallelogram" -> "Area of Parallelogram = base x height \n This is the most common and straightforward method. Imagine a parallelogram with a base of length “b” and a corresponding height “h” (perpendicular to the base). The area (A) can be calculated using this formula."
            "Pythagorean Theorem" -> "Pythagorean Theorem: a² + b² = c² \n The Pythagorean Theorem is a fundamental concept in geometry that relates the lengths of the sides in a right triangle. In a right triangle (a triangle with one 90-degree angle), the square of the length of the hypotenuse (the side opposite the right angle) is equal to the sum of the squares of the lengths of the other two sides (called legs). "
            "Slope Formula" -> "Slope Formula : m = (y₂ – y₁) / (x₂ – x₁) \n The slope formula is a mathematical tool used to calculate the steepness and direction of a non-vertical line. It tells you how much the line slants up or down as you move from left to right. Here’s the breakdown:\n m = (y₂ – y₁) / (x₂ – x₁)\n Where:\n m represents the slope of the line.\n (y₂, x₂) and (y₁, x₁) are the coordinates of two different points on the line.\n (y₂ – y₁) represents the change in the y-coordinate (rise).\n (x₂ – x₁) represents the change in the x-coordinate (run)."
            else -> "No formula found"
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Formula Channel"
            val descriptionText = "Channel for formula notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(topic: String?) {
        try {
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Formula Finder")
                .setContentText("Selected Topic: $topic")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(this)) {
                notify(1, builder.build())
            }
        } catch (e: SecurityException) {
            showPermissionDeniedMessage()
        }
    }
}
