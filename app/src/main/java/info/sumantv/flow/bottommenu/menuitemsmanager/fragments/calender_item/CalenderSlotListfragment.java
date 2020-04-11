package info.sumantv.flow.bottommenu.menuitemsmanager.fragments.calender_item;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataNewAdapter;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.modelclasses.CalenderSlot;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Utility;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CalenderSlotListfragment extends Fragment implements View.OnClickListener, IApiListener, IFragment {

    private RecyclerView schudleListRcyView;
    SlotItemAdapter myschedulerAdapter;
    Button addScheduleBtn;
    ApiInterface apiInterface;
    IApiListener iApiListener;
    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.LayoutManager layoutManagerOffline;
    ArrayList<CalenderSlot> calenderSlotsList;
    int LIMIT_COUNT = 10;
    boolean isLoadMoreApiRunning = false;
    boolean stopLoading = false;
    CoordinatorLayout coordinator_layout;
    String celebID = "";

    static CalenderSlotListfragment instance;
    boolean isSelectAll = false;

    public static CalenderSlotListfragment getInstance() {
        return instance;
    }

    public static CalenderSlotListfragment newInstance(String celebID, String param2) {
        CalenderSlotListfragment fragment = new CalenderSlotListfragment();
        Bundle args = new Bundle();
        args.putString("celebID", celebID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_service_settings, null);
        iApiListener = this;
        if (getArguments() != null) {
            celebID = getArguments().getString("celebID");
        }

        initializeViews(root);
        setSkelltonAdapter();

        allScheduleList(false);
        return root;
    }

    private void initializeViews(View root) {
        coordinator_layout = root.findViewById(R.id.coordinator_layout);
        schudleListRcyView = (RecyclerView) root.findViewById(R.id.schudleList);
        addScheduleBtn = (Button) root.findViewById(R.id.addScheduleBtn);
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        schudleListRcyView.setItemAnimator(new DefaultItemAnimator());
        if (celebID.equalsIgnoreCase(SessionManager.userLogin.userId)) {
            addScheduleBtn.setVisibility(View.VISIBLE);
        } else {
            addScheduleBtn.setVisibility(View.GONE);
        }
        initializeActions();

    }

    private void initializeActions() {
        addScheduleBtn.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            if (Common.checkInternetConnection(activity())) {
                myschedulerAdapter = null;

                isSelectAll = false;
                if (Common.getInstance().getHelperActivity() != null) {
                    Common.getInstance().getHelperActivity().enableNotificationIcons(false);
                }

                allScheduleList(false);
            } else {
                showSnackBar(Constants.PLEASE_CHECK_INTERNET, 5);
            }
        });

        schudleListRcyView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (calenderSlotsList != null && calenderSlotsList.size() > 0 && !stopLoading) {
                        if (!stopLoading) {
                            allScheduleList(true);
                        }

                    }
                }
            }
        });
    }

    public void createAdapter() {
        myschedulerAdapter = null;
    }

    public void refList() {
        createAdapter();
        allScheduleList(false);
    }

    public void allScheduleList(Boolean isLoadmore) {
        if (isLoadMoreApiRunning) {
            return;
        }
        IApiListener iApiListener = this;
        Call<ApiResponseModel> call = null;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if (!isLoadmore) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("createdAt", "null");
                jsonObject.put("limit", LIMIT_COUNT);
                jsonObject.put("memberId", celebID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
            call = apiInterface.POST(ApiClient.GET_SLOTS_LIST, requestBody);
        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("createdAt", calenderSlotsList.get(calenderSlotsList.size() - 1).createdAt);
                jsonObject.put("limit", LIMIT_COUNT);
                jsonObject.put("memberId", celebID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
            call = apiInterface.POST(ApiClient.GET_SLOTS_LIST, requestBody);
        }

        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_SLOTS_LIST,
                false, iApiListener, false));
        isLoadMoreApiRunning = true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addScheduleBtn) {


                Intent intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_KEY, 9043);// CreateScheduleFragment
                startActivity(intent);
        }
    }

    private void setSkelltonAdapter() {
        layoutManagerOffline = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        schudleListRcyView.setLayoutManager(layoutManagerOffline);
        schudleListRcyView.removeAllViews();
        schudleListRcyView.setAdapter(new SlotItemAdapter(RController.LOADING));
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {

        if (condition.equals(ApiClient.GET_SLOTS_LIST)) {
            isLoadMoreApiRunning = false;
            try {
                Type type = new TypeToken<List<CalenderSlot>>() {
                }.getType();
                if (myschedulerAdapter == null) {
                    calenderSlotsList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    stopLoading = false;

                    if (calenderSlotsList.size() > 0) {
                        if (calenderSlotsList.size() < LIMIT_COUNT) {
                            stopLoading = true;
                        }
                        myschedulerAdapter = new SlotItemAdapter(getActivity(), calenderSlotsList, celebID);
                        schudleListRcyView.setAdapter(myschedulerAdapter);

                        myschedulerAdapter.notifyDataSetChanged();
                    } else {
                        emptyDataList();
                    }
                } else {
                    ArrayList<CalenderSlot> scheduleListtemp = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    if (scheduleListtemp.size() < LIMIT_COUNT) {
                        stopLoading = true;
                    }
                    boolean isAllSelected = myschedulerAdapter.getSelectedList().size() == calenderSlotsList.size();

                    if (scheduleListtemp.size() > 0) {
                        calenderSlotsList.addAll(scheduleListtemp);
                        myschedulerAdapter.loadmore(calenderSlotsList);
                    }
                    if (isAllSelected) {
                        if (myschedulerAdapter != null) {
                            myschedulerAdapter.selectAllInAsynTask();
                        }
                    }
                    myschedulerAdapter.updateNotificationIcons();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void emptyDataList() {
        schudleListRcyView.removeAllViews();
        schudleListRcyView.setAdapter(new EmptyDataNewAdapter(getActivity(), "No Schedules", "", R.drawable.ic_no_upcoming_events));
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_SLOTS_LIST)) {
            isLoadMoreApiRunning = false;
            schudleListRcyView.setAdapter(new EmptyDataNewAdapter(getActivity(), Constants.SOMETHING_WENT_WRONG_SERVER, Constants.DRAG_TO_RETRY,
                    R.drawable.ic_no_upcoming_events));

            if (Common.getInstance().getHelperActivity() != null) {
                Common.getInstance().getHelperActivity().enableNotificationIcons(false);
            }
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

    public void selectAll(boolean isAll) {
        if (myschedulerAdapter != null) {
            myschedulerAdapter.selectAllInAsynTask();
            isSelectAll = isAll;
        }
    }

    public void deleteAll() {
        if (myschedulerAdapter != null && myschedulerAdapter.getSelectedList().size() > 0) {
            JSONArray jsonArray = new JSONArray();
            ArrayList<String> selected_ids = myschedulerAdapter.getSelectedList();
            for (int i = 0; i < selected_ids.size(); i++) {
                jsonArray.put(selected_ids.get(i));
            }
            myschedulerAdapter.deleteSchedule(jsonArray, -1, isSelectAll, myschedulerAdapter.getSelectedList().size() == calenderSlotsList.size());
        } else {
            Common.getInstance().cusToast(activity(), "Please select ate least on item");
        }
    }

    public void refreshData(boolean isAllDelete) {

        if (Common.getInstance().getHelperActivity() != null) {
            Common.getInstance().getHelperActivity().enableNotificationIcons(false);
        }

        if (isAllDelete) {
            emptyDataList();
        } else {
            setSkelltonAdapter();
            calenderSlotsList = new ArrayList<>();
            if (myschedulerAdapter != null) {
                myschedulerAdapter.setSelectedList(new ArrayList<>());
            }
            myschedulerAdapter = null;
            allScheduleList(false);
        }


//
    }
}

