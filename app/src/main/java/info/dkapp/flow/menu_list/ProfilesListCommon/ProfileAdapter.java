package info.dkapp.flow.menu_list.ProfilesListCommon;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.dkapp.flow.ModelClass.FanUnFanData;
import info.dkapp.flow.ModelClass.ProfileDataModel;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.constants.RController;
import info.dkapp.flow.bottommenu.menuitemsmanager.fragments.MyFanFollowersFragment;
import info.dkapp.flow.bottommenu.viewholders.SkeletonViewHolder;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by Shiva on 2/24/2018.
 */

public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ProfileInfo> profileinfo;
    String COMING_HEADER;
    String tabName;
    private Dialog promoDialog;
    Button yesBtn, noBtn;
    private ProgressDialog progressDialog;
    TextView content_profile, headtitle, mCeleb_Name;
    CircleImageView profile_pic;
    int deletedPosition;
    int currentItemPostion;
    boolean isFromCelebProfile;

    //    FansOfFragment fragmentclass;
    //    CelebFollowingFragment fragmentclassfollowing;
//    FansFragment fansFragmenClass;
    ProfilesListFragment followersFragmentClass;
    int clickedPosition = -1;
    public RController rController;

    String profileId;
    boolean profileStatus;


    public ProfileAdapter(RController rController) {
        this.rController = rController;
    }


    public ProfileAdapter(ProfilesListFragment fragmentclass, Context context, ArrayList<ProfileInfo> profileinfo,
                          String tabName, boolean isFromCelebProfile, String profileId, boolean profileStatus) {
        this.profileinfo = profileinfo;
        this.context = context;
        this.tabName = tabName;
        this.isFromCelebProfile = isFromCelebProfile;
        this.followersFragmentClass = fragmentclass;
        this.profileId = profileId;
        this.profileStatus = profileStatus;
        //        this.rController = RController.LOADED;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
//        iApiListener = this;
        if (rController == RController.LOADING) {
            int layout = R.layout.fan_follow_list_item_skelton;
            return new SkeletonViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(layout, parent, false));
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_list_item, parent, false);
//        ButterKnife.bind(this, view);
            ProfileAdapter.MyViewHolder myViewHolder = new ProfileAdapter.MyViewHolder(view);
            return myViewHolder;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderGlobal, int position) {

        if (holderGlobal instanceof ProfileAdapter.MyViewHolder) {
            ProfileAdapter.MyViewHolder holder = (ProfileAdapter.MyViewHolder) holderGlobal;
            currentItemPostion = position;

            if (profileinfo.get(position).avtarImgPath != null && !profileinfo.get(position).avtarImgPath.isEmpty()) {
                Glide.with(context).load(ApiClient.BASE_URL + profileinfo.get(position).avtarImgPath)
                        .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.ic_profile_circle_pleace_holder).into(holder.profileImage);
            } else {
                holder.profileImage.setImageResource(R.drawable.ic_profile_circle_pleace_holder);
            }

            if (profileinfo.get(position).firstName != null && !profileinfo.get(position).firstName.isEmpty()) {
                holder.profilenametxt.setText(Common.convertCaseSensitive(profileinfo.get(position).firstName));
                if (profileinfo.get(position).lastName != null && !profileinfo.get(position).lastName.isEmpty()) {
                    holder.profilenametxt.setText(Common.convertCaseSensitive(profileinfo.get(position).firstName
                            + " " + profileinfo.get(position).lastName));
                }
            } else {
                if (profileinfo.get(position).username != null) {
                    holder.profilenametxt.setText(Common.convertCaseSensitive(profileinfo.get(position).username));
                }
            }


            if (profileinfo.get(position).isCeleb) {
                holder.isCelebVerifiedImage.setVisibility(View.VISIBLE);
                if (profileinfo.get(position).profession != null && !profileinfo.get(position).profession.isEmpty()) {

                    holder.professiontxt.setVisibility(View.VISIBLE);
                    holder.professiontxt.setText(Character.toUpperCase(profileinfo.get(position).profession.charAt(0))
                            + profileinfo.get(position).profession.substring(1));

                    if (profileinfo.get(position).category != null && !profileinfo.get(position).category.isEmpty()) {
                        String professionAndCategory = profileinfo.get(position).category + " , " + holder.professiontxt.getText().toString();
                        holder.professiontxt.setText(professionAndCategory);
                    }
                } else {

                    holder.professiontxt.setVisibility(View.GONE);
                }
            } else {
                holder.isCelebVerifiedImage.setVisibility(View.GONE);
                holder.professiontxt.setVisibility(View.GONE);
            }

            if (profileId.equals(Common.isLoginId())) {
                setButtonBasedOnTabView(tabName, holder.deactiveBtn, true);
            } else {
                setButtonBasedOnTabView(tabName, holder.deactiveBtn, false);
            }


            holder.deactiveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonAction(position);
                }
            });

            holder.profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (profileinfo.get(position).id.equals(Common.isLoginId())) {
                        navigateSelfProfile();
                    } else {
                        goToProfilePage(profileinfo, position);
                    }
                }
            });

            holder.profilenametxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (profileinfo.get(position).id.equals(Common.isLoginId())) {
                        navigateSelfProfile();
                    } else {
                        goToProfilePage(profileinfo, position);
                    }
                }
            });
        }
    }

    private void setButtonBasedOnTabView(String tabName, Button deactiveBtn, boolean isSelfProfile) {
        switch (tabName) {
            case Constants.ProfilesConstants.FAN_TAB:
                if (isSelfProfile) {
                    deactiveBtn.setText("Fanned");
                    deactiveBtn.setVisibility(View.VISIBLE);
                } else {
                    deactiveBtn.setVisibility(View.GONE);
                }
                break;
            case Constants.ProfilesConstants.FOLLOWING_TAB:
                if (isSelfProfile) {
                    deactiveBtn.setText("Following");
                    deactiveBtn.setVisibility(View.VISIBLE);
                } else {
                    deactiveBtn.setVisibility(View.GONE);
                }


                break;

            case Constants.ProfilesConstants.FANS_OF_TAB:
                if (isSelfProfile) {
                    deactiveBtn.setText("Block");
                    deactiveBtn.setVisibility(View.VISIBLE);
                } else {
                    deactiveBtn.setVisibility(View.GONE);
                }

                break;
            case Constants.ProfilesConstants.FOLLOWERS_TAB:
//                if (isSelfProfile) {
//                    deactiveBtn.setText("UnFollow");
//                    deactiveBtn.setVisibility(View.VISIBLE);
//                } else {
                deactiveBtn.setVisibility(View.GONE);
//                }


                break;
            case Constants.ProfilesConstants.BLOCKS_TAB:
                if (isSelfProfile) {
                    deactiveBtn.setText("Unblock");
                    deactiveBtn.setVisibility(View.VISIBLE);
                } else {
                    deactiveBtn.setVisibility(View.GONE);
                }

                break;
        }
    }

    private void goToProfilePage(ArrayList<ProfileInfo> profileList_, int position_) {

        try {
            if (profileList_.get(position_).isCeleb != null) {
                if (profileList_.get(position_).isCeleb) {

                    ProfileDataModel profileDataModel = new ProfileDataModel();
                    profileDataModel._id = profileList_.get(position_).id;
                    profileDataModel.firstName = profileList_.get(position_).firstName;
                    profileDataModel.lastName = profileList_.get(position_).lastName;
                    profileDataModel.isCeleb = profileList_.get(position_).isCeleb;
                    profileDataModel.role = "";
                    profileDataModel.avtar_imgPath = profileList_.get(position_).avtarImgPath;
                    if (profileList_.get(position_).isOnline != null) {
                        profileDataModel.isOnline = profileList_.get(position_).isOnline;
                    }
                    profileDataModel.profession = profileList_.get(position_).profession;
                    profileDataModel.aboutMe = "";

                    Common.getInstance().viewCelebProfileDialog(context, profileDataModel, progressDialog);

                } else if (profileList_.get(position_).isManager != null && profileList_.get(position_).isManager) {
                    Intent profileIntent = new Intent(context, HelperActivity.class);
                    profileIntent.putExtra(Constants.FRAGMENT_KEY, 8029);
                    profileIntent.putExtra(Constants.FRAGMENT_TITLE, "Member Profile");
                    profileIntent.putExtra("fromSearchResult", false);
                    profileIntent.putExtra("isUserProfile", true);
                    profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (profileList_.get(position_).isOnline != null) {
                        if (profileList_.get(position_).isOnline) {
                            profileIntent.putExtra("celeb_status", "online");
                        } else {
                            profileIntent.putExtra("celeb_status", "offline");
                        }
                    }
                    profileIntent.putExtra("CelebId", profileList_.get(position_).id);
                    profileIntent.putExtra("PROFILE_NAME", profileList_.get(position_).firstName + " " + profileList_.get(position_).lastName);
                    profileIntent.putExtra("Imaage", ApiClient.BASE_URL + profileList_.get(position_).avtarImgPath);
                    profileIntent.putExtra("proffession", profileList_.get(position_).profession);
                    profileIntent.putExtra("ABOUT", profileList_.get(position_).aboutMe);
                    profileIntent.putExtra("IS_MANAGER", "TRUE");
                    profileIntent.putExtra("MANAGER_ID", profileList_.get(position_).id);


                    context.startActivity(profileIntent);
                } else {
                    Intent profileIntent = new Intent(context, HelperActivity.class);
                    profileIntent.putExtra(Constants.FRAGMENT_KEY, 8029);
                    profileIntent.putExtra(Constants.FRAGMENT_TITLE, "Member Profile");
                    profileIntent.putExtra("fromSearchResult", false);
                    profileIntent.putExtra("isUserProfile", true);
                    profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (profileList_.get(position_).isOnline != null) {
                        if (profileList_.get(position_).isOnline) {
                            profileIntent.putExtra("celeb_status", "online");
                        } else {
                            profileIntent.putExtra("celeb_status", "offline");
                        }
                    }
                    profileIntent.putExtra("CelebId", profileList_.get(position_).id);
                    profileIntent.putExtra("PROFILE_NAME", profileList_.get(position_).firstName + " "
                            + profileList_.get(position_).lastName);
                    profileIntent.putExtra("Imaage", ApiClient.BASE_URL + profileList_.get(position_).avtarImgPath);
                    profileIntent.putExtra("proffession", profileList_.get(position_).profession);
                    profileIntent.putExtra("ABOUT", profileList_.get(position_).aboutMe);
                    profileIntent.putExtra("IS_MANAGER", "FALSE");
                    profileIntent.putExtra("MANAGER_ID", profileList_.get(position_).id);


                    context.startActivity(profileIntent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void conformationPopup(final String celebId, int deletePos, String pic, String name, String role) {

        promoDialog = new Dialog(context);
        promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        promoDialog.setContentView(R.layout.profile_popup);

        content_profile = (TextView) promoDialog.findViewById(R.id.content_profile);
        yesBtn = (Button) promoDialog.findViewById(R.id.profile_follow);
        noBtn = (Button) promoDialog.findViewById(R.id.profile_view);
        headtitle = (TextView) promoDialog.findViewById(R.id.profile_role);
        mCeleb_Name = (TextView) promoDialog.findViewById(R.id.profile_name);
        profile_pic = (CircleImageView) promoDialog.findViewById(R.id.profile_pic);
        TextView avail_creditstxt = (TextView) promoDialog.findViewById(R.id.avail_creditstxt);
        avail_creditstxt.setVisibility(View.VISIBLE);

        if (SessionManager.userLogin.mainCredits != null) {
            avail_creditstxt.setText(Common.getInstance().getCreditBalancetoShowInLabel());
        } else {
            avail_creditstxt.setVisibility(View.GONE);
        }

        content_profile.setVisibility(View.VISIBLE);
        mCeleb_Name.setText(name);
        headtitle.setText(role);
        if (pic != null && !pic.isEmpty()) {
            Glide.with(context).load(ApiClient.BASE_URL + pic).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.logo_with_text).into(profile_pic);
        } else {
            Glide.with(context).load(R.drawable.logo_with_text).diskCacheStrategy(DiskCacheStrategy.ALL).into(profile_pic);
        }


        switch (tabName) {

            case Constants.ProfilesConstants.FANS_OF_TAB:
                avail_creditstxt.setVisibility(View.GONE);
                mCeleb_Name.setText("CelebKonect");
                headtitle.setVisibility(View.GONE);
                profile_pic.setVisibility(View.GONE);
                yesBtn.setText("Yes");
                noBtn.setText("No");
                content_profile.setText("Are you sure you want to block " + name + "?");
                break;
           /* case Constants.ProfilesConstants.FOLLOWERS_TAB:
                break;*/
        }

        promoDialog.show();
        IApiListener iApiListenerFollowUnFollow = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                if (condition.equals(ApiClient.UNFOLLOW_CELEB)) {
                    try {
                        followersFragmentClass.deleteListItem(deletePos);
                        Common.getInstance().cusToast(context, "You have stopped following "
                                + profileinfo.get(getClickedPosition() - 1).firstName);
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

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promoDialog.dismiss();

                switch (tabName) {
                    /*case Constants.ProfilesConstants.FAN_TAB:
                        break;
                    case Constants.ProfilesConstants.FOLLOWING_TAB:
                        removingCelebrityFollow(celebId, deletePos);
                        break;*/

                    case Constants.ProfilesConstants.FANS_OF_TAB:
                        break;
                    /*case Constants.ProfilesConstants.FOLLOWERS_TAB:
                        break;*/
                }
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promoDialog.dismiss();
            }
        });


    }

    @Override
    public int getItemCount() {
        return profileinfo.size();
    }


    private void buttonAction(int clickedPositionLocal) {
        clickedPosition = clickedPositionLocal;
        Log.e("tabName", tabName + "12");

        switch (tabName) { //Following

            case Constants.ProfilesConstants.FAN_TAB:
                    IApiListener iApiListener = new IApiListener() {
                        @Override
                        public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                            profileinfo.remove(getClickedPosition());
                            notifyDataSetChanged();

                            if (ProfilesListFragment.getInstance() != null) {
                                if (profileinfo == null || profileinfo.size() <= 0) {
                                    ProfilesListFragment.getInstance().setEmptyDataList();
                                }
                                ProfilesListFragment.getInstance().updateTabTotalCount();
                            }
                        }

                        @Override
                        public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

                        }
                    };
                    FanUnFanData fanUnFanData = new FanUnFanData(context,
                            profileinfo.get(getClickedPosition()).id, 0.0,
                            profileinfo.get(getClickedPosition()).firstName,
                            profileinfo.get(getClickedPosition()).lastName,
                            profileinfo.get(getClickedPosition()).avtarImgPath,
                            profileinfo.get(getClickedPosition()).profession);
                    Common.getInstance().fanUnFanConfirmationDialog(false, fanUnFanData, iApiListener);
                break;
            case Constants.ProfilesConstants.FOLLOWING_TAB:
                    IApiListener iApiListenerFollowUnFollow = new IApiListener() {
                        @Override
                        public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                            if (condition.equals(ApiClient.UNFOLLOW_CELEB)) {
                                try {
                                    Common.getInstance().cusToast(context, "You have stopped following "
                                            + profileinfo.get(getClickedPosition()).firstName);

                                    followersFragmentClass.deleteListItem(getClickedPosition());
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
                    Common.getInstance().followUnFollow(context, false, profileinfo.get(getClickedPosition()).id,
                            true, iApiListenerFollowUnFollow);


                break;
            case Constants.ProfilesConstants.FANS_OF_TAB:
                    Log.e("selectedPos", getClickedPosition() + "");
                    conformationPopup(profileinfo.get(getClickedPosition()).id, getClickedPosition()
                            , profileinfo.get(getClickedPosition()).avtarImgPath
                            , profileinfo.get(getClickedPosition()).firstName + " "
                                    + profileinfo.get(getClickedPosition()).lastName,
                            profileinfo.get(getClickedPosition()).profession);
                break;
            case Constants.ProfilesConstants.BLOCKS_TAB:

                unblock("Hi, you have blocked " + profileinfo.get(getClickedPosition()).firstName + " " + profileinfo.get(getClickedPosition()).lastName + " on '" +
                        Common.getInstance().getDateSection(profileinfo.get(getClickedPosition()).blockedDate) + "' due to '" + profileinfo.get(getClickedPosition()).feedback + "'", getClickedPosition());

                break;


        }
    }


    private void navigateSelfProfile() {
        Intent intent = new Intent(context, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_KEY, 8038);
        intent.putExtra(Constants.FRAGMENT_TITLE, "My Profile");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.profileImage)
        CircleImageView profileImage;

        @BindView(R.id.isCelebVerifiedImage)
        ImageView isCelebVerifiedImage;

        @BindView(R.id.profilenametxt)
        TextView profilenametxt;

        @BindView(R.id.professiontxt)
        TextView professiontxt;

        @BindView(R.id.deactiveBtn)
        Button deactiveBtn;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

    private void removingCelebrityFan(final String removingcelebId, final int deletePosLocal, String blockreason) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        String url = ApiClient.BLOCK_USER + removingcelebId;
        JSONObject params = new JSONObject();
        try {
            params.put("CelebrityId", SessionManager.userLogin.userId);
            params.put("feedback", blockreason);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                params.toString());
        Call<ApiResponseModel> call = apiInterface.PUT(url, requestBody);

        IApiListener iApiListenerc = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                if (condition.equals("removingCelebrityFan")) {
                    try {

                        followersFragmentClass.deleteListItem(deletePosLocal);
                        if (MyFanFollowersFragment.getInstance() != null) {
                            MyFanFollowersFragment.getInstance().setAdapterRef();
                        }
                        Toast.makeText(context, apiResponseModel.message, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                if (condition.equals("removingCelebrityFan")) {

                }
            }
        };

        Common.getInstance().callAPI(new ApiRequestModel().setModel(context, call, "removingCelebrityFan",
                true, iApiListenerc, true));
    }


    private void removingCelebrityFollow(final String removingcelebId, final int deletePos) {
        String url = ApiClient.UNFOLLOW_CELEB + SessionManager.userLogin.userId;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject params = new JSONObject();
        try {
            params.put("userId", SessionManager.userLogin.userId);
            params.put("CelebrityId", removingcelebId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params.toString());
        Call<ApiResponseModel> call = apiInterface.PUT(url, requestBody);

        IApiListener iApiListenerc = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                if (condition.equals("removingCelebrityFollow")) {
                    try {

                        followersFragmentClass.deleteListItem(deletePos);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

            }
        };

        Common.getInstance().callAPI(new ApiRequestModel().setModel(context, call, "removingCelebrityFollow", true, iApiListenerc, false));
    }


    private void removingUnblockfan(final String removingcelebId, final int deletePosLocal, String blockreason) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        String url = Constants.ProfilesConstants.UNBLOCK;
        JSONObject params = new JSONObject();
        try {
            params.put("celebrityId", SessionManager.userLogin.userId);
            params.put("memberId", removingcelebId);
            params.put("feedback", blockreason);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                params.toString());
        Call<ApiResponseModel> call = apiInterface.POST(url, requestBody);

        IApiListener iApiListenerc = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                if (condition.equals("removingUnblockFan")) {
                    try {

                        followersFragmentClass.deleteListItem(deletePosLocal);
                        Toast.makeText(context, apiResponseModel.message, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

            }
        };

        Common.getInstance().callAPI(new ApiRequestModel().setModel(context, call, "removingUnblockFan",
                true, iApiListenerc, false));
    }


    public int getClickedPosition() {
        return clickedPosition;
    }


    /*@Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equalsIgnoreCase(Constants.UN_FAN_STATUS)) {

            followersFragmentClass.deleteListItem(clickedPosition);
//            profileinfo.remove((int) clickedPosition);
//            notifyDataSetChanged();
        }
    }*/
    /*@Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

    }*/


    public void loadmore(ArrayList<ProfileInfo> celebrityList) {
        profileinfo = celebrityList;
        notifyDataSetChanged();
        /*int positionStart = getItemCount() + 1;
        notifyItemRangeInserted(positionStart, appendList.size());*/
    }

    private void unblock(final String forceMsg, int deletedPosition) {
        final Dialog unblock_popup;
        TextView take_photo_txt;
        Button yesBtn, noBtn;
        unblock_popup = new Dialog(context);
        unblock_popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        unblock_popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        unblock_popup.setCancelable(false);
        unblock_popup.setContentView(R.layout.unblock_popup);

        take_photo_txt = (TextView) unblock_popup.findViewById(R.id.greetingmsgtxt);
        if (forceMsg != null && !forceMsg.isEmpty()) {
            take_photo_txt.setText(forceMsg);
        } else {
            take_photo_txt.setText("");
        }


        noBtn = (Button) unblock_popup.findViewById(R.id.noBtn);
        yesBtn = (Button) unblock_popup.findViewById(R.id.yesBtn);
        unblock_popup.show();

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unblock_popup.dismiss();
            }
        });
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unblock_popup.dismiss();
            }
        });
    }
}
