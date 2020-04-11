package info.sumantv.flow.bottommenu.menuitemsmanager.fragments;


import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataNewAdapter;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;
import info.sumantv.flow.celebflow.Notifications.ActivityLogAdapter;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.celebflow.modelData.ActivityLog;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityLogFragment extends Fragment implements IFragment, IApiListener {

    RecyclerView rViewActivityLOg;
    private RecyclerView.LayoutManager layoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean isLoadMoreApiRunning = false, stopLoading = false;
    int LIMIT_COUNT = 20;
    IApiListener iApiListener;
    List<ActivityLog> activityLogList;
    ActivityLogAdapter activityLogAdapter;
    String userID;
    public ActivityLogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iApiListener = this;
    }

    public static ActivityLogFragment newInstance(String param1, String param2) {
        ActivityLogFragment fragment = new ActivityLogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_activity_log, container, false);
        initializeViews(root);
        userID = SessionManager.userLogin.userId;
        setSkelltonAdapter();
        getActivityLog(false);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            if (Common.checkInternetConnection(activity())) {
                activityLogAdapter = null;
                getActivityLog( false);
            } else {
                Common.getInstance().cusToast(activity(), Constants.PLEASE_CHECK_INTERNET);
            }
        });
        return root;
    }

    private void initializeViews(View view) {
        rViewActivityLOg = view.findViewById(R.id.rViewActivityLOg);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        layoutManager = new LinearLayoutManager(getActivity());
        rViewActivityLOg.setLayoutManager(layoutManager);
        rViewActivityLOg.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (activityLogList != null && activityLogList.size() > 0 && !stopLoading) {
                        getActivityLog(true);
                    }
                }
            }
        });
    }
    private void setSkelltonAdapter() {
        rViewActivityLOg.setAdapter(new ActivityLogAdapter(activity(), RController.LOADING));
    }
    private void getActivityLog( boolean isLoadMore) {
        if (isLoadMoreApiRunning) {
            return;
        }
        IApiListener iApiListener = this;
        Call<ApiResponseModel> call = null;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if (!isLoadMore) {
            if (Common.getInstance().checkInterConnection(rViewActivityLOg, activity())) {
                String url = ApiClient.GET_ACTIVITY_LOG + userID + "/null/" + LIMIT_COUNT;
                call = apiInterface.GET(url);
            }
        } else {
            if (Common.checkInternetConnection(activity())) {
                String url = ApiClient.GET_ACTIVITY_LOG + userID + "/" + activityLogList.get(activityLogList.size() - 1).createdAt + "/" + LIMIT_COUNT;
                call = apiInterface.GET(url);
            } else {
                showSnackBar(Constants.PLEASE_CHECK_INTERNET, 5);
                return;
            }
        }
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_ACTIVITY_LOG, false, iApiListener, false));
        isLoadMoreApiRunning = true;


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

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_ACTIVITY_LOG)) {
            isLoadMoreApiRunning = false;
            try {
                Type type = new TypeToken<List<ActivityLog>>() {}.getType();
                if (activityLogAdapter == null) {
                    activityLogList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    stopLoading = false;
                    if (activityLogList.size() > 0) {
                        activityLogAdapter = new ActivityLogAdapter(activity(), activityLogList);
                        rViewActivityLOg.setAdapter(activityLogAdapter);
                    } else {
                        noOrderAvailable();
                    }
                } else {
                    ArrayList<ActivityLog> activityLogsTemp = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    if (activityLogsTemp.size() < LIMIT_COUNT){
                        stopLoading = true;
                    }
                    if (activityLogsTemp.size() > 0) {
                        activityLogList.addAll(activityLogsTemp);
                        activityLogAdapter.loadmore(activityLogList);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                rViewActivityLOg.setAdapter(new EmptyDataNewAdapter(activity(), Constants.SOMETHING_WENT_WRONG_SERVER, Constants.DRAG_TO_RETRY,
                        R.drawable.ic_no_upcoming_events));
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

    }
    private void noOrderAvailable() {
        rViewActivityLOg.setAdapter(new EmptyDataNewAdapter(activity(), Constants.UH_OH,
                Constants.NO_CONETENTS, R.drawable.ic_no_upcoming_events));
    }
}
