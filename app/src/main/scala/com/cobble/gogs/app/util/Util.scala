package com.cobble.gogs.app.util

import org.json.{JSONException, JSONObject}

object Util {

	def getFromJSON(json: JSONObject, field: String, defaultValue: Any = null): Any = {
		if (json.has(field))
			try {
				json.get(field)
			} catch {
				case e: JSONException => {
					e.printStackTrace()
					defaultValue
				}
			}
		else
			defaultValue
	}

}
