package com.cobble.gogs.app.fragment

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.ListFragment
import android.view.{LayoutInflater, View, ViewGroup}

abstract class CobbleListFragment(@IdRes resourceLayout: Int) extends ListFragment {

	override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
		inflater.inflate(resourceLayout, container, false)
	}

	override def onDestroyView(): Unit = {
		super.onDestroyView()
	}

}
