package com.example.saini.mymobilegallery.activity

import android.Manifest
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.saini.mymobilegallery.CallBackInterface
import com.example.saini.mymobilegallery.PermissionUtils
import com.example.saini.mymobilegallery.R
import com.example.saini.mymobilegallery.Utility
import com.example.saini.mymobilegallery.adapter.ViewPagerAdapter
import com.example.saini.mymobilegallery.fragments.PhotosFragment
import com.example.saini.mymobilegallery.fragments.VideosFragment
import kotlinx.android.synthetic.main.contacts_list.*
import kotlinx.android.synthetic.main.gallery_layout.*

class GalleryActivity :AppCompatActivity() {

    var fragmentList=ArrayList<Fragment>()
    var fragmentListTitle=ArrayList<String>()

    private val permissions = ArrayList<String>()//Permissions arraylist
    var permissionUtils: PermissionUtils? = null
    lateinit var permissionResultCallback: PermissionUtils.CommonPermissionResultCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_layout)

        fragmentList.add(PhotosFragment())
        fragmentList.add(VideosFragment())
        fragmentListTitle.add("Photos")
        fragmentListTitle.add("Videos")

        val adapter=ViewPagerAdapter(supportFragmentManager,fragmentList,fragmentListTitle)
        view_pager.adapter=adapter
        tab_layout.setupWithViewPager(view_pager)

        permissionUtils = PermissionUtils(MainActivity@ this, callback)


    }

    fun checkPermissions(permissi: ArrayList<String>, permissionMsg: String, requestCode: Int, requestPermissionsResultCallback: PermissionUtils.CommonPermissionResultCallback) {
        permissionResultCallback = requestPermissionsResultCallback
        permissionUtils!!.check_permission(permissi, permissionMsg, requestCode)
    }


    var callback: PermissionUtils.PermissionResultCallback = object : PermissionUtils.PermissionResultCallback {
        override fun PermissionGranted(request_code: Int) {
            permissionResultCallback.PermissionGranted(request_code)
        }

        override fun PartialPermissionGranted(request_code: Int, granted_permissions: java.util.ArrayList<String>) {

        }

        override fun PermissionDenied(request_code: Int) {

        }

        override fun NeverAskAgain(request_code: Int) {

        }

    }

}