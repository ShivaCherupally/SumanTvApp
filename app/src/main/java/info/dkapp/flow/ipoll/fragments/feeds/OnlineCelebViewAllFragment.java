package info.dkapp.flow.ipoll.fragments.feeds;


import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.dkapp.flow.appmanagers.feed.models.Celebrity;
import info.dkapp.flow.bottommenu.generic.EmptyDataNewAdapter;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.ipoll.adapters.feeds.celebrities.OnlineCelebrityViewAllAdapter;
import info.dkapp.flow.retrofitcall.*;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.RecyclerUtil.CommonRecycler;
import info.dkapp.flow.utils.RecyclerUtil.IRecyclerViewCommon;
import info.dkapp.flow.utils.Utility;
import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineCelebViewAllFragment extends Fragment implements IApiListener, IRecyclerViewCommon {

    List<Celebrity> celebrityList;

    @BindView(R.id.recyclerViewCommon)
    RecyclerView recyclerViewCommon;


    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinator_layout;

    OnlineCelebrityViewAllAdapter onlineCelebrityViewAllAdapter;

    boolean loadMore = false;


    public OnlineCelebViewAllFragment() {
    }

    public static OnlineCelebViewAllFragment newInstance(Objects objects) {
        OnlineCelebViewAllFragment fragment = new OnlineCelebViewAllFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.common_recycler_view_page, container, false);

        ButterKnife.bind(this, view);

        fetchDataFromServer(true);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            fetchDataFromServer(true);
        });

        //If Pagination Come uncomment this code
        /* recyclerViewCommon.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (celebrityList.size() > 0) {
                        fetchDataFromServer(false);
                    } else {
                        fetchDataFromServer(true);
                    }
                }
            }
        });*/


        return view;
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {

        if (condition.equals(Constants.ONLINE_CELEBS)) {
            Type type = new TypeToken<List<Celebrity>>() {
            }.getType();
            List<Celebrity> celebrityListMoreData = null;

            if (!loadMore) {
                celebrityList = new ArrayList<>();
                celebrityList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
            } else {
                celebrityListMoreData = new ArrayList<>();
                celebrityListMoreData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                Log.e("loadMoreData", loadMore + "");
            }


            if (celebrityList != null && celebrityList.size() > 0) {
                if (!loadMore) {
                    recyclerViewCommon.setAdapter(onlineCelebrityViewAllAdapter = new OnlineCelebrityViewAllAdapter(getActivity(), celebrityList));
                } else {
                    celebrityList.addAll(celebrityListMoreData);
                    onlineCelebrityViewAllAdapter.loadmore(celebrityList);
                    showSnackBar("Getting More", 2);
                }
            } else {
                if (!loadMore) {
                    nodataList(recyclerViewCommon, Constants.WHOOPS, Constants.NO_CELEBS, R.drawable.ic_nodata);
                } else {
                    showSnackBar("NO DATA", 5);
                }

            }


        }

    }


    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.ONLINE_CELEBS)) {
            CommonRecycler.setNoInternetOrServerDown(getActivity(), recyclerViewCommon, false);

        }
    }


    @Override
    public void setSkelltonView(RecyclerView recyclerViewCommon, boolean firstTime) {
        CommonRecycler.setSkelltonView(getActivity(), recyclerViewCommon, true, firstTime);
    }

    @Override
    public void nodataList(RecyclerView recyclerViewCommon, String title, String subTitle, int imageResource) {
        recyclerViewCommon.setAdapter(new EmptyDataNewAdapter(getActivity(), title, subTitle, imageResource));
    }

    @Override
    public boolean checkInterConnection(RecyclerView recyclerViewCommon) {
        if (Common.checkInternetConnection(getActivity())) {
            return true;
        } else {
            CommonRecycler.setNoInternetOrServerDown(getActivity(), recyclerViewCommon, false);
            return false;
        }

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        Utility.showSnackBar(getActivity(), coordinator_layout, snackBarText, type);
    }

    @Override
    public void fetchDataFromServer(boolean firstTime) {
        IApiListener iApiListener = this;
        Call<ApiResponseModel> call = null;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        setSkelltonView(recyclerViewCommon, firstTime);
        Log.e("firstTime", firstTime + "");

        if (firstTime) {
            loadMore = false;
            if (checkInterConnection(recyclerViewCommon)) {
                call = apiInterface.GET(ApiClient.GET_ONLINE_CELEBS + Common.isLoginId() + "/");
            }
        } else {
            loadMore = true;
            if (Common.checkInternetConnection(getActivity())) {
                call = apiInterface.GET(ApiClient.GET_ONLINE_CELEBS + Common.isLoginId() + "/");
            } else {
                showSnackBar(Constants.PLEASE_CHECK_INTERNET, 5);
            }
        }
        if (call != null) {
            Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, Constants.ONLINE_CELEBS, false, iApiListener, false));
        }

    }


}
