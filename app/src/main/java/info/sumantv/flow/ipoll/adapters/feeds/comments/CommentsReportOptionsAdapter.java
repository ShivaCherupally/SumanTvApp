package info.sumantv.flow.ipoll.adapters.feeds.comments;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.appmanagers.feed.models.CommentReportOption;

import info.sumantv.flow.R;
import info.sumantv.flow.ipoll.interfaces.other.ICommentsReportDialog;
import info.sumantv.flow.userflow.Util.Common;

import java.util.List;

public class CommentsReportOptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CommentReportOption> commentReportOptionArrayList;
    private Context context;
    private Integer selectedIndex;
    ICommentsReportDialog iCommentsReportDialog;

    public CommentsReportOptionsAdapter(List<CommentReportOption> commentReportOptionArrayList, Context context, ICommentsReportDialog iCommentsReportDialog) {
        this.commentReportOptionArrayList = commentReportOptionArrayList;
        this.context = context;
        this.iCommentsReportDialog = iCommentsReportDialog;
        selectedIndex = -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.item_comments_report_option;
        return new AdapterHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterHolder) {
            AdapterHolder viewHolder = (AdapterHolder) holder;
            if (!Common.getInstance().IsNull(commentReportOptionArrayList.get(position).feedbackItem)) {
                viewHolder.tvUserName.setText(commentReportOptionArrayList.get(position).feedbackItem);
            }
            if (selectedIndex > -1 && selectedIndex == position) {
                viewHolder.ivRadioBtn.setImageResource(R.drawable.ic_radio_btn_on);
            } else {
                viewHolder.ivRadioBtn.setImageResource(R.drawable.ic_radio_btn_off);
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeSelection(position);
                }
            });
        }
    }

    private void changeSelection(Integer position) {
        selectedIndex = position;
        if (commentReportOptionArrayList.get(position).feedbackItem.equalsIgnoreCase("Others")) {
            iCommentsReportDialog.enableRemarkEditText(true);
        } else {
            iCommentsReportDialog.enableRemarkEditText(false);
        }
        notifyDataSetChanged();
    }

    public Integer getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    public int getItemCount() {
        return commentReportOptionArrayList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvUserName)
        TextView tvUserName;
        @BindView(R.id.ivRadioBtn)
        ImageView ivRadioBtn;

        public AdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
