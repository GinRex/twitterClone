package com.codepath.apps.simpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ginrex on 26/03/2016.
 */
public class user {
    //attributes
    public String name;
    public Long uid;
    public String screenName;
    public String profileImageURL;
    public String tagLine;
    public int followerCount;
    public int followingCount;

    public user() {

    }

    // get the attributes


    public String getTagLine() {
        return tagLine;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public String getName() {
        return name;
    }

    public Long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    //deserialize the json to user
    public static user fromJSON(JSONObject jsonObject) {
        user u = new user();

        //take the data and storage to the "u"
        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.profileImageURL = jsonObject.getString("profile_image_url");
            u.screenName = jsonObject.getString("screen_name");
            u.tagLine = jsonObject.getString("description");
            u.followerCount = jsonObject.getInt("followers_count");
            u.followingCount = jsonObject.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }
}
