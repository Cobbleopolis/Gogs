package com.cobble.gogs.app

import java.net.URL

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
import android.support.v4.app.{Fragment, FragmentManager}
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.{ImageView, TextView}
import com.cobble.gogs.app.fragment.{CobbleFragment, HomeFragment, RepoFragment, SettingsFragment}
import com.cobble.gogs.app.gogs.GogsAPI
import com.cobble.gogs.app.tasks.DownloadImageTask
import com.cobble.gogs.app.util.{GogsUtil, Prefs}
import com.squareup.picasso.Picasso

class MainActivity extends CobbleActivity {

	private var toolbar: Toolbar = _

	private var mNav: NavigationView = _

	private var mDrawer: DrawerLayout = _

	private var drawerToggle: ActionBarDrawerToggle = _

	private var profileImage: ImageView = _

	private var usernameText: TextView = _

	private var userEmailText: TextView = _

	private val frags: Map[Int, Fragment] = Map[Int, Fragment](
		R.id.drawer_profile -> new HomeFragment(),
		R.id.drawer_repos -> new RepoFragment(),
		R.id.drawer_settings -> new SettingsFragment()
	)

	override def onOptionsItemSelected(item: MenuItem): Boolean = {
		item.getItemId match {
			case R.id.home =>
				mDrawer.openDrawer(GravityCompat.START)
				true
			case _ => super.onOptionsItemSelected(item)
		}
	}

	override protected def onCreate(savedInstanceState: Bundle): Unit = {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		toolbar = findViewById(R.id.toolbar).asInstanceOf[Toolbar]
		toolbar.setBackgroundColor(getResources.getColor(R.color.colorPrimary))
		setSupportActionBar(toolbar)


		mDrawer = findViewById(R.id.drawer_layout).asInstanceOf[DrawerLayout]
		drawerToggle = setupDrawerToggle
		mDrawer.setDrawerListener(drawerToggle)
		getSupportActionBar.setDisplayHomeAsUpEnabled(true)
		getSupportActionBar.setHomeButtonEnabled(true)
		drawerToggle.syncState()

		profileImage = findViewById(R.id.profile_image).asInstanceOf[ImageView]

		usernameText = findViewById(R.id.username).asInstanceOf[TextView]

		userEmailText = findViewById(R.id.email).asInstanceOf[TextView]

		GogsAPI.getUser(user => {
			var url: String = user.avatarUrl
			if (!url.matches("^(https?|ftp)://.*$")) { //needs protocol
				if (!url.matches("((.*)\\.(.*))+/(.*/)*$")) { //needs domain
					url = Prefs.getString(R.string.userData_protocol) + Prefs.getString(R.string.userData_server) + url
				} else {
					url = Prefs.getString(R.string.userData_protocol) + url.replaceAll("^/+", "")
				}
			}
			println(url)
			Picasso.`with`(getApplicationContext)
				.load(url)
			    .placeholder(R.drawable.default_profile)
			    .error(R.drawable.default_profile)
				.into(profileImage)

			usernameText.setText(user.username)

			userEmailText.setText(user.email)
		})

		mNav = findViewById(R.id.main_drawer).asInstanceOf[NavigationView]
		setupDrawerContent(mNav)

//		new DownloadImageTask(findViewById(R.id.profile_image).asInstanceOf[ImageView]).execute(GogsUtil.protocol + GogsUtil.server + GogsAPI.)
	}

	def setupDrawerContent(navigationView: NavigationView): Unit = {
		navigationView.setNavigationItemSelectedListener(
			new OnNavigationItemSelectedListener {
				override def onNavigationItemSelected(menuItem: MenuItem): Boolean = {
					selectNavItem(menuItem)
					true
				}
			}
		)
	}

	private def selectNavItem(menuItem: MenuItem): Unit = {
		val fragment: Fragment = frags.getOrElse(menuItem.getItemId, frags.get(R.id.drawer_profile)).asInstanceOf[Fragment]
		val fragmentManager: FragmentManager = getSupportFragmentManager
		fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
		menuItem.setChecked(true)
		mDrawer.closeDrawer(GravityCompat.START)
		setTitle(menuItem.getTitle)
	}

	private def setupDrawerToggle: ActionBarDrawerToggle = {
		new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open,  R.string.navigation_drawer_close)
	}
}