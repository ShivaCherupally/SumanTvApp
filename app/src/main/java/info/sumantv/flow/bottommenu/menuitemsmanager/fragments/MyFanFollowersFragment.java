package info.sumantv.flow.bottommenu.menuitemsmanager.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.menu_list.ProfilesListCommon.ProfilesListFragment;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;

public class MyFanFollowersFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    public static TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    String USER_BOOK_APPOINTMENT = "";
    String CELEB_ID = "";
    boolean isFromCelebProfile = false;
    public static MyFanFollowersFragment instance = null;
    private ProfilesListFragment blockListFragment;
    boolean isSelf = false;
    public String[] tabTitles;
    boolean fromCelebProfilePage = false;
    String visibleTabname = "";

    public static MyFanFollowersFragment newInstance() {
        MyFanFollowersFragment fragment = new MyFanFollowersFragment();
        return fragment;
    }

    public static MyFanFollowersFragment getInstance() {
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.mycelebrity_activity, null);

        try {
            if (getActivity().getIntent().getExtras().getString("FROM_USER_PROFILE") != null) {
                USER_BOOK_APPOINTMENT = getActivity().getIntent().getExtras().getString("FROM_USER_PROFILE");
                CELEB_ID = getActivity().getIntent().getExtras().getString("CELEB_ID");
                isFromCelebProfile = getActivity().getIntent().getExtras().getBoolean("isFromCelebProfile", false);
            } else {
                USER_BOOK_APPOINTMENT = "";
                CELEB_ID = getActivity().getIntent().getExtras().getString("CELEB_ID");
            }
        } catch (Exception e) {
        }

        if (SessionManager.userLogin.userId.equals(CELEB_ID)) {
            tabTitles = new String[]{Constants.ProfilesConstants.FANS_OF_TAB, Constants.ProfilesConstants.FOLLOWERS_TAB, Constants.ProfilesConstants.BLOCKS_TAB};
        } else {
            tabTitles = new String[]{Constants.ProfilesConstants.FANS_OF_TAB, Constants.ProfilesConstants.FOLLOWERS_TAB};

        }
        initializeViews(root);


        return root;
    }

    private void initializeViews(View root) {
        tabLayout = (TabLayout) root.findViewById(R.id.tab_layout_fans);
        viewPager = (ViewPager) root.findViewById(R.id.view_pager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setOnTabSelectedListener(this);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }
        });

        tabLayout.setupWithViewPager(viewPager);

        mViewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());
        viewPager.setAdapter(mViewPagerAdapter);
        try {
            if (getActivity().getIntent().getExtras().getString("FAN_OR_FOLLOW") != null) {
                fromCelebProfilePage = true;

                if (getActivity().getIntent().getExtras().getString("FAN_OR_FOLLOW").equals("FAN")) {
                    viewPager.setCurrentItem(0);
                    visibleTabname = Constants.ProfilesConstants.FANS_OF_TAB;
                } else if (getActivity().getIntent().getExtras().getString("FAN_OR_FOLLOW").equals("FOLLOW")) {
                    viewPager.setCurrentItem(1);
                    visibleTabname = Constants.ProfilesConstants.FOLLOWERS_TAB;
                }
            } else {
                fromCelebProfilePage = false;
                visibleTabname = "";
            }

        } catch (Exception e) {
        }
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        int tabCount;

        public ViewPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return redirectFragment(CELEB_ID, Constants.ProfilesConstants.FANS_OF_TAB);
                case 1:
                    return redirectFragment(CELEB_ID, Constants.ProfilesConstants.FOLLOWERS_TAB);
                case 2:
                    return blockListFragment = redirectFragment(CELEB_ID, Constants.ProfilesConstants.BLOCKS_TAB);

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

    private ProfilesListFragment redirectFragment(String profileId, String tabName) {
        ProfilesListFragment mFollowersFragment = null;

        if (profileId != null && !profileId.isEmpty() && profileId.length() > 0) {
            if (Common.isLoginId().equals(profileId)) {
                mFollowersFragment = new ProfilesListFragment(profileId, Common.isCelebStatus(getContext()), tabName,
                        false, fromCelebProfilePage, visibleTabname);
            } else {
                mFollowersFragment = new ProfilesListFragment(profileId, isFromCelebProfile, tabName, false,
                        fromCelebProfilePage, visibleTabname);
            }
        } else {
            mFollowersFragment = new ProfilesListFragment(Common.isLoginId(),
                    Common.isCelebStatus(getContext()), tabName, false, fromCelebProfilePage, visibleTabname);
        }

        return mFollowersFragment;
    }

    public void setAdapterRef() {
        blockListFragment.refreshData();
    }
}
