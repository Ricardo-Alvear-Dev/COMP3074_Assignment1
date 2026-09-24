package ca.gbc.comp3074.Alvear_Ricardo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var etHoursWorked: TextInputEditText
    private lateinit var etHourlyRate: TextInputEditText
    private lateinit var etTaxRate: TextInputEditText
    private lateinit var btnAbout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etHoursWorked = findViewById(R.id.etHoursWorked)
        etHourlyRate = findViewById(R.id.etHourlyRate)
        etTaxRate = findViewById(R.id.etTaxRate)
        btnAbout = findViewById(R.id.btnAbout)

        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }

    // Helper method to read and validate the input values
    private fun readInputs(): UserInputs? {
        val hoursStr = etHoursWorked.text?.toString()?.trim().orEmpty()
        val rateStr = etHourlyRate.text?.toString()?.trim().orEmpty()
        val taxStr = etTaxRate.text?.toString()?.trim().orEmpty()

        if (hoursStr.isEmpty() || rateStr.isEmpty() || taxStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return null
        }

        val hours = hoursStr.toDoubleOrNull()
        val rate = rateStr.toDoubleOrNull()
        val tax = taxStr.toDoubleOrNull()

        if (hours == null || rate == null || tax == null) {
            Toast.makeText(this, "Please enter valid numeric values", Toast.LENGTH_SHORT).show()
            return null
        }

        return UserInputs(hoursWorked = hours, hourlyRate = rate, taxRate = tax)
    }

    data class UserInputs(
        val hoursWorked: Double,
        val hourlyRate: Double,
        val taxRate: Double
    )
}