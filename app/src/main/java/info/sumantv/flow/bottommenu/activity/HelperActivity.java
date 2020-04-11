package info.sumantv.flow.bottommenu.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.crashlytics.android.Crashlytics;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.appmanagers.feed.models.Feed;
import info.sumantv.flow.appmanagers.feed.models.Media;
import info.sumantv.flow.bottommenu.celebrityprofile.CelebrityProfileFragment;
import info.sumantv.flow.bottommenu.celebrityprofile.FragmentCelebSchedules;
import info.sumantv.flow.bottommenu.fragment.OptionsFragment;
import info.sumantv.flow.bottommenu.interfaces.activity.IActivity;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.ActivityLogFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.ApplicationSettingsFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.ChangePasswordFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.CharitySettingsFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.CreateSlotFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.EditProfileFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.FAQFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.FeedSettingsFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.InviteAFriend;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.MyCelebritiesfragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.MyContentFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.MyFanFollowersFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.MyReportFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.NotificationSettingsFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.NotificationsFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.QuizProceedFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.SettingsPageFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.TermsandConditionsAuditionFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.calender_item.CalenderSlotListfragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.calender_item.FragmentScheduleDeleteBottom;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.my_schedules.ArchiveNotificationFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.my_schedules.CreateScheduleFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.my_schedules.MySchedulesListFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.my_schedules.ViewScheduleFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.modelclasses.CalenderSlot;
import info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsRecharge;
import info.sumantv.flow.bottommenu.mypaymentactionsfragment.OrderFailed;
import info.sumantv.flow.bottommenu.mypaymentactionsfragment.OrderSuccess;
import info.sumantv.flow.bottommenu.mypaymentactionsfragment.PaymentOption;
import info.sumantv.flow.bottommenu.preferencemanager.PrefrencesExpentableListviewFragment;
import info.sumantv.flow.bottommenu.search.SearchFragment;
import info.sumantv.flow.celebflow.EditProfileActivity.GetProfileData;
import info.sumantv.flow.celebflow.Fragments.MediaDetailsFragment;
import info.sumantv.flow.celebflow.Notifications.FragmentNotificationDeleteBottom;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.SignUpFragment;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.celebflow.constants.Permission;
import info.sumantv.flow.celebflow.modelData.NotificationDeleteBottomModel;
import info.sumantv.flow.chat.Fragment.FragmentCallDetails;
import info.sumantv.flow.chat.Fragment.FragmentNewChatList;
import info.sumantv.flow.chat.Fragment.FragmentTabsChat;
import info.sumantv.flow.ipoll.fragments.camera.CameraOptionsFragment;
import info.sumantv.flow.ipoll.fragments.dummy.DummyFragment;
import info.sumantv.flow.ipoll.fragments.feeds.FeedsFragment;
import info.sumantv.flow.ipoll.fragments.feeds.OnlineCelebViewAllFragment;
import info.sumantv.flow.ipoll.fragments.feeds.comments.CommentsFragment;
import info.sumantv.flow.ipoll.fragments.feeds.details.FeedDetailsFragment;
import info.sumantv.flow.ipoll.fragments.feeds.likes.LikesFragment;
import info.sumantv.flow.ipoll.fragments.feeds.options.CreateFeedFragment;
import info.sumantv.flow.ipoll.fragments.feeds.options.EditFeedFragment;
import info.sumantv.flow.ipoll.fragments.feeds.options.UpdateFeedFragment;
import info.sumantv.flow.ipoll.fragments.gallery.GalleryFragment;
import info.sumantv.flow.ipoll.fragments.videoplayer.VideoPlayerFragment;
import info.sumantv.flow.ipoll.interfaces.dialogs.custom.ICustomAlertDialog;
import info.sumantv.flow.menu_list.transactions.TransactionsFragment;
import info.sumantv.flow.otp.OTPFragment;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.stories.fragments.CreateStoriesFragment;
import info.sumantv.flow.stories.fragments.MultipleImageCreateStoryFragment;
import info.sumantv.flow.stories.fragments.StoriesFragment;
import info.sumantv.flow.stories.fragments.StoriesViewPager;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.userflow.Util.FrescoFaceDetector;
import info.sumantv.flow.userflow.Util.GlideFaceDetector;
import info.sumantv.flow.utils.CompleteImage;
import info.sumantv.flow.utils.Logger;
import info.sumantv.flow.utils.SocketForAppUtill;
import info.sumantv.flow.utils.Utility;
import info.sumantv.flow.imagepicker.model.Config;
import info.sumantv.flow.imagepicker.model.Image;
import info.sumantv.flow.imagepicker.ui.imagepicker.ImagePicker;
import io.fabric.sdk.android.Fabric;

