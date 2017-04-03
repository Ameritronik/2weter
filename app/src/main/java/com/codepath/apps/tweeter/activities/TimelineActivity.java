package com.codepath.apps.tweeter.activities;

//region This section includes all imports

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.tweeter.R;
import com.codepath.apps.tweeter.fragments.ComposeTweetFragment;
import com.codepath.apps.tweeter.fragments.HomeTimeLineFragment;
import com.codepath.apps.tweeter.fragments.MentionsTimeLineFragment;

import butterknife.ButterKnife;
//endregion

public class TimelineActivity extends AppCompatActivity
        implements ComposeTweetFragment.SendTweetListener {
    private SmartFragmentStatePagerAdapter adapterViewPager;
    private ViewPager vpPager;
    MenuItem miActionProgressItem;

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
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        // Set the viewpager adapter for the pager
        adapterViewPager = new myPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        // find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);
    }
    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
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

    @Override
    public void onFinishEditDialog(String twBody, String twURL) {
        HomeTimeLineFragment htm = (HomeTimeLineFragment) adapterViewPager.getRegisteredFragment(0);
        htm.dataBack(twBody,twURL);
    }


    public class myPagerAdapter extends SmartFragmentStatePagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = { "Home", "Mentions"};
        // Adapter gets the manager to insert or remove from the activity
        public myPagerAdapter (FragmentManager fm) {
            super(fm);
        }
        // Controls the creation and order of the fragments within the pager
        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return HomeTimeLineFragment.newInstance(0, "Home");
                case 1:
                    return MentionsTimeLineFragment.newInstance(1,"Mentions");
                default:
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
    public void composeTweet(MenuItem mi){
        // Launch composer dialog fragment;
        ComposeTweetFragment composeTweetDialog = new ComposeTweetFragment();
        FragmentManager mFragManager = getSupportFragmentManager();
        composeTweetDialog.show(mFragManager,"Tweet");
    }
    public void onSearch(MenuItem mi) {
        Toast.makeText(this,"You clicked onSearch",Toast.LENGTH_SHORT).show();
    }
    public void onSendDirectMessage(MenuItem mi) {
        Toast.makeText(this,"You clicked on Send Dir Msg",Toast.LENGTH_SHORT).show();
        //    ComposeDirectMessageFragment composeDMsgDialog = new ComposeDirectMessageFragment();
        //    FragmentManager mFragManager = getSupportFragmentManager();
        //    composeDMsgDialog.show(mFragManager,"Tweet");
    }

}
