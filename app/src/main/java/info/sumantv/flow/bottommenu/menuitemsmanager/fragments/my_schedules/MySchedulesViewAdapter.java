package info.sumantv.flow.bottommenu.menuitemsmanager.fragments.my_schedules;

/**
 * Created by user on 3/19/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.celebflow.MySchedules;

import info.sumantv.flow.R;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.userflow.Util.DateUtil;

/**
 */

public class MySchedulesViewAdapter extends RecyclerView.Adapter<MySchedulesViewAdapter.ViewHolder> {
    List<MySchedules> mySchedulesList;
    Context context;

    public MySchedulesViewAdapter(Context mContext, List<MySchedules> mySchedulesList) {
        this.mySchedulesList = mySchedulesList;
        this.context = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_schedule_row_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MySchedulesViewAdapter.ViewHolder holder, int position) {


        if (mySchedulesList.get(position).getStartTime() != null && !mySchedulesList.get(position).getStartTime().isEmpty()) {
            holder.textView_date.setText(DateUtil.getCompleteDate(mySchedulesList.get(position).getStartTime()));
        }

        if (mySchedulesList.get(position).getStartTime() != null && !mySchedulesList.get(position).getStartTime().isEmpty()) {
            holder.textView_startTime.setText(DateUtil.serverSentDateConvertTimeInSchedule(mySchedulesList.get(position).getStartTime()));
        }
        if (mySchedulesList.get(position).getEndTime() != null && !mySchedulesList.get(position).getEndTime().isEmpty()) {
            holder.textView_endTime.setText("" + DateUtil.serverSentDateConvertTimeInSchedule(mySchedulesList.get(position).getEndTime()));
        }

        if (mySchedulesList.get(position).getUser_name() != null && !mySchedulesList.get(position).getUser_name().isEmpty()) {
            holder.schedule_user_name.setText(Character.toUpperCase(mySchedulesList.get(position).getUser_name().charAt(0))
                    + mySchedulesList.get(position).getUser_name().substring(1));
        } else {
            holder.schedule_user_name.setText("");
        }

        if (mySchedulesList.get(position).getUser_lastname() != null && !mySchedulesList.get(position).getUser_lastname().isEmpty()) {
            holder.schedule_las_name.setText(Character.toUpperCase(mySchedulesList.get(position).getUser_lastname().charAt(0))
                    + mySchedulesList.get(position).getUser_lastname().substring(1));
        } else {
            holder.schedule_las_name.setText("");
        }

        if (mySchedulesList.get(position).getStartTime() != null && !mySchedulesList.get(position).getStartTime().isEmpty()) {
            if (mySchedulesList.get(position).getEndTime() != null && !mySchedulesList.get(position).getEndTime().isEmpty()) {

//                holder.textView_duration.setText( " " + DateUtil.getDurationInBetweenStartAndEndInMin(
//                        DateUtil.serverSentDateInHoursAndMinAndSec(mySchedulesList.get(position).getStartTime()),
//                        DateUtil.serverSentDateInHoursAndMinAndSec(mySchedulesList.get(position).getEndTime())));

                holder.textView_duration.setText("" + " " + DateUtil.getDurationInBetweenStartAndEndInMin(
                        DateUtil.serverSentDateInHoursAndMinAndSec(mySchedulesList.get(position).getStartTime()),
                        DateUtil.serverSentDateInHoursAndMinAndSec(mySchedulesList.get(position).getEndTime())));
            }
        }


        if (mySchedulesList.get(position).getChatType() != null && !mySchedulesList.get(position).getChatType().isEmpty()) {
            if (mySchedulesList.get(position).getChatType().equals("chat")) {
                holder.duration_layout.setVisibility(View.VISIBLE);
                holder.textView_durationTime.setText(Character.toUpperCase(mySchedulesList.get(position).getChatType()
                        .charAt(0)) + mySchedulesList.get(position).getChatType().substring(1) + " :");
                holder.mSchdule_image.setBackgroundResource(R.drawable.chatimage);
            } else if (mySchedulesList.get(position).getChatType().equals("audio")) {
                holder.duration_layout.setVisibility(View.VISIBLE);
                holder.mSchdule_image.setBackgroundResource(R.drawable.audiocallimagee);
                holder.textView_durationTime.setText(Character.toUpperCase(mySchedulesList.get(position).getChatType().charAt(0))
                        + mySchedulesList.get(position).getChatType().substring(1) + " Call Duration" + " :");

            } else if (mySchedulesList.get(position).getChatType().equals("video")) {
                holder.duration_layout.setVisibility(View.VISIBLE);
                holder.mSchdule_image.setBackgroundResource(R.drawable.videoiconimg);
                holder.textView_durationTime.setText(Character.toUpperCase(mySchedulesList.get(position).getChatType().charAt(0)) +
                        mySchedulesList.get(position).getChatType().substring(1) + " Call Duration" + " :");
            }
        }

        if (mySchedulesList.get(position).getAvtar_imgPath() != null &&
                !mySchedulesList.get(position).getAvtar_imgPath().isEmpty()) {
            Glide.with(context)
                    .load(ApiClient.BASE_URL + mySchedulesList.get(position).getAvtar_imgPath())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_grey_celebkonect_logo)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_grey_celebkonect_logo);
        }
        if (mySchedulesList.get(position).getServiceSchduleStatus() != null &&
                !mySchedulesList.get(position).getServiceSchduleStatus().isEmpty()) {
            mySchedulesList.get(position).getEndTime();
            holder.serviceSchduleStatusBtn.setText(Character.toUpperCase(mySchedulesList.get(position).getServiceSchduleStatus().charAt(0))
                    + mySchedulesList.get(position).getServiceSchduleStatus().substring(1));
            try {
                checkTheStatusBasedOnBacKwordAndForwardTime(mySchedulesList.get(position).getServiceSchduleStatus(), mySchedulesList.get(position).getEndTime(),
                        holder.serviceSchduleStatusBtn, holder.statusView);
            } catch (Exception e) {
            }
        } else {
            holder.serviceSchduleStatusBtn.setVisibility(View.GONE);
        }


