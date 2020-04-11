package info.dkapp.flow.bottommenu.menuitemsmanager.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ikovac.timepickerwithseconds.MyTimePickerDialog;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.bottommenu.menuitemsmanager.ISlot_value;
import info.dkapp.flow.bottommenu.menuitemsmanager.Slot_value_model;
import info.dkapp.flow.bottommenu.menuitemsmanager.adapter.Slot_value_adapter;
import info.dkapp.flow.bottommenu.menuitemsmanager.modelclasses.ServerTime;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.ipoll.interfaces.dialogs.custom.ICustomAlertDialog;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.userflow.Util.DateUtil;
import info.dkapp.flow.utils.Utility;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class CreateSlotFragment extends Fragment implements View.OnClickListener, IFragment, ICustomAlertDialog, IApiListener {

    // SeekBar seekBar;
    protected static TextView chargeCreditstxt, textView_From_Date, textView_To_Date, textView_From_Time, textView_To_Time, tv_credit_value;
    Spinner spinner_Min_Time, spinner_Break_Time;
    IndicatorSeekBar seekBar_indicator;
    ImageView imgClose;
    RadioButton radioButtonAudio, radioButtonVideo;
    Button proceed, button_proceed;
    //    CheckBox audioCheck, videoCheck, checkbox3;
    RelativeLayout relativeLayout, submitLayout, relativelayout_seekBar;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    static String starttime, startdate, todate, totime;
    String breaktime_item;
    String minimumtime_item;
    String seekbarvalue = "25";
    static int sec;
    String from_formattedDate, to_formattedDate;
    private DatePickerDialog mDatePickerDialog, tomDatePickerDialog;
    private Calendar myCalendar, toCalender;
    Calendar c2, c1;
    private long duration = 0;
    private ProgressDialog progressDialog;
    Date currenttimedt = null;
    LinearLayout audioLayout, videoLayout;
    private RadioGroup radioGroupCall;

    int dateFrom = 0, dateTo = 0, timeFrom = 0, timeTo = 0;
    boolean audioCheckedStatus = false, videoCheckedStatus = false;


    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS_CAL = 1;
    Boolean isSeeking = false, isDialogOpen = false;
    IApiListener iApiListener;
    boolean isActionPerform = false;
    ArrayList<Slot_value_model> values_list = new ArrayList<>();
    String serviceType = Constants.AUDIO_CALL;


    public static CreateSlotFragment newInstance(String param1, String param2) {
        CreateSlotFragment fragment = new CreateSlotFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.create_schedule, null);
        iApiListener = this;
        initComponent(root);
//        getContractForService("audio");
        return root;
    }

    private void getContractForService(String servicetype) {
        String url = ApiClient.GET_CONTRACTS
                + SessionManager.userLogin.userId +
                "/" + servicetype;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(url);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_CONTRACTS,
                true, iApiListener, false));
    }

    private void initComponent(View root) {

        imgClose = root.findViewById(R.id.imgClose);
        relativeLayout = (RelativeLayout) root.findViewById(R.id.relativeLayout);
        relativelayout_seekBar = (RelativeLayout) root.findViewById(R.id.relativelayout_seekBar);
        radioGroupCall = (RadioGroup) root.findViewById(R.id.radioGroupCall);
        radioGroupCall.clearCheck();

        radioButtonAudio = (RadioButton) root.findViewById(R.id.radioButtonAudio);
        radioButtonVideo = (RadioButton) root.findViewById(R.id.radioButtonVideo);
        proceed = (Button) root.findViewById(R.id.button_proceed);
        button_proceed = (Button) root.findViewById(R.id.button_proceed);
        //seekBar = (SeekBar) root.findViewById(R.id.seekBar);
        seekBar_indicator = (IndicatorSeekBar) root.findViewById(R.id.seekBar_indicator);
        chargeCreditstxt = (TextView) root.findViewById(R.id.chargeCreditstxt);
        tv_credit_value = (TextView) root.findViewById(R.id.tv_credit_value);
        textView_From_Date = (TextView) root.findViewById(R.id.textView_fromDate);
        textView_To_Date = (TextView) root.findViewById(R.id.textView_toDate);
        textView_From_Time = (TextView) root.findViewById(R.id.textView_fromTime);
        textView_To_Time = (TextView) root.findViewById(R.id.textView_toTime);
        spinner_Min_Time = (Spinner) root.findViewById(R.id.spinner_minTime);
        spinner_Break_Time = (Spinner) root.findViewById(R.id.spinner_breakTime);


        proceed.setOnClickListener(this);
        button_proceed.setOnClickListener(this);
        textView_From_Date.setOnClickListener(this);
        textView_To_Date.setOnClickListener(this);
        textView_From_Time.setOnClickListener(this);
        textView_To_Time.setOnClickListener(this);
        relativelayout_seekBar.setOnClickListener(this);

        myCalendar = Calendar.getInstance();
        toCalender = Calendar.getInstance();
        progressDialog = new ProgressDialog(activity(), R.style.AppCompatAlertDialogStyle);

        loadData();

        List<String> timeList = new ArrayList<>();
        timeList.add("2 min");
        timeList.add("5 min");
        timeList.add("10 min");
        timeList.add("15 min");
        timeList.add("20 min");
        timeList.add("30 min");


        List<String> mintimeList = new ArrayList<>();
        mintimeList.add("2 min");
        mintimeList.add("5 min");
        mintimeList.add("10 min");
        mintimeList.add("15 min");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity(), android.R.layout.simple_spinner_item, timeList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Break_Time.setAdapter(arrayAdapter);


        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(activity(), android.R.layout.simple_spinner_item, mintimeList);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Min_Time.setAdapter(arrayAdapter1);


        spinner_Min_Time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                minimumtime_item = adapterView.getItemAtPosition(position).toString();
                String[] separated = minimumtime_item.split(" ");
                minimumtime_item = separated[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isActionPerform) {
                    Utility.openCustomAlertDialog(activity(), "Create Schedule",
                            "Do you want to discard the schedule?", true,
                            CreateSlotFragment.this);
                } else {
                    activity().onBackPressed();
                }

            }
        });

        spinner_Break_Time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                breaktime_item = adapterView.getItemAtPosition(position).toString();
                String[] separated = breaktime_item.split(" ");
                breaktime_item = separated[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        seekBar_indicator.setOnSeekChangeListener(new OnSeekChangeListener() {
            int seekBarProgress = 25;

            @Override
            public void onSeeking(SeekParams seekParams) {

                isSeeking = true;
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isSeeking = false;
                    }
                }, 300);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //New Check
                        /*if (!isSeeking && !isDialogOpen) {
                            if (seekParams.progress <= 25) {
                                //    seekBar.setProgress(25);
                                seekBarProgress = 25;
                            } else {
                                seekBarProgress = seekParams.progress;
                            }
                            seekBar_indicator.setProgress(seekBarProgress);
                            seekbarvalue = String.valueOf(seekBarProgress);
                        } else {
                            isDialogOpen = true;
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    isDialogOpen = false;
                                }
                            }, 1000);
                        }*/


                        if (!isSeeking && !isDialogOpen) {
                                if (seekParams.progress <= 25) {
//                                seekBarProgress.setProgress(25);
                                    seekBarProgress = 25;
                                } else {
                                    seekBarProgress = seekParams.progress;
                                }
                                chargeCreditstxt.setText(seekBarProgress + " Credits");
                                seekbarvalue = String.valueOf(seekBarProgress);

                        }
                    }


                }, 500);


            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });


        /*audioCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (Common.isSwicthed()) {
                    if (CommonAccessPermissionOfCeleb.schedulePermissionAvailabilty(getContext(), false,
                            false)) {
                        audioCheckedStatus = (isChecked) ? true : false;
                        if (audioCheckedStatus) {
                            videoCheck.setChecked(false);
                            getContractForService("audio");
                            isActionPerform = true;
                        } else {
                            videoLayout.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    audioCheckedStatus = (isChecked) ? true : false;
                    if (audioCheckedStatus) {
                        videoCheck.setChecked(false);
                        isActionPerform = true;
                    } else {
                        videoLayout.setVisibility(View.VISIBLE);
                    }
                }

            }
        });*/
        /*videoCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (Common.isSwicthed()) {
                    if (CommonAccessPermissionOfCeleb.schedulePermissionAvailabilty(getContext(), false,
                            false)) {
                        videoCheckedStatus = (isChecked) ? true : false;
                        if (videoCheckedStatus) {
                            isActionPerform = true;
                            audioCheck.setChecked(false);
                            getContractForService("video");
                        } else {
                            audioLayout.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    videoCheckedStatus = (isChecked) ? true : false;
                    if (videoCheckedStatus) {
                        isActionPerform = true;
                        audioCheck.setChecked(false);
                    } else {
                        audioLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
//                }
            // }
        });*/


        submitLayout = (RelativeLayout) root.findViewById(R.id.submitLayout);
        submitLayout.setOnClickListener(this);

        radioGroupCall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                if (Common.isSwicthed()) {
                        if (null != rb && checkedId > -1) {
                            if (rb.getText().toString().equalsIgnoreCase(getResources().getString(R.string.audio_call))) {
                                getContractForService(Constants.AUDIO_CALL);
                                isActionPerform = true;
                                serviceType = Constants.AUDIO_CALL;
                            } else {
                                isActionPerform = true;
                                getContractForService(Constants.VIDEO_CALL);
                                serviceType = Constants.VIDEO_CALL;
                            }
                        }

                } else {
                    if (null != rb && checkedId > -1) {
                        if (rb.getText().toString().equalsIgnoreCase(getResources().getString(R.string.audio_call))) {
//                            getContractForService(Constants.AUDIO_CALL);
                            isActionPerform = true;
                            serviceType = Constants.AUDIO_CALL;
                        } else {
                            isActionPerform = true;
//                            getContractForService(Constants.VIDEO_CALL);
                            serviceType = Constants.VIDEO_CALL;
                        }
                    }
                }


            }
        });

    }

    private void loadData() {
        Slot_value_model slot_value_model = new Slot_value_model(" 25 ", true);
        values_list.add(slot_value_model);
        slot_value_model = new Slot_value_model(" 50 ", false);
        values_list.add(slot_value_model);
        slot_value_model = new Slot_value_model(" 100 ", false);
        values_list.add(slot_value_model);
        slot_value_model = new Slot_value_model(" 150 ", false);
        values_list.add(slot_value_model);
        slot_value_model = new Slot_value_model(" 200 ", false);
        values_list.add(slot_value_model);
        slot_value_model = new Slot_value_model(" 250 ", false);
        values_list.add(slot_value_model);
        slot_value_model = new Slot_value_model(" 300 ", false);
        values_list.add(slot_value_model);
        slot_value_model = new Slot_value_model(" 350 ", false);
        values_list.add(slot_value_model);
        slot_value_model = new Slot_value_model(" 400 ", false);
        values_list.add(slot_value_model);
        slot_value_model = new Slot_value_model(" 450 ", false);
        values_list.add(slot_value_model);
        slot_value_model = new Slot_value_model(" 500 ", false);
        values_list.add(slot_value_model);
    }

   /* public void onRadioButtonClicked(View v) {

        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()) {
            case R.id.radioButtonAudio:
                if (checked)
                    getContractForService("audio");
                isActionPerform = true;
                break;

            case R.id.radioButtonVideo:
                if (checked)
                    isActionPerform = true;
                getContractForService("video");
                break;
        }

    }*/

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.submitLayout:
                creatingSlotInServer();
                break;
            case R.id.button_proceed:
                creatingSlotInServer();
                break;
            case R.id.textView_fromDate:
                isActionPerform = true;
                calenderPickerDailog();
                break;
            case R.id.textView_fromTime:
                isActionPerform = true;
                if (!textView_From_Date.getText().toString().isEmpty()) {
                    getfromtime();
                } else {
                    showSnackBar("Please select start date", 1);
                }
                break;
            case R.id.textView_toDate:
                isActionPerform = true;
                if (!textView_From_Date.getText().toString().isEmpty()) {
                    if (!textView_From_Time.getText().toString().isEmpty())
                        tocalenderpickerdialog();
                    else {
                        showSnackBar("Please select start time", 1);
                    }
                } else {
                    showSnackBar("Please select start date and time", 1);
                }
                break;
            case R.id.textView_toTime:
                isActionPerform = true;
                if (!textView_To_Date.getText().toString().isEmpty()) {
                    gettotime();
                } else {
                    showSnackBar("Please select end date", 1);
                }
                break;
            case R.id.relativelayout_seekBar:
              /*  if (CommonAccessPermissionOfCeleb.creditsPermissonAvailabilty(getContext(),
                        false, true)) {*/
                    BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.SheetDialog);

                    View sheetView = getActivity().getLayoutInflater().inflate(R.layout.slot_values_layout, null);
                    RecyclerView slot_values_recyclerview = (RecyclerView) sheetView.findViewById(R.id.slot_values_recyclerview);

                    Slot_value_adapter slot_value_adapter = new Slot_value_adapter(activity(), values_list, new ISlot_value() {
                        @Override
                        public void onClick(String value, int position) {

                            mBottomSheetDialog.dismiss();
                            tv_credit_value.setText(value);
                            for (int i = 0; i < values_list.size(); i++) {
                                if (i == position) {
                                    values_list.get(i).isSelect = true;
                                } else {
                                    values_list.get(i).isSelect = false;
                                }
                            }
                        }
                    });
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(activity(), LinearLayoutManager.HORIZONTAL, false);
                    slot_values_recyclerview.setLayoutManager(layoutManager);
                    slot_values_recyclerview.setItemAnimator(new DefaultItemAnimator());
                    slot_values_recyclerview.setAdapter(slot_value_adapter);
                    mBottomSheetDialog.setContentView(sheetView);
                    mBottomSheetDialog.show();

