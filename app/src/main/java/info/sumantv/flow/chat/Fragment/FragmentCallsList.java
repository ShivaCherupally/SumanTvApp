package info.sumantv.flow.chat.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import info.sumantv.flow.ModelClass.ProfileDataModel;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataAdapter;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.chat.adapters.CallsListAdapter;
import info.sumantv.flow.chat.interfaces.ICallsListAdapter;
import info.sumantv.flow.chat.models.ChatDataConvertModel;
import info.sumantv.flow.chat.models.ChatSenderReceiverInfo;
import info.sumantv.flow.chat.models.RecentCallsModel;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.SocketForAppUtill;
import info.sumantv.flow.utils.Utility;
import retrofit2.Call;

public class FragmentCallsList extends Fragment implements IFragment, ICallsListAdapter, IApiListener {
    private CoordinatorLayout coordinator_layout;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    CallsListAdapter callsListAdapter;
    ArrayList<RecentCallsModel> recentCallsModelArrayList;
    ChatDataConvertModel chatDataConvertModelParent;
    ICallsListAdapter iCallsListAdapter;
    ProgressDialog progressDialog;
    String userName = "", userEmail = "", userMemberId = "";
    Boolean isFragLoadedCompletely = false, isFragVisibleToUser = false, isAPICalled = false;
    IApiListener iApiListener;

    public FragmentCallsList() {
        // Required empty public constructor
    }

    public static FragmentCallsList newInstance(ChatDataConvertModel chatDataConvertModel, String param2) {
        FragmentCallsList fragment = new FragmentCallsList();
        Bundle args = new Bundle();
        args.putParcelable("chatDataConvertModel", chatDataConvertModel);
        args.putString("param", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.getInstance().setFragmentCallsList(this);
        if (getArguments() != null) {
            try {
                chatDataConvertModelParent = getArguments().getParcelable("chatDataConvertModel");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calls_list, container, false);
        iCallsListAdapter = this;
        iApiListener = this;
        isFragLoadedCompletely = false;
        isFragVisibleToUser = false;
        isAPICalled = false;
        recentCallsModelArrayList = new ArrayList<>();
        userName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userName, "");
        userEmail = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, ""), "");
        userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
        if (userMemberId.isEmpty()) {
            activity().finish();
        }
        initializeViews(root);

        SocketForAppUtill.getInstance().missedCallCountEmit();

