package com.ducks.sungwon.phundemo.structure.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import com.ducks.sungwon.phundemo.R
import com.ducks.sungwon.phundemo.manager.RebelScumManager
import com.ducks.sungwon.phundemo.structure.core.CoreActivity
import com.ducks.sungwon.phundemo.utility.Constants
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
        mRebelScumManager.mRebelList[mPosition].image?.let {
            Picasso.with(this).load(it).placeholder(R.drawable.placeholder_nomoon).resize(1080, 800).centerCrop().into(ad_image_header)
        } ?: kotlin.run {
            Picasso.with(this).load(R.drawable.placeholder_nomoon).resize(1080, 800).centerCrop().into(ad_image_header)
        }
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
}