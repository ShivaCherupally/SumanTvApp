package info.sumantv.flow.menu_list.MyCelebrities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.ModelClass.FanUnFanData;
import info.sumantv.flow.ModelClass.ProfileDataModel;
import info.sumantv.flow.RecommededCelebProfiles.ProfileData;

import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.viewholders.SkeletonViewHolder;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;

import info.sumantv.flow.menu_list.MyCelebrities.MyCelebritiesActivity.CelebFollowingFragment.CelebFollowingFragment;
import info.sumantv.flow.menu_list.MyCelebrities.MyCelebritiesActivity.FansOfFragment.FansOfFragment;
import info.sumantv.flow.menu_list.MyFansFollowers.FansFragment.FansFragment;
import info.sumantv.flow.menu_list.MyFansFollowers.FollowersFragment.FollowersFragment;

import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.userflow.Util.DateUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import org.json.JSONObject;

import retrofit2.Call;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Shiva on 2/24/2018.
 */

public class MyCelebAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IApiListener {
    private Context context;
    private ArrayList<ProfileData> _profileList;
    String COMING_HEADER;
    String comingpage;
    private Dialog promoDialog;
    Button yesBtn, noBtn;
    private ProgressDialog progressDialog;
    TextView content_profile, headtitle, mCeleb_Name;
    CircleImageView profile_pic;
    int deletedPosition;
    int currentItemPostion;
    boolean isFromCelebProfile;

    FansOfFragment fragmentclass;
    CelebFollowingFragment fragmentclassfollowing;
    FansFragment fansFragmenClass;
    FollowersFragment followersFragmentClass;
    ApiInterface apiInterface;
    IApiListener iApiListener;
    String memberreason = "";
    int clickedPosition = -1;
    public RController rController;


    public MyCelebAdapter(RController rController) {
        this.rController = rController;
    }


    public MyCelebAdapter(FansOfFragment fragmentclass, Context context, ArrayList<ProfileData> _profileList,
                          String comingpage) {
        this._profileList = _profileList;
        this.context = context;
        this.comingpage = comingpage;
        this.fragmentclass = fragmentclass;
        this.rController = RController.LOADED;
    }

    public MyCelebAdapter(CelebFollowingFragment fragmentclass, Context context, ArrayList<ProfileData> _profileList,
                          String comingpage) {
        this._profileList = _profileList;
        this.context = context;
        this.comingpage = comingpage;
        this.fragmentclassfollowing = fragmentclass;
        this.rController = RController.LOADED;
    }

    public MyCelebAdapter(FansFragment fragmentclass, Context context, ArrayList<ProfileData> _profileList,
                          String comingpage, boolean isFromCelebProfile) {
        this._profileList = _profileList;
        this.context = context;
        this.comingpage = comingpage;
        this.isFromCelebProfile = isFromCelebProfile;
        this.fansFragmenClass = fragmentclass;
        this.rController = RController.LOADED;
    }

