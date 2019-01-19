package com.ducks.sungwon.phundemo.structure.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.transition.Transition
import android.widget.ImageView
import com.ducks.sungwon.phundemo.R
import com.ducks.sungwon.phundemo.manager.RebelScumManager
import com.ducks.sungwon.phundemo.structure.core.CoreActivity
import com.ducks.sungwon.phundemo.utility.Constants
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat

class DetailActivity : CoreActivity(){
    override fun layoutId() = R.layout.activity_detail

    override fun contentContainerId() = 0

    private lateinit var mRebelScumManager: RebelScumManager
    private var mPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPosition = intent.getIntExtra(Constants.IntentKeys.REBEL_ID, 0)
        mRebelScumManager = RebelScumManager.instance
        mRebelScumManager.mRebelList[mPosition].phone?.let {
            ad_call.visibility = ImageView.VISIBLE
        } ?: kotlin.run {
            ad_call.visibility = ImageView.GONE
        }


        ViewCompat.setTransitionName(ad_image_header, Constants.TransitionKeys.VIEW_IMAGE_HEADER)

        setUpClickers()
    }

    override fun onStart() {
        super.onStart()
        setUpViews()
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

    private fun setUpViews(){
        loadTransition()
        ad_date.text = SimpleDateFormat("MMM dd, yyyy' at 'h:mma").format(mRebelScumManager.mRebelList[mPosition].date)
        ad_title.text = mRebelScumManager.mRebelList[mPosition].title
        ad_text.text = mRebelScumManager.mRebelList[mPosition].description
    }

    private fun setUpClickers(){
        ad_back.setOnClickListener {
            onBackPressed()
        }
        ad_call.setOnClickListener {
            val re = Regex("[^A-Za-z0-9 ]")
            val phoneIntent = Intent(Intent.ACTION_DIAL)
            phoneIntent.data = Uri.parse("tel:" + re.replace(mRebelScumManager.mRebelList[mPosition].phone!!, ""))
            startActivity(phoneIntent)
        }
        ad_share.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, mRebelScumManager.mRebelList[mPosition].title)
                putExtra(Intent.EXTRA_TEXT, mRebelScumManager.mRebelList[mPosition].description)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, "Share using..."))
        }
    }

    private fun loadTransition(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && addTransitionListener()) {
            loadFullSizeImage()
        } else {
            loadFullSizeImage()
        }
    }

    private fun loadFullSizeImage(){
        supportPostponeEnterTransition()
        mRebelScumManager.mRebelList[mPosition].image?.let {
            Picasso.with(this).load(it).resize(1080, 800).centerCrop().into(
                    ad_image_header,
                     object : Callback{
                        override fun onSuccess() {
                            supportStartPostponedEnterTransition()
                        }

                        override fun onError() {
                            supportStartPostponedEnterTransition()
                            Picasso.with(context).load(R.drawable.placeholder_nomoon).resize(1080, 800).centerCrop().into(ad_image_header)
                        }
                    })
        } ?: kotlin.run {
            Picasso.with(this).load(R.drawable.placeholder_nomoon).resize(1080, 800).centerCrop().into(ad_image_header)
        }
    }

    private fun addTransitionListener() : Boolean {
        val transition = window.sharedElementEnterTransition

        transition?.let {
            it.addListener( object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition?) {
                    loadFullSizeImage()
                    transition!!.removeListener(this)
                }

                override fun onTransitionResume(transition: Transition?) {

                }

                override fun onTransitionPause(transition: Transition?) {
                }

                override fun onTransitionCancel(transition: Transition?) {
                    transition!!.removeListener(this)
                }

                override fun onTransitionStart(transition: Transition?) {
                }
            }

            )
            return true
        } ?: kotlin.run {
            return false
        }
    }

    private fun detectVisibility(view: ImageView){

    }
}