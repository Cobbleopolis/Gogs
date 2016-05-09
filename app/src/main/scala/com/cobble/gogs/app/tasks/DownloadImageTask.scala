package com.cobble.gogs.app.tasks

import android.graphics.{Bitmap, BitmapFactory}
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView

class DownloadImageTask(var bmImage: ImageView) extends AsyncTask[String, Void, Bitmap] {


	protected def doInBackground(urls: String*): Bitmap = {
		val urldisplay = urls(0)
		var mIcon11: Bitmap = null
		try {
			val in = new java.net.URL(urldisplay).openStream()
			mIcon11 = BitmapFactory.decodeStream(in)
		} catch {
			case e: Exception => {
				Log.e("Error", e.getMessage)
				e.printStackTrace()
			}
		}
		mIcon11
	}

	protected override def onPostExecute(result: Bitmap): Unit = {
		bmImage.setImageBitmap(result)
	}
}