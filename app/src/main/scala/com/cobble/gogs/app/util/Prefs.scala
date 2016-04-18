package com.cobble.gogs.app.util

import android.content.{Context, SharedPreferences}
import android.preference.PreferenceManager

object Prefs {

	private var sharedPrefs: SharedPreferences = _

	private var context: Context = _

	def initalize(ctx: Context): Unit = {
		context = ctx

		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
	}

	def writeString(key: Int, value: String): Unit = {
		val edit = sharedPrefs.edit
		edit.putString(context.getString(key), value)
		edit.commit()
	}

	def writeBoolean(key: Int, value: Boolean): Unit = {
		val edit = sharedPrefs.edit
		edit.putBoolean(context.getString(key), value)
	}

	def getString(key: Int, defaultValue: String = ""): String = {
		sharedPrefs.getString(context.getString(key), defaultValue)
	}

	def getBoolean(key: Int, defaultValue: Boolean = false): Boolean = {
		sharedPrefs.getBoolean(context.getString(key), defaultValue)
	}

}
