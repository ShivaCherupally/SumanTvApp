package info.sumantv.flow.celebflow.Notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.NotificationsFragment;
import info.sumantv.flow.bottommenu.viewholders.SkeletonViewHolder;
import info.sumantv.flow.databaseutil.appstart.AppController;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.celebflow.modelData.NotificationData;
import info.sumantv.flow.celebflow.modelData.NotificationDeleteBottomModel;
import info.sumantv.flow.celebflow.modelData.NotificationFrom;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Utility;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class NotificationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mcontext;
    private List<NotificationData> notificationDataList;
    public RController rController;
    private ArrayList<String> selected_ids = new ArrayList<>(), appendedDatesList = new ArrayList<>(), appendedIdList = new ArrayList<>();
    boolean isSelf;
    String pageType;

    public NotificationsAdapter(Context mContext, RController rController) {
        this.mcontext = mContext;
        this.rController = rController;
    }

    public NotificationsAdapter(Context mContext, boolean isSelf, List<NotificationData> notificationDataList, String pageType) {
        this.mcontext = mContext;
        this.isSelf = isSelf;
        this.pageType = pageType;
        this.notificationDataList = notificationDataList;
        selected_ids = new ArrayList<>();
        appendedDatesList = new ArrayList<>();
        appendedIdList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (rController == RController.LOADING) {
            int layout = R.layout.skeleton_my_celebitem;
            return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifcations_list, parent, false);
            return new MyViewHolder(itemView);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderGlobal, int position) {
        if (holderGlobal instanceof MyViewHolder) {
            MyViewHolder holder = (MyViewHolder) holderGlobal;
            holder.llDayParent.setVisibility(View.GONE);
            holder.ivSelection.setVisibility(View.GONE);
            holder.viewLine.setVisibility(View.GONE);

            if (notificationDataList.get(position).notificationFrom == null) {
                holder.llContent.setVisibility(View.GONE);
                holder.itemView.setBackgroundColor(mcontext.getResources().getColor(R.color.transparent));
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                return;
            }
            //
            holder.ivMore.setVisibility(isSelf ? View.VISIBLE : View.GONE);
            if (notificationDataList.get(position).title != null && !notificationDataList.get(position).title.isEmpty()) {
                holder.tvTitle.setText(Common.getInstance().convertFirstLetterToCapital(notificationDataList.get(position).title));
            }
            String bodyMessage = notificationDataList.get(position).body;
            if (bodyMessage != null && !bodyMessage.isEmpty()) {
                holder.tvContent.setText(bodyMessage);
            }
            final String notificationType = Common.getInstance().IsNullReturnValue(notificationDataList.get(position).notificationType, "");
            final String notificationActivity = Common.getInstance().IsNullReturnValue(notificationDataList.get(position).activity, "");
            String convertedDate = Common.getInstance().getDate(notificationDataList.get(position).createdAt);
            if (notificationDataList.get(position).createdAt != null && !notificationDataList.get(position).createdAt.isEmpty()) {
                if (convertedDate.equalsIgnoreCase("Today")) {
                    holder.tvTime.setText(Utility.makeDateToAgo(notificationDataList.get(position).createdAt));
                } else {
                    holder.tvTime.setText(Common.getInstance().get12HrsTime(notificationDataList.get(position).createdAt));
                }
            }
            if (appendedDatesList.indexOf(convertedDate) < 0 || appendedIdList.indexOf(notificationDataList.get(position).id) >= 0) {
                appendedDatesList.add(convertedDate);
                appendedIdList.add(notificationDataList.get(position).id);
                holder.tvDay.setText(convertedDate);
                holder.llDayParent.setVisibility(View.VISIBLE);
            } else {
                holder.tvDay.setText("");
                holder.llDayParent.setVisibility(View.GONE);
            }
            /*if (position <= 0) {
                holder.tvDay.setText(Common.getInstance().getDate(notificationDataList.get(position).createdAt));
                holder.llDayParent.setVisibility(View.VISIBLE);
            } else {
                try {
                    String currentMsgDate = Common.getInstance().getDate(notificationDataList.get(position).createdAt);
                    String previousMsgDate = Common.getInstance().getDate(notificationDataList.get(position - 1).createdAt);
                    if (!currentMsgDate.equalsIgnoreCase(previousMsgDate)) {
                        holder.tvDay.setText(currentMsgDate);
                        holder.llDayParent.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
            try {
                String currentMsgDate = Common.getInstance().getDate(notificationDataList.get(position).createdAt);
                String nextMsgDate = Common.getInstance().getDate(notificationDataList.get(position + 1).createdAt);
                NotificationFrom notificationFrom = notificationDataList.get(position + 1).notificationFrom;
                if (currentMsgDate.equalsIgnoreCase(nextMsgDate) && notificationFrom != null) {
                    holder.viewLine.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (notificationDataList.get(position).notificationFrom != null && notificationDataList.get(position).notificationFrom.avtarImgPath != null && !Common.getInstance().IsNull(notificationDataList.get(position).notificationFrom.avtarImgPath)) {
                Common.getInstance().setGlideImage(mcontext, ApiClient.BASE_URL + notificationDataList.get(position).notificationFrom.avtarImgPath, holder.ivProfileImage);
            } else {
                holder.ivProfileImage.setImageResource(R.drawable.ic_profile_circle_pleace_holder);
            }
            if (notificationDataList.get(position).isItemSelected == null) {
                setSelection(false, position, holder);
            } else {
                setSelection(notificationDataList.get(position).isItemSelected, position, holder);
            }
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (!isSelf) {
                        return true;
                    }
                    setSelectionClick(position, holder);
                    return true;
                }
            });
            holder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isSelf) {
                        return;
                    }
                        if (selected_ids != null && selected_ids.size() > 0) {
                            //
                        } else {
                            try {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray.put(notificationDataList.get(position).id);
                                NotificationDeleteBottomModel notificationDeleteBottomModel = new NotificationDeleteBottomModel();
                                notificationDeleteBottomModel.itemPosition = position;
                                notificationDeleteBottomModel.title = notificationDataList.get(position).title;
                                notificationDeleteBottomModel.profileImage = notificationDataList.get(position).notificationFrom.avtarImgPath;
                                notificationDeleteBottomModel.notificationsAdapter = NotificationsAdapter.this;
                                notificationDeleteBottomModel._idList = jsonArray;
                                //
                                ((HelperActivity) AppController.getInstance().getCurrentRegisteredActivity())
                                        .openNotificationDeleteBottomLayout(notificationDeleteBottomModel);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                }
            });
            Date myDate = new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
            calendar.setTime(myDate);
            Date CurrentDate = calendar.getTime();
            Date endDate = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                if (notificationDataList.get(position).endTime != null) {
                    endDate = format.parse(notificationDataList.get(position).endTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_SCHEDULE)) {
                if (notificationDataList.get(position).scheduleId != null && endDate != null && CurrentDate.before(endDate)) {
                    holder.tvBookNow.setVisibility(View.VISIBLE);
                }
            } else {
                holder.tvBookNow.setVisibility(View.GONE);
            }
            Date finalEndDate = endDate;
            holder.tvBookNow.setOnClickListener(view -> {
                if (notificationDataList.get(position).scheduleId != null) {
                    Common.getInstance().openViewScheduleScreen(mcontext, notificationDataList.get(position).scheduleId, notificationDataList.get(position).notificationFrom.id);
                }
            });
            holder.itemView.setOnClickListener(view -> {
                if (!isSelf) {
                    return;
                }
                if (selected_ids != null && selected_ids.size() > 0) {
                    setSelectionClick(position, holder);
                } else if (notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_FEED)) {
                    Common.getInstance().getSingleFeedData(notificationDataList.get(position).feedId);
                } else if (notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_FAN)) {
                    Common.getInstance().openProfileScreen(mcontext, notificationDataList.get(position).notificationFrom.id);
                } else if (notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_SCHEDULE) || notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_CALL_REMINDER)) {
                    if (notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_SCHEDULE) && notificationDataList.get(position).scheduleId != null && finalEndDate != null && CurrentDate.before(finalEndDate)) {
                        Common.getInstance().openViewScheduleScreen(mcontext, notificationDataList.get(position).scheduleId,
                                notificationDataList.get(position).notificationFrom.id);
                    } else {
                        Common.getInstance().openCelebSchedulesScreen(mcontext, notificationDataList.get(position).notificationFrom.id, notificationDataList.get(position).activity);
                    }
                } else if (notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_REQUEST_FROM_ADMIN) || notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_MANAGER_ACCEPTED)) {
                    Common.getInstance().openCelebSettingsScreen(mcontext);
                } else if (notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_REQUEST_TO_MANAGER)) {
                    Common.getInstance().openManagerSettingsScreen(mcontext);
                } else if (notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_PURCHASED_CREDITS)) {
                    Common.getInstance().openTransactionScreen(mcontext, "Orders");
                } else {
                    //
                }
            });
        }
    }

    private void setSelectionClick(int position, MyViewHolder holder) {
        boolean isSelected = false;
        if (notificationDataList.get(position).isItemSelected == null || !notificationDataList.get(position).isItemSelected) {
            isSelected = true;
        }
        setSelection(isSelected, position, holder);
        updateNotificationIcons();
    }

    public void updateNotificationIcons() {
        if (selected_ids != null && selected_ids.size() > 0) {
            if (Common.getInstance().getHelperActivity() != null) {
                Common.getInstance().getHelperActivity().enableNotificationIcons(true);
                if (notificationDataList.size() == selected_ids.size()) {
                    Common.getInstance().getHelperActivity().setSelectAllIcon(true);
                } else {
                    Common.getInstance().getHelperActivity().setSelectAllIcon(false);
                }
            }
        } else {
            if (Common.getInstance().getHelperActivity() != null) {
                Common.getInstance().getHelperActivity().enableNotificationIcons(false);
            }
        }
    }

    private void setSelection(boolean isSelected, int position, MyViewHolder holder) {
        if (selected_ids == null) {
            selected_ids = new ArrayList<>();
        }
        if (isSelected) {
            if (selected_ids.indexOf(notificationDataList.get(position).id) < 0) {
                selected_ids.add(notificationDataList.get(position).id);
            }
            notificationDataList.get(position).isItemSelected = true;
            holder.ivProfileImage.setVisibility(View.GONE);
            holder.ivSelection.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundResource(R.color.blue_light_notify_bg);
        } else {
            selected_ids.remove(notificationDataList.get(position).id);
            notificationDataList.get(position).isItemSelected = false;
            holder.ivProfileImage.setVisibility(View.VISIBLE);
            holder.ivSelection.setVisibility(View.GONE);
            holder.itemView.setBackgroundResource(R.color.white);
        }
    }

    public void setSelectedList(ArrayList<String> stringArrayList) {
        selected_ids = stringArrayList;
    }

    public ArrayList<String> getSelectedList() {
        return selected_ids == null ? new ArrayList<>() : selected_ids;
    }

    public void selectAll() {
        if (Common.getInstance().getHelperActivity() != null) {
            @SuppressLint("StaticFieldLeak")
            class asyncTaskData extends AsyncTask<String, Void, String> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {
                    if (selected_ids == null || notificationDataList.size() != selected_ids.size()) {
                        selected_ids = new ArrayList<>();
                        for (int i = 0; i < notificationDataList.size(); i++) {
                            notificationDataList.get(i).isItemSelected = true;
                            if (selected_ids.indexOf(notificationDataList.get(i).id) < 0) {
                                selected_ids.add(notificationDataList.get(i).id);
                            }
                        }
                    } else {
                        selected_ids = new ArrayList<>();
                        for (int i = 0; i < notificationDataList.size(); i++) {
                            notificationDataList.get(i).isItemSelected = false;
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    notifyDataSetChanged();
                    updateNotificationIcons();
                }
            }
            new asyncTaskData().execute();
        }
    }

    private Boolean isNotificationRedirectAvailable(String notificationActivity) {
        if (notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_FAN) || notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_FEED) || notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_SCHEDULE) || notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_CALL_REMINDER) || notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_REQUEST_FROM_ADMIN) || notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_REQUEST_TO_MANAGER) || notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_MANAGER_ACCEPTED) || notificationActivity.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_CELEB_ACCEPTED)) {
            return true;
        }
        return false;
    }

    private void updateDeleteNotification(int position) {
        notificationDataList.remove(position);
        selected_ids = new ArrayList<>();
        notifyDataSetChanged();
        /*if (NotificationsFragment.getInstance() != null) {
            NotificationsFragment.getInstance().refreshFeedAdapter();
        }*/
    }

    public void deleteNotification(JSONArray _idList, int position, boolean isMultipleDelete, boolean isAllDelete, String pageType) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject params = new JSONObject();
        try {
            params.put("id", _idList);
            params.put("memberId", SessionManager.userLogin.userId);
            if (isAllDelete) {
                String notificationType = "";
                if (pageType.equalsIgnoreCase(Constants.NotificationConstants.ALL)) {
                    notificationType = "all";
                } else if (pageType.equalsIgnoreCase(Constants.NotificationConstants.FAN_FOLLOW)) {
                    notificationType = "fan";
                } else if (pageType.equalsIgnoreCase(Constants.NotificationConstants.SERVICE)) {
                    notificationType = "services";
                } else if (pageType.equalsIgnoreCase(Constants.NotificationConstants.MANAGER)) {
                    notificationType = "manager";
                }
                params.put("notificationType", notificationType);
            } else {
                params.put("notificationType", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params.toString());
        Call<ApiResponseModel> call = apiInterface.POST(ApiClient.NOTIFICATION_DELETE, requestBody);
        IApiListener apiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                if (isMultipleDelete || isAllDelete) {
                    if (NotificationsFragment.getInstance() != null) {
                        NotificationsFragment.getInstance().refreshData();
                    }
                } else if (position > -1) {
                    updateDeleteNotification(position);
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                Common.getInstance().cusToast(mcontext, "Notification deletion failed, please try again");
            }
        };
        Common.getInstance().callAPI(new ApiRequestModel().setModel(mcontext, call, ApiClient.NOTIFICATION_DELETE, true, apiListener, true));
    }

    @Override
    public int getItemCount() {
        return rController == RController.LOADING ? 10 : notificationDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvContent)
        TextView tvContent;

        @BindView(R.id.tvBookNow)
        TextView tvBookNow;

        @BindView(R.id.tvTime)
        TextView tvTime;

        @BindView(R.id.tvDay)
        TextView tvDay;

        @BindView(R.id.llDayParent)
        LinearLayout llDayParent;

        @BindView(R.id.llContent)
        LinearLayout llContent;

        @BindView(R.id.ivProfileImage)
        CircleImageView ivProfileImage;

        @BindView(R.id.ivMore)
        ImageView ivMore;

        @BindView(R.id.viewLine)
        View viewLine;

        @BindView(R.id.ivSelection)
        ImageView ivSelection;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void loadMore(List<NotificationData> celebrityList) {
        int positionStart = getItemCount() + 1;
        notifyItemRangeInserted(positionStart, celebrityList.size());
    }
}
