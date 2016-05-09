package com.cobble.gogs.app.gogs

import com.cobble.gogs.app.util.Util
import org.json.JSONObject

class Repository {

	var id: Int = -1

	var owner: User = null

	var fullName: String = ""

	var isPrivate: Boolean = false

	var fork: Boolean = false

	var htmlUrl: String = ""

	var cloneUrl: String = ""

	var sshUrl: String = ""

	var permissionAdmin: Boolean = false

	var permissionPush: Boolean = false

	var permissionPull: Boolean = false


}

object Repository {
	def parseFromJSON(json: JSONObject): Repository = {
		if (json != null) {
			val repo: Repository = new Repository
			repo.id = Util.getFromJSON(json, "id", null).asInstanceOf[Int]
			if (json.has("owner"))
				repo.owner = User.parseFromJSON(Util.getFromJSON(json, "owner", null).asInstanceOf[JSONObject])
			repo.fullName = Util.getFromJSON(json, "full_name", null).asInstanceOf[String]
			repo.isPrivate = Util.getFromJSON(json, "private", null).asInstanceOf[Boolean]
			repo.fork = Util.getFromJSON(json, "fork", null).asInstanceOf[Boolean]
			repo.htmlUrl = Util.getFromJSON(json, "html_url", null).asInstanceOf[String]
			repo.cloneUrl = Util.getFromJSON(json, "clone_url", null).asInstanceOf[String]
			repo.sshUrl = Util.getFromJSON(json, "ssh_url", null).asInstanceOf[String]

			repo
		} else
			null
	}
}
