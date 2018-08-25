package com.example.saini.mymobilegallery.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.saini.mymobilegallery.ImageVideoPicker
import com.example.saini.mymobilegallery.R
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import java.io.IOException


class MainActivity : AppCompatActivity() {
    var mPlayer: MediaPlayer? = null

    var TAG="MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_gallery.setOnClickListener { startActivity(Intent(this@MainActivity, GalleryActivity::class.java)) }
        bt_photos.setOnClickListener { ImageVideoPicker.startActivityPhotoFromGallary(this@MainActivity) }
        bt_videos.setOnClickListener { ImageVideoPicker.startActivityForVideoPicker(this@MainActivity) }
        bt_custom.setOnClickListener { ImageVideoPicker.startActivityForImagePicker(this@MainActivity) }
        pick_audio.setOnClickListener { ImageVideoPicker.startActivityForAudioPicker(this@MainActivity) }
        contacts.setOnClickListener { startActivity(Intent(this@MainActivity, Contacts::class.java)) }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) {
            return
        }
        if (resultCode==Activity.RESULT_OK)
        {
            if(requestCode==ImageVideoPicker.IMAGE_GALLERY||requestCode==ImageVideoPicker.IMAGE_CAMERA)
                img.setImageBitmap(ImageVideoPicker.getBitmapFromActivityResult(this@MainActivity, requestCode, resultCode, data))
            else if(requestCode==ImageVideoPicker.VIDEO_GALLERY||requestCode==ImageVideoPicker.VIDEO_CAMERA)
            {

            }
            else if((requestCode==ImageVideoPicker.AUDIO_GALLERY||requestCode==ImageVideoPicker.AUDIO_CAMERA))
            {

            }
        }

    }

    //"file:///sdcard/Songs/ARR Hits/hosannatamil.mp3"
   fun playAudio(audioPath:String)
    {
        // Even you can refer resource in res/raw directory
        //Uri myUri = Uri.parse("android.resource://com.prgguru.example/" + R.raw.hosannatamil);
        Log.e(TAG," audio "+audioPath)
        val myUri1 = Uri.parse(audioPath)
        if(mPlayer==null)
        mPlayer = MediaPlayer()
        mPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            mPlayer!!.setDataSource(applicationContext, myUri1)
        } catch (e: IllegalArgumentException) {
            Toast.makeText(applicationContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show()
        } catch (e: SecurityException) {
            Toast.makeText(applicationContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show()
        } catch (e: IllegalStateException) {
            Toast.makeText(applicationContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        try {
            mPlayer!!.prepare()
        } catch (e: IllegalStateException) {
            Toast.makeText(applicationContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Toast.makeText(applicationContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show()
        }

        mPlayer!!.start()
    }
fun stopAudio()
{
    if(mPlayer!=null && mPlayer!!.isPlaying()){
        mPlayer!!.stop();
    }
}
}
