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
		setAPI()
	}

	override protected def attachBaseContext(base: Context) = {
		super.attachBaseContext(base)
		MultiDex.install(this)
	}

	def setAPI(): Unit = {
		GogsAPI.setURL(Prefs.getString(R.string.userData_protocol) + Prefs.getString(R.string.userData_server))
		val authUser: User = new User()
		authUser.username = Prefs.getString(R.string.userData_username)
		authUser.token = new Token()
		authUser.token.sha1 = Prefs.getString(R.string.userData_token)
		GogsAPI.setAuthUser(authUser)
	}

}
