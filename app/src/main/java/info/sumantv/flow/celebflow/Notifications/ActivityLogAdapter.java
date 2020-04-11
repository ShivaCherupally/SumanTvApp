package info.sumantv.flow.celebflow.Notifications;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.viewholders.SkeletonViewHolder;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.modelData.ActivityLog;
import info.sumantv.flow.celebflow.modelData.NotificationData;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.userflow.Util.Common;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uday Kumay Vurukonda on 22-Jul-19.
 * <p>
 * Mr.Psycho
 */
public class ActivityLogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public RController rController;
    List<ActivityLog> activityLogList;
    Context mcontext;
    private ArrayList<String> appendedDatesList = new ArrayList<>(), appendedIdList = new ArrayList<>();

    public ActivityLogAdapter(Context mContext, RController rController) {
        this.mcontext = mContext;
        this.rController = rController;
    }

    public ActivityLogAdapter(Context mContext, List<ActivityLog> activityLogList) {
        this.mcontext = mContext;
        this.activityLogList = activityLogList;
        appendedDatesList = new ArrayList<>();
        appendedIdList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (rController == RController.LOADING) {
            int layout = R.layout.skeleton_my_celebitem;
            return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_log_item, parent, false);
            return new MyViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderGlobal, int position) {
        if (holderGlobal instanceof MyViewHolder) {
            MyViewHolder holder = (MyViewHolder) holderGlobal;
            if(activityLogList.get(position).activityOnProfile == null){
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                return;
            }
            String convertedDate = Common.getInstance().getDate(activityLogList.get(position).createdAt);
            if (appendedDatesList.indexOf(convertedDate) < 0 || appendedIdList.indexOf(activityLogList.get(position).id) >= 0) {
                appendedDatesList.add(convertedDate);
                appendedIdList.add(activityLogList.get(position).id);
                holder.tvDay.setText(convertedDate);
                holder.llDayParent.setVisibility(View.VISIBLE);
            } else {
                holder.tvDay.setText("");
                holder.llDayParent.setVisibility(View.GONE);
            }
            String sourceString =
                    "<b>" + activityLogList.get(position).selfProfile.firstName + " " + activityLogList.get(position).selfProfile.lastName + "</b> "
                            + activityLogList.get(position).activityTypeInfo.firstMessagePart +
                            "<b>" + activityLogList.get(position).activityOnProfile.firstName + " " + activityLogList.get(position).activityOnProfile.lastName + "</b> "
                            + activityLogList.get(position).activityTypeInfo.secondMessagePart + " "
                            + activityLogList.get(position).activityType;
            holder.tvTitle.setText(Html.fromHtml(sourceString));
            if (activityLogList.get(position).activityOnProfile.avtarImgPath != null && !activityLogList.get(position).activityOnProfile.avtarImgPath.isEmpty()) {
                Glide.with(mcontext)
                        .load(ApiClient.BASE_URL + activityLogList.get(position).activityOnProfile.avtarImgPath)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_profile_square_pleace_holder)
                        .into(holder.ivProfileImage);
            } else {
                holder.ivProfileImage.setImageResource(R.drawable.ic_profile_circle_pleace_holder);
            }
            if (activityLogList.get(position).activityTypeInfo.iconUrl != null && !activityLogList.get(position).activityTypeInfo.iconUrl.isEmpty()) {
                Glide.with(mcontext)
                        .load(ApiClient.BASE_URL + activityLogList.get(position).activityTypeInfo.iconUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_profile_square_pleace_holder)
                        .into(holder.ivProfileType);
            } else {
                holder.ivProfileType.setImageResource(R.drawable.ic_profile_circle_pleace_holder);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activityLogList.get(position).feedId != null && !activityLogList.get(position).feedId.isEmpty()) {
                        Common.getInstance().getSingleFeedData(activityLogList.get(position).feedId);
                    } else if (activityLogList.get(position).mediaId != null && !activityLogList.get(position).mediaId.isEmpty()) {
                        Common.getInstance().cusToast(mcontext, "No Feed Id");
                    } else {
                        Common.getInstance().cusToast(mcontext, "No Id");
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return rController == RController.LOADING ? 10 : activityLogList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvDay)
        TextView tvDay;

        @BindView(R.id.llDayParent)
        LinearLayout llDayParent;

        @BindView(R.id.ivProfileImage)
        CircleImageView ivProfileImage;

        @BindView(R.id.ivProfileType)
        CircleImageView ivProfileType;

        @BindView(R.id.viewLine)
        View viewLine;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void loadMore(List<NotificationData> celebrityList) {
        int positionStart = getItemCount() + 1;
        notifyItemRangeInserted(positionStart, celebrityList.size());
    }

    public void loadmore(List<ActivityLog> celebrityList) {
        ArrayList<ActivityLog> appendList = (ArrayList<ActivityLog>) (List<?>) celebrityList;
        int positionStart = getItemCount() + 1;
        notifyItemRangeInserted(positionStart, appendList.size());
    }
}
