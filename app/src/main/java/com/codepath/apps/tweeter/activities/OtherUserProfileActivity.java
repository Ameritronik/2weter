package com.codepath.apps.tweeter.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.tweeter.R;
import com.codepath.apps.tweeter.fragments.UserTimeLineFragment;
import com.squareup.picasso.Picasso;

public class OtherUserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Bad Title");
        String screenName = getIntent().getStringExtra("Name");
        actionBar.setTitle(screenName);
        String Name = getIntent().getStringExtra("screen_name");
        String followersCt = getIntent().getStringExtra("followersCt");
        String friendsCt = getIntent().getStringExtra("friendsCt");
        String tagLine = getIntent().getStringExtra("tagLine");
        String ProfileImage = getIntent().getStringExtra("ProfileImageUrl");
        popProfileHeader(screenName, Name, followersCt,
                friendsCt, tagLine, ProfileImage);
        // The null check is to make sure that the fragment is started only once
        // ie, if backgrounded, the saved instance will not be null so previous
        // fragment is pulled up
        if(savedInstanceState == null ) {
            // The order of calling the beginTransition , followed by replace
            // followed by commit is important
            // create the user time line fragment
            UserTimeLineFragment fragmentUserTimeLine =
                    UserTimeLineFragment.newInstance(screenName);
            // Display the user fragment in this activity in a dynamic way
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeLine);
            ft.commit(); // Starts the transaction, changes the fragment view
        }
    }
    private void popProfileHeader(String screenName, String Name, String followersCt,
                                        String friendsCt, String tagLine, String ProfileImage) {
        TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        TextView tvRealName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagLine);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvScreenName.setText(screenName);
        tvRealName.setText(Name);
        tvTagline.setText(tagLine);
        tvFollowers.setText(followersCt+" Followers ");
        tvFollowing.setText(friendsCt+"  Following");
        Picasso.with(this).load(ProfileImage).into(ivProfileImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
