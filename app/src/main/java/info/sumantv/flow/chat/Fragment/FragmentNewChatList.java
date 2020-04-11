package info.sumantv.flow.chat.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.sumantv.flow.ModelClass.ProfileDataModel;

import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataAdapter;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.chat.adapters.NewChatListAdapter;
import info.sumantv.flow.chat.interfaces.INewChatListAdapter;
import info.sumantv.flow.chat.models.ChatDataConvertModel;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Utility;
import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FragmentNewChatList extends Fragment implements IFragment, INewChatListAdapter, IApiListener {
    private CoordinatorLayout coordinator_layout;
    RecyclerView rvChatList;
    EditText etSearch;
    NewChatListAdapter newChatListAdapter;
    ArrayList<ChatDataConvertModel> chatDataConvertModelArrayList;
    INewChatListAdapter iNewChatListAdapter;
    ProgressDialog progressDialog;
    String userName = "", userEmail = "", userMemberId = "";
    IApiListener iApiListener;

    public FragmentNewChatList() {
        // Required empty public constructor
    }

    public static FragmentNewChatList newInstance(String param1, String param2) {
        FragmentNewChatList fragment = new FragmentNewChatList();
        Bundle args = new Bundle();
        /*args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.getInstance().setFragmentNewChatList(this);
        if (getArguments() != null) {
            //
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_chat_list, container, false);
        iNewChatListAdapter = this;
        iApiListener = this;
        chatDataConvertModelArrayList = new ArrayList<>();
        userName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userName, "");
        userEmail = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE,""), "");
        userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
        if (userMemberId.isEmpty()) {
            activity().finish();
        }
        initializeViews(root);
        getOnlineOfflineUser();
        return root;
    }

    private void initializeViews(View root) {
        coordinator_layout = root.findViewById(R.id.coordinator_layout);
        rvChatList = root.findViewById(R.id.rvChatList);
        etSearch = root.findViewById(R.id.etSearch);
        //
        /*root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    Intent returnData = new Intent(activity(), HelperActivity.class);
                    returnData.putParcelableArrayListExtra("ReturnData", recentChatDataConvertModelList);
                    activity().setResult(Activity.RESULT_OK, returnData);
                    return true;
                }
                return false;
            }
        });*/
        //
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.toString().trim().length() > 0) {
                    new getSearchFilterData().execute(editable.toString().trim());
                } else {
                    setChatListAdapter(chatDataConvertModelArrayList);
                }
            }
        });
        rvChatList.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
    }

    private void getOnlineOfflineUser() {
        newChatListAdapter = new NewChatListAdapter(null, activity(), iNewChatListAdapter, RController.LOADING);
        rvChatList.setAdapter(newChatListAdapter);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getOnlineOfflineUser(Constants.ChatConstants.getOnlineOfflineUser + userMemberId + "/");
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.ChatConstants.getOnlineOfflineUser,
                false, iApiListener, false));
    }

    private void setChatListAdapter(ArrayList<ChatDataConvertModel> newChatDataModelList) {
        try {
            if (newChatDataModelList.size() > 0) {
                newChatListAdapter = new NewChatListAdapter(newChatDataModelList, activity(), iNewChatListAdapter, RController.LOADED);
                rvChatList.setAdapter(newChatListAdapter);
            } else {
//                rvChatList.setAdapter(new EmptyDataAdapter(activity(), Constants.NO_DATA_AVAILABLE, R.drawable.ic_nodata, 2));
                //sisss

                rvChatList.setAdapter(new EmptyDataAdapter(activity(), Constants.SORRY, Constants.THERE_IS_NO_DATA,
                        R.drawable.ic_no_results_to_show, 6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openSingleChatWindow(ChatDataConvertModel chatDataConvertModel, Integer position) {
        Common.getInstance().openSingleChatActivity(activity(), activity(), chatDataConvertModel);
        //
        activity().finish();
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
            ArrayList<ChatDataConvertModel> filterList = (ArrayList<ChatDataConvertModel>)
                    (ArrayList<?>) Common.getInstance().getFilteredListOfSearchObject(chatDataConvertModelArrayList, searchString, 1);
            return filterList;
        }

        @Override
        protected void onPostExecute(ArrayList<ChatDataConvertModel> result) {
            super.onPostExecute(result);
            setChatListAdapter(result);
        }
    }

    public void updateListAdapter() {
        new updateListAdapter().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class updateListAdapter extends AsyncTask<String, Void, ArrayList<ChatDataConvertModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ChatDataConvertModel> doInBackground(String... params) {
            ArrayList<ChatDataConvertModel> filterList = new ArrayList<>();
            try {
                ArrayList<ChatDataConvertModel> fanUnFanChatDataConvertModelList = Common.getInstance().getFanUnFanChatDataConvertModelList();
                if (fanUnFanChatDataConvertModelList != null) {
                    for (int i = 0; i < fanUnFanChatDataConvertModelList.size(); i++) {
                        try {
                            int index = -1;
                            for (int j = 0; j < chatDataConvertModelArrayList.size(); j++) {
                                if (fanUnFanChatDataConvertModelList.get(i)._id.equalsIgnoreCase(chatDataConvertModelArrayList.get(j)._id)) {
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
            return filterList;
        }

        @Override
        protected void onPostExecute(ArrayList<ChatDataConvertModel> result) {
            super.onPostExecute(result);
            if (newChatListAdapter != null) {
                newChatListAdapter.notifyDataSetChanged();
            } else {
                setChatListAdapter(chatDataConvertModelArrayList);
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

    @Override
    public void onResume() {
        super.onResume();
        updateListAdapter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Common.getInstance().setFragmentNewChatList(null);
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.ChatConstants.getOnlineOfflineUser)) {
            try {
                Type type = new TypeToken<ArrayList<ChatDataConvertModel>>() {
                }.getType();
                chatDataConvertModelArrayList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                setChatListAdapter(chatDataConvertModelArrayList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.ChatConstants.getOnlineOfflineUser)) {
            rvChatList.setAdapter(new EmptyDataAdapter(activity(),
                    enumConstants == EnumConstants.NO_NETWORK ? Constants.NO_INTERNET : Constants.SOMETHING_WENT_WRONG,
                    R.drawable.ic_network, 2));
        }
    }
}
