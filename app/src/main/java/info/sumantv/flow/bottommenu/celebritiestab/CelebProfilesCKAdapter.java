package info.sumantv.flow.bottommenu.celebritiestab;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.R;
import info.sumantv.flow.appmanagers.feed.models.Celebrity;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;

/**
 * <p>
 * Mr.Psycho
 */
public class CelebProfilesCKAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<Celebrity> list;
    Context context;
    String celebType;

    public List<Celebrity> OnlineCeleblist;


    public CelebProfilesCKAdapter(Context context, List<Celebrity> listTemp, String celebType) {
        Log.e("CelebList", celebType + listTemp.size() + "");
        this.list = listTemp;
        this.celebType = celebType;
        this.context = context;
        if (celebType.equals(context.getResources().getString(R.string.nowonline))) {
            this.OnlineCeleblist = listTemp;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (celebType.equals(context.getResources().getString(R.string.nowonline))) {
            return new CelebProfileOnlineHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item_ck, parent, false));
        } else if (celebType.equals(context.getResources().getString(R.string.trending_celeb))) {
            return new CelebProfilesTrendingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_or_recommended_item_ck, parent, false));
        } else if (celebType.equals(context.getResources().getString(R.string.editor_celeb))) {
            return new CelebProfilesEditorHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.editor_celeb_item, parent, false));
        } else if (celebType.equals(context.getResources().getString(R.string.recomended_celeb))) {
            return new CelebProfilesRecommendedHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_or_recommended_item_ck, parent, false));
        } else {
            return new CelebProfileOnlineHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item_ck, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CelebProfileOnlineHolder) {
            CelebProfileOnlineHolder celebrityViewHolder = (CelebProfileOnlineHolder) holder;
            if (OnlineCeleblist.get(position).id == null || OnlineCeleblist.get(position).id.isEmpty()
                    || OnlineCeleblist.get(position).id.equals(SessionManager.userLogin.userId)) {
                celebrityViewHolder.itemView.getLayoutParams().width = 1;
                return;
            }
            try {
                if (OnlineCeleblist.get(position).id != null && !OnlineCeleblist.get(position).id.isEmpty()) {
                    if (!OnlineCeleblist.get(position).id.equals(SessionManager.userLogin.userId)) {
                        if (OnlineCeleblist.get(position).firstName != null && !OnlineCeleblist.get(position).firstName.isEmpty()) {
                            celebrityViewHolder.user_name.setText(Character.toUpperCase(OnlineCeleblist.get(position).firstName.charAt(0))
                                    + OnlineCeleblist.get(position).firstName.substring(1));

                            if (OnlineCeleblist.get(position).lastName != null && !OnlineCeleblist.get(position).lastName.isEmpty()) {
                                celebrityViewHolder.user_name.setText(Character.toUpperCase(OnlineCeleblist.get(position).firstName.charAt(0))
                                        + OnlineCeleblist.get(position).firstName.substring(1) + " " + Character.toUpperCase(OnlineCeleblist.get(position).lastName.charAt(0))
                                        + OnlineCeleblist.get(position).lastName.substring(1));
                            }

                        } else {
                            celebrityViewHolder.user_name.setText("");
                        }
                    }

                    if (OnlineCeleblist.get(position).profession != null && !OnlineCeleblist.get(position).profession.isEmpty()) {
                        celebrityViewHolder.profession.setText(OnlineCeleblist.get(position).profession);
                    } else {
                        celebrityViewHolder.profession.setText("");
                    }
                    if (OnlineCeleblist.get(position).category != null && !OnlineCeleblist.get(position).category.isEmpty()) {
                        celebrityViewHolder.category.setText(OnlineCeleblist.get(position).category);
                    } else {
                        celebrityViewHolder.category.setText("");
                    }

                    if (OnlineCeleblist.get(position).avatarImgPath != null && !OnlineCeleblist.get(position).avatarImgPath.isEmpty()) {
                        try {
                            Glide.with(context)
                                    .load(ApiClient.BASE_URL + OnlineCeleblist.get(position).avatarImgPath)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_grey_celebkonect_logo)
                                    .into(celebrityViewHolder.user_image);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    } else {
                        celebrityViewHolder.user_image.setImageResource(R.drawable.ic_grey_celebkonect_logo);
                    }

                    celebrityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            navigateTOProfile(position);
                            Common.getInstance().openProfileScreen(context, OnlineCeleblist.get(position).id);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (holder instanceof CelebProfilesTrendingHolder) {

            CelebProfilesTrendingHolder celebrityViewHolderLocal = (CelebProfilesTrendingHolder) holder;
            celebrityViewHolderLocal.profession.setVisibility(View.VISIBLE);
            celebrityViewHolderLocal.category.setVisibility(View.VISIBLE);
            celebrityViewHolderLocal.user_image.setVisibility(View.VISIBLE);

            setProfileData(celebrityViewHolderLocal.user_name, celebrityViewHolderLocal.profession,celebrityViewHolderLocal.category, position);
            setProfileImage(celebrityViewHolderLocal.user_image, celebrityViewHolderLocal.itemView, position);

        } else if (holder instanceof CelebProfilesEditorHolder) {
            CelebProfilesEditorHolder celebrityViewHolderLocal = (CelebProfilesEditorHolder) holder;

//            celebrityViewHolderLocal.profession.setVisibility(View.GONE);
//            celebrityViewHolderLocal.user_name.setVisibility(View.GONE);

//            setProfileData(celebrityViewHolderLocal.user_name, celebrityViewHolderLocal.profession, position);
            setProfileImage(celebrityViewHolderLocal.user_image, celebrityViewHolderLocal.itemView, position);
        } else if (holder instanceof CelebProfilesRecommendedHolder) {

            CelebProfilesRecommendedHolder celebrityViewHolderLocal = (CelebProfilesRecommendedHolder) holder;

            celebrityViewHolderLocal.profession.setVisibility(View.VISIBLE);
            celebrityViewHolderLocal.category.setVisibility(View.VISIBLE);
            celebrityViewHolderLocal.user_image.setVisibility(View.VISIBLE);

            setProfileData(celebrityViewHolderLocal.user_name, celebrityViewHolderLocal.profession,celebrityViewHolderLocal.category, position);
            setProfileImage(celebrityViewHolderLocal.user_image, celebrityViewHolderLocal.itemView, position);
        }
    }

    private void setProfileData(TextView user_name, TextView profession,TextView category, int position) {
        if (list.get(position).firstName != null && !list.get(position).firstName.isEmpty()) {
            user_name.setText(Character.toUpperCase(list.get(position).firstName.charAt(0))
                    + list.get(position).firstName.substring(1) + " " + Character.toUpperCase(list.get(position).lastName.charAt(0))
                    + list.get(position).lastName.substring(1));
        } else {
            user_name.setText("");
        }
        if (list.get(position).profession != null && !list.get(position).profession.isEmpty()) {
            profession.setText(list.get(position).profession);
        } else {
            profession.setText("");
        }

        if (list.get(position).category != null && !list.get(position).category.isEmpty()) {
            category.setText(list.get(position).category);
        } else {
            category.setText("");
        }


    }

    private void setProfileImage(ImageView user_image, View itemView, int position) {
        if (!celebType.equals(context.getResources().getString(R.string.editor_celeb))) {
            if (list.get(position).avatarImgPath != null && !list.get(position).avatarImgPath.isEmpty()) {
                try {
                    Glide.with(context)
                            .load(ApiClient.BASE_URL + list.get(position).avatarImgPath)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_grey_celebkonect_logo)
                            .into(user_image);

                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            } else {
                user_image.setImageResource(R.drawable.ic_grey_celebkonect_logo);
            }

            Log.e("celebType", celebType + "");
            int padding = 8;
            itemView.setPadding(padding, padding, padding, padding);
        } else {
            Log.e("imageurl", ApiClient.BASE_URL + list.get(position).custom_imgPath + "");
            Log.e("celebTypeEditor", celebType + "");
            int padding = 0;
            itemView.setPadding(padding, padding, padding, padding);

            if (list.get(position).custom_imgPath != null && !list.get(position).custom_imgPath.isEmpty()) {
                try {
                    Glide.with(context)
                            .load(ApiClient.BASE_URL + list.get(position).custom_imgPath)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_grey_celebkonect_logo)
                            .into(user_image);

                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            } else {
                user_image.setImageResource(R.drawable.ic_grey_celebkonect_logo);
            }
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navigateTOProfile(position);
                Common.getInstance().openProfileScreen(context, list.get(position).id);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (celebType.equals(context.getResources().getString(R.string.nowonline))) {
            return OnlineCeleblist.size();
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class CelebProfileOnlineHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_image)
        CircleImageView user_image;

        @BindView(R.id.user_name)
        TextView user_name;

        @BindView(R.id.profession)
        TextView profession;

        @BindView(R.id.category)
        TextView category;


        public CelebProfileOnlineHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }

    public class CelebProfilesTrendingHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardview)
        CardView cardview;

        @BindView(R.id.user_image)
        ImageView user_image;

        @BindView(R.id.user_name)
        TextView user_name;

        @BindView(R.id.profession)
        TextView profession;

        @BindView(R.id.category)
        TextView category;

        public CelebProfilesTrendingHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }

    public class CelebProfilesEditorHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_image)
        ImageView user_image;

        public CelebProfilesEditorHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }

    public class CelebProfilesRecommendedHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.user_image)
        ImageView user_image;

        @BindView(R.id.user_name)
        TextView user_name;

        @BindView(R.id.profession)
        TextView profession;

        @BindView(R.id.category)
        TextView category;

        public CelebProfilesRecommendedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public void updateOnlineCelebrityList(List<Celebrity> celebrityListFinal) {
        celebType = context.getResources().getString(R.string.nowonline);
        this.OnlineCeleblist = celebrityListFinal;
        Log.d("onlineCelebList", OnlineCeleblist.size() + "");
    }


}
