package info.dkapp.flow.bottommenu.menuitemsmanager.fragments.my_schedules;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import info.dkapp.flow.bottommenu.constants.RController;
import info.dkapp.flow.bottommenu.viewholders.SkeletonViewHolder;
import info.dkapp.flow.celebflow.Notifications.ArchiveNotificationsHome;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.userflow.Util.DateUtil;

import java.util.List;

class ArchiveNotificationHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public TextView mNotification_Heading, mNotification_Date, mNotification_Description;

    private List<ArchiveNotificationsHome> archiveNotificationsHomeList;
    Context context;
    public RController rController;


    public ArchiveNotificationHomeAdapter(RController rController) {
        this.rController = rController;
    }

    public ArchiveNotificationHomeAdapter(List<ArchiveNotificationsHome> archiveNotificationsHomeList, Context context) {
        this.archiveNotificationsHomeList = archiveNotificationsHomeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (rController == RController.LOADING) {
            int layout = R.layout.skeleton_my_celebitem;
            return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        } else {
            int layout = R.layout.archive_notification_home_adapter;
            return new ArchiveNotificationHomeAdapter.ArchieveNotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderGlobal, int position) {
        if (holderGlobal instanceof ArchiveNotificationHomeAdapter.ArchieveNotificationViewHolder) {

            mNotification_Heading.setText(Character.toUpperCase(archiveNotificationsHomeList.get(position).getTitle().charAt(0))
                    + archiveNotificationsHomeList.get(position).getTitle().substring(1));


            mNotification_Date.setText(DateUtil.getCompleteDate(archiveNotificationsHomeList.get(position).getCreatedAt()) + " - " +
                    DateUtil.serverSentDateConvertTimeInSchedule(archiveNotificationsHomeList.get(position).getCreatedAt()));


            String bodyMessage = archiveNotificationsHomeList.get(position).getBody();
            if (bodyMessage != null && !bodyMessage.isEmpty()) {
                String notificationType = Common.getInstance().IsNullReturnValue(
                        archiveNotificationsHomeList.get(position).getNotificationType(), "");
                if (notificationType.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_FEED)) {
                    if (!archiveNotificationsHomeList.get(position).getStartTime().isEmpty()) {
                        String date = Common.getInstance().getDateSection(archiveNotificationsHomeList.get(position).getStartTime(), "dd/MM/yyyy");
                        String startTime = Common.getInstance().get12HrsTime(archiveNotificationsHomeList.get(position).getStartTime());
                        //
                        bodyMessage += " Date : " + date + " Time : " + startTime;
                    }
                } else {
                    if (!archiveNotificationsHomeList.get(position).getStartTime().isEmpty() && !archiveNotificationsHomeList.get(position).getEndTime().isEmpty()) {
                        String date = Common.getInstance().getDateSection(archiveNotificationsHomeList.get(position).getStartTime(), "dd/MM/yyyy");
                        String startTime = Common.getInstance().get12HrsTime(archiveNotificationsHomeList.get(position).getStartTime());
                        String endTime = Common.getInstance().get12HrsTime(archiveNotificationsHomeList.get(position).getEndTime());
                        //
                        bodyMessage += " Date : " + date + " Time : " + startTime + " To " + endTime;
                    }
                }
//                holder.mNotification_Description.setText(bodyMessage);
                mNotification_Description.setText(bodyMessage);
            }

//            mNotification_Description.setText(archiveNotificationsHomeList.get(position).getBody());
//
//            Log.e("completeData", archiveNotificationsHomeList.get(position) + "");
        }


    }


    @Override
    public int getItemCount() {

        return rController == RController.LOADING ?
                10 : archiveNotificationsHomeList.size();
    }

    public class ArchieveNotificationViewHolder extends RecyclerView.ViewHolder {


        public ArchieveNotificationViewHolder(View itemView) {
            super(itemView);

            mNotification_Heading = (TextView) itemView.findViewById(R.id.notification_heading);
            mNotification_Date = (TextView) itemView.findViewById(R.id.notification_date);
            mNotification_Description = (TextView) itemView.findViewById(R.id.notification_description);


        }
    }
}
