package info.dkapp.flow.ipoll.fragments.feeds.details;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.appmanagers.feed.models.Feed;
import info.dkapp.flow.appmanagers.feed.models.KonectData;
import info.dkapp.flow.appmanagers.feed.models.Media;
import info.dkapp.flow.ModelClass.ProfileDataModel;
import info.dkapp.flow.bottommenu.activity.BottomMenuActivity;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.bottommenu.interfaces.fragments.IKonectDailogClick;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.ipoll.adapters.feeds.details.FeedDetailsAdapter;
import info.dkapp.flow.ipoll.interfaces.feeds.details.IFeedDetailsAdapter;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;
import retrofit2.Call;

public class FeedDetailsFragment extends Fragment implements IFragment, IFeedDetailsAdapter {
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    ProgressDialog progressDialog;
    Feed feed;
    List<Media> mediaList;
    int feedPosition = -1, mediaPosition = -1, clickedPosition = -1;
    FeedDetailsAdapter feedDetailsAdapter;
    IFeedDetailsAdapter iFeedDetailsAdapter;
    Boolean isFromHelperActivity = false;
    private static FeedDetailsFragment instance;

    public FeedDetailsFragment() {
        // Required empty public constructor
    }

    public static FeedDetailsFragment newInstance(Feed feed, int position, int mediaPosition, Boolean isFromHelperActivity) {
        FeedDetailsFragment fragment = new FeedDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("Feed", feed);
        args.putInt("Position", position);
        args.putInt("MediaPosition", mediaPosition);
        args.putBoolean("isFromHelperActivity", isFromHelperActivity);
        fragment.setArguments(args);
        return fragment;
    }

