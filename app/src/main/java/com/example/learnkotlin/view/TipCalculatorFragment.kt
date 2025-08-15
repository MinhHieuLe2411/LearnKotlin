package com.example.learnkotlin.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.learnkotlin.databinding.FragmentTipAlculatorBinding
import android.widget.SeekBar
import android.text.Editable
import android.text.TextWatcher

class TipCalculatorFragment: Fragment() {

    private var _binding: FragmentTipAlculatorBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTipAlculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etBillAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateTipAndTotal()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val tipPercent = progress
                val input = binding.etBillAmount.text.toString()
                val amount = input.toDoubleOrNull() ?: 0.0
                val tip = amount * tipPercent / 100
                val total = amount + tip

                binding.tvTipLabel.text = "Tip (${tipPercent}%)"
                binding.tvTipAmount.text = "$%.2f".format(tip)
                binding.tvTotalAmount.text = "$%.2f".format(total)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateTipAndTotal() {
        val amount = binding.etBillAmount.text.toString().toDoubleOrNull() ?: 0.0
        val tipRate = binding.seekBarTip.progress / 100.0
        val tip = amount * tipRate
        val total = amount + tip

        binding.tvTipLabel.text = "Tip (${(tipRate * 100).toInt()}%)"
        binding.tvTipAmount.text = "$%.2f".format(tip)
        binding.tvTotalAmount.text = "$%.2f".format(total)
    }
}