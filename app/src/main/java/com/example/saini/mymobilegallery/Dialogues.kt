package com.racestake.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.example.saini.mymobilegallery.ImageVideoPicker
import com.example.saini.mymobilegallery.R
import java.util.*


/**
 * Created by nazer on 2/2/2018.
 */

object Dialogues {
    internal var builder: AlertDialog? = null
    internal var alertDialogbuilder: AlertDialog.Builder? = null

    private var mMessage: String? = null
    private var mTitle: String? = ""
    private var mPositiveText: String? = null
    private var mNegativeText: String? = null
    private var mNeutronText: String? = null

    private var mNeutralText: String? = null
    private var titleShow: Boolean = false
    private var isCancelableDialogue: Boolean = false
    var TAG = javaClass.name;

    interface DialgogueInterfaceTwoButton<G> {
        fun onOkClick(t: G)
        fun onCancelClick(t: G)
    }

    interface DialgogueInterfaceThreeButton<G> {
        fun onOkClick(t: G)
        fun onCancelClick(t: G)
        fun onThirdClick(t: G)

    }

    interface DialgogueInterfaceOneButton<G> {
        fun onOkClick(t: G)
    }

    fun setmNeutralText(mNeutralText: String): Dialogues {
        this.mNeutralText = mNeutralText
        return this
    }

    fun setMessage(msg: String): Dialogues {
        mMessage = msg
        return this
    }

    fun setTitle(title: String): Dialogues {
        mTitle = title
        return this
    }

    fun isCancelAble(isCancelableDialogue: Boolean): Dialogues {
        this.isCancelableDialogue = isCancelableDialogue
        return this
    }

    fun setPositiveButtonText(positiveButtonText: String): Dialogues {
        mPositiveText = positiveButtonText
        return this
    }

    fun setNegativeButtonText(positiveNegativeText: String): Dialogues {
        mNegativeText = positiveNegativeText
        return this
    }

    fun setNeutronButtonText(positiveNegativeText: String): Dialogues {
        mNeutronText = positiveNegativeText
        return this
    }

    fun isTitleShow(isTitle: Boolean): Dialogues {
        titleShow = isTitle
        return this
    }

    fun getBuilder(context: Context): AlertDialog.Builder? {
//        if (alertDialogbuilder == null)
        alertDialogbuilder = AlertDialog.Builder(context)
        return alertDialogbuilder
    }

    fun getAlert(context: Context): AlertDialog? {
        builder = getBuilder(context)!!
                .setTitle(mTitle)
                .setCancelable(isCancelableDialogue)
                .setMessage(mMessage)
                .setPositiveButton(mPositiveText) { dialog, which -> dialog.dismiss() }.create()
        return builder;
    }

    fun showDialogueOnlyText(context: Context) {
        builder = getAlert(context)
        builder!!.show()
    }

    fun showDialogueTextWithOneButton(context: Context, dialgogueInterfaceOneButton: DialgogueInterfaceOneButton<String>) {
        builder = getBuilder(context)!!
                .setTitle(mTitle)
                .setMessage(mMessage)
                .setCancelable(isCancelableDialogue)
                .setPositiveButton(mPositiveText) { dialog, which ->
                    dialgogueInterfaceOneButton.onOkClick("")
                    dialog.dismiss()
                }.create()
        if (!builder!!.isShowing)
            builder!!.show()
        else
            builder!!.dismiss()

    }


    fun showDialogueNoInternet(context: Context) {
        builder = getBuilder(context)!!
                .setTitle(context.getString(R.string.app_name))
                .setMessage(context.getString(R.string.no_internet))
                .setCancelable(isCancelableDialogue)
                .setPositiveButton(mPositiveText) { dialog, which ->
                    dialog.dismiss()
//                    if (!Utility.isInternetWorking(context)) {
//                        val intent = Intent(Intent.ACTION_MAIN)
//                        intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting")
//                        context.startActivity(intent)
//                    }
                }.create()
        if (!builder!!.isShowing)
            builder!!.show()
    }

    fun showDialogueTextWithButton(context: Context, dialgogueInterfaceTwoButton: DialgogueInterfaceTwoButton<String>) {
        builder = getBuilder(context)!!
                .setTitle(mTitle)
                .setMessage(mMessage)
                .setCancelable(isCancelableDialogue)
                .setPositiveButton(mPositiveText) { dialog, which ->
                    dialgogueInterfaceTwoButton.onOkClick("")
                    dialog.dismiss()
                }.setNegativeButton(mNegativeText) { dialog, which ->
                    dialgogueInterfaceTwoButton.onCancelClick("")
                    dialog.dismiss()
                }.create()
        if (!builder!!.isShowing)
            builder!!.show()

    }


