package com.carveniche.wisdomleap.base

class BaseContract {
  interface Presenter<in T>
  {
    fun subscribe()
    fun unSubscribe()
    fun attach(view:T)
  }
  interface View{
    fun showProgress(show : Boolean)
  }
}