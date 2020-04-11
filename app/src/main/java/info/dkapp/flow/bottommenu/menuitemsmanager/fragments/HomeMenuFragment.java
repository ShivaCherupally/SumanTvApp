package info.dkapp.flow.bottommenu.menuitemsmanager.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.bottommenu.menuitemsmanager.IMenuItemAdapter;
import info.dkapp.flow.bottommenu.menuitemsmanager.adapter.MenuItemAdapter;
import info.dkapp.flow.bottommenu.menuitemsmanager.modelclasses.MenuItems;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.UtilityNew;
import info.dkapp.flow.utils.internetchecker.InternetSpeedChecker;

import java.util.ArrayList;


public class HomeMenuFragment extends Fragment implements IFragment, IMenuItemAdapter {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static RecyclerView home_menuRecyclerView;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<MenuItems> celeb_List, member_List, manager_List, celebManager_List, switch_own_account_List;
    ApiInterface apiInterface;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout linearLayout;
    String loginType;
    static String Divider = "Divider";
    private static HomeMenuFragment instance = null;
    IMenuItemAdapter iMenuItemAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    public static HomeMenuFragment getInstance() {
        return instance;
    }


    public static HomeMenuFragment newInstance(String param1, String param2) {
        HomeMenuFragment fragment = new HomeMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_menu_fragment, container, false);
        iMenuItemAdapter = this;
        initializeActions(root);
        return root;
    }

    private void initializeActions(View root) {
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        linearLayout = root.findViewById(R.id.linearLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            if (UtilityNew.isNetworkAvailable(getActivity())) {
                loadData();
            } else {
                Snackbar snackBar = Snackbar.make(linearLayout, Constants.PLEASE_CHECK_INTERNET, Snackbar.LENGTH_SHORT);
            }
        });
        home_menuRecyclerView = root.findViewById(R.id.home_menuRecyclerView);
        home_menuRecyclerView.setHasFixedSize(true);
        home_menuRecyclerView.setNestedScrollingEnabled(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        home_menuRecyclerView.setLayoutManager(layoutManager);
        home_menuRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void loadData() {
        class asyncTaskLoadData extends AsyncTask<String, Void, String> {

            public asyncTaskLoadData() {
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {
                if (Common.isCelebAndManager(getActivity())) {
                    //Celeb and Manager Menu data
                    loginType = "CELEB_MANAGER";
                    celebManager_List = new ArrayList<MenuItems>();
                    for (int i = 0; i < MenuItemsDataCelebManager.celebMenuData.length; i++) {
                        celebManager_List.add(new MenuItems(MenuItemsDataCelebManager.celebMenuIconsData[i],
                                MenuItemsDataCelebManager.celebMenuData[i]
                        ));
                    }
                } else if (Common.isCelebStatus(getActivity())) {
                    //Celeb Menu data
                    loginType = "CELEB";
                    celeb_List = new ArrayList<MenuItems>();
                    for (int i = 0; i < MenuItemsData.celebMenuData.length; i++) {
                        celeb_List.add(new MenuItems(MenuItemsData.celebMenuIconsData[i], MenuItemsData.celebMenuData[i]
                        ));
                    }
                } else if (Common.isManagerStatus(getActivity())) {
                    //Manager data
                    loginType = "MANAGER";
                    manager_List = new ArrayList<MenuItems>();
                    for (int i = 0; i < MenuItemDataManager.celebMenuData.length; i++) {
                        manager_List.add(new MenuItems(MenuItemDataManager.celebMenuIconsData[i], MenuItemDataManager.celebMenuData[i]
                        ));
                    }
                } else {
                    //Member Menu Data
                    loginType = "MEMBER";
                    member_List = new ArrayList<MenuItems>();
                    for (int i = 0; i < MenuItemDataMember.celebMenuData.length; i++) {
                        member_List.add(new MenuItems(MenuItemDataMember.celebMenuIconsData[i], MenuItemDataMember.celebMenuData[i]
                        ));
                    }
                }
                //Switch Account Menu data
                    loginType = "SWITCH_ACCOUNT";
                    switch_own_account_List = new ArrayList<MenuItems>();
                    for (int i = 0; i < MenuItemDataManagerSwitchAccount.celebMenuData.length; i++) {
                        switch_own_account_List.add(new MenuItems(
                                MenuItemDataManagerSwitchAccount.celebMenuIconsData[i],
                                MenuItemDataManagerSwitchAccount.celebMenuData[i]
                        ));
                    }

                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                switch (loginType) {
                    case "MEMBER":
                        adapter = new MenuItemAdapter(activity(), member_List, iMenuItemAdapter);
                        home_menuRecyclerView.setAdapter(adapter);
                        break;
                    case "CELEB":
                        adapter = new MenuItemAdapter(activity(), celeb_List, iMenuItemAdapter);
                        home_menuRecyclerView.setAdapter(adapter);
                        break;
                    case "MANAGER":
                        adapter = new MenuItemAdapter(activity(), manager_List, iMenuItemAdapter);
                        home_menuRecyclerView.setAdapter(adapter);
                        break;
                    case "CELEB_MANAGER":
                        adapter = new MenuItemAdapter(activity(), celebManager_List, iMenuItemAdapter);
                        home_menuRecyclerView.setAdapter(adapter);
                        break;
                    case "SWITCH_ACCOUNT":
                        adapter = new MenuItemAdapter(activity(), switch_own_account_List, iMenuItemAdapter);
                        home_menuRecyclerView.setAdapter(adapter);
                        break;
                }
            }
        }
        new asyncTaskLoadData().execute();
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {

    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    @Override
    public void onClick(MenuItems menuItems, int position) {
        Intent intent;
        if (menuItems == null) return;
        switch (menuItems.getItemName()) {

            case "Credits":
                navigateToCreditRechargePage();
                break;
            case "Preferences":
                intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_TITLE, "Preferences");
                intent.putExtra(Constants.FRAGMENT_KEY, 9010);
                startActivity(intent);
                break;
            case "Celebrities":
                intent = new Intent(activity(), HelperActivity.class);
                    if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                        intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
                        intent.putExtra(Constants.FRAGMENT_KEY, 8003);// MyCelebritiesfragment
                        intent.putExtra("CELEB_ID", Common.isLoginId());
                        startActivity(intent);

                    }

                break;
            case "My Schedules":
                    if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                        intent = new Intent(activity(), HelperActivity.class);
                        intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
                        intent.putExtra(Constants.FRAGMENT_KEY, 8004);// MyScheduleDemoFragment
                        startActivity(intent);
                }
                break;
            case "Transactions":
               /* if (CommonAccessPermissionOfCeleb.cartPermissonAvailabilty(getContext(), true, false) && CommonAccessPermissionOfCeleb.orderPermissonAvailabilty(getContext(),
                        true, false)) {*/
                if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                    Common.getInstance().openTransactionScreen(activity(), "");
                }
                //  }
                break;

            case "Settings":
                    intent = new Intent(activity(), HelperActivity.class);
                    settingPageNavigation(intent, menuItems);

                break;
            case "Content":
                    if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                        intent = new Intent(activity(), HelperActivity.class);
                        intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
                        intent.putExtra(Constants.FRAGMENT_KEY, 8008);// MyContentFragment
                        startActivity(intent);
                    }

                break;
            case "Calendar":
                try {
                        if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                            Common.getInstance().openScheduleListFragment(activity(), SessionManager.userLogin.userId);
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Fans & Followers":
                    if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_MEMBER_OR_CELEB_PROFILE, "Fans & Followers");
                        intent = new Intent(activity(), HelperActivity.class);
                        intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
                        intent.putExtra("isFromCelebProfile", true);
                        intent.putExtra("CELEB_ID", Common.isLoginId());
                        intent.putExtra(Constants.FRAGMENT_KEY, 8010);// MyFanFollowersFragment
                        startActivity(intent);
                }
                break;
            case "Clients":
                if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                    intent = new Intent(activity(), HelperActivity.class);
                    intent.putExtra("TalentPermission", "My Talent");
                    intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
                    intent.putExtra(Constants.FRAGMENT_KEY, 8042);// MyClientFragment
                    startActivity(intent);
                }
                break;
            case "Invite a friend":
                    if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                        intent = new Intent(activity(), HelperActivity.class);
                        intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
                        intent.putExtra(Constants.FRAGMENT_KEY, 8012);// AppPromotionsFragment
                        startActivity(intent);

                }
                break;
            case "Auditions":
                    if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                        intent = new Intent(activity(), HelperActivity.class);
                        intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
                        intent.putExtra(Constants.FRAGMENT_KEY, 8048);// Audions
                        startActivity(intent);
                    }

                break;
            case "Reports":
                    if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                        //true
                        intent = new Intent(activity(), HelperActivity.class);
                        intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
                        intent.putExtra(Constants.FRAGMENT_KEY, 8013);// MyReportFragment
                        startActivity(intent);

                }
                break;
            case "About us":
                if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                    intent = new Intent(activity(), HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
                    intent.putExtra(Constants.FRAGMENT_KEY, 8021);// FAQFragmentHelp
                    startActivity(intent);
                }
                break;
            case "Contact":
                if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                    intent = new Intent(activity(), HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
                    intent.putExtra(Constants.FRAGMENT_KEY, 8022);// FAQFragment
                    startActivity(intent);
                }
                break;
            case "Help":
                if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                    intent = new Intent(activity(), HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
                    intent.putExtra(Constants.FRAGMENT_KEY, 8014);// FAQFragment
//                intent.putExtra(Constants.FRAGMENT_KEY, 8044);// OTPFragment
                    startActivity(intent);
                }
                break;
            case "Search Manager":

                    if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                        intent = new Intent(activity(), HelperActivity.class);
                        intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
