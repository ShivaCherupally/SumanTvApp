package info.sumantv.flow.bottommenu.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.crashlytics.android.Crashlytics;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.appmanagers.feed.models.Feed;
import info.sumantv.flow.bottommenu.customviews.viewpagers.SwipeDisableViewPager;
import info.sumantv.flow.bottommenu.interfaces.activity.IActivity;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.MyContentFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.modelclasses.Banner;
import info.sumantv.flow.bottommenu.preferencemanager.PrefrencesExpentableListviewFragment;
import info.sumantv.flow.bottommenu.viewpager.HomeViewPagerAdapter;
import info.sumantv.flow.databaseutil.appstart.AppController;
import info.sumantv.flow.celebflow.Fragments.MediaDetailsFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.celebflow.constants.Permission;
import info.sumantv.flow.chat.socket.ChatSocket;
import info.sumantv.flow.ipoll.fragments.dummy.DummyFragment;
import info.sumantv.flow.ipoll.fragments.feeds.FeedsFragment;
import info.sumantv.flow.ipoll.fragments.feeds.details.FeedDetailsFragment;
import info.sumantv.flow.ipoll.fragments.feeds.likes.LikesFragment;
import info.sumantv.flow.ipoll.fragments.options.OptionsFragment;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiRequestModelJSON;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.userflow.Util.FrescoFaceDetector;
import info.sumantv.flow.userflow.Util.GlideFaceDetector;
import info.sumantv.flow.utils.Logger;
import info.sumantv.flow.utils.SocketForAppUtill;
import info.sumantv.flow.utils.Utility;
import info.sumantv.flow.utils.UtilityNew;
import info.sumantv.flow.utils.internetchecker.ConnectivityReceiver;
import info.sumantv.flow.utils.internetchecker.NetworkUtil;
import info.sumantv.flow.imagepicker.model.Config;
import info.sumantv.flow.imagepicker.model.Image;
import info.sumantv.flow.imagepicker.ui.imagepicker.ImagePicker;
import io.fabric.sdk.android.Fabric;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

public class BottomMenuActivity extends AppCompatActivity implements IActivity, View.OnClickListener, IApiListener {
    private FrameLayout fragmentLayoutBottomSheet;
    private FrameLayout frameLayoutHelper;
    private CoordinatorLayout coordinatorLayout;
    private BottomSheetBehavior bottomSheetBehavior;
    LinearLayout llClose;
    SearchView mSearch;
    ImageView ibListGridView, badge_curve;
    private RelativeLayout callMinimizeLayout;
    public RelativeLayout rrBottomNavigation;
    Boolean isManagerProfileFill = false;
    FrameLayout frameLayoutBottom;
    ProgressBar progressBar;
    private Dialog managerProfileFillDailog;
    ApiInterface apiInterface;
    IApiListener iApiListener;
    //Header
    private EditText eSearchBar;
    private CircleImageView imgUser;
    private ImageView iNotifications, swicthAccountIconFeed, swicthAccountIconOther;
    ImageButton ibRecentChat;
    SwipeDisableViewPager swipeDisableViewPager;
    private TextView tTitle, tvChatCount, tvcallTime, tvcallCelebName;
    private LinearLayout tLayout, llCurveBottom;
    private RelativeLayout trLayout;
    public String redirectMethod = null;
    private boolean doubleBackToExitPressedOnce;
    public static boolean isCurvedBottomBar = true;
    public MenuItem menuSave;
    public Toolbar toolbar;
    public Button btPreferenceSave;
    private ImageView ivBack;
    private Boolean iNotificationsClicked = false, isGrid = false;
    int experience;
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;
    private Handler timeHandler = new Handler();
    IntentFilter intentFilter;
    ConnectivityReceiver receiver;
    int callCountTimer = 0, callCountTimer_min = 0;
    SearchView searchView;
    private View notificationBadge;
    ImageView badgeCounttxt, iVAddBack, iVLogoText;
    RelativeLayout rlHome, rlSearch, rlCk, rlChat, rlMenu;
    ImageView imageViewHome, imageViewSearch, imageViewCK, imageViewChat, imageViewMenu, iVStartChat;
    int paddingToBottom = 0;
    RelativeLayout.LayoutParams params_viewPager;
    String ckMessage = "Coming Soon.... Entertainment unlimited!!!";
    public boolean isCreateStore = false;
    private ArrayList<Image> images = new ArrayList<>();
    public static BottomMenuActivity instance;


    public static BottomMenuActivity getInstance() {
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.screenShotRestrict(BottomMenuActivity.this);
        setTheme(R.style.AppTheme_Bottom);

        if (isCurvedBottomBar) {
//            paddingToBottom = getResources().getDimensionPixelSize(R.dimen._48sdp);
            paddingToBottom = 0;
        } else {
            paddingToBottom = getResources().getDimensionPixelSize(R.dimen._40sdp);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(activity(), R.color.status_bar));// set status background whit
        iApiListener = this;
        isGrid = false;
        internetConnectivityChecker();
        GlideFaceDetector.initialize(getApplicationContext());
        FrescoFaceDetector.initialize(getApplicationContext());
        ChatSocket.getInstance().onSocketListeners();
        ChatSocket.getInstance().emitOnline(activity());


        if (!ApiClient.ENVIRONMENT.equals("test")) {
            Fabric.with(this, new Crashlytics());
        }
        setContentView(R.layout.activity_bottom_menu);
        instance = this;
        setUp();


        if (AppController.getInstance().getRegisteredBottomMenuActivityCount() == 1 && !Common.isSwicthed() && Common.getInstance().isLoginInApp() && Common.isCelebStatus(Utility.getContext()) && !Common.getInstance().isOnlineStatus()) {
            UtilityNew.showSnackBarLong(activity(), coordinatorLayout, Constants.YOU_ARE_OFFLINE, 2);
        }
        //
        checkPreferences();
        //
        if (Build.VERSION.SDK_INT < 23) {
        } else {
            if (checkAndRequestPermissions()) {
            }
        }
        if (Common.chatDataConvertModelFromNotification != null && Common.chatDataConvertModelFromNotification._id != null && !Common.chatDataConvertModelFromNotification._id.isEmpty()) {
            Common.getInstance().openChatTabsActivity(this, Common.chatDataConvertModelFromNotification);
            //
            Common.chatDataConvertModelFromNotification = null;//put this line at last
        }
        if (Common.bundleFromNotification != null && Common.bundleFromNotification.size() > 0) {
            Common.getInstance().notificationRedirection(activity(), Common.bundleFromNotification);
            //
            Common.bundleFromNotification = null;//put this line at last
        }
        Common.getInstance().getLocation(this, this.getClass().getSimpleName(), true);

        if (Common.sharedFeedID != null && !Common.sharedFeedID.isEmpty()) {
            Common.getInstance().getSingleFeedData(Common.sharedFeedID);
            //
            Common.sharedFeedID = "";//clear the value here
        } else if (Common.sharedProfileID != null && !Common.sharedProfileID.isEmpty()) {
            Intent intent = new Intent(this, HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_TITLE, "Audition Profile");
            intent.putExtra(Constants.FRAGMENT_KEY, 8049);// Audition InnerProfile
            intent.putExtra("id", Common.sharedProfileID);
            startActivity(intent);
            //
            Common.sharedProfileID = "";//clear the value here
        } else if (Common.sharedAuditionID != null && !Common.sharedAuditionID.isEmpty()) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ApiResponseModel> call = apiInterface.GET(ApiClient.SHARE_AUDITION + Common.sharedAuditionID + "/" + Common.sharedRoleID + "/" + SessionManager.userLogin.userId);
            Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.SHARE_AUDITION, false, iApiListener, false));
            //
            Common.sharedAuditionID = "";//clear the value here
            Common.sharedRoleID = "";//clear the value here
        }
