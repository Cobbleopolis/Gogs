package com.cobble.gogs.app.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.cobble.gogs.app.util.Prefs
import com.cobble.gogs.app.{ActivityLauncher, R}

class SettingsFragment extends CobbleFragment(R.layout.frag_settings) {

	var signOutButton: Button = _

	override def onActivityCreated(savedInstanceState: Bundle): Unit = {
		super.onActivityCreated(savedInstanceState)
		signOutButton = getView.findViewById(R.id.settings_button_signOut).asInstanceOf[Button]
		signOutButton.setOnClickListener(new View.OnClickListener() {
			override def onClick(view: View): Unit = {
				signOut()
			}
		})
	}

	def signOut(): Unit = {
		Prefs.writeString(R.string.userData_server, "")
		Prefs.writeString(R.string.userData_protocol, "")
		Prefs.writeString(R.string.userData_username, "")
		Prefs.writeString(R.string.userData_token, "")
		startActivity(new Intent(getActivity, classOf[ActivityLauncher]))
	}
}