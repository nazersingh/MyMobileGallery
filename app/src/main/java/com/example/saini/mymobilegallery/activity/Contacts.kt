package com.example.saini.mymobilegallery.activity

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.saini.mymobilegallery.CallBackInterface
import com.example.saini.mymobilegallery.PermissionUtils
import com.example.saini.mymobilegallery.R
import com.example.saini.mymobilegallery.Utility
import kotlinx.android.synthetic.main.contacts_list.*

class Contacts : AppCompatActivity() {
    private val permissions = ArrayList<String>()//Permissions arraylist
    var permissionUtils: PermissionUtils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contacts_list)
        permissionUtils = PermissionUtils(MainActivity@ this, callback)

        permissions.add(Manifest.permission.READ_CONTACTS)


    }

    override fun onResume() {
        super.onResume()
        checkPermissions()
    }

    /**
     * check permission of contacts
     */
    fun checkPermissions() {
        permissionUtils!!.check_permission(permissions, "Contact permissions required", PermissionUtils.REQUEST_PERMISSION)
    }


    var callback: PermissionUtils.PermissionResultCallback = object : PermissionUtils.PermissionResultCallback {
        override fun PermissionGranted(request_code: Int) {

            Utility.contactSync(this@Contacts, object : CallBackInterface<ArrayList<String>> {
                override fun onSuccess(contactList: ArrayList<String>) {
                    runOnUiThread {
                        contacts_data.text = contactList.toString()
                    }

                    Log.e("Main Acticity ", " " + contactList)

                }

                override fun onFailure(throwable: Throwable) {

                }
            })
        }

        override fun PartialPermissionGranted(request_code: Int, granted_permissions: java.util.ArrayList<String>) {

        }

        override fun PermissionDenied(request_code: Int) {

        }

        override fun NeverAskAgain(request_code: Int) {

        }

    }
}