//        throw new RuntimeException("This is a crash");
    }

    public void collapseSearchView() {
        try {
            if (!searchView.isIconified()) {
                searchView.onActionViewCollapsed();
                setChatToolbar(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPreferences() {
        if (!SessionManager.getInstance().getKeyValue(SessionManager.KEY_IS_PREFERENCES_SELECTED, false)) {
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ApiResponseModel> call = apiInterface.GET(ApiClient.getMemberPreferancesCount + SessionManager.userLogin.userId);
            Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.getMemberPreferancesCount, false, iApiListener, false));
        }
    }

    /*private void checkWifiConnection() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
    }*/


//    private void addAutoStartup() {
//        try {
//            Intent intent = new Intent();
//            String manufacturer = android.os.Build.MANUFACTURER;
//            if ("xiaomi".equalsIgnoreCase(manufacturer)) {
//                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
//            } else if ("oppo".equalsIgnoreCase(manufacturer)) {
//                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
//            } else if ("vivo".equalsIgnoreCase(manufacturer)) {
//                intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
//            } else if ("Letv".equalsIgnoreCase(manufacturer)) {
//                intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
//            } else if ("Honor".equalsIgnoreCase(manufacturer)) {
//                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
//            }
//            List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//            if (list.size() > 0) {
//                startActivityForResult(intent, 1234);
//                //startActivity(intent);
//            }
//        } catch (Exception e) {
//            Log.e("exc", String.valueOf(e));
//        }
//    }

    private void setUp() {

        toolbar = findViewById(R.id.toolbar);

        fragmentLayoutBottomSheet = findViewById(R.id.fragmentLayoutBottomSheet);
        swipeDisableViewPager = findViewById(R.id.swipeDisableViewPager);
        rlHome = findViewById(R.id.rlHome);
        rlSearch = findViewById(R.id.rlSearch);
        rlCk = findViewById(R.id.rlCk);
        rlChat = findViewById(R.id.rlChat);
        rlMenu = findViewById(R.id.rlMenu);
        imageViewHome = findViewById(R.id.imageViewHome);
        imageViewSearch = findViewById(R.id.imageViewSearch);
        imageViewCK = findViewById(R.id.imageViewCK);
        imageViewChat = findViewById(R.id.imageViewChat);
        imageViewMenu = findViewById(R.id.imageViewMenu);
        iVStartChat = findViewById(R.id.iVStartChat);

        rrBottomNavigation = findViewById(R.id.rrBottomNavigation);
        btPreferenceSave = (Button) findViewById(R.id.btPreferenceSave);
        btPreferenceSave.setVisibility(View.GONE);
        coordinatorLayout = findViewById(R.id.coordinatorLayout1);
        frameLayoutHelper = findViewById(R.id.frameLayoutHelper);
        ibListGridView = findViewById(R.id.ibListGridView);
        frameLayoutBottom = findViewById(R.id.frameLayoutBottom);
        progressBar = findViewById(R.id.progressBar);
        llClose = findViewById(R.id.llClose);
        mSearch = findViewById(R.id.mSearch);


        mSearch.setMaxWidth(Integer.MAX_VALUE);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //Headers
        eSearchBar = findViewById(R.id.eSearchBar);
        iVAddBack = findViewById(R.id.iVAddBack);
        iVLogoText = findViewById(R.id.iVLogoText);
        imgUser = findViewById(R.id.imgUser);
        iNotifications = findViewById(R.id.iNotifications);
        ibRecentChat = findViewById(R.id.ibRecentChat);
        tTitle = findViewById(R.id.tTitle);
        tvChatCount = findViewById(R.id.tvChatCount);
        badgeCounttxt = findViewById(R.id.badgeCounttxt);
        badge_curve = findViewById(R.id.badge_curve);
        llCurveBottom = findViewById(R.id.llCurveBottom);
        tLayout = findViewById(R.id.tLayout);
        trLayout = findViewById(R.id.trLayout);
        imgUser.setVisibility(View.GONE);
        callMinimizeLayout = findViewById(R.id.callMinimizeLayout);
        tvcallCelebName = findViewById(R.id.tvcallCelebName);
        tvcallTime = findViewById(R.id.tvcallTime);
        ivBack = findViewById(R.id.ivBack);
        searchView = findViewById(R.id.searchView);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setVisibility(View.GONE);
        //
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setChatToolbar(true);
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChatToolbar(false);
            }
        });
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setChatToolbar(false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                setChatToolbar(false);
                try {
                    if (Common.getInstance().getFragmentChatList() != null && Common.getInstance().getFragmentChatList().getFragVisibility()) {
                        Common.getInstance().getFragmentChatList().doSearch(query);
                    } else if (Common.getInstance().getFragmentCallsList() != null
                            && Common.getInstance().getFragmentCallsList().getFragVisibility()) {
                        Common.getInstance().getFragmentCallsList().doSearch(query);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });


        //Check Uday Code
        /*try {
            Intent intent_ = getIntent();
            String callTime = intent_.getStringExtra("time");
            tvcallTime.setText(callTime);
            extras = getIntent().getExtras();
            infoForServiceObj = new JSONObject(extras.getString(Constants.INFO_FOR_SERVICE));

            String callerName = infoForServiceObj.getString(Constants.RECEIVER_NAME);
            tvcallCelebName.setText(callerName + " is on Call");

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isOnCall) {
            String currentString = tvcallTime.getText().toString();
            String[] separated = currentString.split(":");
            min = Integer.parseInt(separated[0]);
            sec = Integer.parseInt(separated[1]);
            callCountTimer = sec;
            callCountTimer_min = min;
            timeHandler.removeCallbacks(updateTime);
            timeHandler.postDelayed(updateTime, 1000);
            callMinimizeLayout.setVisibility(View.VISIBLE);
        } else {
            callMinimizeLayout.setVisibility(View.GONE);
        }*/

        callMinimizeLayout.setVisibility(View.GONE);

        callMinimizeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swicthAccountIconFeed = findViewById(R.id.swicthAccountIconFeed);
        swicthAccountIconOther = findViewById(R.id.swicthAccountIconOther);
        //
        tvChatCount.setVisibility(View.GONE);
        badgeCounttxt.setVisibility(View.GONE);

        swicthAccountIconFeed.setVisibility(View.GONE);
        swicthAccountIconOther.setVisibility(View.GONE);

        fragmentLayoutBottomSheet.setOnClickListener(view -> {

        });

        rrBottomNavigation.setOnClickListener(v -> {

        });


        ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

        btPreferenceSave.setOnClickListener(v -> {
            controlMenuItems();
        });

        String pic = null;
        if (SessionManager.userLogin.profilePic != null
                && !SessionManager.userLogin.profilePic.isEmpty()) {
            pic = SessionManager.userLogin.profilePic;
            Glide.with(getApplicationContext())
                    .load(ApiClient.BASE_URL + pic)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.celebicon_iogo)
                    .into(imgUser);
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.appiconandroid).into(imgUser);
        }

        bottomSheetBehavior = BottomSheetBehavior.from(fragmentLayoutBottomSheet);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Utility.hideKeyboard(activity());
                if (newState == BottomSheetBehavior.STATE_HIDDEN || newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    try {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottom, DummyFragment.newInstance(null, null)).commit();
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        frameLayoutBottom.setLayoutParams(layoutParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (FeedsFragment.getInstance() != null && getCurrentViewPagerPosition() == 0) {
                        FeedsFragment.getInstance().resumeVideoPlayer();
                    }
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    if (FeedsFragment.getInstance() != null) {
                        FeedsFragment.getInstance().stopVideoPlayer(false);
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        badge_curve.setVisibility(View.GONE);
        llClose.setOnClickListener(click -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        });

        swipeDisableViewPager.setPagingEnabled(false);

        swipeDisableViewPager.setOffscreenPageLimit(7);
        swipeDisableViewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRST_EDIT_PROFILE_NOT_COMPLETE, "TRUE");


        //HeaderClickEvent
        eSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_KEY, 8001);// search
                startActivity(intent);
            }
        });
        ibRecentChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //CommonApis.updateOnlineStatus(activity(), "offline");
            }
        });

        iNotifications.setOnClickListener(view -> {
            if (!iNotificationsClicked) {
                iNotificationsClicked = true;
                new Handler().postDelayed(() -> iNotificationsClicked = false, 1500);//1.5 seconds disabling click event

                if (!Common.checkInternetConnection(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), Constants.SEEMS_LIKE_NO_INTRNET, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Intent intent = new Intent(BottomMenuActivity.this, HelperActivity.class);
                    intent.putExtra("title", "Notifications");
                    intent.putExtra("isSelf", true);
                    intent.putExtra("isFromArchive", false);
                    intent.putExtra(Constants.FRAGMENT_KEY, 8019);// NotificationsFragment
                    startActivity(intent);
                }
            }
        });
        rlHome.setOnClickListener(this);
        rlSearch.setOnClickListener(this);
        rlCk.setOnClickListener(this);
        rlChat.setOnClickListener(this);
        rlMenu.setOnClickListener(this);
        swicthAccountIconFeed.setOnClickListener(this);
        swicthAccountIconOther.setOnClickListener(this);
        imageViewCK.setOnClickListener(this);

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BottomMenuActivity.this, HelperActivity.class);
                intent.putExtra("title", "My Profile");
                intent.putExtra(Constants.FRAGMENT_KEY, 8002);// EditProfileFragment
                startActivity(intent);
            }
        });


        mSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tTitle.setVisibility(View.VISIBLE);
                return false;
            }
        });
        mSearch.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tTitle.setVisibility(View.GONE);
            }
        });

        mSearch.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                tTitle.setVisibility(View.GONE);
                //  celebProfileAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                tTitle.setVisibility(View.GONE);
                /*if(query.length() > 0 && query.trim().isEmpty()){
                    mSearch.setQuery("",false);
                } else if(!query.trim().isEmpty()) {
                }*/
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                for (Fragment fragment : fragmentList) {
                   /* if (fragment instanceof CelebrityBaseFragment) {
                        CelebrityBaseFragment categoryCelebsFragment = (CelebrityBaseFragment) fragment;
                        categoryCelebsFragment.sendSearchItem(query);
                    }*/
                }
                return false;
            }
        });
        ibListGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                for (Fragment fragment : fragmentList) {
                   /* if (fragment instanceof CelebrityBaseFragment) {
                        CelebrityBaseFragment categoryCelebsFragment = (CelebrityBaseFragment) fragment;
                        categoryCelebsFragment.changeGridView(null);
                        break;
                    }*/
                }
            }
        });

        try {
            if (getIntent().getExtras().get("FROM_USER_PROFILE") != null) {
                tLayout.setVisibility(View.GONE);
                ivBack.setVisibility(View.VISIBLE);
                swipeDisableViewPager.setPadding(0, 0, 0, 0);
                swipeDisableViewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager(),
                        Common.getInstance().IsNullReturnValue(getIntent().getExtras().get("CELEB_ID").toString(), ""),
                        getApplicationContext()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        swipeDisableViewPager.setOffscreenPageLimit(5);

        swipeDisableViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        //imageZoomSetUp();

        checkBannerStatus();

        if (Common.isManagerStatus(activity())) {
            // For New designs Comment by Uday
            getManagerProfile();
        }
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRST_TIME_SPLASH_LOAD, true);
        params_viewPager = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        if (isCurvedBottomBar) {
            llCurveBottom.setVisibility(View.VISIBLE);

        } else {
            params_viewPager.setMargins(0, 0, 0, paddingToBottom);
            swipeDisableViewPager.setLayoutParams(params_viewPager);
            llCurveBottom.setVisibility(View.GONE);
        }
        if (!SessionManager.getInstance().checkKeyExist(SessionManager.KEY_AUTO_PLAY_VIDEOS)) {
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_AUTO_PLAY_VIDEOS, true);
        }
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_MUTE_ENABLED, true);
        iVStartChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //it open select a celebtity fragment
                Intent intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_KEY, 8101);
                intent.putExtra(Constants.FRAGMENT_TITLE, "Select a Celebrity");
                intent.putExtra("params", "");
                startActivityForResult(intent, Utility.generateRequestCodes().get("CHAT_RECENT_LIST"));
            }
        });
        try {
            if (getIntent().getExtras().get("isForAdd") != null) {
                if (getIntent().getExtras().getBoolean("isForAdd")) {
                    iVAddBack.setVisibility(View.VISIBLE);
                    iVLogoText.setVisibility(View.GONE);
                    iVStartChat.setVisibility(View.VISIBLE);
                    iNotifications.setVisibility(View.GONE);
                    searchView.setVisibility(View.VISIBLE);
                    badgeCounttxt.setVisibility(View.GONE);


                    llCurveBottom.setVisibility(View.GONE);
                    switch (getIntent().getExtras().getString("addType")) {
                        case "video":
                            swipeDisableViewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager(),
                                    getApplicationContext(), 1, true));
                            swipeDisableViewPager.setCurrentItem(3);
                            break;
                        case "audio":
                            swipeDisableViewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager(),
                                    getApplicationContext(), 1, true));
                            swipeDisableViewPager.setCurrentItem(3);
                            break;
                        case "chat":
                            swipeDisableViewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager(),
                                    getApplicationContext(), 0, true));
                            swipeDisableViewPager.setCurrentItem(3);
                            break;
                    }
                }

            } else {
                iVAddBack.setVisibility(View.GONE);
                iVLogoText.setVisibility(View.VISIBLE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        iVAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public int getCurrentViewPagerPosition() {
        if (swipeDisableViewPager.getAdapter() != null) {
            return swipeDisableViewPager.getCurrentItem();
        }
        return -1;
    }

    public void updateChatCount(Integer chatCount, Integer messagesCount) {
        try {
            if (chatCount > 0) {
                badge_curve.setVisibility(View.VISIBLE);
            } else {
                badge_curve.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateNotificationCount(Integer notificationCount) {
        try {
            if (notificationCount > 0) {
                if (getIntent().getExtras() != null) {
                    if (getIntent().getExtras().getBoolean("isForAdd")) {
                        badgeCounttxt.setVisibility(View.GONE);
                    }
                } else {
                    badgeCounttxt.setVisibility(View.VISIBLE);
                }
                /*if (String.valueOf(notificationCount).length() >= 4) {
                    badgeCounttxt.setText("1k+");
                } else {
                    badgeCounttxt.setText(String.valueOf(notificationCount));
                }*/

            } else {
                //  badgeCounttxt.setText("");
                badgeCounttxt.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getManagerProfile() {

        //     ProgressDialog progressDialog = Utility.generateProgressDialog(activity());

        if (!Utility.isNetworkAvailable(activity())) {
            showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
            return;
        }

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getManagerProfile(ApiClient.GET_MANAGER_PROFILE +
                SessionManager.userLogin.userId + "/");
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_MANAGER_PROFILE, false, iApiListener, false));


//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        /*ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ManagerAdditionalProfileNew> call = apiInterface.getManagerProfile(ApiClient.GET_MANAGER_PROFILE +
                SessionManager.userLogin.userId + "/");
        call.enqueue(new retrofit2.Callback<ManagerAdditionalProfileNew>() {
            @Override
            public void onResponse(Call<ManagerAdditionalProfileNew> call, @NonNull retrofit2.Response<ManagerAdditionalProfileNew> response) {

                try {
                    ManagerAdditionalProfileNew managerAdditionalProfileNew = response.body();

                    if (managerAdditionalProfileNew != null) {
                        appendData(managerAdditionalProfileNew);

                    } else {
                        showSnackBar("No data", 2);
                    }
                } catch (Exception e) {

                    showSnackBar(Constants.SOMETHING_WENT_WRONG, 2);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ManagerAdditionalProfileNew> call, Throwable t) {

                showSnackBar(Constants.SOMETHING_WENT_WRONG_SERVER, 2);
            }
        });*/
    }


//    private void imageZoomSetUp() {
//        imageZoomHelper = new ImageZoomHelper(activity());
//        onZoomListener = new ImageZoomHelper.OnZoomListener() {
//            @Override
//            public void onImageZoomStarted(final View view) {
//
//            }
//
//            @Override
//            public void onImageZoomEnded(View view) {
//
//            }
//        };
//    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_DRAGGING || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_SETTLING) {
                return super.dispatchTouchEvent(ev);
            } else {
                Common.hideKeyboard(BottomMenuActivity.this);
                return /*imageZoomHelper.onDispatchTouchEvent(ev) ||*/ super.dispatchTouchEvent(ev);
            }

//            else if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_DRAGGING || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_SETTLING)


        } catch (Exception e) {
            return false;
        }
        //return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResumeBottom", "truee");
        Common.getInstance().setBottomMenuActivity(this);
        try {
            SocketForAppUtill.getInstance().noticationCountEmit();
            ChatSocket.getInstance().numberOfUnSeenMessageEmit();
            SocketForAppUtill.getInstance().missedCallCountEmit();

            if (intentFilter != null) {
                registerReceiver(receiver, intentFilter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        Log.d("onPauseBottom", "truee");


//        internetConnectivityCheckerOnPauseCall();
//        if (Common.checkInternetConnection(AppController.getInstance().getContext())) {
//            Toast.makeText(AppController.getInstance().getApplicationContext(), "Internet connection available!!", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(AppController.getInstance().getApplicationContext(), "No active internet connection!!",
//                    Toast.LENGTH_SHORT).show();
//        }
//        unregisterReceiver(receiver);
    }


    private boolean checkAndRequestPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int storageWritePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int storagePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int microphonePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        int permissionPhoneState = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
        int CHANGE_WIFI_STATE = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CHANGE_WIFI_STATE);
        int SYSTEM_ALERT_WINDOW = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SYSTEM_ALERT_WINDOW);
        /*int ACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int ACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);*/
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (storageWritePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (microphonePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (permissionPhoneState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (CHANGE_WIFI_STATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CHANGE_WIFI_STATE);
        }
        if (SYSTEM_ALERT_WINDOW != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SYSTEM_ALERT_WINDOW);
        }
        /*if (ACCESS_FINE_LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ACCESS_COARSE_LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }*/
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(BottomMenuActivity.this, listPermissionsNeeded.toArray(new
                    String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }
        return true;
    }

    public void changeListGridImage(Boolean isGrid) {
        if (ibListGridView != null) {
            this.isGrid = isGrid;
            if (isGrid) {
                ibListGridView.setImageResource(R.drawable.ic_list_view);
            } else {
                ibListGridView.setImageResource(R.drawable.ic_grid_view);
            }
        }
    }

    public Boolean getIsGrid() {
        return isGrid;
    }


    private void setChatToolbar(Boolean show) {
        if (show) {
            btPreferenceSave.setVisibility(View.GONE);
            ibListGridView.setVisibility(View.GONE);
            mSearch.setVisibility(View.GONE);
            trLayout.setVisibility(View.VISIBLE);
            tTitle.setVisibility(View.VISIBLE);
            tTitle.setText("Konect");
            tLayout.setVisibility(View.GONE);
            iVStartChat.setVisibility(View.VISIBLE);
        } else {
            btPreferenceSave.setVisibility(View.GONE);
            ibListGridView.setVisibility(View.GONE);
            mSearch.setVisibility(View.GONE);
            trLayout.setVisibility(View.VISIBLE);
            tTitle.setVisibility(View.GONE);
            tTitle.setText("Konect");
            tLayout.setVisibility(View.GONE);
            iVStartChat.setVisibility(View.GONE);
        }
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.preferences_menu, menu);
//        menuSave = menu.findItem(R.id.menu_save);
//        if (getSupportFragmentManager().getFragments().get(3) instanceof PreferenceFragment) {
//            menuSave.setVisible(true);
//        }
//
//        return true;
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if (getSupportFragmentManager().getFragments().get(3) instanceof PreferenceFragment) {
//            controlMenuItems();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
    private void controlMenuItems() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment instanceof PrefrencesExpentableListviewFragment) {
                PrefrencesExpentableListviewFragment editFeedFragment = (PrefrencesExpentableListviewFragment) fragment;
                editFeedFragment.savePreferences();
            }
        }
    }

    public void updatePreferences() {
        btPreferenceSave.setVisibility(View.GONE);
    }

    public void updatePreferencesVi() {
        btPreferenceSave.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
//        try {
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getBoolean("isForAdd")) {
                finish();
                return;
            }
        }
        Common.hideKeyboard(activity());
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Common.hideKeyboard(activity());
            }
        }, 300);
        //
        String FROM_USER_PROFILE = "";
        try {
            FROM_USER_PROFILE = getIntent().getExtras().getString("FROM_USER_PROFILE", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (FROM_USER_PROFILE == null) {
            FROM_USER_PROFILE = "";
        }
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            return;
        } else if (frameLayoutHelper.getVisibility() == View.VISIBLE) {
            frameLayoutHelper.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutHelper, DummyFragment.newInstance(null, null)).commit();
            return;
        } else if (swipeDisableViewPager.getCurrentItem() != 0) {
            goToHome();
            return;
        } else if (!FROM_USER_PROFILE.isEmpty()) {
            super.onBackPressed();
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to close CelebKonect", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void goToHome() {
        if (isCurvedBottomBar) {
            params_viewPager.setMargins(0, 0, 0, 0);
            swipeDisableViewPager.setLayoutParams(params_viewPager);
            imageViewHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_home_fill));
            imageViewSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_search_stroke));
            imageViewChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_chat_stroke));
            imageViewMenu.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_menu_stroke));
        } else {
            params_viewPager.setMargins(0, 0, 0, paddingToBottom);
        }
        btPreferenceSave.setVisibility(View.GONE);
        ibListGridView.setVisibility(View.GONE);
        mSearch.setVisibility(View.GONE);
        trLayout.setVisibility(View.VISIBLE);
        tLayout.setVisibility(View.VISIBLE);
        tTitle.setVisibility(View.GONE);
        searchView.setVisibility(View.GONE);
        iVStartChat.setVisibility(View.GONE);
        swipeDisableViewPager.setCurrentItem(0, false);

    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        UtilityNew.showSnackBar(activity(), coordinatorLayout, snackBarText, type);
    }

    public void showBlockError(String errorMsg) {
        showSnackBar(errorMsg, 2);
    }

    @Override
    public Activity activity() {
        return this;
    }

    public void openCreateFeed() {
        Intent intent = new Intent(activity(), HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_KEY, 9037);// create feed
        startActivityForResult(intent, Utility.generateRequestCodes().get("CREATE_FEED"));
        /*ImagePicker.with(this)
                .setFolderMode(true)
                .setCameraOnly(false)
                .setFolderTitle("Album")
                .setMultipleMode(true)
                .setSelectedImages(images)
                .setMaxSize(20)
                .setBackgroundColor("#212121")
                .setAlwaysShowDoneButton(false)
                .setRequestCode(Utility.generateRequestCodes().get("CREATE_FEED"))
                .setKeepScreenOn(true)
                .start();*/
    }

    private void openCustomGallery() {
        if (Utility.checkPermissionRequest(Permission.READ_STORAGE, activity()) && Utility.checkPermissionRequest(Permission.WRITE_STORAGE, activity())) {
            Intent intent = new Intent(activity(), HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_KEY, 9039);// gallery
            intent.putExtra("isFrom", "Feed");
            startActivity(intent);
        } else {
            redirectMethod = "openCustomGallery";
            if (!Utility.checkPermissionRequest(Permission.WRITE_STORAGE, activity()))
                Utility.raisePermissionRequest(Permission.WRITE_STORAGE, activity());
            else if (!Utility.checkPermissionRequest(Permission.READ_STORAGE, activity()))
                Utility.raisePermissionRequest(Permission.READ_STORAGE, activity());
            else {

            }
        }
    }

    public void editFeed(Feed feed, int position) {
        Intent intent = new Intent(activity(), HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_KEY, 9040);// leaders
        intent.putExtra("Feed", feed);
        intent.putExtra("Position", position);
        startActivityForResult(intent, Utility.generateRequestCodes().get("UPDATE_FEED"));
    }

    public void updateFeed(Feed feed, int position) {
        if (feed == null || feed.feedMemberDetails == null) {
            return;
        }
        if (FeedsFragment.getInstance() != null) {
            FeedsFragment.getInstance().updateFeed(feed, position);
        }
    }

    public void updateFeedDetails(Feed feed) {
        if (feed == null) {
            return;
        }
        if (FeedDetailsFragment.getInstance() != null) {
            FeedDetailsFragment.getInstance().update(feed);
        }
        if (MediaDetailsFragment.getInstance() != null) {
            MediaDetailsFragment.getInstance().updateMedia(feed);
        }
        if (MyContentFragment.getInstance() != null) {
            MyContentFragment.getInstance().updateFeed(feed);
        }
    }

    public void openFeedDetails(Feed feed, int position, int mediaPosition) {
        Common.getInstance().openFeedDetailsFrag(activity(), feed, position, mediaPosition);
    }

    public void openFeedLikes(Feed feed, String id, boolean isFeed, int feedPosition,
                              int mediaPosition, boolean isDetails) {
        llClose.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottom, LikesFragment.newInstance(feed, id, isFeed, feedPosition, mediaPosition, isDetails)).commit();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    /*public void openVideoPlayer(Uri uri, Feed feed, Media media, int feedPosition, int mediaPosition) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        frameLayoutBottom.setLayoutParams(layoutParams);
        llClose.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottom, VideoPlayerFragment.newInstance(uri, feed, media, feedPosition, mediaPosition)).commit();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }*/

    public void reDirect(String redirectMethod) {
        if (redirectMethod == null) return;

        switch (redirectMethod) {
            case "openCustomGallery":
                openCustomGallery();
                break;
        }

        this.redirectMethod = null;
    }

    public void openFeedOptions(Feed feed, int position, int type) {
        llClose.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottom, OptionsFragment.newInstance(feed, position, type)).commit();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Logger.d("Request Code", "" + requestCode);
        if (requestCode == Utility.generateRequestCodes().get("READ_STORAGE_REQUEST")) {
            if (Utility.checkPermissionRequest(Permission.READ_STORAGE, activity())) {
                reDirect(redirectMethod);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Utility.showToast(activity(), Constants.GIVE_ALL_PERMISSIONS, false);
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }

                showSnackBar("Permission denied", 2);
            }
        } else if (requestCode == Utility.generateRequestCodes().get("WRITE_STORAGE_REQUEST")) {
            if (Utility.checkPermissionRequest(Permission.WRITE_STORAGE, activity())) {
                reDirect(redirectMethod);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Utility.showToast(activity(), Constants.GIVE_ALL_PERMISSIONS, false);
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }
                showSnackBar("Permission denied", 2);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("Request Code", "" + requestCode);
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            if (isCreateStore) {
                // if (images.size() > 1) {
                isCreateStore = false;
                Intent intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_KEY, 9045);// create store
                intent.putExtra("GalleryFileList", images);
                startActivityForResult(intent, Utility.generateRequestCodes().get("CREATE_SROTE"));
                /*} else {
                    Intent intent = new Intent(activity(), HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_KEY, 9042);// create store
                    intent.putExtra("GalleryFileList", images.get(0));
                    startActivityForResult(intent, Utility.generateRequestCodes().get("CREATE_SROTE"));
                }*/
            } else {
                Intent intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_KEY, 9037);// create feed
                intent.putExtra("GalleryFileList", images);
                startActivityForResult(intent, Utility.generateRequestCodes().get("CREATE_FEED"));
            }
        }
        if (requestCode == Utility.generateRequestCodes().get("CREATE_FEED")) {
            if (data != null && resultCode == RESULT_OK) {
                Common.getInstance().createUpdateFeed(data.getParcelableExtra("Feed"), 0, false);
            }
        } else if (requestCode == Utility.generateRequestCodes().get("UPDATE_FEED")) {
            if (data != null && resultCode == RESULT_OK) {
                if (frameLayoutHelper.getVisibility() == View.VISIBLE) {
                    activity().onBackPressed();
                }
                Feed feed = data.getParcelableExtra("Feed");
                Common.getInstance().createUpdateFeed(feed, data.getIntExtra("FeedPosition", 0), true);
                //doEditFeed(feed, data.getIntExtra("FeedPosition", 0));
            }
        } else if (requestCode == Utility.generateRequestCodes().get("UPDATE_FEED_DATA")) {
            if (data != null && resultCode == RESULT_OK) {
                Feed feed = data.getParcelableExtra("feed");
                int feedPosition = data.getIntExtra("feedPosition", -1);
                if (feed != null && feedPosition > -1) {
                    updateFeed(feed, feedPosition);
                    updateFeedDetails(feed);
                }
            }
        } else if (requestCode == Utility.generateRequestCodes().get("ADD_MEDIA_TO_FEED")) {
            if (data != null && resultCode == RESULT_OK) {
                images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
                if (isCreateStore) {
                    isCreateStore = false;
                    if (images.size() > 1) {
                        Intent intent = new Intent(activity(), HelperActivity.class);
                        intent.putExtra(Constants.FRAGMENT_KEY, 9045);// create store
                        intent.putExtra("GalleryFileList", images);
                        startActivityForResult(intent, Utility.generateRequestCodes().get("CREATE_SROTE"));
                    } else {
                        if (images.get(0).mediaType == info.sumantv.flow.celebflow.constants.MediaType.VIDEO) {
                            Intent intent = new Intent(activity(), HelperActivity.class);
                            intent.putExtra(Constants.FRAGMENT_KEY, 9042);// create store
                            intent.putExtra("GalleryFileList", images.get(0));
                            startActivityForResult(intent, Utility.generateRequestCodes().get("CREATE_SROTE"));
                        } else {
                            Intent intent = new Intent(activity(), HelperActivity.class);
                            intent.putExtra(Constants.FRAGMENT_KEY, 9045);// create store
                            intent.putExtra("GalleryFileList", images);
                            startActivityForResult(intent, Utility.generateRequestCodes().get("CREATE_SROTE"));
                        }
                    }
                } else {
                    Intent intent = new Intent(activity(), HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_KEY, 9037);// create feed
                    intent.putExtra("GalleryFileList", data.getParcelableArrayListExtra(Config.EXTRA_IMAGES));
                    startActivityForResult(intent, Utility.generateRequestCodes().get("CREATE_FEED"));
                }
            }
        } else if (requestCode == Utility.generateRequestCodes().get("ADD_MEDIA_TO_STORE")) {
            if (data != null && resultCode == RESULT_OK) {
                images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);

                //if (images.size() > 1) {
                Intent intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_KEY, 9045);// create store
                intent.putExtra("GalleryFileList", images);
                startActivityForResult(intent, Utility.generateRequestCodes().get("CREATE_SROTE"));
                /*} else {
                    Intent intent = new Intent(activity(), HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_KEY, 9042);// create store
                    intent.putExtra("GalleryFileList", images.get(0));
                    startActivityForResult(intent, Utility.generateRequestCodes().get("CREATE_SROTE"));
                }*/
            }
        } else if (requestCode == Utility.generateRequestCodes().get("UPDATE_ONLINE_USERS")) {
            if (data != null && resultCode == RESULT_OK) {
                Logger.d("IsFollow", "" + data.getBooleanExtra("isFollow", false));
            }
        } else if (requestCode == Utility.generateRequestCodes().get("EDIT_PROFILE_USER")) {
            if (data != null && resultCode == RESULT_OK) {
                goToHome();
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof FeedsFragment) {
                        FeedsFragment feedsFragment = (FeedsFragment) fragment;
                        feedsFragment.refreshFeedAdapter();
                    }
                }

            }
        } else if (requestCode == Utility.generateRequestCodes().get("MANAGER_PROFILE_EMPTY")) {
            if (data != null && resultCode == RESULT_OK) {
                if (data.getStringExtra("SaveData").equalsIgnoreCase("TRUE")) {

                }
            } else {
                finish();
            }
        } else if (requestCode == Constants.REQUEST_LOCATION_CHECK_SETTINGS) {
            Common.getInstance().getLocation(this, this.getClass().getSimpleName(), false);
        }
    }

