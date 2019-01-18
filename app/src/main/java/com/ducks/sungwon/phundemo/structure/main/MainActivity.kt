package com.ducks.sungwon.phundemo.structure.main

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.LinearLayout
import android.widget.ShareActionProvider
import com.ducks.sungwon.phundemo.R
import com.ducks.sungwon.phundemo.manager.RebelScumManager
import com.ducks.sungwon.phundemo.structure.core.CoreActivity
import com.ducks.sungwon.phundemo.utility.adapter.RebelScumAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : CoreActivity() {
    override fun layoutId() = R.layout.activity_main

    override fun contentContainerId() = 0

    private lateinit var mAdapter: RebelScumAdapter
    private lateinit var mRebelScumManager: RebelScumManager
    private var mShareActionProvider: ShareActionProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.app_name)
        showProgress()
        //initiate api call once per activity
        mRebelScumManager = ViewModelProviders.of(this).get(RebelScumManager::class.java)
        makeCall()
    }

    override fun onStart() {
        super.onStart()
        //recycler set on onStart for landscape, ViewModel architecture to persist data
        setUpRecycler()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    //If offline or the call fails, displays a message prompting user to try again
    private fun makeCall(){
        mRebelScumManager.getRebelList {
            if(it){
                am_no_net.visibility = LinearLayout.GONE
            } else {
                am_no_net.visibility = LinearLayout.VISIBLE
            }
        }
    }

    private fun setUpRecycler(){
        mAdapter = RebelScumAdapter(mRebelScumManager.mRebelList, context, {
            when(it.first){
                R.id.cm_container ->{
//                    val intent = Intent(context, )
                }
                R.id.cm_share -> {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, mRebelScumManager.mRebelList[it.second].title)
                        putExtra(Intent.EXTRA_SUBJECT, mRebelScumManager.mRebelList[it.second].description)
                        type = "text/plain"
                    }
                    mShareActionProvider?.setShareIntent(sendIntent)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate menu resource file.
        menuInflater.inflate(R.menu.menu_share, menu)

        // Locate MenuItem with ShareActionProvider
        menu.findItem(R.id.menu_item_share).also { menuItem ->
            // Fetch and store ShareActionProvider
            mShareActionProvider = menuItem.actionProvider as? ShareActionProvider
        }

        // Return true to display menu
        return true
    }
}