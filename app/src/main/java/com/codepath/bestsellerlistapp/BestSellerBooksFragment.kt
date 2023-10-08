package com.codepath.bestsellerlistapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject


// --------------------------------//
// CHANGE THIS TO BE YOUR API KEY  //
// --------------------------------//
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

/*
 * The class for the only fragment in the app, which contains the progress bar,
 * recyclerView, and performs the network calls to the NY Times API.
 */
class BestSellerBooksFragment : Fragment(), OnListFragmentInteractionListener {

    val movies = ArrayList<Movie>()

    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_best_seller_books_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val request = RequestParams()
        request["api_key"] = API_KEY
        // Using the client, perform the HTTP request
        client["https://api.themoviedb.org/3/movie/popular", request,  object : JsonHttpResponseHandler()
        {
            /*
             * The onSuccess function gets called when
             * HTTP response status is "200 OK"
             */
            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JsonHttpResponseHandler.JSON
            ) {
                // The wait for a response is over
                progressBar.hide()

                //TODO - Parse JSON into Models
                val booksJSON: JSONArray = json.jsonObject.get("results") as JSONArray
                for(x in 0 until booksJSON.length()) {
                    var bk: JSONObject = booksJSON.get(x) as JSONObject
                    println(bk)
                    movies.add(
                        Movie(
                            bk.getString("popularity"),
                            bk.getString("vote_count"),
                            bk.getString("vote_average"),
                            bk.getString("original_title"),
                            bk.getString("adult"),
                            bk.getString("poster_path"),
                            bk.getString("overview"),
                        )
                    )
                }
                recyclerView.adapter = BestSellerBooksRecyclerViewAdapter(movies, this@BestSellerBooksFragment)

                // Look for this in Logcat:
                Log.d("BestSellerBooksFragment", "response successful")
            }

            /*
             * The onFailure function gets called when
             * HTTP response status is "4XX" (eg. 401, 403, 404)
             */
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                // The wait for a response is over
                progressBar.hide()

                // If the error is not null, log it!
                t?.message?.let {
                    Log.e("BestSellerBooksFragment", errorResponse)
                }
            }
        }]
        //*/

    }

    /*
     * What happens when a particular book is clicked.
     */
    override fun onItemClick(item: Movie) {
//        val intent = Intent(context, ItemViewFragment::class.java)
//        intent.putExtra(item.title, 1f)
//        context?.startActivity(intent)
        val f:Fragment = ItemViewFragment(item)
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()

        transaction.replace(R.id.content, f)
        transaction.addToBackStack(null)
//        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//            this.activity as Activity,
//            (view?.findViewById(R.id.mvimg) as View?)!!, "move"
//        )
        transaction.commit()
    }

}
