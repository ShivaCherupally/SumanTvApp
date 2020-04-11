package info.dkapp.flow.bottommenu.menuitemsmanager.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import info.dkapp.flow.appmanagers.feed.models.Feed;
import info.dkapp.flow.bottommenu.constants.RController;
import info.dkapp.flow.bottommenu.generic.EmptyDataNewAdapter;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.menu_list.MyContent.MyPostsActivity.MyPostAdapter;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Logger;
import info.dkapp.flow.utils.Utility;
import retrofit2.Call;

public class MyContentFragment extends Fragment implements IApiListener, IFragment {

    RecyclerView myposts_recy;
    private RecyclerView.LayoutManager layoutManagerOffline;
    MyPostAdapter myPostAdapter;
    ApiInterface apiInterface;
    Context mContext;
    List<Feed> feedList;
    public SwipeRefreshLayout swipeRefreshLayout;
    IApiListener iApiListener;
    CoordinatorLayout coordinator_layout;
    boolean isLoadMoreApiRunning = false,stopLoading = false;
    int LIMIT_COUNT = 20;
    private static MyContentFragment instance;

    public static MyContentFragment newInstance() {
        MyContentFragment fragment = new MyContentFragment();
        return fragment;
    }

    public static MyContentFragment getInstance() {
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.common_recycler_view_page, null);
        iApiListener = this;
        initializeViews(root);
        setSkelltonAdapter();
        getAllPostdata(false);
        return root;
    }

    private void initializeViews(View root) {

        myposts_recy = root.findViewById(R.id.recyclerViewCommon);
        coordinator_layout = root.findViewById(R.id.coordinator_layout);
        mContext = getActivity();
        layoutManagerOffline = new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false);
        myposts_recy.setLayoutManager(layoutManagerOffline);
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            if (Common.checkInternetConnection(activity())) {
                myPostAdapter = null;
                setSkelltonAdapter();
                getAllPostdata(false);
            } else {
                showSnackBar(Constants.PLEASE_CHECK_INTERNET, 5);
            }
        });

        myposts_recy.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                if (!recyclerView.canScrollVertically(1)) {
                    if (feedList != null && feedList.size() > 0 && !stopLoading) {
                        getAllPostdata(true);
                    }
                }
            }
        });
    }

    private void getAllPostdata(Boolean isLoadmore) {
        if (isLoadMoreApiRunning) {
            return;
        }
        IApiListener iApiListener = this;
        Call<ApiResponseModel> call = null;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        if (!isLoadmore) {
            if (Common.getInstance().checkInterConnection(myposts_recy, activity())) {
                String url = ApiClient.MY_CONTENTS + SessionManager.userLogin.userId + "/null/" + LIMIT_COUNT;
                call = apiInterface.GET(url);
            }
        } else {
            if (Common.checkInternetConnection(activity())) {
                String url = ApiClient.MY_CONTENTS + SessionManager.userLogin.userId + "/" + feedList.get(feedList.size() - 1).createdDate + "/" + LIMIT_COUNT;
                call = apiInterface.GET(url);
            } else {
                showSnackBar(Constants.PLEASE_CHECK_INTERNET, 5);
                return;
            }
        }
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.MY_CONTENTS, false, iApiListener, false));
        isLoadMoreApiRunning = true;
    }

    public void updateFeed(Feed feed, int position) {
        if (feed != null) {
            Logger.d("Update feed position", "" + position);
            feedList.set(position, feed);
            myPostAdapter.notifyItemChanged(position);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void updateFeed(Feed feed) {
        if (feed != null) {
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... voids) {
                    if(feed.mediaList != null && feed.mediaList.size() > 0){
                        for (int i = 0; i < feed.mediaList.size(); i++) {
                            feed.mediaList.get(i).isBusyLike = false;
                        }
                    }
                    if (feedList != null && feedList.size() > 0) {
                        for (int i = 0; i < feedList.size(); i++) {
                            if (feedList.get(i).id.equals(feed.id)) {
                                feedList.set(i, feed);
                                return i;
                            }
                        }
                    }
                    return -1;
                }

                @Override
                protected void onPostExecute(Integer result) {
                    super.onPostExecute(result);
                    if (result >= 0) {
                        myPostAdapter.notifyItemChanged(result);
                    }
                }
            }.execute();
        }
    }

    private void noOrderAvailable() {
        myposts_recy.setAdapter(new EmptyDataNewAdapter(activity(), Constants.UH_OH,
                Constants.NO_CONETENTS, R.drawable.ic_no_content));
    }
    public void deleteFeed(int feedPosition) {
        if (feedList != null) {
            feedList.remove(feedPosition);
            myPostAdapter.deleteItem(feedPosition);
        }
        if (feedList == null || feedList.size() <= 0) {
            myPostAdapter = null;
            feedList = new ArrayList<>();
            myposts_recy.setAdapter(new EmptyDataNewAdapter(activity(), Constants.UH_OH,
                    Constants.NO_CONETENTS, R.drawable.ic_no_content));
        }
    }

    private void setSkelltonAdapter() {
        myposts_recy.setAdapter(new MyPostAdapter(RController.LOADING));
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.MY_CONTENTS)) {
            isLoadMoreApiRunning = false;
            try {
                Type type = new TypeToken<List<Feed>>() {}.getType();
                if (myPostAdapter == null) {
                    feedList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    stopLoading = false;
                    if (feedList.size() > 0) {
                        myPostAdapter = new MyPostAdapter(activity(), feedList);
                        myposts_recy.setAdapter(myPostAdapter);
                    } else {
                        noOrderAvailable();
                    }
                } else {
                    ArrayList<Feed> feedListTemp = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    if (feedListTemp.size() < LIMIT_COUNT){
                        stopLoading = true;
                    }
                    if (feedListTemp.size() > 0) {
                        feedList.addAll(feedListTemp);
                        myPostAdapter.loadmore(feedList);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                myposts_recy.setAdapter(new EmptyDataNewAdapter(activity(), Constants.SOMETHING_WENT_WRONG_SERVER, Constants.DRAG_TO_RETRY,
                        R.drawable.ic_no_content));
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.MY_CONTENTS)) {
            isLoadMoreApiRunning = false;
            myposts_recy.setAdapter(new EmptyDataNewAdapter(activity(), Constants.SOMETHING_WENT_WRONG_SERVER, Constants.DRAG_TO_RETRY,
                    R.drawable.ic_no_content));
        }
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        Utility.showSnackBar(activity(), coordinator_layout, snackBarText, type);
    }

    @Override
    public Activity activity() {
        return getActivity();
    }
}
