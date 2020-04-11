package info.sumantv.flow.ipoll.adapters.feeds.likes;

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
import info.sumantv.flow.appmanagers.feed.models.Like;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.viewholders.SkeletonViewHolder;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.ipoll.interfaces.feeds.likes.ILikesAdapter;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.utils.Utility;

import java.util.List;

/**
 * Created by User on 03-08-2018.
 **/

public class LikesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Like> list;
    Context context;
    public RController rController;
    ILikesAdapter iLikesAdapter;

    public LikesAdapter(List<Like> list, Context context, RController rController, ILikesAdapter iLikesAdapter) {

        if (context == null) return;
        this.list = list;
        this.context = context;
        this.rController = rController;
        this.iLikesAdapter = iLikesAdapter;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (rController == RController.LOADING) {
            int layout = R.layout.ipoll_skeleton_like_item;
            return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        } else {
            int layout = R.layout.ipoll_like_item;
            return new LikeViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (rController != RController.LOADING) {
            bind(position, holder);
        } else {

        }
    }

    private void bind(int position, RecyclerView.ViewHolder holder) {
        if (holder instanceof LikeViewHolder) {
            LikeViewHolder likeViewHolder = (LikeViewHolder) holder;


            if (list.get(position).profile.profilePic != null && !list.get(position).profile.profilePic.isEmpty()) {
                likeViewHolder.imgUser.setImageURI(Uri.parse(list.get(position).profile != null ? Constants.MEDIA_BASE_URL + list.get(position).profile.profilePic : Constants.AVATAR_IMAGE));
            } else {
//                likeViewHolder.imgUser.setImageURI(Uri.parse(list.get(position).profile != null ? Constants.MEDIA_BASE_URL + list.get(position).profile.profilePic : Constants.AVATAR_IMAGE));
                Glide.with(context).load(R.drawable.logo_with_text).into(likeViewHolder.imgUser);
            }
            likeViewHolder.tvUserName.setText(Utility.getCamelCase(list.get(position).profile != null ? list.get(position).profile.userName : Constants.APP_NAME));

            if (list.get(position).updated_at != null && !list.get(position).updated_at.isEmpty()) {
                likeViewHolder.tvTimeAgo.setText(Utility.makeDateToAgo(list.get(position).updated_at));
            } else {
                if (list.get(position).timeAgo != null && !list.get(position).timeAgo.isEmpty()) {
                    likeViewHolder.tvTimeAgo.setText(Utility.makeDateToAgo(list.get(position).timeAgo));
                }
            }

            likeViewHolder.imgUser.setOnClickListener(v -> {
//                if (list.get(position).profile.isCeleb) {
                    if (list.get(position).profile.id.equals(SessionManager.userLogin.userId)) {
                        iLikesAdapter.selfProfile(list.get(position).profile);
                    } else {
                        iLikesAdapter.celebUserProfile(list.get(position).profile);
                    }
//                } else {
//                    iLikesAdapter.celebUserProfile(list.get(position).profile);
//                }
            });

            likeViewHolder.online_profileCheck.setVisibility(list.get(position).profile.isCeleb ? View.VISIBLE : View.GONE);

            likeViewHolder.llHeader.setOnClickListener(v -> {
//                if (list.get(position).profile.isCeleb) {
                    if (list.get(position).profile.id.equals(SessionManager.userLogin.userId)) {
                        iLikesAdapter.selfProfile(list.get(position).profile);
                    } else {
                        iLikesAdapter.celebUserProfile(list.get(position).profile);
                    }
//                } else {
//                    iLikesAdapter.celebUserProfile(list.get(position).profile);
//                }

            });
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
            List<Like> appendList = (List<Like>) (List<?>) subList;
            int positionStart = list.size() + 1;
            list.addAll(appendList);
            notifyItemRangeInserted(positionStart, appendList.size());
            rController = RController.LOADED;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class LikeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgUser)
        SimpleDraweeView imgUser;

        @BindView(R.id.tvUserName)
        TextView tvUserName;

        @BindView(R.id.tvTimeAgo)
        TextView tvTimeAgo;

        @BindView(R.id.llHeader)
        LinearLayout llHeader;

        @BindView(R.id.online_profileCheck)
        ImageView online_profileCheck;

        public LikeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