//                    intent.putExtra(Constants.FRAGMENT_KEY, 8045);// ManagerOwnNotificationFragment
                        intent.putExtra(Constants.FRAGMENT_KEY, 8060);// ManagerOwnNotificationFragment
                        startActivity(intent);
                    }



                break;

            /*case "AudionsNew":
                if (CommonAccessPermissionOfCeleb.auditionPermissonAvailabilty(getContext(), true, false)) {
                    intent = new Intent(activity(), HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
                    intent.putExtra(Constants.FRAGMENT_KEY, 8048);// AudionsNew
                    startActivity(intent);
                }
                break;*/
            case "AudionsTabs":
                if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                    intent = new Intent(activity(), HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
                    intent.putExtra(Constants.FRAGMENT_KEY, 8053);// TalentFragment
//                intent.putExtra(Constants.FRAGMENT_KEY, 8058);// AddSubManagerFragment
                    startActivity(intent);
                }
                break;
            case "Back to Own Profile":
                if (InternetSpeedChecker.checkInternetAvaialable(activity())) {

                    Common.switchOwnProfile(getContext(), false);
                }
                break;
        }
    }


    private void settingPageNavigation(Intent intent, MenuItems menuItems) {
        intent.putExtra(Constants.FRAGMENT_TITLE, menuItems.getItemName());
        intent.putExtra(Constants.FRAGMENT_KEY, 8007);// SettingsPageFragment
        startActivity(intent);
    }

    public static class MenuItemsData {
        static String[] celebMenuData = {"Credits", Divider, "Preferences", "Celebrities", "Transactions", "Fans & Followers", "Invite a friend", Divider, "Auditions", Divider, "Settings", "Content", "Calendar", Divider,
                "Search Manager", Divider, "About us", "Help"
        };
        static Integer[] celebMenuIconsData = {R.drawable.ic_credits_new, R.drawable.chat_icon,
                R.drawable.ic_preferences,
                R.drawable.ic_celebrities_new, R.drawable.ic_transactions_new,
                R.drawable.ic_fans_followers_new, R.drawable.ic_app_promotions_new, R.drawable.chat_icon,
                R.drawable.ic_auditions_new, R.drawable.chat_icon, R.drawable.ic_settings_new, R.drawable.ic_content_new,
                R.drawable.ic_calender_new, R.drawable.chat_icon, R.drawable.ic_search_manager_new, R.drawable.chat_icon, R.drawable.ic_about_new, R.drawable.ic_help_new};
    }

    public static class MenuItemsDataCelebManager {
        static String[] celebMenuData = {"Credits", Divider, "Preferences", "Celebrities", "Transactions", "Fans & Followers", "Invite a friend",
                Divider, "Auditions", Divider, "Settings", "Content", "Calendar", Divider,
                "Search Manager", "Clients", Divider, "About us", "Help"
        };
        static Integer[] celebMenuIconsData = {R.drawable.ic_credits_new, R.drawable.chat_icon,
                R.drawable.ic_preferences,
                R.drawable.ic_celebrities_new, R.drawable.ic_transactions_new,
                R.drawable.ic_fans_followers_new, R.drawable.ic_app_promotions_new, R.drawable.chat_icon,
                R.drawable.ic_auditions_new, R.drawable.chat_icon, R.drawable.ic_settings_new, R.drawable.ic_content_new,
                R.drawable.ic_calender_new, R.drawable.chat_icon, R.drawable.ic_search_manager_new,
                R.drawable.ic_clients_new, R.drawable.chat_icon, R.drawable.ic_about_new, R.drawable.ic_help_new};
    }

    public static class MenuItemDataManager {
        static String[] celebMenuData = {"Credits", Divider, "Preferences", "Celebrities", "Transactions", "Invite a friend",
                Divider, "Auditions", Divider, "Settings", Divider,
                "Search Manager", "Clients", Divider, "About us", "Help"
        };
        static Integer[] celebMenuIconsData = {R.drawable.ic_credits_new, R.drawable.chat_icon,
                R.drawable.ic_preferences,
                R.drawable.ic_celebrities_new, R.drawable.ic_transactions_new, R.drawable.ic_app_promotions_new, R.drawable.chat_icon,
                R.drawable.ic_auditions_new, R.drawable.chat_icon, R.drawable.ic_settings_new, R.drawable.chat_icon, R.drawable.ic_search_manager_new,
                R.drawable.ic_clients_new, R.drawable.chat_icon, R.drawable.ic_about_new, R.drawable.ic_help_new};
    }


    public static class MenuItemDataManagerSwitchAccount {
        static String[] celebMenuData = {"Credits", Divider, "Preferences", "Celebrities", "Transactions", "Fans & Followers", "Invite a friend",
                Divider, "Auditions", Divider, "Settings", "Content", "Calendar", Divider,
                "Search Manager", "Back to Own Profile", Divider, "About us", "Help"
        };
        static Integer[] celebMenuIconsData = {R.drawable.ic_credits_new, R.drawable.chat_icon,
                R.drawable.ic_preferences,
                R.drawable.ic_celebrities_new, R.drawable.ic_transactions_new,
                R.drawable.ic_fans_followers_new, R.drawable.ic_app_promotions_new, R.drawable.chat_icon,
                R.drawable.ic_auditions_new, R.drawable.chat_icon, R.drawable.ic_settings_new, R.drawable.ic_content_new,
                R.drawable.ic_calender_new, R.drawable.chat_icon, R.drawable.ic_search_manager_new,
                R.drawable.ic_my_orders, R.drawable.chat_icon, R.drawable.ic_about_new, R.drawable.ic_help_new};
    }


    public static class MenuItemDataMember {
        static String[] celebMenuData = {"Credits", Divider, "Preferences", "Celebrities",
                "Transactions", "Invite a friend",
                Divider, "Auditions", Divider, "Settings", Divider, "About us", "Help"
        };
        static Integer[] celebMenuIconsData = {R.drawable.ic_credits_new, R.drawable.chat_icon,
                R.drawable.ic_preferences,
                R.drawable.ic_celebrities_new, R.drawable.ic_transactions_new, R.drawable.ic_app_promotions_new, R.drawable.chat_icon,
                R.drawable.ic_auditions_new, R.drawable.chat_icon, R.drawable.ic_settings_new, R.drawable.chat_icon, R.drawable.ic_about_new, R.drawable.ic_help_new};
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Common.isLiveStatusEmitRunning = false;
        if(isVisibleToUser){
            loadData();
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void navigateToCreditRechargePage() {

//        Intent intent = new Intent(getGlobalActivity(), CreditRechargeActvity.class);
//        intent.putExtra("className", "EditProfile");
//        startActivity(intent);

        Intent intent = new Intent(activity(), HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "Credits");
        intent.putExtra("className", "EditProfile");
        intent.putExtra(Constants.FRAGMENT_KEY, 8034);// CreditsRecharge
        startActivity(intent);
    }
}

