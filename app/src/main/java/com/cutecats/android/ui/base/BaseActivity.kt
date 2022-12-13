package com.cutecats.android.ui.base

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

abstract class BaseActivity: AppCompatActivity(),
    BaseNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    fun showToast(message: Any, tr: Throwable?) {

        Toast.makeText(this, retrieveMessage(message), Toast.LENGTH_LONG).show()
    }

    override fun errorView(message: Any, tr: Throwable?) {
        showToast(message,tr)
    }

    fun retrieveMessage(message: Any):String{
        var text = ""
        if (message is String)
            text = message
        else if (message is Int)
            text = getString(message)

        return text
    }

    companion object {

        val TAG = this::class.java.simpleName

    }
}