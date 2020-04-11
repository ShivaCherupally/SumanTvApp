package info.dkapp.flow.ipoll.fragments.feeds;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.nkzawa.socketio.client.Socket;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.dkapp.flow.appmanagers.feed.models.Celebrity;
import info.dkapp.flow.appmanagers.feed.models.Feed;
import info.dkapp.flow.appmanagers.feed.models.KonectData;
import info.dkapp.flow.appmanagers.feed.models.Media;
import info.dkapp.flow.ModelClass.ProfileDataModel;
import info.dkapp.flow.bottommenu.activity.BottomMenuActivity;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.constants.RController;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.bottommenu.interfaces.fragments.IKonectDailogClick;
import info.dkapp.flow.databaseutil.appstart.AppController;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.chat.socket.ChatSocket;
import info.dkapp.flow.ipoll.adapters.feeds.FeedsAdapter;
import info.dkapp.flow.ipoll.dialogs.custom.FeedOptionsDailog;
import info.dkapp.flow.ipoll.fragments.feeds.comments.AddCommentParams;
import info.dkapp.flow.ipoll.interfaces.feeds.IFeedAdapter;
import info.dkapp.flow.ipoll.viewholders.FeedViewHolder;
import info.dkapp.flow.ipoll.viewholders.OnlineCelebritiesViewHolder;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.stories.models.ShowCelebStoriesData;
import info.dkapp.flow.stories.models.StoryProfileInfo;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Logger;
import info.dkapp.flow.utils.SocketForAppUtill;
import info.dkapp.flow.utils.Utility;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;

