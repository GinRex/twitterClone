package com.codepath.apps.simpletweets.client;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "Vgx23GgwFOpzE1CWwVTHoF4Xi";       // Change this
	public static final String REST_CONSUMER_SECRET = "rH1kyIDoxfmd7s8qBLzwc9EoT39yXna26rVnKwSayfgn1pyQkv"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}
	// DEFINE METHODS for different API endpoints here


	public void getHomeTimeline(int page, AsyncHttpResponseHandler handler) {
		String apiURL = getApiUrl("statuses/home_timeline.json");
		// the params
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("since_id", 1);
		params.put("page", page);
		// execute the request
		getClient().get(apiURL, params, handler);
	}

	// compose tweets

	public void composeNewTweet(String status, AsyncHttpResponseHandler handler) {
		String apiURL = getApiUrl("statuses/update.json");
		Log.d("test", apiURL);
		RequestParams params = new RequestParams();
		params.put("status", status);

		getClient().post(apiURL, params, handler);
	}

	public void getMentionsTimeline(int page, JsonHttpResponseHandler handler) {
		String apiURL = getApiUrl("statuses/mentions_timeline.json");
		// the params
		RequestParams params = new RequestParams();
		params.put("count", 25);
		//params.put("since_id", 1);
		params.put("page", page);
		// execute the request
		getClient().get(apiURL, params, handler);

	}


	public void getUserTimeline(String screenName, int page, AsyncHttpResponseHandler handler) {
		String apiURL = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("screen_name", screenName);
		params.put("page", page);
		// execute the request
		getClient().get(apiURL, params, handler);
	}


	public void getUserInfo(String screenName, AsyncHttpResponseHandler handler) {
		String apiURL = getApiUrl("users/show.json");
		RequestParams params = new RequestParams();
		params.put("screen_name", screenName);
		getClient().get(apiURL, params, handler);
	}

	public void getCurrentUserInfo(AsyncHttpResponseHandler handler) {
		String apiURL = getApiUrl("account/verify_credentials.json");
		getClient().get(apiURL, null, handler);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}