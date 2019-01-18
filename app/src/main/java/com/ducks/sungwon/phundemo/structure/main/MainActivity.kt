package com.ducks.sungwon.phundemo.structure.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.ducks.sungwon.phundemo.R
import com.ducks.sungwon.phundemo.manager.RebelScumManager
import com.ducks.sungwon.phundemo.structure.core.CoreActivity
import com.ducks.sungwon.phundemo.structure.detail.DetailActivity
import com.ducks.sungwon.phundemo.utility.Constants
import com.ducks.sungwon.phundemo.utility.adapter.RebelScumAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : CoreActivity() {
    override fun layoutId() = R.layout.activity_main

    override fun contentContainerId() = 0

    private lateinit var mAdapter: RebelScumAdapter
    private lateinit var mRebelScumManager: RebelScumManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.app_name)
        //initiate api call once per activity
        mRebelScumManager = RebelScumManager.instance
        makeCall()
        //retry clicker
        am_retry.setOnClickListener{
            makeCall()
        }
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
        showProgress()
        mRebelScumManager.getRebelList {
            if(it){
                am_no_net.visibility = LinearLayout.GONE
                setUpRecycler()
            } else {
                am_no_net.visibility = LinearLayout.VISIBLE
            }
            hideProgress()
        }
    }

    private fun setUpRecycler(){
        mAdapter = RebelScumAdapter(mRebelScumManager.mRebelList, context, {
            when(it.first){
                R.id.cm_container ->{
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(Constants.IntentKeys.REBEL_ID, it.second)
                    startActivity(intent)
                }
                R.id.cm_share -> {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TITLE, mRebelScumManager.mRebelList[it.second].title)
                        putExtra(Intent.EXTRA_TEXT, mRebelScumManager.mRebelList[it.second].description)
                        type = "text/plain"
                    }
                    startActivity(Intent.createChooser(sendIntent, "Share using..."))
                }
            }
        })
        am_recycler.setHasFixedSize(true)
        am_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        am_recycler.adapter = mAdapter
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate menu resource file.
//        menuInflater.inflate(R.menu.menu_share, menu)
//
//        // Locate MenuItem with ShareActionProvider
//        menu.findItem(R.id.menu_item_share).also { menuItem ->
//            // Fetch and store ShareActionProvider
//            mShareActionProvider = MenuItemCompat.getActionProvider(menuItem) as ShareActionProvider
//        }
//
//        // Return true to display menu
//        return true
//    }
}