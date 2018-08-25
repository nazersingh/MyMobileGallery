package com.example.saini.mymobilegallery

import android.app.Activity
import android.content.Intent
import com.racestake.utils.Dialogues
import android.media.MediaScannerConnection
import android.graphics.Bitmap
import android.provider.MediaStore
import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


object ImageVideoPicker {
     val IMAGE_GALLERY = 1
     val IMAGE_CAMERA = 2

     val VIDEO_GALLERY = 3
     val VIDEO_CAMERA = 4

    val AUDIO_GALLERY = 3
    val AUDIO_CAMERA = 4
     val IMAGE_DIRECTORY = "/MyProject"

    /**
     * Image Picker
     */
    fun startActivityForImagePicker(activity: Activity) {
        Dialogues.setPositiveButtonText("Gallery")
                .setNegativeButtonText("Camera")
                .showImagePickerDialog(activity)
    }

    /**
     * Video Picker
     */
    fun startActivityForVideoPicker(activity: Activity) {
        Dialogues.setPositiveButtonText("Gallery")
                .setNegativeButtonText("Camera")
                .showVideoPickerDialog(activity)
    }

    /**
     * Audio Picker
     */
    fun startActivityForAudioPicker(activity: Activity) {
        Dialogues.setPositiveButtonText("Gallery")
                .setNegativeButtonText("Record")
                .showAudioPickerDialog(activity)
    }

    /**
     * Audio from gallery
     */
    fun startActivityAudioFromGallary(activity: Activity) {

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(intent, AUDIO_GALLERY)
    }

    /**
     * Audio From Recorded
     */
    fun startActivityAudioFromRecorded(activity: Activity) {

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(intent, AUDIO_CAMERA)
    }
    /**
     * Gallery Image
     */
    fun startActivityPhotoFromGallary(activity: Activity) {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, IMAGE_GALLERY)
    }

    /***
     * Camera Image capture
     */
    fun startActivityPhotoFromCamera(activity: Activity) {
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(intent, IMAGE_CAMERA)
    }

    /**
     * Gallery Video
     */
    fun startActivityVideoFromGallary(activity: Activity) {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, VIDEO_GALLERY)
    }

    /**
     * Camera Video capture
     */
    fun startActivityVideoFromCamera(activity: Activity) {
        val intent = Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE)
        activity.startActivityForResult(intent, VIDEO_CAMERA)
    }

    /**
     * get Bitmap from Activity Result
     */
    fun getBitmapFromActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?): Bitmap? {

        if (requestCode == IMAGE_GALLERY) {
            if (data != null) {
                val contentURI = data.data
                return MediaStore.Images.Media.getBitmap(activity.getContentResolver(), contentURI)
            }

        } else if (requestCode == IMAGE_CAMERA) {
            return data!!.extras!!.get("data") as Bitmap
        }
        return null
    }

    /**
     * Save image
     */
    fun saveImage(context: Context, myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(Environment.getExternalStorageDirectory().toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }

        try {
            val f = File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis().toString() + ".jpg")
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(context, arrayOf(f.getPath()), arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())
            return f.getAbsolutePath()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return ""
    }


    /**
     * get Audio from Activity Result
     */
    fun getAudioFromActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?): Bitmap? {

        if (requestCode == AUDIO_GALLERY) {
            if (data != null) {
                val contentURI = data.data
                return MediaStore.Images.Media.getBitmap(activity.getContentResolver(), contentURI)
            }

        } else if (requestCode == AUDIO_CAMERA) {
            return data!!.extras!!.get("data") as Bitmap
        }
        return null
    }
    /**
     * get Video from Activity Result
     */
    fun getVideoFromActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?): Bitmap? {

        if (requestCode == AUDIO_GALLERY) {
            if (data != null) {
                val contentURI = data.data
                return MediaStore.Images.Media.getBitmap(activity.getContentResolver(), contentURI)
            }

        } else if (requestCode == AUDIO_CAMERA) {
            return data!!.extras!!.get("data") as Bitmap
        }
        return null
    }
}