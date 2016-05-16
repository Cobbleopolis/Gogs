package com.cobble.gogs.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.cobble.gogs.app.gogs.GogsAPI
import com.cobble.gogs.app.util.Prefs

class GogsApp extends Application {

	override def onCreate(): Unit = {
		super.onCreate()
		Prefs.initalize(getApplicationContext)
		GogsAPI.setAPIFromPrefs()
	}

	override protected def attachBaseContext(base: Context) = {
		super.attachBaseContext(base)
		MultiDex.install(this)
	}

}
