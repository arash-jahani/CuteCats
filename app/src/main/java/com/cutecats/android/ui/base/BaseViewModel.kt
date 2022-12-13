package com.cutecats.android.ui.base

import androidx.lifecycle.ViewModel
import com.cutecats.android.data.DataRepository
import java.lang.ref.WeakReference

open class BaseViewModel<N> constructor(val dataRepository: DataRepository) : ViewModel() {

    private var mNavigator: WeakReference<N>? = null

    var navigator: N?
        get() = mNavigator!!.get()
        set(navigator) {
            this.mNavigator = WeakReference<N>(navigator)!!
        }
}