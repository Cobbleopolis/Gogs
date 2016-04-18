package com.cobble.gogs.app.util

import android.util.Log
import com.cobble.gogs.app.{GogsApp, R}
import com.loopj.android.http.{AsyncHttpClient, JsonHttpResponseHandler}

object GogsUtil {

	val client: AsyncHttpClient = new AsyncHttpClient()

	var protocol: String = ""

	var server: String = ""

	def getUserRepos(handler: JsonHttpResponseHandler): Unit = {
		setAuth()
		val url = protocol + server + "/api/v1/user/repos"
		Log.i("Request Url", url)
		client.get(url, handler)
	}

	private def setAuth(): Unit = {
		client.addHeader("Authorization", "token " + Prefs.getString(R.string.userData_token, ""))
		server = Prefs.getString(R.string.userData_server, "")
		protocol = Prefs.getString(R.string.userData_protocol, "")
	}

}
