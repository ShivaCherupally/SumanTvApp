package info.sumantv.flow.bottommenu.menuitemsmanager.fragments.my_schedules;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.bottommenu.activity.BottomMenuActivity;
import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.celebritiestab.CelebProfilesCKAdapter;
import info.sumantv.flow.bottommenu.celebritiestab.CelebritiesList;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataNewAdapter;
import info.sumantv.flow.bottommenu.menuitemsmanager.modelclasses.ScheduleDetails;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.ipoll.interfaces.dialogs.custom.ICustomAlertDialog;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.CommonSkeletonAdapter;
import info.sumantv.flow.utils.RecyclerUtil.CommonRecycler;
import info.sumantv.flow.utils.Utility;
import info.sumantv.flow.utils.internetchecker.InternetSpeedChecker;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewScheduleFragment extends Fragment implements IApiListener, View.OnClickListener {

    @BindView(R.id.LLViewSchedule)
    LinearLayout LLViewSchedule;

    @BindView(R.id.LLPayment)
    LinearLayout LLPayment;

    @BindView(R.id.LLSlotSuccess)
    LinearLayout LLSlotSuccess;

    @BindView(R.id.iVType)
    ImageView iVType;

    @BindView(R.id.iVPaymentSuccess)
    SimpleDraweeView iVPaymentSuccess;

    @BindView(R.id.tvCallType)
    TextView tvCallType;

    @BindView(R.id.tvSuccessCredits)
    TextView tvSuccessCredits;

    @BindView(R.id.tvSuccessCallType)
    TextView tvSuccessCallType;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.iVBack)
    ImageView iVBack;

    @BindView(R.id.tvScheduleDuration)
    TextView tvScheduleDuration;

    @BindView(R.id.tvScheduleDate)
    TextView tvScheduleDate;

    @BindView(R.id.tvScheduleTime)
    TextView tvScheduleTime;

    @BindView(R.id.tvTotalSolts)
    TextView tvTotalSolts;

    @BindView(R.id.tvPaymentCallType)
    TextView tvPaymentCallType;

    @BindView(R.id.tvPaymentCreditValue)
    TextView tvPaymentCreditValue;

    @BindView(R.id.tvContinue)
    TextView tvContinue;

    @BindView(R.id.tvSlotTime)
    TextView tvSlotTime;

    @BindView(R.id.tvCreditPerMinute)
    TextView tvCreditPerMinute;

    @BindView(R.id.tvAvailableSlots)
    TextView tvAvailableSlots;

    @BindView(R.id.tvBookNow)
    TextView tvBookNow;

    @BindView(R.id.tvDone)
    TextView tvDone;

    @BindView(R.id.tvSoltDuration)
    TextView tvSoltDuration;

    @BindView(R.id.tvBreakDuration)
    TextView tvBreakDuration;
    @BindView(R.id.LLBreakDuration)
    LinearLayout LLBreakDuration;
    @BindView(R.id.LLSlotDuration)
    LinearLayout LLSlotDuration;


    @BindView(R.id.LLEranCredits)
    LinearLayout LLEranCredits;
    @BindView(R.id.LLAuditions)
    LinearLayout LLAuditions;
    @BindView(R.id.LLPreferences)
    LinearLayout LLPreferences;
    @BindView(R.id.LLVideo)
    LinearLayout LLVideo;
    @BindView(R.id.LLAudio)
    LinearLayout LLAudio;
    @BindView(R.id.LLChat)
    LinearLayout LLChat;
    @BindView(R.id.recyclerViewOnline)
    RecyclerView recyclerViewOnline;
    @BindView(R.id.recyclerViewSave)
    RecyclerView recyclerViewSave;
    @BindView(R.id.LLAdsLayout1)
    LinearLayout LLAdsLayout1;
    @BindView(R.id.LLAdsLayout2)
    LinearLayout LLAdsLayout2;

    CelebProfilesCKAdapter onlineCelebrityViewAllAdapter;
    ApiInterface apiInterface;
    IApiListener iApiListener;
    ScheduleDetails scheduleDetails;
    String scheduleID, celebID = "";
    boolean isPayment = false, isSuccess = false, isFan = false;
    private static ViewScheduleFragment instance;

    public static ViewScheduleFragment getInstance() {
        return instance;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iApiListener = this;
        instance = this;
    }

    public ViewScheduleFragment() {
        // Required empty public constructor
    }

    public static ViewScheduleFragment newInstance(String scheduleID, String celebID) {
        ViewScheduleFragment fragment = new ViewScheduleFragment();
        Bundle args = new Bundle();
        args.putString("scheduleID", scheduleID);
        args.putString("celebID", celebID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_schedule, container, false);
        ButterKnife.bind(this, root);
        LLEranCredits.setOnClickListener(this);
        LLAuditions.setOnClickListener(this);
        LLPreferences.setOnClickListener(this);
        LLVideo.setOnClickListener(this);
        LLAudio.setOnClickListener(this);
        LLChat.setOnClickListener(this);
        if (!isPayment) {
            tvTitle.setText("View Schedule");
            iVBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_header_back));
            iVType.setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
            LLPayment.setVisibility(View.GONE);
            LLViewSchedule.setVisibility(View.VISIBLE);
            LLSlotSuccess.setVisibility(View.GONE);
            tvDone.setVisibility(View.GONE);
        } else if (isSuccess) {
            tvTitle.setText("Payments");
            iVBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
            iVType.setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
            LLPayment.setVisibility(View.GONE);
            LLSlotSuccess.setVisibility(View.VISIBLE);
            LLViewSchedule.setVisibility(View.GONE);
            tvDone.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setText("Payments");
            iVBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
            iVType.setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
            LLPayment.setVisibility(View.VISIBLE);
            LLSlotSuccess.setVisibility(View.GONE);
            LLViewSchedule.setVisibility(View.GONE);
            tvDone.setVisibility(View.GONE);
        }
        scheduleID = getArguments().getString("scheduleID");
        celebID = getArguments().getString("celebID");
        getScheduleDetails();

        tvBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFan) {
                    if (!scheduleDetails.isBooked) {
                        isPayment = true;
                        tvDone.setVisibility(View.GONE);
                        LLPayment.setVisibility(View.VISIBLE);
                        LLViewSchedule.setVisibility(View.GONE);
                        tvTitle.setText("Payments");
                        iVBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
                        iVType.setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
                        if (scheduleDetails.slotDetails.serviceType.equals(Constants.AUDIO_CALL)) {
                            tvPaymentCallType.setText("Audio Call slot booking");
                        } else {
                            tvPaymentCallType.setText("Video Call slot booking");
                        }
                        tvPaymentCreditValue.setText(scheduleDetails.totalSlotVal + "");
                    }
                } else {
                    Utility.openCustomAlertDialog(getActivity(), "Book Slot",
                            "Please become a FAN to Book a slot", true,
                            new ICustomAlertDialog() {
                                @Override
                                public void doPositiveAction() {
                                    Common.getInstance().openProfileScreen(getActivity(), celebID);
                                }

                                @Override
                                public void doNegativeAction() {

                                }
                            });
                }
            }
        });
        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SessionManager.userLogin.mainCredits <= 0 || scheduleDetails.slotDetails.creditValue > SessionManager.userLogin.mainCredits) {
                    openBuyCreditPopup();
                    return;
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("senderId", SessionManager.userLogin.userId);
                    jsonObject.put("receiverId", celebID);
                    jsonObject.put("service_type", scheduleDetails.slotDetails.serviceType + "");
                    jsonObject.put("actualChargedCredits", scheduleDetails.slotDetails.creditValue + "");
                    jsonObject.put("scheduleId", scheduleID + "");
                    bookSlot(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        iVBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LLPayment.getVisibility() == View.VISIBLE) {
                    LLPayment.setVisibility(View.GONE);
                    LLSlotSuccess.setVisibility(View.GONE);
                    LLViewSchedule.setVisibility(View.VISIBLE);
                    iVBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_header_back));
                    iVType.setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
                    if (scheduleDetails.slotDetails.serviceType.equals(Constants.AUDIO_CALL)) {
                        iVType.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_audio));
                        iVType.setColorFilter(ContextCompat.getColor(getActivity(), R.color.audio_icon_color), android.graphics.PorterDuff.Mode.SRC_IN);
                        tvCallType.setText("Audio Call");
                        tvPaymentCallType.setText("Audio call slot booked");
                    } else {
                        iVType.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_video));
                        iVType.setColorFilter(ContextCompat.getColor(getActivity(), R.color.video_icon_color), android.graphics.PorterDuff.Mode.SRC_IN);
                        tvCallType.setText("Video Call");
                        tvPaymentCallType.setText("Video call slot booked");
                    }
                } else {
                    getActivity().finish();
                }
            }
        });
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.getInstance().openTransactionScreen(getActivity(), "Orders");
                getActivity().finish();
            }
        });
        setSkelltonView(recyclerViewOnline, true);
        setSkelltonView(recyclerViewSave, true);
        getOnlineCeleb();
        return root;
    }

    private void setSkelltonView(RecyclerView recyclerViewOnline, boolean firstTime) {
        CommonRecycler.setSkelltonViewOther(getActivity(), recyclerViewOnline, false, firstTime, true);
        recyclerViewOnline.setAdapter(new CommonSkeletonAdapter(RController.LOADING, true));
        recyclerViewSave.setAdapter(new CommonSkeletonAdapter(RController.LOADING, true));
    }

    private void getOnlineCeleb() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getAllCelebritiesList(ApiClient.GET_ALL_CELEBS + Common.isLoginId());
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.GET_ALL_CELEBS, false, iApiListener, false));
    }

    private void bookSlot(JSONObject jsonObject) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.POST(ApiClient.BOOK_SLOT, RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()));
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.BOOK_SLOT,
                true, iApiListener, true));
    }

    public void openBuyCreditPopup() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.buycredits);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        TextView tvAddCredits = (TextView) dialog.findViewById(R.id.tvAddCredits);

        dialog.show();
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvAddCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), HelperActivity.class);
                intent.putExtra("refCartId", SessionManager.userLogin.userId);
                intent.putExtra(Constants.FRAGMENT_TITLE, "Credit Recharge");
                intent.putExtra("className", "UserBookAppointment");
                intent.putExtra(Constants.FRAGMENT_KEY, 8034);
                getActivity().startActivity(intent);
            }
        });

    }

    public void getScheduleDetails() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(ApiClient.GET_SLOT_DETAILS + scheduleID + "/" + SessionManager.userLogin.userId + "/" + celebID);
