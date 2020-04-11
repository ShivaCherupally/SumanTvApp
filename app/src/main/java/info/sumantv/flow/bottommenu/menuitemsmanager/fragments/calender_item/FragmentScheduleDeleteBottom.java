package info.sumantv.flow.bottommenu.menuitemsmanager.fragments.calender_item;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.bottommenu.menuitemsmanager.modelclasses.CalenderSlot;

import info.sumantv.flow.R;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class FragmentScheduleDeleteBottom extends BottomSheetDialogFragment {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.llDelete)
    LinearLayout llDelete;
    @BindView(R.id.ivProfileImage)
    CircleImageView ivProfileImage;

    private CalenderSlot calenderSlot;
    private Dialog promoDialog;
    private Context context;
    private int position;

    public FragmentScheduleDeleteBottom() {
    }

    public void setData(CalenderSlot calenderSlot, Context context, int position) {
        this.calenderSlot = calenderSlot;
        this.context = context;
        this.position = position;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.fragment_notification_delete_bottom, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        //
        ButterKnife.bind(this, contentView);

        llDelete.setOnClickListener(view -> {
                conformationPopup(calenderSlot.id, position);
            dismiss();
        });
    }

    private void conformationPopup(final String scheduleId, final int deletePos) {
        promoDialog = new Dialog(context);
        promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        promoDialog.setCancelable(false);
        promoDialog.setContentView(R.layout.conformation_popup);
        TextView take_photo_txt = (TextView) promoDialog.findViewById(R.id.take_photo_txt);
        TextView fan_profile_role = (TextView) promoDialog.findViewById(R.id.fan_profile_role);
        TextView fan_profile_name = (TextView) promoDialog.findViewById(R.id.fan_profile_name);
        CircleImageView fan_profile_pic = (CircleImageView) promoDialog.findViewById(R.id.fan_profile_pic);

        fan_profile_name.setVisibility(View.GONE);
        fan_profile_role.setVisibility(View.GONE);
        fan_profile_pic.setVisibility(View.GONE);

        take_photo_txt.setText("Are you sure you want to delete the schedule?");
        Button noBtn = (Button) promoDialog.findViewById(R.id.noBtn);
        Button yesBtn = (Button) promoDialog.findViewById(R.id.yesBtn);
        yesBtn.setText("Yes");
        noBtn.setText("No");
        promoDialog.show();

        yesBtn.setOnClickListener(v -> {
            promoDialog.dismiss();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(scheduleId);
            deleteSchedule(jsonArray);
        });

        noBtn.setOnClickListener(v -> promoDialog.dismiss());

    }


    public void deleteSchedule(JSONArray _idList) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject params = new JSONObject();
        try {
            params.put("id", _idList);
            params.put("memberId", SessionManager.userLogin.userId);
            params.put("deleteAll", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params.toString());
        Call<ApiResponseModel> call = apiInterface.POST(ApiClient.SCHEDULE_DELETE, requestBody);
        IApiListener apiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                Toast.makeText(context, "Schedule deleted successfully", Toast.LENGTH_SHORT).show();

                if (CalenderSlotListfragment.getInstance() != null) {
                    CalenderSlotListfragment.getInstance().refreshData(false);
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                Common.getInstance().cusToast(context, "Schedule deletion failed, please try again");
            }
        };
        Common.getInstance().callAPI(new ApiRequestModel().setModel(context, call,
                ApiClient.SCHEDULE_DELETE, true, apiListener, true));
    }

    private Activity activity() {
        return getActivity();
    }
}
