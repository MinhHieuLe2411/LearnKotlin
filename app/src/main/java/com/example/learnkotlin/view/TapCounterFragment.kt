package com.example.learnkotlin.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.learnkotlin.databinding.FragmentTapCounterBinding

class TapCounterFragment: Fragment() {
    private var count = 0

    private var _binding: FragmentTapCounterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTapCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set tap để tăng 1 lần
//        binding.btnTap.setOnClickListener {
//            count++
//            binding.tvCount.text = count.toString()
//        }

        // Set giữ để tăng liên tục
        val repeatInterval: Long = 100 // milliseconds
        var isHolding = false
        val handler = android.os.Handler()
        val runnable = object : Runnable {
            override fun run() {
                if (isHolding) {
                    count++
                    binding.tvCount.text = count.toString()
                    handler.postDelayed(this, repeatInterval)
                }
            }
        }

        binding.btnTap.setOnTouchListener { _, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    isHolding = false
                    handler.postDelayed({
                        isHolding = true
                        handler.post(runnable)
                    }, 300) // sau 300ms mới bắt đầu hold
                    true
                }
                android.view.MotionEvent.ACTION_UP,
                android.view.MotionEvent.ACTION_CANCEL -> {
                    handler.removeCallbacksAndMessages(null)
                    if (!isHolding) {
                        // Tap ngắn
                        count++
                        binding.tvCount.text = count.toString()
                    }
                    isHolding = false
                    true
                }

                else -> false
            }
        }

        binding.btnReset.setOnClickListener {
            // xử lý reset
            count = 0
            binding.tvCount.text = count.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}