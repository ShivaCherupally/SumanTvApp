package info.sumantv.flow.bottommenu.menuitemsmanager.fragments.my_schedules;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ikovac.timepickerwithseconds.MyTimePickerDialog;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.bottommenu.activity.BottomMenuActivity;
import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.celebritiestab.CelebProfilesCKAdapter;
import info.sumantv.flow.bottommenu.celebritiestab.CelebritiesList;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataNewAdapter;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.calender_item.CalenderSlotListfragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.modelclasses.ServerTime;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.userflow.Util.DateUtil;
import info.sumantv.flow.utils.CommonSkeletonAdapter;
import info.sumantv.flow.utils.RecyclerUtil.CommonRecycler;
import info.sumantv.flow.utils.internetchecker.InternetSpeedChecker;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateScheduleFragment extends Fragment implements View.OnClickListener, IFragment, IApiListener {

    @BindView(R.id.iVBack)
    ImageView iVBack;

    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    @BindView(R.id.LLCreateSchedule)
    LinearLayout LLCreateSchedule;

    @BindView(R.id.LLServiceTimings)
    LinearLayout LLServiceTimings;

    @BindView(R.id.LLScheduleDuration)
    LinearLayout LLScheduleDuration;

    @BindView(R.id.LLCallCharges)
    LinearLayout LLCallCharges;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.LLScheduleAudioCall)
    LinearLayout LLScheduleAudioCall;

    @BindView(R.id.LLScheduleVideoCall)
    LinearLayout LLScheduleVideoCall;

    @BindView(R.id.tvServieTimingNext)
    TextView tvServieTimingNext;

    @BindView(R.id.tvScheduleDurationNext)
    TextView tvScheduleDurationNext;

    @BindView(R.id.tvCreateScheduleSave)
    TextView tvCreateScheduleSave;

    @BindView(R.id.textView_fromDate)
    TextView textView_fromDate;

    @BindView(R.id.textView_fromTime)
    TextView textView_fromTime;

    @BindView(R.id.textView_toDate)
    TextView textView_toDate;

    @BindView(R.id.textView_toTime)
    TextView textView_toTime;

    @BindView(R.id.tvTotalSlots)
    TextView tvTotalSlots;

    @BindView(R.id.SPCallDuration)
    Spinner SPCallDuration;

    @BindView(R.id.SPBreakDuration)
    Spinner SPBreakDuration;

    @BindView(R.id.SPCallCharges)
    Spinner SPCallCharges;

    @BindView(R.id.tvMinites)
    TextView tvMinites;

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
    @BindView(R.id.LLAdsLayout1)
    LinearLayout LLAdsLayout1;
    @BindView(R.id.LLAdsLayout2)
    LinearLayout LLAdsLayout2;
    @BindView(R.id.recyclerViewOnline)
    RecyclerView recyclerViewOnline;
    @BindView(R.id.recyclerViewSave)
    RecyclerView recyclerViewSave;

    CelebProfilesCKAdapter onlineCelebrityViewAllAdapter;
    IApiListener iApiListener;

    public boolean isCreateSchedule = false, isServiceTimings = false, isScheduleDuration = false, isCallCharges = false;
    String from_formattedDate, to_formattedDate;
    Date fromdate, toDate;
    private long duration = 0;
    private DatePickerDialog mDatePickerDialog, tomDatePickerDialog;
    private Calendar myCalendar, toCalender;
    Calendar c2, c1;
    int dateFrom = 0, dateTo = 0, timeFrom = 0, timeTo = 0, callDuration = 0, breakDuration = 0;
    String serviceType = "", startdate, enddate;
    private static CreateScheduleFragment instance = null;
    long minutes;

    public static CreateScheduleFragment getInstance() {
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        iApiListener = this;
    }

    public CreateScheduleFragment() {
        // Required empty public constructor
    }

    public static CreateScheduleFragment newInstance() {
        CreateScheduleFragment fragment = new CreateScheduleFragment();
        //  Bundle args = new Bundle();
        //args.putParcelableArrayList("GalleryFileList", (ArrayList<? extends Parcelable>) gallerySelectedList);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_create_schedule, container, false);
        ButterKnife.bind(this, root);
        setUp();
        setLayoutVisibility("LLCreateSchedule");
        return root;
    }

    private void setUp() {
        iVBack.setOnClickListener(this);
        LLScheduleAudioCall.setOnClickListener(this);
        LLScheduleVideoCall.setOnClickListener(this);
        tvServieTimingNext.setOnClickListener(this);
        tvScheduleDurationNext.setOnClickListener(this);
        tvCreateScheduleSave.setOnClickListener(this);
        textView_toTime.setOnClickListener(this);
        textView_fromDate.setOnClickListener(this);
        textView_fromTime.setOnClickListener(this);
        textView_toDate.setOnClickListener(this);
        myCalendar = Calendar.getInstance();
        toCalender = Calendar.getInstance();
        setSkelltonView(recyclerViewOnline,true);
        setSkelltonView(recyclerViewSave,true);
        LLEranCredits.setOnClickListener(this);
        LLAuditions.setOnClickListener(this);
        LLPreferences.setOnClickListener(this);
        LLVideo.setOnClickListener(this);
        LLAudio.setOnClickListener(this);
        LLChat.setOnClickListener(this);
        getOnlineCeleb();
        String[] spinnerCallList = {"5 min", "4 min", "3 min", "2 min", "1 min"};
        String[] spinnerBreackList = {"1 min", "2 min", "3 min", "4 min", "5 min"};
        Integer[] callCreditsList = {25 , 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};

        ArrayAdapter spinnerAdapter = new ArrayAdapter(activity(), android.R.layout.simple_spinner_item, spinnerCallList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPCallDuration.setAdapter(spinnerAdapter);

        ArrayAdapter spinnerBreakAdapter = new ArrayAdapter(activity(), android.R.layout.simple_spinner_item, spinnerBreackList);
        spinnerBreakAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPBreakDuration.setAdapter(spinnerBreakAdapter);

        ArrayAdapter spinnerCallChargesAdapter = new ArrayAdapter(activity(),R.layout.call_charges_item, callCreditsList);
        spinnerCallChargesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPCallCharges.setAdapter(spinnerCallChargesAdapter);

        SPCallCharges.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) view.findViewById(R.id.text1)).setText(callCreditsList[i]+" Credits");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SPCallDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        callDuration = 5;
                        setTotalSlots();
                        break;
                    case 1:
                        callDuration = 4;
                        setTotalSlots();
                        break;
                    case 2:
                        callDuration = 3;
                        setTotalSlots();
                        break;
                    case 3:
                        callDuration = 2;
                        setTotalSlots();
                        break;
                    case 4:
                        callDuration = 1;
                        setTotalSlots();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SPBreakDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        breakDuration = 1;
                        setTotalSlots();
                        break;
                    case 1:
                        breakDuration = 2;
                        setTotalSlots();
                        break;
                    case 2:
                        breakDuration = 3;
                        setTotalSlots();
                        break;
                    case 3:
                        breakDuration = 4;
                        setTotalSlots();
                        break;
                    case 4:
                        breakDuration = 5;
                        setTotalSlots();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getOnlineCeleb() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel>  call = apiInterface.getAllCelebritiesList(ApiClient.GET_ALL_CELEBS + Common.isLoginId());
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.GET_ALL_CELEBS, false, iApiListener, false));
    }

    private void setSkelltonView(RecyclerView recyclerViewOnline,boolean firstTime) {
        CommonRecycler.setSkelltonViewOther(getActivity(), recyclerViewOnline, false, firstTime, true);
        recyclerViewOnline.setAdapter(new CommonSkeletonAdapter(RController.LOADING, true));
        recyclerViewSave.setAdapter(new CommonSkeletonAdapter(RController.LOADING, true));
    }

    public void setLayoutVisibility(String condition) {

        switch (condition) {
            case "LLCreateSchedule":
                isCreateSchedule = true;
                tvTitle.setText("Create Schedule");
                LLCreateSchedule.setVisibility(View.VISIBLE);
                LLServiceTimings.setVisibility(View.GONE);
                LLScheduleDuration.setVisibility(View.GONE);
                LLCallCharges.setVisibility(View.GONE);
                break;
            case "LLServiceTimings":
                isServiceTimings = true;
                tvTitle.setText("Create Schedule");
                LLCreateSchedule.setVisibility(View.GONE);
                LLServiceTimings.setVisibility(View.VISIBLE);
                LLScheduleDuration.setVisibility(View.GONE);
                LLCallCharges.setVisibility(View.GONE);
                break;
            case "LLScheduleDuration":
                isScheduleDuration = true;
                tvTitle.setText("Create Slots");
                LLCreateSchedule.setVisibility(View.GONE);
                LLServiceTimings.setVisibility(View.GONE);
                LLScheduleDuration.setVisibility(View.VISIBLE);
                LLCallCharges.setVisibility(View.GONE);
                break;
            case "LLCallCharges":
                isCallCharges = true;
                tvTitle.setText("Create Slots");
                LLCreateSchedule.setVisibility(View.GONE);
                LLServiceTimings.setVisibility(View.GONE);
                LLScheduleDuration.setVisibility(View.GONE);
                LLCallCharges.setVisibility(View.VISIBLE);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iVBack:
                if (isCallCharges) {
                    setLayoutVisibility("LLScheduleDuration");
                    isCallCharges = false;
                } else if (isScheduleDuration) {
                    setLayoutVisibility("LLServiceTimings");
                    isScheduleDuration = false;
                } else if (isServiceTimings) {
                    textView_fromDate.setText("");
                    textView_toDate.setText("");
                    textView_fromTime.setText("");
                    textView_toTime.setText("");
                    dateFrom = 1;
                    dateTo = 0;
                    timeFrom = 0;
                    timeTo = 0;
                    setLayoutVisibility("LLCreateSchedule");
                    isServiceTimings = false;
                } else if (isCreateSchedule) {
                    isCreateSchedule = false;
                    activity().finish();
                } else {
                    activity().finish();
                }
                break;
            case R.id.LLScheduleAudioCall:
                setLayoutVisibility("LLServiceTimings");
                serviceType = Constants.AUDIO_CALL;
                break;
            case R.id.LLScheduleVideoCall:
                setLayoutVisibility("LLServiceTimings");
                serviceType = Constants.VIDEO_CALL;
                break;
            case R.id.tvServieTimingNext:
                if (TextUtils.isEmpty(textView_fromDate.getText().toString())) {
                    showSnackBar("Please select start date", 1);
                } else if (TextUtils.isEmpty(textView_fromTime.getText().toString())) {
                    showSnackBar("Please select start time", 1);
                } else if (TextUtils.isEmpty(textView_toDate.getText().toString())) {
                    showSnackBar("Please select end date", 1);
                } else if (TextUtils.isEmpty(textView_toTime.getText().toString())) {
                    showSnackBar("Please select end time", 1);
                } else {
                    String from_datetime = startdate + " " + textView_fromTime.getText().toString();
                    DateUtil.dateAndTimeConvertStardard(from_datetime);
                    SimpleDateFormat fromdateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    fromdateformate.setTimeZone(TimeZone.getDefault());
                    try {
                        fromdate = fromdateformate.parse(from_datetime);    //yyyy-MM-dd'T'HH:mm:ss.SSSZ
                        SimpleDateFormat from_formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                        from_formatter.setTimeZone(TimeZone.getDefault());
                        from_formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                        from_formattedDate = from_formatter.format(fromdate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // convert todate to utc formate
                    String to_datetime = enddate + " " + textView_toTime.getText().toString();
                    SimpleDateFormat todateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    todateformate.setTimeZone(TimeZone.getDefault());

                    try {
                        toDate = todateformate.parse(to_datetime);    //yyyy-MM-dd'T'HH:mm:ss.SSSZ
                        SimpleDateFormat to_formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                        to_formatter.setTimeZone(TimeZone.getDefault());
                        to_formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                        to_formattedDate = to_formatter.format(toDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (fromdate.before(toDate)) {
                        c1 = Calendar.getInstance();
                        //Change to Calendar Date
                        c1.setTime(fromdate);
                        c2 = Calendar.getInstance();
                        //Change to Calendar Date
                        c2.setTime(toDate);
                        //get Time in milli seconds
                        long ms1 = c1.getTimeInMillis();
                        long ms2 = c2.getTimeInMillis();
                        //get difference in milli seconds
                        duration = ms2 - ms1;
                        int minute = (int) duration / (1000 * 60);
                        int hours = (int) duration / (1000 * 60 * 60);
                        if (10 <= minute && hours <= 3) {
                            createScheduleDuration();
                        } else {
                            showSnackBar("A schedule cannot be less than 10 min and more than 3 hours", Snackbar.LENGTH_LONG);
                        }
                    } else {
                        showSnackBar("Start time Cannot be greater than end time", Snackbar.LENGTH_LONG);
                    }
                }
                break;
            case R.id.tvScheduleDurationNext:
                setLayoutVisibility("LLCallCharges");
                break;
            case R.id.textView_toTime:
                if (!textView_toDate.getText().toString().isEmpty()) {
                    gettotime();
                } else {
                    showSnackBar("Please select end date", 1);
                }
                break;
            case R.id.textView_fromDate:
                calenderPickerDailog();
                break;
            case R.id.textView_fromTime:
                if (!textView_fromDate.getText().toString().isEmpty()) {
                    getfromtime();
                } else {
                    showSnackBar("Please select start date", 1);
                }
                break;
            case R.id.textView_toDate:
                if (!textView_fromDate.getText().toString().isEmpty()) {
                    if (!textView_fromTime.getText().toString().isEmpty())
                        tocalenderpickerdialog();
                    else {
                        showSnackBar("Please select start time", 1);
                    }
                } else {
                    showSnackBar("Please select start date and time", 1);
                }
                break;
            case R.id.tvCreateScheduleSave:
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("memberId", SessionManager.userLogin.userId);
                    jsonObject.put("startTime", from_formattedDate);
                    jsonObject.put("endTime", to_formattedDate);
                    jsonObject.put("scheduleDuration", callDuration);
                    jsonObject.put("creditValue", SPCallCharges.getSelectedItem().toString());
                    jsonObject.put("serviceType", serviceType);
                    jsonObject.put("breakDuration", breakDuration);
//                    checkWithServerTime(jsonObject);
                    createSchedule(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
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
    private void checkWithServerTime(JSONObject jsonObject) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(ApiClient.GET_SERVER_TIME);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_SERVER_TIME,
                true, new IApiListener() {
                    @Override
                    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                        if (condition.equals(ApiClient.GET_SERVER_TIME)) {
                            try {
                                Type type = new TypeToken<ServerTime>() {
                                }.getType();
                                ServerTime serverTime = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                                String serverTimeString = "";
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                                format.setTimeZone(TimeZone.getDefault());
                                format.setTimeZone(TimeZone.getTimeZone("UTC"));
                             if (!serverTime.currentTime.isEmpty()) {
                                    if (from_formattedDate != null) {
                                        long diff = DateUtil.getDateDiff(format, from_formattedDate, serverTimeString);
                                        if (diff > 1) {
                                            Common.getInstance().cusToast(activity(),"Please change time");
                                        } else {
                                            createSchedule(jsonObject);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

                    }
                }, true));
    }
    private void createSchedule(JSONObject jsonObject) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.POST(ApiClient.CREATE_SHEDULE, RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()));
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.CREATE_SHEDULE,
                true, iApiListener, true));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createScheduleDuration() {
        long diff = toDate.getTime() - fromdate.getTime();
        long seconds = diff / 1000;
        minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        tvMinites.setText(minutes + " Mins");
        setTotalSlots();
        setLayoutVisibility("LLScheduleDuration");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setTotalSlots() {

        int totalcallDuration = callDuration + breakDuration;
        try {
            int totalSlots = Math.toIntExact(minutes / totalcallDuration);
            int remaingMins  = Math.toIntExact(minutes % totalcallDuration);
            if (remaingMins == callDuration){
                totalSlots = totalSlots+1;
                tvTotalSlots.setText(totalSlots + "");
            }else {
                tvTotalSlots.setText(totalSlots + "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        Snackbar snackbar = Snackbar.make(relativeLayout, snackBarText, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    private void tocalenderpickerdialog() {

        tomDatePickerDialog = new DatePickerDialog(activity(), R.style.datepicker, topickerListener, toCalender
                .get(Calendar.YEAR), toCalender.get(Calendar.MONTH),
                toCalender.get(Calendar.DAY_OF_MONTH));
        tomDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        tomDatePickerDialog.show();
    }

    private void calenderPickerDailog() {

        mDatePickerDialog = new DatePickerDialog(activity(), R.style.datepicker,
                pickerListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        mDatePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    DatePickerDialog.OnDateSetListener topickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {
            toCalender.set(Calendar.YEAR, year);
            toCalender.set(Calendar.MONTH, monthOfYear);
            toCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            toupdateLabel();
        }
    };


    private void toupdateLabel() {
        String myFormat = "yyyy-MM-dd";//"dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        enddate = sdf.format(toCalender.getTime());
        try {
            Date date1 = sdf.parse(startdate);
            Date date2 = sdf.parse(enddate);
            if (date1.compareTo(date2) < 0) {
                textView_toTime.setText("");
                timeTo = 0;
            } else if (enddate.equals(startdate)) {
                textView_toTime.setText("");
                timeTo = 0;
            } else {
                showSnackBar("Please select other date", 0);
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        textView_toDate.setText(sdf.format(toCalender.getTime()));
        dateTo = 1;

    }


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";//"dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        textView_fromDate.setText(sdf.format(myCalendar.getTime()));
        cleanSlot();
        startdate = sdf.format(myCalendar.getTime());
    }

    public void cleanSlot() {
        textView_toDate.setText("");
        textView_fromTime.setText("");
        textView_toTime.setText("");
        dateFrom = 1;
        dateTo = 0;
        timeFrom = 0;
        timeTo = 0;
    }

    private void gettotime() {
        Calendar now = Calendar.getInstance();
        final MyTimePickerDialog mTimePicker = new MyTimePickerDialog(activity(), new MyTimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(com.ikovac.timepickerwithseconds.TimePicker view, int hourOfDay, int minute, int seconds) {
                textView_toTime.setText(String.format("%02d", hourOfDay) +
                        ":" + String.format("%02d", minute) +
                        ":" + String.format("%02d", 0));
                if (startdate.isEmpty() && startdate.equalsIgnoreCase("")) {
                    return;
                }
                if (enddate.equals(startdate)) {
                    int time1 = Integer.parseInt(textView_fromTime.getText().toString().trim().replace(":", ""));
                    int time2 = Integer.parseInt(textView_toTime.getText().toString().trim().replace(":", ""));
                    if (time2 - time1 >= 600) {
                        timeTo = 1;
                    } else {
                        textView_toTime.setText("");
                        showSnackBar("A schedule cannot be less than 10 min and more than 3 hours", 0);
                    }
                } else {
                    timeTo = 1;
                }
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), true);

        mTimePicker.show();
    }

    private void getfromtime() {
        Calendar now = Calendar.getInstance();
        MyTimePickerDialog mTimePicker = new MyTimePickerDialog(activity(), new MyTimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(com.ikovac.timepickerwithseconds.TimePicker view, int hourOfDay, int minute, int seconds) {

                Calendar datetime = Calendar.getInstance();
                Calendar c = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                datetime.set(Calendar.MINUTE, minute);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date strDate = myCalendar.getTime();
                if (System.currentTimeMillis() > strDate.getTime()) {
                    if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {
                        textView_fromTime.setText(String.format("%02d", hourOfDay) +
                                ":" + String.format("%02d", minute) +
                                ":" + String.format("%02d", 0));
                        textView_toDate.setText("");
                        textView_toTime.setText("");
                        dateTo = 0;
                        timeTo = 0;
                        timeFrom = 1;
                    } else {
                        showSnackBar("Please select valid Time", 0);
                    }
                } else {
                    textView_fromTime.setText(String.format("%02d", hourOfDay) +
                            ":" + String.format("%02d", minute) +
                            ":" + String.format("%02d", seconds));
                    textView_toDate.setText("");
                    textView_toTime.setText("");
                    dateTo = 0;
                    timeTo = 0;
                    timeFrom = 1;
                }
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), true);
        mTimePicker.show();

    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.CREATE_SHEDULE)) {

            Toast.makeText(activity(), "Schedule created successfully ", Toast.LENGTH_SHORT).show();
            if (activity() instanceof HelperActivity) {
                ((HelperActivity) activity()).slotCreateSuccess();
                if (CalenderSlotListfragment.getInstance() != null) {
                    CalenderSlotListfragment.getInstance().refList();
                }
            }
            activity().finish();
        }else if (condition.equals(ApiClient.GET_ALL_CELEBS)) {

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

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.CREATE_SHEDULE)) {

        }
    }
}
