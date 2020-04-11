package info.dkapp.flow.bottommenu.search;

import android.app.Activity;
import android.content.Context;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import info.dkapp.flow.bottommenu.constants.RController;

import info.dkapp.flow.R;
import info.dkapp.flow.retrofitcall.ApiClient;

/**
 * Created by Chenna on 21-08-2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ISearchAdapter iSearchAdapter;
    private List<Search> searchList;

    public SearchAdapter(List<Search> searchList, Activity context, RController rController, ISearchAdapter iSearchAdapter) {
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

                if (searchList.get(position).getFirstName() != null && !searchList.get(position).getFirstName().isEmpty()) {

                    if (searchList.get(position).getLastName() != null && !searchList.get(position).getLastName().isEmpty()) {
                        searchViewHolder.sName.setText(Character.toUpperCase(searchList.get(position).getFirstName().charAt(0))
                                + searchList.get(position).getFirstName().substring(1) +
                                " " + Character.toUpperCase(searchList.get(position).getLastName().charAt(0))
                                + searchList.get(position).getLastName().substring(1));
                    } else {
                        searchViewHolder.sName.setText(Character.toUpperCase(searchList.get(position).getFirstName().charAt(0))
                                + searchList.get(position).getFirstName().substring(1));
                    }
                }

                if (searchList.get(position).getProfession() != null && !searchList.get(position).getProfession().isEmpty()) {

                    searchViewHolder.sProfession.setText(Character.toUpperCase(searchList.get(position).getProfession().charAt(0))
                            + searchList.get(position).getProfession().substring(1));
                }

                if (searchList.get(position).getAvtarImgPath() != null && !searchList.get(position).getAvtarImgPath().isEmpty()) {
                    Glide.with(context)
                            .load(ApiClient.BASE_URL + searchList.get(position).getAvtarImgPath())
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
                    iSearchAdapter.saveSearchingCeleb(position);
                }
            });


        }
    }

    public void loadmore(List<Search> celebrityList) {
        ArrayList<Search> appendList = (ArrayList<Search>) (List<?>) celebrityList;
        int positionStart = getItemCount() + 1;
        notifyItemRangeInserted(positionStart, appendList.size());
    }

    public void removeAllData() {
        notifyDataSetChanged();
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
}
