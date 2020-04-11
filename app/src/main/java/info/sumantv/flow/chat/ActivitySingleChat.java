package info.sumantv.flow.chat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.ModelClass.ProfileDataModel;
import info.sumantv.flow.bottommenu.interfaces.activity.IActivity;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.chat.adapters.SingleChatAdapter;
import info.sumantv.flow.chat.interfaces.ISingleChatAdapter;
import info.sumantv.flow.chat.models.ChatDataConvertModel;
import info.sumantv.flow.chat.models.SingleChatHistoryModel;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Utility;
import info.sumantv.flow.utils.internetchecker.InternetSpeedChecker;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class ActivitySingleChat extends AppCompatActivity implements IActivity, ISingleChatAdapter, IApiListener {
    private CoordinatorLayout coordinator_layout;
    private RecyclerView rvChatList;
    private EditText etMessage;
    private ImageButton ibSend, ibAudioCall, ibVideoCall;
    LinearLayout llImageTitle;
    CircleImageView ivProfileImage;
    TextView tvTitle, tvOnlineStatus, tvTypingStatus, tvAvailableBalance, tvDateSection, tvNewMsgCount, tvMsgCharsCount;
    RelativeLayout rlNewMsgCount;
    androidx.appcompat.widget.Toolbar toolbar;
    SingleChatAdapter singleChatAdapter;
    LinearLayoutManager rvLinearLayoutManager;
    ArrayList<SingleChatHistoryModel> singleChatHistoryModelArrayList;
    ISingleChatAdapter iSingleChatListAdapter;
    ProgressDialog progressDialog;
    ChatDataConvertModel chatDataConvertModelOrg;
    String userName = "", userEmail = "", userMemberId = "", userFirstName = "", userLastName = "", userProfilePic = "";
    private String joinEmit = "join", leaveEmit = "leave", onlineUsersEmit = "onlineUsers", checkMemberOnlineEmit = "checkMemberOnline", typingEmit = "typing", messageEmit = "message", changeMessageStatusEmit = "changeMessageStatus";
    Boolean isChatScrolledToLast = false, canShowDateTextView = false, isActivityVisible = false;
    Handler handlerTyping = new Handler(Looper.getMainLooper()), handlerDateSection = new Handler(Looper.getMainLooper());
    Integer charactersLength = 160, animationDuration = 500, newMsgCount = 0;
    String isDataScrollLast = "", conditionLoadMore = "";
    int lastVisibleItemOfRecycler = -1, loadMoreCount = 50;
    IApiListener iApiListener;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_chat);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(activity(), R.color.status_bar));// set status background whit
        iApiListener = this;
        isChatScrolledToLast = false;
        canShowDateTextView = false;
        newMsgCount = 0;
        isDataScrollLast = "";
        lastVisibleItemOfRecycler = -1;
        singleChatHistoryModelArrayList = new ArrayList<>();
        userName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userName, "");
        userEmail = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, ""), "");
        userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
        userFirstName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.firstName, "");
        userLastName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.lastName, "");
        userProfilePic = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.profilePic, "");
        if (userMemberId.isEmpty()) {
            finish();
        }
        try {
            if (getIntent().getExtras() != null) {
                chatDataConvertModelOrg = getIntent().getExtras().getParcelable("ChatDataConvertModel");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        iSingleChatListAdapter = this;
        singleChatHistoryModelArrayList = new ArrayList<>();
        if (chatDataConvertModelOrg == null || chatDataConvertModelOrg._id == null || chatDataConvertModelOrg._id.isEmpty()) {
            Common.toastMessage(activity(), "Data not found");
            finish();
            return;
        }
        if (chatDataConvertModelOrg._id.equalsIgnoreCase(userMemberId)) {
            Common.toastMessage(activity(), "Sorry, you can't do self chat.");
            finish();
        } else {
            ChatDataConvertModel chatDataConvertModel = chatDataConvertModelOrg;
            chatDataConvertModel.counter = 0;
            Common.getInstance().addRecentChatDataConvertModel(chatDataConvertModel, false);
        }
        initializeViews();
        buttons();
        getChatList("firstTimeLoading");
        new NotificationUtil().dismissNotificationsByID(activity(), Common.getInstance().getUniqueIDFromMemberID(chatDataConvertModelOrg._id));
    }

    @SuppressLint("SetTextI18n")
    private void initializeViews() {
        coordinator_layout = findViewById(R.id.coordinator_layout);
        rvChatList = findViewById(R.id.rvChatList);
        etMessage = findViewById(R.id.etMessage);
        llImageTitle = findViewById(R.id.llImageTitle);
        ibSend = findViewById(R.id.ibSend);
        ibAudioCall = findViewById(R.id.ibAudioCall);
        ibVideoCall = findViewById(R.id.ibVideoCall);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvTitle = findViewById(R.id.tvTitle);
        tvOnlineStatus = findViewById(R.id.tvOnlineStatus);
        tvTypingStatus = findViewById(R.id.tvTypingStatus);
        tvAvailableBalance = findViewById(R.id.tvAvailableBalance);
        tvDateSection = findViewById(R.id.tvDateSection);
        tvNewMsgCount = findViewById(R.id.tvNewMsgCount);
        rlNewMsgCount = findViewById(R.id.rlNewMsgCount);
        tvMsgCharsCount = findViewById(R.id.tvMsgCharsCount);
        toolbar = findViewById(R.id.toolbar);
        //
        tvDateSection.setVisibility(View.GONE);
        tvMsgCharsCount.setVisibility(View.GONE);
        ibSend.setVisibility(View.GONE);
        rlNewMsgCount.setVisibility(View.GONE);
        etMessage.requestFocus();
        etMessage.setCursorVisible(true);
        //
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_header_back);
        //
        rvLinearLayoutManager = new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false);
        rvChatList.setLayoutManager(rvLinearLayoutManager);
        tvTitle.setText(chatDataConvertModelOrg.firstName + " " + Common.getInstance().IsNullReturnValue(chatDataConvertModelOrg.lastName, ""));
        if (!Common.getInstance().IsNull(chatDataConvertModelOrg.avtar_imgPath)) {
            String imagePath = chatDataConvertModelOrg.avtar_imgPath;
            if (!imagePath.contains(ApiClient.BASE_URL)) {
                imagePath = ApiClient.BASE_URL + chatDataConvertModelOrg.avtar_imgPath;
            }
            Common.getInstance().setGlideImage(activity(), imagePath, ivProfileImage);
        }
        if (chatDataConvertModelOrg.isCeleb == null || !chatDataConvertModelOrg.isCeleb) {
            ibAudioCall.setVisibility(View.GONE);
            ibVideoCall.setVisibility(View.GONE);
        }
        if (isCallRunning()) {
            enableDisableCallIcons(false);
        }
    }

    private void buttons() {
        rvChatList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    Integer firstVisiblePosition = rvLinearLayoutManager.findFirstVisibleItemPosition();
                    SingleChatHistoryModel singleChatHistoryModel = singleChatAdapter.getSingleChatHistoryModelAtPosition(firstVisiblePosition);
                    tvDateSection.setText(Common.getInstance().getDateSection(singleChatHistoryModel.createdAt));
                    //tvDateSection.setVisibility(View.VISIBLE);
                    if (tvDateSection.getVisibility() == View.GONE && canShowDateTextView) {
                        SlideToDownAnimation(tvDateSection, animationDuration);
                    }
                    handlerDateSection.removeCallbacks(runnableDateSection);
                    handlerDateSection.postDelayed(runnableDateSection, 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lastVisibleItemOfRecycler = ((LinearLayoutManager) rvChatList.getLayoutManager()).findFirstVisibleItemPosition();
                if (!recyclerView.canScrollVertically(1)) {
                    //Common.toastMessage(activity(),"end down");
                    isChatScrolledToLast = true;
                    newMsgCount = 0;
                    setNewMsgCount(newMsgCount);
                } else if (!recyclerView.canScrollVertically(-1)) {
                    //Common.toastMessage(activity(),"end up");
                    isChatScrolledToLast = false;
                    if (isDataScrollLast.isEmpty()) {
                        getChatList("loadMore");
                    }
                } else {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Purpose of handler to use here *if u don't use handler then while keyboard visible recyclerView wil not scroll to bottom
                        }
                    }, 500);
                    //
                    isChatScrolledToLast = false;
                }
            }
        });
        rvChatList.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    //write code here while keyboard visible
                    try {
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Purpose of handler to use here *if u don't use handler then recyclerView wil not scroll to bottom in this situation
                                if (isChatScrolledToLast) {
                                }
                                rvChatList.scrollToPosition(singleChatHistoryModelArrayList.size() - 1);
                            }
                        }, 200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        rlNewMsgCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (singleChatAdapter != null) {
                        rvChatList.scrollToPosition((singleChatHistoryModelArrayList.size() - 1));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        llImageTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ProfileDataModel profileDataModel = Common.getInstance().convertToViewProfile(chatDataConvertModelOrg);
                    Common.getInstance().navigatingToProfile(activity(), profileDataModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (editable != null && !editable.toString().trim().isEmpty()) {
                        ibSend.setVisibility(View.VISIBLE);
                        tvMsgCharsCount.setVisibility(View.VISIBLE);
                        Integer etMsgLength = editable.toString().length();
                        Integer noOfMsgCount = Common.getInstance().roundToNearestInt((double) etMsgLength / (double) charactersLength);
                        Integer remainingCount = charactersLength;
                        if (etMsgLength > charactersLength) {
                            remainingCount = charactersLength - (etMsgLength - (charactersLength * (noOfMsgCount - 1)));
                        } else {
                            remainingCount = charactersLength - etMsgLength;
                        }
                        tvMsgCharsCount.setText("( " + remainingCount + "/" + noOfMsgCount + " )");
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("chatRoomID", Common.getInstance().getChatRoomID(activity(), chatDataConvertModelOrg._id));
                            jsonObject.put("receiverId", chatDataConvertModelOrg._id);
                            Common.getInstance().getSocket().emit(typingEmit, jsonObject);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        ibSend.setVisibility(View.GONE);
                        tvMsgCharsCount.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ibSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    try {
                        String message = etMessage.getText().toString().trim();
                        if (message.isEmpty()) {
                            ibSend.setVisibility(View.GONE);
                            return;
                        } else if (!Utility.isNetworkAvailable(activity())) {
                            showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
                            return;
                        } else if (!InternetSpeedChecker.highNetworkState(activity())) {
                            showSnackBar(Constants.BAD_NETWORK, 2);
                            return;
                        }
                        Integer noOfMsgCount = Common.getInstance().roundToNearestInt((double) message.length() / (double) charactersLength);
                        //Common.toastMessage(activity(),""+result);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("chatRoomID", Common.getInstance().getChatRoomID(activity(), chatDataConvertModelOrg._id));
                        jsonObject.put("message", message);
                        jsonObject.put("senderId", userMemberId);
                        jsonObject.put("receiverId", chatDataConvertModelOrg._id);
                        jsonObject.put("isCeleb", Common.isCelebStatus(activity()));
                        jsonObject.put("os", "android");
                        jsonObject.put("numberOfMessages", noOfMsgCount);
                        jsonObject.put("senderFirstName", userFirstName);
                        jsonObject.put("senderLastName", userLastName);
                        jsonObject.put("senderAvatar", userProfilePic);
                        //jsonObject.put("credits", 1);
                        Common.getInstance().getSocket().emit(messageEmit, jsonObject);
                        //
                        etMessage.setText("");
                        if (singleChatAdapter != null) {
                            rvChatList.scrollToPosition((singleChatHistoryModelArrayList.size() - 1));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
    }

    private Boolean isCallRunning() {
        return false;
    }

    public void enableDisableCallIcons(Boolean enable) {
        if (enable) {
//            ibAudioCall.setColorFilter(ContextCompat.getColor(activity(), R.color.skyblueNew), android.graphics.PorterDuff.Mode.SRC_IN);
//            ibVideoCall.setColorFilter(ContextCompat.getColor(activity(), R.color.skyblueNew), android.graphics.PorterDuff.Mode.SRC_IN);
            ibAudioCall.setColorFilter(ContextCompat.getColor(activity(), R.color.audio_icon_color), android.graphics.PorterDuff.Mode.SRC_IN);
            ibVideoCall.setColorFilter(ContextCompat.getColor(activity(), R.color.video_icon_color), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
//            ibAudioCall.setColorFilter(ContextCompat.getColor(activity(), R.color.audio_icon_color), android.graphics.PorterDuff.Mode.SRC_IN);
//            ibVideoCall.setColorFilter(ContextCompat.getColor(activity(), R.color.video_icon_color), android.graphics.PorterDuff.Mode.SRC_IN);

            ibAudioCall.setColorFilter(ContextCompat.getColor(activity(), R.color.audio_icon_color_150), android.graphics.PorterDuff.Mode.SRC_IN);
            ibVideoCall.setColorFilter(ContextCompat.getColor(activity(), R.color.video_icon_color_150), android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }

    public String getIsDataScrollLast() {
        return isDataScrollLast;
    }

    public void updateIsFanStatus(Boolean isFan) {
        chatDataConvertModelOrg.isFan = isFan;
    }

    private void setSingleChatAdapter(ArrayList<SingleChatHistoryModel> modelArrayList) {
        try {
            if (modelArrayList != null && modelArrayList.size() > 0) {
                //rvChatList.removeAllViews();
                //
                singleChatAdapter = new SingleChatAdapter(modelArrayList, activity(), iSingleChatListAdapter);
                rvChatList.setAdapter(singleChatAdapter);
                rvChatList.invalidate();
                if (isChatScrolledToLast) {
                    rvChatList.scrollToPosition((modelArrayList.size() - 1));
                } else if (lastVisibleItemOfRecycler > 0) {
                    rvChatList.scrollToPosition(lastVisibleItemOfRecycler);
                }
                /*if(singleChatAdapter == null){
                    singleChatAdapter = new SingleChatAdapter(modelArrayList,activity(),iSingleChatListAdapter);
                    rvChatList.setAdapter(singleChatAdapter);
                    rvChatList.scrollToPosition((modelArrayList.size()-1));
                } else {
                    singleChatAdapter.assignLoadMoreData(modelArrayList);
                    if(isChatScrolledToLast){
                        rvChatList.scrollToPosition((modelArrayList.size()-1));
                    } else {
                        rvChatList.scrollToPosition(lastVisibleItemOfRecycler);
                    }
                }*/
            } else {
                //rvChatList.setAdapter(new EmptyDataAdapter(activity(), Constants.NO_DATA_AVAILABLE, R.drawable.ic_nodata, 2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getChatList(final String condition) {
        conditionLoadMore = condition;
        String url;
        if (singleChatHistoryModelArrayList != null && singleChatHistoryModelArrayList.size() > 0) {
            SingleChatHistoryModel singleChatHistoryModel = singleChatHistoryModelArrayList.get(0);
            url = Constants.ChatConstants.getAllChatMessages + userMemberId + "/" + chatDataConvertModelOrg._id + "/" + singleChatHistoryModel.createdAt + "/" + loadMoreCount + "/";
        } else {
            url = Constants.ChatConstants.getAllChatMessages + userMemberId + "/" + chatDataConvertModelOrg._id + "/" + "0" + "/" + loadMoreCount + "/";
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getAllChatMessages(url);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.ChatConstants.getAllChatMessages, true, iApiListener, false));
    }

    @SuppressLint("StaticFieldLeak")
    private class asyncTaskLoadMoreData extends AsyncTask<String, Void, ArrayList<SingleChatHistoryModel>> {
        JSONObject jsonObject;
        String condition;

        public asyncTaskLoadMoreData(JSONObject jsonObject, String condition) {
            this.jsonObject = jsonObject;
            this.condition = condition;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<SingleChatHistoryModel> doInBackground(String... params) {
            ArrayList<SingleChatHistoryModel> filterList = new ArrayList<>();
            try {
                if (condition == null) {
                    condition = "";
                }
                try {
                    Type type = new TypeToken<ArrayList<SingleChatHistoryModel>>() {
                    }.getType();
                    ArrayList<SingleChatHistoryModel> arrayList = new Gson().fromJson(jsonObject.optString("listOfChatMessageObj"), type);
                    isDataScrollLast = "";
                    if (arrayList != null && arrayList.size() < loadMoreCount) {
                        isDataScrollLast = "Yes";
                    }
                    if (singleChatHistoryModelArrayList == null) {
                        singleChatHistoryModelArrayList = new ArrayList<>();
                    }
                    ArrayList<SingleChatHistoryModel> existedArrayList = singleChatHistoryModelArrayList;
                    if (arrayList != null && arrayList.size() > 0) {
                        filterList.addAll(arrayList);
                    }
                    filterList.addAll(existedArrayList);
                    lastVisibleItemOfRecycler = -1;
                    if (existedArrayList.size() > 0 && condition.equalsIgnoreCase("loadMore")) {
                        lastVisibleItemOfRecycler = filterList.size() - existedArrayList.size();
                    } else if (condition.equalsIgnoreCase("firstTimeLoading")) {
                        lastVisibleItemOfRecycler = filterList.size() - 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return filterList;
        }

        @Override
        protected void onPostExecute(ArrayList<SingleChatHistoryModel> result) {
            super.onPostExecute(result);
            try {
                Double celebCharge = 0.0;
                try {
                    celebCharge = jsonObject.optJSONObject("celebCharge").optDouble("serviceCredits", 0.0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (celebCharge > 0 && !Common.isCelebStatus(activity())) {
                    etMessage.setHint(Common.getInstance().roundTwoDecimalPlaces(celebCharge) + " credits per message, 160 char per message");
                }
                //
                updateCredits(jsonObject, null);
                singleChatHistoryModelArrayList = result;
                setSingleChatAdapter(singleChatHistoryModelArrayList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setNewMsgCount(Integer newMsgCount) {
        if (newMsgCount > 0) {
            tvNewMsgCount.setText(String.valueOf(newMsgCount));
            rlNewMsgCount.setVisibility(View.VISIBLE);
        } else {
            tvNewMsgCount.setText("0");
            rlNewMsgCount.setVisibility(View.GONE);
        }
    }

    private void setOnlineTextView(Boolean isOnline) {
        if (isOnline) {
            tvOnlineStatus.setText("online");
            tvOnlineStatus.setVisibility(View.VISIBLE);
        } else {
            tvOnlineStatus.setText("");
            tvOnlineStatus.setVisibility(View.GONE);
        }
    }

    public void updateOnlineStatus(JSONObject jsonObject) {
        try {
            if (jsonObject != null && jsonObject.length() > 0) {
                updateIsFanStatus(jsonObject.optBoolean("isFan", false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //setOnlineTextView();
    }

    public void updateOnlineStatus(JSONArray jsonArray) {
        Boolean isOnline = false;
        try {
            if (jsonArray != null && jsonArray.length() > 0) {
                String senderId = chatDataConvertModelOrg._id;
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.optJSONObject(i);
                        if (senderId.equalsIgnoreCase(jsonObject.optString("userId", "")) && jsonObject.optBoolean("liveLogStatus", true)) {
                            isOnline = true;
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setOnlineTextView(isOnline);
    }

    public void updateChatMessages(JSONObject jsonObject, SingleChatHistoryModel singleChatHistoryModel) {
        try {
            if (jsonObject.length() > 0 && Common.getInstance().checkResponseStatus(jsonObject.optJSONObject("data")) && singleChatHistoryModel != null && singleChatHistoryModel.message != null) {
                updateCredits(jsonObject, "singleMessage");
                if (singleChatHistoryModelArrayList == null) {
                    singleChatHistoryModelArrayList = new ArrayList<>();
                }
                singleChatHistoryModelArrayList.add(singleChatHistoryModel);
                //
                setSingleChatAdapter(singleChatHistoryModelArrayList);
                if (!userMemberId.equalsIgnoreCase(singleChatHistoryModel.senderId)) {
                    if (isChatScrolledToLast) {
                        newMsgCount = 0;
                    } else {
                        newMsgCount = newMsgCount + 1;
                    }
                    setNewMsgCount(newMsgCount);
                } else {
                    /*if(singleChatAdapter != null){
                        rvChatList.smoothScrollToPosition((singleChatHistoryModelArrayList.size()-1));
                    }*/
                }
                //
                ChatDataConvertModel chatDataConvertModel = Common.getInstance().convertToChatDataConvertModel(singleChatHistoryModel, chatDataConvertModelOrg, userMemberId);
                if (chatDataConvertModel != null) {
                    Common.getInstance().addRecentChatDataConvertModel(chatDataConvertModel, true);
                }
            } else if (singleChatHistoryModel != null && singleChatHistoryModel.senderId.equalsIgnoreCase(userMemberId)) {
                showSnackBar(jsonObject.optJSONObject("data").optString("message"), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showSnackBar(e.getMessage(), 2);
        }
    }

    public void updateCredits(JSONObject jsonObjectFinal, String isWhich) {
        try {
            JSONObject jsonObject;
            if (isWhich != null && isWhich.equalsIgnoreCase("singleMessage")) {
                jsonObject = jsonObjectFinal.optJSONObject("data");
            } else {
                jsonObject = jsonObjectFinal;
            }
            Double cumulativeCreditValue = 0.0;
            if (userMemberId.equalsIgnoreCase(jsonObject.optJSONObject("senderUpdatedCredits").optString("memberId", ""))) {
                cumulativeCreditValue = jsonObject.optJSONObject("senderUpdatedCredits").optDouble("cumulativeCreditValue", 0.0);
            } else if (userMemberId.equalsIgnoreCase(jsonObject.optJSONObject("receiverUpdatedCredits").optString("memberId", ""))) {
                cumulativeCreditValue = jsonObject.optJSONObject("receiverUpdatedCredits").optDouble("cumulativeCreditValue", 0.0);
            }
            Common.getInstance().setAvailableBalance(cumulativeCreditValue);
            tvAvailableBalance.setText(Common.getInstance().getCreditBalancetoShowInLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTyping(JSONObject jsonObject) {
        if (userMemberId.equalsIgnoreCase(jsonObject.optString("receiverId", ""))) {
            tvOnlineStatus.setVisibility(View.GONE);
            tvTypingStatus.setVisibility(View.VISIBLE);
            handlerTyping.removeCallbacks(runnableTyping);
            handlerTyping.postDelayed(runnableTyping, 500);
        }
    }

    public String getOtherPersonReceiverID() {
        try {
            return chatDataConvertModelOrg._id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public Boolean getIsActivityVisible() {
        return isActivityVisible;
    }

    final Runnable runnableTyping = new Runnable() {
        public void run() {
            tvOnlineStatus.setVisibility(View.VISIBLE);
            tvTypingStatus.setVisibility(View.GONE);
        }
    };
    final Runnable runnableDateSection = new Runnable() {
        public void run() {
            if (tvDateSection.getVisibility() == View.VISIBLE) {
                SlideToAboveAnimation(tvDateSection, animationDuration);
            }
        }
    };

    public void SlideToDownAnimation(final View view, Integer animationDuration) {
        try {
            Animation slide = null;
            slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                    -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            slide.setDuration(animationDuration);
            slide.setFillAfter(true);
            slide.setFillEnabled(true);
            view.startAnimation(slide);
            slide.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.clearAnimation();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SlideToAboveAnimation(final View view, Integer animationDuration) {
        try {
            Animation slide = null;
            slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                    0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
            slide.setDuration(animationDuration);
            slide.setFillAfter(true);
            slide.setFillEnabled(true);
            view.startAnimation(slide);
            slide.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.clearAnimation();
                    view.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Common.getInstance().setActivitySingleChat(null);
        isActivityVisible = false;
        if (!Common.getInstance().isSocketConnected()) {
            Common.getInstance().connectSocket();
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", userName);
            jsonObject.put("email", userEmail);
            jsonObject.put("uuid", userMemberId);
            jsonObject.put("chatRoomID", Common.getInstance().getChatRoomID(activity(), chatDataConvertModelOrg._id));
            Common.getInstance().getSocket().emit(leaveEmit, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("chatRoomID", Common.getInstance().getChatRoomID(activity(), chatDataConvertModelOrg._id));
            jsonObject.put("receiverId", userMemberId);
            Common.getInstance().getSocket().emit(changeMessageStatusEmit, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Common.getInstance().setActivitySingleChat(this);
        isActivityVisible = true;
        updateCanShowDateTextView();
        if (!Common.getInstance().isSocketConnected()) {
            Common.getInstance().connectSocket();
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", userName);
            jsonObject.put("email", userEmail);
            jsonObject.put("uuid", userMemberId);
            jsonObject.put("chatRoomID", Common.getInstance().getChatRoomID(activity(), chatDataConvertModelOrg._id));
            Common.getInstance().getSocket().emit(joinEmit, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("memberId", userMemberId);
            jsonObject.put("receiverId", chatDataConvertModelOrg._id);
            Common.getInstance().getSocket().emit(checkMemberOnlineEmit, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject();
            Common.getInstance().getSocket().emit(onlineUsersEmit, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("chatRoomID", Common.getInstance().getChatRoomID(activity(), chatDataConvertModelOrg._id));
            jsonObject.put("receiverId", userMemberId);
            Common.getInstance().getSocket().emit(changeMessageStatusEmit, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCanShowDateTextView() {
        canShowDateTextView = false;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                canShowDateTextView = true;
            }
        }, 500);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
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
        return this;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Common.getInstance().setActivitySingleChat(null);
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.ChatConstants.getAllChatMessages)) {
            try {
                /*Type type = new TypeToken<ArrayList<ChatDataModel>>(){}.getType();
                ArrayList<ChatDataModel> chatDataModelList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data),type);*/
                new asyncTaskLoadMoreData(new JSONObject(new Gson().toJson(apiResponseModel.data)), conditionLoadMore).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.ChatConstants.getAllChatMessages)) {
            //rvChatList.setAdapter(new EmptyDataAdapter(activity(), enumConstants == EnumConstants.NO_NETWORK ? Constants.NO_INTERNET:Constants.SOMETHING_WENT_WRONG, R.drawable.ic_network, 2));
            if (conditionLoadMore.equalsIgnoreCase("firstTimeLoading")) {
                finish();
            }
        }
    }
}