        //getCallsList();
        return root;
    }

    private void initializeViews(View root) {
        coordinator_layout = root.findViewById(R.id.coordinator_layout);
        recyclerView = root.findViewById(R.id.recyclerView);
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        //
        recyclerView.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            getCallsList();
            if (Common.getInstance().getBottomMenuActivity() != null) {
                Common.getInstance().getBottomMenuActivity().collapseSearchView();
            }
        });
    }

    public void getCallsList() {
        if (!Utility.isNetworkAvailable(activity())) {
            showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
            recyclerView.setAdapter(new EmptyDataAdapter(activity(), Constants.NO_INTERNET, R.drawable.ic_network, 0));
            return;
        }
        callsListAdapter = new CallsListAdapter(null, activity(), iCallsListAdapter, RController.LOADING);
        recyclerView.setAdapter(callsListAdapter);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.newGetCallHistoryByMemberId(Constants.ChatConstants.newGetCallHistoryByMemberId + userMemberId + "/");
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.ChatConstants.newGetCallHistoryByMemberId, false, iApiListener, false));
    }

    private void setListAdapter(ArrayList<RecentCallsModel> recentCallsModelArrayList) {
        try {
            if (recentCallsModelArrayList != null && recentCallsModelArrayList.size() > 0) {
                callsListAdapter = new CallsListAdapter(recentCallsModelArrayList, activity(), iCallsListAdapter, RController.LOADED);
                recyclerView.setAdapter(callsListAdapter);
            } else {
//                recyclerView.setAdapter(new EmptyDataAdapter(activity(), Constants.NO_DATA_AVAILABLE, R.drawable.ic_nodata, 2));
                recyclerView.setAdapter(new EmptyDataAdapter(activity(), Constants.NO_CALLS, "",
                        R.drawable.ic_no_calls, 6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doSearch(String query) {
        if (query != null && query.trim().length() > 0) {
            new getSearchFilterData().execute(query.trim());
        } else {
            setListAdapter(recentCallsModelArrayList);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getSearchFilterData extends AsyncTask<String, Void, ArrayList<RecentCallsModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<RecentCallsModel> doInBackground(String... params) {
            String searchString = params[0].toLowerCase();
            ArrayList<RecentCallsModel> filterList = (ArrayList<RecentCallsModel>) (ArrayList<?>) Common.getInstance().getFilteredListOfSearchObject(recentCallsModelArrayList, searchString, 2);
            return filterList;
        }

        @Override
        protected void onPostExecute(ArrayList<RecentCallsModel> result) {
            super.onPostExecute(result);
            setListAdapter(result);
        }
    }

    public void updateRecentCallsListAdapter() {
        new updateRecentCallsListAdapter().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class updateRecentCallsListAdapter extends AsyncTask<String, Void, ArrayList<RecentCallsModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<RecentCallsModel> doInBackground(String... params) {
            ArrayList<RecentCallsModel> filterList = new ArrayList<>();
            try {
                ArrayList<ChatDataConvertModel> fanUnFanChatDataConvertModelList = Common.getInstance().getFanUnFanChatDataConvertModelList();
                if (fanUnFanChatDataConvertModelList != null) {
                    for (int i = 0; i < fanUnFanChatDataConvertModelList.size(); i++) {
                        try {
                            int index = -1;
                            for (int j = 0; j < recentCallsModelArrayList.size(); j++) {
                                ChatSenderReceiverInfo chatSenderReceiverInfo;
                                if (userMemberId.equalsIgnoreCase(recentCallsModelArrayList.get(j).lastCallStatus.receiverId._id)) {
                                    chatSenderReceiverInfo = recentCallsModelArrayList.get(j).lastCallStatus.senderId;
                                } else {
                                    chatSenderReceiverInfo = recentCallsModelArrayList.get(j).lastCallStatus.receiverId;
                                }
                                if (fanUnFanChatDataConvertModelList.get(i)._id.equalsIgnoreCase(chatSenderReceiverInfo._id)) {
                                    index = j;
                                    recentCallsModelArrayList.get(j)._id.isFan = fanUnFanChatDataConvertModelList.get(i).isFan;
                                    break;
                                }
                            }
                            if (index > -1) {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return filterList;
        }

        @Override
        protected void onPostExecute(ArrayList<RecentCallsModel> result) {
            super.onPostExecute(result);
            if (callsListAdapter != null) {
                callsListAdapter.notifyDataSetChanged();
            } else {
                if (recentCallsModelArrayList != null && recentCallsModelArrayList.size() > 0) {
                    setListAdapter(recentCallsModelArrayList);
                }
            }
        }
    }

    @Override
    public void openProfileDialog(ChatSenderReceiverInfo chatSenderReceiverInfo, Integer position) {
        try {
            ProfileDataModel profileDataModel = Common.getInstance().convertToViewProfile(chatSenderReceiverInfo);
//            Common.getInstance().viewCelebProfileDialog(activity(), profileDataModel, progressDialog);
            //checking with customize class
            Common.getInstance().viewCelebProfileDialog(activity(), profileDataModel, progressDialog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initiateCall(String serviceType, ChatSenderReceiverInfo chatSenderReceiverInfo) {
        try {
            ProfileDataModel profileDataModel = Common.getInstance().convertToViewProfile(chatSenderReceiverInfo);
            profileDataModel.serviceType = serviceType;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFragLoadedCompletely) {
            updateRecentCallsListAdapter();
        }
    }

    public ArrayList<RecentCallsModel> getRecentCallsModelArrayList() {
        return recentCallsModelArrayList;
    }

    public Boolean getFragVisibility() {
        return isFragVisibleToUser;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isFragVisibleToUser = isVisibleToUser;
        recentCallsModelArrayList = new ArrayList<>();
        if (isVisibleToUser) {
            if (/*isFragLoadedCompletely && */!isAPICalled && recentCallsModelArrayList.size() <= 0) {
                getCallsList();
            }
            if (isFragLoadedCompletely) {
                updateRecentCallsListAdapter();
            }
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                isFragLoadedCompletely = true;
            }
        }, 1000);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Common.getInstance().setFragmentCallsList(null);
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.ChatConstants.newGetCallHistoryByMemberId)) {
            try {
                if (recentCallsModelArrayList == null) {
                    recentCallsModelArrayList = new ArrayList<>();
                }
                Type type = new TypeToken<ArrayList<RecentCallsModel>>() {
                }.getType();
                recentCallsModelArrayList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                setListAdapter(recentCallsModelArrayList);
                isAPICalled = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.ChatConstants.newGetCallHistoryByMemberId)) {
            recyclerView.setAdapter(new EmptyDataAdapter(activity(), enumConstants == EnumConstants.NO_NETWORK ? Constants.NO_INTERNET : Constants.SOMETHING_WENT_WRONG, R.drawable.ic_network, 2));
        }
    }



}
