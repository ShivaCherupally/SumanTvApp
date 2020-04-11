package info.sumantv.flow.ipoll.adapters.feeds.comments;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import info.sumantv.flow.appmanagers.feed.models.Comment;
import info.sumantv.flow.appmanagers.feed.models.Feed;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.viewholders.SkeletonViewHolder;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.ipoll.interfaces.feeds.comments.ICommentsAdapter;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Utility;

import java.util.List;

/**
 * Created by User on 03-08-2018.
 **/

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Feed feed;
    List<Comment> list;
    Context context;
    public RController rController;
    ICommentsAdapter iCommentsAdapter;
    private int position;

    public CommentsAdapter(Feed feed, List<Comment> list, Context context, RController rController, ICommentsAdapter iCommentsAdapter) {

        if (context == null)
            return;

        this.list = list;
        this.feed = feed;
        this.context = context;
        this.rController = rController;
        this.iCommentsAdapter = iCommentsAdapter;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (rController == RController.LOADING) {
            int layout = R.layout.ipoll_skeleton_comment_item;
            return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        } else {
            int layout = R.layout.ipoll_comment_item;
            return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (rController != RController.LOADING) {
            bind(position, holder);
        }
    }

    private void bind(int position, RecyclerView.ViewHolder holder) {
        if (holder instanceof CommentViewHolder) {
            CommentViewHolder commentViewHolder = (CommentViewHolder) holder;

//            commentViewHolder.imgUser.setImageURI(Uri.parse(list.get(position).profile != null ? Constants.MEDIA_BASE_URL + list.get(position).profile.profilePic : Constants.AVATAR_IMAGE));
                if (list.get(position) == null){
                    return;
                }
            if (list.get(position).profile.profilePic != null && !list.get(position).profile.profilePic.isEmpty()) {
                commentViewHolder.imgUser.setImageURI(Uri.parse(list.get(position).profile != null ? Constants.MEDIA_BASE_URL + list.get(position).profile.profilePic : Constants.AVATAR_IMAGE));
            } else {
//                likeViewHolder.imgUser.setImageURI(Uri.parse(list.get(position).profile != null ? Constants.MEDIA_BASE_URL + list.get(position).profile.profilePic : Constants.AVATAR_IMAGE));
                Glide.with(context).load(R.drawable.logo_with_text).into(commentViewHolder.imgUser);
            }

            commentViewHolder.tvUserName.setText(Utility.getCamelCase(list.get(position).profile != null ? list.get(position).profile.userName : Constants.APP_NAME));
            commentViewHolder.tvTimeAgo.setText(Utility.makeDateToAgo(list.get(position).timeAgo));
            commentViewHolder.tvComment.setText(list.get(position).comment);
            commentViewHolder.online_profileCheck.setVisibility(list.get(position).profile.isCeleb ? View.VISIBLE : View.GONE);

            commentViewHolder.imgUser.setOnClickListener(v -> {
//                if (list.get(position).profile.isCeleb) {
                if (list.get(position).profile.id.equals(SessionManager.userLogin.userId)) {
                    iCommentsAdapter.selfProfile(list.get(position).profile);
                } else {
                    iCommentsAdapter.celebUserProfile(list.get(position).profile);
                }
//                } else {
//                    iCommentsAdapter.celebUserProfile(list.get(position).profile);
//                }
            });

            commentViewHolder.llHeader.setOnClickListener(v -> {
                //QA bug related hide
                /*if (list.get(position).profile.id.equals(SessionManager.userLogin.userId)) {
                    iCommentsAdapter.selfProfile(list.get(position).profile);
                } else {
                    iCommentsAdapter.celebUserProfile(list.get(position).profile);
                }*/

            });
            String userID = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
            if (list.get(position).profile.id.equalsIgnoreCase(userID)) {
                commentViewHolder.tvCommentEdit.setVisibility(View.VISIBLE);
                commentViewHolder.tvCommentDelete.setVisibility(View.VISIBLE);
                commentViewHolder.tvCommentReport.setVisibility(View.GONE);
            } else if (feed != null && userID.equalsIgnoreCase(feed.memberId)) {
                commentViewHolder.tvCommentEdit.setVisibility(View.GONE);
                commentViewHolder.tvCommentDelete.setVisibility(View.VISIBLE);
                commentViewHolder.tvCommentReport.setVisibility(View.VISIBLE);
            } else {
                commentViewHolder.tvCommentEdit.setVisibility(View.GONE);
                commentViewHolder.tvCommentDelete.setVisibility(View.GONE);
                commentViewHolder.tvCommentReport.setVisibility(View.VISIBLE);
            }

            commentViewHolder.tvCommentEdit.setOnClickListener(v -> {

                iCommentsAdapter.editComment(list.get(position), position);

            });

            commentViewHolder.tvCommentDelete.setOnClickListener(v -> {
                try {
                    iCommentsAdapter.deleteComments(list.get(position), position);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            commentViewHolder.tvCommentReport.setOnClickListener(v -> {
                try {
                    iCommentsAdapter.reportComments(list.get(position), position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            /*commentViewHolder.llRow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setPosition(position);
                    return false;
                }
            });*/

        }
    }

    @Override
    public int getItemCount() {
        return rController == RController.LOADING ? 10 : list.size();
    }

    public void loadMore(List<Object> subList) {
        if (rController == RController.LOADING || rController == RController.DONE || rController == RController.NETWORK_ERROR_RETRY)
            return;

        try {
            List<Comment> appendList = (List<Comment>) (List<?>) subList;
            int positionStart = list.size() + 1;
            list.addAll(appendList);
            notifyItemRangeInserted(positionStart, appendList.size());
            rController = RController.LOADED;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof CommentViewHolder) {
            CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
            commentViewHolder.llRow.setOnLongClickListener(null);
        }
        super.onViewRecycled(holder);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder/* implements View.OnCreateContextMenuListener*/ {
        @BindView(R.id.imgUser)
        SimpleDraweeView imgUser;

        @BindView(R.id.tvUserName)
        TextView tvUserName;

        @BindView(R.id.tvTimeAgo)
        TextView tvTimeAgo;

        @BindView(R.id.tvComment)
        TextView tvComment;

        @BindView(R.id.llHeader)
        LinearLayout llHeader;

        @BindView(R.id.online_profileCheck)
        ImageView online_profileCheck;

        @BindView(R.id.tvCommentEdit)
        TextView tvCommentEdit;

        @BindView(R.id.tvCommentDelete)
        TextView tvCommentDelete;

        @BindView(R.id.tvCommentReport)
        TextView tvCommentReport;

        @BindView(R.id.llRow)
        LinearLayout llRow;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //llRow.setOnCreateContextMenuListener(this);
        }
        /*@Override
        public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
            try {
                menu.add(Menu.NONE, 1, Menu.NONE, "Feedback/Report");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }
}
