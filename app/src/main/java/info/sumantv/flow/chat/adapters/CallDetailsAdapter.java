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
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.viewholders.SkeletonViewHolder;

import info.sumantv.flow.R;
import info.sumantv.flow.chat.interfaces.ICallDetailsAdapter;
import info.sumantv.flow.chat.models.AllCallsHistoryModel;
import info.sumantv.flow.chat.models.ChatSenderReceiverInfo;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;

import java.util.ArrayList;

public class CallDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<AllCallsHistoryModel> allCallsHistoryModelArrayList;
    private Context context;
    private ICallDetailsAdapter iCallDetailsAdapter;
    String userName = "",userEmail = "",userMemberId = "";
    private RController rController;

    public CallDetailsAdapter(ArrayList<AllCallsHistoryModel> allCallsHistoryModelArrayList, Context context, ICallDetailsAdapter iCallDetailsAdapter,RController rController) {
        this.rController = rController;
        this.allCallsHistoryModelArrayList = allCallsHistoryModelArrayList;
        this.context = context;
        this.iCallDetailsAdapter = iCallDetailsAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (rController == RController.LOADING) {
            int layout = R.layout.skeleton_chat;
            return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        } else {
            int layout = R.layout.item_call_details;
            return new CallsViewAdapter(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CallsViewAdapter) {
            userName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userName, "");
            userEmail = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE,""), "");
            userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
            if (userMemberId.isEmpty()) {
                return;
            }
            if (holder instanceof CallsViewAdapter) {
                CallsViewAdapter viewHolder = (CallsViewAdapter) holder;
                AllCallsHistoryModel allCallsHistoryModel = allCallsHistoryModelArrayList.get(position);
                ChatSenderReceiverInfo chatSenderReceiverInfo;
                if (userMemberId.equalsIgnoreCase(allCallsHistoryModel.receiverId._id)) {
                    chatSenderReceiverInfo = allCallsHistoryModel.senderId;
                } else {
                    chatSenderReceiverInfo = allCallsHistoryModel.receiverId;
                }
                //
                if (allCallsHistoryModel.incoming) {
                    if (allCallsHistoryModel.callDuration.hours <= 0 && allCallsHistoryModel.callDuration.minutes <= 0 && allCallsHistoryModel.callDuration.seconds <= 0) {
                        viewHolder.tvCallStatus.setText("Missed");
                        viewHolder.ivCallStatus.setImageResource(R.drawable.ic_missed_call);
                    } else {
                        viewHolder.tvCallStatus.setText("Incoming");
                        viewHolder.ivCallStatus.setImageResource(R.drawable.ic_incoming_call);
                    }
                } else if (allCallsHistoryModel.outgoing) {
                    viewHolder.tvCallStatus.setText("Outgoing");
                    viewHolder.ivCallStatus.setImageResource(R.drawable.ic_outgoing_call);
                } else {
                    viewHolder.tvCallStatus.setText("Unavailable");
                    viewHolder.ivCallStatus.setImageResource(R.drawable.ic_outgoing_call);
                }
                if (!Common.getInstance().IsNull(allCallsHistoryModel.createdAt)) {
                    String dateTime = Common.getInstance().get12HrsTime(allCallsHistoryModel.createdAt);
                    viewHolder.tvTime.setText(dateTime);
                }
                if (allCallsHistoryModel.callDuration.hours <= 0 && allCallsHistoryModel.callDuration.minutes <= 0 && allCallsHistoryModel.callDuration.seconds <= 0) {
//                    viewHolder.tvDuration.setText("Unavailable");
                    viewHolder.tvDuration.setText("");
                } else if (allCallsHistoryModel.callDuration.hours > 0) {
                    viewHolder.tvDuration.setText(allCallsHistoryModel.callDuration.hours + ":" + allCallsHistoryModel.callDuration.minutes + ":" + allCallsHistoryModel.callDuration.seconds);
                } else {
                    viewHolder.tvDuration.setText(allCallsHistoryModel.callDuration.minutes + ":" + allCallsHistoryModel.callDuration.seconds);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return rController == RController.LOADING ? 10 : allCallsHistoryModelArrayList.size();
    }

    public class CallsViewAdapter extends RecyclerView.ViewHolder {
        @BindView(R.id.tvCallStatus)
        TextView tvCallStatus;

        @BindView(R.id.tvTime)
        TextView tvTime;

        @BindView(R.id.tvDuration)
        TextView tvDuration;

        @BindView(R.id.ivCallStatus)
        ImageView ivCallStatus;

        public CallsViewAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
