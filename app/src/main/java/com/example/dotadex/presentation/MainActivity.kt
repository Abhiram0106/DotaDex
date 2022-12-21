package com.example.dotadex.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dotadex.R
import com.example.dotadex.data.remote.PostService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        TODO implement dependency injection
        val service = PostService.create()
        CoroutineScope(Dispatchers.IO).launch {
            val test = service.getHeroes()
            Log.i("TEST",test.toString())
        }
    }
}