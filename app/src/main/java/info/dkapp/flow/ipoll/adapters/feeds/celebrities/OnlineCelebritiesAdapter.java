package info.dkapp.flow.ipoll.adapters.feeds.celebrities;

import android.content.Context;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import info.dkapp.flow.appmanagers.feed.models.Celebrity;

import info.dkapp.flow.R;
import info.dkapp.flow.ipoll.interfaces.feeds.IFeedAdapter;
import info.dkapp.flow.ipoll.viewholders.CelebrityViewHolder;
import info.dkapp.flow.ipoll.viewholders.OnlineCelebritiesViewHolder;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.SessionManager;

import java.util.List;

/**
 * Created by User on 28-08-2018.
 **/

public class OnlineCelebritiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<Celebrity> list;
    Context context;
    IFeedAdapter iFeedAdapter;
    OnlineCelebritiesViewHolder onlineCelebritiesViewHolder;
    Pair<View, String> pair1;

    public OnlineCelebritiesAdapter(Context context, List<Celebrity> list, IFeedAdapter iFeedAdapter, OnlineCelebritiesViewHolder onlineCelebritiesViewHolder) {
        this.list = list;
        this.context = context;
        this.iFeedAdapter = iFeedAdapter;
        this.onlineCelebritiesViewHolder = onlineCelebritiesViewHolder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CelebrityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_celeb_profile_circle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CelebrityViewHolder) {
            CelebrityViewHolder celebrityViewHolder = (CelebrityViewHolder) holder;
            if (list.get(position).id == null || list.get(position).id.isEmpty() || list.get(position).id.equals(SessionManager.userLogin.userId)) {
                holder.itemView.getLayoutParams().width = 1;
                return;
            }
            if (list.get(position).firstName != null && !list.get(position).firstName.isEmpty()) {
                celebrityViewHolder.user_name.setText(Character.toUpperCase(list.get(position).firstName.charAt(0))
                        + list.get(position).firstName.substring(1));
            } else {
                celebrityViewHolder.user_name.setText("");
            }
            if (list.get(position).isFollower != null) {
                celebrityViewHolder.online_profileCheck.setVisibility(
                        list.get(position).isFollower ? View.VISIBLE : View.GONE);
            }
            if (list.get(position).avatarImgPath != null && !list.get(position).avatarImgPath.isEmpty()) {
                try {
                    Glide.with(context)
                            .load(ApiClient.BASE_URL + list.get(position).avatarImgPath)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_profile_circle_pleace_holder)
                            .into(celebrityViewHolder.user_image);

                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            } else {
                celebrityViewHolder.user_image.setImageResource(R.drawable.ic_profile_circle_pleace_holder);
            }
            celebrityViewHolder.profileLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        iFeedAdapter.navigatetoCelebProfile(position, list, onlineCelebritiesViewHolder);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(List<Celebrity> celebrityList){
        list = celebrityList;
        notifyDataSetChanged();
    }
}
