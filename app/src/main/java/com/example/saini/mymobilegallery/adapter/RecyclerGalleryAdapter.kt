package com.example.saini.mymobilegallery.adapter

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.saini.mymobilegallery.R
import kotlinx.android.synthetic.main.custom_gallery.view.*

import java.io.File
import java.util.ArrayList
import android.R.attr.thumbnail
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop



/**
 * Created by nazer on 9/8/2017.
 */

class RecyclerGalleryAdapter(var fragment:Fragment, private var list:ArrayList<String>) : RecyclerView.Adapter<RecyclerGalleryAdapter.MyViewHolder>() {

    var galleryTypeData=""


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    fun setGalleryType(galleryType:String)
    {
        galleryTypeData=galleryType
        notifyDataSetChanged()
    }
    fun setList(listdata:ArrayList<String>)
    {
        list=listdata
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_gallery, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


//        holder.itemView.gallery_img

        if(!list.isEmpty()) {
            Glide.with(fragment)
                    .load(File(list.get(position)))
                    .into(holder.itemView.gallery_img)

                Log.e("folder name ", "" + position + "" + File(list.get(position)).parentFile.name)
                Log.e("file name ", "" + position + "" + File(list.get(position)))
            } else {
                Log.e("else ", "" + position)
            }
        }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

}