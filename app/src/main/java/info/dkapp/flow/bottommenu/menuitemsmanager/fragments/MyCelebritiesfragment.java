package info.dkapp.flow.bottommenu.menuitemsmanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.menu_list.ProfilesListCommon.ProfilesListFragment;
import info.dkapp.flow.userflow.Util.Common;

public class MyCelebritiesfragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private Context context;
    private String classType = null;
    boolean fromCelebProfilePage = false;
    private String visibleTabname = "";
    String celebId = Common.isLoginId();

    public static MyCelebritiesfragment newInstance(String param1, String param2,String celebId) {
        MyCelebritiesfragment fragment = new MyCelebritiesfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString("celebId",celebId);
        fragment.setArguments(args);
        return fragment;
    }

    public static MyCelebritiesfragment instance = null;
    public static MyCelebritiesfragment getInstance() {
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.mycelebrity_activity, null);

        if (getArguments() != null) {
            classType = getArguments().getString(ARG_PARAM1);
        }
        initializeViews(root);
        return root;
    }

    private void initializeViews(View root) {
        tabLayout = root.findViewById(R.id.tab_layout_fans);
        viewPager = root.findViewById(R.id.view_pager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setOnTabSelectedListener((TabLayout.OnTabSelectedListener) context);
        tabLayout.setupWithViewPager(viewPager);
        mViewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(mViewPagerAdapter);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setOnTabSelectedListener((TabLayout.OnTabSelectedListener) context);

        if (classType != null) {
            if (classType.equals("isFan")) {
                selectPage(0);
            } else if (classType.equals("isFollower")) {
                TabLayout.Tab tab = tabLayout.getTabAt(2);
                selectPage(1);
            }
        }

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }
        });
        try {
            if (getActivity().getIntent().getExtras().getString("FAN_OR_FOLLOW") != null) {
                fromCelebProfilePage = true;

                if (getActivity().getIntent().getExtras().getString("FAN_OR_FOLLOW").equals("FAN")) {
                    visibleTabname = Constants.ProfilesConstants.FAN_TAB;
                    viewPager.setCurrentItem(0);
                } else if (getActivity().getIntent().getExtras().getString("FAN_OR_FOLLOW").equals("FOLLOW")) {
                    visibleTabname = Constants.ProfilesConstants.FOLLOWING_TAB;
                    viewPager.setCurrentItem(1);
                }
            } else {
                fromCelebProfilePage = false;
            }
            if (getActivity().getIntent().getExtras().getString("celebId") != null) {
                celebId = getActivity().getIntent().getExtras().getString("celebId");
            }else {
                celebId = Common.isLoginId();
            }

        } catch (Exception e) {

        }
    }

    void selectPage(int pageIndex) {
        tabLayout.setScrollPosition(pageIndex, 0f, true);
        viewPager.setCurrentItem(pageIndex);
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
        private String[] tabTitles = new String[]{"Fanned", "Following"};

        public ViewPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        //Overriding method getItem
        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    return redirectFragment(Common.isLoginId(), Constants.ProfilesConstants.FAN_TAB);
                case 1:
                    return redirectFragment(Common.isLoginId(), Constants.ProfilesConstants.FOLLOWING_TAB);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }


    }

    private Fragment redirectFragment(String profileId, String tabName) {
        ProfilesListFragment mFollowersFragment = new ProfilesListFragment(profileId, Common.isCelebStatus(getContext()),tabName, true, fromCelebProfilePage, visibleTabname);
        return mFollowersFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            instance = this;
        }
    }

}
