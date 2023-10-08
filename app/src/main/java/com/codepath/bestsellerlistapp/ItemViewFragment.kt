package com.codepath.bestsellerlistapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class ItemViewFragment(val item:Movie) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container:ViewGroup?, savedInstanceState: Bundle? ): View? {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.itemviewfragment, container, false)
        view.findViewById<TextView>(R.id.name)?.text = item.title
        view.findViewById<TextView>(R.id.author)?.text = "Adult movie? "+item.author
        view.findViewById<TextView>(R.id.popularity)?.text = "Votes: "+ item.votecount
        view.findViewById<TextView>(R.id.votecount)?.text = "Out of: "+ item.upvotes
        view.findViewById<TextView>(R.id.avgscore)?.text = "Avg Score: "+ item.score
        view.findViewById<TextView>(R.id.desc)?.text = item.desc
        Glide.with(view)
            .load("https://image.tmdb.org/t/p/w500/"+item.imgurl)
            .centerInside()
            .transform(RoundedCorners(500))
            .into(view.findViewById<ImageView>(R.id.mvimg))
        return view
    }
}