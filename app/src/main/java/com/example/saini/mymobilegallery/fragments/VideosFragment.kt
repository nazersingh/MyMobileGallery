package com.example.saini.mymobilegallery.fragments

import android.animation.LayoutTransition
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saini.mymobilegallery.R

class VideosFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val li = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = LayoutInflater.from(activity).inflate(R.layout.videos, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun setUpLayout() {
//        gridRecycle.setLayoutManager(GridLayoutManager(this, 3))
//        val layoutTransition = LayoutTransition()
//        layoutTransition.setDuration(500)
//        mLlCountMain.setLayoutTransition(layoutTransition)
//        GetImagesFromSdCard().execute()
    }

}