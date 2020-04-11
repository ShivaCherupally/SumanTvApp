package info.sumantv.flow.ipoll.dialogs.custom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.appmanagers.feed.models.CommentReportOption;
import info.sumantv.flow.appmanagers.feed.models.Feed;
import info.sumantv.flow.bottommenu.activity.BottomMenuActivity;
import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.ipoll.fragments.feeds.FeedsFragment;
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

/**
 * Created by Uday Kumay Vurukonda on 23-Aug-19.
 * <p>
 * Mr.Psycho
 */
public class FeedOptionsDailog extends BottomSheetDialogFragment implements View.OnClickListener, IFragment, IApiListener {

    @BindView(R.id.subLLone)
    LinearLayout subLLone;

    @BindView(R.id.llHide)
    LinearLayout llHide;

    @BindView(R.id.llDelete)
    LinearLayout llDelete;

    @BindView(R.id.subLLtwo)
    LinearLayout subLLtwo;

    @BindView(R.id.llEditPost)
    LinearLayout llEditPost;

    @BindView(R.id.llDeletePost)
    LinearLayout llDeletePost;

    @BindView(R.id.llNotificationOnOff)
    LinearLayout llNotificationOnOff;

    @BindView(R.id.tvNotificationDetails)
    TextView tvNotificationDetails;

    @BindView(R.id.ivNotificationIcon)
    ImageView ivNotificationIcon;

    Context context;
    List<Feed> feedList;
    Feed feed;
    int position;
    boolean isNotiofication = false, updateValue = false;


    List<CommentReportOption> commentReportOptionList;

    ApiInterface apiInterface;
    IApiListener iApiListener;
    ReportPostReasonDailog reportPostReasonDailog;

    public FeedOptionsDailog() {
    }

    public void setData(Feed feed, int position, List<Feed> feedList) {
        this.feed = feed;
        this.position = position;
        this.feedList = feedList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.feed_option_dailog, null);
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        ButterKnife.bind(this, contentView);

        context = getContext();

        iApiListener = this;


        //Post Report or Hide Section
        subLLone.setOnClickListener(this::onClick);
        llHide.setOnClickListener(this::onClick);
        llDelete.setOnClickListener(this::onClick);
        llNotificationOnOff.setOnClickListener(this);

        //Post Delete  or Edit Section
        subLLtwo.setOnClickListener(this::onClick);
        llEditPost.setOnClickListener(this::onClick);
        llDeletePost.setOnClickListener(this::onClick);
        if (feed != null) {
            Log.d("feedNotification", feed.feedSettingsDetails +"");

            if (feed.feedSettingsDetails == 0) {
                isNotiofication = true;
            } else {
                isNotiofication = false;
            }
        }
        if (isNotiofication) {
            tvNotificationDetails.setText("Turn OFF notification for this celebrity");
            ivNotificationIcon.setImageDrawable(activity().getDrawable(R.drawable.ic_turn_off_notification));
        } else {
            tvNotificationDetails.setText("Turn ON notification for this celebrity");
            ivNotificationIcon.setImageDrawable(activity().getDrawable(R.drawable.ic_turn_on_notification));
        }
        if (feed.memberId != null && !feed.memberId.isEmpty()) {
            if (SessionManager.userLogin.userId != null && !SessionManager.userLogin.userId.isEmpty()) {
                if (feed.memberId.equals(SessionManager.userLogin.userId)) {
                    subLLone.setVisibility(View.GONE);
                    subLLtwo.setVisibility(View.VISIBLE);
                } else {
                    subLLone.setVisibility(View.VISIBLE);
                    subLLtwo.setVisibility(View.GONE);
                }
            } else {
                subLLone.setVisibility(View.VISIBLE);
                subLLtwo.setVisibility(View.GONE);
            }
        } else {
            subLLone.setVisibility(View.VISIBLE);
            subLLtwo.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llHide:
                try {
                    llHide.setBackgroundColor(activity().getResources().getColor(R.color.managerBackgroundColor));
                    llDelete.setBackgroundColor(activity().getResources().getColor(R.color.white));
                    /*if (FeedsFragment.getInstance() != null) {
                        ((HelperActivity) activity()).hideFeed(feed.id, position, true);
                    }else {
                        ((BottomMenuActivity) activity()).hideFeed(feed.id, position, true);
                    }*/
                    if (FeedsFragment.getInstance() != null) {
                        FeedsFragment.getInstance().hidePostFromFeeds(feed.id, position, true);
                    }

                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                dismiss();
                break;

            case R.id.llDelete:
                llHide.setBackgroundColor(activity().getResources().getColor(R.color.white));
                llDelete.setBackgroundColor(activity().getResources().getColor(R.color.managerBackgroundColor));
                if (commentReportOptionList != null && commentReportOptionList.size() == 0) {
                    openReportPostReasonDailog();
                } else {
                    getFeedReportOptions();
                }
                break;

            case R.id.llEditPost:
                    if (context instanceof BottomMenuActivity) {
                        ((BottomMenuActivity) context).editFeed(feed, position);
                    } else if (context instanceof HelperActivity) {
                        ((HelperActivity) context).editFeed(feed, position);
                    }
                dismiss();
                break;

            case R.id.llDeletePost:
                    Common.getInstance().deleteFeed(feed.id, position, 0, "");
                dismiss();
                break;
            case R.id.llNotificationOnOff:

                if (isNotiofication) {
                   /* tvNotificationDetails.setText("Turn OFF notification for this celebrity");
                    ivNotificationIcon.setImageDrawable(activity().getDrawable(R.drawable.ic_turn_off_notification));*/
                    notificationOnOFF(false);
                } else {
                    /*tvNotificationDetails.setText("Turn ON notification for this celebrity");
                    ivNotificationIcon.setImageDrawable(activity().getDrawable(R.drawable.ic_turn_on_notification));*/
                    notificationOnOFF(true);
                }
                dismiss();
                break;
        }

    }

