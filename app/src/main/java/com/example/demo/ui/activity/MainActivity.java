package com.example.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.demo.R;
import com.example.demo.common.Constants;
import com.example.demo.common.Utils;
import com.example.demo.ui.fragment.AboutFragment;
import com.example.demo.ui.fragment.ExploreFragment;
import com.example.demo.ui.fragment.FavouriteFragment;
import com.example.demo.ui.fragment.HomeFragment;
import com.example.demo.ui.fragment.MainFragment;
import com.example.demo.ui.fragment.SettingsFragment;

import timber.log.Timber;

/**
 *  References:
 *  [1] https://guides.codepath.com/android/Fragment-Navigation-Drawer
 *  [2] http://stackoverflow.com/questions/13472258/handling-actionbar-title-with-the-fragment-back-stack
 *  [3] http://stackoverflow.com/questions/17107005/how-to-clear-fragment-backstack-in-android
 *  [4] https://www.captechconsulting.com/blogs/supporting-phones-and-tablets-v1 (phone/tablet layout)
 */
public class MainActivity extends AppCompatActivity{

    private static final String CURRENT_PAGE_TITLE = "current_page_title";
    private static final String IS_UP_VISIBLE = "is_up_visible";

    private DrawerLayout mDrawer;
    private CoordinatorLayout mLayout;
    private String mCurrentTitle;
    private boolean mIsTablet;
    private boolean mIsPortrait;
    private boolean mIsUpVisible;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

    public interface onBackPressedListener {
        boolean onBackPressed();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mIsTablet = getResources().getBoolean(R.bool.isTablet);
        mIsPortrait = getResources().getBoolean(R.bool.isPortrait);

        initToolbar();
        initFab();
        setupDrawer();

        // set the initial fragment on startup
        if (savedInstanceState == null) {
            displayInitialFragment();
        } else {
            // otherwise restore the current title and display up arrow where req'd
            mCurrentTitle = savedInstanceState.getString(CURRENT_PAGE_TITLE);
            if (mIsTablet && !mIsPortrait) { // tablets in landscape orientation
                setTitle("Home");
            } else { // otherwise
                if (mCurrentTitle == null && !mIsUpVisible) mCurrentTitle = "Home";
                setTitle(mCurrentTitle);
            }
            mIsUpVisible = savedInstanceState.getBoolean(IS_UP_VISIBLE);
            if (mIsUpVisible || (mIsTablet && mIsPortrait && (!mCurrentTitle.equals("Home")))) {
                showUpNav();
            }
            // DEBUG
            Timber.i("%s: title: %s, portrait: %s, isUpVisible: %s",
                    Constants.LOG_TAG, mCurrentTitle, mIsPortrait, mIsUpVisible);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsUpVisible) {
            showUpNav();
        } else {
            hideUpNav();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_container);
        if (currentFragment == null) return;

        if (currentFragment instanceof onBackPressedListener) {
            if (((onBackPressedListener)currentFragment).onBackPressed()) {
                // dealt with in the fragment
                return;
            }
            else {
                finish();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_PAGE_TITLE, mCurrentTitle);
        outState.putBoolean(IS_UP_VISIBLE, mIsUpVisible);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Utils.showSnackbar(mLayout, "Clicked settings");
                return true;
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // helper methods
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() !=null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    // called from main fragment - set the title based on item clicked
    public void setPageTitle(String title) {
        mCurrentTitle = title;
        if (!(mIsTablet && !mIsPortrait)) {
            setTitle(mCurrentTitle);
        }
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelItemActivity.launch(MainActivity.this);
            }
        });
    }

    private void setupDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });

    }

    private void selectDrawerItem(MenuItem item) {
        // select the item to instantiate based on the item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch (item.getItemId()) {
            case R.id.drawer_home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.drawer_explore:
                fragmentClass = ExploreFragment.class;
                break;
            case R.id.drawer_favourite:
                fragmentClass = FavouriteFragment.class;
                break;
            case R.id.drawer_settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.drawer_about:
                fragmentClass = AboutFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Timber.e("%s: error loading fragment, %s", Constants.LOG_TAG, e.getMessage());
        }

        // highlight the selected item & update the page title
        item.setChecked(true);
        mDrawer.closeDrawers();
    }

    private void displayInitialFragment() {
        mCurrentTitle = getString(R.string.nav_menu_title_home);
        setTitle(mCurrentTitle);

        // load the container fragment which hosts the list/detail fragments
        // depending on whether the device is a phone or tablet
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_container, MainFragment.newInstance())
                .commit();

    }

    public void showUpNav() {
        if (!(mIsTablet && !mIsPortrait)) { // everything except tablets in landscape orientation
            mIsUpVisible = true;
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mToolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
            mDrawerToggle.setToolbarNavigationClickListener(clickBackArrowNavIcon);
        }
    }

    public void hideUpNav() {
        if (!(mIsTablet && !mIsPortrait)) {
            mIsUpVisible = false;
            mDrawerToggle.setDrawerIndicatorEnabled(true);
        }
    }

    View.OnClickListener clickBackArrowNavIcon = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    };

}
