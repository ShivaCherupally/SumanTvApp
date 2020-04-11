package info.sumantv.flow.chat.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.sumantv.flow.ModelClass.ProfileDataModel;
import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataAdapter;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.chat.adapters.ChatListAdapter;
import info.sumantv.flow.chat.interfaces.IChatListAdapter;
import info.sumantv.flow.chat.models.ChatDataConvertModel;
import info.sumantv.flow.chat.models.ChatDataModel;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.SocketForAppUtill;
import info.sumantv.flow.utils.Utility;
import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FragmentChatList extends Fragment implements IFragment, IChatListAdapter,IApiListener {
    private CoordinatorLayout coordinator_layout;
    RecyclerView rvChatList;
    SwipeRefreshLayout swipeRefreshLayout;
    ChatListAdapter chatListAdapter;
    ArrayList<ChatDataConvertModel> chatDataConvertModelArrayList;
    ChatDataConvertModel chatDataConvertModelParent;
    IChatListAdapter iChatListAdapter;
    ProgressDialog progressDialog;
    String userName = "", userEmail = "", userMemberId = "";
    FloatingActionButton fabNewChat;
    Boolean isFragLoadedCompletely = false,isFragVisibleToUser = false;
    IApiListener iApiListener;

    public FragmentChatList() {
        // Required empty public constructor
    }
    public static FragmentChatList newInstance(ChatDataConvertModel chatDataConvertModel, String param2) {
        FragmentChatList fragment = new FragmentChatList();
        Bundle args = new Bundle();
        args.putParcelable("chatDataConvertModel", chatDataConvertModel);
        args.putString("param", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.getInstance().setFragmentChatList(this);
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
        View root = inflater.inflate(R.layout.fragment_chat_list, container, false);
        iApiListener = this;
        iChatListAdapter = this;
        isFragLoadedCompletely = false;
        isFragVisibleToUser = true;//Do not change to false, because setUserVisibleHint() method will not call first time
        chatDataConvertModelArrayList = new ArrayList<>();
        userName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userName, "");
        userEmail = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE,""), "");
        userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
        if (userMemberId.isEmpty()) {
            activity().finish();
        }
        initializeViews(root);
        getChatList();
        SocketForAppUtill.getInstance().missedCallCountEmit();
        return root;
    }

    private void initializeViews(View root) {
        coordinator_layout = root.findViewById(R.id.coordinator_layout);
        rvChatList = root.findViewById(R.id.rvChatList);
        fabNewChat = root.findViewById(R.id.fabNewChat);
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        //
        rvChatList.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        //
        fabNewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_KEY, 8101);
                intent.putExtra(Constants.FRAGMENT_TITLE, "Select a Celebrity");
                intent.putExtra("params", "");
                startActivityForResult(intent, Utility.generateRequestCodes().get("CHAT_RECENT_LIST"));
            }
        });
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            getChatList();
            if (Common.getInstance().getBottomMenuActivity() != null) {
                Common.getInstance().getBottomMenuActivity().collapseSearchView();
            }
        });
    }

    private void getChatList() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        chatListAdapter = new ChatListAdapter(null, activity(), iChatListAdapter,RController.LOADING);
        rvChatList.setAdapter(chatListAdapter);
        Call<ApiResponseModel> call = apiInterface.getCurrentMemberChat(Constants.ChatConstants.getCurrentMemberChat + userMemberId + "/");
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(),call,Constants.ChatConstants.getCurrentMemberChat,false,iApiListener,false));
    }

    public void doSearch(String query){
        if (query != null && query.trim().length() > 0) {
            new getSearchFilterData().execute(query.trim());
        } else {
            setChatListAdapter(chatDataConvertModelArrayList);
        }
    }

    private void setChatListAdapter(ArrayList<ChatDataConvertModel> chatDataConvertModelArrayList) {
        try {
            if (chatDataConvertModelArrayList != null && chatDataConvertModelArrayList.size() > 0) {
                chatListAdapter = new ChatListAdapter(chatDataConvertModelArrayList, activity(), iChatListAdapter,RController.LOADED);
                rvChatList.setAdapter(chatListAdapter);
            } else {
//                rvChatList.setAdapter(new EmptyDataAdapter(activity(),
//                        Constants.NO_DATA_AVAILABLE, R.drawable.ic_nodata, 2));


                rvChatList.setAdapter(new EmptyDataAdapter(activity(), Constants.GET_TYPE, Constants.START_TYPE,
                        R.drawable.ic_get_typing, 6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class asyncTaskConvertModelData extends AsyncTask<String, Void, ArrayList<ChatDataConvertModel>> {
        ArrayList<ChatDataModel> chatDataModelList;

        public asyncTaskConvertModelData(ArrayList<ChatDataModel> chatDataModelList) {
            this.chatDataModelList = chatDataModelList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ChatDataConvertModel> doInBackground(String... params) {
            ArrayList<ChatDataConvertModel> filterList = new ArrayList<>();
            try {
                if (chatDataModelList != null && chatDataModelList.size() > 0) {
                    for (int i = 0; i < chatDataModelList.size(); i++) {
                        ChatDataConvertModel chatDataConvertModel = Common.getInstance().convertToChatDataConvertModel(chatDataModelList.get(i), userMemberId);
                        if (chatDataConvertModel != null) {
                            filterList.add(chatDataConvertModel);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return filterList;
        }

        @Override
        protected void onPostExecute(ArrayList<ChatDataConvertModel> result) {
            super.onPostExecute(result);
            chatDataConvertModelArrayList = result;
            setChatListAdapter(result);
        }
    }

    public void updateRecentChatListAdapter(ArrayList<ChatDataConvertModel> recentChatDataConvertModelList) {
        new updateRecentChatListAdapter(recentChatDataConvertModelList).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class updateRecentChatListAdapter extends AsyncTask<String, Void, ArrayList<ChatDataConvertModel>> {
        ArrayList<ChatDataConvertModel> recentChatDataConvertModelList;

        public updateRecentChatListAdapter(ArrayList<ChatDataConvertModel> recentChatDataConvertModelList) {
            this.recentChatDataConvertModelList = recentChatDataConvertModelList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ChatDataConvertModel> doInBackground(String... params) {
            /*try {
                ChatDataConvertModel chatDataConvertModelSingleChat = Common.getInstance().getChatDataConvertModelChatSingleActivity();
                if (chatDataConvertModelSingleChat != null) {
                    for (int i = 0; i < chatDataConvertModelArrayList.size(); i++) {
                        if (chatDataConvertModelArrayList.get(i).receiverId.equalsIgnoreCase(chatDataConvertModelSingleChat.receiverId)) {
                            chatDataConvertModelArrayList.get(i).counter = 0;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            try {
                if (recentChatDataConvertModelList != null) {
                    for (int i = 0; i < recentChatDataConvertModelList.size(); i++) {
                        try {
                            int index = -1;
                            for(int j = 0; j< chatDataConvertModelArrayList.size(); j++){
                                if(recentChatDataConvertModelList.get(i)._id.equalsIgnoreCase(chatDataConvertModelArrayList.get(j)._id)){
                                    index = j;
                                    break;
                                }
                            }
                            if (index > -1) {
                                if (recentChatDataConvertModelList.get(i).putThisAtFirstPosition != null && recentChatDataConvertModelList.get(i).putThisAtFirstPosition) {
                                    chatDataConvertModelArrayList.remove(index);
                                    chatDataConvertModelArrayList.add(0, recentChatDataConvertModelList.get(i));
                                } else {
                                    chatDataConvertModelArrayList.set(index, recentChatDataConvertModelList.get(i));
                                }
                            } else {
                                chatDataConvertModelArrayList.add(0, recentChatDataConvertModelList.get(i));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ArrayList<ChatDataConvertModel> fanUnFanChatDataConvertModelList = Common.getInstance().getFanUnFanChatDataConvertModelList();
                if (fanUnFanChatDataConvertModelList != null) {
                    for (int i = 0; i < fanUnFanChatDataConvertModelList.size(); i++) {
                        try {
                            int index = -1;
                            for(int j = 0; j< chatDataConvertModelArrayList.size(); j++){
                                if(fanUnFanChatDataConvertModelList.get(i)._id.equalsIgnoreCase(chatDataConvertModelArrayList.get(j)._id)){
                                    index = j;
                                    chatDataConvertModelArrayList.get(j).isFan = fanUnFanChatDataConvertModelList.get(i).isFan;
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
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<ChatDataConvertModel> result) {
            super.onPostExecute(result);
            if (chatListAdapter != null) {
                chatListAdapter.notifyDataSetChanged();
            } else {
                setChatListAdapter(chatDataConvertModelArrayList);
            }
            Common.getInstance().setRecentChatDataConvertModelList(null);
        }
    }

    @Override
    public void openSingleChatWindow(ChatDataConvertModel chatDataConvertModel, Integer position) {
        Common.getInstance().openSingleChatActivity(activity(),activity(),chatDataConvertModel);
    }

    @Override
    public void openProfileDialog(ChatDataConvertModel chatDataConvertModel, Integer position) {
        try {
            ProfileDataModel profileDataModel = Common.getInstance().convertToViewProfile(chatDataConvertModel);
//            Common.getInstance().viewCelebProfileDialog(activity(), profileDataModel, progressDialog);

            //checking with customize class
            Common.getInstance().viewCelebProfileDialog(activity(), profileDataModel, progressDialog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getSearchFilterData extends AsyncTask<String, Void, ArrayList<ChatDataConvertModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ChatDataConvertModel> doInBackground(String... params) {
            String searchString = params[0].toLowerCase();
            ArrayList<ChatDataConvertModel> filterList = (ArrayList<ChatDataConvertModel>)(ArrayList<?>) Common.getInstance().getFilteredListOfSearchObject(chatDataConvertModelArrayList,searchString,1);
            return filterList;
        }

        @Override
        protected void onPostExecute(ArrayList<ChatDataConvertModel> result) {
            super.onPostExecute(result);
            setChatListAdapter(result);
        }
    }

    public Boolean getFragVisibility(){
        return isFragVisibleToUser;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isFragLoadedCompletely){
            updateRecentChatListAdapter(Common.getInstance().getRecentChatDataConvertModelList());
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isFragVisibleToUser = isVisibleToUser;
        if(isVisibleToUser){
            if(isFragLoadedCompletely){
                updateRecentChatListAdapter(Common.getInstance().getRecentChatDataConvertModelList());
            }
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                isFragLoadedCompletely = true;
            }
        },1000);
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
        Common.getInstance().setFragmentChatList(null);
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if(condition.equals(Constants.ChatConstants.getCurrentMemberChat)){
            try {
                Type type = new TypeToken<ArrayList<ChatDataModel>>(){}.getType();
                ArrayList<ChatDataModel> chatDataModelList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data),type);
                new asyncTaskConvertModelData(chatDataModelList).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if(condition.equals(Constants.ChatConstants.getCurrentMemberChat)){
            rvChatList.setAdapter(new EmptyDataAdapter(activity(),
                    enumConstants == EnumConstants.NO_NETWORK ? Constants.NO_INTERNET:Constants.SOMETHING_WENT_WRONG,
                    R.drawable.ic_network, 2));
        }
    }
}
