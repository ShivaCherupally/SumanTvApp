package info.dkapp.flow.bottommenu.menuitemsmanager.fragments;

import android.annotation.TargetApi;
import android.os.Build;
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
import info.dkapp.flow.celebflow.Notifications.FansOrFollowersNotificationsFragment;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;

public class NotificationsFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    String GetDataID;
    TabLayout tablayout_notification;
    private ViewPager viewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private String[] tabTitles;
    public boolean isCelebStatus, isSelf,isFromArchive = false;
    String serviceType = "";
    public static NotificationsFragment instance = null;

    public static NotificationsFragment newInstance() {
        NotificationsFragment fragment = new NotificationsFragment();
        return fragment;
    }

    public static NotificationsFragment getInstance() {
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.notifications_activity, null);
        initComponents(root);
        return root;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void initComponents(View root) {
        tablayout_notification = (TabLayout) root.findViewById(R.id.tablayout_notification);
        viewPager = (ViewPager) root.findViewById(R.id.view_pager_v);
        String OtherID = "";
        if(getArguments() != null){
            OtherID = getArguments().getString("OtherID","");
        }
        if(OtherID != null && !OtherID.isEmpty()){
            GetDataID = OtherID;
        } else {
            GetDataID = SessionManager.userLogin.userId;
        }
        isSelf = getActivity().getIntent().getExtras().getBoolean("isSelf", false);
        isFromArchive = getActivity().getIntent().getExtras().getBoolean("isFromArchive",false);

        tablayout_notification.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout_notification.setupWithViewPager(viewPager);
        mViewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), tablayout_notification.getTabCount());
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.setOffscreenPageLimit(mViewPagerAdapter.getCount()-1);
        tablayout_notification.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout_notification.setOnTabSelectedListener(this);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tablayout_notification.getTabAt(position).select();
            }
        });
        //
        serviceType = getActivity().getIntent().getExtras().getString(Constants.NOTIFICATION_SERVICE_TYPE,"");
        if (Common.isCelebAndManager(getActivity())) {
            if (serviceType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_SERVICE) || serviceType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_CREDIT)) {
                selectPage(2);
            } else if (serviceType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_FAN) || serviceType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_FOLLOW)) {
                selectPage(1);
            } else if (serviceType.equals(Constants.NOTIFICATION_TYPE_MANAGER)) {
                selectPage(3);
            }
        } else if (Common.isCelebStatus(getActivity())) {
            if (serviceType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_SERVICE) || serviceType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_CREDIT)) {
                selectPage(2);
            } else if (serviceType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_FAN) || serviceType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_FOLLOW)) {
                selectPage(1);
            } else if (serviceType.equals(Constants.NOTIFICATION_TYPE_MANAGER)) {
                selectPage(3);
            }
        } else if (Common.isManagerStatus(getActivity())) {
            if (serviceType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_SERVICE) || serviceType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_CREDIT)) {
                selectPage(1);
            } else if (serviceType.equals(Constants.NOTIFICATION_TYPE_MANAGER)) {
                selectPage(2);
            }
        } else if (serviceType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_SERVICE) || serviceType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_CREDIT)) {
            selectPage(1);
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

    public void refresh() {
        viewPager.setAdapter(mViewPagerAdapter);
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        int tabCount;

        public ViewPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            if (!isSelf) {
                tabTitles = new String[]{"All", "Fan / Follow", "Services", "Manager"};
            } else if (Common.isCelebAndManager(getActivity())) {
                tabTitles = new String[]{"All", "Fan / Follow", "Services", "Manager"};
            } else if (Common.isCelebStatus(getActivity())) {
                tabTitles = new String[]{"All", "Fan / Follow", "Services", "Manager"};
            } else if (Common.isManagerStatus(getActivity())) {
                tabTitles = new String[]{"All", "Services", "Manager"};
            } else {
                tabTitles = new String[]{"All", "Services"};
            }
            this.tabCount = tabCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }


        @Override
        public Fragment getItem(int position) {
            if (!isSelf) {
                return celebmanagerNotificationTypeTabs(position);
            } else if (Common.isCelebAndManager(getActivity())) {
                return celebmanagerNotificationTypeTabs(position);
            } else if (Common.isCelebStatus(getActivity())) {
                return celebmanagerNotificationTypeTabs(position);
            } else if (Common.isManagerStatus(getActivity())) {
                return managerNotificationTypeTabs(position);
            } else {
                return memberNotificationTypeTabs(position);
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }


    private Fragment memberNotificationTypeTabs(int position) {
        tablayout_notification.setTabMode(TabLayout.MODE_FIXED);
        switch (position) {
            case 0:
                FansOrFollowersNotificationsFragment mFansFragment = new FansOrFollowersNotificationsFragment(Constants.NotificationConstants.ALL, GetDataID, isSelf,isFromArchive);
                return mFansFragment;
            case 1:
                FansOrFollowersNotificationsFragment mFollowersFragment3 = new FansOrFollowersNotificationsFragment(Constants.NotificationConstants.SERVICE, GetDataID, isSelf,isFromArchive);
                return mFollowersFragment3;
            default:
                return null;
        }
    }

    private Fragment managerNotificationTypeTabs(int position) {
        tablayout_notification.setTabMode(TabLayout.MODE_FIXED);
        switch (position) {
            case 0:
                FansOrFollowersNotificationsFragment mFansFragment = new FansOrFollowersNotificationsFragment(Constants.NotificationConstants.ALL, GetDataID, isSelf,isFromArchive);
                return mFansFragment;
            case 1:
                FansOrFollowersNotificationsFragment mFollowersFragment3 = new FansOrFollowersNotificationsFragment(Constants.NotificationConstants.SERVICE, GetDataID, isSelf,isFromArchive);
                return mFollowersFragment3;
            case 2:
                FansOrFollowersNotificationsFragment mFollowersFragment4 = new FansOrFollowersNotificationsFragment(Constants.NotificationConstants.MANAGER, GetDataID, isSelf,isFromArchive);
                return mFollowersFragment4;
            default:
                return null;
        }
    }

    private Fragment celebmanagerNotificationTypeTabs(int position) {
        tablayout_notification.setTabMode(TabLayout.MODE_FIXED);
        switch (position) {
            case 0:
                FansOrFollowersNotificationsFragment mFansFragment = new FansOrFollowersNotificationsFragment(Constants.NotificationConstants.ALL, GetDataID, isSelf,isFromArchive);
                return mFansFragment;
            case 1:
                FansOrFollowersNotificationsFragment mFollowersFragment = new FansOrFollowersNotificationsFragment(Constants.NotificationConstants.FAN_FOLLOW, GetDataID, isSelf,isFromArchive);
                return mFollowersFragment;
            case 2:
                FansOrFollowersNotificationsFragment mFollowersFragment3 = new FansOrFollowersNotificationsFragment(Constants.NotificationConstants.SERVICE, GetDataID, isSelf,isFromArchive);
                return mFollowersFragment3;
            case 3:
                FansOrFollowersNotificationsFragment mFollowersFragment4 = new FansOrFollowersNotificationsFragment(Constants.NotificationConstants.MANAGER, GetDataID, isSelf,isFromArchive);
                return mFollowersFragment4;
            default:
                return null;
        }
    }

    public void selectAll(){
        try {
            if(viewPager == null || mViewPagerAdapter == null){
                return;
            }
            if (FansOrFollowersNotificationsFragment.getInstance() != null) {
                FansOrFollowersNotificationsFragment.getInstance().selectAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(){
        try {
            if(viewPager == null || mViewPagerAdapter == null){
                return;
            }
            if (FansOrFollowersNotificationsFragment.getInstance() != null) {
                FansOrFollowersNotificationsFragment.getInstance().deleteAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshData(){
        try {
            if(viewPager == null || mViewPagerAdapter == null){
                return;
            }
            if (FansOrFollowersNotificationsFragment.getInstance() != null) {
                FansOrFollowersNotificationsFragment.getInstance().refreshData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void selectPage(int pageIndex) {
        tablayout_notification.setScrollPosition(pageIndex, 0f, true);
        viewPager.setCurrentItem(pageIndex);
    }


}