public class FeedsFragment extends Fragment implements IFragment, IFeedAdapter, IApiListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.llNoFeeds)
    LinearLayout llNoFeeds;

    @BindView(R.id.ivImage)
    ImageView ivImage;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvContent)
    TextView tvContent;

    @BindView(R.id.llMoveUp)
    LinearLayout llMoveUp;

    @BindView(R.id.llFooterParent)
    LinearLayout llFooterParent;

    @BindView(R.id.llFeedNetworkError)
    LinearLayout llFeedNetworkError;

    @BindView(R.id.llFeedLoader)
    LinearLayout llFeedLoader;

    @BindView(R.id.tvFeedRetry)
    TextView tvFeedRetry;

    @BindView(R.id.ivFeedLoader)
    GifImageView ivFeedLoader;

    ApiInterface apiInterface;

    LinearLayoutManager linearLayoutManager;

    Boolean isLoadMoreBusy = false;

    IFeedAdapter iFeedAdapter;
    FeedsAdapter feedsAdapter;
    List<Feed> feedList;
    List<StoryProfileInfo> storyProfileInfoList;
    List<Celebrity> celebrityList;

    boolean isFeedTask = false, isOnlineTask = false;
    String CelebId = null;
    IApiListener iApiListener;
    //DailogBox
    private Dialog promoDialog;
    Button btnProfile, btnFanFollow;
    TextView headtitle, mCeleb_Name;
    private ImageView close_popup;
    private CircleImageView mProfile_Pic;
    private ProgressDialog progressDialog;
    private static FeedsFragment instance = null;
    private Socket mSocket;
    int LIMIT_COUNT = 10;
    private Boolean reloadAdapterChanges = false, isFragLoaded = false, isVisibleToUser = false, isFeedUpdateRunning = false, isUpdateFeedMethodCalled = false;
    private SparseArray<RelativeLayout> rlVideoViewSparseArray = new SparseArray<>();
    private List<Media> autoPlayVideoMediaList = new ArrayList<>();
    private int runningVideoItemPosition = -1, videoPlayingPosition = -1;
    private FindVisibleVideoView findVisibleVideoView;
    private SimpleExoPlayer player;
    private float currentVideoVolume = 0f;
    private Handler videoDurationHandler = new Handler(Looper.getMainLooper());
    private Runnable runnableVideoDuration;
    boolean isFanFollow;
    public FeedsFragment() {
        // Required empty public constructor
    }

    public static FeedsFragment getInstance() {
        return instance;
    }

    public static FeedsFragment newInstance(String param1, String param2) {
        FeedsFragment fragment = new FeedsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iApiListener = this;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            CelebId = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ipoll_fragment_feeds, container, false);
        ButterKnife.bind(this, view);
        Common.getInstance().setFeedsFragment(this);
        setUp();
        setFeedLoaderANDError(false, false);
        return view;
    }

    private void setUp() {
        iFeedAdapter = this;
        llMoveUp.setVisibility(View.GONE);
        llNoFeeds.setVisibility(View.GONE);
        llMoveUp.setOnClickListener(v -> {
            llMoveUp.setVisibility(View.GONE);
            scrollToTop();
        });
        recyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(activity()));
        DefaultItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                return true;
            }
        };
        recyclerView.setItemAnimator(animator);
        /*try {
            DividerItemDecoration itemDecorator = new DividerItemDecoration(activity(), DividerItemDecoration.VERTICAL){
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    int position = parent.getChildAdapterPosition(view);
                    // hide the divider for the last child
                    *//*if(feedsAdapter != null && feedsAdapter.showOnlineCelebs(position)){
                        outRect.setEmpty();
                    } else*//* if (position == parent.getAdapter().getItemCount() - 1) {
                        outRect.setEmpty();
                    } else {
                        super.getItemOffsets(outRect, view, parent, state);
                    }
                }
            };
            itemDecorator.setDrawable(ContextCompat.getDrawable(activity(), R.drawable.recyclerview_divider));
            recyclerView.addItemDecoration(itemDecorator);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //recyclerView.setNestedScrollingEnabled(false);
        //((SimpleItemAnimator) recyclerView.getItemAnimator()).setChangeDuration(0);//.setSupportsChangeAnimations(false); //
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            isFeedTask = false;
            isOnlineTask = false;
            fetchFeeds(null);
            ChatSocket.getInstance().numberOfUnSeenMessageEmit();
            SocketForAppUtill.getInstance().onlineCelebEmit();
//            ServiceSockets.onServiceSocketListeners();
            stopVideoPlayer(true);

        });
//        fetchOnlineUsers();
        fetchFeeds(null);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx == 0 && dy == 0) {
                    return;
                }
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (!recyclerView.canScrollVertically(1)) {
                    loadMore(true);
                }
                if (pastVisibleItems + visibleItemCount > Constants.RECYCLER_MOVE_TO_TOP) {
                    llMoveUp.setVisibility(View.VISIBLE);
                } else {
                    llMoveUp.setVisibility(View.GONE);
                }
                //
                stopRunningVideoPlayerItem(runningVideoItemPosition);
                startAutoPlayVideos(false);
            }
        });
        tvFeedRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryLoadMore();
            }
        });
    }

    private void stopRunningVideoPlayerItem(int runningVideoItemPosition) {
        if (runningVideoItemPosition < 0) {
            return;
        }
        try {
            Rect rvRect = new Rect();
            recyclerView.getGlobalVisibleRect(rvRect);
            int percentage = getItemVisiblePercentage(runningVideoItemPosition, rvRect);
            Log.d("videoPlayerItem", runningVideoItemPosition + " @ " + percentage);
            if (percentage < 50) {
                stopVideoPlayer(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startAutoPlayVideos(boolean resumePlayerIfPossible) {
        if (runningVideoItemPosition > -1) {
            if (resumePlayerIfPossible && player != null) {
                resumeVideoPlayer();
            }
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (feedsAdapter != null && SessionManager.getInstance().getKeyValue(SessionManager.KEY_AUTO_PLAY_VIDEOS, false)) {
                    if (findVisibleVideoView != null) {
                        findVisibleVideoView.cancel(true);
                    }
                    findVisibleVideoView = new FindVisibleVideoView();
                    findVisibleVideoView.execute();
                }
            }
        });
    }

    @SuppressLint({"StaticFieldLeak", "WrongThread"})
    private class FindVisibleVideoView extends AsyncTask<String, Void, Integer> {
        Rect rvRect = new Rect();

        public FindVisibleVideoView() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlVideoViewSparseArray = new SparseArray<>();
            autoPlayVideoMediaList = new ArrayList<>();
            recyclerView.getGlobalVisibleRect(rvRect);
        }

        @Override
        protected Integer doInBackground(String... params) {
            int finalVideoViewPosition = -1;
            final int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
            final int lastPosition = linearLayoutManager.findLastVisibleItemPosition();
            FeedViewHolder feedViewHolder = null;
            for (int i = firstPosition; i <= lastPosition; i++) {
                int percentage = getItemVisiblePercentage(i, rvRect);
                Log.v("percentFirst " + i, "percentFirst " + percentage);
                if (percentage > 50) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
                    if (viewHolder instanceof FeedViewHolder) {
                        feedViewHolder = (FeedViewHolder) viewHolder;
                        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_FEED_GRID_STYLE, false)) {
                            View myView = feedViewHolder.viewPager.findViewWithTag("View" + feedViewHolder.viewPager.getCurrentItem());
                            findVideoView(myView);
                        } else {
                            findVideoView(feedViewHolder.collageView);
                        }
                    }
                    if (rlVideoViewSparseArray.size() > 0) {
                        finalVideoViewPosition = i;
                        break;
                    }
                }
            }
            if (finalVideoViewPosition >= 0 && finalVideoViewPosition != runningVideoItemPosition) {
                int listIndex = feedsAdapter.getPositionWithoutHeaders(finalVideoViewPosition);
                try {
                    if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_FEED_GRID_STYLE, false)) {
                        if (feedViewHolder != null) {
                            autoPlayVideoMediaList.add(feedList.get(listIndex).mediaList.get(feedViewHolder.viewPager.getCurrentItem()));
                        }
                    } else {
                        for (int i = 0; i < feedList.get(listIndex).mediaList.size(); i++) {
                            if (i < 5) {
                                Media media = feedList.get(listIndex).mediaList.get(i);
                                if (media.type != null && media.type.equals(Constants.MEDIA_TYPE_VIDEO)) {
                                    autoPlayVideoMediaList.add(media);
                                }
                            } else {
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return finalVideoViewPosition;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result > -1) {
                if (runningVideoItemPosition == result) {
                    resumeVideoPlayer();
                } else if (rlVideoViewSparseArray.size() > 0 && autoPlayVideoMediaList.size() > 0 && isVisibleToUser) {
                    runningVideoItemPosition = result;
                    startVideoPlayer(rlVideoViewSparseArray, autoPlayVideoMediaList, 0);
                }
            }
        }
    }

    private int getItemVisiblePercentage(int position, Rect rvRect) {
        int percentFirst = 0;
        try {
            Rect rowRect = new Rect();
            Objects.requireNonNull(linearLayoutManager.findViewByPosition(position)).getGlobalVisibleRect(rowRect);
            if (rowRect.bottom >= rvRect.bottom) {
                int visibleHeightFirst = rvRect.bottom - rowRect.top;
                percentFirst = (visibleHeightFirst * 100) / Objects.requireNonNull(linearLayoutManager.findViewByPosition(position)).getHeight();
            } else {
                int visibleHeightFirst = rowRect.bottom - rvRect.top;
                percentFirst = (visibleHeightFirst * 100) / Objects.requireNonNull(linearLayoutManager.findViewByPosition(position)).getHeight();
            }
            if (percentFirst > 100) {
                percentFirst = 100;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return percentFirst;
    }

    private void findVideoView(View v) {
        try {
            if (v instanceof PlayerView) {
                PlayerView playerView = (PlayerView) v;
                View view = (View) playerView.getParent().getParent();
                if (view instanceof RelativeLayout) {
                    rlVideoViewSparseArray.put(rlVideoViewSparseArray.size(), (RelativeLayout) view);
                }
            } else if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    findVideoView(child);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startVideoPlayer(SparseArray<RelativeLayout> relativeLayoutList, List<Media> mediaList, int videoPosition) {
        stopVideoPlayer(false);
        try {
            Feed feed = feedList.get(feedsAdapter.getPositionWithoutHeaders(runningVideoItemPosition));
            if (feed.isUpdating || feed.isHide) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            videoPlayingPosition = videoPosition;
            Media media = mediaList.get(videoPosition);
            RelativeLayout rlVideoView = (RelativeLayout) relativeLayoutList.get(videoPosition).getChildAt(0);
            RelativeLayout rlVideoImage = (RelativeLayout) relativeLayoutList.get(videoPosition).getChildAt(1);
            RelativeLayout rlVideoControls = (RelativeLayout) relativeLayoutList.get(videoPosition).getChildAt(2);
            ImageView ivVideoPlayIcon = (ImageView) rlVideoImage.getChildAt(1);
            TextView tvTime = (TextView) rlVideoControls.getChildAt(1);
            setVideoLayout(relativeLayoutList.get(videoPosition), true, false, 0);
            PlayerView playerView = (PlayerView) rlVideoView.getChildAt(0);
            tvTime.setText("00:00");
            //
            playerView.requestFocus();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            player = ExoPlayerFactory.newSimpleInstance(activity(), trackSelector);
            playerView.setPlayer(player);
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
            if(mediaList.size() > 1){
                player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            }
            player.setPlayWhenReady(true);
            if (relativeLayoutList.size() == 1) {
                player.setRepeatMode(Player.REPEAT_MODE_ONE);
            }
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(activity(), Util.getUserAgent(activity(), Constants.APP_NAME));
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(Constants.MEDIA_BASE_URL + media.source.videoUrl));
            //MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(Constants.MEDIA_BASE_URL + media.source.videoUrl));
            setMuteUnMute(SessionManager.getInstance().getKeyValue(SessionManager.KEY_IS_MUTE_ENABLED, false));
            player.prepare(mediaSource);
            //
            player.addListener(new Player.DefaultEventListener() {
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    super.onPlayerStateChanged(playWhenReady, playbackState);
                    Log.d("videoPlaybackState", "" + playbackState);
                    if (playbackState == Player.STATE_IDLE || playbackState == Player.STATE_ENDED || !playWhenReady) {
                        playerView.setKeepScreenOn(false);
                    } else { // STATE_IDLE, STATE_ENDED
                        // This prevents the screen from getting dim/lock
                        playerView.setKeepScreenOn(true);
                    }
                    try {
                        if (playbackState == Player.STATE_ENDED) {
                            if (relativeLayoutList.size() > 1) {
                                setVideoLayout(relativeLayoutList.get(videoPosition), false, false, 1);
                                stopVideoPlayer(false);
                                if (videoPosition == (mediaList.size() - 1)) {
                                    startVideoPlayer(relativeLayoutList, mediaList, 0);
                                } else if (videoPosition < (mediaList.size() - 1)) {
                                    startVideoPlayer(relativeLayoutList, mediaList, videoPosition + 1);
                                }
                            }
                        } else if (playWhenReady && playbackState == Player.STATE_READY) {
                            // media actually playing
                            if (!SessionManager.getInstance().getKeyValue(SessionManager.KEY_IS_MUTE_ENABLED, false)) {
                                Common.getInstance().stopOtherBackgroundMusic(activity());
                            }
                            setVideoDuration();
                            setVideoLayout(relativeLayoutList.get(videoPosition), true, true, 2);
                        } else if (playWhenReady) {
                            // might be idle (plays after prepare()),
                            // buffering (plays when data available)
                            // or ended (plays when seek away from end)
                        } else {
                            // player paused in any state
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setVideoLayout(RelativeLayout rlParent, boolean show, boolean isVideoPlaying, int fromWhich) {
        Log.d("setVideoLayout", fromWhich + " " + show + " " + isVideoPlaying);
        RelativeLayout rlVideoView = (RelativeLayout) rlParent.getChildAt(0);
        RelativeLayout rlVideoImage = (RelativeLayout) rlParent.getChildAt(1);
        RelativeLayout rlVideoControls = (RelativeLayout) rlParent.getChildAt(2);
        ImageView ivVideoPlayIcon = (ImageView) rlVideoImage.getChildAt(1);
        TextView tvTime = (TextView) rlVideoControls.getChildAt(1);
        //
        rlVideoImage.setVisibility(isVideoPlaying ? View.GONE : View.VISIBLE);
        rlVideoView.setVisibility(show ? View.VISIBLE : View.GONE);
        rlVideoControls.setVisibility(show ? View.VISIBLE : View.GONE);
        ivVideoPlayIcon.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    public void resumeVideoPlayer() {
        if (!SessionManager.getInstance().getKeyValue(SessionManager.KEY_IS_MUTE_ENABLED, false)) {
            Common.getInstance().stopOtherBackgroundMusic(activity());
        }
        if (player != null && runningVideoItemPosition > -1) {
            boolean isFeedUpdating = false;
            if (feedsAdapter != null) {
                isFeedUpdating = feedList.get(feedsAdapter.getPositionWithoutHeaders(runningVideoItemPosition)).isUpdating;
            }
            if (!isFeedUpdating) {
                player.setPlayWhenReady(true);
                player.getPlaybackState();
                setVideoDuration();
            } else {
                stopVideoPlayer(true);
            }
        } else {
            startAutoPlayVideos(false);
        }
    }

    public void stopVideoPlayer(boolean isAllCompleted) {
        try {
            if (findVisibleVideoView != null) {
                findVisibleVideoView.cancel(true);
            }
            if (player != null) {
                player.setPlayWhenReady(false);
                player.getPlaybackState();
            }
            if (runnableVideoDuration != null) {
                videoDurationHandler.removeCallbacks(runnableVideoDuration);
            }
            if (rlVideoViewSparseArray.size() > 0 && videoPlayingPosition > -1) {
                setVideoLayout(rlVideoViewSparseArray.get(videoPlayingPosition), false, false, 4);
            }
            if (isAllCompleted) {
                runningVideoItemPosition = -1;
                videoPlayingPosition = -1;
                if (player != null) {
                    player.setPlayWhenReady(false);
                    player.stop();
                    player.seekTo(0);
                    player = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMuteUnMute(boolean muteEnable) {
        try {
            if (player != null) {
                if (currentVideoVolume <= 0) {
                    AudioManager audio = (AudioManager) activity().getSystemService(Context.AUDIO_SERVICE);
                    assert audio != null;
                    currentVideoVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
                }
                if (muteEnable) {
                    player.setVolume(0f);
                } else {
                    Common.getInstance().stopOtherBackgroundMusic(activity());
                    player.setVolume(currentVideoVolume);
                }
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_MUTE_ENABLED, muteEnable);
                //
                if (rlVideoViewSparseArray.size() > 0 && videoPlayingPosition > -1) {
                    RelativeLayout rlVideoControls = (RelativeLayout) rlVideoViewSparseArray.get(videoPlayingPosition).getChildAt(2);
                    ImageView ivMuteUnMute = (ImageView) rlVideoControls.getChildAt(2);
                    ivMuteUnMute.setImageResource(muteEnable ? R.drawable.ic_mute : R.drawable.ic_un_mute);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setVideoDuration() {
        runnableVideoDuration = () -> {
            try {
                if (player != null && rlVideoViewSparseArray.size() > 0 && videoPlayingPosition > -1) {
                    long totalDuration = player.getDuration();
                    long currentDuration = player.getCurrentPosition();
                    String secondsString;
                    String minutesString;
                    int diffDuration = (int) (totalDuration - currentDuration);
                    int minutes = (int) (diffDuration % (1000 * 60 * 60)) / (1000 * 60);
                    int seconds = (int) ((diffDuration % (1000 * 60 * 60)) % (1000 * 60) / 1000);
                    if (seconds < 10) {
                        secondsString = "0" + seconds;
                    } else {
                        secondsString = "" + seconds;
                    }
                    if (minutes < 10) {
                        minutesString = "0" + minutes;
                    } else {
                        minutesString = "" + minutes;
                    }
                    /*int durationSeconds = (int) ((totalDuration - currentDuration) / 1000);
                    int durationMinutes = (durationSeconds % 3600) / 60;*/
                    RelativeLayout rlVideoControls = (RelativeLayout) rlVideoViewSparseArray.get(videoPlayingPosition).getChildAt(2);
                    TextView tvTime = (TextView) rlVideoControls.getChildAt(1);
                    if (tvTime != null && seconds > 0) {
                        if (!SessionManager.getInstance().getKeyValue(SessionManager.KEY_IS_MUTE_ENABLED, false)) {
                            Common.getInstance().stopOtherBackgroundMusic(activity());
                        }
                        tvTime.setText(minutesString + ":" + secondsString);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (runnableVideoDuration != null) {
                videoDurationHandler.postDelayed(runnableVideoDuration, 1000);
            }
        };
        videoDurationHandler.postDelayed(runnableVideoDuration, 0);
    }

    public void setReloadAdapterChanges(Boolean reloadAdapterChanges) {
        this.reloadAdapterChanges = reloadAdapterChanges;
    }

    private void setFeedLoaderANDError(boolean isLoader, boolean isError) {
        if (isLoader) {
            llFooterParent.setVisibility(View.VISIBLE);
            llFeedLoader.setVisibility(View.VISIBLE);
            llFeedNetworkError.setVisibility(View.GONE);
        } else if (isError) {
            llFooterParent.setVisibility(View.VISIBLE);
            llFeedLoader.setVisibility(View.GONE);
            llFeedNetworkError.setVisibility(View.VISIBLE);
        } else {
            llFooterParent.setVisibility(View.GONE);
        }
    }

    public void scrollToTop() {
        try {
            if (feedsAdapter != null) {
                recyclerView.scrollToPosition(0);
                llMoveUp.setVisibility(View.GONE);
                startAutoPlayVideos(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        Log.d("onResume", "onResume");
        instance = this;
        isVisibleToUser = true;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (reloadAdapterChanges && feedsAdapter != null && feedsAdapter.rController != RController.LOADING) {
                    reloadAdapterChanges = false;
                    feedsAdapter.notifyDataSetChanged();
                    //stopVideoPlayer(false);
                }
            }
        });
        if (isFragLoaded && !isUpdateFeedMethodCalled) {
            Activity activity = activity();
            if (activity instanceof BottomMenuActivity && ((BottomMenuActivity) activity).getCurrentViewPagerPosition() == 0) {
                startAutoPlayVideos(true);
            } else if (activity instanceof HelperActivity) {
                startAutoPlayVideos(true);
            }
        }
        if (!isFragLoaded) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    isFragLoaded = true;
                }
            }, 1000);
        }
        isUpdateFeedMethodCalled = false;
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        isVisibleToUser = false;
        isUpdateFeedMethodCalled = false;
        stopVideoPlayer(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            stopVideoPlayer(true);
            onResume();
        } else {
            onPause();
        }
    }

    private void showFeedsError(String content, String note, int imageId) {
        recyclerView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        recyclerView.setAdapter(feedsAdapter = new FeedsAdapter(new ArrayList<>(), storyProfileInfoList, activity(), RController.DONE, iFeedAdapter, CelebId != null));
        tvContent.setText(note);
        ivImage.setImageResource(imageId);
        tvTitle.setText(content);
        llNoFeeds.setVisibility(View.VISIBLE);
    }

    private void addFeedAdapter() {
        if (isOnlineTask && isFeedTask) {
            stopVideoPlayer(true);
            if (feedList != null && feedList.size() > 0) {
                recyclerView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT));

                recyclerView.setAdapter(feedsAdapter = new FeedsAdapter(feedList, storyProfileInfoList
                        , activity(), RController.LOADED, iFeedAdapter, CelebId != null));

                llNoFeeds.setVisibility(View.GONE);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startAutoPlayVideos(false);
                    }
                }, 200);
            } else {
                showFeedsError(Constants.UH_OH, Constants.NO_FEEDS, R.drawable.ic_nodata);
            }
        }
    }

    public void fetchStories() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(ApiClient.GET_STORIES_CELEBS + Common.isLoginId() + "/0");
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.GET_STORIES_CELEBS, false,
                iApiListener, false));
    }

    public void fetchFeeds(Feed feed) {
        llNoFeeds.setVisibility(View.GONE);
        setFeedLoaderANDError(false, false);
        if (Utility.isNetworkAvailable(getActivity())) {
            isLoadMoreBusy = false;
            recyclerView.setAdapter(feedsAdapter = new FeedsAdapter(null, storyProfileInfoList, activity(), RController.LOADING, iFeedAdapter, false));
            IApiListener iApiListener = new IApiListener() {
                @Override
                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                    if (apiResponseModel.success.equals(Constants.TOKEN_EXPIRED) || apiResponseModel.success.equals(Constants.TOKEN_AUTHENTICATION_FAIL)) {
                        Common.getInstance().LogOut(activity(), "false", apiResponseModel.success);
                        Common.getInstance().cusToast(activity(), apiResponseModel.message);
                        return;
                    }
                    isFeedTask = true;
                    isOnlineTask = true;
                    Type type = new TypeToken<List<Feed>>() {
                    }.getType();
                    if (apiResponseModel.data != null) {
                        feedList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    }
                    addFeedAdapter();
                    fetchStories();
                    if (feedsAdapter != null && feedList != null && feedList.size() < Constants.FEEDS_LOAD_MORE_COUNT) {
                        feedsAdapter.donePagination();
                    }
                }

                @Override
                public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                    isFeedTask = true;
                    showFeedsError(Constants.SOMETHING_WENT_WRONG_SERVER, Constants.DRAG_TO_RETRY, R.drawable.ic_network);
                    showSnackBar(Constants.SOMETHING_WENT_WRONG_SERVER, 2);
                }
            };
            int feedSession;
            if (Common.isFeedSessionStarted) {
                feedSession = 0;
            } else {
                feedSession = -1;
                Common.isFeedSessionStarted = true;
            }
            //
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            if (CelebId == null) {
                Call<ApiResponseModel> call = apiInterface.GET(Constants.FeedConstants.URL_GET_FEED_NEW + SessionManager.userLogin.userId + "/" + ((feed == null) ? String.valueOf(feedSession) : feed.createdDate));
                Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.URL_GET_FEED_NEW, false, iApiListener, false));
            } else {
                Call<ApiResponseModel> call = apiInterface.GET(ApiClient.MY_CONTENTS + CelebId + "/" + SessionManager.userLogin.userId + "/null/" + LIMIT_COUNT);
                Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.MY_CONTENTS, false, iApiListener, false));
            }
        } else {
            showFeedsError(Constants.UH_OH, Constants.PLEASE_CHECK_INTERNET, R.drawable.ic_network);
        }
    }

    public void loadMore(Boolean isFromRecyclerScroll) {
        try {
            if (feedsAdapter == null || isLoadMoreBusy || feedsAdapter.rController == RController.LOADING
                    || feedsAdapter.rController == RController.DONE || feedList == null || feedList.get(feedList.size() - 1) == null
                    || (feedsAdapter.getSuggestionType(feedsAdapter.getItemCount() - 1) == Constants.FOOTER_ERROR_RETRY
                    && isFromRecyclerScroll)) {
                return;
            }
            Feed feedInner = feedList.get(feedList.size() - 1);
            if (Utility.isNetworkAvailable(activity())) {
                //feedsAdapter.addBottomLoader();
                setFeedLoaderANDError(true, false);
                isLoadMoreBusy = true;
                IApiListener iApiListener = new IApiListener() {
                    @Override
                    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                        isLoadMoreBusy = false;
//                    feedsAdapter.removeAllFooterOptions();
                        setFeedLoaderANDError(false, false);
                        Type type = new TypeToken<List<Feed>>() {
                        }.getType();
                        List<Feed> appendList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                        //don't remove handler here otherwise you'll get bug
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (appendList != null && appendList.size() > 0) {
                                    feedsAdapter.loadMore(new ArrayList<>(appendList));
                                }
                                if (appendList != null && appendList.size() < Constants.FEEDS_LOAD_MORE_COUNT) {
                                    feedsAdapter.donePagination();
                                }
                            }
                        }, 500);
                    }

                    @Override
                    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                        isLoadMoreBusy = false;