    public MyCelebAdapter(FollowersFragment fragmentclass, Context context, ArrayList<ProfileData> _profileList,
                          String comingpage, boolean isFromCelebProfile) {
        this._profileList = _profileList;
        this.context = context;
        this.comingpage = comingpage;
        this.isFromCelebProfile = isFromCelebProfile;
        this.followersFragmentClass = fragmentclass;
        this.rController = RController.LOADED;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        iApiListener = this;

        View view;


        if (rController == RController.LOADING) {
            int layout = R.layout.skeleton_my_celebitem;
//            return new SkeletonViewHolder(LayoutInflater.from(
//                    parent.getContext()).inflate(layout, parent, false));

            return new SkeletonViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(layout, parent, false));
        } else {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_my_celeb_item_new, parent, false);
            MyCelebAdapter.MyViewHolder myViewHolder = new MyCelebAdapter.MyViewHolder(view);
            return myViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderGlobal, int position) {

//    }
//
//    @Override
//    public void onBindViewHolder(final MyCelebAdapter.MyViewHolder holder, final int position) {

        if (holderGlobal instanceof MyCelebAdapter.MyViewHolder) {

            MyCelebAdapter.MyViewHolder holder = (MyCelebAdapter.MyViewHolder) holderGlobal;

            currentItemPostion = position;


            if (_profileList.get(position).getName() != null && !_profileList.get(position).getName().isEmpty()) {
                holder.user_name.setText(Common.convertCaseSensitive(_profileList.get(position).getName() + " " + _profileList.get(position).getLastName()));
            }


//        holder.spentdollor.setText(String.valueOf(_profileList.get(position).getCumulativeCreditValue()));

            if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_MEMBER_OR_CELEB_PROFILE, "") != null &&
                    !SessionManager.getInstance().getKeyValue(SessionManager.KEY_MEMBER_OR_CELEB_PROFILE, "").isEmpty()) {
                if (!SessionManager.getInstance().getKeyValue(SessionManager.KEY_MEMBER_OR_CELEB_PROFILE, "").equals("My Celebrities")) {
                    if (_profileList.get(position).getCeleb()) {
                        holder.online_profileCheck.setVisibility(View.VISIBLE);

                    } else {
                        holder.online_profileCheck.setVisibility(View.GONE);
                    }
                } else {
                    holder.online_profileCheck.setVisibility(View.VISIBLE);
                }
            } else {
                holder.online_profileCheck.setVisibility(View.VISIBLE);
            }
            if (_profileList.get(position).getLastActivity() != null && !_profileList.get(position).getLastActivity().isEmpty()) {
                StringTokenizer tokens = new StringTokenizer(_profileList.get(position).getLastActivity(), "@");

                String serviceType = tokens.nextToken();// this will contain "Fruit"
                String scheduleDateAndTime = tokens.nextToken();
                Log.e("celebTime", scheduleDateAndTime);
                Log.e("ServerDate", scheduleDateAndTime);

                if (serviceType != null && !serviceType.isEmpty()) {
                    holder.phoneimg.setImageDrawable(context.getResources().getDrawable(R.color.emptycolor));
                    if (serviceType.equals("video")) {
                        holder.phoneimg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pop_up_video_call));
                    } else if (serviceType.equals("audio")) {
                        holder.phoneimg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pop_up_call));
                    } else if (serviceType.equals("chat")) {
                        holder.phoneimg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pop_up_chat));
                    }
                } else {
                    holder.phoneimg.setVisibility(View.GONE);
                }
                if (scheduleDateAndTime != null && !scheduleDateAndTime.isEmpty()) {

                    holder.datetxt.setVisibility(View.GONE);
                    holder.datetxt.setText(DateUtil.getDateMonthNameandYear(scheduleDateAndTime));
                }
                if (scheduleDateAndTime != null && !scheduleDateAndTime.isEmpty()) {
                    Log.e("scheduleDateAndTimeSd", scheduleDateAndTime + "");
                    holder.timetxt.setText(DateUtil.getDateMonthNameandYear(scheduleDateAndTime) + " , " + DateUtil.getServerTimeInUTC(scheduleDateAndTime));
                }
            } else {
                holder.phoneimg.setVisibility(View.GONE);
            }

