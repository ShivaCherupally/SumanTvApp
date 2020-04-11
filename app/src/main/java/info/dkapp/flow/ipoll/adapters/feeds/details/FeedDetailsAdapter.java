package info.dkapp.flow.ipoll.adapters.feeds.details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import info.dkapp.flow.appmanagers.feed.models.Feed;
import info.dkapp.flow.appmanagers.feed.models.Media;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.ipoll.interfaces.feeds.details.IFeedDetailsAdapter;
import info.dkapp.flow.ipoll.viewholders.FeedDetailViewHolder;
import info.dkapp.flow.ipoll.viewholders.FeedViewHolder;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;

/**
 * Created by User on 03-08-2018.
 **/

public class FeedDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Feed feed;
    List<Media> mediaList;
    public Context context;
    public IFeedDetailsAdapter iFeedDetailsAdapter;
    String description = "";

    public FeedDetailsAdapter(Context context, Feed feed, List<Media> mediaList, IFeedDetailsAdapter iFeedDetailsAdapter) {

        if (context == null) return;

        this.feed = feed;
        this.mediaList = mediaList;
        this.context = context;
        this.iFeedDetailsAdapter = iFeedDetailsAdapter;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new FeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ipoll_feed_item, parent, false));
        } else {
            return new FeedDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ipoll_feed_detail_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bind(holder, position);
    }

    private void bind(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FeedViewHolder) {
            FeedViewHolder feedViewHolder = (FeedViewHolder) holder;
            // header
            feedViewHolder.imgUser.setImageURI(Uri.parse(Constants.MEDIA_BASE_URL + feed.feedMemberDetails.avtar_imgPath));
            feedViewHolder.tvUserName.setText(Utility.getCamelCase(feed.feedMemberDetails.firstName + " " + feed.feedMemberDetails.lastName));
            feedViewHolder.tvTimeAgo.setText(Utility.makeDateToAgo(feed.createdDate));
            feedViewHolder.tvLocation.setText(feed.location);
            feedViewHolder.tvAddComment.setVisibility(View.GONE);

            if (feed.feedMemberDetails.profession != null && !feed.feedMemberDetails.profession.isEmpty()) {
                feedViewHolder.tvProfession.setText(Character.toUpperCase(feed.feedMemberDetails.profession.charAt(0)) + feed.feedMemberDetails.profession.substring(1));
                if (feed.feedMemberDetails.category != null && !feed.feedMemberDetails.category.isEmpty()) {
                    String professionAndCategory = feed.feedMemberDetails.category + ", " + feedViewHolder.tvProfession.getText().toString();
                    feedViewHolder.tvProfession.setText(professionAndCategory);
                }
            }


            // collage
            if (feed.mediaList != null && feed.mediaList.size() > 0) {
                if (feed.mediaList.size() == 1) {
                    feedViewHolder.collageView.setVisibility(View.VISIBLE);
                    feedViewHolder.collageView.addMedia(feed.mediaList, Utility.getIPollCollageController(feed.mediaList), Utility.getScreenWidth(context));
                } else {
                    feedViewHolder.collageView.setVisibility(View.GONE);
                }
            } else {
                feedViewHolder.collageView.setVisibility(View.GONE);
            }

            feedViewHolder.llShare.setOnClickListener(v -> {
                    iFeedDetailsAdapter.shareToOther(feed, position);
            });

            // content
            feedViewHolder.llMediaContentTextOnly.setVisibility(View.VISIBLE);
            feedViewHolder.llMediaContent.setVisibility(View.GONE);
            Common.getInstance().setFeedContent(feedViewHolder.tvFeedContentTextOnly, feedViewHolder.tvFeedDescriptionTextOnly,
                    feedViewHolder.view_moreTextOnly, position, feed, null);
            // footer
            Common.getInstance().setFeedLikeUnLike(feedViewHolder, feed, false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(
                    0,
                    Utility.dpSize(context, 0),
                    Utility.dpSize(context, 0),
                    Utility.dpSize(context, 3)
            );
            //feedViewHolder.itemView.setLayoutParams(params);

            feedViewHolder.llLike.setOnClickListener(v -> {
                    iFeedDetailsAdapter.likeAction(feed, position);
            });

            feedViewHolder.tvCommentCount.setOnClickListener(v -> {
                    iFeedDetailsAdapter.userComments(feed, position);
            });

            feedViewHolder.llComment.setOnClickListener(v -> {
                    iFeedDetailsAdapter.userComments(feed, position);
            });

            feedViewHolder.tvLikeCount.setOnClickListener(v -> {
                    iFeedDetailsAdapter.userLikes(feed, position);
            });

            feedViewHolder.collageView.setCollageItemClickListener(mediaPosition -> {
                if (mediaList.get(mediaPosition).type.equals(Constants.MEDIA_TYPE_VIDEO)) {
                    iFeedDetailsAdapter.openVideoPlayer(feed, Utility.addVideoUrl(mediaList.get(mediaPosition)), mediaList.get(mediaPosition), position);
                } else {
                    iFeedDetailsAdapter.openPhotosMedia(feed, mediaList, mediaPosition);
                }
            });

            feedViewHolder.itemView.setOnClickListener(v -> {

            });


            feedViewHolder.llCelebProfile.setOnClickListener(v -> {

                boolean isSelf = false;
                if (SessionManager.userLogin.userId != null && !SessionManager.userLogin.userId.isEmpty()) {
                    if (feed.memberId.equals(SessionManager.userLogin.userId)) {
                        isSelf = true;
                    }
                }

                iFeedDetailsAdapter.viewCelebDetails(feed, position, isSelf);
            });
            if (feed.memberId != null && !feed.memberId.isEmpty()) {
                if (SessionManager.userLogin.userId != null
                        && !SessionManager.userLogin.userId.isEmpty()) {
                    if (feed.memberId.equals(SessionManager.userLogin.userId)) {
                        feedViewHolder.imgOptions.setVisibility(View.VISIBLE);
                        feedViewHolder.LLKonect.setVisibility(View.GONE);
                    } else {
                        feedViewHolder.imgOptions.setVisibility(View.GONE);
                        feedViewHolder.LLKonect.setVisibility(View.VISIBLE);
                    }
                } else {
                    feedViewHolder.imgOptions.setVisibility(View.GONE);
                    feedViewHolder.LLKonect.setVisibility(View.GONE);
                }
            } else {
                feedViewHolder.imgOptions.setVisibility(View.GONE);
                feedViewHolder.LLKonect.setVisibility(View.GONE);
            }

            //
            feedViewHolder.imgOptions.setVisibility(View.GONE);

            feedViewHolder.imgOptions.setOnClickListener(v -> {
                iFeedDetailsAdapter.openFeedOptions(feed, position);
            });
            feedViewHolder.LLKonect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iFeedDetailsAdapter.openKonect(feed, position);
                }
            });
        } else if (holder instanceof FeedDetailViewHolder) {
            FeedDetailViewHolder feedDetailViewHolder = (FeedDetailViewHolder) holder;

            // header
            feedDetailViewHolder.imgFeed.setLayoutParams(Utility.getLayoutParams(1 / mediaList.get(position - 1).ratio, Utility.getScreenWidth(context)));
            feedDetailViewHolder.imgFeed.setTag(R.id.imgFeed, Constants.MEDIA_BASE_URL + mediaList.get(position - 1).url);
            Common.getInstance().setGIFImageToFresco(feedDetailViewHolder.imgFeed, null, mediaList.get(position - 1), true);
            if (mediaList.get(position - 1).type != null && mediaList.get(position - 1).type.equals(Constants.MEDIA_TYPE_GIF)) {
                Common.getInstance().setGIFImageToFresco(feedDetailViewHolder.imgFeed, null, mediaList.get(position - 1), !SessionManager.getInstance().getKeyValue(SessionManager.KEY_AUTO_PLAY_GIFS, false));
            }
            feedDetailViewHolder.tvGIFLabel.setVisibility(View.GONE);
            //feedDetailViewHolder.imgFeed.setImageURI(Utility.addMediaThumbnailUrl(mediaList.get(position - 1)));
            feedDetailViewHolder.imgMediaType.setVisibility(mediaList.get(position - 1).type != null && mediaList.get(position - 1).type.equals(Constants.MEDIA_TYPE_VIDEO) ? View.VISIBLE : View.GONE);
            if (mediaList.get(position - 1).type != null && mediaList.get(position - 1).type.equals(Constants.MEDIA_TYPE_GIF)) {
                if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_AUTO_PLAY_GIFS, false)) {
                    feedDetailViewHolder.tvGIFLabel.setVisibility(View.VISIBLE);
                } else {
                    feedDetailViewHolder.imgMediaType.setImageResource(R.drawable.gif_icon);
                    feedDetailViewHolder.imgMediaType.setVisibility(View.VISIBLE);
                }
            }
            // content
            feedDetailViewHolder.tvFeedCaption.setVisibility(View.VISIBLE);

            if (mediaList.get(position - 1).caption != null && mediaList.get(position - 1).caption.length() > 0) {
                feedDetailViewHolder.tvFeedCaption.setVisibility(View.VISIBLE);
                feedDetailViewHolder.tvFeedCaption.setText("" + mediaList.get(position - 1).caption);
            } else {
                feedDetailViewHolder.tvFeedCaption.setVisibility(View.GONE);
            }

            // footer
            setMediaLikeUnLike(feedDetailViewHolder, position);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(
                    Utility.dpSize(context, 0),
                    Utility.dpSize(context, 0),
                    Utility.dpSize(context, 0),
                    Utility.dpSize(context, 3)
            );
            // feedDetailViewHolder.itemView.setLayoutParams(params);

            feedDetailViewHolder.llLike.setOnClickListener(v -> {
                    iFeedDetailsAdapter.likeActionMedia(mediaList.get(position - 1), position);
            });

            feedDetailViewHolder.llComment.setOnClickListener(v ->
            {
                    iFeedDetailsAdapter.userCommentsMedia(mediaList.get(position - 1), position);
            });

            feedDetailViewHolder.tvCommentCount.setOnClickListener(v -> {
                    iFeedDetailsAdapter.userCommentsMedia(mediaList.get(position - 1), position);
            });

            feedDetailViewHolder.tvLikeCount.setOnClickListener(v -> {
                    iFeedDetailsAdapter.userLikesMedia(mediaList.get(position - 1), position);
            });

            feedDetailViewHolder.llShare.setOnClickListener(v -> {
                    iFeedDetailsAdapter.shareToOther(mediaList.get(position - 1), position);
            });

            feedDetailViewHolder.imgFeed.setOnClickListener(v -> {
                if (mediaList.get(position - 1).type.equals(Constants.MEDIA_TYPE_VIDEO)) {
                    iFeedDetailsAdapter.openVideoPlayer(feed, Utility.addVideoUrl(mediaList.get(position - 1)), mediaList.get(position - 1), position);
                } else {
                    iFeedDetailsAdapter.openPhotosMedia(feed, mediaList, position - 1);
                }
            });

        } else {

        }
    }

    @SuppressLint("SetTextI18n")
    public void setMediaLikeUnLike(FeedDetailViewHolder feedDetailViewHolder, int position) {
        feedDetailViewHolder.imgLike.setImageResource(Common.getInstance().getBooleanFromInt(mediaList.get(position - 1).isLike) ? R.drawable.ic_like_fill : R.drawable.ic_like_stroke);
        feedDetailViewHolder.progressBar.setVisibility(mediaList.get(position - 1).isBusyLike ? View.VISIBLE : View.GONE);
        feedDetailViewHolder.imgLike.setVisibility(mediaList.get(position - 1).isBusyLike ? View.GONE : View.VISIBLE);
        feedDetailViewHolder.llLikeComment.setVisibility(View.VISIBLE);
        feedDetailViewHolder.tvDot.setVisibility(View.GONE);
        if (mediaList.get(position - 1).numberOfLikes > 0) {
            if (mediaList.get(position - 1).numberOfLikes == 1) {
                feedDetailViewHolder.tvLikeCount.setText("" + mediaList.get(position - 1).numberOfLikes + " like");
            } else {
                feedDetailViewHolder.tvLikeCount.setText("" + mediaList.get(position - 1).numberOfLikes + " likes");
            }
            feedDetailViewHolder.tvLikeCount.setVisibility(View.VISIBLE);
        } else {
            feedDetailViewHolder.tvLikeCount.setText("");
            feedDetailViewHolder.tvLikeCount.setVisibility(View.VISIBLE);
        }
        if (mediaList.get(position - 1).numberOfComments > 0) {
            if (mediaList.get(position - 1).numberOfComments == 1) {
                feedDetailViewHolder.tvCommentCount.setText("" + mediaList.get(position - 1).numberOfComments + " comment");
            } else {
                feedDetailViewHolder.tvCommentCount.setText("" + mediaList.get(position - 1).numberOfComments + " comments");
            }
            feedDetailViewHolder.tvCommentCount.setVisibility(View.VISIBLE);
        } else {
            feedDetailViewHolder.tvCommentCount.setText("");
            feedDetailViewHolder.tvCommentCount.setVisibility(View.VISIBLE);
        }
        if (mediaList.get(position - 1).numberOfLikes <= 0 && mediaList.get(position - 1).numberOfComments <= 0) {
            feedDetailViewHolder.llLikeComment.setVisibility(View.GONE);
        } else if (mediaList.get(position - 1).numberOfLikes > 0 && mediaList.get(position - 1).numberOfComments > 0) {
            feedDetailViewHolder.tvDot.setVisibility(View.VISIBLE);
        }
    }

    public void update(Feed feed, int position) {
        this.feed = feed;
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return feed != null ? feed.mediaList != null ? mediaList.size() == 1 ? 1 : mediaList.size() + 1 : 1 : 0;
    }

}
