package com.codepath.apps.simpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.adapter.RecyclerAdapter;
import com.codepath.apps.simpletweets.models.Tweet;

import java.util.ArrayList;

/**
 * Created by ginrex on 02/04/2016.
 */
public class TweetsListFragment extends Fragment {

    protected ArrayList<Tweet> tweets;
    protected RecyclerAdapter rvAdapter;
    protected RecyclerView rvTweet;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        rvTweet = (RecyclerView) v.findViewById(R.id.rvTweets);
        rvTweet.setAdapter(rvAdapter);
        return v;
    }

    public void customLoadMoreDataFromApi(int offset) {
        // Send an API request to retrieve appropriate data using the offset value as a parameter.
        // Deserialize API response and then construct new objects to append to the adapter
        // Add the new objects to the data source for the adapter
        // For efficiency purposes, notify the adapter of only the elements that got changed
        // curSize will equal to the index of the first element inserted because the list is 0-indexed
        int curSize = rvAdapter.getItemCount();
        rvAdapter.notifyItemRangeInserted(curSize, tweets.size() - 1);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        rvAdapter = new RecyclerAdapter(getContext(), tweets);
    }

    public void clear() {
        tweets.clear();
    }

    public void notifyDataSetChanged() {
        rvAdapter.notifyDataSetChanged();
    }

    public void setLayoutManager(LinearLayoutManager linearLayoutManager) {
        rvTweet.setLayoutManager(linearLayoutManager);
    }

}