//        Logger.e("NameProfile", _profileList.get(position).getName());
//        Logger.e("ProfilePic", _profileList.get(position).getAvtar_imgPath());


            if (_profileList.get(position).getAvtar_imgPath() != null && !_profileList.get(position).getAvtar_imgPath().isEmpty()) {
                Glide.with(context).load(ApiClient.BASE_URL + _profileList.get(position).getAvtar_imgPath())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_profile_circle_pleace_holder)
                        .into(holder.user_image);
            } else {
//            Glide.with(context).load(R.drawable.ic_grey_celebkonect_logo).into(holder.user_image);
                holder.user_image.setImageResource(R.drawable.ic_profile_circle_pleace_holder);
            }

            try {
                if (_profileList.get(position).getCeleb() != null && _profileList.get(position).getCeleb()) {
                    holder.occupationtv.setText(Html.fromHtml(_profileList.get(position).getProfession()));
                    holder.occupationtv.setVisibility(View.VISIBLE);
                } else {
                    holder.occupationtv.setVisibility(View.GONE);
                }
            } catch (Exception e) {

            }


            if (comingpage != null && !comingpage.isEmpty()) {
                if (comingpage.equals("FANS_OF")) {
                    holder.cancelBtn.setText("Fanned");
                    holder.cancelBtn.setVisibility(View.VISIBLE);
                    holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FanUnFanData fanUnFanData = new FanUnFanData(context, _profileList.get(position).get_id(), 0.0, _profileList.get(position).getName(), _profileList.get(position).getLastName(), _profileList.get(position).getAvtar_imgPath(), _profileList.get(position).getProfession());
                            Common.getInstance().fanUnFanConfirmationDialog(false, fanUnFanData, iApiListener);
                            clickedPosition = position;
                        }
                    });
                } else if (comingpage.equals("FOLLOWERS")) {
                    holder.cancelBtn.setText("Unfollow");
                    holder.cancelBtn.setVisibility(View.VISIBLE);
                    holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("selectedPos", position + "");


                            //Next Build its required
                                /*conformationPopup(_profileList.get(position).get_id(), holder.itemLayout, comingpage, position
                                        , _profileList.get(position).getAvtar_imgPath()
                                        , _profileList.get(position).getName() + " " + _profileList.get(position).getLastName(),
                                        _profileList.get(position).getProfession());*/

                            IApiListener iApiListenerFollowUnFollow = new IApiListener() {
                                @Override
                                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
//                                        if (condition.equals(ApiClient.FOLLOW_CELEB)) {
//                                            //
//                                        } else
                                    if (condition.equals(ApiClient.UNFOLLOW_CELEB)) {
                                        try {
                                            Common.getInstance().cusToast(context, "You have stopped following " + _profileList.get(position).getName());
                                            fragmentclassfollowing.deleteListItem(position);
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
                            Common.getInstance().followUnFollow(context, false, _profileList.get(position).get_id(),
                                    true, iApiListenerFollowUnFollow);


                        }
                    });
                } else if (comingpage.equals("FANS")) {
                    holder.cancelBtn.setText("Block");
                    if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_SELF_MEMBER_PROFILE, "") != null &&
                            !SessionManager.getInstance().getKeyValue(SessionManager.KEY_SELF_MEMBER_PROFILE, "").isEmpty()) {
                        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_SELF_MEMBER_PROFILE, "").equals("TRUE")) {
                            holder.cancelBtn.setVisibility(View.GONE);
                        } else {
                            holder.cancelBtn.setVisibility(View.VISIBLE);
                        }
                    } else {
                        holder.cancelBtn.setVisibility(View.VISIBLE);
                    }
                    holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Log.e("selectedPos", position + "");
                                conformationPopup(_profileList.get(position).get_id(), holder.itemLayout, comingpage, position
                                        , _profileList.get(position).getAvtar_imgPath()
                                        , _profileList.get(position).getName() + " " + _profileList.get(position).getLastName(),
                                        _profileList.get(position).getProfession());
                        }
                    });
                } else if (comingpage.equals("FOLLOWER")) {
                    holder.cancelBtn.setText("Block");
                    holder.cancelBtn.setVisibility(View.GONE);
                    holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("selectedPos", position + "");
                            conformationPopup(_profileList.get(position).get_id(), holder.itemLayout, comingpage, position
                                    , _profileList.get(position).getAvtar_imgPath()
                                    , _profileList.get(position).getName() + " " + _profileList.get(position).getLastName(),
                                    _profileList.get(position).getProfession());
                        }
                    });
                } else {
//                holder.cancelBtn.setVisibility(View.VISIBLE);
                }
            }


            holder.user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean isSelf = false;
                    if (SessionManager.userLogin.userId != null &&
                            !SessionManager.userLogin.userId.isEmpty()) {
                        if (_profileList.get(position).get_id().equals(SessionManager.userLogin.userId)) {
                            Intent intent = new Intent(context, HelperActivity.class);
                            intent.putExtra(Constants.FRAGMENT_KEY, 8038);
                            intent.putExtra(Constants.FRAGMENT_TITLE, "My Profile");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            return;
                        }
                    }
                    goToProfilePage(_profileList, position);


                }
            });

//        holder.user_name.setText(_profileList.get(position).getName());
            holder.user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*boolean isSelf = false;
                    if (SessionManager.userLogin.userId != null && !SessionManager.userLogin.userId.isEmpty()) {
                        if (_profileList.get(position).get_id().equals(SessionManager.userLogin.userId)) {
                            isSelf = true;
                        }
                    }
                    navigatingToProfile(position, isSelf);*/

                    boolean isSelf = false;
                    if (SessionManager.userLogin.userId != null &&
                            !SessionManager.userLogin.userId.isEmpty()) {
                        if (_profileList.get(position).get_id().equals(SessionManager.userLogin.userId)) {
                            Intent intent = new Intent(context, HelperActivity.class);
                            intent.putExtra(Constants.FRAGMENT_KEY, 8038);
                            intent.putExtra(Constants.FRAGMENT_TITLE, "My Profile");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            return;
                        }
                    }
                    goToProfilePage(_profileList, position);
                }
            });

            if (comingpage.equals("FOLLOWER")) {
                holder.cancelBtn.setVisibility(View.GONE);

                if (isFromCelebProfile) {
                    holder.relative_hist.setVisibility(View.GONE);
                } else {
                    holder.relative_hist.setVisibility(View.VISIBLE);
                }
            } else {
                if (isFromCelebProfile) {
                    holder.cancelBtn.setVisibility(View.GONE);
                    holder.relative_hist.setVisibility(View.GONE);
                } else {
                    holder.cancelBtn.setVisibility(View.VISIBLE);
                    holder.relative_hist.setVisibility(View.VISIBLE);
                }
            }
