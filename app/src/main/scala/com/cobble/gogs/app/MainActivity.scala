package com.cobble.gogs.app

import android.content.res.Configuration
import android.os.{Bundle, PersistableBundle}
import android.support.design.widget.NavigationView
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.{ActionBarDrawerToggle, AppCompatActivity}
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.cobble.gogs.app.fragment.{CobbleFragment, HomeFragment, RepoFragment}

class MainActivity extends AppCompatActivity {

	private var toolbar: Toolbar = _

	private var mNav: NavigationView = _

	private var mDrawer: DrawerLayout = _

	override protected def onCreate(savedInstanceState: Bundle): Unit = {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		toolbar = findViewById(R.id.toolbar).asInstanceOf[Toolbar]
//		setSupportActionBar(toolbar)

		mDrawer = findViewById(R.id.drawer_layout).asInstanceOf[DrawerLayout]

		mNav = findViewById(R.id.main_drawer).asInstanceOf[NavigationView]
		setupDrawerContent(mNav)
	}

	override def onOptionsItemSelected(item: MenuItem): Boolean = {
		item.getItemId match {
			case R.id.home =>
				mDrawer.openDrawer(GravityCompat.START)
				true
			case _ => super.onOptionsItemSelected(item)
		}
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
		val fragment: CobbleFragment = menuItem.getItemId match {
			case R.id.drawer_favourite => new RepoFragment
			case _ => new HomeFragment
		}
		val fragmentManager: FragmentManager = getSupportFragmentManager
		fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
		menuItem.setChecked(true)
		mDrawer.closeDrawer(GravityCompat.START)
	}
}