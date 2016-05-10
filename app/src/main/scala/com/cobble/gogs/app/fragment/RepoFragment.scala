package com.cobble.gogs.app.fragment

import java.util

import android.graphics.Color
import android.os.Bundle
import android.view.{View, ViewGroup}
import android.widget._
import com.cobble.gogs.app.R
import com.cobble.gogs.app.gogs.GogsAPI

class RepoFragment extends CobbleListFragment(R.layout.frag_repos) {

	val listItems: util.ArrayList[String] = new util.ArrayList[String]()

	override def onActivityCreated(savedInstanceState: Bundle): Unit = {
		listItems.clear()
		super.onActivityCreated(savedInstanceState)
		requestRepos()

	}

	def requestRepos(): Unit = {
		Toast.makeText(getActivity.getApplicationContext, "Request Started", Toast.LENGTH_SHORT).show()
		GogsAPI.getUserRepos(repos => {
			repos.foreach(repo => {
				listItems.add(repo.fullName)
			})
			setListAdapter(new ArrayAdapter[String](getActivity.getApplicationContext, android.R.layout.simple_list_item_1, listItems) {
				override def getView(pos: Int, convertView: View, parent: ViewGroup): View = {
					val v = super.getView(pos, convertView, parent)
					val textView: TextView = v.findViewById(android.R.id.text1).asInstanceOf[TextView]
					textView.setTextColor(Color.BLACK)
					v
				}
			})
		})
	}

}
