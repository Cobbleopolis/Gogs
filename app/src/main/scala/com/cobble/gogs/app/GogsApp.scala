package com.cobble.gogs.app

import android.app.Application
import android.content.{Context, SharedPreferences}
import android.support.multidex.MultiDex
import com.cobble.gogs.app.gogs.{GogsAPI, Token, User}
import com.cobble.gogs.app.util.{GogsUtil, Prefs}

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
