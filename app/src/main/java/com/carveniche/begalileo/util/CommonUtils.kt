package com.carveniche.begalileo.util


import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.View
import android.widget.Toast

import com.google.android.material.snackbar.Snackbar
import java.util.*
import android.net.ConnectivityManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.util.Log

import java.io.File
import java.io.FileOutputStream
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.ProgressBar
import com.carveniche.begalileo.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId


fun showLongToast(msg:String,context:Context)
{
    Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
}
fun showShortToast(msg:String,context:Context)
{
    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
}

fun screenWidth(activity: Activity) : Int
{
    val display  = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(display)
    return display.widthPixels
}
fun screenHeight(activity: Activity) : Int
{
    val display  = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(display)
    return display.heightPixels
}

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Context.snackBar(message: CharSequence,view : View) =
        Snackbar.make(view,message,Snackbar.LENGTH_LONG).show()

fun String.isValidEmail(): Boolean
        = this.isNotEmpty() &&
        Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Random.getInt(range: IntRange):Int{
    return range.start + nextInt(range.last - range.start)
}

fun getPercentage(totValue:Float,percentValue:Float) : Float
{
    return (totValue/100)*percentValue
}

fun isNetworkAvailable(context: Context): Boolean {
    // Get Connectivity Manager class object from Systems Service
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Get Network Info from connectivity Manager
    val networkInfo = cm.activeNetworkInfo

    // if no network is available networkInfo will be null
    // otherwise check if we are connected
    return networkInfo != null && networkInfo.isConnected
}

fun loadBitmapFromView(v: View): Bitmap {
    if (v.measuredHeight <= 0) {
        v.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val b = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        v.layout(0, 0, v.measuredWidth, v.measuredHeight)
        v.draw(c)
        return b
    }
    else
    {
        val b =
            Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        v.layout(v.left, v.top, v.right, v.bottom)
        v.draw(c)
        return b
    }

}


 fun SaveImage(finalBitmap: Bitmap) {

    val root = Environment.getExternalStorageDirectory().absolutePath
    val myDir = File("$root/saved_images")
    myDir.mkdirs()
    var currentTime = Calendar.getInstance().time
    val fname = "Image ${currentTime.time}.png"
    val file = File(myDir, fname)
    if (file.exists()) file.delete()
    try {
        val out = FileOutputStream(file)
        finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.flush()
        out.close()


    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun showLoadingProgress(progressBar : ProgressBar, status : Boolean)
{
    progressBar.bringToFront()
    if(status)
        progressBar.visibility = View.VISIBLE
    else
        progressBar.visibility = View.GONE
}

fun getHtmlFormaatedQuestionData(data : String) : String
{
    return ("<html><head>"
            + "<style type=\"text/css\">"
            + "body{color: #ffffff;  text-align:center; font-size:30px; vertical-align:middle; }"
            + "img{ width : 50px }"
            + "</style></head>"
            + "<body>"
            + data
            + "</body></html>")
}

fun getHtmlFormattedSolution(data : String) : String
{
    return ("<html><head>"
            + "<style type=\"text/css\">"
            + "body{color: red;  text-align:center; font-size:50px; vertical-align:middle; }"
            + "img{ width : 100px }"
            + "</style></head>"
            + "<body><center>"
            + data
            + "</center></body></html>")
}

fun setWebSettingsQuiz(wvSettings: WebSettings,context: Context)
{
    wvSettings.defaultFontSize =  context.resources.getDimension(R.dimen.font_size_medium).toInt()
    wvSettings.loadWithOverviewMode = true
    wvSettings.useWideViewPort  = true
}

fun getFirebaseToken() : String
{
    var token = ""
    FirebaseInstanceId.getInstance().instanceId
        .addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(Constants.LOG_TAG, "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }

            // Get new Instance ID token
             token = task.result?.token!!

            // Log and toast

            Log.d(Constants.LOG_TAG, token)

        })
        return token

}

/*
private fun storeImage(context: Context,image: Bitmap) {
    val pictureFile = getOutputMediaFile()
    if (pictureFile == null) {
        Log.d(
            Constants.LOG_TAG,
            "Error creating media file, check storage permissions: "
        )// e.getMessage());
        return
    }
    try {
        val fos = FileOutputStream(pictureFile)
        image.compress(Bitmap.CompressFormat.PNG, 90, fos)
        fos.close()
    } catch (e: FileNotFoundException) {
        Log.d(Constants.LOG_TAG, "File not found: " + e.message)
    } catch (e: IOException) {
        Log.d(Constants.LOG_TAG, "Error accessing file: " + e.message)
    }

}
private fun getOutputMediaFile(context: Context): File? {
 // To be safe, you should check that the SDCard is mounted
    // using Environment.getExternalStorageState() before doing this.
    var externalPath = getExternalStorageDirectory().absolutePath
    */
/*val mediaStorageDir = File(
        Environment.getExternalStorageDirectory()
    + "/Android/data/"
    + context.packageName
    + "/Files"
    )*//*




 // This location works best if you want the created images to be shared
    // between applications and persist after your app has been uninstalled.

    // Create the storage directory if it does not exist
    if (!mediaStorageDir.exists())
{
if (!mediaStorageDir.mkdirs())
{
return null
}
}
 // Create a media file name
    val timeStamp = SimpleDateFormat("ddMMyyyy_HHmm").format(Date())
val mediaFile:File
val mImageName = "MI_$timeStamp.png"
mediaFile = File(mediaStorageDir.getPath() + File.separator + mImageName)
return mediaFile
}
*/




