package com.ducks.sungwon.phundemo.structure.core

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.view_toolbar.*
import java.util.ArrayList

abstract class CoreActivity : AppCompatActivity() {
    /*Properties*/
    //Back button listeners
    private val mOnBackPressListeners = ArrayList<OnBackPressListener>()

    val TAG = javaClass.simpleName

    @LayoutRes
    protected abstract fun layoutId(): Int

    //Return 0 for activity with no container
    @IdRes
    protected abstract fun contentContainerId(): Int

    val context: Context
        get() = this

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(layoutId())
        setSupportActionBar(toolbar)
    }

    /*Toolbar*/
    fun setToolbarTitle(@StringRes title: Int) {
        setToolbarTitle(getString(title))
    }

    fun setToolbarTitle(title: String) {
        toolbar.title = title
    }

    /*Navigation*/
    fun showBackArrow(icon: Int) {

        toolbar.setNavigationIcon(icon)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    /*Progress Dialog*/
    fun showProgress() {
        progress_view.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progress_view.visibility = View.GONE
    }

//    /*Fragment Control*/
//    fun addFragment(fragment: Fragment, addToBackStack: Boolean) {
//        addFragment(contentContainerId(), fragment, addToBackStack)
//    }
//
//    fun replaceFragment(fragment: CoreFragment, addToBackStack: Boolean, oppositeAnimation: Boolean) {
//        replaceFragment(contentContainerId(), fragment, addToBackStack, oppositeAnimation)
//    }
//
//    /*Fragment transactions*/
//    @SuppressLint("CommitTransaction")
//    fun addFragment(@IdRes containerId: Int, fragment: Fragment, addToBackStack: Boolean) {
//        val name = fragment.javaClass.name
//        val add = supportFragmentManager.beginTransaction().add(containerId, fragment, name)
//        if (addToBackStack) {
//            add.addToBackStack(name)
//        }
//        add.commit()
//    }
//
//    @SuppressLint("CommitTransaction")
//    fun replaceFragment(@IdRes containerId: Int, fragment: CoreFragment, addToBackStack: Boolean, oppositeAnimation: Boolean) {
//        val name = fragment.javaClass.name
//        val replaceTransaction = supportFragmentManager.beginTransaction()
//        if (oppositeAnimation) {
//            replaceTransaction.setCustomAnimations(android.R.anim.fade_out, android.R.anim.slide_out_right)
//        } else {
//            replaceTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
//        }
//        replaceTransaction.replace(containerId, fragment, name)
//        if (addToBackStack) {
//            replaceTransaction.addToBackStack(name)
//        }
//        replaceTransaction.commit()
//    }
//
//
//    fun removeFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction().remove(fragment).commit()
//    }
//
//    fun removeFragment(@IdRes id: Int) {
//        val fragmentManager = supportFragmentManager
//        val fragment = fragmentManager.findFragmentById(id)
//        if (fragment != null) {
//            fragmentManager.beginTransaction().remove(fragment).commit()
//        }
//    }
//
//    fun removeFragment(tag: String) {
//        val fragmentManager = supportFragmentManager
//        val fragment = fragmentManager.findFragmentByTag(tag)
//        if (fragment != null) {
//            fragmentManager.beginTransaction().remove(fragment).commit()
//        }
//    }
//
//    fun removeFragment(fragmentClass: Class<out Fragment>) {
//        val fragmentManager = supportFragmentManager
//        val fragment = fragmentManager.findFragmentByTag(fragmentClass.name)
//        if (fragment != null) {
//            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_out, android.R.animator.fade_in).remove(fragment).commit()
//        }
//    }
//
//    fun popFragment() {
//        val fm = supportFragmentManager
//        fm.popBackStack()
//    }
//
//    fun closeFromChild() {
//        if (supportFragmentManager.backStackEntryCount > 0) {
//            supportFragmentManager.popBackStack()
//        } else {
//            supportFinishAfterTransition()
//        }
//    }

    /*Navigation - Back Button*/
    override fun onBackPressed() {
        if (interruptedByListener()) {

            return
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    private fun interruptedByListener(): Boolean {
        var interrupt = false
        for (listener in mOnBackPressListeners) {
            if (listener.onBackPressed()) {
                interrupt = true
            }
        }
        return interrupt
    }

    /*Error handling*/
    fun showError(errorMessage: String) {
        AlertDialog.Builder(this).setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, null).show()
    }

    /*Info feedback*/
    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun addOnBackPressListener(listener: OnBackPressListener?) {
        if (listener != null) {
            mOnBackPressListeners.add(listener)
        }
    }

    fun removeOnBackPressListener(listener: OnBackPressListener) {
        mOnBackPressListeners.remove(listener)
    }

    /*Navigation Interface - Back Button*/
    interface OnBackPressListener {
        fun onBackPressed(): Boolean
    }

    open fun close() {
        finishAffinity()
    }
}