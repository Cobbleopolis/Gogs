package com.cobble.gogs.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.{RelativeLayout, TextView, Toast}
import com.cobble.gogs.app.R
import com.cobble.gogs.app.gogs.GogsAPI

class HomeFragment extends CobbleFragment(R.layout.frag_home) {

	override def onActivityCreated(savedInstanceState: Bundle): Unit = {
		requestRepos()
		super.onActivityCreated(savedInstanceState)
	}

	def requestRepos(): Unit = {
		val mount: RelativeLayout = getView.findViewById(R.id.repoList).asInstanceOf[RelativeLayout]
		Toast.makeText(getActivity.getApplicationContext, "Request Started", Toast.LENGTH_SHORT).show()
		GogsAPI.getUserRepos(repos => {
			var i = 0
			repos.foreach(repo => {
				val textView = new TextView(getActivity.getApplicationContext)
				textView.setText(repo.fullName)
				val layoutParams: RelativeLayout.LayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
				layoutParams.setMargins(0, (i + 1) * textView.getTextSize.toInt, 0, 0)
				textView.setTextColor(Color.BLACK)
				textView.setLayoutParams(layoutParams)
				mount.addView(textView)
				i += 1
			})
		})
	}
}