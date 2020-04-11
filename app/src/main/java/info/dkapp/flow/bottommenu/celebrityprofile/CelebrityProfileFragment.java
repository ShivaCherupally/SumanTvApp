package info.dkapp.flow.bottommenu.celebrityprofile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import info.dkapp.flow.appmanagers.feed.models.Media;
import info.dkapp.flow.ModelClass.FanUnFanData;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.bottommenu.models.EditProfileUserDetails;
import info.dkapp.flow.celebflow.Fragments.BrandsFragment;
import info.dkapp.flow.celebflow.Fragments.PhotosFragment;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.celebflow.constants.Permission;
import info.dkapp.flow.chat.models.ChatDataConvertModel;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;
import info.dkapp.flow.utils.WrapContentHeightViewPager;
import retrofit2.Call;

public class CelebrityProfileFragment extends Fragment implements IFragment, TabLayout.OnTabSelectedListener, View.OnClickListener, IApiListener {
    private Button fanBtn, follow_button, btnRefresh, btEditprofile;
    private boolean isFollowClick = false;
    private WrapContentHeightViewPager viewPager;
    private String aboutMe_fromServer = "", profileId = "";
    private ViewPagerAdapter adapter;
    private CircleImageView ivProfileImage;
    private ImageView imageViewCoverPic, ivToolbarBg;
    private ImageView ivChatIcon, video_call_icon, audio_call_icon, backComplete, ivOnlineCircle;
    private LinearLayout iLself_fan_layout, iLself_following_layout, iLfan_following_layout, lLEditprofile, llTabsParent, fan_follow_buttons_layout, service_layout, posts_layout, fans_layout, followers_layout, total_layout, schedules_layout, llToolbarTitle, llTopContent, llTabsParentAnimated, llMoveUp;
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;
    private TextView tv_self_fan_count, tv_self_textViewFan, tv_self_following_count,
            tv_self_textViewFollowing, tvAboutMeMore, tvAboutMe, tvProfession, tvProfileName, tvProfessionToolbar,
            tvProfileNameToolbar, posts_count, followers_count, textViewFollowers, textViewPosts, textViewFans,
            schedules_count, fans_count, activetime;
    private int mFans_count, mFollowers_count, self_fan_count, self_following_count;
    private Boolean isMember = false, isCeleb = false, isManager = false, isCelebManager = false;
    private ArrayList<Media> memberMediaList, memberVideoList;
    private Bundle arguments;
    private ApiInterface apiInterface;
    private IApiListener iApiListener;
    private static CelebrityProfileFragment instance;
    private boolean followStatus = false, isSelf = false;
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS_CALL = 2;
    private EditProfileUserDetails editProfileUserDetailsOrg;
    private boolean fanStatusGlobal = false;
    private Double fanCreditCharge, audioCreditCharge, videoCreditCharge;
    private String[] tabTitles;
    private View userDivider;
    private int textBottom = 0, textTop = 0, selectedTabPosition = 0, llTopContentHeight = 0, toolbarHeight = 0;
    private RelativeLayout rlContainer;
    private NestedScrollView nestedScrollView;
    private RecyclerView rvTabs, rvTabsAnimated;
    private CustomTabAdapter customTabAdapter, customTabAdapterAnimated;
    private PhotosFragment photosFragment, videosFragment;


    public static CelebrityProfileFragment newInstance(String param1, String param2) {
        CelebrityProfileFragment fragment = new CelebrityProfileFragment();
        return fragment;
    }