public class HelperActivity extends AppCompatActivity implements IActivity, ICustomAlertDialog, IApiListener {
    BottomSheetBehavior bottomSheetBehavior;
    private CoordinatorLayout coordinatorLayout;
    private FrameLayout frameLayoutHelper;
    private FrameLayout frameLayoutBottom;
    LinearLayout youLinearLayout;
    private FrameLayout frameLayout;
    private TextView tvTitle;
    Button btPreferenceSave;
    private ImageView imageView_notification, ivCancel, imageView_archive, celebListIcon;
    CircleImageView imageView_userIcon;
    private LinearLayout tLayout;
    private androidx.appcompat.widget.Toolbar toolbar;
    private Button btUpdate, btContinue, btnEdit;
    private RelativeLayout rlLayout;
    private LinearLayout llClose;
    private FrameLayout fragmentLayoutBottomSheet;
    TextView textViewSaveChanges;
    ImageButton ibFavourite, ibGridView, ibSelectAll, ibUnSelectAll, ibDelete;
    androidx.appcompat.widget.SearchView searchView;
    ImageView ivHomeImage;
    RecyclerView myclientRecyclerView;
    TextView tvClearAll;
    IApiListener iApiListener;
    public MenuItem menuDone, menuUpdate, menuShare, menuArchive, menuSearch;
    public MenuItem menuMediaDone, menuAuditionProfileEdit;
    public String redirectMethod = null;
    boolean isGridStatus = true;
    public boolean isEdit = false;
    File imageFile = null;
    boolean isNewReg = false;
    public static HelperActivity instance;
    private boolean connectionStatus;
    public static String HomeBtnClick = "HomeBtnClick";
    ApiInterface apiInterface;
    MediaDetailsFragment mediaDetailsFragment;
    private ArrayList<Image> images = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.screenShotRestrict(HelperActivity.this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(activity(), R.color.status_bar));// set status background whit
        if (!ApiClient.ENVIRONMENT.equals("test")) {
            Fabric.with(this, new Crashlytics());
        }
        setContentView(R.layout.activity_helper);
        iApiListener = this;
        instance = this;
        GlideFaceDetector.initialize(getApplicationContext());
        FrescoFaceDetector.initialize(getApplicationContext());
        setUp();
        setUpFragment();
        showStatusBar();
        setOrientation(true);
    }

    public static HelperActivity getInstance() {
        return instance;
    }

    private void setOrientation(boolean status) {
        if (status) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    private void hideStatusBar() {
        //How to hide status bar in android in just one activity
        if (Build.VERSION.SDK_INT > 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void showStatusBar() {
        // Show status bar
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    public void showBtnEdit(Boolean show) {
        if (show) {
            btnEdit.setVisibility(View.VISIBLE);
        } else {
            btnEdit.setVisibility(View.GONE);
        }
    }

    public void collapseSearchView() {
        try {
            if (!searchView.isIconified()) {
                searchView.onActionViewCollapsed();
                tvTitle.setVisibility(View.VISIBLE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpFragment() {
        Intent intent = getIntent();
        int fragmentKey = intent.getIntExtra(Constants.FRAGMENT_KEY, 0);
        String title = intent.getStringExtra(Constants.FRAGMENT_TITLE);
        //
        Logger.d(Constants.FRAGMENT_KEY, "" + fragmentKey);
        btnEdit.setVisibility(View.GONE);
        if (fragmentKey > 0) {
            Fragment fragment = null;
            switch (fragmentKey) {
                case 8001: //search fragment
                    changeTitle("");
                    rlLayout.setVisibility(View.GONE);
                    celebListIcon.setVisibility(View.GONE);
                    fragment = SearchFragment.newInstance(null, null);
                    break;

                case 8002: //EditProfileFragment

                    //New Screen changes Uday
                    changeTitle(title);
                    rlLayout.setVisibility(View.GONE);
                    Bundle arguments = new Bundle();
                    arguments.putString("CelebId", SessionManager.userLogin.userId);
                    arguments.putBoolean("fromSearchResult", intent.getBooleanExtra("fromSearchResult", false));
                    fragment = CelebrityProfileFragment.newInstance(null, null);
                    fragment.setArguments(arguments);

                    /*changeTitle(title);
                    rlLayout.setVisibility(View.GONE);
                    celebListIcon.setVisibility(View.GONE);
                    btUpdate.setVisibility(View.VISIBLE);
                    btUpdate.setText("Edit");
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    Bundle arguments = new Bundle();
                    arguments.putString("HEADER_TITLE", title);

                    if (intent.getStringExtra("NEW_REG") != null && !intent.getStringExtra("NEW_REG").isEmpty()) {
                        Log.e("NEW_REG", intent.getStringExtra("NEW_REG") + "");
                        if (intent.getStringExtra("NEW_REG").equals("TRUE")) {
                            arguments.putString("NEW_REG", "TRUE");
                            btContinue.setVisibility(View.VISIBLE);
                            isNewReg = true;
                            arguments.putBoolean("reload", false);
                            btUpdate.setVisibility(View.GONE);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            getSupportActionBar().setHomeButtonEnabled(true);
                            tLayout.setVisibility(View.GONE);
                        } else {
                            arguments.putString("NEW_REG", "FALSE");
                            arguments.putBoolean("reload", true);
                            btContinue.setVisibility(View.GONE);
                            isNewReg = false;
                            tLayout.setVisibility(View.GONE);
                        }
                    } else {
                        arguments.putString("NEW_REG", "FALSE");
                        arguments.putBoolean("reload", true);
                        isNewReg = false;
                        btContinue.setVisibility(View.GONE);
                        tLayout.setVisibility(View.GONE);
                    }

                    fragment = EditProfileFragmentNew.newInstance(null, null);
                    fragment.setArguments(arguments);*/
                    break;

                case 8003: //MyCelebritiesfragment
                    changeTitle(title);
                    tLayout.setVisibility(View.GONE);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_MEMBER_OR_CELEB_PROFILE, "My Celebrities");
                    tLayout.setVisibility(View.VISIBLE);

                    if (intent.getStringExtra("selectionName") != null) {
                        fragment = MyCelebritiesfragment.newInstance(intent.getStringExtra("selectionName"),
                                intent.getStringExtra("FAN_OR_FOLLOW"), intent.getStringExtra("CELEB_ID"));
                    } else fragment = MyCelebritiesfragment.newInstance(null, null, null);

                    break;

                case 8004: //MyScheduleDemoFragment
//                    SharedPrefsUtil.setStringPreference(getApplicationContext(), "USER_ID", "5ad4ae85e9dd1117d0d3d55e");
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.VISIBLE);
                    tLayout.setVisibility(View.VISIBLE);
                    arguments = new Bundle();
                    arguments.putString("COMING_PAGE", intent.getStringExtra("COMING_PAGE"));
//                    fragment = MyScheduleDemoFragment.newInstance(null, null);
                    fragment = MySchedulesListFragment.newInstance(null, null);
                    fragment.setArguments(arguments);
                    break;

               /* case 8005: //MyCartfragmentCart

                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    arguments = new Bundle();
                    arguments.putString("MyCartfragment", "Cart");
                    arguments.putString("CELEB_ID", intent.getStringExtra("CELEB_ID"));
                    arguments.putString("CELEB_REFRL_CREDITS", intent.getStringExtra("CELEB_REFRL_CREDITS"));
                    arguments.putString("MEMBER_REFRL_CREDITS", intent.getStringExtra("MEMBER_REFRL_CREDITS"));
                    //MEMBER_REFRL_CREDITS
                    fragment = MyCartfragment.newInstance(null, null);
                    fragment.setArguments(arguments);
                    changeTitle(title);
                    tLayout.setVisibility(View.GONE);
                    break;*/

                case 8205: //TransactionsFragment

                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    arguments = new Bundle();
                    arguments.putString("MyCartfragment", "Cart");
                    arguments.putString("condition", intent.getStringExtra("condition"));
                    arguments.putString("CELEB_ID", intent.getStringExtra("CELEB_ID"));
                    arguments.putString("CELEB_REFRL_CREDITS", intent.getStringExtra("CELEB_REFRL_CREDITS"));
                    arguments.putString("MEMBER_REFRL_CREDITS", intent.getStringExtra("MEMBER_REFRL_CREDITS"));
                    //MEMBER_REFRL_CREDITS
                    fragment = TransactionsFragment.newInstance(null, null);
                    fragment.setArguments(arguments);
                    changeTitle(title);
                    tLayout.setVisibility(View.GONE);
                    break;

                case 8007: //SettingsPageFragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = SettingsPageFragment.newInstance(null, null);
                    break;

                case 8008: //MyContentFragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = MyContentFragment.newInstance();
                    break;

                case 8009: //ScheduleListfragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
//                    fragment = ScheduleListfragment.newInstance(null, null);
                    fragment = CalenderSlotListfragment.newInstance(getIntent().getStringExtra("celebID"), null);
                    break;

                case 8010: //MyFanFollowersFragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    arguments = new Bundle();


                    arguments.putString("FAN_OR_FOLLOW", intent.getStringExtra("FAN_OR_FOLLOW"));
                    arguments.putString("FROM_USER_PROFILE", intent.getStringExtra("FROM_USER_PROFILE"));
                    arguments.putBoolean("isFromCelebProfile", intent.getBooleanExtra("isFromCelebProfile", false));
                    arguments.putString("CELEB_ID", intent.getStringExtra("CELEB_ID"));
                    fragment = MyFanFollowersFragment.newInstance();
                    break;

                case 8012: //AppPromotionsFragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = InviteAFriend.newInstance(null);
                    break;

                case 8013: //MyReportFragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.GONE);
                    fragment = MyReportFragment.newInstance(null, null);
                    break;

                case 8014: //FAQFragmentHelp
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    arguments = new Bundle();
                    arguments.putString("FAQ", "Help");
                    fragment = FAQFragment.newInstance(null, null);
                    fragment.setArguments(arguments);
                    break;

                case 8016: //ApplicationSettingsFragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = ApplicationSettingsFragment.newInstance(null, null);
                    break;

                case 8017: //CharitySettingsFragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = CharitySettingsFragment.newInstance(null, null);
                    break;

                case 8018: //ChangePasswordFragment
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    changeTitle(title);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = ChangePasswordFragment.newInstance(null, null);
                    break;

                case 8019: //NotificationsFragment


                    changeTitle(title);
                    tLayout.setVisibility(View.VISIBLE);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.VISIBLE);
                    if (intent.getBooleanExtra("isFromArchive", false)) {
                        imageView_archive.setVisibility(View.GONE);
                    } else {
                        imageView_archive.setVisibility(View.GONE);
                    }

                    arguments = intent.getExtras();
                    String OtherProfilePic = "";
                    if (arguments != null) {
                        OtherProfilePic = arguments.getString("OtherProfilePic", "");
                    }
                    if (OtherProfilePic != null && !OtherProfilePic.isEmpty()) {
                        Common.getInstance().setGlideImage(this, ApiClient.BASE_URL + OtherProfilePic, imageView_userIcon);
                    } else if (SessionManager.userLogin.profilePic != null && !SessionManager.userLogin.profilePic.isEmpty()) {
                        Common.getInstance().setGlideImage(this, ApiClient.BASE_URL + SessionManager.userLogin.profilePic, imageView_userIcon);
                    } else {
                        imageView_userIcon.setImageResource(R.drawable.ic_grey_celebkonect_logo);
                    }
                    if (Common.isCelebAndManager(activity()) || Common.isManagerStatus(activity())) {
                        celebListIcon.setVisibility(View.VISIBLE);
                    } else {
                        celebListIcon.setVisibility(View.GONE);
                    }

                    fragment = NotificationsFragment.newInstance();
                    fragment.setArguments(arguments);
                    break;
                case 8020: //NotificationSettingsFragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = NotificationSettingsFragment.newInstance();
                    break;

                case 8021: //FAQFragmentAbout Us
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    arguments = new Bundle();
                    arguments.putString("FAQ", "About Us");
                    fragment = FAQFragment.newInstance(null, null);
                    fragment.setArguments(arguments);
                    break;


                case 8022: //FAQFragmentContact Us
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    arguments = new Bundle();
                    arguments.putString("FAQ", "Contact Us");
                    fragment = FAQFragment.newInstance(null, null);
                    fragment.setArguments(arguments);
                    break;

                case 8023://CreateSlotFragment
                    // changeTitle(title);
                    changeTitle("Create Schedule");
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = CreateSlotFragment.newInstance(null, null);
                    break;

                case 8024://AuditionAllinfoFragment
                    break;

                case 8025://TermsandConditionsAuditionFragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = TermsandConditionsAuditionFragment.newInstance(null, null);
                    break;

                case 8026://AudionsPublicProfileFragment
                    changeTitle(title);
                    tLayout.setVisibility(View.VISIBLE);
//                    fragment = AudionsPublicProfileFragment.newInstance(null, null);
                    break;

                case 8027://AudionsApplySucessfullfragment
                    changeTitle(title);
                    tLayout.setVisibility(View.VISIBLE);
//                    fragment = AudionsApplySucessfullfragment.newInstance(null, null);
                    break;

//                case 8028://SelFProfileFragment
//                    changeTitle(title);
//                    tLayout.setVisibility(View.VISIBLE);
//                    arguments = new Bundle();
//                    arguments.putString("CelebId", intent.getStringExtra("CelebId"));
//                    arguments.putString("ABOUT", intent.getStringExtra("ABOUT"));
//                    fragment = SelfProfileFragment.newInstance(null, null);
//                    fragment.setArguments(arguments);
//                    break;

                case 8029://UserBookAppointment

                    //CelebrityProfileFragment

                    changeTitle(title);
                    rlLayout.setVisibility(View.GONE);
                    arguments = new Bundle();
                    arguments.putString("CelebId", intent.getStringExtra("CelebId"));
                    arguments.putBoolean("fromSearchResult", intent.getBooleanExtra("fromSearchResult", false));
                    fragment = CelebrityProfileFragment.newInstance(null, null);
                    fragment.setArguments(arguments);


                    break;
//                case 8030://MyClientFragment
//                    changeTitle(title);
//                    tvTitle.setTextColor(getResources().getColor(R.color.skyblue));
//                    toolbar.setBackgroundColor(getResources().getColor(R.color.white));
//                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
//                    imageView_notification.setColorFilter(ContextCompat.getColor(activity(), R.color.skyblueNew), PorterDuff.Mode.SRC_IN);
//                    tLayout.setVisibility(View.VISIBLE);
//                    arguments = new Bundle();
//                    arguments.putString("TalentPermission", intent.getStringExtra("TalentPermission"));
//
//                    fragment = MyClientFragment.newInstance(null, null);
//                    fragment.setArguments(arguments);
//                    break;
//
//                case 8031://MyPermissionsFragment
//                    changeTitle(title);
//                    tvTitle.setTextColor(getResources().getColor(R.color.skyblue));
//                    toolbar.setBackgroundColor(getResources().getColor(R.color.white));
//                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
//                    imageView_notification.setColorFilter(ContextCompat.getColor(activity(), R.color.skyblueNew), PorterDuff.Mode.SRC_IN);
//                    tLayout.setVisibility(View.VISIBLE);
//                    arguments = new Bundle();
//                    arguments.putString("TalentPermission", intent.getStringExtra("TalentPermission"));
//
//                    fragment = MyClientFragment.newInstance(null, null);
//                    fragment.setArguments(arguments);
//                    break;

                case 8032://MyFeedsFragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = FeedsFragment.newInstance(null, getIntent().getExtras().getString("CELEB_ID"));
                    break;

                case 9037: // create feed
                    // changeTitle("Create Post");
                    changeTitle("What's new");
                    tLayout.setVisibility(View.GONE);
                    fragment = CreateFeedFragment.newInstance(
                            getIntent().getParcelableArrayListExtra("GalleryFileList") != null ?
                                    getIntent().getParcelableArrayListExtra("GalleryFileList")
                                    : null);

                    break;
                case 9038: // edit feed with gallery
                    changeTitle("Edit");
                    tLayout.setVisibility(View.GONE);
                    fragment = EditFeedFragment.newInstance(getIntent().getParcelableArrayListExtra("GalleryFileList"), getIntent().getIntExtra("Position", -1));

                    break;

                case 9039: //custom gallery picker
                    changeTitle("Media");
                    tLayout.setVisibility(View.GONE);
                    if (Utility.checkPermissionRequest(Permission.READ_STORAGE, activity()) &&
                            Utility.checkPermissionRequest(Permission.WRITE_STORAGE, activity()))
                        if (getIntent().getParcelableArrayListExtra("MediaList") != null) {
                            fragment = GalleryFragment.newInstance(getIntent().getParcelableArrayListExtra("GalleryFileList"), getIntent().getParcelableArrayListExtra("MediaList"), getIntent().getStringExtra("isFrom"));
                        } else {
                            fragment = GalleryFragment.newInstance(getIntent().getParcelableArrayListExtra("GalleryFileList"), null, getIntent().getStringExtra("isFrom"));
                        }
                    else {
                        redirectMethod = "setUpFragment";
                        if (!Utility.checkPermissionRequest(Permission.WRITE_STORAGE, activity()))
                            Utility.raisePermissionRequest(Permission.WRITE_STORAGE, activity());
                        else if (!Utility.checkPermissionRequest(Permission.READ_STORAGE, activity()))
                            Utility.raisePermissionRequest(Permission.READ_STORAGE, activity());
                        else {

                        }
                    }
                    break;

                case 9040: // edit feed with media and gallery
                    changeTitle("Edit");
                    tLayout.setVisibility(View.GONE);
                    fragment = UpdateFeedFragment.newInstance(getIntent().getParcelableExtra("Feed"),
                            getIntent().getIntExtra("Position", -1));
                    break;
                case 9041: //Feed Advertisement click
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    arguments = new Bundle();
                    arguments.putString("FAQ", "FeedAdvertisement");
                    arguments.putString("FeedAdvertisementURL", intent.getStringExtra("FeedAdvertisementURL"));
                    fragment = FAQFragment.newInstance(null, null);
                    fragment.setArguments(arguments);
                    break;
                case 9042: //CreateStoriesFragment
                    changeTitle("");
                    tLayout.setVisibility(View.GONE);
                    rlLayout.setVisibility(View.GONE);

                    fragment = CreateStoriesFragment.newInstance(
                            getIntent().getParcelableExtra("GalleryFileList") != null ?
                                    getIntent().getParcelableExtra("GalleryFileList")
                                    : null, false, 0, true);
                    break;
                case 9043: //CreateScheduleFragment
                    changeTitle("");
                    tLayout.setVisibility(View.GONE);
                    rlLayout.setVisibility(View.GONE);
                    fragment = CreateScheduleFragment.newInstance();
                    break;
                case 9044: //ViewScheduleFragment
                    changeTitle("");
                    tLayout.setVisibility(View.GONE);
                    rlLayout.setVisibility(View.GONE);
                    fragment = ViewScheduleFragment.newInstance(getIntent().getStringExtra("scheduleID"), getIntent().getStringExtra("celebID"));
                    break;
                case 9045: //MulipleImageCreateStoryFragment
                    changeTitle("");
                    tLayout.setVisibility(View.GONE);
                    rlLayout.setVisibility(View.GONE);
                    fragment = MultipleImageCreateStoryFragment.newInstance(
                            getIntent().getParcelableArrayListExtra("GalleryFileList") != null ?
                                    getIntent().getParcelableArrayListExtra("GalleryFileList")
                                    : null);
                    break;
//                case 8033://ManagerSettingsFragment
//                    changeTitle(title);
//                    tvTitle.setTextColor(getResources().getColor(R.color.skyblue));
//                    toolbar.setBackgroundColor(getResources().getColor(R.color.white));
//                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
//                    tLayout.setVisibility(View.GONE);
//                    fragment = ManagerSettingsFragment.newInstance(null, null);
//                    break;

                case 8034://CreditsRecharge

                    changeTitle(title);
                    rlLayout.setVisibility(View.VISIBLE);
                    tLayout.setVisibility(View.GONE);


                    arguments = new Bundle();
                    arguments.putString("className", intent.getStringExtra("className"));
                    arguments.putString("refCartId", intent.getStringExtra("refCartId"));
                    arguments.putString("refCartIds", intent.getStringExtra("refCartIds"));


                    fragment = CreditsRecharge.newInstance(null, null);
                    fragment.setArguments(arguments);

                    break;

                case 8036://OrderFailed
                    changeTitle(title);
                    rlLayout.setVisibility(View.GONE);
                    arguments = new Bundle();

                    arguments.putString("transactionId", intent.getStringExtra("transactionId"));
                    arguments.putString("currencyamount", intent.getStringExtra("currencyamount"));

                    tLayout.setVisibility(View.GONE);
                    fragment = OrderFailed.newInstance();
                    fragment.setArguments(arguments);
                    break;


                case 8038://SelfProfile Fragment

//                    changeTitle(title);
//                    rlLayout.setVisibility(View.GONE);
//                    toolbar.setBackgroundColor(getResources().getColor(R.color.skyblue));
//                    tLayout.setVisibility(View.VISIBLE);
//                    imageView_notification.setVisibility(View.GONE);
//                    imageView_userIcon.setVisibility(View.GONE);
//                    //
//                    SharedPrefsUtil.setStringPreference(getApplicationContext(), "SELF_PROFILE", "TRUE");
//                    fragment = SelfProfileFragment.newInstance(null, null);


                    //New Screen changes Uday
                    /*changeTitle(title);
                    rlLayout.setVisibility(View.GONE);
                    btUpdate.setVisibility(View.VISIBLE);
                    btUpdate.setText("Edit");
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    arguments = new Bundle();
                    arguments.putString("HEADER_TITLE", title);

                    if (intent.getStringExtra("NEW_REG") != null && !intent.getStringExtra("NEW_REG").isEmpty()) {
                        Log.e("NEW_REG", intent.getStringExtra("NEW_REG") + "");
                        if (intent.getStringExtra("NEW_REG").equals("TRUE")) {
                            arguments.putString("NEW_REG", "TRUE");
                            btContinue.setVisibility(View.VISIBLE);
                            isNewReg = true;
                            arguments.putBoolean("reload", false);
                            btUpdate.setVisibility(View.GONE);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            getSupportActionBar().setHomeButtonEnabled(true);
                            tLayout.setVisibility(View.GONE);
                        } else {
                            arguments.putString("NEW_REG", "FALSE");
                            arguments.putBoolean("reload", true);
                            btContinue.setVisibility(View.GONE);
                            isNewReg = false;
                            tLayout.setVisibility(View.GONE);
                        }
                    } else {
                        arguments.putString("NEW_REG", "FALSE");
                        arguments.putBoolean("reload", true);
                        isNewReg = false;
                        btContinue.setVisibility(View.GONE);
                        tLayout.setVisibility(View.GONE);
                    }

                    fragment = EditProfileFragmentNew.newInstance(null, null);
                    fragment.setArguments(arguments);*/

                    changeTitle(title);
                    rlLayout.setVisibility(View.GONE);
                    arguments = new Bundle();
                    if (intent.getStringExtra("ClASS_NAME") != null && !intent.getStringExtra("ClASS_NAME").isEmpty()) {
                        arguments.putString("ClASS_NAME", intent.getStringExtra("ClASS_NAME"));
                    } else {
                        arguments.putString("ClASS_NAME", "");
                    }

                    arguments.putString("celeb_status", intent.getStringExtra("celeb_status"));
                    arguments.putString("CelebId", SessionManager.userLogin.userId);
                    arguments.putString("PROFILE_NAME", intent.getStringExtra("PROFILE_NAME"));
                    arguments.putBoolean("isUserProfile", intent.getBooleanExtra("isUserProfile", false));
                    arguments.putBoolean("fromSearchResult", intent.getBooleanExtra("fromSearchResult", false));
                    arguments.putBoolean("isCelebManagerProfile", intent.getBooleanExtra("isCelebManagerProfile", false));
                    arguments.putBoolean("isManagerProfile", intent.getBooleanExtra("isManagerProfile", false));
                    arguments.putBoolean("isSelf", true);
                    if (intent.getStringExtra("Imaage") != null && !intent.getStringExtra("Imaage").isEmpty()) {
                        arguments.putString("Imaage", intent.getStringExtra("Imaage"));
                    } else {
                        arguments.putString("Imaage", "");
                    }
                    arguments.putString("proffession", intent.getStringExtra("proffession"));
                    arguments.putString("ABOUT", intent.getStringExtra("ABOUT"));
                    arguments.putString("ClASS_TYPE", intent.getStringExtra("ClASS_TYPE"));

                    //IS_MANAGER ,MANAGER_ID
                    if (intent.getStringExtra("IS_MANAGER") != null && !intent.getStringExtra("IS_MANAGER").isEmpty()) {
                        arguments.putString("IS_MANAGER", intent.getStringExtra("IS_MANAGER"));
                        arguments.putString("MANAGER_ID", intent.getStringExtra("MANAGER_ID"));
                    }
                    fragment = CelebrityProfileFragment.newInstance(null, null);
                    fragment.setArguments(arguments);

                    break;

                case 8039:
                    changeTitle(title);
                    rlLayout.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);

                    fragment = OrderSuccess.newInstance(intent.getParcelableExtra("orderSuccessData"));

                    break;

                case 8040://MemberProfile View
                   /* changeTitle(title);
                    rlLayout.setVisibility(View.GONE);
                    arguments = new Bundle();
                    arguments.putString("POKE_USER_ID", intent.getStringExtra("CelebId"));
                    arguments.putString("ABOUT", intent.getStringExtra("ABOUT"));
                    if (intent.getStringExtra("Imaage") != null && !intent.getStringExtra("Imaage").isEmpty()) {
                        arguments.putString("Imaage", intent.getStringExtra("Imaage"));
                    } else {
                        arguments.putString("Imaage", "");
                    }

                    arguments.putString("celeb_status", intent.getStringExtra("celeb_status"));
                    arguments.putString("PROFILE_NAME", intent.getStringExtra("PROFILE_NAME"));
                    arguments.putString("proffession", intent.getStringExtra("proffession"));
                    if (intent.getStringExtra("IS_MANAGER") != null && !intent.getStringExtra("IS_MANAGER").isEmpty()) {
                        arguments.putString("IS_MANAGER", intent.getStringExtra("IS_MANAGER"));
                        arguments.putString("MANAGER_ID", intent.getStringExtra("MANAGER_ID"));
                    }
//                    fragment = MemberProfileViewFragment.newInstance(null, null);
                    fragment = MemberProfileDetailViewFragment.newInstance(null, null);
                    fragment.setArguments(arguments);*/
                    break;
                case 8041://SelFProfileFragment
                    changeTitle(title);
                    tLayout.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    arguments = new Bundle();
                    fragment = QuizProceedFragment.newInstance(null, null);
                    fragment.setArguments(arguments);
                    break;


                case 8044://OTPFragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);

//                    tvTitle.setTextColor(getResources().getColor(R.color.skyblue));
//                    toolbar.setBackgroundColor(getResources().getColor(R.color.white));
//                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
//                    imageView_notification.setColorFilter(ContextCompat.getColor(activity(), R.color.skyblueNew), PorterDuff.Mode.SRC_IN);
                    tLayout.setVisibility(View.VISIBLE);

                    arguments = new Bundle();
                    arguments.putString("managerId", intent.getStringExtra("managerId"));
                    arguments.putString("CelebrityName", intent.getStringExtra("CelebrityName"));
                    arguments.putString("isSubManager", intent.getStringExtra("isSubManager"));
                    fragment = OTPFragment.newInstance(null, null);
                    fragment.setArguments(arguments);
                    break;
                case 8045:
                    changeTitle("");
                    rlLayout.setVisibility(View.GONE);
                    fragment = SearchFragment.newInstance(null, null);
                    break;


//                case 8048://AudionsNew
//                    changeTitle(title);
//                    tLayout.setVisibility(View.VISIBLE);
//                    arguments = new Bundle();
//                    arguments.putString("FAQ", "Auditions");
//                    imageView_notification.setVisibility(View.GONE);
//                    imageView_userIcon.setVisibility(View.GONE);
//                    fragment = Auditions.newInstance(null, null);
//                    break;


                case 9007:
                    changeTitle(title);
                    /*toolbar.setBackgroundColor(getResources().getColor(R.color.skyblue));
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);*/
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    ibFavourite.setVisibility(View.GONE);
                    ibGridView.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    //    imageView_archive.setVisibility(View.GONE);
                    //   tvTitle.setTextColor(getResources().getColor(R.color.white));
                    fragment = ArchiveNotificationFragment.newInstance(null, null);
                    break;


                case 8100://Chat Tabs Fragment
                    changeTitle(title);
                    //toolbar.setBackgroundColor(getResources().getColor(R.color.white));
                    //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                    collapseSearchView();
                    //tvTitle.setTextColor(getResources().getColor(R.color.skyblue));
                    fragment = FragmentTabsChat.newInstance(getIntent().getParcelableExtra("chatDataConvertModel"), getIntent().getIntExtra("position", 0), true);
                    break;

                case 8101://New Chat List Fragment
                    changeTitle(title);
                    //toolbar.setBackgroundColor(getResources().getColor(R.color.white));
                    //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    //tvTitle.setTextColor(getResources().getColor(R.color.skyblue));
                    fragment = FragmentNewChatList.newInstance(null, null);
                    break;

                case 8102://Call Details Fragment
                    changeTitle(title);
                    //toolbar.setBackgroundColor(getResources().getColor(R.color.white));
                    //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    //tvTitle.setTextColor(getResources().getColor(R.color.skyblue));
                    fragment = FragmentCallDetails.newInstance(getIntent().getParcelableExtra("recentCallsModel"), null);
                    break;
                case 8065://Credit recharge payment Fragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = OnlineCelebViewAllFragment.newInstance(null);
                    break;

                case 8064:
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.GONE);
                    rlLayout.setVisibility(View.GONE);

                    fragment = PaymentOption.newInstance(getIntent().getParcelableExtra("packageSelectionDataObj"));
                    break;

                case 8066://Fragment Celeb Schedules
                    changeTitle(title);
                    //toolbar.setBackgroundColor(getResources().getColor(R.color.white));
                    //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    //tvTitle.setTextColor(getResources().getColor(R.color.skyblue));
                    fragment = FragmentCelebSchedules.newInstance(getIntent().getExtras().getString("CELEB_ID"), null);
                    break;


                case 8069://SingleFeedDetails
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = FeedDetailsFragment.newInstance(getIntent().getParcelableExtra("feed"), getIntent().getExtras().getInt("feedPosition", -1), getIntent().getExtras().getInt("mediaPosition", -1), true);
                    break;

                case 8070://FeedCommentsFragment
                    changeTitle(title);
                    //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                    //toolbar.setBackgroundColor(getResources().getColor(R.color.white));
                    //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = CommentsFragment.newInstance(getIntent().getParcelableExtra("feed"), getIntent().getExtras().getString("id", ""),
                            getIntent().getExtras().getBoolean("isFeed", false), getIntent().getExtras().getInt("feedPosition", -1), getIntent().getExtras().getInt("mediaPosition", -1),
                            getIntent().getExtras().getBoolean("isDetails", false),
                            getIntent().getExtras().getBoolean("isFromMediaDetailsFragment", false));
                    break;

                case 8071://MediaDetailsFragmen
                    rlLayout.setVisibility(View.GONE);

                    mediaDetailsFragment = MediaDetailsFragment.newInstance(getIntent().getStringExtra("mediaType"),
                            getIntent().getStringExtra("CelelbID"),
                            getIntent().getParcelableExtra("feed"),
                            getIntent().getParcelableArrayListExtra("media"),
                            getIntent().getExtras().getInt("mediaPosition", -1),
                            getIntent().getExtras().getBoolean("isSelf", false),
                            getIntent().getExtras().getBoolean("isMember", false),
                            true,
                            getIntent().getExtras().getInt("feedPosition", -1),
                            getIntent().getExtras().getString("isFromWhich", ""),
                            getIntent().getExtras().getBoolean("showBottomLikes", false));
                    fragment = mediaDetailsFragment;
                    break;

                case 8072://Preferences Fragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    fragment = PrefrencesExpentableListviewFragment.newInstance(getIntent().getExtras().getBoolean("isFromRegister", false), null);
                    break;
                case 9010://Preferences Fragment

                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = PrefrencesExpentableListviewFragment.newInstance(getIntent().getExtras().getBoolean("isFromRegister", false), null);
                    break;

                case 1001: //SignUpFragment
                    changeTitle(title);
                    rlLayout.setVisibility(View.GONE);
          /*          fragment = SignUpFragment.newInstance(getIntent().getExtras().getBoolean("isLogin", false), getIntent().getExtras().getString("userName"), getIntent().getExtras().getString("userId"),

                            getIntent().getExtras().getBoolean("isForgot", false), getIntent().getExtras().getBoolean("isChangePassword", false), getIntent().getExtras().getBoolean("forOTPVerification", false),
                            getIntent().getExtras().getBoolean("isMobile", false), getIntent().getExtras().getString("EmailOrMobile"),
                            getIntent().getExtras().getBoolean("isSocialNetwork", false), getIntent().getExtras().getString("socialNetworkEmailID"),getIntent().getExtras().getString("firstName"));*/
                    fragment = SignUpFragment.newInstance(getIntent().getExtras().getParcelable("signUpConditions"));
                    break;

                case 8073://Edit Profile Fragment
                    rlLayout.setVisibility(View.GONE);
                    fragment = EditProfileFragment.newInstance(null, null);
                    break;
                case 8074://Feed Settings Fragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    ivHomeImage.setVisibility(View.VISIBLE);
                    fragment = FeedSettingsFragment.newInstance(null, null);
                    break;
                case 8079://ActivityLogFragment
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.VISIBLE);
                    fragment = ActivityLogFragment.newInstance(null, null);
                    break;

                case 8080://Video Player Fragment
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    tLayout.setVisibility(View.GONE);
                    rlLayout.setVisibility(View.GONE);
                    llClose.setVisibility(View.GONE);
                    fragment = VideoPlayerFragment.newInstance(getIntent().getParcelableExtra("uri"),
                            getIntent().getExtras().getString("feedId", ""),
                            getIntent().getExtras().getString("mediaUnderScoreId", ""),
                            getIntent().getIntExtra("feedPosition", -1),
                            getIntent().getIntExtra("mediaPosition", -1),
                            getIntent().getExtras().getBoolean("isFeedEdit", false),
                            getIntent().getExtras().getString("isFromWhich", ""));
                    break;

                case 2020:
                    changeTitle(title);
                    imageView_notification.setVisibility(View.GONE);
                    imageView_userIcon.setVisibility(View.GONE);
                    rlLayout.setVisibility(View.GONE);
                    tLayout.setVisibility(View.GONE);
                    fragment = StoriesViewPager.newInstance(getIntent().getExtras().getInt("POSITION"),
                            getIntent().getParcelableArrayListExtra("celeblist"));
                    break;
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commitAllowingStateLoss();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int fragmentKey = getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_camera:
                openCameraOptions();
                break;
            case R.id.menu_done:
                controllerMediaDone();
                break;
            case R.id.menu_add_gallery:
                if (fragmentKey == 9038)
                    isEdit = true;
                openCustomGallery();
                break;
//            case R.id.audition_notification:
//                if (fragmentKey == 8099)
//                    if (getSupportFragmentManager().getFragments().get(0) instanceof AuditionNotificationFragment) {
//                        AuditionNotificationFragment auditionNotificationFragment = (AuditionNotificationFragment) getSupportFragmentManager().getFragments().get(0);
//                        auditionNotificationFragment.getArchivedData();
//                    }
//                break;
            case R.id.menu_send:
                if (fragmentKey == 9037) {

                    List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                    for (Fragment fragment : fragmentList) {
                        if (fragment instanceof CreateFeedFragment) {
                            CreateFeedFragment createFeedFragment = (CreateFeedFragment) fragment;
                            createFeedFragment.verifyFeedUpload();
                        }
                    }

                }
                break;
            case R.id.menu_update:
                if (fragmentKey == 9040) {

                    List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                    for (Fragment fragment : fragmentList) {
                        if (fragment instanceof UpdateFeedFragment) {
                            UpdateFeedFragment updateFeedFragment = (UpdateFeedFragment) fragment;
                            updateFeedFragment.verifyFeed();
                        }
                    }

                }
                break;


            case R.id.menu_media_done:
                sendMedia();
                break;

            case R.id.audition_notification:
                break;
        }

        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int fragmentKey = getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);
        switch (fragmentKey) {

            case 8056:
                getMenuInflater().inflate(R.menu.menu_audition_create_profile, menu);
                menuAuditionProfileEdit = menu.findItem(R.id.menu_audition_edit);
                menuAuditionProfileEdit.setVisible(false);
                try {
                    SpannableString s = new SpannableString("EDIT");
                    s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.sky_blue)), 0, s.length(), 0);
                    menuAuditionProfileEdit.setTitle(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case 3001:
                getMenuInflater().inflate(R.menu.menu_media_picker, menu);
                menuMediaDone = menu.findItem(R.id.menu_media_done);
                Common.getInstance().setMenuItemColor(getResources().getColor(R.color.color222), menuMediaDone);
                menuMediaDone.setVisible(false);

                break;

            case 3003:
                getMenuInflater().inflate(R.menu.menu_media_picker, menu);
                menuMediaDone = menu.findItem(R.id.menu_media_done);
                Common.getInstance().setMenuItemColor(getResources().getColor(R.color.color222), menuMediaDone);
                menuMediaDone.setVisible(false);

            case 9037: // feed create
                getMenuInflater().inflate(R.menu.feed_menu, menu);
                menuShare = menu.findItem(R.id.menu_send);
                Common.getInstance().setMenuItemColor(getResources().getColor(R.color.color222), menuShare);
                menuShare.setVisible(false);
                break;

            case 8099:// Audition Notifications
                getMenuInflater().inflate(R.menu.audition_notifications, menu);
                menuArchive = menu.findItem(R.id.audition_notification);
                menuArchive.setVisible(true);
                break;

//            case 8076:// Audition Drafts Search
//                getMenuInflater().inflate(R.menu.audition_search, menu);
//                menuSearch = menu.findItem(R.id.audition_search);
//                menuSearch.setVisible(true);
//                break;

            case 9038:// feed edit
                getMenuInflater().inflate(R.menu.feed_edit_menu, menu);
                menuDone = menu.findItem(R.id.menu_done);
                menuDone.setVisible(false);
                menuUpdate = menu.findItem(R.id.menu_update);
                menuUpdate.setVisible(false);
                Common.getInstance().setMenuItemColor(getResources().getColor(R.color.color222), menuUpdate);
                break;

//            case 8002:// Edit Profile
//                getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
//                menuDone = menu.findItem(R.id.menu_done);
//                menuDone.setVisible(false);
//                menuUpdate = menu.findItem(R.id.menu_update);
//                menuUpdate.setTitle("Edit");
//                menuUpdate.setVisible(true);
//                break;

            case 9039: // gallery
                getMenuInflater().inflate(R.menu.gallery_menu, menu);
                menuDone = menu.findItem(R.id.menu_done);
                Common.getInstance().setMenuItemColor(getResources().getColor(R.color.color222), menuDone);
                menuDone.setVisible(false);
                break;

            case 9040: // feed edit with gallery and media
                getMenuInflater().inflate(R.menu.feed_edit_menu, menu);
                menuDone = menu.findItem(R.id.menu_done);
                menuDone.setVisible(false);
                menuUpdate = menu.findItem(R.id.menu_update);
                Common.getInstance().setMenuItemColor(getResources().getColor(R.color.color222), menuUpdate);
                menuUpdate.setVisible(false);
                break;


        }

        return super.onCreateOptionsMenu(menu);
    }

    public String getMediaTitle(Integer type) {
        if (type == 2) {
            return "Audios";
        } else if (type == 3) {
            return "Videos";
        } else if (type == 1) {
            return "Photos";
        } else if (type == 0) {
            return "Documents";
        } else {
            return "Media";
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    private void setUp() {
        btContinue = findViewById(R.id.btContinue);
        fragmentLayoutBottomSheet = findViewById(R.id.fragmentLayoutBottomSheet);
        llClose = findViewById(R.id.llClose);
        rlLayout = findViewById(R.id.rlLayout);
        ivCancel = findViewById(R.id.ivCancel);
        btUpdate = (Button) findViewById(R.id.btUpdate);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        toolbar = findViewById(R.id.toolbar);
        btPreferenceSave = findViewById(R.id.btPreferenceSave);
        btPreferenceSave.setVisibility(View.GONE);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_header_back);

        ivHomeImage = findViewById(R.id.ivHomeImage);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        frameLayoutHelper = findViewById(R.id.frameLayoutHelper);
        frameLayoutBottom = findViewById(R.id.frameLayoutBottom);
        frameLayout = findViewById(R.id.frameLayout);
        imageView_notification = (ImageView) findViewById(R.id.imageView_notification);
        imageView_userIcon = (CircleImageView) findViewById(R.id.imageView_userIcon);

        textViewSaveChanges = findViewById(R.id.textViewSaveChanges);
        ibFavourite = findViewById(R.id.ibFavourite);
        ibGridView = findViewById(R.id.ibGridView);
        ibSelectAll = findViewById(R.id.ibSelectAll);
        ibUnSelectAll = findViewById(R.id.ibUnSelectAll);
        ibDelete = findViewById(R.id.ibDelete);
        searchView = findViewById(R.id.searchView);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setVisibility(View.GONE);
        /*try {
            ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
            searchIcon.setImageDrawable(ContextCompat.getDrawable(activity(),R.drawable.ic_search));
            //
            SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.white));
            searchAutoComplete.setTextColor(getResources().getColor(android.R.color.white));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
//        imageView_back = findViewById(R.id.imageView_back);

        tvClearAll = findViewById(R.id.tvClearAll);

        imageView_archive = findViewById(R.id.imageView_archive);
        celebListIcon = findViewById(R.id.celebListIcon);


        ivHomeImage.setOnClickListener(view -> {
            try {
                int fragmentKey = getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);
                if (fragmentKey == 8056) {
                } else {
                    Intent intent = new Intent(HelperActivity.this, BottomMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        bottomSheetBehavior = BottomSheetBehavior.from(fragmentLayoutBottomSheet);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN || newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    showStatusBar();
                    setOrientation(true);
                    try {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottom, DummyFragment.newInstance(null, null)).commit();
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        frameLayoutBottom.setLayoutParams(layoutParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (FeedsFragment.getInstance() != null && getFragmentKey() == 8032) {
                        FeedsFragment.getInstance().resumeVideoPlayer();
                    }
                    if (Common.getInstance().getVideoPlayerFragment() != null) {
                        Common.getInstance().getVideoPlayerFragment().resumeVideoPlayer();
                    }
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    if (FeedsFragment.getInstance() != null) {
                        FeedsFragment.getInstance().stopVideoPlayer(false);
                    }
                    if (Common.getInstance().getVideoPlayerFragment() != null) {
                        Common.getInstance().getVideoPlayerFragment().pauseVideoPlayer();
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        tvTitle = findViewById(R.id.tvTitle);
        tLayout = findViewById(R.id.tLayout);

        if (SessionManager.userLogin.profilePic != null
                && !SessionManager.userLogin.profilePic.isEmpty()) {
            Glide.with(getBaseContext()).load(ApiClient.BASE_URL + SessionManager.userLogin.profilePic)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_profile_circle_pleace_holder)
                    .into(imageView_userIcon);
        } else {
            imageView_userIcon.setImageResource(R.drawable.ic_profile_circle_pleace_holder);
        }


        imageView_notification.setOnClickListener(v -> {

            if (SessionManager.userLogin.userId != null
                    && !SessionManager.userLogin.userId.isEmpty()) {
                if (SessionManager.userLogin.profilePic != null
                        && !SessionManager.userLogin.profilePic.isEmpty()) {
                }
            }

        });

        celebListIcon.setOnClickListener(v -> {
            final PopupWindow popupWindow = new PopupWindow(this);
            LayoutInflater inflater = (LayoutInflater) activity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.popup_my_clients, null);
            youLinearLayout = (LinearLayout) view.findViewById(R.id.youLinearLayout);
            Intent intent = getIntent();
            if (intent.getBooleanExtra("isSelf", false)) {
                youLinearLayout.setVisibility(View.GONE);
            } else {
                youLinearLayout.setVisibility(View.VISIBLE);
            }

            youLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SessionManager.userLogin.userId != null && !SessionManager.userLogin.userId.isEmpty()) {
                    }


                }
            });
            CircleImageView managerImage = (CircleImageView) view.findViewById(R.id.managerImage);
            if (SessionManager.userLogin.profilePic != null
                    && !SessionManager.userLogin.profilePic.isEmpty()) {
                Glide.with(activity())
                        .load(ApiClient.BASE_URL + SessionManager.userLogin.profilePic)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_profile_square_pleace_holder)
                        .into(managerImage);
            }
            TextView managerName = (TextView) view.findViewById(R.id.managerName);
            managerName.setText("You");


            myclientRecyclerView = (RecyclerView) view.findViewById(R.id.myclientRecyclerView);


            popupWindow.setFocusable(true);
            popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            popupWindow.setContentView(view);


            popupWindow.showAsDropDown(v);


        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatingProfileInServer();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                    for (Fragment fragment : fragmentList) {
                        if (fragment instanceof CharitySettingsFragment) {
                            CharitySettingsFragment charitySettingsFragment = (CharitySettingsFragment) fragment;
                            charitySettingsFragment.showBtnSave(true);
                            showBtnEdit(false);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        llClose.setOnClickListener(click -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        });


        imageView_userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                if (intent.getBooleanExtra("isSelf", false)) {
                    navigateToSelfProfile();
                }
            }
        });

        imageView_archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToArchive();
            }
        });

        ibSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (CalenderSlotListfragment.getInstance() != null) {
                    CalenderSlotListfragment.getInstance().selectAll(true);
                } else if (NotificationsFragment.getInstance() != null) {
                    NotificationsFragment.getInstance().selectAll();
                }*/

                int fragmentKey = getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);
                if (fragmentKey == 8009) {
                    if (CalenderSlotListfragment.getInstance() != null) {
                        CalenderSlotListfragment.getInstance().selectAll(true);
                    }
                } else if (fragmentKey == 8019) {
                    if (NotificationsFragment.getInstance() != null) {
                        NotificationsFragment.getInstance().selectAll();
                    }
                }

            }
        });

        ibUnSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fragmentKey = getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);
                if (fragmentKey == 8009) {
                    if (CalenderSlotListfragment.getInstance() != null) {
                        CalenderSlotListfragment.getInstance().selectAll(false);
                    }
                } else if (fragmentKey == 8019) {
                    if (NotificationsFragment.getInstance() != null) {
                        NotificationsFragment.getInstance().selectAll();
                    }
                }

            }
        });

        ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "";

                int fragmentKey = getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);
                if (fragmentKey == 8009) {
                    if (ibUnSelectAll.getVisibility() == View.VISIBLE) {
                        if (CalenderSlotListfragment.getInstance() != null) {
                            message = "Are you sure, you want to delete all the schedules?";
                        }
                    } else if (CalenderSlotListfragment.getInstance() != null) {
                        message = "Are you sure, you want to delete the selected schedule?";
                    }
                } else if (fragmentKey == 8019) {
                    if (ibUnSelectAll.getVisibility() == View.VISIBLE) {
                        if (NotificationsFragment.getInstance() != null) {
                            message = "Are you sure, you want to delete all the notifications?";
                        }
                    } else if (NotificationsFragment.getInstance() != null) {
                        message = "Are you sure, you want to delete the selected notifications?";
                    }
                }
                new AlertDialog.Builder(activity())
                        .setTitle("Confirmation")
                        .setMessage(message)
                        .setCancelable(true)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                int fragmentKey = getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);
                                if (fragmentKey == 8009) {
                                    if (CalenderSlotListfragment.getInstance() != null) {
                                        CalenderSlotListfragment.getInstance().deleteAll();
                                    }
                                } else if (fragmentKey == 8019) {
                                    if (NotificationsFragment.getInstance() != null) {
                                        NotificationsFragment.getInstance().deleteAll();
                                    }
                                }
                            }
                        })
                        .create()
                        .show();
            }
        });

        ibGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        searchView.setOnCloseListener(new androidx.appcompat.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tvTitle.setVisibility(View.VISIBLE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTitle.setVisibility(View.GONE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        });
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tvTitle.setVisibility(View.GONE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                tvTitle.setVisibility(View.GONE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                try {
                    if (Common.getInstance().getFragmentChatList() != null && Common.getInstance().getFragmentChatList().getFragVisibility()) {
                        Common.getInstance().getFragmentChatList().doSearch(query);
                    } else if (Common.getInstance().getFragmentCallsList() != null && Common.getInstance().getFragmentCallsList().getFragVisibility()) {
                        Common.getInstance().getFragmentCallsList().doSearch(query);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        btPreferenceSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlMenuItems();
            }
        });
    }

    public int getFragmentKey() {
        return getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);
    }

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


    private void navigateToArchive() {
        /*Intent intent = new Intent(HelperActivity.this, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "Archive");
        intent.putExtra(Constants.FRAGMENT_KEY, 9007);
        startActivity(intent);*/
        Intent intent = new Intent(activity(), HelperActivity.class);
        intent.putExtra("title", "Archive");
        intent.putExtra("isSelf", true);
        intent.putExtra("isFromArchive", true);
        intent.putExtra(Constants.FRAGMENT_KEY, 8019);// NotificationsFragment
        startActivity(intent);
    }

    public void changeListGridImage(Boolean isGrid) {
        if (ibGridView != null) {
            if (isGrid) {
                ibGridView.setImageResource(R.drawable.ic_grid_view);
            } else {
                ibGridView.setImageResource(R.drawable.ic_list_view);
            }
        }
    }


    private void updatingProfileInServer() {
    }

    private void navigateToSelfProfile() {
        Intent intent = new Intent(HelperActivity.this, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "My Profile");
        intent.putExtra(Constants.FRAGMENT_KEY, 8038);
        startActivity(intent);
    }

    @Override
    public void changeTitle(String title) {
        tvTitle.setText(title);
    }


    @Override
    public void showSnackBar(String snackBarText, int type) {
        Utility.showSnackBar(activity(), coordinatorLayout, snackBarText, type);
    }


    @Override
    public Activity activity() {
        return this;
    }

    public void showUpdate(String update) {
        btUpdate.setVisibility(View.VISIBLE);
        btUpdate.setText(update);
        if (update.equals("success")) {
            finish();
        }
    }

    public void editFeed(Feed feed, int position) {
        Intent intent = new Intent(activity(), HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_KEY, 9040);
        intent.putExtra("Feed", feed);
        intent.putExtra("Mine", true);
        intent.putExtra("Position", position);
        startActivityForResult(intent, Utility.generateRequestCodes().get("CONTENT_FEED_EDIT"));
    }

    public void openFeedOptnions(String myPostData, int position, Feed feed) {
        llClose.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottom, OptionsFragment.newInstance(myPostData, position, feed)).commit();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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

    public void openFeedLikes(Feed feed, String id, boolean isFeed, int feedPosition, int mediaPosition, boolean isDetails) {
        llClose.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottom, LikesFragment.newInstance(feed, id, isFeed, feedPosition, mediaPosition, isDetails)).commit();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void openFeedOptions(Feed feed, int position, int type) {
        llClose.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottom, info.sumantv.flow.ipoll.fragments.options.OptionsFragment.newInstance(feed, position, type)).commit();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

   /* public void openManagerPermissionOptions(String ID, String IDASST, String permissionName, String isManagerOrAsstManager, String managerId, String permisionType) {
        llClose.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottom, ManagerOnClick.newInstance(ID, IDASST, permissionName, isManagerOrAsstManager, managerId, permisionType)).commit();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }*/

    private void sendMedia() {

    }

    public void controllerMediaDone() {
        int fragmentKey = getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);
        switch (fragmentKey) {
            case 9038: // feed edit
                if (getSupportFragmentManager().getFragments().get(0) instanceof EditFeedFragment) {
                    EditFeedFragment editFeedFragment = (EditFeedFragment) getSupportFragmentManager().getFragments().get(0);
                    if (editFeedFragment.galleryFileList != null) {
                        Intent returnData = new Intent(activity(), HelperActivity.class);
                        returnData.putParcelableArrayListExtra("GalleryFileList", (ArrayList<? extends Parcelable>) editFeedFragment.galleryFileList);
                        setResult(Activity.RESULT_OK, returnData);
                        finish();
                    } else {
                        finish();
                    }
                }
                break;
            case 9039: // gallery
                if (getSupportFragmentManager().getFragments().get(0) instanceof GalleryFragment) {
                    GalleryFragment galleryFragment = (GalleryFragment) getSupportFragmentManager().getFragments().get(0);
                    if (galleryFragment.selectedItems != null && galleryFragment.selectedItems.size() > 0) {
                        Intent returnData = new Intent(activity(), HelperActivity.class);
                        returnData.putParcelableArrayListExtra("GalleryFileList", (ArrayList<? extends Parcelable>) galleryFragment.selectedItems);
                        setResult(Activity.RESULT_OK, returnData);

                        finish();
                    } else {
                        showSnackBar("Please select items", 2);
                    }
                }
                break;
            case 8059:// ManagerAditionalProfile Profile
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

                break;
        }
    }

    public void openCustomGallery() {
        if (Utility.checkPermissionRequest(Permission.READ_STORAGE, activity()) && Utility.checkPermissionRequest(Permission.WRITE_STORAGE, activity())) {
            if (getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0) == 9040) {
              /*  Intent intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_KEY, 9039);// gallery
                intent.putParcelableArrayListExtra("MediaList", (ArrayList<? extends Parcelable>) getMediaFilesList());
                intent.putParcelableArrayListExtra("GalleryFileList", (ArrayList<? extends Parcelable>) getGalleryFilesList());
                intent.putExtra("isFrom", "Feed");
                activity().startActivityForResult(intent, Utility.generateRequestCodes().get("EXIST_FEED_EDIT"));
                return;*/
                images = new ArrayList<>();
                List<Media> mediaSize = getMediaFilesList();
                int mediaCount = 20;
                if (mediaSize != null && mediaSize.size() > 0) {
                    mediaCount = 20 - mediaSize.size();
                }
                ImagePicker.with(HelperActivity.this)
                        .setFolderMode(true)
                        .setCameraOnly(false)
                        .setFolderTitle("Album")
                        .setMultipleMode(true)
                        .setRequestCode(Utility.generateRequestCodes().get("EXIST_FEED_EDIT"))
                        .setMaxSize(mediaCount)
                        .setSelectedImages(images)
                        .setBackgroundColor("#212121")
                        .setAlwaysShowDoneButton(false)
                        .setKeepScreenOn(true)
                        .setIsFeed(true)
                        .start();
                return;

            }
            /*Intent intent = new Intent(activity(), HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_KEY, 9039);// gallery
            intent.putParcelableArrayListExtra("GalleryFileList", (ArrayList<? extends Parcelable>) getGalleryFilesList());
            intent.putExtra("isFrom", "Feed");
            activity().startActivityForResult(intent, Utility.generateRequestCodes().get(isEdit ? "ADD_MEDIA_FEED_EDIT" : "FEED_EDIT"));*/
            if (getGalleryFilesList() != null) {
                ImagePicker.with(this)
                        .setFolderMode(true)
                        .setCameraOnly(false)
                        .setFolderTitle("Album")
                        .setMultipleMode(true)
                        .setRequestCode(Utility.generateRequestCodes().get(isEdit ? "ADD_MEDIA_FEED_EDIT" : "FEED_EDIT"))
                        .setSelectedImages(getGalleryFilesList())
                        .setMaxSize(20)
                        .setIsFeed(true)
                        .setBackgroundColor("#212121")
                        .setAlwaysShowDoneButton(false)
                        .setKeepScreenOn(true)
                        .start();
            } else {
                ImagePicker.with(this)
                        .setFolderMode(true)
                        .setCameraOnly(false)
                        .setFolderTitle("Album")
                        .setMultipleMode(true)
                        .setRequestCode(Utility.generateRequestCodes().get(isEdit ? "ADD_MEDIA_FEED_EDIT" : "FEED_EDIT"))
                        .setSelectedImages(images)
                        .setMaxSize(20)
                        .setBackgroundColor("#212121")
                        .setAlwaysShowDoneButton(false)
                        .setIsFeed(true)
                        .setKeepScreenOn(true)
                        .start();
            }

            isEdit = false;
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

    public ArrayList<Image> getGalleryFilesList() {
        int fragmentKey = getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);
        switch (fragmentKey) {
            case 9037: // feed create
                if (getSupportFragmentManager().getFragments().get(0) instanceof CreateFeedFragment) {
                    CreateFeedFragment createFeedFragment = (CreateFeedFragment) getSupportFragmentManager().getFragments().get(0);
                    if (createFeedFragment.gallerySelectedList != null && createFeedFragment.gallerySelectedList.size() > 0) {
                        return createFeedFragment.gallerySelectedList;
                    }
                }
                break;
            case 9038:// feed edit
                if (getSupportFragmentManager().getFragments().get(0) instanceof EditFeedFragment) {
                    EditFeedFragment editFeedFragment = (EditFeedFragment) getSupportFragmentManager().getFragments().get(0);
                    if (editFeedFragment.galleryFileList != null && editFeedFragment.galleryFileList.size() > 0) {
                        return editFeedFragment.galleryFileList;
                    }
                }
            case 9040:
                if (getSupportFragmentManager().getFragments().get(0) instanceof UpdateFeedFragment) {
                    UpdateFeedFragment updateFeedFragment = (UpdateFeedFragment) getSupportFragmentManager().getFragments().get(0);
                    if (updateFeedFragment.galleryFileList != null && updateFeedFragment.galleryFileList.size() > 0) {
                        return updateFeedFragment.galleryFileList;
                    }
                }
                break;

        }

        return null;
    }

    public List<Media> getMediaFilesList() {
        int fragmentKey = getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);
        switch (fragmentKey) {
            case 9040:
                if (getSupportFragmentManager().getFragments().get(0) instanceof UpdateFeedFragment) {
                    UpdateFeedFragment updateFeedFragment = (UpdateFeedFragment) getSupportFragmentManager().getFragments().get(0);
                    if (updateFeedFragment.mediaList != null && updateFeedFragment.mediaList.size() > 0) {
                        return updateFeedFragment.mediaList;
                    }
                }
                break;
        }

        return null;
    }

    @Override
    public void onBackPressed() {
        int fragmentKey = getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            return;
        } else if (fragmentKey == 8068 || fragmentKey == 8072) {
            finish();
            return;
        } else if (fragmentKey == 8070) {
            List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragmentList) {
                if (fragment instanceof CommentsFragment) {
                    CommentsFragment commentsFragment = (CommentsFragment) fragment;
                    Intent returnData = new Intent(activity(), BottomMenuActivity.class);
                    returnData.putExtra("feed", commentsFragment.getUpdateFeed());
                    returnData.putExtra("feedPosition", commentsFragment.getFeedPosition());
                    setResult(Activity.RESULT_OK, returnData);
                }
            }
            finish();
            return;
        } else if (frameLayoutHelper.getVisibility() == View.VISIBLE) {
            frameLayoutHelper.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutHelper, DummyFragment.newInstance(null, null)).commit();
        } else {
            if (fragmentKey == 8069) {
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof FeedDetailsFragment) {
                        FeedDetailsFragment feedDetailsFragment = (FeedDetailsFragment) fragment;
                        Intent returnData = new Intent(activity(), BottomMenuActivity.class);
                        returnData.putExtra("feed", feedDetailsFragment.getUpdateFeed());
                        Log.e("ReturnPosition", feedDetailsFragment.getFeedPosition() + "");
                        returnData.putExtra("feedPosition", feedDetailsFragment.getFeedPosition());
                        setResult(Activity.RESULT_OK, returnData);
                    }
                }
                finish();
                return;
            } else if (fragmentKey == 8071) {
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof MediaDetailsFragment) {
                        MediaDetailsFragment mediaDetailsFragment = (MediaDetailsFragment) fragment;
                        Intent returnData = new Intent(activity(), HelperActivity.class);
                        returnData.putExtra("feed", mediaDetailsFragment.getUpdateFeed());
                        returnData.putExtra("feedPosition", mediaDetailsFragment.getFeedPosition());
                        setResult(Activity.RESULT_OK, returnData);
                    }
                }
                finish();
                return;
            } else if (fragmentKey == 9037) {
                if (getSupportFragmentManager().getFragments().get(0) instanceof CreateFeedFragment) {
                    CreateFeedFragment createFeedFragment = (CreateFeedFragment) getSupportFragmentManager().getFragments().get(0);
                    if (createFeedFragment.gallerySelectedList != null && createFeedFragment.gallerySelectedList.size() > 0) {
                        Utility.openCustomAlertDialog(activity(), "Discard", "Do you want to discard this post?", true,
                                this);
                        return;
                    }
                }
            } else if (fragmentKey == 9040) {
                if (menuUpdate != null && menuUpdate.isVisible()) {
                    Utility.openCustomAlertDialog(activity(), "Discard", "Do you want to discard this post?", true, this);
                    return;
                }
            } else if (fragmentKey == 1001) {
                if (SignUpFragment.getInstance() != null) {
                    if (SignUpFragment.getInstance().isChangePasswordBack) {
                        SignUpFragment.getInstance().setVisibilityForAllLayout(7);
                        SignUpFragment.getInstance().isChangePasswordBack = false;
                        return;
                    } else if (SignUpFragment.getInstance().isChangePasswordBackOTP) {
                        SignUpFragment.getInstance().setVisibilityForAllLayout(7);
                        SignUpFragment.getInstance().isChangePasswordBackOTP = false;
                        return;
                    } else if (SignUpFragment.getInstance().isForgotBack) {
                        SignUpFragment.getInstance().setVisibilityForAllLayout(6);
                        SignUpFragment.getInstance().isForgotBack = false;
                        return;
                    } else if (SignUpFragment.getInstance().isForgotBackDetails) {
                        SignUpFragment.getInstance().setVisibilityForAllLayout(9);
                        SignUpFragment.getInstance().isForgotBackDetails = false;
                        SignUpFragment.getInstance().isForgotBack = true;
                        return;
                    }
                }
            }
            if (fragmentKey == 8002) {
                if (isNewReg) {
                    return;
                } else {
                    finish();
                }

            }
            if (fragmentKey == 8024) {
                finish();
            }
            if (fragmentKey == 8025) {
                finish();
            }
            if (fragmentKey == 8026) {
                finish();
            }

            if (fragmentKey == 8026) {
                finish();
            }


            if (fragmentKey == 8017) {
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof CharitySettingsFragment) {
                        try {
                            CharitySettingsFragment charitySettingsFragment = (CharitySettingsFragment) fragment;
                            if (charitySettingsFragment.isBtnSaveEnabled()) {
                                new AlertDialog.Builder(this)
                                        .setTitle("CelebKonect")
                                        .setMessage("Are you sure, you want to discard the changes?")
                                        .setCancelable(true)
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        })
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                                finish();
                                            }
                                        })
                                        .create()
                                        .show();
                                //
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