//        Call<ApiResponseModel> call = apiInterface.GET(ApiClient.GET_SLOT_DETAILS + scheduleID + "/" + SessionManager.userLogin.userId);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.GET_SLOT_DETAILS, true, iApiListener, false));
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_SLOT_DETAILS)) {
            try {
                Type type = new TypeToken<ScheduleDetails>() {
                }.getType();
                scheduleDetails = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                updateDate();
            } catch (Exception e) {
                Toast.makeText(getContext(), Constants.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else if (condition.equals(ApiClient.BOOK_SLOT)) {
            try {
                if (apiResponseModel.message.equalsIgnoreCase("In Sufficiant Credits")) {
                    openBuyCreditPopup();
                } else {
                    ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.successful_animation).build();
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setUri(imageRequest.getSourceUri())
                            .setAutoPlayAnimations(true)
                            .build();
                    iVPaymentSuccess.setController(controller);

                    iVBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
                    iVType.setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
                    LLPayment.setVisibility(View.GONE);
                    LLSlotSuccess.setVisibility(View.VISIBLE);
                    LLViewSchedule.setVisibility(View.GONE);
                    if (scheduleDetails.slotDetails.serviceType.equals(Constants.AUDIO_CALL)) {
                        tvSuccessCallType.setText("Audio call slot booked");
                    } else {
                        tvSuccessCallType.setText("Video call slot booked");
                    }
                    isSuccess = true;
                    tvTitle.setText("Payments");
                    tvDone.setVisibility(View.VISIBLE);
                    int creditValue = (scheduleDetails.slotDetails.creditValue) * (scheduleDetails.slotDetails.scheduleDuration);
                    tvSuccessCredits.setText(String.valueOf(creditValue) + "");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals(ApiClient.GET_ALL_CELEBS)) {

            Type type = new TypeToken<CelebritiesList>() {
            }.getType();
            CelebritiesList celebritiesList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
            if (celebritiesList != null) {

                //In future once approved from new screeen un comment code
                if (Common.getInstance().getOnlineCelebList().size() > 0) {
                    recyclerViewOnline.setAdapter(onlineCelebrityViewAllAdapter = new CelebProfilesCKAdapter(getActivity(),
                            Common.getInstance().getOnlineCelebList(), getResources().getString(R.string.nowonline)));
                    recyclerViewSave.setAdapter(onlineCelebrityViewAllAdapter = new CelebProfilesCKAdapter(getActivity(),
                            Common.getInstance().getOnlineCelebList(), getResources().getString(R.string.nowonline)));
                }

            } else {
                LLAdsLayout1.setVisibility(View.GONE);
                LLAdsLayout2.setVisibility(View.GONE);
                nodataList(recyclerViewOnline, Constants.WHOOPS, Constants.NO_CELEBS, R.drawable.ic_nodata);
                nodataList(recyclerViewSave, Constants.WHOOPS, Constants.NO_CELEBS, R.drawable.ic_nodata);
            }
        }
    }

    private void nodataList(RecyclerView recyclerViewOnline, String title, String subTitle, int imageResource) {
        recyclerViewOnline.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewOnline.setAdapter(new EmptyDataNewAdapter(getActivity(), title, subTitle, imageResource));
    }

    private void updateDate() {

        if (scheduleDetails.isBooked) {
            if (scheduleDetails.isDeleted){
                tvBookNow.setText("Schedule not\navailable");
            }else {
                tvBookNow.setText("Booked");
            }

        } else {
            if (scheduleDetails.isDeleted){
                tvBookNow.setText("Schedule not\navailable");
            }else {
                tvBookNow.setText("Book Now");
            }
        }

        if (scheduleDetails.availableSlotsCount.equals(0)) {
            tvBookNow.setVisibility(View.GONE);
        } else {
            if (!celebID.equalsIgnoreCase(SessionManager.userLogin.userId)) {
                tvBookNow.setVisibility(View.VISIBLE);


            } else {
                tvBookNow.setVisibility(View.GONE);
            }
        }
        if (scheduleDetails.slotDetails.serviceType.equals(Constants.AUDIO_CALL)) {
            iVType.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_audio));
            iVType.setColorFilter(ContextCompat.getColor(getActivity(), R.color.audio_icon_color), android.graphics.PorterDuff.Mode.SRC_IN);
            tvCallType.setText("Audio Call");
            tvPaymentCallType.setText("Audio call slot booked");
        } else {
            iVType.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_video));
            iVType.setColorFilter(ContextCompat.getColor(getActivity(), R.color.video_icon_color), android.graphics.PorterDuff.Mode.SRC_IN);
            tvCallType.setText("Video Call");
            tvPaymentCallType.setText("Video call slot booked");
        }
        tvAvailableSlots.setText(scheduleDetails.availableSlotsCount + "");
        tvTotalSolts.setText(scheduleDetails.totalSlots + "");

        tvCreditPerMinute.setText(scheduleDetails.slotDetails.creditValue + "");

        tvSlotTime.setText(scheduleDetails.slotDetails.scheduleDuration + " min slot");
        if (scheduleDetails.slotDetails.scheduleDuration == 1) {
            tvSoltDuration.setText(scheduleDetails.slotDetails.scheduleDuration + " min ");
        } else {
            tvSoltDuration.setText(scheduleDetails.slotDetails.scheduleDuration + " mins ");
        }
        if (scheduleDetails.slotDetails.breakDuration == 1) {
            tvBreakDuration.setText(scheduleDetails.slotDetails.breakDuration + " min ");
        } else {
            tvBreakDuration.setText(scheduleDetails.slotDetails.breakDuration + " mins ");
        }

        if (!celebID.equalsIgnoreCase(SessionManager.userLogin.userId)) {
            LLSlotDuration.setVisibility(View.GONE);
            LLBreakDuration.setVisibility(View.GONE);
        } else {
            LLSlotDuration.setVisibility(View.VISIBLE);
            LLBreakDuration.setVisibility(View.VISIBLE);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        long diff = 0;
        try {
            diff = format.parse(scheduleDetails.slotDetails.endTime).getTime() - format.parse(scheduleDetails.slotDetails.startTime).getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            long remaingMinutes = minutes % 60;
            tvScheduleDuration.setText(hours + " Hrs " + remaingMinutes + " Min");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM yyyy");
            tvScheduleDate.setText(formatDate.format(format.parse(scheduleDetails.slotDetails.startTime).getTime()) + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm a");
            tvScheduleTime.setText(formatDate.format(format.parse(scheduleDetails.slotDetails.startTime).getTime()) + " - " + formatDate.format(format.parse(scheduleDetails.slotDetails.endTime).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            if (scheduleDetails.isFan != null) {
                isFan = scheduleDetails.isFan;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_SLOT_DETAILS)) {

        } else if (condition.equals(ApiClient.BOOK_SLOT)) {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.LLEranCredits:
                    if (InternetSpeedChecker.checkInternetAvaialable(getActivity())) {
                        Intent intent = new Intent(getActivity(), HelperActivity.class);
                        intent.putExtra(Constants.FRAGMENT_TITLE, "Invite a friend");
                        intent.putExtra(Constants.FRAGMENT_KEY, 8012);// AppPromotionsFragment
                        startActivity(intent);

                }
                break;
            case R.id.LLAuditions:
                    if (InternetSpeedChecker.checkInternetAvaialable(getActivity())) {
                        Intent intent = new Intent(getActivity(), HelperActivity.class);
                        intent.putExtra(Constants.FRAGMENT_TITLE, "Auditions");
                        intent.putExtra(Constants.FRAGMENT_KEY, 8048);// Audions
                        startActivity(intent);

                }
                break;
            case R.id.LLPreferences:
                Intent intent = new Intent(getActivity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_TITLE, "Preferences");
                intent.putExtra(Constants.FRAGMENT_KEY, 9010);
                startActivity(intent);
                break;
            case R.id.LLVideo:
                Intent intentBottom = new Intent(getActivity(), BottomMenuActivity.class);
                intentBottom.putExtra("isForAdd", true);
                intentBottom.putExtra("addType", "video");
                startActivity(intentBottom);

                break;
            case R.id.LLAudio:
                intentBottom = new Intent(getActivity(), BottomMenuActivity.class);
                intentBottom.putExtra("isForAdd", true);
                intentBottom.putExtra("addType", "audio");
                startActivity(intentBottom);

                break;
            case R.id.LLChat:
                intentBottom = new Intent(getActivity(), BottomMenuActivity.class);
                intentBottom.putExtra("isForAdd", true);
                intentBottom.putExtra("addType", "chat");
                startActivity(intentBottom);
                break;

        }
    }
}
