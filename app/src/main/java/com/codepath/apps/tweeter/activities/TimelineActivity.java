package com.codepath.apps.tweeter.activities;

//region This section includes all imports

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.tweeter.R;
import com.codepath.apps.tweeter.fragments.ComposeTweetFragment;
import com.codepath.apps.tweeter.fragments.HomeTimeLineFragment;
import com.codepath.apps.tweeter.fragments.MentionsTimeLineFragment;

import butterknife.ButterKnife;
//endregion

public class TimelineActivity extends AppCompatActivity
        implements ComposeTweetFragment.SendTweetListener {
    TweetsPagerAdapter adapterViewPager = new TweetsPagerAdapter(null);
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
        // Get the viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        // Set the viewpager adapter for the pager
        adapterViewPager = new TweetsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        // find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);
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
        return super.onOptionsItemSelected(item);
    }


    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = { "Home", "Mentions"};
        // Adapter gets the manager insert or remove from the activity
        public TweetsPagerAdapter (FragmentManager fm) {
            super(fm);
        }
        // Controls the creation and order of the fragments within the pager
        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return new HomeTimeLineFragment();
            } else if (position == 1){
                return new MentionsTimeLineFragment();
            } else {
                return null;
            }
        }
        // Will return the Tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
        // returns how many tabs there are
        @Override
        public int getCount() {
            return tabTitles.length;
        }
        public int getItemPosition(Object object) {
            // this method will be called for every fragment in the ViewPager
            if (object instanceof MentionsTimeLineFragment) {
                return POSITION_UNCHANGED; // don't force a reload
            } else {
                // POSITION_NONE means something like: this fragment is no longer valid
                // triggering the ViewPager to re-build the instance of this fragment.
                return POSITION_NONE;
            }
        }

    }

    public void onProfileView(MenuItem mi){
        // Launch the profile view
        Intent i = new Intent(this,ProfileActivity.class);
        startActivity(i);
    }
    public void composeTweet(MenuItem mi){
        // Launch composer dialog fragment;
        //Toast.makeText(this,"You clicked on Compose",Toast.LENGTH_SHORT).show();
        ComposeTweetFragment composeTweetDialog = new ComposeTweetFragment();
        FragmentManager mFragManager = getSupportFragmentManager();
        composeTweetDialog.show(mFragManager,"Tweet");
    }

    @Override
    public void onFinishEditDialog(String twBody, String twURL) {
        Log.d("DEBUG"," Time Line Activity Got back twBody "+twBody);
        Log.d("DEBUG"," Time Line Activity Got back twURL "+twURL);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fr = adapterViewPager.getItem(0);
        if( fr instanceof HomeTimeLineFragment) {
            HomeTimeLineFragment htm = new HomeTimeLineFragment();
            htm.dataBack(twBody,twURL);
        }
    }
}
