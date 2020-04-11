package info.dkapp.flow.bottommenu.celebrityprofile;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.bottommenu.constants.RController;
import info.dkapp.flow.bottommenu.viewholders.SkeletonViewHolder;

import info.dkapp.flow.R;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.userflow.Util.DateUtil;

import java.util.ArrayList;

public class CelebSchedulesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<CelebScheduleModel> arrayListData;
    private Context context;
    private RController rController;

    public CelebSchedulesListAdapter(ArrayList<CelebScheduleModel> newChatDataModelArrayList, Context context, RController rController) {
        this.rController = rController;
        this.arrayListData = newChatDataModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (rController == RController.LOADING) {
            int layout = R.layout.skeleton_chat;
            return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        } else {
            int layout = R.layout.item_fragment_celeb_schedules;
            return new AdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterViewHolder) {
            try {
                AdapterViewHolder viewHolder = (AdapterViewHolder) holder;
                viewHolder.tvDuration.setText(DateUtil.getDurationInBetweenStartAndEndInMin(
                        DateUtil.serverSentDateInHoursAndMinAndSec(arrayListData.get(position).startTime),
                        DateUtil.serverSentDateInHoursAndMinAndSec(arrayListData.get(position).endTime)));
                viewHolder.tvDate.setText(Common.getInstance().getDateSection(arrayListData.get(position).startTime));
                viewHolder.tvTime.setText(Common.getInstance().get12HrsTime(arrayListData.get(position).startTime) + " - " + Common.getInstance().get12HrsTime(arrayListData.get(position).endTime));
                //
                if (arrayListData.get(position).slotStatus.equalsIgnoreCase("expired")) {
                    holder.itemView.setBackgroundResource(R.color.whiteGrey);
                } else {
                    holder.itemView.setBackgroundResource(R.color.white);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return rController == RController.LOADING ? 10 : arrayListData.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDuration)
        TextView tvDuration;

        @BindView(R.id.tvDate)
        TextView tvDate;

        @BindView(R.id.tvTime)
        TextView tvTime;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
