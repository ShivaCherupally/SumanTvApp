package info.dkapp.flow.celebflow.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import info.dkapp.flow.appmanagers.feed.models.Feed;
import info.dkapp.flow.appmanagers.feed.models.Media;
import info.dkapp.flow.bottommenu.activity.BottomMenuActivity;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.celebrityprofile.CelebrityProfileFragment;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.celebflow.Fragments.model.PhotosMediaModel;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.celebflow.constants.Permission;
import info.dkapp.flow.commonuses.zoomable.ZoomableDraweeView;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaDetailsFragment extends Fragment implements IFragment, IApiListener {
    // private ViewPager viewPager;
    private List<Media> mediaList = new ArrayList<>();
    private int mediaPosition, clickedPosition = -1;
    private Timeline.Window window;
    private DefaultTrackSelector trackSelector;
    private ViewPager viewPager;
    private BandwidthMeter bandwidthMeter;
    private boolean shouldAutoPlay;
    private SimpleExoPlayer player;
    private Boolean isSelf;
    private ViewPagerMediaAdapter adapter;
    private Boolean isMember, showBottomLikes;
    private Feed feed;
    private Boolean isFromHelperActivity = false;
    private int feedPosition;
    private static MediaDetailsFragment instance = null;
    private String mediaType, CelelbID, isFromWhich = "";
    private IApiListener iApiListener;
    private PhotosMediaModel photosMediaModel;
    private int lastPosition, nextOrPervious;
    private boolean isSingleMediaFeed = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        iApiListener = this;
    }

    public MediaDetailsFragment() {
        // Required empty public constructor
    }

    public static MediaDetailsFragment getInstance() {
        return instance;
    }

    public static MediaDetailsFragment newInstance(String mediaType, String CelelbID, Feed feed,
                                                   List<Media> media, int mediaPosition, Boolean isSelf,
                                                   Boolean isMember, Boolean isFromHelperActivity, int feedPosition,
                                                   String isFromWhich, Boolean showBottomLikes) {
        MediaDetailsFragment mediaDetailsFragment = new MediaDetailsFragment();
        Bundle args = new Bundle();
        args.putString("mediaType", mediaType);
        args.putString("CelelbID", CelelbID);
        args.putParcelable("feed", feed);
        args.putParcelableArrayList("mediaList", (ArrayList<? extends Parcelable>) media);
        args.putInt("mediaPosition", mediaPosition);
        args.putInt("feedPosition", feedPosition);
        args.putBoolean("isSelf", isSelf);
        args.putBoolean("isMember", isMember);

        args.putBoolean("isFromHelperActivity", isFromHelperActivity);
        args.putString("isFromWhich", isFromWhich);
        args.putBoolean("showBottomLikes", showBottomLikes);
        mediaDetailsFragment.setArguments(args);
        return mediaDetailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_media_details, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            feed = getArguments().getParcelable("feed");
            mediaList = getArguments().getParcelableArrayList("mediaList");
            if (mediaList == null || mediaList.size() <= 0) {
                Common.getInstance().cusToast(getActivity(), "Data not found");
                getActivity().finish();
            }
            if (feed != null && feed.mediaList != null && feed.mediaList.size() > 0) {
                for (int i = 0; i < feed.mediaList.size(); i++) {
                    feed.mediaList.get(i).isBusyLike = false;
                }
            }
            if (mediaList != null && mediaList.size() > 0) {
                for (int i = 0; i < mediaList.size(); i++) {
                    mediaList.get(i).isBusyLike = false;
                }
            }
            if ((feed == null || feed.mediaList == null || feed.mediaList.size() <= 0) && mediaList != null && mediaList.size() > 0) {
                if (feed == null) {
                    feed = new Feed();
                }
                if (feed.mediaList == null || feed.mediaList.size() <= 0) {
                    feed.id = mediaList.get(0).feedId;
                    feed.mediaList = mediaList;
                }
            }
            isSingleMediaFeed = mediaList != null && mediaList.size() == 1;
            mediaPosition = getArguments().getInt("mediaPosition");
            feedPosition = getArguments().getInt("feedPosition");
            isSelf = getArguments().getBoolean("isSelf", false);
            isMember = getArguments().getBoolean("isMember", false);
            isFromHelperActivity = getArguments().getBoolean("isFromHelperActivity", false);
            CelelbID = getArguments().getString("CelelbID", "");
            mediaType = getArguments().getString("mediaType", "");
            isFromWhich = getArguments().getString("isFromWhich", "");
            showBottomLikes = getArguments().getBoolean("showBottomLikes", false);
        }

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mediaList.size() - 1) {
                    lastPosition = mediaList.size();
                    nextOrPervious = -1;
                    loadImages(nextOrPervious, mediaList.get(mediaList.size() - 1).createdAt);
                } else if (position == 0) {
                    lastPosition = 0;
                    nextOrPervious = 1;
                    loadImages(nextOrPervious, mediaList.get(0).createdAt);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        setAdapter(mediaList, false);
        return view;
    }


    public void setAdapter(List<Media> mediaList, boolean isFromLoadMore) {
        try {
            if (mediaList != null && mediaList.size() > 0) {
                if (isFromLoadMore) {
                    adapter.notifyDataSetChanged();
                    if (nextOrPervious == 1) {
                        viewPager.setCurrentItem((mediaList.size()) + lastPosition, false);
                    }
                } else {
                    adapter = new ViewPagerMediaAdapter(activity());
                    viewPager.setAdapter(adapter);
                    //viewPager.setOffscreenPageLimit(adapter.getCount()-1);
                    if (mediaPosition > 0) {
                        viewPager.setCurrentItem(mediaPosition, true);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Feed getUpdateFeed() {
        return feed;
    }

    public Integer getFeedPosition() {
        return feedPosition;
    }

    public void updateMedia(Feed feed) {
        try {
            if (feed != null && feed.mediaList != null && feed.mediaList.size() > 0) {
                for (int i = 0; i < feed.mediaList.size(); i++) {
                    feed.mediaList.get(i).isBusyLike = false;
                }
            }
            if (isFromWhich.equals("CelebMedia")) {
                mediaList.get(clickedPosition).feedMediaDetails = feed;
            } else {
                this.feed = feed;
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userLikesMedia(Feed feed, Media media, int position) {
        try {
            if (isFromHelperActivity) {
                ((HelperActivity) activity()).openFeedLikes(feed, media.mediaId, false, feedPosition, position, true);
            } else {
                ((BottomMenuActivity) activity()).openFeedLikes(feed, media.mediaId, false, feedPosition, position, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userCommentsMedia(Feed feed, Media media, int position) {
        Common.getInstance().openFeedComments(feed, media.mediaId, false, feedPosition, position, true, true);
    }

    public void shareToOther(Feed feed, Media media, int position) {
        if (media != null) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, ApiClient.SHARE_FEED_IN_SOCIAL_MEDIA + feed.id);
            startActivity(Intent.createChooser(intent, "Share"));
        }
    }


    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        Common.getInstance().cusToast(activity(), snackBarText);
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private void loadImages(int condition, String createdAt) {
        if (CelelbID == null || CelelbID.isEmpty()) {
            return;
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(ApiClient.GET_MEDIA_NEXT_PREVIOUS + CelelbID + "/" + mediaType + condition + "/" + createdAt);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.GET_MEDIA_NEXT_PREVIOUS,
                false,
                iApiListener, false));
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_MEDIA_NEXT_PREVIOUS)) {
            try {
                Type type = new TypeToken<PhotosMediaModel>() {
                }.getType();
                photosMediaModel = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                if (photosMediaModel != null && photosMediaModel.media != null && photosMediaModel.media.size() > 0) {
                    if (nextOrPervious == -1) {
                        mediaList.addAll(photosMediaModel.media);
                    } else {
                        mediaList.addAll(0, photosMediaModel.media);
                    }
                    setAdapter(photosMediaModel.media, true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_MEDIA_NEXT_PREVIOUS)) {

        }
    }

    public class AdapterHolder {
        ZoomableDraweeView simpleDraweeView;
        ImageView imageviewDelete;
        LinearLayout llFeedDetails;
        LinearLayout llFeedLikesCommentShare;
        LinearLayout llLike;
        LinearLayout llComment;
        LinearLayout llShare;
        ImageView imgClose;

        TextView tvUserName;
        TextView tvProfession;
        TextView tvTimeAgo;
        TextView tvMediaLikeCount;
        TextView tvCommentCount;
        TextView tvDot;
        LinearLayout llLikeComment;
        View viewLine;
        ImageView imgLike;
        ProgressBar progressBarimLike;
        ProgressBar pbImageLoading;

        ProgressBar progressBarDown;
        ImageView imgDownload;

    }

    private class ViewPagerMediaAdapter extends PagerAdapter {
        Context context;
        LayoutInflater inflater;

        ViewPagerMediaAdapter(Context context) {
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mediaList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View itemView = inflater.inflate(R.layout.item_frag_image_slider, container, false);

            AdapterHolder adapterHolder = new AdapterHolder();


            adapterHolder.simpleDraweeView = itemView.findViewById(R.id.ZoomableDraweeViewImg);
            adapterHolder.llFeedDetails = itemView.findViewById(R.id.llFeedDetails);

            adapterHolder.llFeedLikesCommentShare = itemView.findViewById(R.id.llFeedLikesCommentShare);
            adapterHolder.llLike = itemView.findViewById(R.id.llLikelayout);
            adapterHolder.llComment = itemView.findViewById(R.id.llCommentLayout);
            adapterHolder.llShare = itemView.findViewById(R.id.llShareLayout);

            adapterHolder.tvUserName = itemView.findViewById(R.id.tvUserName);
            adapterHolder.tvProfession = itemView.findViewById(R.id.tvProfession);
            adapterHolder.tvTimeAgo = itemView.findViewById(R.id.tvTimeAgo);
            adapterHolder.tvMediaLikeCount = itemView.findViewById(R.id.tvMediaLikeCount);
            adapterHolder.tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            adapterHolder.tvDot = itemView.findViewById(R.id.tvDot);
            adapterHolder.llLikeComment = itemView.findViewById(R.id.llLikeComment);
            adapterHolder.viewLine = itemView.findViewById(R.id.viewLine);
            adapterHolder.imgLike = itemView.findViewById(R.id.imgLikeMedia);
            adapterHolder.progressBarimLike = itemView.findViewById(R.id.progressBarimLikeNew);
            adapterHolder.pbImageLoading = itemView.findViewById(R.id.pbImageLoading);
            adapterHolder.imgDownload = itemView.findViewById(R.id.imgDownload);
            adapterHolder.progressBarDown = itemView.findViewById(R.id.progressBarDown);
            adapterHolder.imgClose = itemView.findViewById(R.id.imgClose);
            adapterHolder.imageviewDelete = itemView.findViewById(R.id.imageviewDelete);
            adapterHolder.llFeedDetails.setVisibility(View.GONE);

            adapterHolder.pbImageLoading.getProgressDrawable().setColorFilter(getResources().getColor(R.color.sky_blue), android.graphics.PorterDuff.Mode.SRC_IN);
            //
            if (isFromWhich.equals("Audition") || isMember) {
                adapterHolder.imageviewDelete.setVisibility(View.GONE);
            } else if (isSelf) {
               /* if (!isMember) {
                    adapterHolder.imageviewDelete.setVisibility(View.VISIBLE);
                    adapterHolder.downloadLay.setVisibility(View.GONE);
                }*/
                adapterHolder.imageviewDelete.setVisibility(View.VISIBLE);

            } else {
                adapterHolder.imageviewDelete.setVisibility(View.GONE);
            }

            if (showBottomLikes) {
                /*String feedId, mediaId;
                if (feed != null) {
                    feedId = feed.id;
                    mediaId = feed.mediaList.get(position).id;
                } else if (mediaList.get(position).feedMediaDetails != null) {
                    feedId = mediaList.get(position).feedMediaDetails.id;
                    mediaId = mediaList.get(position).id;
                } else {
                    feedId = mediaList.get(position).feedId;
                    mediaId = mediaList.get(position).id;
                }
                getFeedById(adapterHolder, feedId, mediaId, position);*/
                if (feed != null && feed.feedMemberDetails != null) {
                    setFeedMemberDetails(adapterHolder, feed, position, position);
                } else if (mediaList.get(position).feedMediaDetails != null) {
                    setFeedDataGetByAPI(adapterHolder, mediaList.get(position).mediaId, position, mediaList.get(position).feedMediaDetails);
                } else {
                    adapterHolder.llFeedDetails.setVisibility(View.GONE);
                    if (mediaList.get(position).feedId != null && !mediaList.get(position).feedId.isEmpty()) {
                        getFeedById(adapterHolder, mediaList.get(position).feedId, mediaList.get(position).mediaId, position);
                    }
                }
            }

            if (isMember || mediaList.get(position).type.equals(Constants.MEDIA_TYPE_GIF)) {
                adapterHolder.imgDownload.setVisibility(View.GONE);
            } else {
                adapterHolder.imgDownload.setVisibility(View.VISIBLE);
            }

            adapterHolder.imgClose.setOnClickListener(v -> {
                activity().finish();
            });
            adapterHolder.imgDownload.setOnClickListener(v -> {
                if (!Utility.checkPermissionRequest(Permission.WRITE_STORAGE, activity())) {
                    Utility.raisePermissionRequest(Permission.WRITE_STORAGE, activity());
                } else {
                        if (feed != null && feed.mediaList != null && feed.mediaList.get(position).source.url != null) {
                            Common.getInstance().downloadFileFromUrl(ApiClient.BASE_URL + feed.mediaList.get(position).source.url ,
                                    feed.mediaList.get(position).type, adapterHolder.progressBarDown, adapterHolder.imgDownload);

                        }

                }
            });
            adapterHolder.imageviewDelete.setOnClickListener(v -> {
                    deleteMedia(mediaList.get(position)._id, position);
            });

            try {
                adapterHolder.simpleDraweeView.getHierarchy().setProgressBarImage(new ProgressBarDrawable() {
                    @Override
                    protected boolean onLevelChange(int level) {
                        adapterHolder.pbImageLoading.setProgress(level / 100);
                        if (level / 100 == 100) {
                            adapterHolder.pbImageLoading.setVisibility(View.GONE);
                        }
                        return true;
                    }
                });

                Common.getInstance().setGIFImageToFresco(adapterHolder.simpleDraweeView,null, mediaList.get(position), false);
                adapterHolder.simpleDraweeView.setVisibility(View.VISIBLE);

                if (mediaList.get(position).type.equals(Constants.MEDIA_TYPE_VIDEO)) {
//                    adapterHolder.iVPlayButton.setVisibility(View.VISIBLE);
                } else if (mediaList.get(position).type.equals(Constants.MEDIA_TYPE_GIF)) {
                    adapterHolder.simpleDraweeView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return true;
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            container.addView(itemView);
            return itemView;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        public void notify(int position) {

            try {
                mediaList.remove(position);
                if (mediaList == null || mediaList.size() <= 0) {
                    activity().finish();
                } else {
                    notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    private void setFeedMemberDetails(AdapterHolder adapterHolder, Feed feed, int mediaPosition, int orgPosition) {
        try {
            String userName = feed.feedMemberDetails.firstName + " " + feed.feedMemberDetails.lastName;
            if (!userName.isEmpty()) {
                adapterHolder.tvUserName.setText(Character.toUpperCase(userName.charAt(0)) + userName.substring(1));
            }
            adapterHolder.tvTimeAgo.setText(Utility.makeDateToAgo(feed.createdDate));
            adapterHolder.tvProfession.setText(feed.feedMemberDetails.profession);
            adapterHolder.imgLike.setImageResource(Common.getInstance().getBooleanFromInt(isSingleMediaFeed ? feed.isLike : feed.mediaList.get(mediaPosition).isLike) ? R.drawable.ic_like_fill : R.drawable.ic_like_stroke);
            adapterHolder.imgLike.setVisibility(feed.mediaList.get(mediaPosition).isBusyLike ? View.GONE : View.VISIBLE);
            Common.getInstance().setFeedMediaLikeUnLike(feed, mediaPosition, adapterHolder.llLikeComment,
                    adapterHolder.tvDot, adapterHolder.tvMediaLikeCount, adapterHolder.tvCommentCount);
            adapterHolder.llLike.setOnClickListener(v -> {
                    clickedPosition = orgPosition;
                    adapterHolder.progressBarimLike.setVisibility(View.VISIBLE);
                    likeActionMedia(feed, feed.mediaList, mediaPosition, adapterHolder.progressBarimLike);
            });
            adapterHolder.llComment.setOnClickListener(v -> {
                    clickedPosition = orgPosition;
                    userCommentsMedia(feed, feed.mediaList.get(mediaPosition), mediaPosition);
            });
            adapterHolder.tvCommentCount.setOnClickListener(v -> {
                    clickedPosition = orgPosition;
                    userCommentsMedia(feed, feed.mediaList.get(mediaPosition), mediaPosition);
            });
            adapterHolder.tvMediaLikeCount.setOnClickListener(v -> {
                    clickedPosition = orgPosition;
                    userLikesMedia(feed, feed.mediaList.get(mediaPosition), mediaPosition);
            });
            adapterHolder.llShare.setOnClickListener(v -> {
                    clickedPosition = orgPosition;
                    shareToOther(feed, feed.mediaList.get(mediaPosition), mediaPosition);
            });
            adapterHolder.llFeedDetails.setVisibility(View.VISIBLE);
            adapterHolder.llFeedLikesCommentShare.setVisibility(View.VISIBLE);
            adapterHolder.viewLine.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getFeedById(AdapterHolder adapterHolder, String feedId, String mediaId, int adapterPosition) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(Constants.FeedConstants.getFeedById + feedId + "/" +
                SessionManager.userLogin.userId);
        IApiListener apiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                try {
                    Type type = new TypeToken<Feed>() {
                    }.getType();
                    Feed feed = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    setFeedDataGetByAPI(adapterHolder, mediaId, adapterPosition, feed);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                Log.d("getFeedById", apiResponseModel.message);
                /*if (apiResponseModel.message != null && !apiResponseModel.message.isEmpty()) {
                    Common.getInstance().cusToast(activity(), apiResponseModel.message);
                } else {
                    Common.getInstance().cusToast(activity(), Constants.SOMETHING_WENT_WRONG);
                }*/
            }
        };
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.getFeedById, false, apiListener, false));
    }

    private void setFeedDataGetByAPI(AdapterHolder adapterHolder, String mediaId, int adapterPosition, Feed feed) {
        if (feed != null && feed.feedMemberDetails != null && feed.mediaList != null && feed.mediaList.size() > 0) {
            mediaList.get(adapterPosition).feedMediaDetails = feed;
            int mediaPosition = -1;
            for (int i = 0; i < feed.mediaList.size(); i++) {
                if (feed.mediaList.get(i).mediaId.equals(mediaId)) {
                    mediaPosition = i;
                }
            }
            if (mediaPosition > -1) {
                setFeedMemberDetails(adapterHolder, feed, mediaPosition, adapterPosition);
            }
        }
    }

    public void likeActionMedia(Feed feed, List<Media> mediaList, int position, ProgressBar progressBarLikeLocal) {
        if (!mediaList.get(position).isBusyLike) {
            mediaList.get(position).isBusyLike = true;
//            adapter.notifyDataSetChanged();
            IApiListener iApiListener = new IApiListener() {
                @Override
                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                    progressBarLikeLocal.setVisibility(View.GONE);
                    mediaList.get(position).isBusyLike = false;
                    if (isSingleMediaFeed) {
                        feed.isLike = Common.getInstance().getBooleanFromInt(feed.isLike) ? 0 : 1;
                        feed.numberOfLikes = Common.getInstance().getBooleanFromInt(feed.isLike) ? feed.numberOfLikes + 1 : feed.numberOfLikes - 1;
                    } else {
                        mediaList.get(position).isLike = Common.getInstance().getBooleanFromInt(mediaList.get(position).isLike) ? 0 : 1;
                        mediaList.get(position).numberOfLikes = Common.getInstance().getBooleanFromInt(mediaList.get(position).isLike) ?
                                mediaList.get(position).numberOfLikes + 1 : mediaList.get(position).numberOfLikes - 1;
                    }
                    try {
                        if (isFromWhich.equals("CelebMedia")) {
                            MediaDetailsFragment.this.mediaList.get(clickedPosition).feedMediaDetails.mediaList = mediaList;
                            applyUpdates(MediaDetailsFragment.this.mediaList.get(clickedPosition).feedMediaDetails);
                        } else {
                            MediaDetailsFragment.this.mediaList = mediaList;
                            applyUpdates(feed);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                    progressBarLikeLocal.setVisibility(View.GONE);
                    mediaList.get(position).isBusyLike = false;
                    adapter.notifyDataSetChanged();
                    showSnackBar(Constants.CONNECTION_ERROR, 2);
                }
            };
            Common.getInstance().addLike(isSingleMediaFeed ? null : mediaList.get(position).mediaId, feed.memberId, feed.id, isSingleMediaFeed ? feed.isLike : mediaList.get(position).isLike, false, iApiListener);
        } else {
            showSnackBar("Its already running...", 2);
        }
    }

    public void applyUpdates(Feed feed) {
        try {
            if (!isFromWhich.equals("CelebMedia")) {
                feed.mediaList = mediaList;
            }
            if (isFromHelperActivity) {
                ((HelperActivity) activity()).updateFeed(feed, feedPosition);
                ((HelperActivity) activity()).updateFeedDetails(feed);
            } else {
                ((BottomMenuActivity) activity()).updateFeed(feed, feedPosition);
                ((BottomMenuActivity) activity()).updateFeedDetails(feed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteMedia(String id, final int position) {
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
                    Common.getInstance().showSweetAlertSuccess(activity(), "CelebKonect", "Media Deleted");
                    adapter.notify(position);
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
