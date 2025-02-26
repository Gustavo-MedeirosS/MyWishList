package com.example.mywishlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mywishlist.presentation.routes.Navigation
import com.example.mywishlist.ui.theme.MyWishListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            MyWishListTheme {
                Navigation()
            }
        }
    }
}

