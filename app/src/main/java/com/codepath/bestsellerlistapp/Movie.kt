package com.codepath.bestsellerlistapp


/**
 * The Model for storing a single book from the NY Times API
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the gson library.
 */
data class Movie(val upvotes:String, val votecount:String, val score:String, val title:String, val author:String, val imgurl:String, val desc:String) {

}