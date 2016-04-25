package com.cobble.gogs.app

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.ViewGroup.LayoutParams
import android.widget.{RelativeLayout, TextView, Toast}
import com.cobble.gogs.app.fragment.{CobbleFragment, HomeFragment}
import com.cobble.gogs.app.gogs.GogsAPI

class MainActivity extends AppCompatActivity with NavigationView.OnNavigationItemSelectedListener {

	private var toolbar: Toolbar = _

	private var mDrawer: NavigationView = _

	private var mDrawerLayout: DrawerLayout = _

	private var drawerToggle: ActionBarDrawerToggle = _

	private var mSelectedId: Int = _

	protected override def onCreate(savedInstanceState: Bundle): Unit = {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setToolbar()
		initView()
		initFrag(savedInstanceState)
		drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
		mDrawerLayout.setDrawerListener(drawerToggle)
		drawerToggle.syncState()
		mSelectedId = if (savedInstanceState == null) R.id.drawer_home else savedInstanceState.getInt("SELECTED_ID")
		itemSelection(mSelectedId)
	}

	private def setToolbar(): Unit = {
		toolbar = findViewById(R.id.toolbar).asInstanceOf[Toolbar]
//		if (toolbar != null) {
//			setSupportActionBar(toolbar)
//		}
	}

	private def initView(): Unit = {
		mDrawer = findViewById(R.id.main_drawer).asInstanceOf[NavigationView]
		mDrawer.setNavigationItemSelectedListener(this)
		mDrawerLayout = findViewById(R.id.drawer_layout).asInstanceOf[DrawerLayout]
	}

	private def itemSelection(mSelectedId: Int): Unit = {
		mDrawerLayout.closeDrawer(GravityCompat.START)

	}

	private def initFrag(savedInstanceState: Bundle): Unit = {
		if (findViewById(R.id.fragment_container) != null) {
			if(savedInstanceState != null)
				return
			val homeFragment: HomeFragment = new HomeFragment

			homeFragment.setArguments(getIntent.getExtras)

			getSupportFragmentManager.beginTransaction().add(R.id.fragment_container, homeFragment).commit()
		}
	}

	override def onConfigurationChanged(newConfig: Configuration): Unit = {
		super.onConfigurationChanged(newConfig)
		drawerToggle.onConfigurationChanged(newConfig)
	}

	override def onNavigationItemSelected(menuItem: MenuItem): Boolean = {
		menuItem.setChecked(true)
		mSelectedId = menuItem.getItemId
		itemSelection(mSelectedId)
		true
	}

	override def onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle): Unit = {
		super.onSaveInstanceState(outState, outPersistentState)
		outState.putInt("SELECTED_ID", mSelectedId)
	}
}