//    private void exitFromApplication() {
//        promoDialog = new Dialog(this);
//        promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        promoDialog.setCancelable(false);
//        promoDialog.setContentView(R.layout.are_you_sure_exit_popup);
//        Button nobtn = (Button) promoDialog.findViewById(R.id.nobtn);
//        Button yesBtn = (Button) promoDialog.findViewById(R.id.yesBtn);
//        promoDialog.show();
//
//        nobtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                promoDialog.dismiss();
//            }
//        });
//        yesBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                promoDialog.dismiss();
//                Intent a = new Intent(Intent.ACTION_MAIN);
//                a.addCategory(Intent.CATEGORY_HOME);
//                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(a);
//
//            }
//        });
//
//    }

    /*public void HideSaveButton() {
        btPreferenceSave = findViewById(R.id.btPreferenceSave);
        btPreferenceSave.setVisibility(View.VISIBLE);

    }*/

    public void showQuizDailog(Banner banner) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dailog_quiz_home);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView ivBanner = dialog.findViewById(R.id.ivBanner);
        ImageView ivCLose = dialog.findViewById(R.id.ivClose);

        Glide.with(getApplicationContext())
                .load(ApiClient.BASE_URL + banner.bannerImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.celebicon_iogo)
                .into(ivBanner);

        ivCLose.setOnClickListener(v -> {
            updateBannerStatus(banner);
            dialog.dismiss();

        });
        ivBanner.setOnClickListener(v -> {
            updateBannerStatus(banner);
            dialog.dismiss();
        });

        dialog.show();
    }


    public void checkBannerStatus() {
        if (!Utility.isNetworkAvailable(activity())) {
            return;
        }
        IApiListener iApiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                Banner banner = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), new TypeToken<Banner>() {
                }.getType());
                if (!banner.isSubmitted) {
                    if (banner.isActive) {
                        showQuizDailog(banner);
                    }
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                //
            }
        };
        //
        ApiInterface apiInterface = ApiClient.getClientJSON().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.GET_JSON(Constants.BannerConstants.URL_CHECK_BANNER_STATUS + SessionManager.userLogin.userId);
        Common.getInstance().callAPIJSON(new ApiRequestModelJSON().setModel(activity(), call, Constants.BannerConstants.URL_CHECK_BANNER_STATUS,
                false, iApiListener, false));
    }

    public void updateBannerStatus(Banner banner) {

        JSONObject input = new JSONObject();
        try {
            if (SessionManager.userLogin.userId != null && !SessionManager.userLogin.userId.isEmpty()) {
                input.put("memberId", SessionManager.userLogin.userId);
                input.put("bannerId", banner.id);
                input.put("createdBy", SessionManager.userLogin.userId);
                updateBanner(input);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateBanner(JSONObject input) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), input.toString());
        Call<ApiResponseModel> call = apiInterface.POST(Constants.BannerConstants.URL_SUBMIT_BANNER, requestBody);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.BannerConstants.URL_SUBMIT_BANNER, false, iApiListener, false));
    }

    public void managerProfileDialog() {
        try {
            managerProfileFillDailog = new Dialog(activity());
            managerProfileFillDailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            managerProfileFillDailog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            managerProfileFillDailog.setCancelable(false);
            managerProfileFillDailog.setContentView(R.layout.manager_profile_fill);
            Button bt_go_to_profile = managerProfileFillDailog.findViewById(R.id.bt_go_to_profile);
            bt_go_to_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        managerProfileFillDailog.dismiss();
                        Intent intent = new Intent(activity(), HelperActivity.class);
                        intent.putExtra(Constants.FRAGMENT_TITLE, "Manager Profile");
                        intent.putExtra("ManagerDetailsEmpty", true);
                        intent.putExtra(Constants.FRAGMENT_KEY, 8059);// ManagerAdditionalDetails
                        startActivityForResult(intent, Utility.generateRequestCodes().get("MANAGER_PROFILE_EMPTY"));
                        //    finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            managerProfileFillDailog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        searchView.setVisibility(View.GONE);
        SocketForAppUtill.getInstance().missedCallCountEmit();
        switch (view.getId()) {
            case R.id.rlHome:
                params_viewPager.setMargins(0, 0, 0, 0);
                swipeDisableViewPager.setLayoutParams(params_viewPager);
                imageViewHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_home_fill));
                imageViewSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_search_stroke));
                imageViewChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_chat_stroke));
                imageViewMenu.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_menu_stroke));
                imageViewCK.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_bott));

                try {
                    List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                    for (Fragment fragmentInner : fragmentList) {
                        if (fragmentInner instanceof FeedsFragment) {
                            if (swipeDisableViewPager != null && swipeDisableViewPager.getCurrentItem() == 0) {
                                FeedsFragment feedsFragment = (FeedsFragment) fragmentInner;
                                feedsFragment.scrollToTop();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                btPreferenceSave.setVisibility(View.GONE);
                imgUser.setVisibility(View.GONE);
                ibListGridView.setVisibility(View.GONE);
                mSearch.setVisibility(View.GONE);
                trLayout.setVisibility(View.VISIBLE);
                tLayout.setVisibility(View.VISIBLE);
                tTitle.setVisibility(View.GONE);
                swicthAccountIconOther.setVisibility(View.GONE);
                iVStartChat.setVisibility(View.GONE);
                swipeDisableViewPager.setCurrentItem(0, false);

//                            searchRelatedChanges(getResources().getColor(R.color.skyblue), getResources().getColor(R.color.white));

                //Notification And Online
                SocketForAppUtill.getInstance().noticationCountEmit();

                SocketForAppUtill.getInstance().missedCallCountEmit();

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                }
                break;
            case R.id.rlSearch:
                params_viewPager.setMargins(0, 0, 0, paddingToBottom);
                swipeDisableViewPager.setLayoutParams(params_viewPager);
                imageViewHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_home_stroke));
                imageViewSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_search_fill));
                imageViewChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_chat_stroke));
                imageViewMenu.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_menu_stroke));
                imageViewCK.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_bott));
                btPreferenceSave.setVisibility(View.GONE);
                ibListGridView.setVisibility(View.VISIBLE);
                mSearch.setVisibility(View.VISIBLE);
                mSearch.onActionViewCollapsed();

                trLayout.setVisibility(View.GONE);
                tTitle.setVisibility(View.VISIBLE);
                tTitle.setText("Celebrities");
                tLayout.setVisibility(View.GONE);
                iVStartChat.setVisibility(View.GONE);

                //for New Screens
                tTitle.setVisibility(View.GONE);
                ibListGridView.setVisibility(View.GONE);
                mSearch.setVisibility(View.GONE);

