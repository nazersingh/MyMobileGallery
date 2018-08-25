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
import android.R.attr.data
import android.media.MediaRecorder
import android.os.Environment.getExternalStorageDirectory
import java.io.File


class MainActivity : AppCompatActivity() {

    var TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_gallery.setOnClickListener { startActivity(Intent(this@MainActivity, GalleryActivity::class.java)) }
        bt_photos.setOnClickListener { ImageVideoPicker.startActivityForImagePicker(this@MainActivity) }
        bt_videos.setOnClickListener { ImageVideoPicker.startActivityForVideoPicker(this@MainActivity) }
        bt_custom.setOnClickListener { ImageVideoPicker.OpenDiaLogueImageVideo(this@MainActivity) }
        pick_audio.setOnClickListener { ImageVideoPicker.startActivityForAudioPicker(this@MainActivity, AudioRecorderCallBack) }
        contacts.setOnClickListener { startActivity(Intent(this@MainActivity, Contacts::class.java)) }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e(TAG, " onActivityResult " + requestCode + " " + resultCode)

        if (resultCode == Activity.RESULT_CANCELED) {
            return
        }
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ImageVideoPicker.IMAGE_GALLERY || requestCode == ImageVideoPicker.IMAGE_CAMERA) {
                img.setImageBitmap(ImageVideoPicker.getBitmapFromActivityResult(this@MainActivity, requestCode, resultCode, data))
                if (requestCode == ImageVideoPicker.IMAGE_CAMERA)
                    Log.e(TAG, " save camera image file " + ImageVideoPicker.saveImageAndGetPath(this@MainActivity, ImageVideoPicker.getBitmapFromActivityResult(this@MainActivity, requestCode, resultCode, data)))
                else
                    Log.e(TAG, " gallery image file " + ImageVideoPicker.getRealPathFromUri(this@MainActivity, data?.data!!))
            }
            if (requestCode == ImageVideoPicker.VIDEO_GALLERY || requestCode == ImageVideoPicker.VIDEO_CAMERA) {
                if (requestCode == ImageVideoPicker.VIDEO_CAMERA) {
                    Log.e(TAG, "Camera Video File " + ImageVideoPicker.getRealPathFromUri(this@MainActivity,data?.getData()))
                } else {
                    playVideo(ImageVideoPicker.getVideoFromActivityResult(this@MainActivity, requestCode, resultCode, data)!!)
                    Log.e(TAG, " gallery Video file " + ImageVideoPicker.getRealPathFromUri(this@MainActivity, data?.data!!))
                }
            }
            if (requestCode == ImageVideoPicker.AUDIO_GALLERY ) {
                ImageVideoPicker.playAudio(ImageVideoPicker.getAudioFromActivityResult(this@MainActivity, requestCode, resultCode, data)!!,this@MainActivity)
                Log.e(TAG," Audio File is "+ImageVideoPicker.getRealPathFromUri(this@MainActivity,data?.data))
            }
        }

    }




    override fun onBackPressed() {
        super.onBackPressed()
        ImageVideoPicker.stopAudio()
    }

    /**
     * Call cack of Audio Recorder
     */
    var AudioRecorderCallBack: ImageVideoPicker.AudioRecorderCallBack = object : ImageVideoPicker.AudioRecorderCallBack {
        override fun onRecordingSatrt() {
            Toast.makeText(this@MainActivity, "start recording", Toast.LENGTH_SHORT).show()
        }

        override fun onRecordingStop(uri: Uri) {
            Log.e(TAG," Audio OutPutFile "+ File(uri.path))
            ImageVideoPicker.playAudio(uri,this@MainActivity)
            Toast.makeText(this@MainActivity, "Stop recording", Toast.LENGTH_SHORT).show()
        }
    }

    fun playVideo(uri: Uri) {
        val intent = Intent(android.content.Intent.ACTION_VIEW, uri)
//        intent.setDataAndType(uri, "video/mp4")
        startActivity(intent)
    }

}
