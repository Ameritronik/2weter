package com.codepath.apps.tweeter.network;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthAsyncHttpClient;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
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
	public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
	//public static final String REST_CONSUMER_KEY = "RYwocycb6TCu9f0fsPNrEulHm"; // alternate did not work
	public static final String REST_CONSUMER_KEY = "bhQ7PamFItULbj0yJkXltfqLh";       // Change this
	//public static final String REST_CONSUMER_SECRET = "WObp5bBhhVzwLAcjRRVqgC9JvooWYxuBEdVH1FfFhWger0Ycf2";
	public static final String REST_CONSUMER_SECRET = "ZtD0pO34Bz6ETbzSdtvBo9EjIZxDkPuyEHbiESbosfKcv47kSD"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://hkcpsimpletweets"; // Change this (here and in manifest)
	private long id =1;
	private String myTweet = "no News";
	private String tweetUser_id = "HK";

	public void setMyTweet(String myTweet) {
		this.myTweet = myTweet;
	}

	public void settweetUser_id(String tUser_id) {
		this.tweetUser_id = tUser_id;
	}

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	// Method == Endpoint
	// HomeTimeLine -- gets us the home timeline
	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
		long newId = this.id;
		if (newId < 10) {
			newId = 1;
			params.put("since_id", newId);
		} else {
			params.put("max_id",newId);
		}
        // Execute request
        getClient().get(apiUrl,params,handler);
	}
	public void getHomeTimelineExtended(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		String tMode = "extended_entities";
		RequestParams params = new RequestParams();
		params.put("count", 25);
		long newId = this.id;
		if (newId < 10) {
			newId = 1;
			params.put("since_id", newId);
		} else {
			params.put("max_id",newId);
		}
		params.put("tweet_mode",tMode);
		//Log.d("DEBUG","From Client Url: "+apiUrl+" id: "+newId+" mode: "+tMode);
		// Execute request
		getClient().get(apiUrl,params,handler);
	}

	// Compose tweets

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
    public void postHomeTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", this.myTweet);
        //params.put("screen_name",this.tweetUser_id);
		String xTweet = "About to Tweet: Text "+this.myTweet+" To: "+this.tweetUser_id;
		Log.d("DEBUG",xTweet);
		// Execute request
        OAuthAsyncHttpClient client = getClient();
        Log.d("DEBUG",client.toString());
        client.post(apiUrl,params,handler);
    }

	// Method == Endpoint
	// HomeTimeLine -- gets us the home timeline
	public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		// Execute request
		getClient().get(apiUrl,params,handler);
	}

	public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("screen_name",screenName);
		params.put("count", 25);
		// Execute request
		getClient().get(apiUrl,params,handler);
	}
	public void getUserInfo(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		// Execute request
		getClient().get(apiUrl,null,handler);
	}

}