    public void notificationOnOFF(boolean isOFF) {
        updateValue = isOFF;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("memberId", SessionManager.userLogin.userId);
            jsonObject.put("celebId", feed.feedMemberDetails._id);
            jsonObject.put("feedId", feed.id);
            jsonObject.put("isEnabled", isOFF);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("feedNotification", feed.feedSettingsDetails +"");

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Call<ApiResponseModel> call = apiInterface.POST(Constants.FeedConstants.NOTIFICSTION_ON_OFF, requestBody);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.NOTIFICSTION_ON_OFF, true, iApiListener, false));
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {

    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    public void openReportPostReasonDailog() {

        dismiss();
        reportPostReasonDailog = new ReportPostReasonDailog(context, feed, commentReportOptionList);
        reportPostReasonDailog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reportPostReasonDailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        reportPostReasonDailog.show();

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = (int) (metrics.widthPixels * 0.85);
        reportPostReasonDailog.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);


    }


    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.FeedConstants.GET_REPORT_POST_OPTIONS)) {
            try {
                Type type = new TypeToken<ArrayList<CommentReportOption>>() {
                }.getType();
                commentReportOptionList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                openReportPostReasonDailog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals(Constants.FeedConstants.NOTIFICSTION_ON_OFF)) {
            try {
                //Common.getInstance().cusToast(activity(), "Done");
                if (FeedsFragment.getInstance() != null) {
                    for (int i = 0; i < feedList.size(); i++) {
                        if (feedList.get(i).feedMemberDetails != null) {
                            if (feedList.get(i).feedMemberDetails._id.equals(feed.feedMemberDetails._id)) {
                              /*  if (feedList.get(i).feedSettingsDetails.size() != 0) {
                                    feedList.get(i).feedSettingsDetails.get(0).isEnabled = updateValue;
                                } else {
                                    List<FeedSettingsDetails> feedSettingsDetails = new ArrayList<>();
                                    feedList.get(i).feedSettingsDetails = feedSettingsDetails;
                                    feedList.get(i).feedSettingsDetails.get(0).isEnabled = updateValue;
                                }*/
                              if (updateValue){
                                  feedList.get(i).feedSettingsDetails = 0;
                              }else {
                                  feedList.get(i).feedSettingsDetails = 1;
                              }
                            }
                        }
                    }
                    FeedsFragment.getInstance().updateFeedList(feedList, position);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.FeedConstants.GET_REPORT_POST_OPTIONS)) {
            Common.getInstance().cusToast(activity(), "No data");
        } else if (condition.equals(Constants.FeedConstants.NOTIFICSTION_ON_OFF)) {
            Common.getInstance().cusToast(activity(), apiResponseModel.message);
        }
    }

    private void getFeedReportOptions() {

        if (!Common.checkInternetConnection(getActivity())) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getAllFeedbackItems(Constants.FeedConstants.GET_REPORT_POST_OPTIONS);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, Constants.FeedConstants.GET_REPORT_POST_OPTIONS,
                true, iApiListener, false));
    }
}
