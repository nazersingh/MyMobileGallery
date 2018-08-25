package com.example.saini.mymobilegallery.fragments

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.AsyncTask.execute
import android.animation.LayoutTransition
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.content.Context.LAYOUT_INFLATER_SERVICE
import com.example.saini.mymobilegallery.PermissionUtils
import com.example.saini.mymobilegallery.R
import com.example.saini.mymobilegallery.Utility
import com.example.saini.mymobilegallery.activity.GalleryActivity
import java.nio.file.Files.size
import android.os.AsyncTask
import android.util.Log
import com.example.saini.mymobilegallery.adapter.RecyclerGalleryAdapter
import kotlinx.android.synthetic.main.photos.*
import kotlinx.android.synthetic.main.photos.view.*


class PhotosFragment : Fragment() {
val LOG_TAG="PhotosFragment"
    private val permissions = ArrayList<String>()//Permissions arraylist
    var permissionUtils: PermissionUtils? = null

    var arrayList=ArrayList<String>()
    lateinit var adapter:RecyclerGalleryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val li = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = LayoutInflater.from(activity).inflate(R.layout.photos, null)
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        permissions.add(Manifest.permission.RECORD_AUDIO)

        view.galleryGridView.setLayoutManager(GridLayoutManager(context!!, 3))
        var layoutTransition:LayoutTransition  = LayoutTransition()
        layoutTransition.setDuration(500)
        view.ll_main.setLayoutTransition(layoutTransition)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter=RecyclerGalleryAdapter(this@PhotosFragment,arrayList)
        view.galleryGridView.adapter=adapter
        checkPermissions()
    }

    /**
     * check permission of contacts
     */
    fun checkPermissions() {
        (context as GalleryActivity).checkPermissions(permissions, "Gallery permissions required", PermissionUtils.REQUEST_PERMISSION_READ, permissionResultCallback)
    }

    /**
     * call back of permissions then hit api
     */
    var permissionResultCallback: PermissionUtils.CommonPermissionResultCallback = object : PermissionUtils.CommonPermissionResultCallback {
        override fun PermissionGranted(request_code: Int) {
            GetImagesFromSdCard().execute()
        }
    }

    private inner class GetImagesFromSdCard : AsyncTask<Void, Void, ArrayList<String>>() {

        override fun onPreExecute() {
            Log.e(LOG_TAG, " : - onPreExecute "+Thread.currentThread().id)
        }

        override fun doInBackground(vararg params: Void): ArrayList<String>? {
            try {
                arrayList = Utility.getAllShownImagesPath(context as GalleryActivity)
                Log.e(LOG_TAG, " : -  doInBackground" + "" + arrayList.size+" "+Thread.currentThread().id)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return arrayList
        }

        override fun onPostExecute(result: ArrayList<String>) {
            try {
                Log.e(LOG_TAG, " : - onPostExecute")
                view?.nameGallery?.text=result.size.toString()
                if (arrayList.size > 0) {
                    adapter.setList(arrayList)
                    adapter.notifyDataSetChanged()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}