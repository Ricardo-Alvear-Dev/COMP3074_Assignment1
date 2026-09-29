package ca.gbc.comp3074.Alvear_Ricardo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var etHoursWorked: TextInputEditText
    private lateinit var etHourlyRate: TextInputEditText
    private lateinit var etTaxRate: TextInputEditText
    private lateinit var btnCalculate: Button
    private lateinit var btnAbout: Button

    private lateinit var tvRegularPay: TextView
    private lateinit var tvOvertimePay: TextView
    private lateinit var tvTotalPay: TextView
    private lateinit var tvTax: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Views
        etHoursWorked = findViewById(R.id.etHoursWorked)
        etHourlyRate = findViewById(R.id.etHourlyRate)
        etTaxRate = findViewById(R.id.etTaxRate)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnAbout = findViewById(R.id.btnAbout)

        tvRegularPay = findViewById(R.id.tvRegularPay)
        tvOvertimePay = findViewById(R.id.tvOvertimePay)
        tvTotalPay = findViewById(R.id.tvTotalPay)
        tvTax = findViewById(R.id.tvTax)

        btnCalculate.setOnClickListener {
            calculateAndDisplayPay()
        }

        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun calculateAndDisplayPay() {
        val hoursStr = etHoursWorked.text?.toString()?.trim().orEmpty()
        val rateStr = etHourlyRate.text?.toString()?.trim().orEmpty()
        val taxStr = etTaxRate.text?.toString()?.trim().orEmpty()

        if (hoursStr.isEmpty() || rateStr.isEmpty() || taxStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val noOfHours = hoursStr.toDoubleOrNull()
        val hourlyRate = rateStr.toDoubleOrNull()
        val rawTaxRate = taxStr.toDoubleOrNull()

        if (noOfHours == null || hourlyRate == null || rawTaxRate == null) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            return
        }

        // Convert percentage to decimal if tax rate entered as e.g. 15 for 15%
        val taxRateDecimal = if (rawTaxRate > 1.0) rawTaxRate / 100.0 else rawTaxRate

        val pay: Double
        val overtimePay: Double

        if (noOfHours <= 40) {
            pay = noOfHours * hourlyRate
            overtimePay = 0.0
        } else {
            pay = 40.0 * hourlyRate
            overtimePay = (noOfHours - 40.0) * hourlyRate * 1.5
        }

        val totalPay = pay + overtimePay
        val tax = pay * taxRateDecimal

        // Display formatted results
        tvRegularPay.text = String.format(Locale.getDefault(), "Regular Pay: $%.2f", pay)
        tvOvertimePay.text = String.format(Locale.getDefault(), "Overtime Pay: $%.2f", overtimePay)
        tvTotalPay.text = String.format(Locale.getDefault(), "Total Pay (Gross): $%.2f", totalPay)
        tvTax.text = String.format(Locale.getDefault(), "Tax Deducted: $%.2f", tax)
    }
}