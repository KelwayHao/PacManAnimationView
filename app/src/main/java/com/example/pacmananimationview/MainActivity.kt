package com.example.pacmananimationview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.pacmananimationview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        with(binding) {
            buttonPlay.setOnClickListener {
                pacMan.startAnimation()
            }
            buttonGradient.setOnClickListener {
                pacMan.gradientPacMan()
            }
        }
    }
}
