package com.cobble.gogs.app.gogs

import com.cobble.gogs.app.reference.RequestMethod
import com.loopj.android.http.{AsyncHttpClient, JsonHttpResponseHandler, RequestParams}
import cz.msebera.android.httpclient.Header
import org.json.{JSONArray, JSONObject}

object GogsAPI {

	var client: AsyncHttpClient = new AsyncHttpClient()

	private var url: String = ""

	private var authUser: User = null

	var readTimeout: Int = 5000

	var connectionTimeout: Int = 5000

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
				callback(Array[Token]())
			}

			override def onFailure(statusCode: Int, headers: Array[Header], errorString: String, error: Throwable): Unit = {
				callback(Array[Token]())
			}

		}, RequestMethod.GET)
	}

	def getUser (callback: => User => Unit, authUser: User = authUser): Unit = {
		request("api/v1/users/" + authUser.username, authUser, new JsonHttpResponseHandler() {
			override def onSuccess(statusCode: Int, headers: Array[Header], responseBody: JSONObject): Unit = {
				callback(User.parseFromJSON(responseBody))
			}
		}, RequestMethod.GET)
	}

	private def request(urn: String, authUser: User, handler: JsonHttpResponseHandler, requestMethod: RequestMethod.type, requestParams: RequestParams = null): Unit = {
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

	def getAuthUser: User = authUser

	def getURL: String = url
}
