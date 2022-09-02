package com.example.risingcampw4

import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.risingcampw4.Model.ActivityViewModel
import com.example.risingcampw4.Model.FragmentViewModel
import com.example.risingcampw4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel = ViewModelProvider(this)[ActivityViewModel::class.java]
        viewModel.message.observe(this, Observer { showGameOver() })

        mediaPlayer = MediaPlayer.create(this, R.raw.bgm)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    override fun onStop() {
        super.onStop()

        showGameOver()
    }

    private fun showGameOver() {
        mediaPlayer.stop()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("알림")
            .setMessage("게임을 다시 시작합니다.")
            .setPositiveButton("확인", DialogInterface.OnClickListener { _, i ->
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            })
        builder.show()
    }
}