    public static FeedDetailsFragment getInstance() {
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ipoll_fragment_feeds, container, false);
        ButterKnife.bind(this, view);
        iFeedDetailsAdapter = this;
        setUp();
        return view;
    }

    private void setUp() {
        if (getArguments() != null) {
            feed = getArguments().getParcelable("Feed");
            feedPosition = getArguments().getInt("Position");
            mediaPosition = getArguments().getInt("MediaPosition");
            isFromHelperActivity = getArguments().getBoolean("isFromHelperActivity", false);
        }
        if (feed == null) return;
        getFeedById();
        setData();
    }

    private void setData() {
        try {
            if (feed == null || feed.feedMemberDetails == null) {
                return;
            }
            mediaList = feed.mediaList;
            swipeRefreshLayout.setEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity()));
            ((SimpleItemAnimator) recyclerView.getItemAnimator()).setChangeDuration(0); //.setSupportsChangeAnimations(false);
            recyclerView.setAdapter(feedDetailsAdapter = new FeedDetailsAdapter(activity(), feed, mediaList, iFeedDetailsAdapter));
            if (mediaPosition > -1) {
                if (feed.mediaList.size() > 1)
                    recyclerView.scrollToPosition(mediaPosition + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getFeedById() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(Constants.FeedConstants.getFeedById + feed.id + "/" +
                SessionManager.userLogin.userId);
        IApiListener apiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<Feed>() {
                    }.getType();
                    feed = gson.fromJson(new Gson().toJson(apiResponseModel.data), type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (feed != null && feed.feedMemberDetails != null) {
                        setData();
                    } else {
                        Common.getInstance().cusToast(activity(), Constants.SOMETHING_WENT_WRONG);
                        Objects.requireNonNull(activity()).finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                if (apiResponseModel.message != null && !apiResponseModel.message.isEmpty()) {
                    Common.getInstance().cusToast(activity(), apiResponseModel.message);
                } else {
                    Common.getInstance().cusToast(activity(), Constants.SOMETHING_WENT_WRONG);
                }
                Objects.requireNonNull(activity()).finish();
            }
        };
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.getFeedById,
                true, apiListener, false));
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void likeAction(Feed feedObject, int position) {
        if (!Utility.isNetworkAvailable(activity())) {
            showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
            return;
        }
        if (!feed.isBusyLike) {
            feed.isBusyLike = true;
            feedDetailsAdapter.notifyItemChanged(position);
            IApiListener iApiListener = new IApiListener() {
                @Override
                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                    feed.isBusyLike = false;
                    feed.isLike = Common.getInstance().getBooleanFromInt(feed.isLike) ? 0 : 1;
                    feed.numberOfLikes = Common.getInstance().getBooleanFromInt(feed.isLike) ? feed.numberOfLikes + 1 : feed.numberOfLikes - 1;
                    feedDetailsAdapter.notifyItemChanged(position);
                    applyUpdates();
                }

                @Override
                public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                    feed.isBusyLike = false;
                    feedDetailsAdapter.notifyItemChanged(position);
                    showSnackBar(Constants.CONNECTION_ERROR, 2);
                }
            };
            Common.getInstance().addLike(feed.id, feed.memberId, feed.id, feed.isLike, true, iApiListener);
        } else {
            showSnackBar("Its already running...", 2);
        }
    }

    private void applyUpdates() {
        try {
            feed.mediaList = mediaList;
            if (isFromHelperActivity) {
                ((HelperActivity) activity()).updateFeed(feed, feedPosition);
            } else {
                ((BottomMenuActivity) activity()).updateFeed(feed, feedPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shareToOther(Media media, int position) {

        if (media != null) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, ApiClient.SHARE_FEED_IN_SOCIAL_MEDIA + feed.id);
            startActivity(Intent.createChooser(intent, "Share"));
        }
    }

    @Override
    public void shareToOther(Feed feed, int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, ApiClient.SHARE_FEED_IN_SOCIAL_MEDIA + feed.id);
        startActivity(Intent.createChooser(intent, "Share"));
    }

    @Override
    public void userLikes(Feed feed, int position) {
        try {
            if (isFromHelperActivity) {
                ((HelperActivity) activity()).openFeedLikes(feed, feed.id, true, feedPosition, -1, true);
            } else {
                ((BottomMenuActivity) activity()).openFeedLikes(feed, feed.id, true, feedPosition, -1, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addComment(Feed feed, int position) {

    }

    @Override
    public void userComments(Feed feed, int position) {
        try {
            Common.getInstance().openFeedComments(feed, feed.id, true, feedPosition, -1, true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void likeActionMedia(Media media, int position) {
        if (!Utility.isNetworkAvailable(activity())) {
            showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
            return;
        }
        if (!mediaList.get(position - 1).isBusyLike) {
            mediaList.get(position - 1).isBusyLike = true;
            feedDetailsAdapter.notifyItemChanged(position);
            IApiListener iApiListener = new IApiListener() {
                @Override
                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                    mediaList.get(position - 1).isBusyLike = false;
                    mediaList.get(position - 1).isLike = Common.getInstance().getBooleanFromInt(mediaList.get(position - 1).isLike) ? 0 : 1;
                    mediaList.get(position - 1).numberOfLikes = Common.getInstance().getBooleanFromInt(mediaList.get(position - 1).isLike) ?
                            mediaList.get(position - 1).numberOfLikes + 1 : mediaList.get(position - 1).numberOfLikes - 1;
                    feedDetailsAdapter.notifyItemChanged(position);
                    applyUpdates();
                }

                @Override
                public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                    mediaList.get(position - 1).isBusyLike = false;
                    feedDetailsAdapter.notifyItemChanged(position);
                    showSnackBar(Constants.CONNECTION_ERROR, 2);
                }
            };
            Common.getInstance().addLike(mediaList.get(position - 1).mediaId, feed.memberId, feed.id, mediaList.get(position - 1).isLike, false, iApiListener);
        } else {
            showSnackBar("Its already running...", 2);
        }
    }

    @Override
    public void userLikesMedia(Media media, int position) {
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

    @Override
    public void userCommentsMedia(Media media, int position) {
        Common.getInstance().openFeedComments(feed, media.mediaId, false, feedPosition, position, true, false);
    }

    @Override
    public void openFeedOptions(Feed feed, int position) {
        try {
            if (isFromHelperActivity) {
                ((HelperActivity) activity()).openFeedOptions(feed, feedPosition, 1);
            } else {
                ((BottomMenuActivity) activity()).openFeedOptions(feed, feedPosition, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openVideoPlayer(Feed feed, Uri uri, Media media, int position) {
        Common.getInstance().openVideoPlayer(activity(), uri, feed.id,"", position, mediaPosition, false,"");
    }

    @Override
    public void openPhotosMedia(Feed feed, List<Media> mediaList, int position) {
        try {
            clickedPosition = position;
            Common.getInstance().openMediaDetailsFragment("", "", activity(), feed, mediaList, position, false, false, feedPosition, "", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewCelebDetails(Feed feeds, int position, boolean isSelf) {

        Intent profileIntent = new Intent(activity(), HelperActivity.class);
        if (isSelf) {

            profileIntent.putExtra(Constants.FRAGMENT_KEY, 8038);
            profileIntent.putExtra(Constants.FRAGMENT_TITLE, "My Profile");

        } else {

//            profileIntent.putExtra(Constants.FRAGMENT_KEY, 8029);
//            profileIntent.putExtra(Constants.FRAGMENT_TITLE, "Celeb Profile");
//            profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            profileIntent.putExtra("celeb_status", "online");
//
//            if (feeds.memberId != null && !feeds.memberId.isEmpty()) {
//                profileIntent.putExtra("CelebId", feeds.memberId);
//            }
//
//            if (feeds.userName != null && !feeds.userName.isEmpty()) {
//                profileIntent.putExtra("PROFILE_NAME", feeds.userName);
//            } else {
//                profileIntent.putExtra("PROFILE_NAME", "null");
//            }
//
//            if (feeds.profilePic != null && !feeds.profilePic.isEmpty()) {
//                profileIntent.putExtra("Imaage", ApiClient.BASE_URL + feeds.profilePic);
//            } else {
//                profileIntent.putExtra("Imaage", "");
//            }
//
//            if (feeds.profession != null && !feeds.profession.isEmpty()) {
//                profileIntent.putExtra("proffession", feeds.profession);
//            } else {
//                profileIntent.putExtra("proffession", "");
//            }
//
//            profileIntent.putExtra("ABOUT", "");
//
////            if (feeds.aboutMe != null && !feeds.aboutMe.isEmpty()) {
////                profileIntent.putExtra("ABOUT", feeds.aboutMe);
////            } else {
////                profileIntent.putExtra("ABOUT", "");
////            }
//        }
//        activity().startActivity(profileIntent);

            ProfileDataModel profileDataModel = new ProfileDataModel();
            profileDataModel._id = feeds.memberId;
            profileDataModel.firstName = feeds.feedMemberDetails.firstName;
            profileDataModel.lastName = "";
            profileDataModel.isCeleb = true;
            profileDataModel.role = "";
            profileDataModel.avtar_imgPath = feeds.feedMemberDetails.avtar_imgPath;
            profileDataModel.isOnline = false;
            profileDataModel.profession = feeds.profession;
            profileDataModel.aboutMe = "";
            profileDataModel.isFan = true;
            Common.getInstance().viewCelebProfileDialog(activity(), profileDataModel, progressDialog);
        }
    }

    @Override
    public void openKonect(Feed feed, int position) {
        try {
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

    public void update(Feed feed) {
        try {
            if (feed != null && feed.mediaList != null && feed.mediaList.size() > 0) {
                for (int i = 0; i < feed.mediaList.size(); i++) {
                    feed.mediaList.get(i).isBusyLike = false;
                }
            }
            this.feed = feed;
            this.mediaList = feed.mediaList;
            recyclerView.setAdapter(feedDetailsAdapter = new FeedDetailsAdapter(activity(), feed, mediaList, iFeedDetailsAdapter));
            //feedDetailsAdapter.notifyDataSetChanged();
            if (clickedPosition > -1) {
                if (feed.mediaList.size() >= clickedPosition)
                    recyclerView.scrollToPosition(clickedPosition + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
