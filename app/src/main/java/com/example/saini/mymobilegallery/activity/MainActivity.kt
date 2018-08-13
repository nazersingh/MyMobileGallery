package com.example.saini.mymobilegallery.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.saini.mymobilegallery.PermissionUtils
import com.example.saini.mymobilegallery.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_gallery.setOnClickListener { startActivity(Intent(this@MainActivity, GalleryActivity::class.java)) }
        bt_photos.setOnClickListener { startActivity(Intent(this@MainActivity, GalleryActivity::class.java)) }
        bt_videos.setOnClickListener { startActivity(Intent(this@MainActivity, GalleryActivity::class.java)) }
        contacts.setOnClickListener { startActivity(Intent(this@MainActivity, Contacts::class.java)) }
    }

}
