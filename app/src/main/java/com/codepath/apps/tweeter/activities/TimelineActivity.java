package com.codepath.apps.tweeter.activities;

//region This section includes all imports

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.tweeter.R;
import com.codepath.apps.tweeter.fragments.ComposeTweetFragment;
import com.codepath.apps.tweeter.fragments.TweetsListFragment;
import com.codepath.apps.tweeter.models.Tweet;
import com.codepath.apps.tweeter.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
//endregion

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private TweetsListFragment fragmentTweetsList;
    private static Toast toast;

    private void showEditDialog() {
        //showToast(getBaseContext(),"You clicked on compose button");
        ComposeTweetFragment composeTweetDialog = new ComposeTweetFragment();
        FragmentManager mFragManager = getSupportFragmentManager();
        composeTweetDialog.show(mFragManager,"Tweet");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.tw_icon_white);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //region Floating Action buttom code
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setSize(FloatingActionButton.SIZE_MINI);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showEditDialog();
//            }
//        });
        //endregion

        client = com.codepath.apps.tweeter.activities.TwitterApplication.getRestClient();
        //if(com.codepath.apps.tweeter.network.checknetwork.HaveCloud()) {
        populateTimeline(1);
        //}
        if(savedInstanceState == null) {
            fragmentTweetsList =  (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
            Log.d("DEBUG","Executed the fragment list access");
        }
    }

    // Send API request to get the timeline json
    // Fill the list view by creating the tweet objects from json
    private void populateTimeline(long uId) {
        client.setId(uId);
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            // Successful
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //super.onSuccess(statusCode, headers, response);
                //Log.d("DEBUG",json.toString()); // Got JSON here
                // Deserialize
                // Create models and add to adapter here
                //Load models into listview
                //tweets.addAll(Tweet.fromJSONArray(json));
                //Log.d("DEBUG","Tw:"+tweets.toString());
                //aTweets.notifyDataSetChanged();
                fragmentTweetsList.addAll(Tweet.fromJSONArray(json));
                fragmentTweetsList.refreshView();
            }
            // failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                String JsonErrorMessage = "Json message corrupted";
                Log.d("DEBUG", JsonErrorMessage);
                showToast(getBaseContext(), JsonErrorMessage);
            }
        });
    }


    public static void showToast(Context context, String message) {
        if (toast != null) { // prevent multi-toasts
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }
    public void showCustomToast(String message, String color) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));
        TextView text = (TextView) layout.findViewById(R.id.toastText);
        text.setText(message);
        if(color != null) {
            if (color.equals("BLACK")) {
                text.setTextColor(Color.BLACK);
            } else if (color.equals("RED")) {
                text.setTextColor(Color.RED);
            } else if (color.equals("BLUE")) {
                text.setTextColor(Color.BLUE);
            }
        } // else defaul toast text color of green
        Toast toast = new Toast(getBaseContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showToast(getBaseContext(),"You clicked on compose button");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