//                            iNotifications.setVisibility(View.GONE);
//                            ibRecentChat.setVisibility(View.GONE);

//                            searchRelatedChanges(getResources().getColor(R.color.white), getResources().getColor(R.color.gray));


                swipeDisableViewPager.setCurrentItem(1, false);


//                            if (getSupportFragmentManager().getFragments().get(1) instanceof CelebrityBaseFragment) {
//                                CelebrityBaseFragment celebrityBaseFragment = (CelebrityBaseFragment) getSupportFragmentManager().getFragments().get(1);
//                                celebrityBaseFragment.getAllCelebritiesListFormServer();
//                            }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                }
                break;
            case R.id.rlCk:
                break;
            case R.id.rlChat:
                params_viewPager.setMargins(0, 0, 0, paddingToBottom);
                swipeDisableViewPager.setLayoutParams(params_viewPager);
                imageViewHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_home_stroke));
                imageViewSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_search_stroke));
                imageViewChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_chat_fill));
                imageViewMenu.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_menu_stroke));
                imageViewCK.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_bott));

                setChatToolbar(true);
                searchView.setVisibility(View.VISIBLE);
                iVStartChat.setVisibility(View.VISIBLE);
                collapseSearchView();
                swipeDisableViewPager.setCurrentItem(3, false);

                           /* if (CommonAccessPermissionOfCeleb.servicePermissonAvailabilty(activity(), true, false)) {
                                Common.getInstance().openChatTabsActivity(activity(), null);
                            }*/
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                }
                break;
            case R.id.rlMenu:
                params_viewPager.setMargins(0, 0, 0, paddingToBottom);
                swipeDisableViewPager.setLayoutParams(params_viewPager);
                imageViewHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_home_stroke));
                imageViewSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_search_stroke));
                imageViewChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_chat_stroke));
                imageViewMenu.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_menu_fill));
                imageViewCK.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_bott

                ));
                btPreferenceSave.setVisibility(View.GONE);
                ibListGridView.setVisibility(View.GONE);
                mSearch.setVisibility(View.GONE);
                trLayout.setVisibility(View.GONE);
                tLayout.setVisibility(View.GONE);
                iVStartChat.setVisibility(View.GONE);
                swipeDisableViewPager.setCurrentItem(4, false);
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                }
                break;
            case R.id.swicthAccountIconFeed:
                Common.getInstance().switchOwnProfile(BottomMenuActivity.this, false);
                break;
            case R.id.swicthAccountIconOther:
                Common.getInstance().switchOwnProfile(BottomMenuActivity.this, false);
                break;
            case R.id.imageViewCK:
                // Uday for create stores
                showSnackBar(ckMessage, 2);
                imageViewHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_home_stroke));
                imageViewSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_search_stroke));
                imageViewChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_chat_stroke));
                imageViewMenu.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_menu_stroke));
                imageViewCK.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_bott));
