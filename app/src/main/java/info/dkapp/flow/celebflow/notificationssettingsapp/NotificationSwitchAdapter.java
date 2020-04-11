package info.dkapp.flow.celebflow.notificationssettingsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import info.dkapp.flow.R;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import retrofit2.Call;

/**
 * Created by Shiva on 7/17/2018.
 */

public class NotificationSwitchAdapter extends RecyclerView.Adapter<NotificationSwitchAdapter.NotificationSwitchAdapterViewHolder> implements IApiListener {
    private Context context;
    private List<NotificationSwitchListData> notificationSwitchListData;
    //    private NotificationSettings notificationSettings;
    int selected_position = -1;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    IApiListener iApiListener;


    public NotificationSwitchAdapter(Context context, List<NotificationSwitchListData> notificationSwitchListData) {
        this.context = context;
        this.notificationSwitchListData = notificationSwitchListData;
//        this.notificationSettings = notificationSettings;
    }

    @Override
    public NotificationSwitchAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        iApiListener = this;
        View view = LayoutInflater.from(context).inflate(R.layout.notification_switch_item, parent, false);
        return new NotificationSwitchAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationSwitchAdapterViewHolder holder, final int position) {
        if (notificationSwitchListData.get(position).notificationName != null
                && !notificationSwitchListData.get(position).notificationName.isEmpty()) {

            if (Common.isCelebAndManager(context)) {
                holder.notificationTypetxt.setText(notificationSwitchListData.get(position).notificationName);

            } else if (Common.isCelebStatus(context)) {
                if (!notificationSwitchListData.get(position).notificationName.equals("Manager Updates")) {
                    holder.notificationTypetxt.setText(notificationSwitchListData.get(position).notificationName);
                } else {
                    holder.notificationLayout.setVisibility(View.GONE);
                    holder.notificationLayout.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }
            } else if (Common.isManagerStatus(context)) {
                if (!notificationSwitchListData.get(position).notificationName.equals("Fan/Follow Updates")) {
                    holder.notificationTypetxt.setText(notificationSwitchListData.get(position).notificationName);
                } else {
                    holder.notificationLayout.setVisibility(View.GONE);
                    holder.notificationLayout.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }
            } else {
                if (!notificationSwitchListData.get(position).notificationName.equals("Fan/Follow Updates")) {
                    holder.notificationTypetxt.setText(notificationSwitchListData.get(position).notificationName);
                } else {
                    holder.notificationLayout.setVisibility(View.GONE);
                    holder.notificationLayout.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }
                if (!notificationSwitchListData.get(position).notificationName.equals("Manager Updates")) {
                    holder.notificationTypetxt.setText(notificationSwitchListData.get(position).notificationName);
                } else {
                    holder.notificationLayout.setVisibility(View.GONE);
                    holder.notificationLayout.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }
            }


           /* if (SharedPrefsUtil.getStringPreference(context, "isCeleb") != null
                    && !SharedPrefsUtil.getStringPreference(context, "isCeleb").isEmpty()) {
                if (SharedPrefsUtil.getStringPreference(context, "isCeleb").equals("TRUE")) {
                    holder.notificationTypetxt.setText(notificationSwitchListData.get(position).notificationName);
                } else {
                    if (!notificationSwitchListData.get(position).notificationName.equals("Fan/Follow Updates")) {
                        holder.notificationTypetxt.setText(notificationSwitchListData.get(position).notificationName);
                    } else {
                        holder.notificationLayout.setVisibility(View.GONE);
                        holder.notificationLayout.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                    }
                }
            } else {
                if (!notificationSwitchListData.get(position).notificationName.equals("Fan/Follow Updates")) {
                    holder.notificationTypetxt.setText(notificationSwitchListData.get(position).notificationName);
                } else {
                    holder.notificationLayout.setVisibility(View.GONE);
                    holder.notificationLayout.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }

//                if (Common.isCelebAndManager(context)) {
//
//                } else if (Common.isCelebStatus(context)) {
//
//                }else {
//                    if (!notificationSwitchListData.get(position).notificationName.equals("Manager Updates")) {
//                        holder.notificationTypetxt.setText(notificationSwitchListData.get(position).notificationName);
//                    } else {
//                        holder.notificationLayout.setVisibility(View.GONE);
//                    }
//                }
            }*/
        }


        if (notificationSwitchListData.get(position).notificationStats != null) {
            if (notificationSwitchListData.get(position).notificationStats.isEnabled) {
                holder.switchNotification.setChecked(notificationSwitchListData.get(position)
                        .notificationStats.isEnabled);
                holder.switchNotification.setTag(notificationSwitchListData.get(position));
            } else {
//                if (selected_position == position) {
//                    holder.switchNotification.setChecked(true);
//                    holder.switchNotification.setTag(notificationSwitchListData.get(position));
//                } else {
                holder.switchNotification.setChecked(false);
                holder.switchNotification.setTag(notificationSwitchListData.get(position));
//                }
            }
        } else {
            holder.switchNotification.setChecked(true);
            holder.switchNotification.setTag(notificationSwitchListData.get(position));
        }


        holder.switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                Toast.makeText(context, "Come soon", Toast.LENGTH_SHORT).show();

                if (isChecked) {
//                    Toast.makeText(context, "SWITCH_ON " + notificationSwitchListData.get(position)
//                            .get_id(), Toast.LENGTH_SHORT).show();
                    enableOrDisbleSaveintoServer(notificationSwitchListData.get(position).
                                    _id,
                            true);
                } else {
                    enableOrDisbleSaveintoServer(notificationSwitchListData.get(position)
                                    ._id,
                            false);
//                    Toast.makeText(context, "SWITCH_OFF " + notificationSwitchListData.get(position)
//                            .get_id(), Toast.LENGTH_SHORT).show();
                }

               /* for (int i = 0; i < notificationSwitchListData.size(); i++) {
                    if (i == position) {
                        selected_position = position;
//                        notificationSwitchListData.get(position).setEnable(true);
                        enableOrDisbleSaveintoServer(notificationSwitchListData.get(position).getNotificationInnerData().getNotificationSettingId(),
                                true);
                    } else {
                        selected_position = -1;
//                        notificationSwitchListData.get(i).setEnable(false);
                        enableOrDisbleSaveintoServer(notificationSwitchListData.get(position).getNotificationInnerData().getNotificationSettingId(),
                                true);
                    }
                }
                notificationSettings.updateAdapter();*/
            }
        });
    }


    private void enableOrDisbleSaveintoServer(String notificationSettingId, final boolean isEnable) {
        //    progressDialog = Common.showProgressDialog(context, progressDialog);
        NotificationSaveData notificationSaveData = new NotificationSaveData(
                SessionManager.userLogin.userId,
                notificationSettingId, isEnable);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        /*  Call<NotificationSaveData> call = apiInterface.saveNotificationData(notificationSaveData);*/

        Call<ApiResponseModel> call = apiInterface.saveNotificationData(notificationSaveData);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(context, call, ApiClient.SAVE_NOTIFICATION, true, iApiListener, true));


       /* call.enqueue(new retrofit2.Callback<NotificationSaveData>() {
            @Override
            public void onResponse(Call<NotificationSaveData> call, retrofit2.Response<NotificationSaveData> response) {
                Common.dismissProgressDialog(context, progressDialog);
                try {
                    String message = response.body().getMessage();
                    Log.v("response", message);
                    if (message != null) {
                        if (message.equals("Notification updated successfully")) {
                            Toast.makeText(context, "Notification updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<NotificationSaveData> call, Throwable t) {
//                Toast.makeText(context, "Failed to login", Toast.LENGTH_SHORT).show();
                Common.dismissProgressDialog(context, progressDialog);
                Log.e("Failed", "vv" + call + t);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return notificationSwitchListData.size();
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.SAVE_NOTIFICATION)) {
            try {

                Toast.makeText(context, apiResponseModel.message, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

    }


    public class NotificationSwitchAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView notificationTypetxt;
        public Switch switchNotification;
        public RelativeLayout notificationLayout;

        public NotificationSwitchAdapterViewHolder(View itemView) {
            super(itemView);
            notificationTypetxt = (TextView) itemView.findViewById(R.id.notificationTypetxt);
            switchNotification = (Switch) itemView.findViewById(R.id.switchNotification);
            notificationLayout = (RelativeLayout) itemView.findViewById(R.id.notificationLayout);
        }
    }
}