//            if (fragmentKey == 8044) {
//                try {
//                    if (Managing_celeb_or_sub_manger_list.getInstance() != null) {
//                        Managing_celeb_or_sub_manger_list.getInstance().getManagerList();
//                    }
//                }catch (Exception e ){
//
//                }
//            }
            if (fragmentKey == 8029) {
                Intent returnData = new Intent(activity(), BottomMenuActivity.class);
                returnData.putExtra("isFollow", true);
                setResult(Activity.RESULT_OK, returnData);
                finish();
            }


            if (fragmentKey == 8036) {
                return;
            } else if (fragmentKey == 8037) {
                return;
            } else if (fragmentKey == 8039) {
                return;
            } else if (fragmentKey == 8024) {
                if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_TERMS_CONDITION_ALREADY_ACCESS, "") != null && !SessionManager.getInstance().getKeyValue(SessionManager.KEY_TERMS_CONDITION_ALREADY_ACCESS, "").isEmpty()) {
                    if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_TERMS_CONDITION_ALREADY_ACCESS, "").equals("TRUE")) {
                        startActivity(new Intent(HelperActivity.this, BottomMenuActivity.class));
                        finish();
                    }
                }
                return;
            } else if (fragmentKey == 8019) {
//                ServiceSockets.getInstance().noticationCountEmit();

                SocketForAppUtill.getInstance().noticationCountEmit();

                finish();
            } else if (fragmentKey == 9043) {

                if (CreateScheduleFragment.getInstance() != null) {
                    if (CreateScheduleFragment.getInstance().isCallCharges) {
                        CreateScheduleFragment.getInstance().setLayoutVisibility("LLScheduleDuration");
                        CreateScheduleFragment.getInstance().isCallCharges = false;
                        return;
                    } else if (CreateScheduleFragment.getInstance().isScheduleDuration) {
                        CreateScheduleFragment.getInstance().setLayoutVisibility("LLServiceTimings");
                        CreateScheduleFragment.getInstance().isScheduleDuration = false;
                        return;
                    } else if (CreateScheduleFragment.getInstance().isServiceTimings) {
                        CreateScheduleFragment.getInstance().setLayoutVisibility("LLCreateSchedule");
                        CreateScheduleFragment.getInstance().isServiceTimings = false;
                        return;
                    } else if (CreateScheduleFragment.getInstance().isCreateSchedule) {
                        finish();
                    }
                    finish();
                }
            } else {
                finish();
            }
        }
        if (fragmentKey == 8037) {

        }

        if (fragmentKey == 2020) {
            Log.e("checkBack2020", true + "");
            if (StoriesFragment.getInstance() != null) {
                StoriesFragment.getInstance().getStoriesAllSeenStatus();
            }
            return;
        }


        try {
            if (getIntent().getExtras().getString("title") != null && !getIntent().getExtras().getString("title").isEmpty()) {
                if (getIntent().getExtras().getString("title").equals("My Schedule")) {
                    if (getIntent().getExtras().getString("COMING_PAGE") != null && !getIntent().getExtras().getString("COMING_PAGE").isEmpty()) {
                        if (getIntent().getExtras().getString("COMING_PAGE").equals("SUCCESS_ACTIVITY")) {
                            startActivity(new Intent(HelperActivity.this, BottomMenuActivity.class));
                            finish();
                        }
                    }
                }


            }
        } catch (Exception e) {

        }
        clearFragmentInstances();
    }

    public void openCameraOptions() {
        llClose.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottom, CameraOptionsFragment.newInstance(false, null)).commit();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void openCameraSnap() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        if (Utility.checkPermissionRequest(Permission.CAMERA, activity())) {
            Intent snapIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (snapIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = Utility.createImageFile(activity());
                    imageFile = photoFile;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoUri = FileProvider.getUriForFile(activity(), Constants.FILE_PROVIDER_AUTHORITY, photoFile);
                    snapIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    snapIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(snapIntent, Utility.generateRequestCodes().get("SNAP_FROM_CAMERA"));
                }
            }
        } else {
            redirectMethod = "openCameraSnap";
            Utility.raisePermissionRequest(Permission.CAMERA, activity());
        }
    }

    public void openCameraVideo() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        if (Utility.checkPermissionRequest(Permission.CAMERA, activity()) && Utility.checkPermissionRequest(Permission.RECORD, activity())) {
            Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            startActivityForResult(videoIntent, Utility.generateRequestCodes().get("VIDEO_FROM_CAMERA"));
        } else {
            redirectMethod = "openCameraVideo";
            if (!Utility.checkPermissionRequest(Permission.CAMERA, activity()))
                Utility.raisePermissionRequest(Permission.CAMERA, activity());
            else if (!Utility.checkPermissionRequest(Permission.RECORD, activity()))
                Utility.raisePermissionRequest(Permission.RECORD, activity());
            else {

            }
        }
    }

    public void showUpdateFeed() {
        if (menuUpdate != null)
            menuUpdate.setVisible(true);
    }

    public void hideUpdateFeed() {
        if (menuUpdate != null)
            menuUpdate.setVisible(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Logger.d("Request Code", "" + requestCode);
        if (requestCode == Utility.generateRequestCodes().get("READ_STORAGE_REQUEST")) {
            if (Utility.checkPermissionRequest(Permission.READ_STORAGE, activity())) {
                reDirect(redirectMethod);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
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
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Utility.showToast(activity(), Constants.GIVE_ALL_PERMISSIONS, false);
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }
                showSnackBar("Permission denied", 2);
            }
        } else if (requestCode == Utility.generateRequestCodes().get("CAMERA_REQUEST")) {
            if (Utility.checkPermissionRequest(Permission.CAMERA, activity())) {
                reDirect(redirectMethod);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        Utility.showToast(activity(), Constants.GIVE_ALL_PERMISSIONS, false);
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }
                showSnackBar("Permission denied", 2);
            }
        } else if (requestCode == Utility.generateRequestCodes().get("RECORD_REQUEST")) {
            if (Utility.checkPermissionRequest(Permission.RECORD, activity())) {
                reDirect(redirectMethod);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                        Utility.showToast(activity(), Constants.GIVE_ALL_PERMISSIONS, false);
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }
                showSnackBar("Permission denied", 2);
            }
        } else {

        }
    }

    private void addPictureToGallery() {
        MediaScannerConnection.scanFile(
                getApplicationContext(),
                new String[]{imageFile.getAbsolutePath()},
                null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {

                        refreshCustomGallery();
                    }
                });
    }

    public void refreshCustomGallery() {
        int fragmentKey = getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);
        switch (fragmentKey) {
            case 9039:
                if (getSupportFragmentManager().getFragments().get(0) instanceof GalleryFragment) {
                    GalleryFragment galleryFragment = (GalleryFragment) getSupportFragmentManager().getFragments().get(0);
                    galleryFragment.refresh();
                }
        }
    }

    private void reDirect(String redirectMethod) {
        if (redirectMethod == null) return;

        switch (redirectMethod) {
            case "openCameraSnap":
                openCameraSnap();
                break;

            case "openCameraVideo":
                openCameraVideo();
                break;

            case "openCustomGallery":
                openCustomGallery();
                break;

            case "setUpFragment":
                setUpFragment();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("REQUEST CODE", "" + requestCode);
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);

        }
        if (requestCode == Utility.generateRequestCodes().get("MEDIA_FROM_GALLERY")) {
            if (data != null && resultCode == RESULT_OK) {
                if (getIntent().getStringExtra("isFrom").equalsIgnoreCase("CreateStories")) {
                    if (getSupportFragmentManager().getFragments().get(0) instanceof CreateFeedFragment) {

                    }
                } else {
                    if (getSupportFragmentManager().getFragments().get(0) instanceof CreateFeedFragment) {
                        CreateFeedFragment createFeedFragment = (CreateFeedFragment) getSupportFragmentManager().getFragments().get(0);
                        createFeedFragment.updateMediaPost(data.getParcelableArrayListExtra(Config.EXTRA_IMAGES));
                    }
                }
            } else {

            }
        } else if (requestCode == Utility.generateRequestCodes().get("UPDATE_FEED_DATA")) {
            if (data != null && resultCode == RESULT_OK) {
                Feed feed = data.getParcelableExtra("feed");
                int feedPosition = data.getIntExtra("feedPosition", -1);
                if (feed != null) {
                    updateFeed(feed, feedPosition);
                    updateFeedDetails(feed);
                }
            }
        } else if (requestCode == Utility.generateRequestCodes().get("FEED_EDIT")) {
            if (data != null && resultCode == RESULT_OK) {
                if (getSupportFragmentManager().getFragments().get(0) instanceof CreateFeedFragment) {
                    if (data.getParcelableArrayListExtra("GalleryFileList") != null) {
                        CreateFeedFragment createFeedFragment = (CreateFeedFragment) getSupportFragmentManager().getFragments().get(0);
                        createFeedFragment.updateMediaPost(data.getParcelableArrayListExtra("GalleryFileList"));
                    } else {
                        CreateFeedFragment createFeedFragment = (CreateFeedFragment) getSupportFragmentManager().getFragments().get(0);
                        createFeedFragment.updateMediaPost(data.getParcelableArrayListExtra(Config.EXTRA_IMAGES));
                    }
                }
            } else {

            }
        } else if (requestCode == Utility.generateRequestCodes().get("ADD_MEDIA_FEED_EDIT")) {

            if (data != null && resultCode == RESULT_OK) {
                if (getSupportFragmentManager().getFragments().get(0) instanceof EditFeedFragment) {
                    EditFeedFragment editFeedFragment = (EditFeedFragment) getSupportFragmentManager().getFragments().get(0);
                    editFeedFragment.updateMediaPost(data.getParcelableArrayListExtra(Config.EXTRA_IMAGES));
                }
            } else {

            }
        } else if (requestCode == Utility.generateRequestCodes().get("EXIST_FEED_EDIT")) {
            if (data != null) {

                if (data != null && resultCode == RESULT_OK) {
                    if (getSupportFragmentManager().getFragments().get(0) instanceof UpdateFeedFragment) {
                        UpdateFeedFragment updateFeedFragment = (UpdateFeedFragment) getSupportFragmentManager().getFragments().get(0);
                        updateFeedFragment.updateMediaPost(data.getParcelableArrayListExtra(Config.EXTRA_IMAGES));
                    }
                } else {

                }
            }
        } else if (requestCode == Utility.generateRequestCodes().get("SNAP_FROM_CAMERA")) {
            if (resultCode == RESULT_OK) {
                addPictureToGallery();
            }
        } else if (requestCode == Utility.generateRequestCodes().get("VIDEO_FROM_CAMERA")) {
            if (resultCode == RESULT_OK) {
                refreshCustomGallery();
            }
        } else if (requestCode == Utility.generateRequestCodes().get("OPEN_PLACES_SEARCH")) {


        } else if (requestCode == Utility.generateRequestCodes().get("CONTENT_FEED_EDIT")) {
            if (data != null && resultCode == RESULT_OK) {
                Common.getInstance().createUpdateFeed(data.getParcelableExtra("Feed"), data.getIntExtra("FeedPosition", 0), true);
            }
        } else if (requestCode == Utility.generateRequestCodes().get("ADD_MEDIA_FOR_PROFILE")) {
            if (data != null && resultCode == RESULT_OK) {
                //uploadProfileMedia(data.getParcelableArrayListExtra("GalleryFileList"));
            }
        } else if (requestCode == Utility.generateRequestCodes().get("MAIN_EXISTING_MEDIA_PICKER")) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

            }
        } else if (requestCode == Utility.generateRequestCodes().get("ADD_MEDIA_TO_STORY")) {
            if (data != null && resultCode == Activity.RESULT_OK) {
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof MultipleImageCreateStoryFragment) {
                        MultipleImageCreateStoryFragment mulipleImageCreateStoryFragment = (MultipleImageCreateStoryFragment) fragment;
                        mulipleImageCreateStoryFragment.mediaUpdate(data.getParcelableArrayListExtra(Config.EXTRA_IMAGES));
                    }
                }
            }
        } else if (requestCode == Utility.generateRequestCodes().get("ADD_MEDIA_TO_STORY_FOR_SINGLE_IMAGE")) {
            if (data != null && resultCode == Activity.RESULT_OK) {
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof MultipleImageCreateStoryFragment) {
                        MultipleImageCreateStoryFragment mulipleImageCreateStoryFragment = (MultipleImageCreateStoryFragment) fragment;
                        mulipleImageCreateStoryFragment.mediaUpdate(data.getParcelableArrayListExtra(Config.EXTRA_IMAGES));
                    }
                }
            }
        }


    }


    @Override
    public void doPositiveAction() {
        activity().finish();
    }

    @Override
    public void doNegativeAction() {

    }


    public void enableNotificationIcons(boolean enable) {
        if (enable) {
            ibDelete.setVisibility(View.VISIBLE);
            ibSelectAll.setVisibility(View.VISIBLE);
            ibUnSelectAll.setVisibility(View.VISIBLE);
        } else {
            ibDelete.setVisibility(View.GONE);
            ibSelectAll.setVisibility(View.GONE);
            ibUnSelectAll.setVisibility(View.GONE);
        }
    }

    public void setSelectAllIcon(boolean allSelected) {
        if (allSelected) {
            ibSelectAll.setVisibility(View.GONE);
            ibUnSelectAll.setVisibility(View.VISIBLE);
        } else {
            ibSelectAll.setVisibility(View.VISIBLE);
            ibUnSelectAll.setVisibility(View.GONE);
        }
    }


    public void openProfileImageComplete(String imageUrl) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        frameLayoutBottom.setLayoutParams(layoutParams);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottom, CompleteImage.newInstance(imageUrl)).commit();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    /*public void openVideoPlayer(Uri uri, Feed feed, Media media, int feedPosition, int mediaPosition) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        frameLayoutBottom.setLayoutParams(layoutParams);
        llClose.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottom, VideoPlayerFragment.newInstance(uri, feed, media, feedPosition, mediaPosition)).commit();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }*/


    public void changeProfileImage(String profilePic) {
        if (profilePic != null && !profilePic.isEmpty()) {
            if (SessionManager.userLogin.profilePic != null
                    && !SessionManager.userLogin.profilePic.isEmpty()) {
                Glide.with(getApplicationContext()).load(ApiClient.BASE_URL + SessionManager.userLogin.profilePic)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_grey_celebkonect_logo)
                        .into(imageView_userIcon);
            } else {
                imageView_userIcon.setImageResource(R.drawable.ic_grey_celebkonect_logo);
            }
        }


    }


    public void openNotificationDeleteBottomLayout(NotificationDeleteBottomModel notificationDeleteBottomModel) {
        FragmentNotificationDeleteBottom fragmentNotificationDeleteBottom = new FragmentNotificationDeleteBottom();
        fragmentNotificationDeleteBottom.setData(notificationDeleteBottomModel);
        fragmentNotificationDeleteBottom.show(getSupportFragmentManager(), "FragmentNotificationDeleteBottom");
    }

    public void openScheduleDeleteBottomLayout(CalenderSlot calenderSlot, int position, Context context) {
        FragmentScheduleDeleteBottom fragmentScheduleDeleteBottom = new FragmentScheduleDeleteBottom();
        fragmentScheduleDeleteBottom.setData(calenderSlot, context, position);
        fragmentScheduleDeleteBottom.show(getSupportFragmentManager(), "FragmentScheduleDeleteBottom");
    }


    public void slotCreateSuccess() {
        if (getSupportFragmentManager().getFragments().get(0) instanceof CalenderSlotListfragment) {
            onBackPressed();
            CalenderSlotListfragment calenderSlotListfragment = (CalenderSlotListfragment) getSupportFragmentManager().getFragments().get(0);
            calenderSlotListfragment.createAdapter();
            calenderSlotListfragment.allScheduleList(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Common.getInstance().setHelperActivity(HelperActivity.this);
        //
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                int fragmentKey = getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);
                if (fragmentKey > 0) {
                    switch (fragmentKey) {
                        case 8100://Chat Tabs Fragment
                            try {
                                tLayout.setVisibility(View.VISIBLE);
                                searchView.setVisibility(View.VISIBLE);
                                collapseSearchView();
                                tvTitle.setVisibility(View.VISIBLE);
                                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                                break;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                    }
                }
            }
        });
    }

    private void clearFragmentInstances() {
        int fragmentKey = getIntent().getIntExtra(Constants.FRAGMENT_KEY, 0);
        if (fragmentKey <= 0) {
            return;
        }
        switch (fragmentKey) {
            case 8100://Chat Tabs Fragment
                Common.getInstance().setFragmentTabsChat(null);
                Common.getInstance().setFragmentChatList(null);
                Common.getInstance().setFragmentCallsList(null);
                break;

            case 8101://New Chat List Fragment
                Common.getInstance().setFragmentNewChatList(null);
                break;

            case 8102://Call Details Fragment
                break;

            case 8029://CelebrityProfileFragment
                Common.getInstance().setCelebrityProfileFragment(null);
                break;

            case 8097:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearFragmentInstances();
        GlideFaceDetector.releaseDetector();
        FrescoFaceDetector.releaseDetector();
    }


    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.LOGINDETAILS_URL)) {
            try {
                Type type = new TypeToken<GetProfileData>() {
                }.getType();
                GetProfileData profileData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                if (profileData != null) {
                    if (profileData.getUsername() != null && !profileData.getUsername().isEmpty()) {
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_USER_NAME, profileData.getUsername());
                    }
                    if (profileData.get_id() != null && !profileData.get_id().isEmpty()) {
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_USER_ID, profileData.get_id());
                    }

                    if (profileData.getMobileNumber() != null && !profileData.getMobileNumber().isEmpty()) {
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_MOBILE_NO, profileData.getMobileNumber());
                    }

                    if (profileData.getLiveStatus().equals("online")) {
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_LIVE_STATUS, "TRUE");
                    } else {
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_LIVE_STATUS, "FALSE");
                    }

                    if (profileData.getFirstName() != null && !profileData.getFirstName().isEmpty()) {
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRST_NAME, profileData.getFirstName());

                    }

                    if (profileData.getLastName() != null && !profileData.getLastName().isEmpty()) {
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_LAST_NAME, profileData.getLastName());
                    } else {
                    }

                    if (profileData.getEmail() != null && !profileData.getEmail().isEmpty()) {
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, profileData.getEmail());
                    }

                    try {
                        if (profileData.getAvtar_imgPath() != null && !profileData.getAvtar_imgPath().isEmpty()) {
                            String profileImg = ApiClient.BASE_URL + profileData.getAvtar_imgPath();

                            SessionManager.getInstance().setKeyValue(SessionManager.KEY_PROFILE_PIC, Common.getInstance().IsNullReturnValue(profileData.getAvtar_imgPath(), ""));

                        } else {
                            SessionManager.getInstance().setKeyValue(SessionManager.KEY_PROFILE_PIC, "");
                        }


                    } catch (Exception e) {
                    }


                    if (profileData.getAboutMe() != null && !profileData.getAboutMe().isEmpty()) {
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_ABOUT, profileData.getAboutMe());
                    }

                    if (profileData.getCeleb()) {
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_CELEB, true);
                    } else {
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_CELEB, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equalsIgnoreCase("getMyClient")) {

        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        //
    }

   /* public void braineTreeIntialize(String tokenId, String totalAmount, String countryCode) {
        try {
            mBraintreeFragment = BraintreeFragment.newInstance(this, tokenId);
            // mBraintreeFragment is ready to use!
            PayPalRequest request = new PayPalRequest(totalAmount).currencyCode(countryCode).intent(PayPalRequest.INTENT_AUTHORIZE);
            PayPal.requestOneTimePayment(mBraintreeFragment, request);
        } catch (InvalidArgumentException e) {
            // There was an issue with your authorization string.
        }

    }*/


   /* @Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        String nonce = paymentMethodNonce.getNonce();
        *//*if (paymentMethodNonce instanceof PayPalAccountNonce) {
            PayPalAccountNonce payPalAccountNonce = (PayPalAccountNonce) paymentMethodNonce;
            Log.e("paypalEmail", payPalAccountNonce.getEmail() + "");
            // Access additional information
            String email = payPalAccountNonce.getEmail();
            String firstName = payPalAccountNonce.getFirstName();
            String lastName = payPalAccountNonce.getLastName();
            String phone = payPalAccountNonce.getPhone();
            // See PostalAddress.java for details
            PostalAddress billingAddress = payPalAccountNonce.getBillingAddress();
            PostalAddress shippingAddress = payPalAccountNonce.getShippingAddress();
        }*//*

        if (PaymentOption.getInstance() != null) {
            Toast.makeText(getApplicationContext(), "Paypal Payment", Toast.LENGTH_SHORT).show();
            PaymentOption.getInstance().LLMainLayout.setVisibility(View.GONE);
            PaymentOption.getInstance().progress_bar.setVisibility(View.VISIBLE);
            PaymentOption.getInstance().postNonceToServer(nonce);
        }
    }*/

    public void showBlockError(String errorMsg) {
        showSnackBar(errorMsg, 2);
    }
}
