package info.sumantv.flow.chat.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataAdapter;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.chat.adapters.CallDetailsAdapter;
import info.sumantv.flow.chat.interfaces.ICallDetailsAdapter;
import info.sumantv.flow.chat.models.AllCallsHistoryModel;
import info.sumantv.flow.chat.models.ChatDataConvertModel;
import info.sumantv.flow.chat.models.ChatSenderReceiverInfo;
import info.sumantv.flow.chat.models.RecentCallsModel;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Utility;

import java.util.ArrayList;

public class FragmentCallDetails extends Fragment implements IFragment, ICallDetailsAdapter {
    private CoordinatorLayout coordinator_layout;
    RecyclerView recyclerView;
    CircleImageView ivProfileImage;
    TextView tvUserName, tvDate;
    ImageButton ibAudioCall, ibVideoCall, ibChat;
    CallDetailsAdapter callDetailsAdapter;
    RecentCallsModel recentCallsModel;
    ICallDetailsAdapter iCallDetailsAdapter;
    ProgressDialog progressDialog;
    String userName = "", userEmail = "", userMemberId = "";

    public FragmentCallDetails() {
        // Required empty public constructor
    }

    public static FragmentCallDetails newInstance(RecentCallsModel recentCallsModel, String param2) {
        FragmentCallDetails fragment = new FragmentCallDetails();
        Bundle args = new Bundle();
        args.putParcelable("recentCallsModel", recentCallsModel);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                recentCallsModel = getArguments().getParcelable("recentCallsModel");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_call_details, container, false);
        iCallDetailsAdapter = this;
        userName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userName, "");
        userEmail = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE,""), "");
        userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
        if (userMemberId.isEmpty()) {
            activity().finish();
        }
        initializeViews(root);
        callDetailsAdapter = new CallDetailsAdapter(recentCallsModel.allCallsHistory, activity(), iCallDetailsAdapter, RController.LOADING);
        recyclerView.setAdapter(callDetailsAdapter);
        //
        setListAdapter(recentCallsModel.allCallsHistory);
        return root;
    }

    @SuppressLint("SetTextI18n")
    private void initializeViews(View root) {
        coordinator_layout = root.findViewById(R.id.coordinator_layout);
        recyclerView = root.findViewById(R.id.recyclerView);
        ivProfileImage = root.findViewById(R.id.ivProfileImage);
        tvUserName = root.findViewById(R.id.tvUserName);
        tvDate = root.findViewById(R.id.tvDate);
        ibAudioCall = root.findViewById(R.id.ibAudioCall);
        ibVideoCall = root.findViewById(R.id.ibVideoCall);
        ibChat = root.findViewById(R.id.ibChat);
        //
        recyclerView.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        //
        ChatSenderReceiverInfo chatSenderReceiverInfo;
        if (userMemberId.equalsIgnoreCase(recentCallsModel.lastCallStatus.receiverId._id)) {
            chatSenderReceiverInfo = recentCallsModel.lastCallStatus.senderId;
        } else {
            chatSenderReceiverInfo = recentCallsModel.lastCallStatus.receiverId;
        }
        chatSenderReceiverInfo.isFan = recentCallsModel._id.isFan;
        if (!Common.getInstance().IsNull(chatSenderReceiverInfo.firstName)) {
            tvUserName.setText(Common.convertCaseSensitive(
                    chatSenderReceiverInfo.firstName) + " " + chatSenderReceiverInfo.lastName);
            //
        }
        if (!Common.getInstance().IsNull(chatSenderReceiverInfo.avtar_imgPath)) {
            String imagePath = chatSenderReceiverInfo.avtar_imgPath;
            if (!imagePath.contains(ApiClient.BASE_URL)) {
                imagePath = ApiClient.BASE_URL + chatSenderReceiverInfo.avtar_imgPath;
            }
            Common.getInstance().setGlideImage(activity(), imagePath, ivProfileImage);
        }
        if (chatSenderReceiverInfo.isCeleb != null && chatSenderReceiverInfo.isCeleb) {
//            ibAudioCall.setColorFilter(ContextCompat.getColor(activity(), R.color.skyblueNew), android.graphics.PorterDuff.Mode.SRC_IN);
//            ibVideoCall.setColorFilter(ContextCompat.getColor(activity(), R.color.skyblueNew), android.graphics.PorterDuff.Mode.SRC_IN);
            //ibChat.setColorFilter(ContextCompat.getColor(activity(), R.color.skyblueNew), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            ibAudioCall.setColorFilter(ContextCompat.getColor(activity(), R.color.color_ddd), android.graphics.PorterDuff.Mode.SRC_IN);
            ibVideoCall.setColorFilter(ContextCompat.getColor(activity(), R.color.color_ddd), android.graphics.PorterDuff.Mode.SRC_IN);
            //ibChat.setColorFilter(ContextCompat.getColor(activity(), R.color.color_ddd), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        ibAudioCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chatSenderReceiverInfo.isCeleb != null && chatSenderReceiverInfo.isCeleb) {
//                    initiateCall(Constants.AUDIO_CALL, chatSenderReceiverInfo);

                }
            }
        });
        ibVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chatSenderReceiverInfo.isCeleb != null && chatSenderReceiverInfo.isCeleb) {
//                    initiateCall(Constants.VIDEO_CALL, chatSenderReceiverInfo);

                }
            }
        });
        ibChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatDataConvertModel chatDataConvertModel = Common.getInstance().convertToChatDataConvertModel(recentCallsModel, userMemberId);
                Common.getInstance().openSingleChatActivity(activity(), activity(), chatDataConvertModel);
            }
        });
    }

    private void setListAdapter(ArrayList<AllCallsHistoryModel> allCallsHistoryModelArrayList) {
        try {
            if (allCallsHistoryModelArrayList != null && allCallsHistoryModelArrayList.size() > 0) {
                callDetailsAdapter = new CallDetailsAdapter(allCallsHistoryModelArrayList, activity(), iCallDetailsAdapter, RController.LOADED);
                recyclerView.setAdapter(callDetailsAdapter);
                //
                tvDate.setText(Common.getInstance().getDateANDTime(recentCallsModel.lastCallStatus.createdAt));
                tvDate.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setAdapter(new EmptyDataAdapter(activity(), Constants.NO_DATA_AVAILABLE, R.drawable.ic_nodata, 2));
                tvDate.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
}
