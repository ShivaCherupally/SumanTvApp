package info.dkapp.flow.ipoll.fragments.videoplayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.appmanagers.feed.models.Feed;
import info.dkapp.flow.bottommenu.activity.BottomMenuActivity;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.celebrityprofile.CelebrityProfileFragment;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.celebflow.constants.Permission;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.DoubleTapCallback;
import info.dkapp.flow.utils.DoubleTapListener;
import info.dkapp.flow.utils.Utility;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

@SuppressLint("SyntheticAccessor,SetTextI18n")
public class VideoPlayerFragment extends Fragment implements IFragment, DoubleTapCallback {
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.playerView)
    PlayerView playerView;

    @BindView(R.id.imgClose)
    ImageView imgClose;

    @BindView(R.id.iVForword)
    ImageView iVForword;

    @BindView(R.id.iVRewind)
    ImageView iVRewind;

    @BindView(R.id.tvLikeCount)
    TextView tvLikeCount;

    @BindView(R.id.tvRewind)
    TextView tvRewind;

    @BindView(R.id.tvForword)
    TextView tvForword;

    @BindView(R.id.tvComment)
    TextView tvComment;

    @BindView(R.id.imgLike)
    ImageView imgLike;

    @BindView(R.id.imgShare)
    ImageView imgShare;

    @BindView(R.id.imgComment)
    ImageView imgComment;

    @BindView(R.id.imgDownload)
    ImageView imgDownload;

    @BindView(R.id.imageviewDelete)
    ImageView imageviewDelete;

    @BindView(R.id.progressBarDown)
    ProgressBar progressBarDown;

    @BindView(R.id.progressBarLike)
    ProgressBar progressBarLike;

    @BindView(R.id.LLRewind)
    LinearLayout LLRewind;

    @BindView(R.id.LLForward)
    LinearLayout LLForward;

    @BindView(R.id.llLikeCommentIconsParent)
    LinearLayout llLikeCommentIconsParent;

    @BindView(R.id.tvProgress)
    TextView tvProgress;

    private SimpleExoPlayer player;
    private Timeline.Window window;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;
    private Uri uri;
    private Feed feed;
    private int feedPosition = -1, mediaPosition = -1;
    private String feedId,mediaUnderScoreId, isFromWhich = "";
    private boolean hideLikeCommentSection, isSingleMediaFeed = false;

    public VideoPlayerFragment() {
        // Required empty public constructor
    }

    public static VideoPlayerFragment newInstance(Uri uri, String feedId, String mediaUnderScoreId, int feedPosition, int mediaPosition, boolean showLikeCommentSection, String isFromWhich) {
        VideoPlayerFragment fragment = new VideoPlayerFragment();
        Bundle args = new Bundle();
        args.putParcelable("Uri", uri);
        args.putString("feedId", feedId);
        args.putString("mediaUnderScoreId", mediaUnderScoreId);
        args.putInt("feedPosition", feedPosition);
        args.putInt("mediaPosition", mediaPosition);
        args.putBoolean("hideLikeCommentSection", showLikeCommentSection);
        args.putString("isFromWhich", isFromWhich);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            uri = getArguments().getParcelable("Uri");
            feedId = getArguments().getString("feedId", "");
            mediaUnderScoreId = getArguments().getString("mediaUnderScoreId", "");
            feedPosition = getArguments().getInt("feedPosition", -1);
            mediaPosition = getArguments().getInt("mediaPosition", -1);
            hideLikeCommentSection = getArguments().getBoolean("hideLikeCommentSection", false);
            isFromWhich = getArguments().getString("isFromWhich", "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ipoll_fragment_video_player, container, false);
        ButterKnife.bind(this, view);
        Common.getInstance().setVideoPlayerFragment(this);
        setUp();
        initializePlayer();
        LLForward.setOnClickListener(new DoubleTapListener(this));
        LLRewind.setOnClickListener(new DoubleTapListener(this));
        return view;
    }

    private void setUp() {
        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        window = new Timeline.Window();
        if (hideLikeCommentSection || feedId.isEmpty()) {
            llLikeCommentIconsParent.setVisibility(View.GONE);
        } else {
            getFeedById(feedId);
        }
        imgDownload.setVisibility(hideLikeCommentSection ? View.GONE : View.VISIBLE);
        imgClose.setOnClickListener(v -> {
            releasePlayer();
            activity().onBackPressed();
        });
        imgDownload.setOnClickListener(view -> {
            if (!Utility.checkPermissionRequest(Permission.WRITE_STORAGE, activity())) {
                Utility.raisePermissionRequest(Permission.WRITE_STORAGE, activity());
            } else {
                Common.getInstance().downloadFileFromUrl(uri.toString(), "video", progressBarDown, imgDownload);
            }
        });
        imgLike.setOnClickListener(v -> {
                likeActionMedia();
        });
        tvLikeCount.setOnClickListener(v -> {
                if (activity() instanceof BottomMenuActivity) {

                    ((BottomMenuActivity) activity()).openFeedLikes(feed, isSingleMediaFeed ? feed.id : feed.mediaList.get(mediaPosition).mediaId, isSingleMediaFeed,
                            feedPosition, mediaPosition, false);
                } else {
                    ((HelperActivity) activity()).openFeedLikes(feed, isSingleMediaFeed ? feed.id : feed.mediaList.get(mediaPosition).mediaId,
                            isSingleMediaFeed, feedPosition, mediaPosition, false);
                }
        });
        imgComment.setOnClickListener(v -> {
                Common.getInstance().openFeedComments(feed, isSingleMediaFeed ? feed.id : feed.mediaList.get(mediaPosition).mediaId, isSingleMediaFeed, feedPosition, mediaPosition, false, false);
        });
        tvComment.setOnClickListener(v -> {
                Common.getInstance().openFeedComments(feed, isSingleMediaFeed ? feed.id : feed.mediaList.get(mediaPosition).mediaId, isSingleMediaFeed,
                        feedPosition, mediaPosition, false, false);
        });
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Common.getInstance().feedShare(activity(), feed);
            }
        });
        imageviewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaUnderScoreId.isEmpty()){
                    Common.getInstance().cusToast(activity(), "mediaId not found");
                    return;
                }
                    deleteMedia(mediaUnderScoreId, mediaPosition);
            }
        });
    }

    public void showProgress(int progress){
        tvProgress.setText(""+progress);
    }

    private void initializePlayer() {
        if (uri == null) {
            return;
        }
        playerView.requestFocus();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(activity(), trackSelector);
        if (Common.getInstance().stopOtherBackgroundMusic(activity())) {
            playerView.setPlayer(player);
            player.setPlayWhenReady(shouldAutoPlay);
        }
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(activity(), Util.getUserAgent(activity(), Constants.APP_NAME));
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        player.prepare(mediaSource);

    }

    private void releasePlayer() {
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            player.stop();
            player.seekTo(0);
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    public void resumeVideoPlayer() {
        if (player != null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        }
    }

    public void pauseVideoPlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class asyncTaskFindMediPosition extends AsyncTask<String, Void, String> {

        public asyncTaskFindMediPosition() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            if(feed == null || feed.mediaList == null){
                return "";
            }
            for (int i = 0; i < feed.mediaList.size(); i++) {
                try {
                    if(uri.toString().contains(feed.mediaList.get(i).source.videoUrl)){
                        mediaPosition = i;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(mediaPosition > -1){
                llLikeCommentIconsParent.setVisibility(View.VISIBLE);
                updateLikeCount();
                updateCommentCount();
                if (isFromWhich.equalsIgnoreCase("MediaSection")) {
                    if (feed.memberId.equals(Common.isLoginId())) {
                        imageviewDelete.setVisibility(View.VISIBLE);
                    } else {
                        imageviewDelete.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    private void likeActionMedia() {
        if(mediaPosition < 0){
            return;
        }
        if (!feed.mediaList.get(mediaPosition).isBusyLike) {
            feed.mediaList.get(mediaPosition).isBusyLike = true;
            progressBarLike.setVisibility(View.VISIBLE);
            imgLike.setVisibility(View.GONE);
            IApiListener iApiListener = new IApiListener() {
                @Override
                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                    progressBarLike.setVisibility(View.GONE);
                    imgLike.setVisibility(View.VISIBLE);
                    feed.mediaList.get(mediaPosition).isBusyLike = false;
                    if (!isSingleMediaFeed) {
                        feed.mediaList.get(mediaPosition).isLike = Common.getInstance().getBooleanFromInt(feed.mediaList.get(mediaPosition).isLike) ? 0 : 1;
                        feed.mediaList.get(mediaPosition).numberOfLikes = Common.getInstance().getBooleanFromInt(feed.mediaList.get(mediaPosition).isLike) ?
                                feed.mediaList.get(mediaPosition).numberOfLikes + 1 : feed.mediaList.get(mediaPosition).numberOfLikes - 1;
                    } else {
                        feed.isLike = Common.getInstance().getBooleanFromInt(feed.isLike) ? 0 : 1;
                        feed.numberOfLikes = Common.getInstance().getBooleanFromInt(feed.isLike) ?
                                feed.numberOfLikes + 1 : feed.numberOfLikes - 1;
                    }
                    updateLikeCount();
                    applyUpdates(feed);
                }

                @Override
                public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                    progressBarLike.setVisibility(View.GONE);
                    imgLike.setVisibility(View.VISIBLE);
                    feed.mediaList.get(mediaPosition).isBusyLike = false;
                    showSnackBar(Constants.CONNECTION_ERROR, 2);
                }
            };
            Common.getInstance().addLike(isSingleMediaFeed ? feed.id : feed.mediaList.get(mediaPosition).mediaId, feed.memberId, feed.id,
                    isSingleMediaFeed ? feed.isLike : feed.mediaList.get(mediaPosition).isLike, isSingleMediaFeed, iApiListener);
        } else {
            showSnackBar("Its already running...", 2);
        }
    }

    private void updateLikeCount() {
        if (!isSingleMediaFeed) {
            tvLikeCount.setText(feed.mediaList.get(mediaPosition).numberOfLikes + "");
            imgLike.setImageResource(Common.getInstance().getBooleanFromInt(feed.mediaList.get(mediaPosition).isLike) ? R.drawable.ic_like_fill : R.drawable.ic_like_stroke);
        } else {
            tvLikeCount.setText(feed.numberOfLikes + "");
            imgLike.setImageResource(Common.getInstance().getBooleanFromInt(feed.isLike) ? R.drawable.ic_like_fill : R.drawable.ic_like_stroke);
        }
    }

    private void updateCommentCount() {
        if (!isSingleMediaFeed) {
            tvComment.setText(feed.mediaList.get(mediaPosition).numberOfComments + "");
        } else {
            tvComment.setText(feed.numberOfComments + "");
        }
    }

    public void updateCommentCount(int updateCount) {
        if (!isSingleMediaFeed) {
            feed.mediaList.get(mediaPosition).numberOfComments = feed.mediaList.get(mediaPosition).numberOfComments + updateCount;
            tvComment.setText(feed.mediaList.get(mediaPosition).numberOfComments + "");
        } else {
            feed.numberOfComments = feed.numberOfComments + updateCount;
            tvComment.setText(feed.numberOfComments + "");
        }
        applyUpdates(feed);
    }

    public void applyUpdates(Feed feed) {
        try {
            ((HelperActivity) activity()).updateFeed(feed, feedPosition);
            ((HelperActivity) activity()).updateFeedDetails(feed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getFeedById(String feedId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(Constants.FeedConstants.getFeedById + feedId + "/" + SessionManager.userLogin.userId);
        IApiListener apiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                try {
                    Type type = new TypeToken<Feed>() {}.getType();
                    feed = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    isSingleMediaFeed = feed.mediaList != null && feed.mediaList.size() == 1;
                    new asyncTaskFindMediPosition().execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                //
            }
        };
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.getFeedById, false, apiListener, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeVideoPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseVideoPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        Utility.showSnackBar(activity(), coordinatorLayout, snackBarText, type);
    }

    @Override
    public Activity activity() {
        return getActivity();
    }


    @Override
    public void onDoubleClick(View v) {
        switch (v.getId()) {
            case R.id.LLForward:
                playerView.requestFocus();
                tvForword.setVisibility(View.VISIBLE);
                iVForword.setVisibility(View.VISIBLE);
                player.seekTo(player.getCurrentPosition() + 10000);
                tvForword.postDelayed(new Runnable() {
                    public void run() {
                        tvForword.setVisibility(View.GONE);
                        iVForword.setVisibility(View.GONE);
                    }
                }, 1000);
                break;
            case R.id.LLRewind:
                playerView.requestFocus();
                player.seekTo(player.getCurrentPosition() - 10000);
                tvRewind.setVisibility(View.VISIBLE);
                iVRewind.setVisibility(View.VISIBLE);
                tvRewind.postDelayed(new Runnable() {
                    public void run() {
                        tvRewind.setVisibility(View.GONE);
                        iVRewind.setVisibility(View.GONE);
                    }
                }, 1000);
                break;
        }
    }

    private void deleteMedia(String id, final int position) { //5d4410a165ae31606c8ef7c3
        //5d4410a265ae31606c8ef7c8
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject params = new JSONObject();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params.toString());
        Call<ApiResponseModel> call = apiInterface.PUT(ApiClient.DELETE_SELF_MEDIA + id, requestBody);
        IApiListener iApiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                try {
                    if (CelebrityProfileFragment.getInstance() != null) {
                        CelebrityProfileFragment.getInstance().getProfileDetails(SessionManager.userLogin.userId, true);

                    }
                    releasePlayer();
                    activity().finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

            }
        };
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.DELETE_SELF_MEDIA,
                true, iApiListener, true));
    }

}
