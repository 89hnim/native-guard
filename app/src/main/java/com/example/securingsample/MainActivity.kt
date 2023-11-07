package com.example.securingsample

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.native_guard.NativeGuard
import com.example.securingsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener {
            val inputKey = binding.inputSaveKey.text.toString()
            val inputValue = binding.inputSaveValue.text.toString()
            NativeGuard.save(inputKey, inputValue)
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
        }

        binding.buttonGet.setOnClickListener {
            val inputKey = binding.inputGetKey.text.toString()
            val data = NativeGuard.get(inputKey)
            binding.textResult.text = "Value: $data"
        }
    }
}