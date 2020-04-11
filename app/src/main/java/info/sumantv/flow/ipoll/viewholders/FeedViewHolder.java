package info.sumantv.flow.ipoll.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.bottommenu.customviews.images.CollageView;

import info.sumantv.flow.R;

/**
 * Created by User on 03-08-2018.
 **/

public class FeedViewHolder extends RecyclerView.ViewHolder {

    // feed header
    @BindView(R.id.imgUser)
    public SimpleDraweeView imgUser;

    @BindView(R.id.tvLocation)
    public TextView tvLocation;

    @BindView(R.id.tvTimeAgo)
    public TextView tvTimeAgo;

    @BindView(R.id.tvUserName)
    public TextView tvUserName;

    @BindView(R.id.imgOptions)
    public ImageView imgOptions;

    @BindView(R.id.llCelebProfile)
    public LinearLayout llCelebProfile;

    @BindView(R.id.LLKonect)
    public LinearLayout LLKonect;

    // feed collage
    @BindView(R.id.collageView)
    public CollageView collageView;

    @BindView(R.id.viewPager)
    public ViewPager viewPager;

    // feed content
    @BindView(R.id.tvFeedContent)
    public TextView tvFeedContent;

    @BindView(R.id.tvFeedDescription)
    public TextView tvFeedDescription;

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

    @BindView(R.id.rlFeed)
    public RelativeLayout rlFeed;

    @BindView(R.id.llUpdate)
    public LinearLayout llUpdate;

    @BindView(R.id.llMediaAction)
    public LinearLayout llMediaAction;

    @BindView(R.id.view_more)
    public TextView view_more;

    @BindView(R.id.rlParentViewPager)
    public RelativeLayout rlParentViewPager;

    @BindView(R.id.tvPagerCount)
    public TextView tvPagerCount;

    @BindView(R.id.llParent)
    public LinearLayout llParent;

    @BindView(R.id.tvProfession)
    public TextView tvProfession;

    @BindView(R.id.tvDot)
    public TextView tvDot;

    @BindView(R.id.llLikeComment)
    public LinearLayout llLikeComment;

    @BindView(R.id.llMediaContent)
    public LinearLayout llMediaContent;

    @BindView(R.id.llMediaContentTextOnly)
    public LinearLayout llMediaContentTextOnly;

    @BindView(R.id.tvFeedContentTextOnly)
    public TextView tvFeedContentTextOnly;

    @BindView(R.id.tvFeedDescriptionTextOnly)
    public TextView tvFeedDescriptionTextOnly;

    @BindView(R.id.view_moreTextOnly)
    public TextView view_moreTextOnly;

    @BindView(R.id.llHidePost)
    public LinearLayout llHidePost;

    @BindView(R.id.llHeader)
    public LinearLayout llHeader;

    @BindView(R.id.llMedia)
    public LinearLayout llMedia;

    @BindView(R.id.undobtn)
    public Button undobtn;

    @BindView(R.id.tvAddComment)
    public TextView tvAddComment;

    @BindView(R.id.llLikeCommentIconsParent)
    public LinearLayout llLikeCommentIconsParent;

    @BindView(R.id.ivLikeAnimation)
    public SimpleDraweeView ivLikeAnimation;

    public FeedViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
