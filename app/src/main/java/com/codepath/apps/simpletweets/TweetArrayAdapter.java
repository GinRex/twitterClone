package com.codepath.apps.simpletweets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ginrex on 26/03/2016.
 */

//take the data from object and fectch to listview
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the tweet
        Tweet tweet = getItem(position);
        //inflate the template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        ImageView userProfilePic = (ImageView) convertView.findViewById(R.id.ivProfilePic);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        //pupulate data into subview
        tvName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        userProfilePic.setImageResource(0);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageURL()).into(userProfilePic);
        //return the view into the list
        return convertView;
    }
}
