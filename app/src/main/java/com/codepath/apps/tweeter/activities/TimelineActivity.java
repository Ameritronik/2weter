package com.codepath.apps.tweeter.activities;

//region This section includes all imports

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.tweeter.R;
import com.codepath.apps.tweeter.fragments.ComposeTweetFragment;
import com.codepath.apps.tweeter.fragments.HomeTimeLineFragment;
import com.codepath.apps.tweeter.fragments.MentionsTimeLineFragment;
import com.codepath.apps.tweeter.fragments.TweetsListFragment;

import butterknife.ButterKnife;
//endregion

public class TimelineActivity extends AppCompatActivity {
    private TweetsListFragment fragmentTweetsList;
    private Toast toast;
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
        // Get the viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        // Set the viewpager adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        // find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);
    }

    public void showToast(Context context, String message) {
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
        return super.onOptionsItemSelected(item);
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = { "Home", "Mentions"};
        // Adapter gets the manager insert or remove from the activity
        public TweetsPagerAdapter (FragmentManager fm) {
            super(fm);
        }
        // Controls the creation and order of the fragments withing the pager
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
    }

    public void onProfileView(MenuItem mi){
        // Launch the profile view
        Intent i = new Intent(this,ProfileActivity.class);
        startActivity(i);
    }
}
