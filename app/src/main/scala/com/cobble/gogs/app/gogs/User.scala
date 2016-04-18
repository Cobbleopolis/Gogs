package com.cobble.gogs.app.gogs

import java.io.UnsupportedEncodingException

import android.util.Base64
import com.cobble.gogs.app.util.Util
import org.json.JSONObject

class User {

	/**
	  * The ID of the user
	  */
	var id: Int = -1

	/**
	  * The username of the user
	  */
	var username: String = ""

	/**
	  * The password of the user. This is only used when getting the authentication for a user when a token is not provided.
	  */
	private var password: String = ""

	/**
	  * The user's full name
	  */
	var fullName: String = ""

	/**
	  * The email of the user. This is empty without authentication
	  */
	var email: String = ""

	/**
	  * The url to the user's profile picture
	  */
	var avatarUrl: String = ""

	/**
	  * The token used to authenticate this user
	  */
	var token: Token = null

	/**
	  * The login name of the user. This will be `null` if one is not provided.
	  */
	var loginName: String = null

	/**
	  * The website of the user. This will be `null` if one is not provided.
	  */
	var website: String = null

	/**
	  * The location of the user. This will be `null` if one is not provided.
	  */
	var location: String = null

	/**
	  * `true` if the user is active, otherwise `false`. This will be `false` if one is not provided.
	  */
	var active: Boolean = false

	/**
	  * `true` if the user is an admin, otherwise `false`. This will be `false` if one is not provided.
	  */
	var admin: Boolean = false

	var allowImportLocal: Boolean = false

	var allowGitHook: Boolean = false

	def setPassword(pass: String): Unit = {
		password = pass
	}

	def getPassword: String = password

	def getAuthentication: String = {
		if(token != null)
			"token " + token
		else if (!username.isEmpty && !password.isEmpty) {
			val credentials: String = username + ":" + password
			try {
				"Basic " + Base64.encodeToString(credentials.getBytes("UTF-8"), Base64.NO_WRAP)
			} catch {
				case e: UnsupportedEncodingException => {
					e.printStackTrace()
					null
				}
			}
		} else
			null
	}

}

object User {
	def parseFromJSON(json: JSONObject): User = {
		if(json != null) {
			val user = new User()
			user.username = Util.getFromJSON(json, "username", null).asInstanceOf[String]
			user.email = Util.getFromJSON(json, "email", null).asInstanceOf[String]
			user.token = Util.getFromJSON(json, "token", null).asInstanceOf[Token]
			user.avatarUrl = Util.getFromJSON(json, "avatar_url", null).asInstanceOf[String]
			user.fullName = Util.getFromJSON(json, "full_name", null).asInstanceOf[String]
			user.loginName = Util.getFromJSON(json, "login_name", null).asInstanceOf[String]
			user.website = Util.getFromJSON(json, "website", null).asInstanceOf[String]
			user.location = Util.getFromJSON(json, "location", null).asInstanceOf[String]
			user.active = Util.getFromJSON(json, "active", true).asInstanceOf[Boolean]
			user.admin = Util.getFromJSON(json, "admin", false).asInstanceOf[Boolean]
			user.allowGitHook = Util.getFromJSON(json, "allow_git_hook", true).asInstanceOf[Boolean]
			user.allowImportLocal = Util.getFromJSON(json, "allow_import_local", true).asInstanceOf[Boolean]
			return user
		} else {
			null
		}
	}
}