    fun showDialgogueMultiChoiceValues(context: Context, dialgogueInterface: DialgogueInterfaceTwoButton<String>) {
        //        String[] multiChoiceItems = context.getResources().getStringArray(R.array.dialog_multi_choice_array);
        val multiChoiceItems = arrayOf("one", "two", "three", "four")
        val checkedItems = booleanArrayOf(false, false, false, false)
        getBuilder(context)!!
                .setTitle(mTitle)
                .setMultiChoiceItems(multiChoiceItems, checkedItems) { dialog, index, isChecked -> }.setPositiveButton(mPositiveText) { dialog, which ->
                    dialgogueInterface.onOkClick("")
                    dialog.dismiss()
                }.setNegativeButton(mNegativeText) { dialog, which ->
                    dialog.dismiss()
                    dialgogueInterface.onCancelClick("")
                }.show()
    }

    fun showDialgogueSingleChoiceValue(context: Context, list: ArrayList<String>, dialgogueInterface: DialgogueInterfaceOneButton<Int>) {
        val array = list.toArray(arrayOfNulls<String>(list.size))

        getBuilder(context)!!
                .setTitle(mTitle)
                .setItems(array, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialgogueInterface.onOkClick(which)
                        dialog!!.dismiss()
                    }
                }).show()

    }

    fun showDialgogueSingleChoiceWithButtons(context: Context, list: Array<String>, dialgogueInterface: DialgogueInterfaceOneButton<Int>) {
//        val singleChoiceItems = arrayOf("LEFT", "RIGHT", "CENTRE")
        // val array = list.toArray(arrayOfNulls<String>(list.size))
        var itemSelected = 0
        getBuilder(context)!!
                .setTitle(mTitle)
                .setPositiveButton(mPositiveText) { dialog, which ->
                    dialgogueInterface.onOkClick(itemSelected)
                    dialog.dismiss()
                }
                .setNegativeButton(mNegativeText) { dialog, which ->
                    dialog.dismiss()
                }.setSingleChoiceItems(list, itemSelected) { dialogInterface1, selectedIndex ->
                    itemSelected = selectedIndex
                    dialgogueInterface.onOkClick(itemSelected)
                    /*  dialgogueInterface.onOkClick(itemSelected)
                      dialog.dismiss()*/

                }.show()
    }
//    companion object {
//
//        internal val dialogues: Dialogues
//
//
//        fun with(): Dialogues {
//            return if (dialogues == null) Dialogues() else dialogues
//        }
//    }

    /**
     * Picture Picker Dialogue
     */
     fun showImagePickerDialog(activity: Activity) {
        Dialogues.getBuilder(activity)?.setTitle("Select Action")
                ?.setMessage("Select photo from gallery \n Capture photo from camera")
                ?.setPositiveButton("Gallery") { dialog, which ->
                    ImageVideoPicker.startActivityPhotoFromGallary(activity)
                }
                ?.setNegativeButton("Camera") { dialog, which ->
                    ImageVideoPicker.startActivityPhotoFromCamera(activity)
                }?.setNeutralButton(mNeutralText) { dialog, which ->
                    dialog.dismiss()
                }?.show()
    }
    /**
     * Video Picker Dialogue
     */
    fun showVideoPickerDialog(activity: Activity) {
        Dialogues.getBuilder(activity)?.setTitle("Select Action")
                ?.setMessage("Select video from gallery \n Capture video from camera")
                ?.setPositiveButton("Gallery") { dialog, which ->
                    ImageVideoPicker.startActivityVideoFromGallary(activity)
                }
                ?.setNegativeButton("Camera") { dialog, which ->
                    ImageVideoPicker.startActivityVideoFromCamera(activity)
                }?.setNeutralButton(mNeutralText) { dialog, which ->
                    dialog.dismiss()
                }?.show()
    }

    /**
     * Video and Image Picker Dialogue
     */
    fun showGalleryPickerDialog(activity: Activity) {
        Dialogues.getBuilder(activity)?.setTitle(mTitle)
                ?.setMessage(mMessage)
                ?.setPositiveButton(mPositiveText) { dialog, which ->
                    showImagePickerDialog(activity)
                }
                ?.setNegativeButton(mNegativeText) { dialog, which ->
                   showVideoPickerDialog(activity)
                }?.setNeutralButton(mNeutralText) { dialog, which ->
                    dialog.dismiss()
                }?.show()
    }
    /**
     * Audio Picker Dialogue
     */
    fun showAudioPickerDialog(activity: Activity) {
        Dialogues.getBuilder(activity)?.setTitle(mTitle)
                ?.setMessage("Select Audio from gallery \n Record audio from camera")
                ?.setPositiveButton(mPositiveText) { dialog, which ->
                    ImageVideoPicker.startActivityAudioFromGallary(activity)
                }
                ?.setNegativeButton(mNegativeText) { dialog, which ->
                    ImageVideoPicker.startActivityAudioFromRecorded(activity)
                }?.setNeutralButton(mNeutralText) { dialog, which ->
                    dialog.dismiss()
                }?.show()
    }
}
