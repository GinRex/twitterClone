package com.codepath.apps.simpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.client.TwitterClient;
import com.codepath.apps.simpletweets.interfaces.EndlessRecyclerViewScrollListener;
import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ginrex on 03/04/2016.
 */
public class UserTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline(0);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTweet.setLayoutManager(linearLayoutManager);
        rvTweet.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                //Log.d("loadm", "ok");
                populateTimeline(page + 1);
                customLoadMoreDataFromApi(page);
            }
        });
    }

    // Creates a new fragment given an int and title
    // DemoFragment.newInstance(5, "Hello");
    public static UserTimelineFragment newInstance(String screen_name) {
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        userFragment.setArguments(args);
        return userFragment;
    }

    //send api requet
    //fill the view
    private void populateTimeline(int page) {
        String screenName = getArguments().getString("screen_name");
        client.getUserTimeline(screenName, page, new JsonHttpResponseHandler() {
            //success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                if (Tweet.fromJSONArray(json) != null) {
                    tweets.addAll(Tweet.fromJSONArray(json));
                    notifyDataSetChanged();
                }
                //swipeContainer.setRefreshing(false);
            }
            // on failure

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
