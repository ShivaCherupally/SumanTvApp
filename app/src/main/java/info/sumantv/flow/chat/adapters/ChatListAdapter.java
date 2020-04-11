package info.sumantv.flow.chat.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
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
import info.sumantv.flow.chat.interfaces.IChatListAdapter;
import info.sumantv.flow.chat.models.ChatDataConvertModel;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatDataConvertModel> chatDataConvertModelArrayList;
    private Context context;
    private IChatListAdapter iChatListAdapter;
    String userName = "", userEmail = "", userMemberId = "";
    private RController rController;

    public ChatListAdapter(ArrayList<ChatDataConvertModel> chatDataModelArrayList,
                           Context context, IChatListAdapter iChatListAdapter,RController rController) {
        this.rController = rController;
        this.chatDataConvertModelArrayList = chatDataModelArrayList;
        this.context = context;
        this.iChatListAdapter = iChatListAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (rController == RController.LOADING) {
            int layout = R.layout.skeleton_chat;
            return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        } else {
            int layout = R.layout.item_chat_list;
            return new ChatDataModelViewAdapter(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        userName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userName, "");
        userEmail = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE,""), "");
        userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
        if (userMemberId.isEmpty()) {
            return;
        }
        if (holder instanceof ChatDataModelViewAdapter) {
            ChatDataModelViewAdapter viewHolder = (ChatDataModelViewAdapter) holder;
            String firstName, lastName, message, createdAt, avtar_imgPath;
            Integer messageCount = 0;
            //
            firstName = chatDataConvertModelArrayList.get(position).firstName;
            lastName = chatDataConvertModelArrayList.get(position).lastName;
            message = chatDataConvertModelArrayList.get(position).message;
            createdAt = chatDataConvertModelArrayList.get(position).createdAt;
            avtar_imgPath = chatDataConvertModelArrayList.get(position).avtar_imgPath;
            messageCount = chatDataConvertModelArrayList.get(position).counter;
            //
            if (!Common.getInstance().IsNull(firstName)) {
                viewHolder.tvUserName.setText(Common.convertCaseSensitive(
                        firstName) + " " + lastName);
            }
            if (!Common.getInstance().IsNull(message)) {
                viewHolder.tvContent.setText(message);
            }
            if (!Common.getInstance().IsNull(createdAt)) {
                viewHolder.tvTime.setText(Common.getInstance().getDateORTime(createdAt));
            }
            if (messageCount == null) {
                messageCount = 0;
            }
            if (messageCount > 0) {
                viewHolder.tvMessageCount.setText(String.valueOf(messageCount));
                viewHolder.tvMessageCount.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvMessageCount.setVisibility(View.GONE);
            }
            if (!Common.getInstance().IsNull(avtar_imgPath)) {
                Common.getInstance().setGlideImage(context, ApiClient.BASE_URL + avtar_imgPath, viewHolder.ivProfileImage);
            } else {
                viewHolder.ivProfileImage.setImageResource(R.drawable.ic_profile_circle_pleace_holder);
            }
            /*if(!Common.getInstance().IsNull(chatStatus) && chatStatus.equalsIgnoreCase("Active")){
                viewHolder.ivOnline.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ivOnline.setVisibility(View.GONE);
            }*/
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        setMsgCountZero(position);
                        iChatListAdapter.openSingleChatWindow(chatDataConvertModelArrayList.get(position), position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            viewHolder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iChatListAdapter.openProfileDialog(chatDataConvertModelArrayList.get(position), position);
                }
            });
            if (position == chatDataConvertModelArrayList.size() - 1) {
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

            if (chatDataConvertModelArrayList.get(position).isCeleb){
                viewHolder.isCelebVerifiedImage.setVisibility(View.VISIBLE);
            }else {
                viewHolder.isCelebVerifiedImage.setVisibility(View.GONE);
            }
        }
    }

    public void setMsgCountZero(Integer position) {
        ChatDataConvertModel chatDataModel = chatDataConvertModelArrayList.get(position);
        chatDataModel.counter = 0;
        chatDataConvertModelArrayList.set(position, chatDataModel);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return rController == RController.LOADING ? 10 : chatDataConvertModelArrayList.size();
    }

    public class ChatDataModelViewAdapter extends RecyclerView.ViewHolder {
        @BindView(R.id.tvUserName)
        TextView tvUserName;

        @BindView(R.id.tvContent)
        TextView tvContent;

        @BindView(R.id.tvTime)
        TextView tvTime;

        @BindView(R.id.tvMessageCount)
        TextView tvMessageCount;

        @BindView(R.id.ivProfileImage)
        CircleImageView ivProfileImage;

        @BindView(R.id.ivOnline)
        ImageView ivOnline;

        @BindView(R.id.viewdivider)
        View viewdivider;

        @BindView(R.id.isCelebVerifiedImage)
        ImageView isCelebVerifiedImage;

        public ChatDataModelViewAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
