package info.dkapp.flow.chat.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import info.dkapp.flow.R;
import info.dkapp.flow.chat.ActivitySingleChat;
import info.dkapp.flow.chat.interfaces.ISingleChatAdapter;
import info.dkapp.flow.chat.models.SingleChatHistoryModel;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;

import java.util.ArrayList;

public class SingleChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<SingleChatHistoryModel> singleChatHistoryModelArrayList;
    private Context context;
    private ISingleChatAdapter iSingleChatAdapter;

    public SingleChatAdapter(ArrayList<SingleChatHistoryModel> modelArrayList, Context context, ISingleChatAdapter iSingleChatAdapter) {
        this.singleChatHistoryModelArrayList = modelArrayList;
        this.context = context;
        this.iSingleChatAdapter = iSingleChatAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.item_single_chat;
        return new ChatDataModelViewAdapter(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChatDataModelViewAdapter) {
            try {
                ChatDataModelViewAdapter viewHolder = (ChatDataModelViewAdapter) holder;
                String userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
                viewHolder.tvContentMine.setText("");
                viewHolder.tvContentOther.setText("");
                /*ViewCompat.setElevation(viewHolder.llMessageMineCell, 2);
                ViewCompat.setElevation(viewHolder.llMessageOtherCell, 2);*/
                if (!Common.getInstance().IsNull(singleChatHistoryModelArrayList.get(position).message)) {
                    viewHolder.tvContentMine.setText(singleChatHistoryModelArrayList.get(position).message);
                    viewHolder.tvContentOther.setText(singleChatHistoryModelArrayList.get(position).message);
                    viewHolder.tvContentFirstTime.setText(singleChatHistoryModelArrayList.get(position).message);
                }
                viewHolder.tvDateSection.setVisibility(View.GONE);
                ActivitySingleChat activitySingleChat = Common.getInstance().getActivitySingleChat();
                if (position <= 0 && activitySingleChat != null && !activitySingleChat.getIsDataScrollLast().isEmpty()) {
                    viewHolder.tvDateSection.setText(Common.getInstance().getDateSection(singleChatHistoryModelArrayList.get(position).createdAt));
                    viewHolder.tvDateSection.setVisibility(View.VISIBLE);
                } else {
                    try {
                        String currentMsgDate = Common.getInstance().getDateSection(singleChatHistoryModelArrayList.get(position).createdAt);
                        String previousMsgDate = Common.getInstance().getDateSection(singleChatHistoryModelArrayList.get(position - 1).createdAt);
                        if (!currentMsgDate.equalsIgnoreCase(previousMsgDate)) {
                            viewHolder.tvDateSection.setText(currentMsgDate);
                            viewHolder.tvDateSection.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!Common.getInstance().IsNull(singleChatHistoryModelArrayList.get(position).createdAt)) {
                    viewHolder.tvTimeMine.setText(Common.getInstance().get12HrsTime(singleChatHistoryModelArrayList.get(position).createdAt));
                    viewHolder.tvTimeOther.setText(Common.getInstance().get12HrsTime(singleChatHistoryModelArrayList.get(position).createdAt));
                }
                Boolean isSameUserMsg = false;
                try {
                    if (position == 0 || !singleChatHistoryModelArrayList.get(position).senderId.equalsIgnoreCase(singleChatHistoryModelArrayList.get(position - 1).senderId)) {
                        isSameUserMsg = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (isSameUserMsg) {
                    viewHolder.llMessageMineCell.setBackgroundResource(R.drawable.ic_chat_right_msg_bg_new);
                    viewHolder.llMessageOtherCell.setBackgroundResource(R.drawable.ic_chat_left_msg_bg_new);
                    //
                    LinearLayout.LayoutParams layoutParamsRight = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParamsRight.setMargins(0, 20, 0, 0);
                    viewHolder.llMessageMineCell.setLayoutParams(layoutParamsRight);
                    //
                    LinearLayout.LayoutParams layoutParamsLeft = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParamsLeft.setMargins(0, 20, 0, 0);
                    viewHolder.llMessageOtherCell.setLayoutParams(layoutParamsLeft);
                } else {
                    viewHolder.llMessageMineCell.setBackgroundResource(R.drawable.ic_chat_right_msg_bg_new);
                    viewHolder.llMessageOtherCell.setBackgroundResource(R.drawable.ic_chat_left_msg_bg_new);
                    //
                    LinearLayout.LayoutParams layoutParamsRight = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParamsRight.setMargins(0, 10, 0, 0);
                    viewHolder.llMessageMineCell.setLayoutParams(layoutParamsRight);
                    //
                    LinearLayout.LayoutParams layoutParamsLeft = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParamsLeft.setMargins(0, 10, 0, 0);
                    viewHolder.llMessageOtherCell.setLayoutParams(layoutParamsLeft);
                }

                if (singleChatHistoryModelArrayList.get(position).isWelcomeMessage != null && singleChatHistoryModelArrayList.get(position).isWelcomeMessage) {
                    viewHolder.llMessageMine.setVisibility(View.GONE);
                    viewHolder.llMessageOther.setVisibility(View.GONE);
                    viewHolder.llMessageFirstTime.setVisibility(View.VISIBLE);
                } else if (singleChatHistoryModelArrayList.get(position).senderId.equalsIgnoreCase(userMemberId)) {
                    viewHolder.llMessageMine.setVisibility(View.VISIBLE);
                    viewHolder.llMessageOther.setVisibility(View.GONE);
                } else {
                    viewHolder.llMessageMine.setVisibility(View.GONE);
                    viewHolder.llMessageOther.setVisibility(View.VISIBLE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return singleChatHistoryModelArrayList.size();
    }

    public SingleChatHistoryModel getSingleChatHistoryModelAtPosition(Integer position) {
        return singleChatHistoryModelArrayList.get(position);
    }

    public void assignLoadMoreData(ArrayList<SingleChatHistoryModel> singleChatHistoryModelArrayList) {
        this.singleChatHistoryModelArrayList = singleChatHistoryModelArrayList;
        notifyDataSetChanged();
    }

    public class ChatDataModelViewAdapter extends RecyclerView.ViewHolder {
        @BindView(R.id.tvContentMine)
        TextView tvContentMine;

        @BindView(R.id.tvTimeMine)
        TextView tvTimeMine;

        @BindView(R.id.tvContentFirstTime)
        TextView tvContentFirstTime;

        @BindView(R.id.tvContentOther)
        TextView tvContentOther;

        @BindView(R.id.tvTimeOther)
        TextView tvTimeOther;

        @BindView(R.id.tvDateSection)
        TextView tvDateSection;

        @BindView(R.id.llMessageMine)
        LinearLayout llMessageMine;

        @BindView(R.id.llMessageOther)
        LinearLayout llMessageOther;

        @BindView(R.id.llMessageMineCell)
        LinearLayout llMessageMineCell;

        @BindView(R.id.llMessageOtherCell)
        LinearLayout llMessageOtherCell;

        @BindView(R.id.llMessageFirstTime)
        LinearLayout llMessageFirstTime;


        public ChatDataModelViewAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
