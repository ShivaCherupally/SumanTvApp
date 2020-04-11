package info.sumantv.flow.bottommenu.menuitemsmanager.fragments.my_schedules;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataAdapter;
import info.sumantv.flow.bottommenu.generic.EmptyDataNewAdapter;
import info.sumantv.flow.celebflow.Notifications.ArchiveNotificationsHome;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArchiveNotificationFragment extends Fragment {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ArchiveNotificationHomeAdapter archiveNotificationHomeAdapter;

    private List<ArchiveNotificationsHome> archiveNotificationsHomeList;


    public static Fragment newInstance(Object o, Object o1) {
        ArchiveNotificationFragment archiveNotificationFragment = new ArchiveNotificationFragment();
        return archiveNotificationFragment;
    }


    public ArchiveNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_archive_notification, container, false);
        ButterKnife.bind(this, view);
        setUp();
        setSkelltonAdapter();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            getHomeArchiveNotifications();
        });

        return view;
    }

    private void setUp() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        getHomeArchiveNotifications();
    }

    private void getHomeArchiveNotifications() {
        setSkelltonAdapter();


        if (!Common.checkInternetConnection(getContext())) {
            recyclerView.removeAllViews();
            recyclerView.setAdapter(new EmptyDataAdapter(getActivity(), Constants.UH_OH, Constants.PLEASE_CHECK_INTERNET,
                    R.drawable.ic_network, 0));
            return;
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(Constants.AuditionConstants.GET_ALL_ARCHIEVED_NOTIFICATION +
                SessionManager.userLogin.userId);
        IApiListener apiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                try {
                    JSONArray response = new JSONArray(new Gson().toJson(apiResponseModel.data));
                    if (response.length() != 0) {
                        archiveNotificationsHomeList = new ArrayList<>();


                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Log.e("completeData", jsonObject + "");
                                ArchiveNotificationsHome archiveNotificationsHome = new ArchiveNotificationsHome();
                                archiveNotificationsHome.setTitle(jsonObject.optString("title", ""));
                                archiveNotificationsHome.setBody(jsonObject.optString("body", ""));
                                archiveNotificationsHome.setNotificationIcon(jsonObject.optString("notificationIcon", ""));
                                archiveNotificationsHome.setStatus(jsonObject.optString("status", ""));
                                archiveNotificationsHome.setNotificationType(jsonObject.optString("notificationType", ""));
                                archiveNotificationsHome.setCreatedBy(jsonObject.optString("createdBy", ""));
                                archiveNotificationsHome.setUpdatedBy(jsonObject.optString("_id", ""));
                                archiveNotificationsHome.setUpdatedAt(jsonObject.optString("updatedAt", ""));
                                archiveNotificationsHome.setMemberId(jsonObject.optString("memberId", ""));
                                archiveNotificationsHome.setCreatedAt(jsonObject.optString("createdAt", ""));
                                archiveNotificationsHome.setStartTime(jsonObject.optString("startTime", ""));
                                archiveNotificationsHome.setEndTime(jsonObject.optString("endTime", ""));
                                archiveNotificationsHomeList.add(archiveNotificationsHome);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        archiveNotificationHomeAdapter = new ArchiveNotificationHomeAdapter(archiveNotificationsHomeList, getActivity());
                        recyclerView.setAdapter(archiveNotificationHomeAdapter);

                    } else {
                        recyclerView.setAdapter(new EmptyDataNewAdapter(getActivity(),
                                Constants.NO_ARCHIVE_NOTIFICATIONS_AVAILALE,
                                Constants.NO_ARCHIVE_NOTIFICATIONS_DATA,
                                R.drawable.ic_no_archived_messages));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                recyclerView.setAdapter(new EmptyDataNewAdapter(getActivity(),
                        Constants.SOMETHING_WENT_WRONG_SERVER,
                        Constants.DRAG_TO_RETRY,
                        R.drawable.ic_no_new_notifications));
            }
        };
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call,
                Constants.AuditionConstants.GET_ALL_ARCHIEVED_NOTIFICATION, false, apiListener, false));
    }


    public Activity activity() {
        return getActivity();
    }

    private void setSkelltonAdapter() {
        archiveNotificationHomeAdapter = new ArchiveNotificationHomeAdapter(RController.LOADING);
        recyclerView.setAdapter(archiveNotificationHomeAdapter);
    }


}
