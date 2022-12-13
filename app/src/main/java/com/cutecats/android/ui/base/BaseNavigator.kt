package com.cutecats.android.ui.base

interface BaseNavigator {

    fun loadingView()

    fun contentLoaded()

    fun errorView(message: Any, tr: Throwable?)

    fun showLazyLoading()

    fun hideLazyLoading()
}