//        holder.editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();
//            }
//        });

        if (position == mySchedulesList.size()-1){
            holder.itemView.setPadding(0,0,0,context.getResources().getDimensionPixelOffset(R.dimen._10sdp));
        }

    }

    @SuppressLint("ResourceAsColor")
    private void checkTheStatusBasedOnBacKwordAndForwardTime(String serviceSchduleStatus,
                                                             String endTime,
                                                             TextView serviceSchduleStatusBtn, View statusView) {

        try {
            boolean calTimeBeforeStatus = false;
            Log.e("ServiceENDTime", endTime + "_Call");
            String currentDateAndTime = DateUtil.getCurrentDateAndTimeInUTC();


            long serverEndDateAndTimeEpoc = DateUtil.dateTimeToEpoch(endTime);
            long currentDateAndTimeEpoc = DateUtil.dateTimeToEpoch(currentDateAndTime);

            Log.e("ServiceENDTimeINEPOIc", serverEndDateAndTimeEpoc + "_Call");
            Log.e("getCurrentDTINUTC", currentDateAndTimeEpoc + "_Call");


            if (serverEndDateAndTimeEpoc < currentDateAndTimeEpoc) {
                calTimeBeforeStatus = true;
            } else {
                calTimeBeforeStatus = false;
            }

            if (serviceSchduleStatus.equals("completed")) {
                serviceSchduleStatusBtn.setText("Completed");
                statusView.setBackgroundColor(context.getResources().getColor(R.color.green_circle));
            } else if (serviceSchduleStatus.equals("disconnected")) {
                serviceSchduleStatusBtn.setText("Disconnected");
                statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
            } else if (serviceSchduleStatus.equals("created")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Elapsed");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.status_color_red));
                } else {
                    serviceSchduleStatusBtn.setText("Booked");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                }
            } else if (serviceSchduleStatus.equals("scheduled")) {
                if (calTimeBeforeStatus) {
//                    serviceSchduleStatusBtn.setText("Un-Confirmed");
                    serviceSchduleStatusBtn.setText("Not confirmed");
                } else {
                    serviceSchduleStatusBtn.setText("Confirmed");
                }
            } else if (serviceSchduleStatus.equals("membercalling")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Completed");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.green_circle));
                } else {
                    serviceSchduleStatusBtn.setText("In Progress");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.splashbgcolor));
                }
            } else if (serviceSchduleStatus.equals("celebritycalling")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Completed");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.green_circle));
                } else {
                    serviceSchduleStatusBtn.setText("In Progress");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.splashbgcolor));
                }
            } else if (serviceSchduleStatus.equals("memberAccepted")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Completed");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.green_circle));
                } else {
                    serviceSchduleStatusBtn.setText("In Progress");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.splashbgcolor));
                }
            } else if (serviceSchduleStatus.equals("celebAccepted")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Completed");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.green_circle));
                } else {
                    serviceSchduleStatusBtn.setText("In Progress");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.splashbgcolor));
                }
            } else if (serviceSchduleStatus.equals("memberNotResponded")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Completed");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.green_circle));
                } else {
                    serviceSchduleStatusBtn.setText("Completed");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.green_circle));
                }
            } else if (serviceSchduleStatus.equals("celebNotResponded")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Re-Trying");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.skyblue));
                } else {
                    serviceSchduleStatusBtn.setText("Re-Trying");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.skyblue));
                }
            } else if (serviceSchduleStatus.equals("celebNotResponded2")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Re-Trying");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.skyblue));
                } else {
                    serviceSchduleStatusBtn.setText("Re-Trying");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.skyblue));
                }
            } else if (serviceSchduleStatus.equals("celebNotResponded3")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Cancelled");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                } else {
                    serviceSchduleStatusBtn.setText("Cancelled");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                }
            } else if (serviceSchduleStatus.equals("memberRejected")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Rejected");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                } else {
                    serviceSchduleStatusBtn.setText("Rejected");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                }
            } else if (serviceSchduleStatus.equals("celebRejected")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Rejected");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                } else {
                    serviceSchduleStatusBtn.setText("Rejected");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                }
            } else if (serviceSchduleStatus.equals("celebdisconnected")) {
                if (calTimeBeforeStatus) {
//                    serviceSchduleStatusBtn.setText("Reported");
                    serviceSchduleStatusBtn.setText("Blocked");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                } else {
//                    serviceSchduleStatusBtn.setText("Reported");
                    serviceSchduleStatusBtn.setText("Blocked");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                }
            } else if (serviceSchduleStatus.equals("Reschduled")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Rescheduled");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.skyblue));
                } else {
                    serviceSchduleStatusBtn.setText("Rescheduled");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.skyblue));
                }
            } else if (serviceSchduleStatus.equals("canceled")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Cancelled");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                } else {
                    serviceSchduleStatusBtn.setText("Cancelled");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                }
            } else if (serviceSchduleStatus.equals("blocked")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Blocked");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                } else {
                    serviceSchduleStatusBtn.setText("Blocked");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                }
            } else if (serviceSchduleStatus.equals("completed")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Completed");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.green_circle));
                } else {
                    serviceSchduleStatusBtn.setText("Completed");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.green_circle));
                }
            } else if (serviceSchduleStatus.equals("deleted")) {
                if (calTimeBeforeStatus) {
                    serviceSchduleStatusBtn.setText("Deleted");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                } else {
                    serviceSchduleStatusBtn.setText("Deleted");
                    statusView.setBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1));
                }
            }
        } catch (Exception e) {

        }


    }


    @Override
    public int getItemCount() {
        return mySchedulesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageView;

        TextView textView_date, textView_startTime, textView_endTime, textView_durationTime, textView_duration, textView_chatType;
        Button editBtn;
        TextView schedule_user_name, schedule_las_name;
        TextView serviceSchduleStatusBtn;
        private ImageView mSchdule_image;
        LinearLayout duration_layout;
        View statusView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.mySchedule_profile_image);
            editBtn = (Button) itemView.findViewById(R.id.editBtn);
            textView_date = (TextView) itemView.findViewById(R.id.textView_date);
            textView_startTime = (TextView) itemView.findViewById(R.id.textView_start_time);
            textView_endTime = (TextView) itemView.findViewById(R.id.textView_end_time);
            textView_durationTime = (TextView) itemView.findViewById(R.id.textView_duration_time);
            textView_duration = (TextView) itemView.findViewById(R.id.textView_duration);
            textView_chatType = (TextView) itemView.findViewById(R.id.textView_);
            serviceSchduleStatusBtn = (TextView) itemView.findViewById(R.id.serviceSchduleStatusBtn);
            mSchdule_image = (ImageView) itemView.findViewById(R.id.schedule_phoneimg);
            schedule_user_name = (TextView) itemView.findViewById(R.id.schedule_user_name);
            schedule_las_name = (TextView) itemView.findViewById(R.id.schedule_las_name);
            duration_layout = (LinearLayout) itemView.findViewById(R.id.duration_layout);
            statusView = (View) itemView.findViewById(R.id.statusView);
        }
    }
}
