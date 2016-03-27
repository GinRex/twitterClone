package com.codepath.apps.simpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.ComposeActivity;
import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetArrayAdapter aTweets;
    private RecyclerAdapter rvAdapter;
    //private ListView lvTweets;
    private RecyclerView rvTweet;

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mtimeline, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_timeline);
        //find listview
        //lvTweets = (ListView) findViewById(R.id.lvTweets);
        //create the arrayList
        tweets = new ArrayList<>();
        //construct the adapter from data source
        //aTweets = new TweetArrayAdapter(this, tweets);
        //connect to the listview
        //lvTweets.setAdapter(aTweets);
        //get the client

        rvTweet = (RecyclerView) findViewById(R.id.rvTweets);
        rvAdapter = new RecyclerAdapter(getApplicationContext(), tweets);

        rvTweet.setAdapter(rvAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTweet.setLayoutManager(linearLayoutManager);

        //add scrollinfinitive

        rvTweet.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline(page + 1);
            }
        });

        client = TwitterApplication.getRestClient();

        populateTimeline(0);
    }


    //send api requet
    //fill the view
     private void populateTimeline (int page) {
         client.getHomeTimeline(page, new JsonHttpResponseHandler() {
             //success

             @Override
             public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                 //@Override
                 //public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray json) {
                 //Log.d("DEBUG", json.toString());
                 //deserialize json
                 //create model
                 //load model data into listview

                 Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                 //if (Tweet.fromJSONArray(json) != null) {
                 tweets.addAll(Tweet.fromJSONArray(json));
                 rvAdapter.notifyDataSetChanged();
             }
             // on failure

             @Override
             public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
             //@Override
             //public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                 //Log.d("fail", errorResponse.toString());
                 Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
             }
         });
     }



    //onClick compose
    public void onComposeAction(MenuItem mi) {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivity(i);
    }

}
