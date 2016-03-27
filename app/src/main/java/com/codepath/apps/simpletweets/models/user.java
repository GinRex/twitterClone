package com.codepath.apps.simpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ginrex on 26/03/2016.
 */
public class user {
    //attributes
    private String name;
    private Long uid;
    private String screenName;
    private String profileImageURL;

    // get the attributes


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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }
}
