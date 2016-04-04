package com.codepath.apps.simpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ginrex on 26/03/2016.
 */
public class Tweet implements Parcelable {
    // attributes
    public String body;
    public long uid;
    public user User;
    public String createdAt;
    public String picURL;
    public int likeC;
    public int shareC;

    //empty constructor for the parceler libary
    public Tweet() {
    }


    //acess the attributes


    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public user getUser() {
        return User;
    }

    public String getPicURL() {
        return picURL;
    }

    public int getShareC() {
        return shareC;
    }

    public int getLikeC() {
        return likeC;
    }

    //from JSOn -> tweet obj
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        //extract and store data to tweet obj using try/catch
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt =  jsonObject.getString("created_at");
            tweet.User = user.fromJSON(jsonObject.getJSONObject("user"));

            tweet.likeC = jsonObject.getInt("favorite_count");
            //Log.d("like", String.valueOf(tweet.likeC));
            tweet.shareC = jsonObject.getInt("retweet_count");
            JSONArray media = jsonObject.getJSONObject("entities").getJSONArray("media");
            JSONObject mediap = media.getJSONObject(0);
            tweet.picURL = mediap.getString("media_url");
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }


    //get date time ago
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }


    //pass the array of items
    //output to the list
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (tweet!= null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }

        return tweets;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.body);
        dest.writeLong(this.uid);
        //dest.writeParcelable((Parcelable) this.User, flags);
        dest.writeString(this.createdAt);
        dest.writeString(this.picURL);
        dest.writeInt(this.likeC);
        dest.writeInt(this.shareC);
    }

    protected Tweet(Parcel in) {
        this.body = in.readString();
        this.uid = in.readLong();
        //this.User = in.readParcelable(user.class.getClassLoader());
        this.createdAt = in.readString();
        this.picURL = in.readString();
        this.likeC = in.readInt();
        this.shareC = in.readInt();
    }

    public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel source) {
            return new Tweet(source);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };
}
