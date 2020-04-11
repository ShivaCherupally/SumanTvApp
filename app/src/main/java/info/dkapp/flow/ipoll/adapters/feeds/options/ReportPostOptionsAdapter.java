package info.dkapp.flow.ipoll.adapters.feeds.options;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.appmanagers.feed.models.CommentReportOption;

import info.dkapp.flow.R;
import info.dkapp.flow.ipoll.interfaces.feeds.options.IReportsPostReaonDialog;
import info.dkapp.flow.userflow.Util.Common;

public class ReportPostOptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CommentReportOption> commentReportOptionArrayList;
    private Context context;
    private Integer selectedIndex;
    IReportsPostReaonDialog iReportsPostReaonDialog;

    public ReportPostOptionsAdapter(List<CommentReportOption> commentReportOptionArrayList, Context contextL, IReportsPostReaonDialog iReportsPostReaonDialog) {
        this.commentReportOptionArrayList = commentReportOptionArrayList;
        this.context = contextL;
        this.iReportsPostReaonDialog = iReportsPostReaonDialog;
        selectedIndex = -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.item_report_post_option;
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
//        notifyDataSetChanged();

//        int updateIndex = 3;
//        data.set(updateIndex, newValue);
        notifyItemChanged(position);



        iReportsPostReaonDialog.reportPost(commentReportOptionArrayList.get(position)._id);
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
