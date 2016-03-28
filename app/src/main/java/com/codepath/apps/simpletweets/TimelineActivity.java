package com.codepath.apps.simpletweets;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity implements ComposeActivity.ComposeActivityListener{
    private SwipeRefreshLayout swipeContainer;

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private RecyclerAdapter rvAdapter;
    private RecyclerView rvTweet;

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mtimeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itCompose) {
            composeDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //do somthing after dismiss the dialog
    @Override
    public void onFinishEditDialog() {
        //clear and reload data
        tweets.clear();
        populateTimeline(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_timeline);
        //find listview
        //lvTweets = (ListView) findViewById(R.id.lvTweets);
        //create the arrayList
        tweets = new ArrayList<>();
        //construct the adapter from data source;
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



        // Lookup the swipe container view

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {

                // Your code to refresh the list here.

                // Make sure you call swipeContainer.setRefreshing(false)

                // once the network request has completed successfully.
                tweets.clear();

                populateTimeline(0);

            }

        });

        // Configure the refreshing colors

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);

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
                 swipeContainer.setRefreshing(false);
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


    private void composeDialog () {
        FragmentManager fm = getSupportFragmentManager();
        ComposeActivity composeActivity = ComposeActivity.newInstance("Tweet ur status");
        composeActivity.show(fm, "fragment_compose");
    }

//    //onClick compose
//    public void onComposeAction(MenuItem mi) {
//        Intent i = new Intent(this, ComposeActivity.class);
//        startActivity(i);
//    }

}
