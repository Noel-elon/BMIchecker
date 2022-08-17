package com.example.noelnwaelugo.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.provider.MediaStore
import android.view.View

 fun screenShot(view: View): Bitmap? {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

 fun share(bitmap: Bitmap, context: Context) {
    val pathOfBitmap: String = MediaStore.Images.Media.insertImage(
        context.contentResolver,
        bitmap, "BMI", null
    )
    val uri: Uri = Uri.parse(pathOfBitmap)
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "image/*"
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "BMI")
    shareIntent.putExtra(Intent.EXTRA_TEXT, "")
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
    context.startActivity(Intent.createChooser(shareIntent, "My BMI"))
}