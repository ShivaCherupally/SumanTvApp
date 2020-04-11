package info.sumantv.flow.stories;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.search.ISearchAdapter;
import info.sumantv.flow.bottommenu.search.Search;

import info.sumantv.flow.R;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.stories.models.SeenProfileInnerData;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Utility;

/**
 *
 */

public class SeenProfilesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ISearchAdapter iSearchAdapter;
    private List<SeenProfileInnerData> searchList;

    public SeenProfilesAdapter(List<SeenProfileInnerData> searchList, Activity context, RController rController, ISearchAdapter iSearchAdapter) {

        this.context = context;
        this.searchList = searchList;
        this.iSearchAdapter = iSearchAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.storyseenprofiles;
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SearchViewHolder) {

            SearchViewHolder searchViewHolder = (SearchViewHolder) holder;

            if (searchList != null) {

                if (searchList.get(position).storyByMemberInfo.firstName != null && !searchList.get(position).storyByMemberInfo.firstName.isEmpty()) {

                    if (searchList.get(position).storyByMemberInfo.lastName != null && !searchList.get(position).storyByMemberInfo.lastName.isEmpty()) {
                        searchViewHolder.sName.setText(Character.toUpperCase(searchList.get(position).storyByMemberInfo.firstName.charAt(0))
                                + searchList.get(position).storyByMemberInfo.firstName.substring(1) +
                                " " + Character.toUpperCase(searchList.get(position).storyByMemberInfo.lastName.charAt(0))
                                + searchList.get(position).storyByMemberInfo.lastName.substring(1));
                    } else {
                        searchViewHolder.sName.setText(Character.toUpperCase(searchList.get(position).storyByMemberInfo.firstName.charAt(0))
                                + searchList.get(position).storyByMemberInfo.firstName.substring(1));
                    }
                }

                if (searchList.get(position).storyByMemberInfo.profession != null && !searchList.get(position).storyByMemberInfo.profession.isEmpty()) {
                    searchViewHolder.sProfession.setText(Character.toUpperCase(searchList.get(position).storyByMemberInfo.profession.charAt(0))
                            + searchList.get(position).storyByMemberInfo.profession.substring(1));
                }

                if (searchList.get(position).storyByMemberInfo.isCeleb) {
                    searchViewHolder.ivOnline.setVisibility(View.VISIBLE);
                } else {
                    searchViewHolder.ivOnline.setVisibility(View.GONE);
                }


                if (searchList.get(position).createdAt != null && !searchList.get(position).createdAt.isEmpty()) {
                   // searchViewHolder.seentime.setText(Utility.timesAgo(searchList.get(position).createdAt));
                    searchViewHolder.seentime.setText(Utility.activeAgotime(searchList.get(position).createdAt));
                } else {
                    searchViewHolder.seentime.setText("");
                }


                if (searchList.get(position).storyByMemberInfo.avtar_imgPath != null && !searchList.get(position).storyByMemberInfo.avtar_imgPath.isEmpty()) {
                    Glide.with(context)
                            .load(ApiClient.BASE_URL + searchList.get(position).storyByMemberInfo.avtar_imgPath)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_profile_circle_pleace_holder)
                            .into(searchViewHolder.sProfile);
                } else {
                    searchViewHolder.sProfile.setImageResource(R.drawable.ic_profile_circle_pleace_holder);
                }
            }

            searchViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    iSearchAdapter.saveSearchingCeleb(position);
                    Common.getInstance().openProfileScreen(context, searchList.get(position).storyByMemberInfo.id);

                }
            });


        }
    }

    public void loadmore(List<Search> celebrityList) {
        ArrayList<Search> appendList = (ArrayList<Search>) (List<?>) celebrityList;
        int positionStart = getItemCount() + 1;
        notifyItemRangeInserted(positionStart, appendList.size());
    }


    @Override
    public int getItemCount() {
        return searchList.size();
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sProfile)
        CircleImageView sProfile;

        @BindView(R.id.sName)
        TextView sName;

        @BindView(R.id.sProfession)
        TextView sProfession;

        @BindView(R.id.removeimg)
        ImageView removeimg;


        @BindView(R.id.ivOnline)
        ImageView ivOnline;

        @BindView(R.id.seentime)
        TextView seentime;

        @BindView(R.id.profileDataLayout)
        LinearLayout profileDataLayout;


        public SearchViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
