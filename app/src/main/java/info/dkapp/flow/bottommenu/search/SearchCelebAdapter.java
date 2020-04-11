package info.dkapp.flow.bottommenu.search;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;
import info.dkapp.flow.ModelClass.ProfileDataModel;

import info.dkapp.flow.R;
import info.dkapp.flow.retrofitcall.*;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shiva on 21-08-2018.
 */

public class SearchCelebAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ISearchAdapter iSearchAdapter;
    private List<SearchCelebItemModel> searchList;

    public SearchCelebAdapter(List<SearchCelebItemModel> searchList, Activity context, ISearchAdapter iSearchAdapter) {
        this.context = context;
        this.searchList = searchList;
        this.iSearchAdapter = iSearchAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.search_layout;
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SearchViewHolder) {

            SearchViewHolder searchViewHolder = (SearchViewHolder) holder;

            if (searchList != null) {

                if (searchList.get(position).firstName != null && !searchList.get(position).firstName.isEmpty()) {
                    if (searchList.get(position).lastName != null && !searchList.get(position).lastName.isEmpty()) {
                        searchViewHolder.sName.setText(Character.toUpperCase(searchList.get(position).firstName.charAt(0))
                                + searchList.get(position).firstName.substring(1) +
                                " " + Character.toUpperCase(searchList.get(position).lastName.charAt(0))
                                + searchList.get(position).lastName.substring(1));
                    } else {
                        searchViewHolder.sName.setText(Character.toUpperCase(searchList.get(position).firstName.charAt(0))
                                + searchList.get(position).firstName.substring(1));
                    }
                } else {
                    if (searchList.get(position).username != null && !searchList.get(position).username.isEmpty()) {
                        searchViewHolder.sName.setText(Character.toUpperCase(searchList.get(position).username.charAt(0))
                                + searchList.get(position).username.substring(1));
                    }
                }

                if (searchList.get(position).profession != null && !searchList.get(position).profession.isEmpty()) {

                    searchViewHolder.sProfession.setText(Character.toUpperCase(searchList.get(position).profession.charAt(0))
                            + searchList.get(position).profession.substring(1));

                    //sProfession
                }

                if (searchList.get(position).profession != null && !searchList.get(position).profession.isEmpty()) {
                    searchViewHolder.sProfession.setText(Character.toUpperCase(searchList.get(position).profession.charAt(0))
                            + searchList.get(position).profession.substring(1));

                    /*if (searchList.get(position).ca != null && !searchList.get(position).ca.isEmpty()) {
                        String professionAndCategory = feed.feedMemberDetails.category + ", " + searchViewHolder.sProfession.getText().toString();
                        feedViewHolder.tvProfession.setText(professionAndCategory);
                    }*/
                }

                if (searchList.get(position).avtar_imgPath != null && !searchList.get(position).avtar_imgPath.isEmpty()) {
                    Glide.with(context)
                            .load(ApiClient.BASE_URL + searchList.get(position).avtar_imgPath)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_profile_circle_pleace_holder)
                            .into(searchViewHolder.sProfile);
                } else {
                    searchViewHolder.sProfile.setImageResource(R.drawable.ic_profile_circle_pleace_holder);
                }
            }

            searchViewHolder.sProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navigateTOProfile(position);
                }
            });
            searchViewHolder.sName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navigateTOProfile(position);
                }
            });
            searchViewHolder.sProfession.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navigateTOProfile(position);
                }
            });

            searchViewHolder.removeimg.setVisibility(View.VISIBLE);
            searchViewHolder.removeimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("CelebRemoved", true + "");
                    iSearchAdapter.deleteIndividualSearch(position);
                }
            });


            searchViewHolder.profileDataLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateTOProfile(position);
                }
            });
        }
    }

    public void loadmore(List<SearchCelebItemModel> celebrityList) {
        ArrayList<SearchCelebItemModel> appendList = (ArrayList<SearchCelebItemModel>) (List<?>) celebrityList;
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

        @BindView(R.id.profileDataLayout)
        LinearLayout profileDataLayout;


        public SearchViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void navigateTOProfile(int position) {
        ProfileDataModel profileDataModel = new ProfileDataModel();

        profileDataModel._id = searchList.get(position).celebrityId;
        if (searchList.get(position).firstName != null && !searchList.get(position).firstName.isEmpty()) {
            profileDataModel.firstName = searchList.get(position).firstName;
        } else {
            profileDataModel.firstName = "";
        }

        if (searchList.get(position).lastName != null && !searchList.get(position).lastName.isEmpty()) {
            profileDataModel.lastName = searchList.get(position).lastName;
        } else {
            profileDataModel.lastName = "";
        }

        if (searchList.get(position).avtar_imgPath != null && !searchList.get(position).avtar_imgPath.isEmpty()) {
            profileDataModel.avtar_imgPath = searchList.get(position).avtar_imgPath;
        } else {
            profileDataModel.avtar_imgPath = "";
        }


        if (searchList.get(position).profession != null && !searchList.get(position).profession.isEmpty()) {
            profileDataModel.profession = searchList.get(position).profession;
        } else {
            profileDataModel.profession = "";
        }

        profileDataModel.isCeleb = true;
        profileDataModel.isOnline = true;
        profileDataModel.aboutMe = "";
        profileDataModel.isFan = true;
        profileDataModel.role = "";

        Utility.hideKeyboard(context);
        Common.getInstance().viewCelebProfileDialog(context, profileDataModel, null);
    }


}
