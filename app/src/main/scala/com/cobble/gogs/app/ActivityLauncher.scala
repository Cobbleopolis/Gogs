package com.cobble.gogs.app

import android.content.Intent
import android.os.Bundle
import com.cobble.gogs.app.gogs.{GogsAPI, Token, User}
import com.cobble.gogs.app.util.Prefs

class ActivityLauncher extends CobbleActivity {

	protected override def onCreate(savedInstanceState: Bundle): Unit = {
		super.onCreate(savedInstanceState)

		val server: String = Prefs.getString(R.string.userData_server)
		val protocol: String = Prefs.getString(R.string.userData_protocol)
		val username: String = Prefs.getString(R.string.userData_username)
		val token: String = Prefs.getString(R.string.userData_token)

		if (!server.isEmpty && !protocol.isEmpty && !username.isEmpty && !token.isEmpty)
			GogsAPI.getUser(user => {
				if (user.email.isEmpty)
					authFailed()
				else
					authSuccessful()
			})
		else
			authFailed()

	}

	def authSuccessful(): Unit = {
		startActivity(new Intent(this, classOf[MainActivity]))
		finish()
	}

	def authFailed(): Unit = {
		startActivity(new Intent(this, classOf[LoginActivity]))
		finish()
	}

}
