package com.cobble.gogs.app.fragment

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.{LayoutInflater, View, ViewGroup}

abstract class CobbleFragment(@IdRes resourceLayout: Int) extends Fragment {

	override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
		inflater.inflate(resourceLayout, container, false)
	}

}
