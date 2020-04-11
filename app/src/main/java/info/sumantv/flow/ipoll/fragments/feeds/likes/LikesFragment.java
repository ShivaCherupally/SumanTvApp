package info.sumantv.flow.ipoll.fragments.feeds.likes;


import android.app.Activity;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import info.sumantv.flow.appmanagers.feed.models.Feed;
import info.sumantv.flow.appmanagers.feed.models.Like;
import info.sumantv.flow.appmanagers.feed.models.Profile;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataAdapter;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.ipoll.adapters.feeds.likes.LikesAdapter;
import info.sumantv.flow.ipoll.interfaces.feeds.likes.ILikesAdapter;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Logger;
import info.sumantv.flow.utils.Utility;
import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LikesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LikesFragment extends Fragment implements IFragment, ILikesAdapter {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.imgClose)
    ImageView imgClose;

    LinearLayoutManager linearLayoutManager;
    List<Like> likeList;
    Boolean isLoadMoreBusy = false;
    LikesAdapter likesAdapter;
    ILikesAdapter iLikesAdapter;

    boolean isFeed = true, isDetails = false, isLoadMoreFinish = false;
    String id = null;
    int feedPosition;
    int mediaPosition;
    Feed feed;


    public LikesFragment() {
        // Required empty public constructor
    }


    public static LikesFragment newInstance(Feed feed, String id, boolean isFeed, int feedPosition, int mediaPosition, boolean isDetails) {
        LikesFragment fragment = new LikesFragment();
        Bundle args = new Bundle();
        args.putParcelable("Feed", feed);
        args.putString("Id", id);
        args.putBoolean("IsFeed", isFeed);
        args.putInt("FeedPosition", feedPosition);
        args.putInt("MediaPosition", mediaPosition);
        args.putBoolean("IsDetails", isDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ipoll_fragment_likes, container, false);
        ButterKnife.bind(this, view);
        iLikesAdapter = this;

        setUp();
        return view;
    }

    private void setUp() {
        if (getArguments() != null) {
            if (getArguments() != null) {
                feed = getArguments().getParcelable("Feed");
                id = getArguments().getString("Id");
                isFeed = getArguments().getBoolean("IsFeed");
                feedPosition = getArguments().getInt("FeedPosition");
                mediaPosition = getArguments().getInt("MediaPosition");
                isDetails = getArguments().getBoolean("IsDetails");
            } else {
                return;
            }
        } else {
            return;
        }
        progressBar.setVisibility(View.GONE);
        recyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(activity()));
        swipeRefreshLayout.setEnabled(false);

        imgClose.setOnClickListener(v ->
        {
            Utility.hideKeyboard(activity());
            activity().onBackPressed();
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                Logger.d("DATA " + firstVisibleItemPosition + " " + visibleItemCount + " " + totalItemCount + " " + pastVisibleItems);
                if (pastVisibleItems + visibleItemCount >= totalItemCount && !isLoadMoreFinish) {
                    loadMore();
                }
            }
        });

        fetchFeedLikes();
    }


    private void fetchFeedLikes() {
        isLoadMoreBusy = false;
        if (Utility.isNetworkAvailable(activity())) {
            recyclerView.setAdapter(likesAdapter = new LikesAdapter(Arrays.asList(null, null), activity(), RController.LOADING, iLikesAdapter));
            IApiListener iApiListener = new IApiListener() {
                @Override
                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                    Type type = new TypeToken<List<Like>>() {}.getType();
                    likeList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    if (likeList != null && likeList.size() > 0) {
                        recyclerView.setAdapter(likesAdapter = new LikesAdapter(likeList, activity(), RController.LOADED, iLikesAdapter));
                    } else {
                        recyclerView.setAdapter(new EmptyDataAdapter(activity(), Constants.NO_LIKES, R.drawable.ic_nodata, 1));
                    }
                }

                @Override
                public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                    recyclerView.setAdapter(new EmptyDataAdapter(activity(), Constants.NO_LIKES, R.drawable.ic_nodata, 1));
                    showSnackBar(Constants.CONNECTION_ERROR, 2);
                }
            };
            //
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            if (isFeed) {
                Call<ApiResponseModel> call = apiInterface.GET(Constants.FeedConstants.URL_GET_FEED_LIKES + id + "/" + "0");
                Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.URL_GET_FEED_LIKES, false, iApiListener, false));
            } else if(feed != null){
                Call<ApiResponseModel> call = apiInterface.GET(Constants.FeedConstants.URL_GET_FEED_MEDIA_LIKES + feed.id + "/" + id + "/" + "0");
                Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.URL_GET_FEED_MEDIA_LIKES, false, iApiListener, false));
            }
        } else {
            recyclerView.setAdapter(new EmptyDataAdapter(activity(), Constants.PLEASE_CHECK_INTERNET, R.drawable.ic_network, 0));
        }
    }

    private void loadMore() {
        if (!isLoadMoreBusy && likesAdapter.rController != RController.LOADING) {
            isLoadMoreBusy = true;
            Like like = likeList.get(likeList.size() - 1);
            if (Utility.isNetworkAvailable(activity())) {
                isLoadMoreBusy = true;
                IApiListener iApiListener = new IApiListener() {
                    @Override
                    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                        isLoadMoreBusy = false;
                        Type type = new TypeToken<List<Like>>() {}.getType();
                        List<Like> appendList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                        if (appendList != null && appendList.size() > 0) {
                            likesAdapter.loadMore(new ArrayList<>(appendList));
                        }
                        if (appendList != null && appendList.size() < Constants.LIKES_LOAD_MORE_COUNT) {
                            isLoadMoreFinish = true;
                        }
                    }

                    @Override
                    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                        isLoadMoreBusy = false;
                    }
                };
                //
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                if (isFeed) {
                    Call<ApiResponseModel> call = apiInterface.GET(Constants.FeedConstants.URL_GET_FEED_LIKES + id + "/" + like.timeAgo);
                    Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.URL_GET_FEED_LIKES, false, iApiListener, false));
                } else if(feed != null){
                    Call<ApiResponseModel> call = apiInterface.GET(Constants.FeedConstants.URL_GET_FEED_MEDIA_LIKES + feed.id + "/" + id + "/" + like.timeAgo);
                    Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.URL_GET_FEED_MEDIA_LIKES, false, iApiListener, false));
                }
            } else {
                recyclerView.setAdapter(new EmptyDataAdapter(activity(), Constants.PLEASE_CHECK_INTERNET, R.drawable.ic_network, 0));
            }

        }
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
    public void onProfileClick(Like like, int position) {

    }

    @Override
    public void retryLoadMore() {
        isLoadMoreBusy = false;
        loadMore();
    }

    @Override
    public void selfProfile(Profile profile) {
        Common.getInstance().selfProfile(activity(), profile);
    }

    @Override
    public void celebUserProfile(Profile profile) {
        Common.getInstance().celebUserProfile(activity(), profile);
    }
}
