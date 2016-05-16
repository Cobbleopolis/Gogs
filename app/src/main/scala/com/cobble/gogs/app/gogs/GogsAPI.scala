package com.cobble.gogs.app.gogs

import android.util.Log
import com.cobble.gogs.app.R
import com.cobble.gogs.app.reference.RequestMethod
import com.cobble.gogs.app.util.Prefs
import com.loopj.android.http.{AsyncHttpClient, JsonHttpResponseHandler, RequestParams}
import cz.msebera.android.httpclient.Header
import org.json.{JSONArray, JSONObject}

object GogsAPI {

	var client: AsyncHttpClient = new AsyncHttpClient()
	var readTimeout: Int = 5000
	var connectionTimeout: Int = 5000
	private var url: String = ""
	private var authUser: User = null

	def setAPIFromPrefs(): Unit = {
		setURL(Prefs.getString(R.string.userData_protocol) + Prefs.getString(R.string.userData_server))
		val authUser: User = new User()
		authUser.username = Prefs.getString(R.string.userData_username)
		authUser.token = new Token()
		authUser.token.sha1 = Prefs.getString(R.string.userData_token)
		setAuthUser(authUser)
	}

	def setURL(apiUri: String): Unit = {
		url = apiUri.replaceAll("/+$", "") + "/"
	}

	def setAuthUser(user: User): Unit = {
		authUser = user
	}

	def getUserRepos(callback: => Array[Repository] => Unit, authUser: User = authUser): Unit = {
		request("api/v1/user/repos", authUser, new JsonHttpResponseHandler() {

			override def onSuccess(statusCode: Int, headers: Array[Header], responseBody: JSONArray): Unit = {
				callback(
					(
						for (i <- 0 until responseBody.length)
							yield Repository.parseFromJSON(responseBody.getJSONObject(i))
						).toArray
				)
			}

		}, RequestMethod.GET)
	}

	private def request(urn: String, authUser: User, handler: JsonHttpResponseHandler, requestMethod: RequestMethod.type, requestParams: RequestParams = null): Unit = {
		Log.d("GogsAPI", "Calling " + url + urn)
		client.addHeader("Authorization", authUser.getAuthentication)
		requestMethod match {
			case RequestMethod.GET =>
				client.get(url + urn, requestParams, handler)
			case RequestMethod.POST =>
				client.post(url + urn, requestParams, handler)
			case RequestMethod.PUT =>
				client.put(url + urn, requestParams, handler)
			case RequestMethod.PATCH =>
				client.patch(url + urn, requestParams, handler)
			case RequestMethod.DELETE =>
				client.delete(url + urn, requestParams, handler)
			case RequestMethod.HEAD =>
				client.head(url + urn, requestParams, handler)
			case RequestMethod.OPTIONS =>
				client.head(url + urn, requestParams, handler)
		}
	}

	def getUserAccessTokens(callback: => Array[Token] => Unit, authUser: User = authUser): Unit = {
		request("api/v1/users/" + authUser.username + "/tokens", authUser, new JsonHttpResponseHandler() {

			override def onSuccess(statusCode: Int, headers: Array[Header], responseBody: JSONArray): Unit = {
				callback(
					(
						for (i <- 0 until responseBody.length)
							yield Token.parseFromJSON(responseBody.getJSONObject(i))
						).toArray
				)
			}

			override def onSuccess(statusCode: Int, headers: Array[Header], responseBody: JSONObject): Unit = {
				callback(Array[Token]())
			}

			override def onFailure(statusCode: Int, headers: Array[Header], error: Throwable, errorObject: JSONObject): Unit = {
				println("Error " + statusCode + "\n" + errorObject)
				callback(Array[Token]())
			}

			override def onFailure(statusCode: Int, headers: Array[Header], errorString: String, error: Throwable): Unit = {
				println("Error " + statusCode + "\n" + errorString)
				callback(Array[Token]())
			}

		}, RequestMethod.GET)
	}

	def getUser(callback: => User => Unit, authUser: User = authUser): Unit = {
		request("api/v1/users/" + authUser.username, authUser, new JsonHttpResponseHandler() {
			override def onSuccess(statusCode: Int, headers: Array[Header], responseBody: JSONObject): Unit = {
				callback(User.parseFromJSON(responseBody))
			}
		}, RequestMethod.GET)
	}

	def getAuthUser: User = authUser

	def getURL: String = url
}
