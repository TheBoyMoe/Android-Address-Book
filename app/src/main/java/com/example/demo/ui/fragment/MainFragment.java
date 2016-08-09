package com.example.demo.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;
import com.example.demo.common.Constants;
import com.example.demo.ui.activity.MainActivity;

import timber.log.Timber;

public class MainFragment extends Fragment implements MainActivity.onBackPressedListener{

    private static final String IS_DETAIL_SHOWING = "is_detail_showing";
    private boolean mIsTablet = false;
    private boolean mIsPortrait = false;
    private boolean mIsDetailShowing = false;
    private Uri mItemUri = null;

    public MainFragment() {}

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsTablet = getResources().getBoolean(R.bool.isTablet);
        mIsPortrait = getResources().getBoolean(R.bool.isPortrait);
        if (savedInstanceState != null) {
            mItemUri = savedInstanceState.getParcelable(Constants.MODEL_ITEM_URI);
            mIsDetailShowing = savedInstanceState.getBoolean(IS_DETAIL_SHOWING);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.MODEL_ITEM_URI, mItemUri);
        outState.putBoolean(IS_DETAIL_SHOWING, mIsDetailShowing);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        if (mIsTablet && !mIsPortrait) { // tablet in landscape
            showTabletView();
        } else {
            showPhoneView();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mIsDetailShowing) {
            if (mIsTablet && !mIsPortrait) {
                showSecondaryFragment(HomeDetailFragment.newInstance(mItemUri), false, null, false);
            } else {
                showPrimaryFragment(HomeDetailFragment.newInstance(mItemUri), false, null, false);
            }
        }
    }

    // impl hosting activities onBackPressed method
    @Override
    public boolean onBackPressed() {
        ((MainActivity)getActivity()).setPageTitle("Home");
        if (mIsTablet && !mIsPortrait) {
            return false;
        } else {
            Fragment currentFragment = getChildFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment instanceof HomeDetailFragment) {
                // swap detail fragment for list, and display the drawer icon
                showPrimaryFragment(HomeFragment.newInstance(), false, null, true);
                mIsDetailShowing = false;
                ((MainActivity)getActivity()).hideUpNav();
                return true;
            }
        }
        return false;
    }


    // called by the child HomeFragment
    protected void listItemClick(Uri uri) {
        mIsDetailShowing = true;
        mItemUri = uri;

        if (mIsTablet && !mIsPortrait) { // tablet in landscape
            showSecondaryFragment(HomeDetailFragment.newInstance(mItemUri), false, null, true);
        } else {
            showPrimaryFragment(HomeDetailFragment.newInstance(mItemUri), false, null, true);
        }

        // tell the hosting activity to show the 'up arrow' on devices other than tablets in landscape orientation
        ((MainActivity)getActivity()).showUpNav();
    }

    // called by the child HomeDetailFragment
    protected void setPageTitle(String title) {
        ((MainActivity)getActivity()).setPageTitle(title);
    }

    private void showTabletView() {
        // Timber.i("%s, tablet landscape, isTablet: %s, isPortrait: %s, detailPosition: %d", Constants.LOG_TAG, mIsTablet, mIsPortrait, mDetailItemPosition);
        showPrimaryFragment(HomeFragment.newInstance(), true, "home", false);
        showSecondaryFragment(HomeDetailFragment.newInstance(mItemUri), false, null, false);
    }

    private void showPhoneView() {
        // Timber.i("%s, everything else, isTablet: %s, isPortrait: %s, detailPosition: %d", Constants.LOG_TAG, mIsTablet, mIsPortrait, mDetailItemPosition);
         showPrimaryFragment(HomeFragment.newInstance(), false, null, false); // change
    }

    private void showFragment(Fragment fragment, boolean primary, boolean addToBackStack, String backStackTag, boolean animate) {
        if (fragment == null) return;

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        if (mIsTablet && !mIsPortrait) { // tablet in landscape
            if (primary) {
                if (addToBackStack) {
                    // Timber.i("%s, tablet landscape, primary, addToBackStack");
                    ft.replace(R.id.fragment_container, fragment).addToBackStack(backStackTag).commit();
                } else {
                    // Timber.i("%s, tablet landscape, primary, don't addToBackStack");
                    ft.replace(R.id.fragment_container, fragment).commit();
                }
            } else {
                if (addToBackStack) {
                    // Timber.i("%s, tablet landscape, secondary, addToBackStack");
                    ft.replace(R.id.detail_pane, fragment).addToBackStack(backStackTag).commit();
                } else  {
                    // Timber.i("%s, tablet landscape, secondary, don't addToBackStack");
                    ft.replace(R.id.detail_pane, fragment).commit();
                }
            }
        } else { // everything else
            if (animate) {
                if (fragment instanceof HomeFragment) {
                    ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
                } else {
                    ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out);
                }
            }
            if (addToBackStack) {
                //Timber.i("%s, everything else, addToBackStack", Constants.LOG_TAG);
                ft.replace(R.id.fragment_container, fragment).addToBackStack(backStackTag).commit();
            } else {
                //Timber.i("%s, everything else, don't addToBackStack", Constants.LOG_TAG);
                ft.replace(R.id.fragment_container, fragment).commit();
            }
        }
    }

    private void showFragment(Fragment fragment, boolean primary) {
        showFragment(fragment, primary, true, null, false);
    }

    private void showPrimaryFragment(Fragment fragment, boolean addToBackStack, String backStackTag, boolean animate) {
        showFragment(fragment, true, addToBackStack, backStackTag, animate);
    }

    private void showSecondaryFragment(Fragment fragment, boolean addToBackStack, String backStackTag, boolean animate) {
        showFragment(fragment, false, addToBackStack, backStackTag, animate);
    }


}
