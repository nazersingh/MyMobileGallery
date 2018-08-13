package com.example.saini.mymobilegallery

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.nio.file.Files.exists



object Utility {
    var contactList = ArrayList<String>()

    /**
     * Contacts Sync
     */
    fun contactSync(context: Context, callBackInterface: CallBackInterface<ArrayList<String>>): ArrayList<String> {
        try {
//            if (contactList.isEmpty()) {
            Thread(Runnable {
                val cursor: Cursor = context!!.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)

                while (cursor.moveToNext()) {

                    var name: String = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    var phonenumber: String = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//                    Log.e("contactSync ", " " + name + " " + phonenumber)
//                        contactList.add(name.toString() + " " + ":" + " " + phonenumber)
                    if (phonenumber.contains("("))
                        phonenumber= phonenumber.replace("(", "")
                    if (phonenumber.contains(")"))
                        phonenumber= phonenumber.replace(")", "")
                    if (phonenumber.contains(" "))
                        phonenumber= phonenumber.replace(" ", "")
                    contactList.add(phonenumber)
                }
                Log.e("contactSync ", " " + contactList.size)
                cursor.close()
                callBackInterface.onSuccess(contactList)
            }, "").start()
//            }
//            else
//            {
//                callBackInterface.onSuccess(contactList)
//            }
        } catch (e: Exception) {
            e.printStackTrace()
            callBackInterface.onFailure(e)
        }
        return contactList
    }


    /**
     * Getting All Videos Path.
     *
     * @param activity the activity
     * @return ArrayList with images Path
     */
    private fun getAllShownVideosPath(activity: Activity): ArrayList<String> {
        val uri: Uri
        val cursor: Cursor?
        val column_index_data: Int
        val column_index_folder_name: Int
        val listOfAllImages = java.util.ArrayList<String>()
        var absolutePathOfImage: String? = null
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME)

        cursor = activity.contentResolver.query(uri, projection, null, null, null)

        column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data)
            listOfAllImages.add(absolutePathOfImage)
        }
        cursor.close()
        return listOfAllImages
    }

    /**
     * Get All Images From Mobile
     */
    public  fun getAllShownImagesPath(activity: Activity): ArrayList<String> {
        val listOfAllImages = ArrayList<String>()
        try {
            val uri: Uri
            val cursor: Cursor?
            val column_index_data: Int
            val column_index_folder_name: Int
            var absolutePathOfImage: String? = null
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            cursor = activity.contentResolver.query(uri, projection, null, null, null)

            column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data)
                val file = File(absolutePathOfImage)
                if (file.exists())
                    if (absolutePathOfImage!!.endsWith(".jpg")
                            || absolutePathOfImage.endsWith(".png")
                            || absolutePathOfImage.endsWith(".jpeg"))
                        listOfAllImages.add(absolutePathOfImage)
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return listOfAllImages
    }

}