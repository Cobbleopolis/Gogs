package com.cobble.gogs.app.gogs

import com.cobble.gogs.app.util.Util
import org.json.JSONObject

class Token(var name: String = null) {

	var sha1: String = null

	override def toString: String = sha1

}

object Token {

	def parseFromJSON(json: JSONObject): Token = {
		if (json != null) {
			val token = new Token
			token.name = Util.getFromJSON(json, "name", null).asInstanceOf[String]
			token.sha1 = Util.getFromJSON(json, "sha1", null).asInstanceOf[String]
			token
		} else
			null
	}

}
