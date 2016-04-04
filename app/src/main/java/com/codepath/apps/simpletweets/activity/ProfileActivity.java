package com.codepath.apps.simpletweets.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.client.TwitterClient;
import com.codepath.apps.simpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.simpletweets.models.user;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    user User;

    @Bind(R.id.tvName) TextView tvName;
    @Bind(R.id.tvTagline) TextView tvTag;
    @Bind(R.id.ivProfilePic) ImageView ivUserProfilePic;
    @Bind(R.id.tvFollowers) TextView tvFollowers;
    @Bind(R.id.tvFollowing) TextView tvFollowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_profile);

        client = TwitterApplication.getRestClient();
        String screen_name = getIntent().getStringExtra("screen_name");

        client.getUserInfo(screen_name, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                User = user.fromJSON(response);
                Log.d("user", User.toString());
                getSupportActionBar().setTitle("@" + User.getName());

                populateProfileHeader(User);
            }
        });

        

        if (savedInstanceState == null) {
            UserTimelineFragment fragment = UserTimelineFragment.newInstance(screen_name);

            FragmentTransaction fm = getSupportFragmentManager().beginTransaction();

            fm.replace(R.id.flContainer, fragment);

            fm.commit();
        }
    }

    private void populateProfileHeader(user user) {
        ButterKnife.bind(this);
        tvName.setText(user.getName());
        tvTag.setText(user.getTagLine());
        tvFollowers.setText(user.getFollowerCount() + " followers");
        tvFollowing.setText(user.getFollowingCount() + " following");
        Picasso.with(this).load(user.getProfileImageURL()).into(ivUserProfilePic);
    }

    private void populateUserpro(String screenName) {
        client.getUserTimeline(screenName, 0, new JsonHttpResponseHandler() {

        });
    }
}
