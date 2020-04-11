package info.sumantv.flow.ipoll.dialogs.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.appmanagers.feed.models.CommentReportOption;
import info.sumantv.flow.appmanagers.feed.models.Feed;
import info.sumantv.flow.appmanagers.feed.models.PostReportOptionsParams;
import info.sumantv.flow.bottommenu.generic.EmptyDataAdapter;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.ipoll.adapters.feeds.options.ReportPostOptionsAdapter;
import info.sumantv.flow.ipoll.interfaces.feeds.options.IReportsPostReaonDialog;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import retrofit2.Call;

/**
 * Created by User on 17-07-2018.
 **/

public class ReportPostReasonDailog extends Dialog implements IReportsPostReaonDialog, IApiListener {

    Context context;
    IReportsPostReaonDialog iReportsPostReaonDialog;

    List<CommentReportOption> commentReportOptionList;
    ReportPostOptionsAdapter adapter;

    @BindView(R.id.rvList)
    RecyclerView rvList;


    ApiInterface apiInterface;
    IApiListener iApiListener;
    Feed feed;


    public ReportPostReasonDailog(Context context, Feed feed, List<CommentReportOption> commentReportOptionList) {
        super(context);
        this.context = context;
        this.feed = feed;
        this.commentReportOptionList = commentReportOptionList;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.report_post_dialog);

        ButterKnife.bind(this);

        iReportsPostReaonDialog = this;

        iApiListener = this;

        setAdapter();
    }

    private void setAdapter() {
        try {
            rvList.setLayoutManager(new LinearLayoutManager(context));
            if (commentReportOptionList != null && commentReportOptionList.size() > 0) {
                adapter = new ReportPostOptionsAdapter(commentReportOptionList, context, iReportsPostReaonDialog);
                rvList.setAdapter(adapter);
            } else {
                rvList.setAdapter(new EmptyDataAdapter(context, Constants.NO_DATA_AVAILABLE, R.drawable.ic_nodata, 2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void reportPost(String reportId) {

        PostReportOptionsParams postReportOptionsParams = new PostReportOptionsParams();
        postReportOptionsParams.memberId = SessionManager.userLogin.userId;
        if (feed.id != null && !feed.id.isEmpty()) {
            postReportOptionsParams.feedId = feed.id;
        }

        if (reportId != null && !reportId.isEmpty()) {
            postReportOptionsParams.reportReasonId = reportId;
        }


        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.reportFeedPost(postReportOptionsParams);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(context, call, Constants.FeedConstants.POST_REPORT_FEEDBACK_ON_POST_OPTIONS, true, iApiListener, true));


    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.FeedConstants.POST_REPORT_FEEDBACK_ON_POST_OPTIONS)) {
            try {
                if (apiResponseModel.success == 1) {
                    Toast toast = Toast.makeText(context,apiResponseModel.message, Toast.LENGTH_LONG);
                    ViewGroup group = (ViewGroup) toast.getView();
                    TextView messageTextView = (TextView) group.getChildAt(0);
                    messageTextView.setTextSize(18);
                    messageTextView.setTypeface(messageTextView.getTypeface(), Typeface.NORMAL);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.FeedConstants.POST_REPORT_FEEDBACK_ON_POST_OPTIONS)) {
            Toast.makeText(context, "Fail to report", Toast.LENGTH_SHORT).show();
            dismiss();
        }

    }
}