//                    feedsAdapter.removeAllFooterOptions();
//                    feedsAdapter.addRetryOption();
                        setFeedLoaderANDError(false, true);
                    }
                };
                int feedSession;
                if (Common.isFeedSessionStarted) {
                    feedSession = 0;
                } else {
                    feedSession = -1;
                    Common.isFeedSessionStarted = true;
                }
                //
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                if (CelebId == null) {
                    Call<ApiResponseModel> call = apiInterface.GET(Constants.FeedConstants.URL_GET_FEED_NEW + SessionManager.userLogin.userId + "/" + ((feedInner == null) ? String.valueOf(feedSession) : feedInner.createdDate));
                    Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.URL_GET_FEED_NEW, false, iApiListener, false));
                } else {
                    Call<ApiResponseModel> call = apiInterface.GET(ApiClient.MY_CONTENTS + CelebId + "/" + SessionManager.userLogin.userId + "/" + feedInner.createdDate + "/" + LIMIT_COUNT);
                    Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.MY_CONTENTS, false, iApiListener, false));
                }
            } else {
                showSnackBar(Constants.NO_INTERNET, 2);
//                feedsAdapter.addRetryOption();
                setFeedLoaderANDError(false, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showFeedProgress(boolean isUpload, int position) {
        if (feedsAdapter != null && feedsAdapter.rController != RController.LOADING) {
            feedsAdapter.isUpload = isUpload;
            feedsAdapter.notifyItemChanged(position);
            swipeRefreshLayout.setEnabled(!isUpload);
            stopVideoPlayer(false);
        }
    }

    public void showFeedUpdate(boolean isUpdate, int position) {
        Logger.d("Changing Feed", "" + position);
        if (feedsAdapter != null && feedsAdapter.rController != RController.LOADING) {
            feedList.get(position).isUpdating = isUpdate;
            position = feedsAdapter.getAdapterPositionWithHeaders(position);
            feedsAdapter.notifyItemChanged(position);
            stopVideoPlayer(false);
            swipeRefreshLayout.setEnabled(!isUpdate);
            refreshFeedAdapter();
        }
    }

    public void addFeed(Feed feed) {
        if (feedsAdapter != null && feedsAdapter.rController != RController.LOADING && feed != null) {
            feedList.add(0, feed);
            addFeedAdapter();
        }
    }

    public void isFeedUpdateRunning(boolean isFeedUpdateRunning) {
        this.isFeedUpdateRunning = isFeedUpdateRunning;
        if (isFeedUpdateRunning) {
            stopVideoPlayer(true);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void updateFeed(Feed feed, int position) {
        Log.d("onResume", "updateFeed");
        isUpdateFeedMethodCalled = true;
        if (feed != null && feed.feedMemberDetails != null && feedsAdapter != null && feedsAdapter.rController != RController.LOADING) {
            stopVideoPlayer(true);
            feed.isBusyLike = false;
            if (feed.mediaList != null && feed.mediaList.size() > 0) {
                for (int i = 0; i < feed.mediaList.size(); i++) {
                    feed.mediaList.get(i).isBusyLike = false;
                }
            }
            if (position > -1 && position < feedList.size()) {
                if (feedList.get(position).id.equals(feed.id)) {
                    feedList.set(position, feed);
                    position = feedsAdapter.getAdapterPositionWithHeaders(position);
                    feedsAdapter.notifyItemChanged(position);
                    //feedsAdapter.notifyDataSetChanged();
                } else {
                    new AsyncTask<Void, Void, Integer>() {
                        @Override
                        protected Integer doInBackground(Void... voids) {
                            return setGFeedHardly(feed);
                        }

                        @Override
                        protected void onPostExecute(Integer result) {
                            super.onPostExecute(result);
                            if (result != -1) {
                                feedsAdapter.notifyItemChanged(result);
                            }
                        }
                    }.execute();
                }
            } else {
                new AsyncTask<Void, Void, Integer>() {
                    @Override
                    protected Integer doInBackground(Void... voids) {
                        return setGFeedHardly(feed);
                    }

                    @Override
                    protected void onPostExecute(Integer result) {
                        super.onPostExecute(result);
                        if (result != -1) {
                            feedsAdapter.notifyItemChanged(result);
                        }
                    }
                }.execute();
            }
            startAutoPlayVideos(false);
        }
    }

    public void updateFeedList(List<Feed> feedList, int position) {
        Log.d("onResume", "updateFeed");
        isUpdateFeedMethodCalled = true;
        for (int j = 0; j < feedList.size(); j++) {
            if (feedList.get(j) != null && feedsAdapter != null && feedsAdapter.rController != RController.LOADING) {
                stopVideoPlayer(true);
                feedList.get(j).isBusyLike = false;
                if (feedList.get(j).mediaList != null && feedList.get(j).mediaList.size() > 0) {
                    for (int i = 0; i < feedList.get(i).mediaList.size(); i++) {
                        feedList.get(i).mediaList.get(i).isBusyLike = false;
                    }
                }
                if (position > -1 && position < feedList.size()) {
                    if (feedList.get(position).id.equals(feedList.get(j).id)) {
                        feedList.set(position, feedList.get(j));
                        position = feedsAdapter.getAdapterPositionWithHeaders(position);
                        feedsAdapter.notifyItemChanged(position);
                        //feedsAdapter.notifyDataSetChanged();
                    } else {
                        int finalJ1 = j;
                        new AsyncTask<Void, Void, Integer>() {
                            @Override
                            protected Integer doInBackground(Void... voids) {
                                return setGFeedHardly(feedList.get(finalJ1));
                            }

                            @Override
                            protected void onPostExecute(Integer result) {
                                super.onPostExecute(result);
                                if (result != -1) {
                                    feedsAdapter.notifyItemChanged(result);
                                }
                            }
                        }.execute();
                    }
                } else {
                    int finalJ = j;
                    new AsyncTask<Void, Void, Integer>() {
                        @Override
                        protected Integer doInBackground(Void... voids) {
                            return setGFeedHardly(feedList.get(finalJ));
                        }

                        @Override
                        protected void onPostExecute(Integer result) {
                            super.onPostExecute(result);
                            if (result != -1) {
                                feedsAdapter.notifyItemChanged(result);
                            }
                        }
                    }.execute();
                }
                startAutoPlayVideos(false);
            }
        }
    }

    public int setGFeedHardly(Feed feed) {
        try {
            if (feed != null) {
                feed.isBusyLike = false;
                if (feed.mediaList != null && feed.mediaList.size() > 0) {
                    for (int j = 0; j < feed.mediaList.size(); j++) {
                        feed.mediaList.get(j).isBusyLike = false;
                    }
                }
            }
            if (feedList != null && feedList.size() > 0) {
                for (int i = 0; i < feedList.size(); i++) {
                    if (feedList.get(i).id.equals(feed.id)) {
                        feedList.set(i, feed);
                        i = feedsAdapter.getAdapterPositionWithHeaders(i);
                        return i;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void navigatetoCelebProfile(int position, List<Celebrity> list, OnlineCelebritiesViewHolder onlineCelebritiesViewHolder) {
        try {
            ProfileDataModel profileDataModel = new ProfileDataModel();
            profileDataModel._id = list.get(position).id;
            profileDataModel.firstName = list.get(position).firstName;
            profileDataModel.lastName = list.get(position).lastName;
            profileDataModel.isCeleb = list.get(position).isCeleb;
            profileDataModel.role = list.get(position).role;
            profileDataModel.avtar_imgPath = list.get(position).avatarImgPath;
            profileDataModel.isOnline = list.get(position).isOnline;
            profileDataModel.profession = list.get(position).profession;
            profileDataModel.aboutMe = list.get(position).aboutMe;
            profileDataModel.isFan = list.get(position).isFan;
//            Common.getInstance().viewCelebProfileDialog(activity(), profileDataModel, progressDialog);
            //checking with customize class
            Common.getInstance().viewCelebProfileDialog(activity(), profileDataModel, progressDialog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void feedContentViewLess(int position) {
        try {
            if (feedsAdapter != null) {
                position = feedsAdapter.getAdapterPositionWithHeaders(position);
                recyclerView.scrollToPosition(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openKonect(Feed feed, int position) {
        try {
            KonectData konectData = new KonectData(feed.feedMemberDetails.isCeleb, feed.feedMemberDetails._id, feed.feedMemberDetails.avtar_imgPath, feed.feedMemberDetails.profession, feed.feedMemberDetails.lastName, feed.feedMemberDetails.firstName, false, "");
            Common.getInstance().openKonectPopup(activity(), konectData, new IKonectDailogClick() {
                @Override
                public void IKonectDailogClick(KonectData konectData) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openOptionDailog(Feed feed, int position) {
        try {
            FeedOptionsDailog konectCelebDailog = new FeedOptionsDailog();
            konectCelebDailog.setData(feed, position, feedList);
            konectCelebDailog.show(getFragmentManager(), "FeedOptionsDailog");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        showSnackBar(activity(), coordinatorLayout, snackBarText, type);
    }

    public static void showSnackBar(Context activity, CoordinatorLayout coordinatorLayout, String text, int type) {
        if (coordinatorLayout == null || text == null || activity == null) {
            return;
        }
        Snackbar snackBar = Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_SHORT);
        TextView txtMessage = (TextView) snackBar.getView().findViewById(R.id.snackbar_text);
        txtMessage.setTextColor(ContextCompat.getColor(activity, R.color.white));
        if (type == 2)
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.black));
        else if (type == 1)
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.app_snack_bar_true));
        else if (type == 3)
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.app_snack_bar_true));
        else {
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.mainColorPrimary));
        }
        txtMessage.setTypeface(Utility.getTypeface(1, activity));
        //
        if (BottomMenuActivity.isCurvedBottomBar) {
            int paddingToBottom = activity.getResources().getDimensionPixelSize(R.dimen._48sdp);
            final View snackBarView = snackBar.getView();
            final CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackBarView.getLayoutParams();
            params.setMargins(0, 0, 0, paddingToBottom);
            snackBarView.setLayoutParams(params);
        }
        //
        snackBar.show();
    }

    @Override
    public Activity activity() {
        return getActivity() == null ? AppController.getInstance().getCurrentRegisteredActivity() : getActivity();
    }

    @Override
    public void addMedia() {
        ((BottomMenuActivity) activity()).isCreateStore = false;
        Common.getInstance().addMediaDirectly(activity(), "Feed");
    }

    @Override
    public void retryLoadMore() {
        loadMore(false);
    }

    @Override
    public void showAlert(String content, int type) {
        showSnackBar(content, type);
    }

    @Override
    public void viewFeedDetails(Feed feed, int position, int mediaPosition) {
        Common.getInstance().openFeedDetailsFrag(activity(), feed, position, mediaPosition);
    }

    @Override
    public void viewCelebDetails(List<Feed> feeds, int position, boolean isSelf) {
        Intent profileIntent = new Intent(activity(), HelperActivity.class);
        if (isSelf) {
            profileIntent.putExtra(Constants.FRAGMENT_KEY, 8038);
            profileIntent.putExtra(Constants.FRAGMENT_TITLE, "My Profile");
            activity().startActivity(profileIntent);
        } else {
            openProfilePopup(feeds, position);
        }
    }

    @Override
    public void openImage(Feed feed, Media media, int position) {
        //Common.getInstance().openMediaDetailsFragment("", "", activity(), feed, feed.mediaList, -1, false, false, position, "", true);
    }

    @Override
    public void likeAction(Feed feed, int position, FeedViewHolder feedViewHolder) {
        if (!feedList.get(position).isBusyLike) {
            feedList.get(position).isBusyLike = true;
            setFeedLikeResponse(feed, position, feedViewHolder, true);
            if (feedsAdapter != null) {
                feedsAdapter.showProgressFeedLikeUnLike(feedViewHolder, feedList.get(position));
                //
                if (Common.getInstance().getBooleanFromInt(feedList.get(position).isLike)) {
                    feedViewHolder.imgLike.setImageResource(R.color.transparent);
                    Common.getInstance().setLikeAnimation(feedViewHolder.ivLikeAnimation);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            feedViewHolder.ivLikeAnimation.setVisibility(View.GONE);
                            feedViewHolder.imgLike.setImageResource(Common.getInstance().getBooleanFromInt(feedList.get(position).isLike) ? R.drawable.ic_like_fill : R.drawable.ic_like_stroke);
                        }
                    }, 1400);
                } else {
                    feedViewHolder.imgLike.setImageResource(R.drawable.ic_like_stroke);
                }
            }
            //
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            JSONObject input = new JSONObject();
            try {
                String userid = null;
                if (SessionManager.userLogin.userId != null
                        && !SessionManager.userLogin.userId.isEmpty()) {
                    userid = SessionManager.userLogin.userId;
                }
                input.put("memberId", userid);
                input.put("celebId", feed.memberId);
                input.put("feedId", feed.id);
                input.put("isLike", !Common.getInstance().getBooleanFromInt(feed.isLike) ? 0 : 1);

                input.put("activities", Constants.ACTION_TYPE_LIKE);
                input.put("status", "Active");
            } catch (Exception e) {
                e.printStackTrace();
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), input.toString());
            Call<ApiResponseModel> call = apiInterface.POST(Constants.FeedConstants.URL_ADD_LIKE, requestBody);
            IApiListener iApiListener = new IApiListener() {
                @Override
                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                    feedList.get(position).isBusyLike = false;
                    //setFeedLikeResponse(feed,position,feedViewHolder,true);
                }

                @Override
                public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                    feedList.get(position).isBusyLike = false;
                    setFeedLikeResponse(feed, position, feedViewHolder, false);
                    if (feedsAdapter != null) {
                        feedsAdapter.showProgressFeedLikeUnLike(feedViewHolder, feedList.get(position));
                    }
                    showSnackBar(Constants.CONNECTION_ERROR, 2);
                }
            };
            Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.URL_ADD_LIKE, false, iApiListener, false));
        } else {
            showSnackBar("Its already running...", 2);
        }
    }

    private void setFeedLikeResponse(Feed feed, int position, FeedViewHolder feedViewHolder, boolean addLike) {
        if (addLike) {
            feedList.get(position).isLike = Common.getInstance().getBooleanFromInt(feed.isLike) ? 0 : 1;
            feedList.get(position).numberOfLikes = Common.getInstance().getBooleanFromInt(feedList.get(position).isLike) ? feedList.get(position).numberOfLikes + 1 : feedList.get(position).numberOfLikes - 1;
        } else {
            feedList.get(position).isLike = Common.getInstance().getBooleanFromInt(feed.isLike) ? 1 : 0;
            feedList.get(position).numberOfLikes = Common.getInstance().getBooleanFromInt(feedList.get(position).isLike) ? feedList.get(position).numberOfLikes - 1 : feedList.get(position).numberOfLikes + 1;
        }
//        feedList.get(position).isLike = Common.getInstance().getBooleanFromInt(feed.isLike) ? 0 : 1;
//        feedList.get(position).numberOfLikes = Common.getInstance().getBooleanFromInt(feedList.get(position).isLike) ? feedList.get(position).numberOfLikes + 1 : feedList.get(position).numberOfLikes - 1;
        if (feedsAdapter != null) {
            Common.getInstance().setFeedLikeUnLike(feedViewHolder, feedList.get(position), true);
        }
    }

    @Override
    public void shareToOther(Feed feed, int position) {
        Common.getInstance().feedShare(activity(), feed);
    }

    @Override
    public void userLikes(Feed feed, int position) {
        if (activity() instanceof BottomMenuActivity) {
            ((BottomMenuActivity) activity()).openFeedLikes(feed, feed.id, true, position, -1, false);
        } else {
            ((HelperActivity) activity()).openFeedLikes(feed, feed.id, true, position, -1, false);
        }
    }

    @Override
    public void addComment(Feed feed, int position) {

    }

    @Override
    public void userComments(Feed feed, int position) {
        Common.getInstance().openFeedComments(feed, feed.id, true, position, -1, false, false);
    }

    @Override
    public void createFeed() {
        ((BottomMenuActivity) activity()).openCreateFeed();
    }

    @Override
    public void openOptionsFeed(Feed feed, int position) {
    }

    @Override
    public void openVideoPlayer(Feed feed, Uri uri, Media media, int position, int mediaPosition) {
        Common.getInstance().openVideoPlayer(activity(), uri, feed.id,"", position, mediaPosition, false,"");

    }

    @Override
    public void deleteFeed(int position) {
        Logger.d("Remove Feed Position", "" + position);
        feedList.remove(position);
        position = feedsAdapter.getAdapterPositionWithHeaders(position);
        feedsAdapter.notifyItemRemoved(position);
        addFeedAdapter();
    }

    @Override
    public void hideFeed(int position, boolean ishide) {
        feedList.get(position).isHide = ishide;
        refreshFeedAdapter();
    }

    public void moveToCommentSection(AddCommentParams addCommentParams) {
        int keyboardHeight = AppController.getInstance().getKeyboardHeight();
        if (keyboardHeight <= 0) {
            return;
        }
        int[] viewLocation = new int[2];
        addCommentParams.feedViewHolder.tvAddComment.getLocationInWindow(viewLocation);
        //viewLocation[0] - here 0 is tvAddComment height
        //viewLocation[1] - here 1 is tvAddComment offset height from top
        int diffHeight = Utility.getScreenHeight(activity()) - (keyboardHeight + viewLocation[1]);
        int viewsHeight = 0;
        if (addCommentParams.feed.numberOfComments <= 0 && addCommentParams.feed.numberOfLikes <= 0) {
            viewsHeight = addCommentParams.feedViewHolder.llLikeCommentIconsParent.getMeasuredHeight();
        } else {
            viewsHeight = addCommentParams.feedViewHolder.llLikeComment.getMeasuredHeight() + addCommentParams.feedViewHolder.tvAddComment.getMeasuredHeight();
            viewsHeight = viewsHeight - 20;
        }
        recyclerView.smoothScrollBy(0, -(diffHeight - viewsHeight));
        //
        /*Logger.d("moveToCommentSection", "computeVerticalScrollOffset " + recyclerView.computeVerticalScrollOffset() + " computeVerticalScrollExtent " + recyclerView.computeVerticalScrollExtent() + " computeVerticalScrollRange " + recyclerView.computeVerticalScrollRange() + " keyboardHeight " + keyboardHeight + " viewLocation[1] " + viewLocation[1] + " ScreenHeight " + Utility.getScreenHeight(activity()) + " diffHeight " + diffHeight);*/
    }

    private float getCutoff() {
        LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
        int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
        if (lastVisibleItemPosition == RecyclerView.NO_POSITION) {
            return 0f;
        }
        View view = lm.findViewByPosition(lastVisibleItemPosition);
        float fractionOfView;
        if (view.getBottom() < recyclerView.getHeight()) { // last visible position is fully visible
            fractionOfView = 0f;
        } else { // last view is cut off and partially displayed
            fractionOfView = (float) (recyclerView.getHeight() - view.getTop()) / (float) view.getHeight();
        }
        return lastVisibleItemPosition + fractionOfView;
    }

    public void refreshFeedAdapter() {
        feedsAdapter.notifyDataSetChanged();
        stopVideoPlayer(false);
    }

    public void openProfilePopup(List<Feed> feeds, int position) {
        if (feeds.get(position).feedMemberDetails != null && feeds.get(position).feedMemberDetails.isCeleb != null) {
            if (feeds.get(position).feedMemberDetails.isCeleb) {
                ProfileDataModel profileDataModel = new ProfileDataModel();
                profileDataModel._id = feeds.get(position).memberId;
                profileDataModel.firstName = feeds.get(position).feedMemberDetails.firstName;
                profileDataModel.lastName = feeds.get(position).feedMemberDetails.lastName;
                profileDataModel.isCeleb = feeds.get(position).feedMemberDetails.isCeleb;
                profileDataModel.role = "";
                profileDataModel.avtar_imgPath = feeds.get(position).feedMemberDetails.avtar_imgPath;
                profileDataModel.isOnline = feeds.get(position).feedMemberDetails.isOnline;
                profileDataModel.profession = feeds.get(position).feedMemberDetails.profession;
                profileDataModel.aboutMe = "";
                profileDataModel.celeAudioCharge = feeds.get(position).celeAudioCharge;
                profileDataModel.celeFanCharge = feeds.get(position).celeFanCharge;
                profileDataModel.celeVideoCharge = feeds.get(position).celeVideoCharge;
                //profileDataModel.isFan = isfan;//Need to get from Backend - IS_FAN
//                Common.getInstance().viewCelebProfileDialog(activity(), profileDataModel, progressDialog);


                //checking with customize class
                Common.getInstance().viewCelebProfileDialog(activity(), profileDataModel, progressDialog);

            } else {
                Intent profileIntent = new Intent(activity(), HelperActivity.class);
                profileIntent.putExtra(Constants.FRAGMENT_KEY, 8029);
                profileIntent.putExtra(Constants.FRAGMENT_TITLE, "Celeb Profile");
                profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                profileIntent.putExtra("fromSearchResult", false);
                profileIntent.putExtra("isManagerProfile", false);
                profileIntent.putExtra("isUserProfile", true);
                if (feeds.get(position).memberId != null && !feeds.get(position).memberId.isEmpty()) {
                    profileIntent.putExtra("CelebId", feeds.get(position).memberId);
                }
                String userName = feeds.get(position).feedMemberDetails.firstName + " " + feeds.get(position).feedMemberDetails.lastName;
                if (userName != null && !userName.isEmpty()) {
                    profileIntent.putExtra("PROFILE_NAME", userName);
                } else {
                    profileIntent.putExtra("PROFILE_NAME", "");
                }
                if (feeds.get(position).feedMemberDetails.avtar_imgPath != null && !feeds.get(position).feedMemberDetails.avtar_imgPath.isEmpty()) {
                    profileIntent.putExtra("Imaage", ApiClient.BASE_URL + feeds.get(position).feedMemberDetails.avtar_imgPath);
                } else {
                    profileIntent.putExtra("Imaage", "");
                }
                if (feeds.get(position).feedMemberDetails.profession != null && !feeds.get(position).feedMemberDetails.profession.isEmpty()) {
                    profileIntent.putExtra("proffession", feeds.get(position).feedMemberDetails.profession);
                } else {
                    profileIntent.putExtra("proffession", "");
                }
                if (feeds.get(position).memberId != null && !feeds.get(position).memberId.isEmpty()) {
                    profileIntent.putExtra("ABOUT", feeds.get(position).memberId);
                } else {
                    profileIntent.putExtra("ABOUT", "");
                }
                profileIntent.putExtra("ClASS_TYPE", "Feed");
            }
        }
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_STORIES_CELEBS)) {
            storyProfileInfoList = new ArrayList<>();
            Log.e("storiesdata", true + "");
            Type type = new TypeToken<ShowCelebStoriesData>() {
            }.getType();
            ShowCelebStoriesData showCelebStoriesData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
            storyProfileInfoList = showCelebStoriesData.storyProfileInfo;

            if (feedsAdapter != null) {
                feedsAdapter.setStoriesAdapter(storyProfileInfoList);
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

    }

    public void hidePostFromFeeds(String id, int position, boolean isHideStatus) {
        Common.getInstance().showProgressDialog(activity());
        JSONObject input = new JSONObject();
        try {
            input.put("feedId", id);
            input.put("memberId", SessionManager.userLogin.userId);
            input.put("isHide", isHideStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        IApiListener iApiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                Common.getInstance().closeProgressDialog();
                hideFeed(position, isHideStatus);
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                Common.getInstance().closeProgressDialog();
                showSnackBar(Constants.CONNECTION_ERROR, 2);
            }
        };
        //
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.POST(Constants.FeedConstants.HIDE_FEED, RequestBody.create(MediaType.parse("application/json; charset=utf-8"), input.toString()));
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.HIDE_FEED, false, iApiListener, false));
    }
}
