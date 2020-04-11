package info.sumantv.flow.chat.adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.viewholders.SkeletonViewHolder;

import info.sumantv.flow.R;
import info.sumantv.flow.chat.interfaces.ICallsListAdapter;
import info.sumantv.flow.chat.models.ChatSenderReceiverInfo;
import info.sumantv.flow.chat.models.RecentCallsModel;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;

import java.util.ArrayList;

public class CallsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<RecentCallsModel> recentCallsModelArrayList;
    private Context context;
    private ICallsListAdapter iCallsListAdapter;
    String userName = "", userEmail = "", userMemberId = "";
    private RController rController;

    public CallsListAdapter(ArrayList<RecentCallsModel> recentCallsModelArrayList, Context context,
                            ICallsListAdapter iCallsListAdapter, RController rController) {
        this.rController = rController;
        this.recentCallsModelArrayList = recentCallsModelArrayList;
        this.context = context;
        this.iCallsListAdapter = iCallsListAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (rController == RController.LOADING) {
            int layout = R.layout.skeleton_chat;
            return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        } else {
            int layout = R.layout.item_calls_list;
            return new CallsViewAdapter(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        userName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userName, "");
        userEmail = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, ""), "");
        userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
        if (userMemberId.isEmpty()) {
            return;
        }
        if (holder instanceof CallsViewAdapter) {
            CallsViewAdapter viewHolder = (CallsViewAdapter) holder;
            RecentCallsModel recentCallsModel = recentCallsModelArrayList.get(position);
            ChatSenderReceiverInfo chatSenderReceiverInfo;
            if (userMemberId.equalsIgnoreCase(recentCallsModel.lastCallStatus.receiverId._id)) {
                chatSenderReceiverInfo = recentCallsModel.lastCallStatus.senderId;
            } else {
                chatSenderReceiverInfo = recentCallsModel.lastCallStatus.receiverId;
            }
            chatSenderReceiverInfo.isFan = recentCallsModel._id.isFan;
            //
            if (!Common.getInstance().IsNull(chatSenderReceiverInfo.firstName)) {
//                viewHolder.tvUserName.setText(chatSenderReceiverInfo.firstName + " " + chatSenderReceiverInfo.lastName);

                viewHolder.tvUserName.setText(Common.convertCaseSensitive(
                        chatSenderReceiverInfo.firstName) + " " + chatSenderReceiverInfo.lastName);

            }
            if (!Common.getInstance().IsNull(recentCallsModel.lastCallStatus.createdAt)) {
                String dateTime = Common.getInstance().getDateANDTime(recentCallsModel.lastCallStatus.createdAt);
                if (recentCallsModel.numberOfCalls > 1) {
                    viewHolder.tvContent.setText("(" + recentCallsModel.numberOfCalls + ") " + dateTime);
                } else {
                    viewHolder.tvContent.setText(dateTime);
                }
            }
            if (recentCallsModel.lastCallStatus.serviceType.equalsIgnoreCase("video")) {
                viewHolder.ivServiceType.setColorFilter(ContextCompat.getColor(context, R.color.video_icon_color), android.graphics.PorterDuff.Mode.SRC_IN);
                viewHolder.ivServiceType.setImageResource(R.drawable.ic_video);
            } else {
                viewHolder.ivServiceType.setColorFilter(ContextCompat.getColor(context, R.color.audio_icon_color), android.graphics.PorterDuff.Mode.SRC_IN);
                viewHolder.ivServiceType.setImageResource(R.drawable.ic_audio);
            }
            if (chatSenderReceiverInfo.isCeleb != null && chatSenderReceiverInfo.isCeleb) {
//                viewHolder.ivServiceType.setColorFilter(ContextCompat.getColor(context, R.color.skyblueNew), android.graphics.PorterDuff.Mode.SRC_IN);
            } else {
                viewHolder.ivServiceType.setColorFilter(ContextCompat.getColor(context, R.color.color_ddd), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            if (!Common.getInstance().IsNull(chatSenderReceiverInfo.avtar_imgPath)) {
                String imagePath = chatSenderReceiverInfo.avtar_imgPath;
                if (!imagePath.contains(ApiClient.BASE_URL)) {
                    imagePath = ApiClient.BASE_URL + chatSenderReceiverInfo.avtar_imgPath;
                }
                Common.getInstance().setGlideImage(context, imagePath, viewHolder.ivProfileImage);
            } else {
                viewHolder.ivProfileImage.setImageResource(R.drawable.appiconandroid);
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Common.getInstance().openCallDetailsActivity(context, recentCallsModel);
                }
            });
            viewHolder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iCallsListAdapter.openProfileDialog(chatSenderReceiverInfo, position);
                }
            });
            viewHolder.ivServiceType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (chatSenderReceiverInfo.isCeleb != null && chatSenderReceiverInfo.isCeleb) {
                        if (recentCallsModel.lastCallStatus.serviceType.equalsIgnoreCase("video")) {

//                            iCallsListAdapter.initiateCall(Constants.VIDEO_CALL, chatSenderReceiverInfo);

                        } else {
//                            iCallsListAdapter.initiateCall(Constants.AUDIO_CALL, chatSenderReceiverInfo);


                        }
                    }
                }
            });
            if (position == recentCallsModelArrayList.size() - 1) {
                viewHolder.viewdivider.setVisibility(View.GONE);
                int paddingToBottom = context.getResources().getDimensionPixelSize(R.dimen._48sdp);
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, paddingToBottom);
                holder.itemView.setLayoutParams(layoutParams);
                //holder.itemView.setPadding(0, 0, 0, paddingToBottom);
            } else {
                viewHolder.viewdivider.setVisibility(View.VISIBLE);
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, 0);
                holder.itemView.setLayoutParams(layoutParams);
                //holder.itemView.setPadding(0, 0, 0, 0);
            }
            if (chatSenderReceiverInfo.isCeleb){
                viewHolder.isCelebVerifiedImage.setVisibility(View.VISIBLE);
            }else {
                viewHolder.isCelebVerifiedImage.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return rController == RController.LOADING ? 10 : recentCallsModelArrayList.size();
    }

    public class CallsViewAdapter extends RecyclerView.ViewHolder {
        @BindView(R.id.tvUserName)
        TextView tvUserName;

        @BindView(R.id.tvContent)
        TextView tvContent;

        @BindView(R.id.ivServiceType)
        ImageView ivServiceType;

        @BindView(R.id.ivProfileImage)
        CircleImageView ivProfileImage;

        @BindView(R.id.isCelebVerifiedImage)
        ImageView isCelebVerifiedImage;

        @BindView(R.id.viewdivider)
        View viewdivider;

        public CallsViewAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
