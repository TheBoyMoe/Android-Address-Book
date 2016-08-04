package com.example.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.demo.ui.fragment.HomeDetailFragment;
import com.example.demo.ui.fragment.HomeFragment;
import com.example.demo.ui.fragment.SettingsFragment;

import timber.log.Timber;

/**
 *  References:
 *  [1] https://guides.codepath.com/android/Fragment-Navigation-Drawer
 *  [2] http://stackoverflow.com/questions/13472258/handling-actionbar-title-with-the-fragment-back-stack
 *  [3] http://stackoverflow.com/questions/17107005/how-to-clear-fragment-backstack-in-android
 *  [4] https://www.captechconsulting.com/blogs/supporting-phones-and-tablets-v1 (phone/tablet layout)
 */
public class MainActivity extends AppCompatActivity implements
        HomeFragment.Contract{

    private static final String CURRENT_PAGE_TITLE = "current_page_title";
    private static final String DETAIL_PAGE_FRAGMENT = "detail_page_fragment";
    private static final String IS_UP_VISIBLE = "is_up_visible";
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private CoordinatorLayout mLayout;
    private String mCurrentTitle;
    private boolean mIsTablet;
    private boolean mIsPortrait;
    private boolean mIsUpVisible;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

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

        // set the initial fragment on startup, otherwise restore the page title
        if (savedInstanceState == null) {
            displayInitialFragment();
        } else {
            mIsUpVisible = savedInstanceState.getBoolean(IS_UP_VISIBLE);
            if (mIsUpVisible) {
                showUpNav();
            }
            mCurrentTitle = savedInstanceState.getString(CURRENT_PAGE_TITLE);
            setTitle(mCurrentTitle);
        }

    }

    @Override
    public void onBackPressed() {
        // update the page title
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if (count <= 1) {
            finish();
        } else{
            mCurrentTitle = fm.getBackStackEntryAt(count - 2).getName();
            if (mCurrentTitle.equals(DETAIL_PAGE_FRAGMENT)) {
                mCurrentTitle = "Home";
            }
        }
        super.onBackPressed();
        setTitle(mCurrentTitle);
        if (mIsUpVisible) {
            hideUpNav();
        }

        // update nav drawer selection
        switch (mCurrentTitle) {
            case "Home":
                mNavigationView.setCheckedItem(R.id.drawer_home);
                break;
            case "Explore":
                mNavigationView.setCheckedItem(R.id.drawer_explore);
                break;
            case "Favourites":
                mNavigationView.setCheckedItem(R.id.drawer_favourite);
                break;
            case "Settings":
                mNavigationView.setCheckedItem(R.id.drawer_settings);
                break;
            case "About":
                mNavigationView.setCheckedItem(R.id.drawer_about);
                break;
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

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showSnackbar(mLayout, "Clicked fab");
            }
        });
    }

    private void setupDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
        switch (item.getTitle().toString()) {
            case "Home":
                mCurrentTitle = getString(R.string.nav_menu_title_home);
                break;
            case "Explore":
                mCurrentTitle = getString(R.string.nav_menu_title_explore);
                break;
            case "Favourites":
                mCurrentTitle = getString(R.string.nav_menu_title_favourite);
                break;
            case "Settings":
                mCurrentTitle = getString(R.string.nav_menu_title_settings);
                break;
            case "About":
                mCurrentTitle = getString(R.string.nav_menu_title_about);
                break;
            default:
                mCurrentTitle = getString(R.string.nav_menu_title_home);
        }

        // clear the back stack if adding home fragment again
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if (count > 1 && fragmentClass == HomeFragment.class) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        // hide up arrow and pop stack if home fragment is visible
        if (mIsUpVisible) {
            hideUpNav();
            getSupportFragmentManager().popBackStackImmediate();
        }
        addFragmentToLayout(fragment, true, true, mCurrentTitle);
        setTitle(mCurrentTitle); // display title on toolbar

        mDrawer.closeDrawers();
    }

    private void displayInitialFragment() {
        mCurrentTitle = getString(R.string.nav_menu_title_home);
        setTitle(mCurrentTitle);
        if (mIsTablet) {
            // add the list fragment
            addFragmentToLayout(HomeFragment.newInstance(), true, true, mCurrentTitle);

            // add detail fragment
            addFragmentToLayout(HomeDetailFragment.newInstance(), false, true, DETAIL_PAGE_FRAGMENT);
        } else {
            // add the list fragment
            addFragmentToLayout(HomeFragment.newInstance(), true, true, mCurrentTitle);
        }
    }


    @Override
    public void listItemClick() {
        if (!mIsTablet) {
            showUpNav();
            // swap list fragment for the detail fragment
            addFragmentToLayout(HomeDetailFragment.newInstance(), false, true, DETAIL_PAGE_FRAGMENT);
        } else {
            Utils.showSnackbar(mLayout, "on tablet");
        }
    }

    private void addFragmentToLayout(Fragment fragment, boolean primary, boolean addToBackStack, String fragmentTag) {
        if (fragment == null) return;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction(); // TODO add animation
        if (mIsTablet) {
            if (primary) {
                if (addToBackStack) {
                    ft.replace(R.id.fragment_container, fragment).addToBackStack(fragmentTag).commit();
                } else {
                    ft.replace(R.id.fragment_container,fragment).commit();
                }
            } else {
                if (addToBackStack) {
                    ft.replace(R.id.detail_pane, fragment).addToBackStack(fragmentTag).commit();
                } else {
                    ft.replace(R.id.detail_pane, fragment).commit();
                }
            }
        } else {
            if (addToBackStack) {
                ft.replace(R.id.fragment_container, fragment).addToBackStack(fragmentTag).commit();
            } else {
                ft.replace(R.id.fragment_container,fragment).commit();
            }
        }
    }

    private void showUpNav() {
        if (!mIsTablet || mIsPortrait) {
            mIsUpVisible = true;
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mToolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
            mDrawerToggle.setToolbarNavigationClickListener(clickDrawerNavIcon);
        }
    }

    private void hideUpNav() {
        if (!mIsTablet || mIsPortrait) {
            mIsUpVisible = false;
            mDrawerToggle.setDrawerIndicatorEnabled(true);
        }
    }

    View.OnClickListener clickDrawerNavIcon = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getSupportFragmentManager().popBackStackImmediate();
            hideUpNav();
        }
    };

}
