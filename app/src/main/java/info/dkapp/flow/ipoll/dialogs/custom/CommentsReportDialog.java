package info.dkapp.flow.ipoll.dialogs.custom;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.appmanagers.feed.models.Comment;
import info.dkapp.flow.appmanagers.feed.models.CommentReportOption;
import info.dkapp.flow.bottommenu.generic.EmptyDataAdapter;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.ipoll.adapters.feeds.comments.CommentsReportOptionsAdapter;
import info.dkapp.flow.ipoll.interfaces.feeds.comments.ICommentsAdapter;
import info.dkapp.flow.ipoll.interfaces.other.ICommentsReportDialog;
import info.dkapp.flow.userflow.Util.Common;

import java.util.List;

public class CommentsReportDialog extends BottomSheetDialogFragment implements ICommentsReportDialog {
    @BindView(R.id.rvList)
    RecyclerView rvList;
    @BindView(R.id.ivClose)
    ImageButton ivClose;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.etRemarks)
    EditText etRemarks;

    Context context;
    List<CommentReportOption> commentReportOptionList;
    CommentsReportOptionsAdapter adapter;
    ICommentsAdapter iCommentsAdapter;
    ICommentsReportDialog iCommentsReportDialog;
    Comment comments;

    public CommentsReportDialog() {
    }

    public void setData(List<CommentReportOption> commentReportOptionList, Comment comments, ICommentsAdapter iCommentsAdapter) {
        this.commentReportOptionList = commentReportOptionList;
        this.iCommentsAdapter = iCommentsAdapter;
        this.comments = comments;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comments_report_dialog, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        iCommentsReportDialog = this;
        //
        setup();
        setAdapter();
        return view;
    }

    private void setup() {
        etRemarks.setVisibility(View.GONE);
        rvList.setLayoutManager(new LinearLayoutManager(context));
        //
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (adapter == null || adapter.getSelectedIndex() <= -1) {
                        Common.getInstance().cusToast(context, "Please select your option");
                    } else if (etRemarks.getVisibility() == View.VISIBLE && etRemarks.getText().toString().trim().isEmpty()) {
                        Common.getInstance().cusToast(context, "Please enter your remarks");
                    } else {
                        dismiss();
                        iCommentsAdapter.reportSubmit(comments, commentReportOptionList.get(adapter.getSelectedIndex()),
                                etRemarks.getText().toString().trim());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setAdapter() {
        try {
            if (commentReportOptionList != null && commentReportOptionList.size() > 0) {
                adapter = new CommentsReportOptionsAdapter(commentReportOptionList, context, iCommentsReportDialog);
                rvList.setAdapter(adapter);
            } else {
                rvList.setAdapter(new EmptyDataAdapter(context, Constants.NO_DATA_AVAILABLE, R.drawable.ic_nodata, 2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enableRemarkEditText(Boolean enable) {
        if (enable) {
            etRemarks.setText("");
            etRemarks.setVisibility(View.VISIBLE);
        } else {
            etRemarks.setVisibility(View.GONE);
        }
    }
}
