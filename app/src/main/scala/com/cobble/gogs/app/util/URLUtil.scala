package com.cobble.gogs.app.util

object URLUtil {

	def createAccessTokenURL(server: String, username: String): String = {
		f"http://$server/api/v1/users/$username/tokens"
	}

	def getUserReposURL(server: String): String = {
		f"http://$server/api/v1/user/repos"
	}

}
