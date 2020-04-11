package info.sumantv.flow.bottommenu.menuitemsmanager.fragments;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.notificationssettingsapp.NotificationSwitchAdapter;
import info.sumantv.flow.celebflow.notificationssettingsapp.NotificationSwitchListData;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NotificationSettingsFragment extends Fragment implements IFragment, IApiListener {
    ApiInterface apiInterface;
    public SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView.Adapter notificationSwitchAdapter;
    ArrayList<NotificationSwitchListData> notificationSwitchListData;
    RecyclerView notificationRecyList;
    TextView common_nodatatxt;
    ProgressBar common_progressBar;
    IApiListener iApiListener;

    public static NotificationSettingsFragment newInstance() {
        NotificationSettingsFragment fragment = new NotificationSettingsFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iApiListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_notification_settings, null);
        initializeViews(root);
        getNoticationSwitchList();
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            getNoticationSwitchList();
        });
        return root;
    }


    private void initializeViews(View root) {
        commonRecyclerAndnodata(root);
    }

    private void commonRecyclerAndnodata(View root) {
        notificationRecyList = (RecyclerView) root.findViewById(R.id.common_Recyclerview);
        common_nodatatxt = (TextView) root.findViewById(R.id.common_nodatatxt);
        common_progressBar = (ProgressBar) root.findViewById(R.id.common_progressBar);
    }

    private void getNoticationSwitchList() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getAllNotificationAppList(
                ApiClient.GET_NOTIFICATION_SWTTCH_LIST + SessionManager.userLogin.userId + "/");
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_NOTIFICATION_SWTTCH_LIST,
                true, iApiListener, true));
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
        if (condition.equals(ApiClient.GET_NOTIFICATION_SWTTCH_LIST)) {
            try {
                Type type = new TypeToken<ArrayList<NotificationSwitchListData>>() {}.getType();
                notificationSwitchListData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                if (notificationSwitchListData != null) {
                    if (notificationSwitchListData.size() != 0) {
                        notificationSwitchAdapter = new NotificationSwitchAdapter(activity(), notificationSwitchListData);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
                        notificationRecyList.setLayoutManager(mLayoutManager);
                        notificationRecyList.setItemAnimator(new DefaultItemAnimator());
                        notificationRecyList.setAdapter(notificationSwitchAdapter);
                    } else {
                        common_nodatatxt.setText("No Data Available");
                    }
                } else {
                    common_nodatatxt.setText("No Data Available");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

    }
}