    public static CelebrityProfileFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.celebrity_view_profile, null);
        Common.getInstance().setCelebrityProfileFragment(this);
        textBottom = getResources().getDimensionPixelSize(R.dimen._15sdp);
        textTop = getResources().getDimensionPixelSize(R.dimen._10sdp);
        instance = this;
        iApiListener = this;
        arguments = getArguments();

        initializeViews(root);
        initializeActions();
        //setUpAmazingAvatar();

        if (arguments.getString("CelebId") != null && !arguments.getString("CelebId").isEmpty()) {
            profileId = arguments.getString("CelebId");
            getProfileDetails(profileId, false);
        } else {
            ifProfileDataNotFound();
        }
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProfileDetails(profileId, true);
            }
        });
        /*if(profileId.equals(SessionManager.userLogin.userId)){
            schedules_layout.setVisibility(View.GONE);
        }else {
            schedules_layout.setVisibility(View.VISIBLE);
        }*/
        audio_call_icon.setOnClickListener(this);
        video_call_icon.setOnClickListener(this);
        ivChatIcon.setOnClickListener(this);

        fanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FanUnFanData fanUnFanData = new FanUnFanData(activity(), editProfileUserDetailsOrg.getUserDetails().getId(), fanCreditCharge, editProfileUserDetailsOrg.getUserDetails().getFirstName(), editProfileUserDetailsOrg.getUserDetails().getLastName(), editProfileUserDetailsOrg.getUserDetails().getAvtarImgPath(), editProfileUserDetailsOrg.getUserDetails().getProfession());
                if (fanBtn.getText().toString().equals("Fanned")) {
                    Common.getInstance().fanUnFanConfirmationDialog(false, fanUnFanData, iApiListener);
                } else {
                    Common.getInstance().fanUnFanConfirmationDialog(true, fanUnFanData, iApiListener);
                }
            }
        });
        IApiListener iApiListenerFollowUnFollow = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                if (condition.equals(ApiClient.FOLLOW_CELEB)) {
                    try {
                        followStatus = true;
                        follow_button.setText("Following");
                        follow_button.setTextColor(getResources().getColor(R.color.dark_grey));
                        follow_button.setBackground(getResources().getDrawable(R.drawable.submit_rectangle_for_settings_empty_drakgrey_5dp));
                        Common.getInstance().cusToast(activity(), "You are now a follower of " + editProfileUserDetailsOrg.getUserDetails().getFirstName() + " " + editProfileUserDetailsOrg.getUserDetails().getLastName());
                        isFollowClick = false;
                        mFollowers_count = mFollowers_count + 1;
                        followers_count.setText(String.valueOf(mFollowers_count));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (condition.equals(ApiClient.UNFOLLOW_CELEB)) {
                    try {
                        followStatus = false;
                        follow_button.setText("Follow");
                        follow_button.setTextColor(getResources().getColor(R.color.white));
                        follow_button.setBackground(getResources().getDrawable(R.drawable.submit_rectangle_normal_rad));
                        Common.getInstance().cusToast(activity(), "You have stopped following " + editProfileUserDetailsOrg.getUserDetails().getFirstName() + " " + editProfileUserDetailsOrg.getUserDetails().getLastName());
                        isFollowClick = false;
                        mFollowers_count = mFollowers_count - 1;
                        followers_count.setText(String.valueOf(mFollowers_count));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                //Toast.makeText(activity(), "please check your network and try again", Toast.LENGTH_SHORT).show();
            }
        };
        follow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Common.getInstance().followUnFollow(activity(), follow_button.getText().toString().equals("Follow"),
                            editProfileUserDetailsOrg.getUserDetails().getId(), true, iApiListenerFollowUnFollow);
            }
        });
        btEditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_TITLE, "");
                intent.putExtra(Constants.FRAGMENT_KEY, 8073);// Editprofile Fragment
                startActivity(intent);
            }
        });
        tvAboutMeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvAboutMeMore.getText().toString().equals("more")) {
                    tvAboutMe.setMaxLines(Integer.MAX_VALUE);
                    tvAboutMeMore.setText("less");
                } else {
                    tvAboutMe.setMaxLines(3);
                    tvAboutMeMore.setText("more");
                }
            }
        });
        return root;
    }

    private void ifProfileDataNotFound() {
        Common.getInstance().cusToast(activity(), "Data not found");
        getActivity().finish();
    }

    private void setActiveTabPosition(int position){
        selectedTabPosition = position;
        customTabAdapter.notifyDataSetChanged();
        customTabAdapterAnimated.notifyDataSetChanged();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setTabs() {
        rvTabs.setAdapter(customTabAdapter = new CustomTabAdapter());
        rvTabsAnimated.setAdapter(customTabAdapterAnimated = new CustomTabAdapter());
        //
        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        setActiveTabPosition(position);
                    }
                });
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), tabTitles.length);
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        viewPager.setAdapter(adapter);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            int dragthreshold = 30;
            int downX;
            int downY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getRawX();
                        downY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int distanceX = Math.abs((int) event.getRawX() - downX);
                        int distanceY = Math.abs((int) event.getRawY() - downY);
                        if (distanceY > distanceX && distanceY > dragthreshold) {
                            viewPager.getParent().requestDisallowInterceptTouchEvent(false);
                            nestedScrollView.getParent().requestDisallowInterceptTouchEvent(true);
                        } else if (distanceX > distanceY && distanceX > dragthreshold) {
                            viewPager.getParent().requestDisallowInterceptTouchEvent(true);
                            nestedScrollView.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        nestedScrollView.getParent().requestDisallowInterceptTouchEvent(false);
                        viewPager.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }

    public void getProfileDetails(String celebid, boolean isRefresh) {
        if (!Common.checkInternetConnection(activity())) {
            Toast.makeText(activity(), Constants.PLEASE_CHECK_INTERNET, Toast.LENGTH_SHORT).show();
            return;
        }
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getCelebrityCompleteData(ApiClient.GET_CELEBRITY_COMPLETE_DATA + celebid + "/" + SessionManager.userLogin.userId);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_CELEBRITY_COMPLETE_DATA, true, iApiListener, true));
    }

    private void initializeActions() {
        backComplete.setOnClickListener(this);
        posts_layout.setOnClickListener(this);

        iLself_following_layout.setOnClickListener(this);
        iLself_fan_layout.setOnClickListener(this);

        fans_layout.setOnClickListener(this);
        schedules_layout.setOnClickListener(this);
        followers_layout.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    private void initializeViews(View root) {
        userDivider = root.findViewById(R.id.userDivider);
        llTabsParent = root.findViewById(R.id.llTabsParent);
        lLEditprofile = root.findViewById(R.id.lLEditprofile);
        iLself_fan_layout = root.findViewById(R.id.iLself_fan_layout);
        iLself_following_layout = root.findViewById(R.id.iLself_following_layout);
        iLfan_following_layout = root.findViewById(R.id.iLfan_following_layout);
        service_layout = root.findViewById(R.id.service_layout);
        fan_follow_buttons_layout = root.findViewById(R.id.fan_follow_buttons_layout);
        backComplete = root.findViewById(R.id.backComplete);
        btnRefresh = root.findViewById(R.id.refreshImageview);
        textViewFollowers = root.findViewById(R.id.textViewFollowers);
        textViewPosts = root.findViewById(R.id.textViewPosts);
        textViewFans = root.findViewById(R.id.textViewFans);
        tv_self_fan_count = root.findViewById(R.id.tv_self_fan_count);
        tv_self_textViewFan = root.findViewById(R.id.tv_self_textViewFan);
        tv_self_following_count = root.findViewById(R.id.tv_self_following_count);
        tv_self_textViewFollowing = root.findViewById(R.id.tv_self_textViewFollowing);
        ivProfileImage = root.findViewById(R.id.imageviewceleb);
        imageViewCoverPic = root.findViewById(R.id.imageViewCoverPic);
        tvProfession = root.findViewById(R.id.proffesion);
        tvProfileName = root.findViewById(R.id.profile_name);
        tvProfessionToolbar = root.findViewById(R.id.tvProfessionToolbar);
        tvProfileNameToolbar = root.findViewById(R.id.tvProfileNameToolbar);
        tvAboutMe = root.findViewById(R.id.tvAboutMe);
        tvAboutMeMore = root.findViewById(R.id.tvAboutMeMore);
        fanBtn = root.findViewById(R.id.fanBtn);
        follow_button = root.findViewById(R.id.follow_button);
        btEditprofile = root.findViewById(R.id.btEditprofile);
        viewPager = root.findViewById(R.id.pager);
        ivChatIcon = root.findViewById(R.id.chaticons);
        posts_count = root.findViewById(R.id.posts_count);
        fans_count = root.findViewById(R.id.fans_count);
        followers_count = root.findViewById(R.id.followers_count);
        posts_layout = root.findViewById(R.id.posts_layout);
        fans_layout = root.findViewById(R.id.fans_layout);
        total_layout = root.findViewById(R.id.total_layout);
        followers_layout = root.findViewById(R.id.followers_layout);
        schedules_layout = root.findViewById(R.id.schedules_layout);
        schedules_count = root.findViewById(R.id.schedules_count);
        video_call_icon = root.findViewById(R.id.video_call_icon);
        audio_call_icon = root.findViewById(R.id.audio_call_icon);
        llToolbarTitle = root.findViewById(R.id.llToolbarTitle);
        llTopContent = root.findViewById(R.id.llTopContent);
        llTabsParentAnimated = root.findViewById(R.id.llTabsParentAnimated);
        llMoveUp = root.findViewById(R.id.llMoveUp);
        rlContainer = root.findViewById(R.id.rlContainer);
        ivToolbarBg = root.findViewById(R.id.ivToolbarBg);
        nestedScrollView = root.findViewById(R.id.nestedScrollView);
        rvTabsAnimated = root.findViewById(R.id.rvTabsAnimated);
        rvTabs = root.findViewById(R.id.rvTabs);

        activetime = root.findViewById(R.id.activetime);
        ivOnlineCircle = root.findViewById(R.id.ivOnlineCircle);
        //
        rvTabs.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.HORIZONTAL, false));
        rvTabsAnimated.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.HORIZONTAL, false));
        llMoveUp.setVisibility(View.GONE);
        llTabsParentAnimated.setVisibility(View.GONE);
        ivToolbarBg.setAlpha(0f);
        if (Build.VERSION.SDK_INT > 23) {
            checkCalendarPermission();
        }
        //
        ivProfileImage.setOnClickListener(this);
        imageViewCoverPic.setOnClickListener(this);
        llMoveUp.setOnClickListener(this);
        //
        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                setProfileAnimation(scrollY);
                if (scrollY > (llTopContentHeight + Utility.getScreenHeight(activity()))) {
                    llMoveUp.setVisibility(View.VISIBLE);
                } else {
                    llMoveUp.setVisibility(View.GONE);
                }
                if (nestedScrollView.getChildAt(0).getBottom() <= (nestedScrollView.getHeight() + nestedScrollView.getScrollY())) {
                    if (photosFragment != null && photosFragment.getFragVisibleToUser()) {
                        photosFragment.recyclerLoadMore();
                    } else if (videosFragment != null && videosFragment.getFragVisibleToUser()) {
                        videosFragment.recyclerLoadMore();
                    }
                }
            }
        });
    }

    private class CustomTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public CustomTabAdapter() {
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TabViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tab_adapter, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof TabViewHolder) {
                TabViewHolder viewHolder = (TabViewHolder) holder;
                viewHolder.itemView.getLayoutParams().width = Utility.getScreenWidth(activity()) / tabTitles.length;
                //
                viewHolder.tvTitle.setText(tabTitles[position]);
                if (position == selectedTabPosition) {
                    viewHolder.tvTitle.setTextColor(getResources().getColor(R.color.skyblueNew));
                } else {
                    viewHolder.tvTitle.setTextColor(getResources().getColor(R.color.color222));
                }
                //
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedTabPosition = position;
                        viewPager.setCurrentItem(position);
                        notifyDataSetChanged();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return tabTitles.length;
        }

        private class TabViewHolder extends RecyclerView.ViewHolder {
            public TextView tvTitle;

            private TabViewHolder(View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tvTitle);
            }
        }
    }

    private void setProfileAnimation(int verticalOffset) {
        int rlContainerHeight = rlContainer.getMeasuredHeight();
        int svHeight = nestedScrollView.getChildAt(0).getHeight();
        llTopContentHeight = llTopContent.getHeight();
        float percentage;
        percentage = (float) Math.abs(verticalOffset) / (float) llTopContentHeight;
        int ivProfileImageHeight = ivProfileImage.getLayoutParams().height;
        int resizedValue = (int) (ivProfileImageHeight * (percentage * 1.5));
        int finalHeight = ivProfileImageHeight - resizedValue;
        toolbarHeight = getResources().getDimensionPixelSize(R.dimen._44sdp);
        int toolbarDiff = 0;
        int withoutToolbar = (rlContainerHeight - toolbarHeight) + getResources().getDimensionPixelSize(R.dimen._4sdp);
        if (verticalOffset > withoutToolbar) {
            toolbarDiff = verticalOffset - withoutToolbar;
            if (toolbarDiff > toolbarHeight) {
                toolbarDiff = toolbarHeight;
            }
        }
        /*Log.d("handle:percentage", "" + percentage + " verticalOffset " + verticalOffset + " llTopContentHeight " + llTopContentHeight + " finalHeight " + finalHeight + " rlContainerHeight " + rlContainerHeight + " toolbarDiff " + toolbarDiff + " svHeight " + svHeight);*/
        //
        if (verticalOffset < llTopContentHeight) {
            //setting image animation
            if (finalHeight > 100) {
                RelativeLayout.LayoutParams layoutParams_ = (RelativeLayout.LayoutParams) ivProfileImage.getLayoutParams();
                layoutParams_.width = finalHeight;
                layoutParams_.setMargins(getResources().getDimensionPixelSize(R.dimen._10sdp), -getResources().getDimensionPixelSize(R.dimen._40sdp), 0, 0);
                ivProfileImage.setLayoutParams(layoutParams_);
            }
            //setting toolbar
            if (toolbarDiff > 0) {
                int finalDiff = toolbarHeight - toolbarDiff;//setting toolbar minus margin
                LinearLayout.LayoutParams layoutParams_ = (LinearLayout.LayoutParams) llToolbarTitle.getLayoutParams();
                layoutParams_.setMargins(0, finalDiff, 0, 0);
                llToolbarTitle.setLayoutParams(layoutParams_);
            } else {
                LinearLayout.LayoutParams layoutParams_ = (LinearLayout.LayoutParams) llToolbarTitle.getLayoutParams();
                layoutParams_.setMargins(0, toolbarHeight, 0, 0);
                llToolbarTitle.setLayoutParams(layoutParams_);
            }
            //setting toolbar background
            if (verticalOffset <= 0) {
                ivToolbarBg.setAlpha(0f);
            } else {
                float rlContainerMovedPercentage = ((verticalOffset + toolbarHeight) * 100) / rlContainerHeight;
                if (rlContainerMovedPercentage <= 100) {
                    ivToolbarBg.setAlpha((float) rlContainerMovedPercentage / 100);
                } else if (rlContainerMovedPercentage > 100) {
                    ivToolbarBg.setAlpha(1f);
                }
            }
        }
        //setting tabLayout
        if (llTabsParent.getVisibility() == View.VISIBLE) {
            if (verticalOffset > (llTopContentHeight - toolbarHeight)) {
                /*int diff = verticalOffset - (llTopContentHeight-toolbarHeight);
                if(diff > toolbarHeight){
                    diff = toolbarHeight;
                }
                RelativeLayout.LayoutParams layoutParams_ = (RelativeLayout.LayoutParams) llTabsParentAnimated.getLayoutParams();
                layoutParams_.setMargins(0, diff, 0, 0);
                llTabsParentAnimated.setLayoutParams(layoutParams_);*/
                llTabsParentAnimated.setVisibility(View.VISIBLE);
            } else {
                /*RelativeLayout.LayoutParams layoutParams_ = (RelativeLayout.LayoutParams) llTabsParentAnimated.getLayoutParams();
                layoutParams_.setMargins(0, 0, 0, 0);
                llTabsParentAnimated.setLayoutParams(layoutParams_);*/
                llTabsParentAnimated.setVisibility(View.GONE);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void profileDetails() {
        tabTitles = new String[]{};
        if (arguments.getBoolean("fromSearchResult", false)) {
            service_layout.setVisibility(View.GONE);
            if (isManager) {
                isMember = true;
                userDivider.setVisibility(View.VISIBLE);
                tvProfession.setVisibility(View.GONE);
                tvProfessionToolbar.setVisibility(View.GONE);
                total_layout.setVisibility(View.GONE);
                llTabsParent.setVisibility(View.GONE);

                if (isSelf) {
                    fan_follow_buttons_layout.setVisibility(View.GONE);
                    lLEditprofile.setVisibility(View.VISIBLE);
                    iLfan_following_layout.setVisibility(View.VISIBLE);
                } else {
                    iLfan_following_layout.setVisibility(View.GONE);
                    fan_follow_buttons_layout.setVisibility(View.GONE);
                    lLEditprofile.setVisibility(View.GONE);
                }
                tabTitles = new String[]{"Photos"};
            } else if (isCelebManager) {
                isMember = false;
                userDivider.setVisibility(View.GONE);
                total_layout.setVisibility(View.VISIBLE);
                tvProfession.setVisibility(View.VISIBLE);
                tvProfessionToolbar.setVisibility(View.VISIBLE);
                llTabsParent.setVisibility(View.VISIBLE);
                if (isSelf) {
                    service_layout.setVisibility(View.GONE);
                    fan_follow_buttons_layout.setVisibility(View.GONE);
                    lLEditprofile.setVisibility(View.VISIBLE);
                    iLfan_following_layout.setVisibility(View.VISIBLE);
                } else {
                    service_layout.setVisibility(View.VISIBLE);
                    fan_follow_buttons_layout.setVisibility(View.VISIBLE);
                    lLEditprofile.setVisibility(View.GONE);
                    iLfan_following_layout.setVisibility(View.GONE);
                }
                //  tabTitles = new String[]{"Profile", "Photos", "Videos", "Brands"};
                tabTitles = new String[]{"Photos", "Videos", "Brands"};
            } else {
                isMember = false;
                userDivider.setVisibility(View.GONE);
                total_layout.setVisibility(View.VISIBLE);
                tvProfession.setVisibility(View.VISIBLE);
                tvProfessionToolbar.setVisibility(View.VISIBLE);
                llTabsParent.setVisibility(View.VISIBLE);
                if (isSelf) {
                    service_layout.setVisibility(View.GONE);
                    fan_follow_buttons_layout.setVisibility(View.GONE);
                    lLEditprofile.setVisibility(View.VISIBLE);
                    iLfan_following_layout.setVisibility(View.VISIBLE);
                } else {
                    service_layout.setVisibility(View.VISIBLE);
                    fan_follow_buttons_layout.setVisibility(View.VISIBLE);
                    lLEditprofile.setVisibility(View.GONE);
                    iLfan_following_layout.setVisibility(View.GONE);
                }
                // tabTitles = new String[]{"Profile", "Photos", "Videos", "Brands"};
                tabTitles = new String[]{"Photos", "Videos", "Brands"};
            }
        } else if (!editProfileUserDetailsOrg.getUserDetails().getCeleb()) {
            isMember = true;
            tvProfession.setVisibility(View.GONE);
            tvProfessionToolbar.setVisibility(View.GONE);
            service_layout.setVisibility(View.GONE);
            userDivider.setVisibility(View.VISIBLE);

            if (isSelf) {
                fan_follow_buttons_layout.setVisibility(View.GONE);
                lLEditprofile.setVisibility(View.VISIBLE);
                iLfan_following_layout.setVisibility(View.VISIBLE);
            } else {
                fan_follow_buttons_layout.setVisibility(View.GONE);
                lLEditprofile.setVisibility(View.GONE);
                iLfan_following_layout.setVisibility(View.GONE);
            }
            total_layout.setVisibility(View.GONE);
            llTabsParent.setVisibility(View.GONE);
            // tabTitles = new String[]{"Profile", "Photos"};
            tabTitles = new String[]{"Photos"};
        } else {
            userDivider.setVisibility(View.GONE);
            service_layout.setVisibility(View.VISIBLE);
            total_layout.setVisibility(View.VISIBLE);
            tvProfession.setVisibility(View.VISIBLE);
            tvProfessionToolbar.setVisibility(View.VISIBLE);
            llTabsParent.setVisibility(View.VISIBLE);
            if (isSelf) {
                service_layout.setVisibility(View.GONE);
                fan_follow_buttons_layout.setVisibility(View.GONE);
                lLEditprofile.setVisibility(View.VISIBLE);
                iLfan_following_layout.setVisibility(View.VISIBLE);
            } else {
                service_layout.setVisibility(View.VISIBLE);
                fan_follow_buttons_layout.setVisibility(View.VISIBLE);
                lLEditprofile.setVisibility(View.GONE);
                iLfan_following_layout.setVisibility(View.GONE);
            }
            isMember = false;
            //  tabTitles = new String[]{"Profile", "Photos", "Videos", "Brands"};
            tabTitles = new String[]{"Photos", "Videos", "Brands"};
        }
        if (editProfileUserDetailsOrg.getUserDetails().getAvtarImgPath() != null && !editProfileUserDetailsOrg.getUserDetails().getAvtarImgPath().isEmpty()) {
            Glide.with(activity()).load(ApiClient.BASE_URL + editProfileUserDetailsOrg.getUserDetails().getAvtarImgPath())
                    .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.profile_picture_placeholder)
                    .into(ivProfileImage);
        } else {
            Glide.with(activity()).load(R.drawable.profile_picture_placeholder).into(ivProfileImage);
        }
        if (editProfileUserDetailsOrg.getUserDetails().getCover_imgPath() != null && !editProfileUserDetailsOrg.getUserDetails().getCover_imgPath().isEmpty()) {
            Glide.with(activity()).load(ApiClient.BASE_URL + editProfileUserDetailsOrg.getUserDetails().getCover_imgPath())
                    .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.cover_picture_place_holder)
                    .into(imageViewCoverPic);
        } else {
            Glide.with(activity()).load(R.drawable.cover_picture_place_holder).into(imageViewCoverPic);
        }
        if (editProfileUserDetailsOrg.getUserDetails().getFirstName() != null && !editProfileUserDetailsOrg.getUserDetails().getFirstName().isEmpty()) {
            tvProfileName.setText(Common.getInstance().convertFirstLetterToCapital(editProfileUserDetailsOrg.getUserDetails().getFirstName().trim()) + " " + editProfileUserDetailsOrg.getUserDetails().getLastName());
            tvProfileNameToolbar.setText(Common.getInstance().convertFirstLetterToCapital(editProfileUserDetailsOrg.getUserDetails().getFirstName().trim()) + " " + editProfileUserDetailsOrg.getUserDetails().getLastName());
        } else {
            tvProfileName.setText("");
            tvProfileNameToolbar.setText("");
        }
        if (editProfileUserDetailsOrg.getUserDetails().getAboutMe() != null && !editProfileUserDetailsOrg.getUserDetails().getAboutMe().isEmpty()) {
            tvAboutMe.setText(Common.decodeMessage(Common.convertCaseSensitive(editProfileUserDetailsOrg.getUserDetails().getAboutMe().trim())));
            tvAboutMe.post(new Runnable() {
                @Override
                public void run() {
                    int lineCnt = tvAboutMe.getLineCount();
                    // Perform any actions you want based on the line count here.
                    if (lineCnt >= 3) {
                        tvAboutMe.setMaxLines(3);
                        LinearLayout.LayoutParams params_viewPager = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params_viewPager.setMargins(0, textTop, 0, 0);
                        tvAboutMe.setLayoutParams(params_viewPager);
                        tvAboutMeMore.setVisibility(View.VISIBLE);
                    } else {
                        LinearLayout.LayoutParams params_viewPager = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params_viewPager.setMargins(0, textTop, 0, textBottom);
                        tvAboutMe.setLayoutParams(params_viewPager);
                        tvAboutMeMore.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            tvAboutMe.setText("");
            tvAboutMeMore.setVisibility(View.GONE);
        }
        if (editProfileUserDetailsOrg.getUserDetails().getProfession() != null && !editProfileUserDetailsOrg.getUserDetails().getProfession().isEmpty()) {
            tvProfession.setText(Character.toUpperCase(editProfileUserDetailsOrg.getUserDetails().getProfession().charAt(0))
                    + editProfileUserDetailsOrg.getUserDetails().getProfession().substring(1));

            if (editProfileUserDetailsOrg.getUserDetails().getUserCategory() != null && !editProfileUserDetailsOrg.getUserDetails().getUserCategory().isEmpty()) {
                String professionAndCategory = editProfileUserDetailsOrg.getUserDetails().getUserCategory() + ", " + tvProfession.getText().toString();
                tvProfession.setText(professionAndCategory);
            }
            //
            tvProfessionToolbar.setText(Character.toUpperCase(editProfileUserDetailsOrg.getUserDetails().getProfession().charAt(0)) + editProfileUserDetailsOrg.getUserDetails().getProfession().substring(1));
        } else {
            tvProfession.setText("");
            tvProfession.setVisibility(View.GONE);
            tvProfession.setBackgroundColor(activity().getResources().getColor(R.color.transparent));
            //
            tvProfessionToolbar.setText("");
            tvProfessionToolbar.setVisibility(View.GONE);
            tvProfessionToolbar.setBackgroundColor(activity().getResources().getColor(R.color.transparent));
        }
        posts_count.setText(String.valueOf(editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getFeedCount()));
        mFans_count = editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getFanOfUr();
        self_fan_count = editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getUrFanOf();
        tv_self_fan_count.setText(String.valueOf(editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getUrFanOf()));
        tv_self_following_count.setText(String.valueOf(editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getFollowing()));
        self_following_count = editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getFollowing();
        fans_count.setText(String.valueOf(editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getFanOfUr()));
        mFollowers_count = editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getFollowers();
        followers_count.setText(String.valueOf(editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getFollowers()));
        String scheduleCount = Common.getInstance().IsNullReturnValue(String.valueOf(editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getScheduleCount()), "0");
        schedules_count.setText(scheduleCount);
        if (editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getFollowers() == 1) {
            textViewFollowers.setText("Follower");
        } else {
            textViewFollowers.setText("Followers");
        }
        if (editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getFanOfUr() == 1) {
            textViewFans.setText("Fan");
        } else {
            textViewFans.setText("Fans");
        }
        if (editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getFeedCount() == 1) {
            textViewPosts.setText("Post");
        } else {
            textViewPosts.setText("Posts");
        }
        if (editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().isFan()) {
            fanBtn.setText("Fanned");
            fanBtn.setTextColor(getResources().getColor(R.color.dark_grey));
            fanBtn.setBackground(getResources().getDrawable(R.drawable.submit_rectangle_for_settings_empty_drakgrey_5dp));
            fanStatusGlobal = true;
        } else {
            fanStatusGlobal = false;
            fanBtn.setText("Fan");
            fanBtn.setTextColor(getResources().getColor(R.color.white));
            fanBtn.setBackground(getResources().getDrawable(R.drawable.submit_rectangle_normal_rad));
        }
        if (editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().isFollower()) {
            follow_button.setText("Following");
            follow_button.setTextColor(getResources().getColor(R.color.dark_grey));
            follow_button.setBackground(getResources().getDrawable(R.drawable.submit_rectangle_for_settings_empty_drakgrey_5dp));
        } else {
            follow_button.setText("Follow");
            follow_button.setTextColor(getResources().getColor(R.color.white));
            follow_button.setBackground(getResources().getDrawable(R.drawable.submit_rectangle_normal_rad));
        }

        //Celeb Active Status time
        if (isSelf) {
            activetime.setVisibility(View.GONE);
            ivOnlineCircle.setVisibility(View.GONE);

        } else if (isCeleb || isCelebManager) {

            Log.e("activeTime", editProfileUserDetailsOrg.getUserDetails().activeTime + "_shiva");

            activetime.setVisibility(View.VISIBLE);
            if (editProfileUserDetailsOrg.getUserDetails().isOnline) {
                activetime.setText("Online");
                activetime.setTextColor(getActivity().getResources().getColor(R.color.konnect_green));
                ivOnlineCircle.setVisibility(View.VISIBLE);
            } else {
                ivOnlineCircle.setVisibility(View.GONE);
                if (editProfileUserDetailsOrg.getUserDetails().activeTime != null && !editProfileUserDetailsOrg.getUserDetails().activeTime.isEmpty()) {
                    if (Utility.activeAgotime(editProfileUserDetailsOrg.getUserDetails().activeTime).equals("Online")) {
                        activetime.setText("Online");
                        activetime.setTextColor(getActivity().getResources().getColor(R.color.konnect_green));
                        ivOnlineCircle.setVisibility(View.VISIBLE);
                    } else {
                        activetime.setText("Active " + Utility.activeAgotime(editProfileUserDetailsOrg.getUserDetails().activeTime) + " ago");
                    }

                } else {
                    activetime.setText("");
                }
            }
        } else {
            activetime.setVisibility(View.GONE);
            ivOnlineCircle.setVisibility(View.GONE);
        }
        setTabs();
        setActiveTabPosition(0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCOUNTS:
                if (grantResults.length <= 0 || grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(activity(), "camera permission denied", Toast.LENGTH_LONG).show();
                }
                break;
            case MY_PERMISSIONS_REQUEST_ACCOUNTS_CALL:
                if (grantResults.length <= 0 || grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(activity(), "Calender permission denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backComplete) {
            activity().onBackPressed();
        } else if (v.getId() == R.id.posts_layout) {
            if (editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getFeedCount() > 0) {
                Common.getInstance().openCelebFeeds(activity(), editProfileUserDetailsOrg.getUserDetails().getId(),true);
            } else {
                Toast.makeText(activity(), "No Posts available", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.fans_layout) {
            if (mFans_count != 0) {
                navigatingToFanOoFollowPage("FAN");
            } else {
                Toast.makeText(activity(), "There is no fans available", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.followers_layout) {
            if (mFollowers_count != 0) {
                navigatingToFanOoFollowPage("FOLLOW");
            } else {
                Toast.makeText(activity(), "There is no followers available", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.schedules_layout) {
            if (editProfileUserDetailsOrg.getFanFollowingFollowerFeedCount().getScheduleCount() > 0) {
                //Common.getInstance().openCelebSchedulesScreen(activity(), editProfileUserDetailsOrg.getUserDetails().getId());
                Common.getInstance().openScheduleListFragment(activity(), editProfileUserDetailsOrg.getUserDetails().getId());
            } else {
                Toast.makeText(activity(), "No schedules available", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.audio_call_icon) {

            if (!Utility.checkPermissionRequest(Permission.RECORD, activity())) {
                Utility.raisePermissionRequest(Permission.RECORD, activity());
            } else {

            }
        } else if (v.getId() == R.id.video_call_icon) {

            if (!Utility.checkPermissionRequest(Permission.CAMERA, activity())) {
                Utility.raisePermissionRequest(Permission.CAMERA, activity());
            } else if (!Utility.checkPermissionRequest(Permission.RECORD, activity())) {
                Utility.raisePermissionRequest(Permission.RECORD, activity());
            } else {

            }
        } else if (v.getId() == R.id.chaticons) {

            ChatDataConvertModel chatDataConvertModel = new ChatDataConvertModel();
            try {
                chatDataConvertModel._id = editProfileUserDetailsOrg.getUserDetails().getId();
                chatDataConvertModel.firstName = editProfileUserDetailsOrg.getUserDetails().getFirstName();
                chatDataConvertModel.lastName = editProfileUserDetailsOrg.getUserDetails().getLastName();
                chatDataConvertModel.avtar_imgPath = editProfileUserDetailsOrg.getUserDetails().getAvtarImgPath();
                chatDataConvertModel.aboutMe = editProfileUserDetailsOrg.getUserDetails().getAboutMe();
                chatDataConvertModel.profession = editProfileUserDetailsOrg.getUserDetails().getProfession();
                chatDataConvertModel.isFan = fanStatusGlobal;
                chatDataConvertModel.isCeleb = editProfileUserDetailsOrg.getUserDetails().getCeleb();
                chatDataConvertModel.message = "";
                chatDataConvertModel.receiverId = editProfileUserDetailsOrg.getUserDetails().getId();
                chatDataConvertModel.senderId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
                chatDataConvertModel.createdAt = "";
                chatDataConvertModel.counter = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
            Common.getInstance().openChatTabsActivity(activity(), chatDataConvertModel);
        } else if (v.getId() == R.id.iLself_fan_layout) {
            if (self_fan_count != 0) {
                Intent intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_TITLE, "Celebrities");
                intent.putExtra(Constants.FRAGMENT_KEY, 8003);// MyFanFollowersFragment
                intent.putExtra("FAN_OR_FOLLOW", "FAN");
                intent.putExtra("FROM_USER_PROFILE", "USER_BOOK_APPOINTMENT");
                intent.putExtra("CELEB_ID", profileId);
                startActivity(intent);
            } else {
                Toast.makeText(activity(), "There is no fans available", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.iLself_following_layout) {
            if (self_following_count != 0) {
                Intent intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_TITLE, "Celebrities");
                intent.putExtra(Constants.FRAGMENT_KEY, 8003);// MyFanFollowersFragment
                intent.putExtra("FAN_OR_FOLLOW", "FOLLOW");
                intent.putExtra("FROM_USER_PROFILE", "USER_BOOK_APPOINTMENT");
                intent.putExtra("CELEB_ID", profileId);
                startActivity(intent);
            } else {
                Toast.makeText(activity(), "There is no followings available", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.imageviewceleb) {
            if (editProfileUserDetailsOrg.getUserDetails().getAvtarImgPath() != null
                    && !editProfileUserDetailsOrg.getUserDetails().getAvtarImgPath().isEmpty() &&
                    editProfileUserDetailsOrg.getUserDetails().getAvtarImgPath().length() > 6) {
                ((HelperActivity) getActivity()).openProfileImageComplete(editProfileUserDetailsOrg.getUserDetails().getAvtarImgPath());
            }
        } else if (v.getId() == R.id.imageViewCoverPic) {
            if (editProfileUserDetailsOrg.getUserDetails().getCover_imgPath() != null && !editProfileUserDetailsOrg.getUserDetails().getCover_imgPath().isEmpty()) {
                ((HelperActivity) getActivity()).openProfileImageComplete(editProfileUserDetailsOrg.getUserDetails().getCover_imgPath());
            }
        } else if (v.getId() == R.id.llMoveUp) {
            nestedScrollView.smoothScrollTo(0, (llTopContent.getBottom() - toolbarHeight) + getResources().getDimensionPixelSize(R.dimen._1sdp));
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

    private void getCelebChargeCredits(EditProfileUserDetails editProfileUserDetailsLocal) {
        for (int i = 0; i < editProfileUserDetailsLocal.getCelebContracts().size(); i++) {
            Log.e("serviceType", editProfileUserDetailsLocal.getCelebContracts().get(i).getServiceType() + "");
            if (editProfileUserDetailsLocal.getCelebContracts().get(i).getServiceType().equals(Constants.FAN_STATUS)) {
                fanCreditCharge = editProfileUserDetailsLocal.getCelebContracts()
                        .get(i).getServiceCredits();
            } else if (editProfileUserDetailsLocal.getCelebContracts().get(i).getServiceType().equals(Constants.AUDIO_CALL)) {
                audioCreditCharge = editProfileUserDetailsLocal.getCelebContracts()
                        .get(i).getServiceCredits();
            } else if (editProfileUserDetailsLocal.getCelebContracts().get(i).getServiceType().equals(Constants.VIDEO_CALL)) {
                videoCreditCharge = editProfileUserDetailsLocal.getCelebContracts()
                        .get(i).getServiceCredits();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        int tabCount;
        private int mCurrentPosition = -1;

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
                    return photosFragment = new PhotosFragment(editProfileUserDetailsOrg.getUserDetails().getId(),
                            isSelf, memberMediaList, isMember, false);

                case 1:
                    return videosFragment = new PhotosFragment(editProfileUserDetailsOrg.getUserDetails().getId(), isSelf, memberMediaList, isMember, true);

                case 2:
                    return new BrandsFragment();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (position != mCurrentPosition) {
                Fragment fragment = (Fragment) object;
                WrapContentHeightViewPager pager = (WrapContentHeightViewPager) container;
                if (fragment != null && fragment.getView() != null) {
                    mCurrentPosition = position;
                    pager.measureCurrentView(fragment.getView());
                }
            }
        }
    }

    private void checkCalendarPermission() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(activity(), Manifest.permission.WRITE_CALENDAR);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_CALENDAR);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS_CALL);
        }
    }
    public void setViewPagerTab(int position){
        viewPager.setCurrentItem(position);
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


    private void navigatingToFanOoFollowPage(String fan) {
        if (Common.isCelebAndManager(activity())) {
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_MEMBER_OR_CELEB_PROFILE, "Fans & Followers");
            Intent intent = new Intent(activity(), HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_TITLE, "Fans & Followers");
            intent.putExtra(Constants.FRAGMENT_KEY, 8010);// MyFanFollowersFragment
            intent.putExtra("FAN_OR_FOLLOW", fan);
            intent.putExtra("isFromCelebProfile", true);
            intent.putExtra("FROM_USER_PROFILE", "USER_BOOK_APPOINTMENT");
            intent.putExtra("CELEB_ID", profileId);
            startActivity(intent);
        } else if (Common.isCelebStatus(activity())) {
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_MEMBER_OR_CELEB_PROFILE, "Fans & Followers");
            Intent intent = new Intent(activity(), HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_TITLE, "Fans & Followers");
            intent.putExtra(Constants.FRAGMENT_KEY, 8010);// MyFanFollowersFragment
            intent.putExtra("FAN_OR_FOLLOW", fan);
            intent.putExtra("isFromCelebProfile", true);
            intent.putExtra("FROM_USER_PROFILE", "USER_BOOK_APPOINTMENT");
            intent.putExtra("CELEB_ID", profileId);
            startActivity(intent);
        } else if (Common.isManagerStatus(activity())) {
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_MEMBER_OR_CELEB_PROFILE, "Fans & Followers");
            Intent intent = new Intent(activity(), HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_TITLE, "Fans & Followers");
            intent.putExtra(Constants.FRAGMENT_KEY, 8010);// MyFanFollowersFragment
            intent.putExtra("FAN_OR_FOLLOW", fan);
            intent.putExtra("isFromCelebProfile", true);
            intent.putExtra("FROM_USER_PROFILE", "USER_BOOK_APPOINTMENT");
            intent.putExtra("CELEB_ID", profileId);
            startActivity(intent);
        } else {
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_MEMBER_OR_CELEB_PROFILE, "Fans & Followers");
            Intent intent = new Intent(activity(), HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_TITLE, "Fans & Followers");
            intent.putExtra(Constants.FRAGMENT_KEY, 8010);// MyFanFollowersFragment
            intent.putExtra("FAN_OR_FOLLOW", fan);
            intent.putExtra("isFromCelebProfile", true);
            intent.putExtra("FROM_USER_PROFILE", "USER_BOOK_APPOINTMENT");
            intent.putExtra("CELEB_ID", profileId);
            startActivity(intent);
        }
        if (SessionManager.userLogin.isCeleb != null) {
            if (SessionManager.userLogin.isCeleb) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_SELF_MEMBER_PROFILE, "FALSE");
            } else {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_SELF_MEMBER_PROFILE, "TRUE");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Common.getInstance().setCelebrityProfileFragment(null);
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_CELEBRITY_COMPLETE_DATA)) {
            try {
                Type type = new TypeToken<EditProfileUserDetails>() {}.getType();
                EditProfileUserDetails editProfileUserDetails = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                if (editProfileUserDetails != null) {
                    editProfileUserDetailsOrg = editProfileUserDetails;
                    memberMediaList = new ArrayList<>(100);
                    memberVideoList = new ArrayList<>();
                    if (editProfileUserDetails.getUserDetails().getId().equals(SessionManager.userLogin.userId)) {
                        isSelf = true;
                    } else {
                        isSelf = false;
                    }
                    if (editProfileUserDetails.getUserDetails().getCeleb() && editProfileUserDetails.getUserDetails().getManager()) {
                        isCelebManager = true;
                    } else if (editProfileUserDetails.getUserDetails().getCeleb()) {
                        isCeleb = true;
                    } else if (editProfileUserDetails.getUserDetails().getManager()) {
                        isManager = true;
                    }
                    if (editProfileUserDetails.getUserDetails().getAboutMe() != null && !editProfileUserDetails.getUserDetails().getAboutMe().isEmpty()) {
                        aboutMe_fromServer = editProfileUserDetails.getUserDetails().getAboutMe();
                    }
                    if (!editProfileUserDetails.getUserDetails().getCeleb()) {
                        if (editProfileUserDetails.getUserDetails().getPastProfileImages() != null && editProfileUserDetails.getUserDetails().getPastProfileImages().size() > 0) {
                            for (int i = 0; i < editProfileUserDetails.getUserDetails().getPastProfileImages().size(); i++) {
                                Media media = new Media();
                                media.url = editProfileUserDetails.getUserDetails().getPastProfileImages().get(i).avtarImgPath;
                                memberMediaList.add(media);
                            }
                        }
                    }
                    profileDetails();
                    getCelebChargeCredits(editProfileUserDetailsOrg);
                } else {
                    ifProfileDataNotFound();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals(Constants.FAN_STATUS)) {
            try {
                mFans_count = mFans_count + 1;
                fans_count.setText(String.valueOf(mFans_count));
                fanBtn.setText("Fanned");
                fanBtn.setTextColor(getResources().getColor(R.color.dark_grey));
                fanBtn.setBackground(getResources().getDrawable(R.drawable.submit_rectangle_for_settings_empty_drakgrey_5dp));
                fanStatusGlobal = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals(Constants.UN_FAN_STATUS)) {
            try {
                fanBtn.setVisibility(View.VISIBLE);
                fanStatusGlobal = false;
                fanBtn.setText("Fan");
                fanBtn.setTextColor(getResources().getColor(R.color.white));
                fanBtn.setBackground(getResources().getDrawable(R.drawable.submit_rectangle_normal_rad));
                mFans_count = mFans_count - 1;
                fans_count.setText(String.valueOf(mFans_count));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_CELEBRITY_COMPLETE_DATA)) {
            ifProfileDataNotFound();
        }
    }

}
