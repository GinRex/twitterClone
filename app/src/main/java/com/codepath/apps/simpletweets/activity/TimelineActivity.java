package com.codepath.apps.simpletweets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.ReplyTweetDialogFragment;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.client.TwitterClient;
import com.codepath.apps.simpletweets.fragments.ComposeDialogFragment;
import com.codepath.apps.simpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.simpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.simpletweets.models.user;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class TimelineActivity extends AppCompatActivity implements ComposeDialogFragment.ComposeDialogListener{
    private SwipeRefreshLayout swipeContainer;
    private TwitterClient client;
    private String screenName;

    //occur when finish dialog
    @Override
    public void onFinishEditDialog() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

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
        if (id == R.id.iProfile) {
            profilePage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void profilePage() {
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("screen_name", screenName);
        startActivity(i);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //get the viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the adapter
        vpPager.setAdapter(new TweetsPaperAdapter(getSupportFragmentManager()));
        //find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);

        //get the current user name
        client = TwitterApplication.getRestClient();
        client.getCurrentUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user User;
                String screen_Name = user.fromJSON(response).getScreenName();
                screenName = screen_Name;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });



//        if (savedInstanceState == null) {
//            accessFragment();
//        }



        // Lookup the swipe container view

//        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
//
//        // Setup refresh listener which triggers new data loading
//
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//
//            @Override
//
//            public void onRefresh() {
//                fragmentTweetsList.clear();
//
//                populateTimeline(0);
//
//            }
//
//        });

        // Configure the refreshing colors

//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//
//                android.R.color.holo_green_light,
//
//                android.R.color.holo_orange_light,
//
//                android.R.color.holo_red_light);



    }



    private void composeDialog () {
        FragmentManager fm = getSupportFragmentManager();
        ComposeDialogFragment composeDialogFragment = ComposeDialogFragment.newInstance("Tweet ur status");
        //composeDialogFragment.setTargetFragment(HomeTimelineFragment.class, 300);
        composeDialogFragment.show(fm, "fragment_compose");
    }


    public void showReplyDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ReplyTweetDialogFragment replyTweetDialogFragment = ReplyTweetDialogFragment.newInstance("wtv");
        replyTweetDialogFragment.show(fm, "fragment_detail");
    }



    //return the order of the fragment
    public class TweetsPaperAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = {"Home", "Mentions"};

        public TweetsPaperAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            }
            else if (position == 1) {
                return new MentionsTimelineFragment();
            }else return null;
        }

        //tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        //number of swipes
        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

}
