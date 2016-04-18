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
import com.cobble.gogs.app.gogs.GogsAPI;

class MainActivity extends AppCompatActivity with NavigationView.OnNavigationItemSelectedListener {

	private var toolbar: Toolbar = _

	private var mDrawer: NavigationView = _

	private var mDrawerLayout: DrawerLayout = _

	private var drawerToggle: ActionBarDrawerToggle = _

	private var mSelectedId: Int = _

	protected override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setToolbar()
		initView()
		drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
		mDrawerLayout.setDrawerListener(drawerToggle)
		drawerToggle.syncState()
		mSelectedId = if (savedInstanceState == null) R.id.drawer_home else savedInstanceState.getInt("SELECTED_ID")
		itemSelection(mSelectedId)
	}

	private def setToolbar() {
		toolbar = findViewById(R.id.toolbar).asInstanceOf[Toolbar]
//		if (toolbar != null) {
//			setSupportActionBar(toolbar)
//		}
	}

	private def initView() {
		mDrawer = findViewById(R.id.main_drawer).asInstanceOf[NavigationView]
		mDrawer.setNavigationItemSelectedListener(this)
		mDrawerLayout = findViewById(R.id.drawer_layout).asInstanceOf[DrawerLayout]
	}

	private def itemSelection(mSelectedId: Int) {
		mDrawerLayout.closeDrawer(GravityCompat.START)

	}

	override def onConfigurationChanged(newConfig: Configuration) {
		super.onConfigurationChanged(newConfig)
		drawerToggle.onConfigurationChanged(newConfig)
	}

	override def onNavigationItemSelected(menuItem: MenuItem): Boolean = {
		menuItem.setChecked(true)
		mSelectedId = menuItem.getItemId
		itemSelection(mSelectedId)
		true
	}

	override def onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
		super.onSaveInstanceState(outState, outPersistentState)
		outState.putInt("SELECTED_ID", mSelectedId)
	}

	def requestRepos(): Unit = {
		val layout: RelativeLayout = findViewById(R.id.main_content).asInstanceOf[RelativeLayout]
		Toast.makeText(getApplicationContext, "Request Started", Toast.LENGTH_SHORT).show()
		GogsAPI.getUserRepos(repos => {
			var i = 0
			repos.foreach(repo => {
				val textView = new TextView(getApplicationContext)
				textView.setText(repo.fullName)
				val layoutParams: RelativeLayout.LayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
				layoutParams.setMargins(0, (i + 1) * textView.getTextSize.toInt, 0, 0)
				textView.setTextColor(Color.BLACK)
				textView.setLayoutParams(layoutParams)
				layout.addView(textView)
				i += 1
			})
		})
	}
}