//        }
        }
    }

    private void goToProfilePage(ArrayList<ProfileData> profileList_, int position_) {

        try {
            if (profileList_.get(position_).getCeleb() != null) {
                if (profileList_.get(position_).getCeleb()) {

                    ProfileDataModel profileDataModel = new ProfileDataModel();
                    profileDataModel._id = profileList_.get(position_).get_id();
                    profileDataModel.firstName = profileList_.get(position_).getName();
                    profileDataModel.lastName = profileList_.get(position_).getLastName();
                    ;
                    profileDataModel.isCeleb = profileList_.get(position_).getCeleb();
                    profileDataModel.role = "";
                    profileDataModel.avtar_imgPath = profileList_.get(position_).getAvtar_imgPath();
                    if (profileList_.get(position_).getOnline() != null) {
                        profileDataModel.isOnline = profileList_.get(position_).getOnline();
                    }
                    profileDataModel.profession = profileList_.get(position_).getProfession();
                    profileDataModel.aboutMe = "";
                 /*   profileDataModel.celeAudioCharge = profileList_.get(position_).celeAudioCharge;
                    profileDataModel.celeFanCharge =profileList_.get(position_).celeFanCharge;
                    profileDataModel.celeVideoCharge = profileList_.get(position_).celeVideoCharge;*/
                    //profileDataModel.isFan = isfan;//Need to get from Backend - IS_FAN

//                    Common.getInstance().viewCelebProfileDialog(context, profileDataModel, progressDialog);
                    //checking with customize class
                    Common.getInstance().viewCelebProfileDialog(context, profileDataModel, progressDialog);

                } else if (profileList_.get(position_).getManager() != null && profileList_.get(position_).getManager()) {
                    Intent profileIntent = new Intent(context, HelperActivity.class);
                    profileIntent.putExtra(Constants.FRAGMENT_KEY, 8029);
                    profileIntent.putExtra(Constants.FRAGMENT_TITLE, "Member Profile");
                    profileIntent.putExtra("fromSearchResult", false);
                    profileIntent.putExtra("isUserProfile", true);
                    profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (profileList_.get(position_).getOnline() != null) {
                        if (profileList_.get(position_).getOnline()) {
                            profileIntent.putExtra("celeb_status", "online");
                        } else {
                            profileIntent.putExtra("celeb_status", "offline");
                        }
                    }
                    profileIntent.putExtra("CelebId", profileList_.get(position_).get_id());
                    profileIntent.putExtra("PROFILE_NAME", profileList_.get(position_).getName() + " " + profileList_.get(position_).getLastName());
                    profileIntent.putExtra("Imaage", ApiClient.BASE_URL + profileList_.get(position_).getAvtar_imgPath());
                    profileIntent.putExtra("proffession", profileList_.get(position_).getProfession());
                    profileIntent.putExtra("ABOUT", profileList_.get(position_).getAboutMe());
                    profileIntent.putExtra("IS_MANAGER", "TRUE");
                    profileIntent.putExtra("MANAGER_ID", profileList_.get(position_).get_id());


                    context.startActivity(profileIntent);
                } else {
                    Intent profileIntent = new Intent(context, HelperActivity.class);
                    profileIntent.putExtra(Constants.FRAGMENT_KEY, 8029);
                    profileIntent.putExtra(Constants.FRAGMENT_TITLE, "Member Profile");
                    profileIntent.putExtra("fromSearchResult", false);
                    profileIntent.putExtra("isUserProfile", true);
                    profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (profileList_.get(position_).getOnline() != null) {
                        if (profileList_.get(position_).getOnline()) {
                            profileIntent.putExtra("celeb_status", "online");
                        } else {
                            profileIntent.putExtra("celeb_status", "offline");
                        }
                    }
                    profileIntent.putExtra("CelebId", profileList_.get(position_).get_id());
                    profileIntent.putExtra("PROFILE_NAME", profileList_.get(position_).getName() + " "
                            + profileList_.get(position_).getLastName());
                    profileIntent.putExtra("Imaage", ApiClient.BASE_URL + profileList_.get(position_).getAvtar_imgPath());
                    profileIntent.putExtra("proffession", profileList_.get(position_).getProfession());
                    profileIntent.putExtra("ABOUT", profileList_.get(position_).getAboutMe());
                    profileIntent.putExtra("IS_MANAGER", "FALSE");
                    profileIntent.putExtra("MANAGER_ID", profileList_.get(position_).get_id());


                    context.startActivity(profileIntent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void conformationPopup(final String celebId, final LinearLayout itemlayout, final String comingpagelocal
            , final int deletePos, String pic, String name, String role) {

        promoDialog = new Dialog(context);
        promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        promoDialog.setCancelable(false);
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
            Glide.with(context)
                    .load(ApiClient.BASE_URL + pic)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.logo_with_text)
                    .into(profile_pic);
        } else {
            Glide.with(context)
                    .load(R.drawable.logo_with_text)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profile_pic);
        }


        if (comingpagelocal.equals("FANS_OF")) {
            yesBtn.setText("Yes");
            noBtn.setText("No");
            content_profile.setText("Are you sure you want to cancel the fan subscription for this celebrity ?");
        } else if (comingpagelocal.equals("FOLLOWERS")) {
            yesBtn.setText("Yes");
            noBtn.setText("No");
            content_profile.setText("Are you sure you want to unfollow this celebrity?");
        } else if (comingpagelocal.equals("FANS")) {
            avail_creditstxt.setVisibility(View.GONE);
            mCeleb_Name.setText("CelebKonect");
            headtitle.setVisibility(View.GONE);
            profile_pic.setVisibility(View.GONE);
            yesBtn.setText("Yes");
            noBtn.setText("No");
            content_profile.setText("Are you sure you want to block " + name + "?");
        } else if (comingpagelocal.equals("FOLLOWER")) {
        }

        promoDialog.show();


        IApiListener iApiListenerFollowUnFollow = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                if (condition.equals(ApiClient.FOLLOW_CELEB)) {
                    //
                } else if (condition.equals(ApiClient.UNFOLLOW_CELEB)) {
                    try {
                        fragmentclassfollowing.deleteListItem(deletePos);
                        Common.getInstance().cusToast(context, "You have stopped following " + _profileList.get(deletePos).getName());
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
                Log.e("deletePos", deletePos + "");
                if (comingpagelocal.equals("FANS_OF")) {
                    //feedBackDialog(context, deletePos);
                } else if (comingpagelocal.equals("FOLLOWERS")) {
                    Common.getInstance().followUnFollow(context, false, celebId, true, iApiListenerFollowUnFollow);
                } else if (comingpagelocal.equals("FANS")) {
                } else if (comingpagelocal.equals("FOLLOWER")) {
                    removingCelebrityFollow(celebId, itemlayout, deletePos);
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
        return rController == RController.LOADING ?
                10 : _profileList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView user_image, profileCheck;
        ImageView online_profileCheck;
        private TextView user_name, occupationtv, datetxt, timetxt;
        boolean checked = true;
        RelativeLayout relative_hist;
        Button cancelBtn;
        LinearLayout itemLayout;
        ImageView phoneimg;


        public MyViewHolder(View itemView) {
            super(itemView);
            user_image = (CircleImageView) itemView.findViewById(R.id.user_image);
            user_name = (TextView) itemView.findViewById(R.id.starname_tv);
            occupationtv = (TextView) itemView.findViewById(R.id.occupationtv);
            cancelBtn = (Button) itemView.findViewById(R.id.cancelBtn);
            relative_hist = (RelativeLayout) itemView.findViewById(R.id.relative_hist);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.itemLayout);
            datetxt = (TextView) itemView.findViewById(R.id.datetxt);
            timetxt = (TextView) itemView.findViewById(R.id.timetxt);
            phoneimg = (ImageView) itemView.findViewById(R.id.phoneimg);
            online_profileCheck = (ImageView) itemView.findViewById(R.id.online_profileCheck);
            user_image.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
        }
    }

    private void removingCelebrityFan(final String removingcelebId, final int deletePos, String blockreason) {
        //   progressDialog = Common.showProgressDialog(context, progressDialog);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        String url = ApiClient.BLOCK_USER + removingcelebId;//SessionManager.userLogin.userId;
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

                        fansFragmenClass.deleteListItem(deletePos);
                        Toast.makeText(context, apiResponseModel.message, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants,
                                         ApiResponseModel apiResponseModel) {
                if (condition.equals("removingCelebrityFan")) {

                }
            }
        };

        Common.getInstance().callAPI(new ApiRequestModel().setModel(context, call, "removingCelebrityFan",
                true, iApiListenerc, true));
    }


    private void removingCelebrityFollow(final String removingcelebId, final LinearLayout itemlayout, final int deletePos) {
        // progressDialog = Common.showProgressDialog(context, progressDialog);


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


    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equalsIgnoreCase(Constants.UN_FAN_STATUS)) {
            _profileList.remove((int) clickedPosition);
            notifyDataSetChanged();
        }
    }

    public int getClickedPosition() {
        return clickedPosition;
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

    }

}
