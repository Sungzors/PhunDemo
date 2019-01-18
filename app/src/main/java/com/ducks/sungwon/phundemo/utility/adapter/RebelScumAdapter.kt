package com.ducks.sungwon.phundemo.utility.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ducks.sungwon.phundemo.R
import com.ducks.sungwon.phundemo.model.RebelScum
import com.ducks.sungwon.phundemo.utility.CircleTransform
import com.squareup.picasso.Picasso

class RebelScumAdapter(list: ArrayList<RebelScum>, context: Context, onClick: (Int) -> Unit) : RecyclerView.Adapter<RebelScumAdapter.ViewHolder>(){

    private val mRecyclerList = list
    private val mContext = context
    private val mClick = onClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_main, parent, false))

    override fun getItemCount(): Int = mRecyclerList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, pos: Int) {
        viewHolder.title.text = mRecyclerList[pos].title
        viewHolder.location.text = mContext.resources.getString(
                R.string.location_combined,
                mRecyclerList[pos].locationline1,
                mRecyclerList[pos].locationline2
        )
        mRecyclerList[pos].image?.let {
            Picasso.with(mContext).load(it).transform(CircleTransform()).into(viewHolder.picture)
        } ?: kotlin.run {
            Picasso.with(mContext).load(R.drawable.placeholder_nomoon).transform(CircleTransform()).into(viewHolder.picture)
        }
        viewHolder.description.text = mRecyclerList[pos].description
        viewHolder.share.setOnClickListener {
            mClick(R.id.cm_share)
        }
        viewHolder.container.setOnClickListener {
            mClick(R.id.cm_container)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var container : LinearLayout = itemView.findViewById(R.id.cm_container)
        var title : TextView = itemView.findViewById(R.id.cm_title)
        var location : TextView = itemView.findViewById(R.id.cm_location)
        var picture : ImageView = itemView.findViewById(R.id.cm_picture)
        var description: TextView = itemView.findViewById(R.id.cm_description)
        var share : TextView = itemView.findViewById(R.id.cm_share)
    }
}