//                }
                break;
        }

    }


    private void creatingSlotInServer() {
        if (!radioButtonAudio.isChecked() && !radioButtonVideo.isChecked()) {
            showSnackBar("Please select a service type", 1);
        } else if (TextUtils.isEmpty(textView_From_Date.getText().toString()) && TextUtils.isEmpty(textView_From_Time.getText().toString()) && TextUtils.isEmpty(textView_To_Date.getText().toString()) && TextUtils.isEmpty(textView_To_Time.getText().toString())) {
            showSnackBar("Please select service timings", 1);
        } else if (TextUtils.isEmpty(textView_From_Date.getText().toString())) {
            showSnackBar("Please select start date", 1);
        } else if (TextUtils.isEmpty(textView_From_Time.getText().toString())) {
            showSnackBar("Please select start time", 1);
        } else if (TextUtils.isEmpty(textView_To_Date.getText().toString())) {
            showSnackBar("Please select end date", 1);
        } else if (TextUtils.isEmpty(textView_To_Time.getText().toString())) {
            showSnackBar("Please select end time", 1);
        } else {
            JSONObject jsonObject = new JSONObject();
            // convert fromdate to utc formate
            String from_datetime = startdate + " " + textView_From_Time.getText().toString();
            DateUtil.dateAndTimeConvertStardard(from_datetime);
            SimpleDateFormat fromdateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            fromdateformate.setTimeZone(TimeZone.getDefault());
            Date fromdate = null;
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
            String to_datetime = todate + " " + textView_To_Time.getText().toString();
            SimpleDateFormat todateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            todateformate.setTimeZone(TimeZone.getDefault());
            Date todate = null;
            try {
                todate = todateformate.parse(to_datetime);    //yyyy-MM-dd'T'HH:mm:ss.SSSZ
                SimpleDateFormat to_formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                to_formatter.setTimeZone(TimeZone.getDefault());
                to_formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                to_formattedDate = to_formatter.format(todate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                jsonObject.put("memberId", SessionManager.userLogin.userId);
                jsonObject.put("startTime", from_formattedDate);
                jsonObject.put("endTime", to_formattedDate);
                jsonObject.put("breakDuration", 0);
//                jsonObject.put("creditValue", Double.parseDouble(seekbarvalue));
                jsonObject.put("creditValue", tv_credit_value.getText().toString().trim());
                jsonObject.put("scheduleDuration", 2);
                jsonObject.put("createdBy", SessionManager.userLogin.userName);
                JSONArray jsonArray = new JSONArray();

//                if (audioCheck.isChecked()) {
//                    jsonArray.put("audio");
//                }
//                if (videoCheck.isChecked()) {
//                    jsonArray.put("video");
//                }

                jsonArray.put(serviceType);

                jsonObject.put("serviceType", jsonArray);
                if (fromdate.before(todate)) {
                    c1 = Calendar.getInstance();
                    //Change to Calendar Date
                    c1.setTime(fromdate);
                    c2 = Calendar.getInstance();
                    //Change to Calendar Date
                    c2.setTime(todate);
                    //get Time in milli seconds
                    long ms1 = c1.getTimeInMillis();
                    long ms2 = c2.getTimeInMillis();
                    //get difference in milli seconds
                    duration = ms2 - ms1;
                    int minute = (int) duration / (1000 * 60);
                    int hours = (int) duration / (1000 * 60 * 60);
                    if (6 <= minute && hours <= 3) {
                        checkWithServerTime(jsonObject);
                        //createslot(jsonObject);
                    } else {
                        showSnackBar("A schedule cannot be less than 6 min and more than 3 hours", Snackbar.LENGTH_LONG);
                    }
                } else {
                    showSnackBar("Start time Cannot be greater than end time", Snackbar.LENGTH_LONG);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                                try {
                                    Date serverDate = format.parse(serverTime.currentTime);
                                    Date startDate = format.parse(from_formattedDate);
                                    long diff = DateUtil.getDateDiff(format,from_formattedDate,serverTime.currentTime);
                                    if (diff > 1){
                                        Common.getInstance().cusToast(activity(), "Please Change Time");
                                    }else {
                                        createslot(jsonObject);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
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
        todate = sdf.format(toCalender.getTime());
        try {
            Date date1 = sdf.parse(startdate);
            Date date2 = sdf.parse(todate);
            if (date1.compareTo(date2) < 0) {
                textView_To_Time.setText("");
                timeTo = 0;
            } else if (todate.equals(startdate)) {
                textView_To_Time.setText("");
                timeTo = 0;
            } else {
                showSnackBar("Please select other date", 0);
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        textView_To_Date.setText(sdf.format(toCalender.getTime()));
        dateTo = 1;

    }


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";//"dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        textView_From_Date.setText(sdf.format(myCalendar.getTime()));
        cleanSlot();
        startdate = sdf.format(myCalendar.getTime());
    }


    private void gettotime() {
        Calendar now = Calendar.getInstance();
        final MyTimePickerDialog mTimePicker = new MyTimePickerDialog(activity(), new MyTimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(com.ikovac.timepickerwithseconds.TimePicker view, int hourOfDay, int minute, int seconds) {
                textView_To_Time.setText(String.format("%02d", hourOfDay) +
                        ":" + String.format("%02d", minute) +
                        ":" + String.format("%02d", seconds));
                if (startdate.isEmpty() && startdate.equalsIgnoreCase("")) {
                    return;
                }
                if (todate.equals(startdate)) {
                    int time1 = Integer.parseInt(textView_From_Time.getText().toString().trim().replace(":", ""));
                    int time2 = Integer.parseInt(textView_To_Time.getText().toString().trim().replace(":", ""));
                    if (time2 - time1 >= 200) {
                        timeTo = 1;
                    } else {
                        textView_To_Time.setText("");
                        showSnackBar("A schedule cannot be less than 6 min and more than 3 hours", 0);
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
                        textView_From_Time.setText(String.format("%02d", hourOfDay) +
                                ":" + String.format("%02d", minute) +
                                ":" + String.format("%02d", seconds));
                        textView_To_Date.setText("");
                        textView_To_Time.setText("");
                        dateTo = 0;
                        timeTo = 0;
                        timeFrom = 1;
                    } else {
                        showSnackBar("Please select valid Time", 0);
                    }
                } else {
                    textView_From_Time.setText(String.format("%02d", hourOfDay) +
                            ":" + String.format("%02d", minute) +
                            ":" + String.format("%02d", seconds));
                    textView_To_Date.setText("");
                    textView_To_Time.setText("");
                    dateTo = 0;
                    timeTo = 0;
                    timeFrom = 1;
                }
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), true);
        mTimePicker.show();

    }

    private void createslot(JSONObject jsonObject) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.POST(ApiClient.CREATE_SHEDULE, RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()));
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.CREATE_SHEDULE,
                true, iApiListener, true));
    }


    public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            sec = c.get(Calendar.SECOND);


            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

            textView_From_Time.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));

        }
    }


    public void cleanSlot() {
        textView_To_Date.setText("");
        textView_From_Time.setText("");
        textView_To_Time.setText("");
        dateFrom = 1;
        dateTo = 0;
        timeFrom = 0;
        timeTo = 0;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCOUNTS_CAL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(activity(), "Calender permission Access", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity(), "Calender permission denied", Toast.LENGTH_LONG).show();
                }
                break;
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

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_CONTRACTS)) {
            try {
                JSONArray jsonArray = new JSONArray(new Gson().toJson(apiResponseModel.data));
                JSONObject jsonFanObject = jsonArray.getJSONObject(0);
                if (jsonFanObject.getString("serviceCredits") != null && !jsonFanObject.getString("serviceCredits").isEmpty()) {
                    chargeCreditstxt.setText(jsonFanObject.getString("serviceCredits") + " Credits");
                    seekbarvalue = String.valueOf(jsonFanObject.getString("serviceCredits") + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals(ApiClient.CREATE_SHEDULE)) {
            Toast.makeText(activity(), "Schedule created successfully ", Toast.LENGTH_SHORT).show();
            if (activity() instanceof HelperActivity) {
                ((HelperActivity) activity()).slotCreateSuccess();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_CONTRACTS)) {
            //
        } else if (condition.equals(ApiClient.CREATE_SHEDULE)) {

        }
    }

    @Override
    public void doPositiveAction() {
        activity().onBackPressed();
    }

    @Override
    public void doNegativeAction() {

    }
}
