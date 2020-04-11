package info.sumantv.flow.ipoll.viewholders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

import info.sumantv.flow.R;

/**
 * Created by User on 03-08-2018.
 **/

public class FeedDetailViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgFeed)
    public SimpleDraweeView imgFeed;

    @BindView(R.id.imgMediaType)
    public ImageView imgMediaType;

    @BindView(R.id.tvFeedCaption)
    public TextView tvFeedCaption;

    // feed caption
    @BindView(R.id.tvGIFLabel)
    public TextView tvGIFLabel;

    // feed bottom footer
    @BindView(R.id.imgLike)
    public ImageView imgLike;

    @BindView(R.id.imgShare)
    public ImageView imgShare;

    @BindView(R.id.tvLikeCount)
    public TextView tvLikeCount;

    @BindView(R.id.tvCommentCount)
    public TextView tvCommentCount;

    @BindView(R.id.llLike)
    public LinearLayout llLike;

    @BindView(R.id.llComment)
    public LinearLayout llComment;

    @BindView(R.id.llShare)
    public LinearLayout llShare;

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    @BindView(R.id.tvDot)
    public TextView tvDot;

    @BindView(R.id.llLikeComment)
    public LinearLayout llLikeComment;

    public FeedDetailViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
