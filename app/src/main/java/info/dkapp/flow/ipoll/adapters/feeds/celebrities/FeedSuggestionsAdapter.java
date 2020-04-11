package info.dkapp.flow.ipoll.adapters.feeds.celebrities;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import info.dkapp.flow.appmanagers.feed.models.SuggestionsModel;
import info.dkapp.flow.ModelClass.FanUnFanData;
import info.dkapp.flow.ModelClass.ProfileDataModel;
import info.dkapp.flow.databaseutil.appstart.AppController;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.userflow.Util.Common;

import java.util.List;

public class FeedSuggestionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SuggestionsModel> list;
    private Context context;
    private RecyclerView recyclerView;

    public FeedSuggestionsAdapter(Context context, List<SuggestionsModel> list, RecyclerView recyclerView) {
        this.list = list;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FeedSuggestionsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_suggestions, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FeedSuggestionsViewHolder) {
            FeedSuggestionsViewHolder feedSuggestionsViewHolder = (FeedSuggestionsViewHolder) holder;
            int screenWidth = 0, screenHeight = 0;
            if (AppController.getInstance().getCurrentRegisteredActivity() != null) {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                AppController.getInstance().getCurrentRegisteredActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                screenWidth = displaymetrics.widthPixels;
                screenHeight = displaymetrics.heightPixels;
            }
            //feedSuggestionsViewHolder.llParent.getLayoutParams().width = (screenWidth/2)-100;
//            feedSuggestionsViewHolder.ivUserImage.getLayoutParams().width = (screenWidth/3);
//            feedSuggestionsViewHolder.ivUserImage.getLayoutParams().height = (screenWidth/3);
            if (list.get(position).firstName != null && !list.get(position).firstName.isEmpty()) {
                feedSuggestionsViewHolder.tvUserName.setText(Character.toUpperCase(list.get(position).firstName.charAt(0)) + list.get(position).firstName.substring(1) + " " + list.get(position).lastName);
            } else {
                feedSuggestionsViewHolder.tvUserName.setText("");
            }
            if (list.get(position).profession != null && !list.get(position).profession.isEmpty()) {
                feedSuggestionsViewHolder.tvProfession.setText(list.get(position).profession);
            } else {
                feedSuggestionsViewHolder.tvProfession.setText("");
            }
            if (list.get(position).avtar_imgPath != null && !list.get(position).avtar_imgPath.isEmpty()) {
                try {
                    Glide.with(context)
                            .load(ApiClient.BASE_URL + list.get(position).avtar_imgPath)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_profile_circle_pleace_holder)
                            .into(feedSuggestionsViewHolder.ivUserImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                feedSuggestionsViewHolder.ivUserImage.setImageResource(R.drawable.ic_profile_circle_pleace_holder);
            }
            setFollowUnFollow(feedSuggestionsViewHolder, list.get(position).isFollowStatus);
            //
            feedSuggestionsViewHolder.llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ProfileDataModel profileDataModel = new ProfileDataModel();
                        profileDataModel._id = list.get(position)._id;
                        profileDataModel.firstName = list.get(position).firstName;
                        profileDataModel.lastName = list.get(position).lastName;
                        profileDataModel.isCeleb = list.get(position).isCeleb;
                        profileDataModel.role = "";
                        profileDataModel.avtar_imgPath = list.get(position).avtar_imgPath;
                        profileDataModel.isOnline = false;
                        profileDataModel.profession = list.get(position).profession;
                        profileDataModel.aboutMe = list.get(position).aboutMe;
                        profileDataModel.isFan = false;
                        Common.getInstance().navigatingToProfile(context, profileDataModel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            IApiListener apiListenerFanUnFan = new IApiListener() {
                @Override
                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                    if (condition.equals(Constants.FAN_STATUS)) {
                        try {
                            feedSuggestionsViewHolder.btnFan.setText("Fanned");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (condition.equals(Constants.UN_FAN_STATUS)) {
                        try {
                            feedSuggestionsViewHolder.btnFan.setText("Fan");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                    //
                }
            };

            feedSuggestionsViewHolder.btnFan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        FanUnFanData fanUnFanData = new FanUnFanData(context, list.get(position)._id, 0.0, list.get(position).firstName, list.get(position).lastName, list.get(position).avtar_imgPath, list.get(position).profession);
                        Common.getInstance().getFanCredits(context, feedSuggestionsViewHolder.btnFan.getText().toString().equals("Fan"), fanUnFanData, apiListenerFanUnFan);
                }
            });

            IApiListener iApiListenerFollowUnFollow = new IApiListener() {
                @Override
                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                    if (condition.equals(ApiClient.FOLLOW_CELEB)) {
                        try {
                            list.get(position).isFollowStatus = true;
                            setFollowUnFollow(feedSuggestionsViewHolder, list.get(position).isFollowStatus);
                            if(position+1 < list.size() && recyclerView != null){
                                recyclerView.smoothScrollToPosition(position+1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (condition.equals(ApiClient.UNFOLLOW_CELEB)) {
                        try {
                            list.get(position).isFollowStatus = false;
                            setFollowUnFollow(feedSuggestionsViewHolder, list.get(position).isFollowStatus);
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

            feedSuggestionsViewHolder.btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Common.getInstance().followUnFollow(context, feedSuggestionsViewHolder.btnFollow.getText().toString().equals("Follow"), list.get(position)._id, true, iApiListenerFollowUnFollow);
                }
            });
        }
    }

    public void setFollowUnFollow(FeedSuggestionsViewHolder feedSuggestionsViewHolder, Boolean isFollowStatus) {
        if (isFollowStatus != null && isFollowStatus) {
            feedSuggestionsViewHolder.btnFollow.setText("Following");
            feedSuggestionsViewHolder.btnFollow.setBackgroundResource(R.drawable.submit_rectangle_for_settings_empty_drakgrey_5dp);
            feedSuggestionsViewHolder.btnFollow.setTextColor(context.getResources().getColor(R.color.dark_grey));
        } else {
            feedSuggestionsViewHolder.btnFollow.setText("Follow");
            feedSuggestionsViewHolder.btnFollow.setBackgroundResource(R.drawable.submit_rectangle_normal_rad);
            feedSuggestionsViewHolder.btnFollow.setTextColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FeedSuggestionsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.llParent)
        LinearLayout llParent;

        @BindView(R.id.ivUserImage)
        ImageView ivUserImage;

        @BindView(R.id.tvUserName)
        TextView tvUserName;

        @BindView(R.id.tvProfession)
        TextView tvProfession;

        @BindView(R.id.btnFan)
        Button btnFan;

        @BindView(R.id.btnFollow)
        Button btnFollow;

        public FeedSuggestionsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
