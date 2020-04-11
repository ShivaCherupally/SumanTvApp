package info.dkapp.flow.bottommenu.menuitemsmanager.fragments.calender_item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.constants.RController;
import info.dkapp.flow.bottommenu.menuitemsmanager.modelclasses.CalenderSlot;
import info.dkapp.flow.bottommenu.viewholders.SkeletonViewHolder;
import info.dkapp.flow.databaseutil.appstart.AppController;

import info.dkapp.flow.R;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.userflow.Util.DateUtil;
import info.dkapp.flow.utils.internetchecker.InternetSpeedChecker;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 *
 */

public class SlotItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CalenderSlot> schedulesList;
    private Context context;
    public RController rController;
    private String CelebID;
    private ArrayList<String> selected_ids = new ArrayList<>();


    public SlotItemAdapter(RController rController) {
        this.rController = rController;
    }

    public SlotItemAdapter(Context context, List<CalenderSlot> list, String CelebID) {
        this.schedulesList = list;
        this.context = context;
        this.rController = RController.LOADED;
        this.CelebID = CelebID;
        Log.e("schedulesList", schedulesList.size() + "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (rController == RController.LOADING) {
            int layout = R.layout.my_post_list_item_skelton;
            return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderGlobal, int position) {

        if (holderGlobal instanceof SlotItemAdapter.MyViewHolder) {
            SlotItemAdapter.MyViewHolder holder = (SlotItemAdapter.MyViewHolder) holderGlobal;
            Log.e("position", position + "");

            if (schedulesList.get(position).startTime == null) {
                holder.content_linearlayout.setVisibility(View.GONE);
                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                return;
            }

            if (schedulesList.get(position).scheduleStatus.equalsIgnoreCase("expired")) {
                holder.tvStatus.setText("Expired");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                holder.tvStatus.setText("Active");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
            }


            holder.textView_start_time.setText(DateUtil.serverSentDateConvertTimeInSchedule(schedulesList.get(position).startTime));
            holder.textView_end_time.setText(DateUtil.serverSentDateConvertTimeInSchedule(schedulesList.get(position).endTime));
            holder.scheduleDurationtxt.setText(DateUtil.getDurationTime(schedulesList.get(position).startTime, schedulesList.get(position).endTime));
            holder.dateinfotxt.setText(DateUtil.getCompleteDate(schedulesList.get(position).endTime));

            holder.itemView.setOnClickListener(v -> {
                if (InternetSpeedChecker.checkInternetAvaialable(context)) {
                    if (selected_ids != null && selected_ids.size() > 0) {
                        setSelectionClick(position, holder);
                    } else if (!schedulesList.get(position).scheduleStatus.equalsIgnoreCase("expired") || SessionManager.userLogin.userId.equalsIgnoreCase(schedulesList.get(position).id)) {
                        Common.getInstance().openViewScheduleScreen(context, schedulesList.get(position).id, schedulesList.get(position).memberId);
                    }
                }
            });
            holder.iVMoreOptions.setOnClickListener(view -> {
                if (selected_ids != null && selected_ids.size() > 0) {
                    return;
                } else {
                    ((HelperActivity) AppController.getInstance().getCurrentRegisteredActivity())
                            .openScheduleDeleteBottomLayout(schedulesList.get(position), position, context);
                }
            });


            holder.itemView.setOnLongClickListener(view -> {
                if (CelebID.equals(SessionManager.userLogin.userId)) {
                    setSelectionClick(position, holder);
                    return true;
                }
                return true;
            });

            if (schedulesList.get(position).isItemSelected == null) {
                setSelection(false, position, holder);
            } else {
                setSelection(schedulesList.get(position).isItemSelected, position, holder);
            }


            if (CelebID.equals(SessionManager.userLogin.userId)) {
                holder.iVMoreOptions.setVisibility(View.VISIBLE);
            } else {
                holder.iVMoreOptions.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return rController == RController.LOADING ? 10 : schedulesList.size();
//        return schedulesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dateinfotxt)
        public TextView dateinfotxt;

        @BindView(R.id.textView_start_time)
        public TextView textView_start_time;

        @BindView(R.id.scheduleDurationtxt)
        public TextView scheduleDurationtxt;

        @BindView(R.id.textView_end_time)
        public TextView textView_end_time;

        @BindView(R.id.content_linearlayout)
        public LinearLayout content_linearlayout;

        @BindView(R.id.iVMoreOptions)
        public ImageView iVMoreOptions;


        @BindView(R.id.tvStatus)
        public TextView tvStatus;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void loadmore(List<CalenderSlot> celebrityList) {
        ArrayList<CalenderSlot> appendList = (ArrayList<CalenderSlot>) (List<?>) celebrityList;
        int positionStart = getItemCount() + 1;
        notifyItemRangeInserted(positionStart, appendList.size());
    }

    private void setSelectionClick(int position, SlotItemAdapter.MyViewHolder holder) {
        boolean isSelected = false;
        if (schedulesList.get(position).isItemSelected == null || !schedulesList.get(position).isItemSelected) {
            isSelected = true;
        }
        setSelection(isSelected, position, holder);
        multiSelectionDelete();
    }

    private void setSelection(boolean isSelected, int position, SlotItemAdapter.MyViewHolder holder) {
        if (selected_ids == null) {
            selected_ids = new ArrayList<>();
        }
        if (isSelected) {
            if (selected_ids.indexOf(schedulesList.get(position).id) < 0) {
                selected_ids.add(schedulesList.get(position).id);
            }
            schedulesList.get(position).isItemSelected = true;
            holder.content_linearlayout.setBackgroundResource(R.color.blue_light_notify_bg);
        } else {
            selected_ids.remove(schedulesList.get(position).id);
            schedulesList.get(position).isItemSelected = false;
            holder.content_linearlayout.setBackgroundResource(R.color.white);
        }
    }

    public void multiSelectionDelete() {
        if (selected_ids != null && selected_ids.size() > 0) {
            if (Common.getInstance().getHelperActivity() != null) {
                Common.getInstance().getHelperActivity().enableNotificationIcons(true);
                if (schedulesList.size() == selected_ids.size()) {
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

    public void selectAllInAsynTask() {
        if (Common.getInstance().getHelperActivity() != null) {
            @SuppressLint("StaticFieldLeak")
            class asyncTaskData extends AsyncTask<String, Void, String> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {
                    if (selected_ids == null || schedulesList.size() != selected_ids.size()) {
                        selected_ids = new ArrayList<>();

                        for (int i = 0; i < schedulesList.size(); i++) {
                            schedulesList.get(i).isItemSelected = true;

                            if (selected_ids.indexOf(schedulesList.get(i).id) < 0) {
                                selected_ids.add(schedulesList.get(i).id);
                            }
                        }

                        Log.e("selected_ids", selected_ids.size() + "");

                    } else {
                        selected_ids = new ArrayList<>();
                        for (int i = 0; i < schedulesList.size(); i++) {
                            schedulesList.get(i).isItemSelected = false;
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

    public void updateNotificationIcons() {
        if (selected_ids != null && selected_ids.size() > 0) {
            if (Common.getInstance().getHelperActivity() != null) {
                Common.getInstance().getHelperActivity().enableNotificationIcons(true);
                if (schedulesList.size() == selected_ids.size()) {
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

    public void setSelectedList(ArrayList<String> stringArrayList) {
        selected_ids = stringArrayList;
    }

    public ArrayList<String> getSelectedList() {
        return selected_ids == null ? new ArrayList<>() : selected_ids;
    }

    public void deleteSchedule(JSONArray _idList, int position, boolean isMultipleDelete, boolean isAllDelete) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject params = new JSONObject();
        try {
            params.put("id", _idList);
            params.put("memberId", SessionManager.userLogin.userId);
            if (isAllDelete) {
                params.put("deleteAll", true);
            } else {
                params.put("deleteAll", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params.toString());
        Call<ApiResponseModel> call = apiInterface.POST(ApiClient.SCHEDULE_DELETE, requestBody);
        IApiListener apiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                if (_idList.length() > 1) {
                    Toast.makeText(context, "Schedules deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Schedule deleted successfully", Toast.LENGTH_SHORT).show();
                }
                if (CalenderSlotListfragment.getInstance() != null) {
                    CalenderSlotListfragment.getInstance().refreshData(isAllDelete);
                }
//                } else {
//                    if (position > -1) {
//                        updateDeletedSchedules(_idList.get());
//                    }
//                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                Common.getInstance().cusToast(context, "Schedule deletion failed, please try again");
            }
        };
        Common.getInstance().callAPI(new ApiRequestModel().setModel(context, call,
                ApiClient.SCHEDULE_DELETE, true, apiListener, true));
    }


    private void updateDeletedSchedules(int position) {
        schedulesList.remove(position);
        selected_ids = new ArrayList<>();
        notifyDataSetChanged();
    }

}
