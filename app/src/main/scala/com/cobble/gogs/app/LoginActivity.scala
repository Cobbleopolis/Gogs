package com.cobble.gogs.app

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget._
import com.cobble.gogs.app.gogs.{GogsAPI, User}
import com.cobble.gogs.app.util.{GogsUtil, Prefs}
import com.loopj.android.http.AsyncHttpClient

class LoginActivity extends CobbleActivity {

	var serverText: EditText = _

	var protocolSpinner: Spinner = _

	var usernameText: EditText = _

	var passwordText: EditText = _

	var loginButton: Button = _

	override def onBackPressed(): Unit = {
		moveTaskToBack(true)
	}

	protected override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)



		serverText = findViewById(R.id.input_server).asInstanceOf[EditText]

		protocolSpinner = findViewById(R.id.spinner_protocol).asInstanceOf[Spinner]

		usernameText = findViewById(R.id.input_username).asInstanceOf[EditText]

		passwordText = findViewById(R.id.input_password).asInstanceOf[EditText]

		loginButton = findViewById(R.id.btn_login).asInstanceOf[Button]

		loginButton.setOnClickListener(new View.OnClickListener() {
			override def onClick(v: View): Unit = {
				login()
			}
		})

		val protocolAdapter: ArrayAdapter[CharSequence] = ArrayAdapter.createFromResource(this, R.array.login_spinner_protocol,
			android.R.layout.simple_spinner_dropdown_item)
		protocolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

		protocolSpinner.setAdapter(protocolAdapter)
	}

	def login(): Unit = {

		val client: AsyncHttpClient = new AsyncHttpClient()

		val server: String = serverText.getText.toString.trim
		val protocol: String = protocolSpinner.getSelectedItem.toString.trim
		val username: String = usernameText.getText.toString.trim
		val password: String = passwordText.getText.toString //Trim???

		if (!validate(server, username, password)) {
			onLoginFailed()
			return
		}

		loginButton.setEnabled(false)
		val progressDialog: ProgressDialog = new ProgressDialog(LoginActivity.this)
		progressDialog.setIndeterminate(true)
		progressDialog.setMessage("Logging in...")
		progressDialog.setCancelable(false)
		progressDialog.show()

		val authUser: User = new User()
		authUser.username = username
		authUser.setPassword(password)
		GogsAPI.getUserAccessTokens(tokens => {
			progressDialog.setMessage("Message get")
			progressDialog.dismiss()
			if(!tokens.isEmpty) {
				var key: String = ""
				for (token <- tokens)
					if (token.name == "gogs-app")
						key = token.sha1
				onLoginSuccess(server, protocol, username, key)
			} else
				onLoginFailed()
		}, authUser)
	}

	def onLoginSuccess(server: String, protocol: String, username: String, token: String): Unit = {
		loginButton.setEnabled(true)
		Prefs.writeString(R.string.userData_server, server)
		Prefs.writeString(R.string.userData_protocol, protocol)
		Prefs.writeString(R.string.userData_username, username)
		Prefs.writeString(R.string.userData_token, token)
		startActivity(new Intent(this, classOf[MainActivity]))
		finish()
	}

	def onLoginFailed(): Unit = {
		Toast.makeText(getApplicationContext, "Login failed", Toast.LENGTH_LONG).show()
		loginButton.setEnabled(true)
	}

	//Activity
	//	override def onActivityResult(requestCode: Int, resultCode: Int, data: Intent): Unit = {
	//
	//	}

	def validate(server: String, username: String, password: String): Boolean = {
		!server.isEmpty && !username.isEmpty && !password.isEmpty
	}
}