//                openCreateStore();
                break;

        }
    }

    public void openCreateStore() {
        addMediaDirectly("CreateStories");
        isCreateStore = true;
    }

    public void searchClose() {
        mSearch.onActionViewCollapsed();
        tTitle.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlideFaceDetector.releaseDetector();
        FrescoFaceDetector.releaseDetector();
//        unregisterReceiver(receiver);
    }

/*@Override
    protected void onDestroy() {
        Log.e("BottomDestroyCalled", "true");
        Common.getInstance().setBottomMenuActivity(null);

        //commented 0n 20-2-19
        *//*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e("AppDestoyedBottom", "trueee");
            try {
                if (userInfo != null) {
                    Common.getInstance().getSocket().emit("exit service", userInfo);
                    Log.e("celebLeftBackground", "true");
                }
            } catch (Exception e) {
            }
            super.finishAndRemoveTask();
            super.onDestroy();
        } else {
            Log.e("AppDestoyedBottom", "truyes");
            try {
                if (userInfo != null) {
                    Common.getInstance().getSocket().emit("exit service", userInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onDestroy();
        }*//*
        super.onDestroy();
    }*/

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.BannerConstants.URL_SUBMIT_BANNER)) {
            Toast.makeText(BottomMenuActivity.this, apiResponseModel.message, Toast.LENGTH_LONG).show();
        }  else if (condition.equals(ApiClient.getMemberPreferancesCount)) {
            try {
                JSONObject jsonObject = new JSONObject(new Gson().toJson(apiResponseModel.data));
                if (!jsonObject.optBoolean("isPreferencesSelected", false)) {
                    finish();
                    Common.getInstance().openPreferencesFrag(activity(), true);
                } else {
                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_PREFERENCES_SELECTED, true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.BannerConstants.URL_SUBMIT_BANNER)) {

        }
    }

    public void visiblePreferenceUpdateButton() {
        btPreferenceSave.setVisibility(View.VISIBLE);
    }

    private Runnable updateTime = new Runnable() {
        public void run() {
            try {
                int seconds = callCountTimer + 1;
                int minutes = callCountTimer_min + seconds / 60;
                seconds = seconds % 60;
                callCountTimer++;
                //
                String duration = String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
                tvcallTime.setText(duration);
            } catch (Exception e) {
                e.printStackTrace();
            }
            timeHandler.postDelayed(this, 1000);
        }
    };


    private void internetConnectivityChecker() {
        try {
            intentFilter = new IntentFilter();
            intentFilter.addAction(CONNECTIVITY_ACTION);

            receiver = new ConnectivityReceiver();
            if (NetworkUtil.getConnectivityStatus(BottomMenuActivity.this) > 0) {
                System.out.println("Connect");
            } else {
                System.out.println("No connection");
            }
            addLogText(NetworkUtil.getConnectivityStatusString(BottomMenuActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMediaDirectly(String isFrom) {
       /* Intent intent = new Intent(activity(), HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_KEY, 9039);
        intent.putExtra("isFrom", isFrom);
        startActivityForResult(intent, Utility.generateRequestCodes().get("ADD_MEDIA_TO_FEED"));*/
        ArrayList<Image> images = new ArrayList<>();
        ImagePicker.with(this)
                .setFolderMode(true)
                .setCameraOnly(false)
                .setFolderTitle("Album")
                .setMultipleMode(true)
                .setGif(false)
                .setSelectedImages(images)
                .setMaxSize(1)
                .setBackgroundColor("#212121")
                .setAlwaysShowDoneButton(false)
                .setRequestCode(Utility.generateRequestCodes().get("ADD_MEDIA_TO_FEED"))
                .setKeepScreenOn(true)
                .setIsFeed(false)
                .setMultipleImages(false)
                .start();
    }

   /* private void internetConnectivityCheckerOnPauseCall() {
        try {
            intentFilter = new IntentFilter();
            intentFilter.addAction(CONNECTIVITY_ACTION);

            receiver = new ConnectivityReceiver();
            if (NetworkUtil.getConnectivityStatus(AppController.getInstance().getContext()) > 0) {
                System.out.println("Connect");
            } else {
                System.out.println("No connection");
            }
            addLogText(NetworkUtil.getConnectivityStatusString(AppController.getInstance().getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static void addLogText(String log) {
        try {
            if (log.equals("NOT_CONNECT")) {

                Toast.makeText(AppController.getInstance().getApplicationContext(), "No active internet connection!!", Toast.LENGTH_SHORT).show();


                Common.getInstance().callBusyRelatedDataClear();

//                Common.getInstance().disconnectSocket();
            } else {
//                Toast.makeText(AppController.getInstance().getApplicationContext(), "Internet connection available!!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }

//    @Override
//    public boolean isDestroyed() {
//
////        EventBus.getDefault().unregister(this);
//
//        return super.isDestroyed();
//
//    }

/*    @Subscribe
    public void onEvent(EventChangeChatServerStateEvent event) {
        switch (event.getState()) {
            case connectedToSocket:
//                connectionState(new ConnectionTypes(ConnectionTypes.connectionType.connectedToSocket));
                break;
            case disconnectedFromSocket:
//                connectionState(new ConnectionTypes(ConnectionTypes.connectionType.disconnectedFromSocket));
                break;
            case flashConnectionIcon:
//                connectionState(new ConnectionTypes(ConnectionTypes.connectionType.prepareConnection));
                break;
        }
    }*/

    private void searchRelatedChanges(int toolBarColor, int hintAndIconColor) {
        eSearchBar.setVisibility(View.VISIBLE);
        toolbar.setBackgroundColor(toolBarColor); //getResources().getColor(R.color.white)
        eSearchBar.setHintTextColor(hintAndIconColor); //getResources().getColor(R.color.gray)

        final Drawable originalDrawable = eSearchBar.getBackground();
        originalDrawable.mutate();
        final Drawable wrappedDrawable = DrawableCompat.wrap(originalDrawable);
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(hintAndIconColor));
        eSearchBar.setBackground(wrappedDrawable);
    }


}
