package info.dkapp.flow.stories.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.databaseutil.appstart.AppController;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.ipoll.fragments.feeds.FeedsFragment;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.stories.DeleteStoryDailog;
import info.dkapp.flow.stories.SeenListDailog;
import info.dkapp.flow.stories.models.StoriesData;
import info.dkapp.flow.stories.models.StoryProfileInfo;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.SocketForAppUtill;
import info.dkapp.flow.utils.StoriesProgressView;
import info.dkapp.flow.utils.Utility;
import retrofit2.Call;

public class StoriesFragment extends Fragment implements StoriesProgressView.StoriesListener,
        View.OnClickListener, IFragment, IApiListener {

    @BindView(R.id.reverse)
    View reverse;

    @BindView(R.id.skip)
    View skip;

    @BindView(R.id.stories)
    StoriesProgressView storiesProgressView;

    @BindView(R.id.storyImagebackground)
    ImageView storyImagebackground;

    @BindView(R.id.storyImage)
    ImageView storyImage;

    @BindView(R.id.imgOptions)
    ImageView imgOptions;

    @BindView(R.id.closeicon)
    ImageView closeicon;

    @BindView(R.id.celebritypic)
    ImageView celebritypic;


    @BindView(R.id.multiimageLayout)
    RelativeLayout multiimageLayout;

    @BindView(R.id.seenLayout)
    LinearLayout seenLayout;

    @BindView(R.id.statustime)
    TextView statustime;


    @BindView(R.id.celebrityname)
    TextView celebrityname;

    @BindView(R.id.statusDes)
    TextView statusDes;

    @BindView(R.id.seencounttxt)
    TextView seencounttxt;

    @BindView(R.id.bottomLayout)
    RelativeLayout bottomLayout;

    @BindView(R.id.viewScreen)
    View viewScreen;

    @BindView(R.id.celeprofileLayout)
    LinearLayout celeprofileLayout;

    @BindView(R.id.playerView)
    PlayerView playerView;

    private int PositionValue;

    private long pressTime = 0L;
    private long limit = 500L;
    private String CelebId = "";

    private DefaultTrackSelector trackSelector;
    private BandwidthMeter bandwidthMeter;

    private static StoriesFragment instance = null;
    public boolean isImageUploaded = false, isStoriesStarted = false;
    private StoryProfileInfo storyProfileInfo;

    private SimpleExoPlayer player;

    private StoriesData storiesData;
    private boolean isVisibleToUser = false;
    private long minDuration = 3000L;

    private IApiListener iApiListener;

    private int CLICK_ACTION_THRESHOLD = 200, screenWidth = 0;
    float startX = 0, startY = 0;

    private String TAG = "OnTouchListener";
    float initialX, initialY;


    public static StoriesFragment getInstance() {
        return instance;
    }

    public StoriesFragment() {
    }

    public static StoriesFragment newInstance(int position, String celebId, StoryProfileInfo storyProfileInfo) {
        StoriesFragment fragment = new StoriesFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("celebId", celebId);
        args.putParcelable("storyProfileInfo", storyProfileInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getArguments() != null) {
                PositionValue = getArguments().getInt("position");
                CelebId = getArguments().getString("celebId");
                storyProfileInfo = getArguments().getParcelable("storyProfileInfo");
            }
        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_story_list, container, false);
        Common.getInstance().setStoriesFragment(this);

        ButterKnife.bind(this, root);
        iApiListener = this;

        Objects.requireNonNull(getActivity()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        screenWidth = Utility.getScreenWidth(activity());

        intializeActions();


        if (storyProfileInfo != null) {
            setupProfileInfo(storyProfileInfo);
        }

        if (StoriesViewPager.celeblist.get(PositionValue).storiesData != null) {
            storiesData = StoriesViewPager.celeblist.get(PositionValue).storiesData;
            setStoriesAdapter();
        } else {
            getIndividualCelebStories(CelebId);
        }

        //
        /*viewScreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        Log.d("setOnTouchListener","down");
                        return false;

                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        float endY = event.getY();
                        if (isAClick(startX, endX, startY, endY)) {
                            if(startX <= (screenWidth*50)/100){
                                Log.d("setOnTouchListener","left");
                            } else if(startX >= (screenWidth*50)/100){
                                Log.d("setOnTouchListener","right");
                            }
                        }
                        Log.d("setOnTouchListener","up");
                        return false;
                }
                //view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/

//        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        /*mDetector = new GestureDetectorCompat(getActivity(), new MyGestureListener());

        reverse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                getActivity().mDetector.onTouchEvent(motionEvent);
                return false;
            }
        });*/
        return root;
    }

    private void intializeActions() {
        reverse.setOnTouchListener(onTouchListener);
        skip.setOnTouchListener(onTouchListener);

        reverse.setOnClickListener(this);
        skip.setOnClickListener(this);
        closeicon.setOnClickListener(this);
        celeprofileLayout.setOnClickListener(this);
        imgOptions.setOnClickListener(this);
        seenLayout.setOnClickListener(this);
    }

    /*private boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        return !(differenceX > CLICK_ACTION_THRESHOLD*//* =5 *//* || differenceY > CLICK_ACTION_THRESHOLD);
    }*/

    private void getIndividualCelebStories(String CelebId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getMyclient(Constants.StoriesConstants.GET_CELEB_INDIVIDUAL_STORIES + CelebId
                + "/" + Common.isLoginId() + "/0");
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, Constants.StoriesConstants.GET_CELEB_INDIVIDUAL_STORIES,
                true, iApiListener, true));
    }

    private void expandableTextView(String inputText) {
        String text = inputText.substring(0, 150) + "... ";
        final String fulltext = inputText;
        final SpannableString moreTxt = new SpannableString(text + "More");
        ClickableSpan span1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if (player != null) {
                    pauseVideo();
                }
                // do some thing
                String fulltextAdd = fulltext + " ";
                SpannableString lessTxt = new SpannableString(fulltextAdd + "Less");
                ClickableSpan span2 = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        statusDes.setText(moreTxt);
                        resumeVideo();
                        statusDes.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                };
                lessTxt.setSpan(span2, fulltextAdd.length(), lessTxt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                lessTxt.setSpan(new ForegroundColorSpan(Color.WHITE), fulltextAdd.length(), lessTxt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                statusDes.setText(lessTxt);
                statusDes.setMovementMethod(LinkMovementMethod.getInstance());
            }
        };
        moreTxt.setSpan(span1, 154, 157, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        moreTxt.setSpan(new ForegroundColorSpan(Color.WHITE), 154, 157, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        statusDes.setText(moreTxt);
        statusDes.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @SuppressLint("ClickableViewAccessibility")
    private View.OnTouchListener onTouchListener = (v, event) -> {
        if (isVisibleToUser) {
            int action = event.getActionMasked();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d("OnTouchListener", "Action was DOWN");
                    pressTime = System.currentTimeMillis();
                    pauseVideo();
                    return false;
                case MotionEvent.ACTION_UP:
                    Log.d("OnTouchListener", "Action was UP");
                    long now = System.currentTimeMillis();
                    resumeVideo();
                    return limit < now - pressTime;
            }
        }
        return false;
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reverse:
                if (isVisibleToUser) {
                    storiesProgressView.reverse();
                }
                break;
            case R.id.skip:
                if (isVisibleToUser) {
                    if (StoriesViewPager.getInstance() != null) {
                        if (StoriesViewPager.getInstance().viewPager.getAdapter().getCount() == PositionValue + 1) {
                            if (storiesProgressView.getCurrent() != storiesData.storiesInnerData.size() - 1) {
                                storiesProgressView.skip();
                            } else {
                                if (StoriesViewPager.getInstance().mViewPagerAdapter.getCount()
                                        == StoriesViewPager.getInstance().viewPager.getCurrentItem() + 1) {
                                    if (player != null) {
                                        releasePlayer();
                                    }

                                    if (FeedsFragment.getInstance() != null) {
                                        Log.e("allSeenStories", true + "");
                                        FeedsFragment.getInstance().fetchStories();
                                    }

                                    activity().finish();
                                    return;
                                }
                            }
                        } else {
                            storiesProgressView.skip();
                        }
                    } else {
                        storiesProgressView.skip();
                    }


                }
                break;
            case R.id.closeicon:
                if (isVisibleToUser) {
                    if (player != null) {
                        releasePlayer();
                    }
                    getStoriesAllSeenStatus();
                    activity().finish();
                }
                break;
            case R.id.celeprofileLayout:
                if (isVisibleToUser) {
                    pauseVideo();
                    Common.getInstance().openProfileScreen(activity(), CelebId);
                }
                break;
            case R.id.seenLayout:
                if (isVisibleToUser) {
                    pauseVideo();
                    openStorySeenDailog(storiesData.storiesInnerData.get(storiesProgressView.getCurrent())._id);
                }
                break;
            case R.id.imgOptions:
                Log.e("checkDelete", true + "");
                pauseVideo();
                openDeleteStoryDailog(storiesData.storiesInnerData.get(storiesProgressView.getCurrent())._id);
                break;
        }
    }

    @Override
    public void onDestroy() {
        Log.e("onDestroy", true + "");
        if (storiesProgressView != null) {
            storiesProgressView.destroy();
        }
        if (player != null) {
            releasePlayer();
        }
        super.onDestroy();
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {

    }

    @Override
    public Activity activity() {
        return AppController.getInstance().getCurrentRegisteredActivity();
    }

    public void storyDelete(String deletestoryId) {
        Log.e("deleteId", "delete" + deletestoryId);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.deleteStory(storiesData.storiesInnerData.get(storiesProgressView.getCurrent())._id);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.StoriesConstants.DELETE_STORY, true,
                iApiListener, true));
    }


    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equalsIgnoreCase(Constants.StoriesConstants.GET_CELEB_INDIVIDUAL_STORIES)) {
            try {
                Type type = new TypeToken<StoriesData>() {
                }.getType();
                storiesData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);

                StoriesViewPager.celeblist.get(PositionValue).storiesData = storiesData;

                setStoriesAdapter();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals(Constants.StoriesConstants.DELETE_STORY)) {
            try {
                JSONObject jsonObject = new JSONObject(new Gson().toJson(apiResponseModel.data));
                if (!jsonObject.optString("message").isEmpty()) {
                    Toast.makeText(activity(), "" + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            releasePlayer();
            int currentStoryPosition = storiesProgressView.getCurrent();
            storiesData.storiesInnerData.remove(currentStoryPosition);

            if (storiesData.storiesInnerData == null || storiesData.storiesInnerData.size() <= 0) {
                if (StoriesViewPager.getInstance() != null) {
                    StoriesViewPager.getInstance().refreshAdapter(PositionValue);
                }
            } else {
                StoriesViewPager.celeblist.get(PositionValue).setStoriesData(storiesData);
            }
            if (currentStoryPosition >= storiesData.storiesInnerData.size() || storiesData.storiesInnerData == null || storiesData.storiesInnerData.size() <= 0) {
                onComplete();
            } else {
                setStatusCompleteInfo(currentStoryPosition);
            }
            if (FeedsFragment.getInstance() != null) {
                FeedsFragment.getInstance().fetchStories();
            }
        }
    }

    private void setupProfileInfo(StoryProfileInfo storyProfileInfo) {
        celebritypic.setVisibility(View.VISIBLE);
        closeicon.setVisibility(View.VISIBLE);
        if (storyProfileInfo.storyMemberInfo.firstName != null && !storyProfileInfo.storyMemberInfo.firstName.isEmpty()) {
            celebrityname.setText(Common.convertCaseSensitive(storyProfileInfo.storyMemberInfo.firstName));
            if (storyProfileInfo.storyMemberInfo.lastName != null && !storyProfileInfo.storyMemberInfo.lastName.isEmpty()) {
                celebrityname.setText(Common.convertCaseSensitive(storyProfileInfo.storyMemberInfo.firstName + " "
                        + storyProfileInfo.storyMemberInfo.lastName));
            }
        } else {
            celebrityname.setText("");
        }
        if (storyProfileInfo.storyMemberInfo.avtarImgPath != null && !storyProfileInfo.storyMemberInfo.avtarImgPath.isEmpty()) {
            Common.getInstance().setStoryImage(activity(), ApiClient.BASE_URL + storyProfileInfo.storyMemberInfo.avtarImgPath, celebritypic);
        }
    }

    private void setStoriesAdapter() {
        if (isVisibleToUser) {
            Log.e("seenCount", storiesData.getStatusSeenCount() + "");
            if (storiesData.getStatusSeenCount() == storiesData.storiesInnerData.size()) {
                setStatusCompleteInfo(0);
            } else {
                setStatusCompleteInfo(storiesData.getStatusSeenCount());
            }
        }
    }

    private void setStatusCompleteInfo(int storyPosition) {
        if (storiesData.storiesInnerData != null && storiesData.storiesInnerData.size() > 0) {
            storiesProgressView.setStoriesCount(storiesData.storiesInnerData.size());
            storiesProgressView.setStoriesListener(this);
            setStatusInfo(storyPosition);
        }
    }


    @Override
    public void apiErrorResponse(String condition, EnumConstants
            enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equalsIgnoreCase(Constants.StoriesConstants.GET_CELEB_INDIVIDUAL_STORIES)) {
            Toast.makeText(activity(), " No status available", Toast.LENGTH_SHORT).show();
        } else {
            if (condition.equals(Constants.StoriesConstants.DELETE_STORY)) {
                Toast.makeText(activity(), "Failed to delete Story", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onNext() {
        Log.d("storiesProgressView", "onNext");
        if (player != null) {
            releasePlayer();
        }
        if (isVisibleToUser) {
            StoriesViewPager.celeblist.get(PositionValue).storiesData.setStatusSeenCount(storiesProgressView.getCurrent() + 1);
            setStatusInfo(storiesProgressView.getCurrent() + 1);
        }
    }

    @Override
    public void onPrev() {
        Log.d("storiesProgressView", "onPrev");
        if (isVisibleToUser) {
            if ((storiesProgressView.getCurrent() - 1) < 0) {
                if (PositionValue == 0) {
                    Log.d("CamePos", "" + true);
                } else {
                    if (player != null) {
                        Log.d("unSeenView", "" + true);
                        releasePlayer();
                    }

                    StoriesViewPager.getInstance().setViewPagerPosition(PositionValue - 1);
                }
            } else {
                if (player != null) {
                    Log.d("unSeenView", "" + true);
                    releasePlayer();
                }
                StoriesViewPager.celeblist.get(PositionValue).storiesData.setStatusSeenCount(storiesProgressView.getCurrent() - 1);
                setStatusInfo(storiesProgressView.getCurrent() - 1);
            }
        } else {
            if (player != null) {
                Log.d("unSeenView", "" + true);
                releasePlayer();
            }
        }
    }

    private void setStatusInfo(int statusPosition) {
        if (storiesData.celebProfileInfo != null && storiesData.celebProfileInfo._id.equals(Common.isLoginId())) {
            imgOptions.setVisibility(View.VISIBLE);
            seenLayout.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.VISIBLE);
        } else {
            imgOptions.setVisibility(View.GONE);
            seenLayout.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.GONE);
        }
        if (storiesData.storiesInnerData.get(statusPosition).isSeen != 1) {
            SocketForAppUtill.getInstance().storySeenStatusEmit(storiesData.storiesInnerData.get(statusPosition)._id, storiesData.celebProfileInfo._id);
        }
        statustime.setText(Utility.timeLastUpdate(storiesData.storiesInnerData.get(statusPosition).createdAt));
        if (storiesData.storiesInnerData.get(statusPosition).mediaType.equals(Constants.MEDIA_TYPE_IMAGE)) {
            if (player != null) {
                releasePlayer();
            }
            storyImage.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.GONE);
            Common.getInstance().setStoryImage(Utility.getActivity(), ApiClient.BASE_URL
                    + storiesData.storiesInnerData.get(statusPosition).imageDataInfo.mediaUrl, storyImage);
            if (storiesData.storiesInnerData.get(statusPosition).imageDataInfo.mediaUrl != null) {
                reverse.setEnabled(false);
                Glide.with(Utility.getActivity()).load(ApiClient.BASE_URL + storiesData.storiesInnerData.get(statusPosition).imageDataInfo.mediaUrl)
                        .override(15, 15).error(R.drawable.celebicon_iogo).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                isImageUploaded = true;
                                reverse.setEnabled(true);
                                storyImagebackground.setImageDrawable(resource);
                                storiesProgressView.setStoryDuration(minDuration);
                                storiesProgressView.startStories(statusPosition);
                            }
                        });
            }
        } else {
            storyImage.setVisibility(View.GONE);
            playerView.setVisibility(View.GONE);
            isImageUploaded = true;
            Glide.with(Utility.getActivity()).load(ApiClient.BASE_URL + storiesData.storiesInnerData.get(statusPosition).imageDataInfo.thumbnailUrl).
                    override(10, 10).error(R.drawable.celebicon_iogo).into(storyImagebackground);
            if (player != null) {
                player.release();
                player = null;
                trackSelector = null;
                playerView.setPlayer(player);
                playerView.setVisibility(View.GONE);
            }
            startVideoStream(storiesData.storiesInnerData.get(statusPosition).imageDataInfo.mediaUrl, statusPosition);
        }
        if (seenLayout.getVisibility() == View.VISIBLE) {
            seenProfileData(statusPosition);
        }
        if (storiesData.storiesInnerData.get(statusPosition).mediaCaption != null
                && !storiesData.storiesInnerData.get(statusPosition).mediaCaption.isEmpty()) {
            bottomLayout.setVisibility(View.VISIBLE);
            if (storiesData.storiesInnerData.get(statusPosition).mediaCaption.length() > 150) {
                expandableTextView(storiesData.storiesInnerData.get(statusPosition).mediaCaption);
            } else {
                statusDes.setText(storiesData.storiesInnerData.get(statusPosition).mediaCaption);
            }
        } else {
            if (storiesData.celebProfileInfo != null && storiesData.celebProfileInfo._id.equals(Common.isLoginId())) {
                if (storiesData.getStatusSeenCount() >= 1) {
                    bottomLayout.setVisibility(View.VISIBLE);
                }
            }
            statusDes.setText("");
        }
    }

    private void seenProfileData(int statusPosition) {
        seencounttxt.setText(String.valueOf(storiesData.storiesInnerData.get(statusPosition).seenCount));
        int width = activity().getResources().getDimensionPixelSize(R.dimen._25sdp);
        int height = activity().getResources().getDimensionPixelSize(R.dimen._25sdp);
        multiimageLayout.removeAllViews();
        if (storiesData.storiesInnerData.get(statusPosition).seenprofilesList.size() > 0) {
            for (int i = 0; i < storiesData.storiesInnerData.get(statusPosition).seenprofilesList.size(); i++) {
                CircleImageView circleImageView = new CircleImageView(activity());
                circleImageView.setBackgroundColor(activity().getResources().getColor(R.color.transparent));
                int marginLeft = 0;
                String imageUrl = Common.getInstance().IsNullReturnValue(storiesData.storiesInnerData.get(statusPosition).seenprofilesList.get(i).avtar_imgPath, "");
                if (imageUrl.isEmpty() || imageUrl.length() < 5) {
                    circleImageView.setImageResource(R.drawable.ic_profile_circle_pleace_holder);
                } else {
                    Common.getInstance().setStoryImage(activity(), ApiClient.BASE_URL + imageUrl, circleImageView);
                }
                switch (i) {
                    case 0:
                        marginLeft = activity().getResources().getDimensionPixelSize(R.dimen._5sdp);
                        break;
                    case 1:
                        marginLeft = activity().getResources().getDimensionPixelSize(R.dimen._20sdp);
                        break;
                    case 2:
                        marginLeft = activity().getResources().getDimensionPixelSize(R.dimen._35sdp);
                        break;
                    case 3:
                        marginLeft = activity().getResources().getDimensionPixelSize(R.dimen._50sdp);
                        break;
                }
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
                layoutParams.setMargins(marginLeft, 0, 0, 0);
                circleImageView.setLayoutParams(layoutParams);
                multiimageLayout.addView(circleImageView);
            }
        } else {
            seenLayout.setVisibility(View.GONE);
        }
    }

    private void startVideoStream(String videourl, int statusPosition) {
        isStoriesStarted = false;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                storiesProgressView.pause();
            }
        }, 100);
        playerView.setVisibility(View.VISIBLE);
        Uri uri = Utility.addVideoUrl(videourl);
        bandwidthMeter = new DefaultBandwidthMeter();
        if (uri == null) {
            return;
        }
        playerView.requestFocus();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(activity(), trackSelector);
        player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_IDLE || playbackState == Player.STATE_ENDED || !playWhenReady) {
                    playerView.setKeepScreenOn(false);
                } else {
                    playerView.setKeepScreenOn(true);
                }
                if (playbackState == ExoPlayer.STATE_BUFFERING) {
                    //Common.getInstance().showProgressDialog(activity());
                    if (isStoriesStarted) {
                        storiesProgressView.pause();
                    }
                } else if (playWhenReady && playbackState == Player.STATE_READY) {
                    //Common.getInstance().closeProgressDialog();
                    if (isStoriesStarted) {
                        storiesProgressView.resume();
                    } else {
                        long duration = (long) storiesData.storiesInnerData.get(statusPosition).videoDuration * 1000;
                        // Common.getInstance().getVideoDuration(storiesData.storiesInnerData.get(statusPosition).imageDataInfo.mediaUrl);
                        if (duration <= 0) {
                            duration = minDuration;
                        }
                        storiesProgressView.setStoryDuration(duration);
                        storiesProgressView.startStories(statusPosition);
                        isStoriesStarted = true;
                    }
                }
            }
        });
        if (Common.getInstance().stopOtherBackgroundMusic(activity())) {
            playerView.setPlayer(player);
        }
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(activity(), Util.getUserAgent(activity(), Constants.APP_NAME));
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        player.prepare(mediaSource);
        if (Common.getInstance().stopOtherBackgroundMusic(activity())) {
            player.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (isVisibleToUser && player != null) {
            player.setPlayWhenReady(false);
            player.stop();
            player.seekTo(0);
            player.release();
            player = null;
            trackSelector = null;
            playerView.setPlayer(player);
        }
    }

    void pauseVideo() {
        storiesProgressView.pause();
        if (isVisibleToUser) {
            if (player != null) {
                player.setPlayWhenReady(false);
            }
        }
    }

    public void resumeVideo() {
        storiesProgressView.resume();
        if (isVisibleToUser) {
            if (player != null) {
                if (Common.getInstance().stopOtherBackgroundMusic(activity())) {
                    player.setPlayWhenReady(true);
                }
            }
        }
    }

    @Override
    public void onResume() {
        if (isVisibleToUser) {
            Log.d("storiesProgressView", "onResume");
            if (Common.getInstance().getSeenListDailog() != null && Common.getInstance().getSeenListDailog().getSeenListDailogVisibility()) {
                Log.d("SeenListDailog", "true");
            } else {
                resumeVideo();
            }
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (isVisibleToUser) {
            Log.d("storiesProgressView", "onPause");
            pauseVideo();
        }
        super.onPause();
    }

    @Override
    public void onComplete() {
        if (isVisibleToUser) {
            Log.d("storiesProgressView", "onComplete");
            if (player != null) {
                releasePlayer();
            }
            if (isVisibleToUser && StoriesViewPager.getInstance() != null) {
                Log.d("completedPos", PositionValue + "");
                StoriesViewPager.getInstance().setViewPagerPosition(PositionValue + 1);
            }
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            instance = this;
            if (storiesData != null) {
                if (storiesData.getStatusSeenCount() == storiesData.storiesInnerData.size()) {
                    setStatusCompleteInfo(0);
                } else {
                    setStatusCompleteInfo(storiesData.getStatusSeenCount());
                }
            }
            Log.e("PositionData", PositionValue + "");
        }
    }

    private void openDeleteStoryDailog(String storyId) {
        DeleteStoryDailog deleteStoryDailog = new DeleteStoryDailog();
        deleteStoryDailog.setData(storyId);
        deleteStoryDailog.show(getFragmentManager(), "DeleteStoryDailog");
    }

    private void openStorySeenDailog(String storyId) {
        SeenListDailog seenListDailog = new SeenListDailog();
        seenListDailog.setData(storyId);
        seenListDailog.show(getFragmentManager(), "OpenStoryDailog");
    }

    public void getStoriesAllSeenStatus() {
        boolean callStoriesApi = true;
        if (callStoriesApi) {
            if (FeedsFragment.getInstance() != null) {
                Log.e("allSeenStories", true + "");
                FeedsFragment.getInstance().fetchStories();
            }
        }
    }


}
