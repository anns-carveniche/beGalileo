package com.carveniche.wisdomleap.model

import android.content.SharedPreferences
import javax.inject.Inject


class MySharedPreferences @Inject constructor(private val mSharedPreferences: SharedPreferences) {

  fun putIntData(key: String, data: Int) {
    mSharedPreferences.edit().putInt(key, data).apply()
  }

  fun getIntData(key: String): Int {
    return mSharedPreferences.getInt(key, 0)
  }

  fun putBoolean(key : String,data: Boolean)
  {
    mSharedPreferences.edit().putBoolean(key,data).apply()
  }

  fun getBoolean(key: String,status: Boolean) : Boolean {
    return mSharedPreferences.getBoolean(key,status)
  }

  fun putString(key: String,data: String)
  {
    mSharedPreferences.edit().putString(key,data).apply()
  }
  fun getString(key: String) : String
  {
    return mSharedPreferences.getString(key,"")!!
  }
}