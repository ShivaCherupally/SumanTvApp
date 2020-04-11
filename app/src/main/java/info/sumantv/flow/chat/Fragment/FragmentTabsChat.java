package info.sumantv.flow.chat.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.chat.models.ChatDataConvertModel;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Utility;

public class FragmentTabsChat extends Fragment implements IFragment {
    private CoordinatorLayout coordinator_layout;
    ChatDataConvertModel chatDataConvertModelParent;
    String userName = "", userEmail = "", userMemberId = "";
    public static TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    int addPosition;
    boolean isFromAdd = false;

    private static FragmentTabsChat instance = null;

    public static FragmentTabsChat getInstance() {
        return instance;
    }

    public FragmentTabsChat() {
        // Required empty public constructor
    }

    public static FragmentTabsChat newInstance(ChatDataConvertModel chatDataConvertModel, int addPosition, boolean isFromAdd) {
        FragmentTabsChat fragment = new FragmentTabsChat();
        Bundle args = new Bundle();
        args.putParcelable("chatDataConvertModel", chatDataConvertModel);
        args.putInt("addPosition", addPosition);
        args.putBoolean("isFromAdd", isFromAdd);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.getInstance().setFragmentTabsChat(this);
        if (getArguments() != null) {
            try {
                addPosition = getArguments().getInt("addPosition");
                isFromAdd = getArguments().getBoolean("isFromAdd");
                chatDataConvertModelParent = getArguments().getParcelable("chatDataConvertModel");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tabs_chat, container, false);

        userName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userName, "");
        userEmail = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, ""), "");
        userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
        if (userMemberId.isEmpty()) {
            activity().finish();
        }
        Common.getInstance().setFanUnFanChatDataConvertModelList(null);
        coordinator_layout = root.findViewById(R.id.coordinator_layout);
        tabLayout = root.findViewById(R.id.tab_layout);
        viewPager = root.findViewById(R.id.view_pager);
        //
        if (isFromAdd) {

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
 setUpTabAdapter();
                }
            }, 100);
        }
        if (chatDataConvertModelParent != null && (chatDataConvertModelParent.chatCount == null || chatDataConvertModelParent.chatCount == 1)) {
            Common.getInstance().openSingleChatActivity(activity(), activity(), chatDataConvertModelParent);
        }
        return root;
    }

    private void setUpTabAdapter() {

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        try {
            mViewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(mViewPagerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                try {
                    Utility.hideKeyboard(activity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (Common.getInstance().getBottomMenuActivity() != null) {
                    Common.getInstance().getBottomMenuActivity().collapseSearchView();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //
        /*FragmentCallsList fragmentCallsList = Common.getInstance().getFragmentCallsList();
        if (fragmentCallsList != null && fragmentCallsList.getRecentCallsModelArrayList().size() <= 0) {
            fragmentCallsList.getCallsList();
        }*/
        instance = this;
        if (addPosition == 0) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(1);
        }
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        int tabCount;
        private String[] tabTitles = new String[]{"Chats", "Calls"};
        private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

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
                    return FragmentChatList.newInstance(chatDataConvertModelParent, null);
                case 1:
                    return FragmentCallsList.newInstance(chatDataConvertModelParent, null);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }

    public FragmentCallsList getCallFragDirect() {
        try {
            return (FragmentCallsList) mViewPagerAdapter.getRegisteredFragment(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        Utility.showSnackBar(activity(), coordinator_layout, snackBarText, type);
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Common.getInstance().setFragmentTabsChat(null);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mViewPagerAdapter == null) {
                isFromAdd = getArguments().getBoolean("isFromAdd");
                if (!isFromAdd) {
                    setUpTabAdapter();
                }
            }
        }
    }

    public void updateMissedCallCount(Integer missedCallCount) {
        if (missedCallCount == 0) {
            FragmentTabsChat.tabLayout.getTabAt(1).setText("Calls");
        } else {
            FragmentTabsChat.tabLayout.getTabAt(1).setText("Calls " + " " + "(" + missedCallCount + ")");
        }

    }
}
