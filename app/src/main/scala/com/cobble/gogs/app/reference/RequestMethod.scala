package com.cobble.gogs.app.reference

object RequestMethod extends Enumeration {
	type RequestMethod = Value
	val GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS = RequestMethod
}
