package com.cobble.gogs.app.fragment

import java.util

import android.graphics.Color
import android.os.Bundle
import android.view.{View, ViewGroup}
import android.widget.{SimpleAdapter, TextView, Toast}
import com.cobble.gogs.app.R
import com.cobble.gogs.app.gogs.GogsAPI

class ProfileFragment extends CobbleListFragment(R.layout.frag_profile) {

	val listItems: util.ArrayList[util.Map[String, String]] = new util.ArrayList[util.Map[String, String]]()

	override def onActivityCreated(savedInstanceState: Bundle): Unit = {
		listItems.clear()
		requestRepos()
		super.onActivityCreated(savedInstanceState)
	}

	def requestRepos(): Unit = {
		val fields: Array[String] = getResources.getStringArray(R.array.user_frag_list_title)
		GogsAPI.getUser(user => {
			for (i <- fields.indices) {
				val mRepo = new util.HashMap[String, String]()
				mRepo.put("head", fields(i))
				mRepo.put("sub", i match {
					case 0 => if(user.id != -1) user.id.toString else "N/A"
					case 1 => if(user.username != null) user.username else "N/A"
					case 2 => if(user.fullName != null) user.fullName else "N/A"
					case 3 => if(user.email != null) user.email else "N/A"
					case 4 => if(user.avatarUrl != null) user.avatarUrl else "N/A"
					case 5 => if(user.website != null) user.website else "N/A"
					case 6 => if(user.location != null) user.location else "N/A"
					case _ => "N/A"
				})
				listItems.add(mRepo)
			}
			setListAdapter(new SimpleAdapter(
				getActivity.getApplicationContext,
				listItems,
				android.R.layout.simple_list_item_2,
				Array("head", "sub"),
				Array(android.R.id.text1, android.R.id.text2)) {
				override def getView(pos: Int, convertView: View, parent: ViewGroup): View = {
					val v = super.getView(pos, convertView, parent)
					val textView: TextView = v.findViewById(android.R.id.text1).asInstanceOf[TextView]
					val textView2: TextView = v.findViewById(android.R.id.text2).asInstanceOf[TextView]
					textView.setTextColor(Color.BLACK)
					textView2.setTextColor(Color.BLACK)
					v
				}
			})
		})
	}
}
