package info.dkapp.flow.ipoll.adapters.feeds.celebrities;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import info.dkapp.flow.appmanagers.feed.models.Celebrity;
import info.dkapp.flow.ModelClass.ProfileDataModel;

import info.dkapp.flow.R;
import info.dkapp.flow.ipoll.viewholders.CelebrityViewHolderViewAll;
import info.dkapp.flow.retrofitcall.*;
import info.dkapp.flow.userflow.Util.Common;

import java.util.List;

/**
 * Created by Uday Kumay Vurukonda on 12/18/2018.
 * <p>
 * Mr.Psycho
 */
public class OnlineCelebrityViewAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<Celebrity> list;
    Context context;
    private ProgressDialog progressDialog;
    ApiInterface apiInterface;


    public OnlineCelebrityViewAllAdapter(Context context, List<Celebrity> list) {
        this.list = list;
        this.context = context;
    }

    public OnlineCelebrityViewAllAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CelebrityViewHolderViewAll(LayoutInflater.from(parent.getContext()).inflate(R.layout.onlineviewall_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CelebrityViewHolderViewAll) {
            CelebrityViewHolderViewAll celebrityViewHolder = (CelebrityViewHolderViewAll) holder;

            if (list.get(position).firstName != null && !list.get(position).firstName.isEmpty()) {
                celebrityViewHolder.user_name.setText(Character.toUpperCase(list.get(position).firstName.charAt(0))
                        + list.get(position).firstName.substring(1) + " " + Character.toUpperCase(list.get(position).lastName.charAt(0))
                        + list.get(position).lastName.substring(1));
            } else {
                celebrityViewHolder.user_name.setText("");
            }
            if (list.get(position).profession != null && !list.get(position).profession.isEmpty()) {
                celebrityViewHolder.occupationtv.setText(list.get(position).profession);
            } else {
                celebrityViewHolder.occupationtv.setText("");
            }

            if (list.get(position).isFollower != null) {

                if (list.get(position).isFollower) {
                    celebrityViewHolder.online_profileCheck.setText("Following");
                    celebrityViewHolder.online_profileCheck.setTextColor(context.getResources().getColor(R.color.dark_grey));
                    celebrityViewHolder.online_profileCheck.setBackground(context.getResources().getDrawable(R.drawable.submit_rectangle_for_settings_empty_drakgrey_5dp));
                } else {
                    celebrityViewHolder.online_profileCheck.setTextColor(context.getResources().getColor(R.color.white));
                    celebrityViewHolder.online_profileCheck.setText("Follow");
                    celebrityViewHolder.online_profileCheck.setBackground(context.getResources().getDrawable(R.drawable.submit_rectangle_normal_rad));
                }
                // celebrityViewHolder.online_profileCheck.setVisibility(list.get(position).isFollower ? View.VISIBLE : View.GONE);
            } else {
                celebrityViewHolder.online_profileCheck.setText("Follow");
            }

            if (list.get(position).avatarImgPath != null && !list.get(position).avatarImgPath.isEmpty()) {
                try {
                    Glide.with(context)
                            .load(ApiClient.BASE_URL + list.get(position).avatarImgPath)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_grey_celebkonect_logo)
                            .into(celebrityViewHolder.user_image);

                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            } else {
                celebrityViewHolder.user_image.setImageResource(R.drawable.ic_grey_celebkonect_logo);
            }
            celebrityViewHolder.user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ProfileDataModel profileDataModel = new ProfileDataModel();
                    profileDataModel._id = list.get(position).id;
                    profileDataModel.firstName = list.get(position).firstName;
                    profileDataModel.lastName = list.get(position).lastName;
                    profileDataModel.isCeleb = list.get(position).isCeleb;
                    profileDataModel.role = list.get(position).role;
                    profileDataModel.avtar_imgPath = list.get(position).avatarImgPath;
                    profileDataModel.isOnline = list.get(position).isOnline;
                    profileDataModel.profession = list.get(position).profession;
                    profileDataModel.aboutMe = list.get(position).aboutMe;
                    profileDataModel.isFan = list.get(position).isFan;
                    Common.getInstance().viewCelebProfileDialog(context, profileDataModel, progressDialog);
                }
            });

            IApiListener iApiListenerFollowUnFollow = new IApiListener() {
                @Override
                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                    if (condition.equals(ApiClient.FOLLOW_CELEB)) {
                        try {
                            Common.getInstance().cusToast(context, "You are now a follower of " + list.get(position).firstName);

                            /*if (FeedsFragment.getInstance() != null) {
                                FeedsFragment.getInstance().fetchOnlineUsers();
                            }*/
                            list.get(position).isFollower = true;
                            notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (condition.equals(ApiClient.UNFOLLOW_CELEB)) {
                        try {
                            list.get(position).isFollower = false;
                            notifyDataSetChanged();

                           /* if (FeedsFragment.getInstance() != null) {
                                FeedsFragment.getInstance().fetchOnlineUsers();
                            }*/

                            Common.getInstance().cusToast(context, "You have stopped following " + list.get(position).firstName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                    Toast.makeText(context, "please check your network and try again", Toast.LENGTH_SHORT).show();
                }
            };

            celebrityViewHolder.online_profileCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Common.getInstance().followUnFollow(context, celebrityViewHolder.online_profileCheck.getText().toString().equals("Follow"), list.get(position).id, true, iApiListenerFollowUnFollow);
                }
            });

            celebrityViewHolder.user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProfileDataModel profileDataModel = new ProfileDataModel();
                    profileDataModel._id = list.get(position).id;
                    profileDataModel.firstName = list.get(position).firstName;
                    profileDataModel.lastName = list.get(position).lastName;
                    profileDataModel.isCeleb = list.get(position).isCeleb;
                    profileDataModel.role = list.get(position).role;
                    profileDataModel.avtar_imgPath = list.get(position).avatarImgPath;
                    profileDataModel.isOnline = list.get(position).isOnline;
                    profileDataModel.profession = list.get(position).profession;
                    profileDataModel.aboutMe = list.get(position).aboutMe;
                    profileDataModel.isFan = list.get(position).isFan;
                    ;//Need to get from Backend - IS_FAN


//                    Common.getInstance().viewCelebProfileDialog(context, profileDataModel, progressDialog);
                    //checking with customize class
                    Common.getInstance().viewCelebProfileDialog(context, profileDataModel, progressDialog);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void loadmore(List<Celebrity> celebrityList) {
        List<Celebrity> appendList = (List<Celebrity>) (List<?>) celebrityList;
        int positionStart = getItemCount() + 1;
        notifyItemRangeInserted(positionStart, appendList.size());
    }
}
