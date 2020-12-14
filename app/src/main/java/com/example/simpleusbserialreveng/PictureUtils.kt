package com.example.simpleusbserialreveng

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point

//functions to read image from file and scale it down

fun GetScaledBitMap(path: String, destWidth: Int, destHeight: Int): Bitmap
{
    //get the demintion of the picture
    var options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(path,options)

    val srcWidth = options.outWidth.toFloat()
    val srcHeight = options.outHeight.toFloat()

    //scale the image down
    var inSampleSize = 1
    if (srcHeight > destHeight || srcWidth > destWidth) {
        val heightScale = srcHeight / destHeight
        val widthScale = srcWidth / destWidth

        val sampleScale = if (heightScale > widthScale) {
            heightScale
        } else {
            widthScale
        }

        inSampleSize = Math.round(sampleScale)
    }

    options = BitmapFactory.Options()
    options.inSampleSize = inSampleSize

    //return a scaled doen image
    return BitmapFactory.decodeFile(path,options)


}


//use this function as we don't know how big the photoView will be on the screen until the photoView is already created, thus we have to guesstimate its size
fun GetScaledBitMap(path: String, activity: Activity): Bitmap
{
    //checks how big the screen is and scale down the image to screen size
    val size = Point()
    activity.windowManager.defaultDisplay.getSize(size)

    return GetScaledBitMap(path, size.x, size.y)

}

fun UnScaledBitMap(path: String, activity: Activity): Bitmap
{

    //get the demintion of the picture
    var options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(path,options)


    options = BitmapFactory.Options()

    //return a scaled doen image
    return BitmapFactory.decodeFile(path,options)

}

