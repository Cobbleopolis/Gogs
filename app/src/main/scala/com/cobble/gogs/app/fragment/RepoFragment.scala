package com.cobble.gogs.app.fragment

import java.util

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.{View, ViewGroup}
import android.widget._
import com.cobble.gogs.app.R
import com.cobble.gogs.app.gogs.GogsAPI

class RepoFragment extends CobbleListFragment(R.layout.frag_repos) {

	val listItems: util.ArrayList[util.Map[String, String]] = new util.ArrayList[util.Map[String, String]]()

	override def onActivityCreated(savedInstanceState: Bundle): Unit = {
		listItems.clear()
		super.onActivityCreated(savedInstanceState)
		requestRepos()

	}

	override def onListItemClick(listView: ListView, view: View, pos: Int, id: Long): Unit = {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(view.findViewById(android.R.id.text2).asInstanceOf[TextView].getText.toString)))
	}

	def requestRepos(): Unit = {
//		Toast.makeText(getActivity.getApplicationContext, "Request Started", Toast.LENGTH_SHORT).show()
		GogsAPI.getUserRepos(repos => {
			repos.foreach(repo => {
				val mRepo = new util.HashMap[String, String]()
				mRepo.put("head",  (if(repo.isPrivate) "\uD83D\uDD12 " else "") + repo.fullName)
				mRepo.put("sub", repo.htmlUrl)
				listItems.add(mRepo)
			})
			setListAdapter(new SimpleAdapter(
				getActivity.getApplicationContext,
				listItems,
				android.R.layout.simple_list_item_2,
				Array("head", "sub"),
				Array(android.R.id.text1, android.R.id.text2)) {
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
