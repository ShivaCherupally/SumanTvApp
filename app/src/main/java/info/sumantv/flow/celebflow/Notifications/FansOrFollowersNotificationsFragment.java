package info.sumantv.flow.celebflow.Notifications;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataNewAdapter;
import info.sumantv.flow.bottommenu.interfaces.fragments.INotificationClick;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.celebflow.modelData.NotificationData;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import org.json.JSONArray;
import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class FansOrFollowersNotificationsFragment extends Fragment implements IApiListener, INotificationClick {
    private RecyclerView mFansRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    public String pageType;
    String GetDataID;
    private NotificationsAdapter notificationsAdapter;
    boolean isSelf;
    IApiListener iApiListener;
    INotificationClick iNotificationClick;
    List<NotificationData> notificationDataList;

    boolean isLoadMoreApiRunning = false, stopLoading = false,isFromArchive = false;
    int LIMIT_COUNT = 20;
    String notificationAll = " ", notifications = " ";
    public static FansOrFollowersNotificationsFragment instance = null;

    public static FansOrFollowersNotificationsFragment getInstance() {
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ValidFragment")
    public FansOrFollowersNotificationsFragment(String pageType, String GetDataID, boolean isSelf ,boolean isFromArchive) {
        this.pageType = pageType;
        this.GetDataID = GetDataID;
        this.isSelf = isSelf;
        this.isFromArchive = isFromArchive;
        Log.e("pageType", pageType);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_fragment, container, false);
        iApiListener = this;
        iNotificationClick = this;
        initializeViews(view);
        if (isFromArchive){
            notificationAll = ApiClient.GET_ARCHIVE_NOTIFICATIONS_ALL;
            notifications = ApiClient.GET_ARCHIVE_NOTIFICATIONS;
        }else {
            notificationAll = ApiClient.GET_NOTIFICATIONS_ALL;
            notifications = ApiClient.GET_NOTIFICATIONS;
        }
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            if (Common.checkInternetConnection(activity())) {
                notificationsAdapter = null;
                getNotifications(GetDataID, false);
            } else {
                Common.getInstance().cusToast(activity(),Constants.PLEASE_CHECK_INTERNET);
            }
        });
        refreshData();
        return view;
    }

    private void setSkelltonAdapter() {
        mFansRecyclerView.setAdapter(new NotificationsAdapter(activity(),RController.LOADING));
    }

    private void initializeViews(View view) {
        mFansRecyclerView = (RecyclerView) view.findViewById(R.id.fans_recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        layoutManager = new LinearLayoutManager(getActivity());
        mFansRecyclerView.setLayoutManager(layoutManager);
        mFansRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (notificationDataList != null && notificationDataList.size() > 0 && !stopLoading) {
                        getNotifications(GetDataID, true);
                    }
                }
            }
        });
    }

    private void getNotifications(String GetDataID, boolean isLoadMore) {
        if (isLoadMoreApiRunning) {
            return;
        }
        IApiListener iApiListener = this;
        Call<ApiResponseModel> call = null;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        if (!isLoadMore) {
            if (Common.getInstance().checkInterConnection(mFansRecyclerView, activity())) {
                if (pageType.equals(Constants.NotificationConstants.ALL)) {
                    call = apiInterface.getNotificationData(notificationAll +
                            GetDataID + "/null/" + LIMIT_COUNT);
                } else {
                    call = apiInterface.getNotificationData(notifications +
                            GetDataID + "/" + pageType + "/null/" + LIMIT_COUNT);
                }
            }
        } else {
            if (Common.checkInternetConnection(activity())) {
                if (pageType.equals(Constants.NotificationConstants.ALL)) {
                    call = apiInterface.getNotificationData(notificationAll +
                            GetDataID + "/" + notificationDataList.get(notificationDataList.size() - 1).createdAt + "/" + LIMIT_COUNT);
                } else {
                    call = apiInterface.getNotificationData(notifications +
                            GetDataID + "/" + pageType + "/" + notificationDataList.get(notificationDataList.size() - 1).createdAt + "/" + LIMIT_COUNT);
                }
            } else {
                Common.getInstance().cusToast(activity(),Constants.PLEASE_CHECK_INTERNET);
                return;
            }
        }
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_NOTIFICATIONS, false, iApiListener, false));
        isLoadMoreApiRunning = true;
    }


    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_NOTIFICATIONS)) {
            isLoadMoreApiRunning = false;
            try {
                Type type = new TypeToken<List<NotificationData>>() {}.getType();
                if (notificationsAdapter == null) {
                    notificationDataList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    stopLoading = false;
                    if (notificationDataList != null && notificationDataList.size() > 0) {
                        notificationsAdapter = new NotificationsAdapter(getContext(),isSelf, notificationDataList,pageType);
                        mFansRecyclerView.setAdapter(notificationsAdapter);
                    } else {
                        noOrderAvailable();
                    }
                } else {
                    List<NotificationData> notificationDataListTemp = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    if (notificationDataListTemp.size() < LIMIT_COUNT) {
                        stopLoading = true;
                    }
                    boolean isAllSelected = notificationsAdapter.getSelectedList().size() == notificationDataList.size();
                    if (notificationDataListTemp.size() > 0) {
                    notificationDataList.addAll(notificationDataListTemp);
                    notificationsAdapter.loadMore(notificationDataList);
                    if(isAllSelected){
                        selectAll();
                    }
                }
            }
                notificationsAdapter.updateNotificationIcons();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        isLoadMoreApiRunning = false;
        mFansRecyclerView.setAdapter(new EmptyDataNewAdapter(getActivity(),
                Constants.SOMETHING_WENT_WRONG_SERVER,
                Constants.DRAG_TO_RETRY,
                R.drawable.ic_no_new_notifications));
    }

    private void noOrderAvailable() {
        mFansRecyclerView.setAdapter(new EmptyDataNewAdapter(activity(), Constants.UH_OH,
                Constants.NO_NOTIFICATIONS_AVAILALE, R.drawable.ic_no_notifications));
    }

    @Override
    public void INotificationClick(int position, List<NotificationData> notificationDataList) {

    }

    public Activity activity() {
        return getActivity();
    }

    public void selectAll(){
        if(notificationsAdapter != null){
            notificationsAdapter.selectAll();
        }
    }

    public void deleteAll(){
        if(notificationsAdapter != null && notificationsAdapter.getSelectedList().size() > 0){
            JSONArray jsonArray = new JSONArray();
            ArrayList<String> selected_ids = notificationsAdapter.getSelectedList();
            for(int i=0;i<selected_ids.size();i++){
                jsonArray.put(selected_ids.get(i));
            }
            notificationsAdapter.deleteNotification(jsonArray,-1,true,
                    notificationsAdapter.getSelectedList().size() == notificationDataList.size(), pageType);
        } else {
            Common.getInstance().cusToast(activity(),"Please select ate least on item");
        }
    }

    public void refreshData(){
        setSkelltonAdapter();
        notificationDataList = new ArrayList<>();
        if(notificationsAdapter != null){
            notificationsAdapter.setSelectedList(new ArrayList<>());
        }
        notificationsAdapter = null;
        getNotifications(GetDataID, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            try {
                instance = this;
                refreshData();
                if (Common.getInstance().getHelperActivity() != null) {
                    Common.getInstance().getHelperActivity().enableNotificationIcons(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*if (notificationsAdapter != null && notificationsAdapter.getSelectedList().size() > 0) {
                notificationsAdapter.updateNotificationIcons();
            } else {
                if (Common.getInstance().getHelperActivity() != null) {
                    Common.getInstance().getHelperActivity().enableNotificationIcons(false);
                }
            }*/
        }
    }
}
