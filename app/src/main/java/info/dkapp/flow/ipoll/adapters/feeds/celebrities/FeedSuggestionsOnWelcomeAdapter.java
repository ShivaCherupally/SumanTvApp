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
import info.dkapp.flow.ipoll.fragments.feeds.FeedsFragment;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.userflow.Util.Common;

import java.util.List;

public class FeedSuggestionsOnWelcomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SuggestionsModel> list;
    private Context context;

    public FeedSuggestionsOnWelcomeAdapter(Context context, List<SuggestionsModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FeedSuggestionsOnWelcomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_suggestions_on_welcome, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FeedSuggestionsOnWelcomeViewHolder) {
            final FeedSuggestionsOnWelcomeViewHolder feedSuggestionsOnWelcomeViewHolder = (FeedSuggestionsOnWelcomeViewHolder) holder;
            int screenWidth = 0, screenHeight = 0;
            if (AppController.getInstance().getCurrentRegisteredActivity() != null) {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                AppController.getInstance().getCurrentRegisteredActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                screenWidth = displaymetrics.widthPixels;
                screenHeight = displaymetrics.heightPixels;
            }
            //feedSuggestionsOnWelcomeViewHolder.llParent.getLayoutParams().width = (screenWidth/2)-100;
//            feedSuggestionsOnWelcomeViewHolder.ivUserImage.getLayoutParams().width = (screenWidth/3);
//            feedSuggestionsOnWelcomeViewHolder.ivUserImage.getLayoutParams().height = (screenWidth/3);
            if (list.get(position).firstName != null && !list.get(position).firstName.isEmpty()) {
                feedSuggestionsOnWelcomeViewHolder.tvUserName.setText(Character.toUpperCase(list.get(position).firstName.charAt(0)) + list.get(position).firstName.substring(1) + " " + list.get(position).lastName);
            } else {
                feedSuggestionsOnWelcomeViewHolder.tvUserName.setText("");
            }
            if (list.get(position).profession != null && !list.get(position).profession.isEmpty()) {
                feedSuggestionsOnWelcomeViewHolder.tvProfession.setText(list.get(position).profession);
            } else {
                feedSuggestionsOnWelcomeViewHolder.tvProfession.setText("");
            }
            if (list.get(position).avtar_imgPath != null && !list.get(position).avtar_imgPath.isEmpty()) {
                try {
                    Glide.with(context)
                            .load(ApiClient.BASE_URL + list.get(position).avtar_imgPath)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_profile_circle_pleace_holder)
                            .into(feedSuggestionsOnWelcomeViewHolder.ivUserImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                feedSuggestionsOnWelcomeViewHolder.ivUserImage.setImageResource(R.drawable.ic_profile_circle_pleace_holder);
            }
            setFollowUnFollow(feedSuggestionsOnWelcomeViewHolder, list.get(position).isFollowStatus);
            //
            feedSuggestionsOnWelcomeViewHolder.llParent.setOnClickListener(new View.OnClickListener() {
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
                            feedSuggestionsOnWelcomeViewHolder.btnFan.setText("Fanned");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (condition.equals(Constants.UN_FAN_STATUS)) {
                        try {
                            feedSuggestionsOnWelcomeViewHolder.btnFan.setText("Fan");
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

            feedSuggestionsOnWelcomeViewHolder.btnFan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        FanUnFanData fanUnFanData = new FanUnFanData(context, list.get(position)._id, 0.0, list.get(position).firstName, list.get(position).lastName, list.get(position).avtar_imgPath, list.get(position).profession);
                        Common.getInstance().getFanCredits(context, feedSuggestionsOnWelcomeViewHolder.btnFan.getText().toString().equals("Fan"), fanUnFanData, apiListenerFanUnFan);
                }
            });

            IApiListener iApiListenerFollowUnFollow = new IApiListener() {
                @Override
                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                    if (condition.equals(ApiClient.FOLLOW_CELEB)) {
                        try {
                            list.get(position).isFollowStatus = true;
                            setFollowUnFollow(feedSuggestionsOnWelcomeViewHolder, list.get(position).isFollowStatus);
                            //
                            list.remove(position);
                            notifyDataSetChanged();
                            if (list.size() <= 0) {
                                if (FeedsFragment.getInstance() != null) {
                                    FeedsFragment.getInstance().fetchFeeds(null);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (condition.equals(ApiClient.UNFOLLOW_CELEB)) {
                        try {
                            list.get(position).isFollowStatus = false;
                            setFollowUnFollow(feedSuggestionsOnWelcomeViewHolder, list.get(position).isFollowStatus);
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

            feedSuggestionsOnWelcomeViewHolder.btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     if (list.size() <= 0) {
                        if (FeedsFragment.getInstance() != null) {
                            FeedsFragment.getInstance().fetchFeeds(null);
                        }
                    }
                }
            });
        }
    }

    public void setFollowUnFollow(FeedSuggestionsOnWelcomeViewHolder feedSuggestionsOnWelcomeViewHolder, Boolean isFollowStatus) {
        if (isFollowStatus != null && isFollowStatus) {
            feedSuggestionsOnWelcomeViewHolder.btnFollow.setText("Following");
            feedSuggestionsOnWelcomeViewHolder.btnFollow.setBackgroundResource(R.drawable.submit_rectangle_white);
            feedSuggestionsOnWelcomeViewHolder.btnFollow.setTextColor(context.getResources().getColor(R.color.color222));
        } else {
            feedSuggestionsOnWelcomeViewHolder.btnFollow.setText("Follow");
            feedSuggestionsOnWelcomeViewHolder.btnFollow.setBackgroundResource(R.drawable.submit_rectangle);
            feedSuggestionsOnWelcomeViewHolder.btnFollow.setTextColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FeedSuggestionsOnWelcomeViewHolder extends RecyclerView.ViewHolder {
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

        public FeedSuggestionsOnWelcomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
