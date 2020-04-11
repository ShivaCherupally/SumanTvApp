//package info.celebkonnect.flow.bottommenu.allcelebritiestab;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.*;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import info.celebkonnect.flow.ModelClass.FanUnFanData;
//import info.celebkonnect.flow.audiovideocall.AudioVideoApiCalls;
//import info.celebkonnect.flow.bottommenu.activity.HelperActivity;
//import info.celebkonnect.flow.bottommenu.constants.RController;
//import info.celebkonnect.flow.bottommenu.viewholders.SkeletonViewHolder;
//import info.celebkonnect.flow.celebflow.R;
//import info.celebkonnect.flow.celebflow.constants.Constants;
//import info.celebkonnect.flow.chat.models.ChatDataConvertModel;
//import info.celebkonnect.flow.managermodule.switchingprofiles.CommonAccessPermissionOfCeleb;
//import info.celebkonnect.flow.retrofitcall.ApiClient;
//import info.celebkonnect.flow.retrofitcall.ApiResponseModel;
//import info.celebkonnect.flow.retrofitcall.EnumConstants;
//import info.celebkonnect.flow.retrofitcall.IApiListener;
//import info.celebkonnect.flow.retrofitcall.SessionManager;
//import info.celebkonnect.flow.userflow.Util.Common;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * Created by Shiva on 2/24/2018.
// */
//
//public class CelebProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
//    private Context context;
//    public List<CelebProfileCompleteData> offlineOrCategoryList;
//    public List<CelebProfileCompleteData> offlineOrCategoryListFilter;
//    //    public String memberstatus;
//    View viewadpter;
//    TextView take_photo_txt, textViewProf, textViewFullProfile;
//    private CategoryCelebsFragment categoryCelebsFragment;
//    private ImageView close_popup;
//    //    String celebType;
//    String mSuccess_fan = "";
//    private Dialog promoDialog;
//    Button yesBtn, noBtn;
//    TextView mCeleb_Name;
//    private int mFans_count;
//    public Boolean isGrid = false;
//    public RController rController;
//    private int clickedPosition = -1;
//
//
//    public CelebProfileAdapter(CategoryCelebsFragment recommdedAvailableCeleb, Context context,
//                               List<CelebProfileCompleteData> offlineOrCategoryList,
//                               Boolean isGrid, RController rController) {
//        this.offlineOrCategoryList = offlineOrCategoryList;
//        this.offlineOrCategoryListFilter = offlineOrCategoryList;
//        this.context = context;
//        this.categoryCelebsFragment = recommdedAvailableCeleb;
//        this.isGrid = isGrid;
//        this.rController = rController;
//    }
//
//    public CelebProfileAdapter(RController rController) {
//        this.rController = rController;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
//
//        if (rController == RController.LOADING) {
//            int layout = R.layout.skeleton_my_celebitem;
//            return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
//        } else {
//            viewadpter = LayoutInflater.from(parent.getContext()).inflate(isGrid ? R.layout.celeb_profile_item : R.layout.celeb_profile_list_item,
//                    parent, false);
//            CelebProfileAdapter.MyViewHolder myViewHolder = new CelebProfileAdapter.MyViewHolder(viewadpter);
//            return myViewHolder;
//        }
//    }
//
//    public RController getRController() {
//        return rController;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof CelebProfileAdapter.MyViewHolder) {
//            CelebProfileAdapter.MyViewHolder viewHolder = (CelebProfileAdapter.MyViewHolder) holder;
//
//            if (offlineOrCategoryListFilter.get(position).getFirstName() != null && !offlineOrCategoryListFilter.get(position).getFirstName().isEmpty()) {
//                String CelebName = offlineOrCategoryListFilter.get(position).getFirstName();
//
//                if (offlineOrCategoryListFilter.get(position).getLastName() != null && !offlineOrCategoryListFilter.get(position).getLastName().isEmpty()) {
//                    CelebName = CelebName + " " + offlineOrCategoryListFilter.get(position).getLastName();
//                }
//
//                viewHolder.celeb_name_main.setText(Common.convertCaseSensitive(CelebName));
//            } else {
//                viewHolder.celeb_name_main.setText("");
//            }
//
//            if (offlineOrCategoryListFilter.get(position).getProfession() != null && !offlineOrCategoryListFilter.get(position).getProfession().isEmpty()) {
//                viewHolder.celeb_profession.setText(offlineOrCategoryListFilter.get(position).getProfession());
//            } else {
//                viewHolder.celeb_profession.setText("");
//            }
//
//            viewHolder.fanOrunfanBtn.setText(offlineOrCategoryListFilter.get(position).isFan ? "Fanned" : "Fan");
//            viewHolder.progressBarFan.setVisibility(offlineOrCategoryListFilter.get(position).isFanLoading ? View.VISIBLE : View.GONE);
//            viewHolder.fanOrunfanBtn.setVisibility(offlineOrCategoryListFilter.get(position).isFanLoading ? View.GONE : View.VISIBLE);
//
//
//            viewHolder.followorUnfollowBtn.setText(offlineOrCategoryListFilter.get(position).isFollower ? "UnFollow" : "Follow");
//            viewHolder.progressBarFollow.setVisibility(offlineOrCategoryListFilter.get(position).isFollowLoading ? View.VISIBLE : View.GONE);
//            viewHolder.followorUnfollowBtn.setVisibility(offlineOrCategoryListFilter.get(position).isFollowLoading ? View.GONE : View.VISIBLE);
//
//
//            try {
//                if (offlineOrCategoryListFilter.get(position).getAvtar_imgPath() != null && !offlineOrCategoryListFilter.get(position).getAvtar_imgPath().isEmpty()) {
//                    Glide.with(context).load(ApiClient.BASE_URL + offlineOrCategoryListFilter.get(position).getAvtar_imgPath())
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
//                            .error(R.drawable.ic_profile_square_pleace_holder)
//                            .into(viewHolder.celeb_profile_img);
//                } else {
//                    viewHolder.celeb_profile_img.setImageResource(R.drawable.ic_profile_square_pleace_holder);
//                }
//            } catch (Exception e) {
//            }
//
//
//            IApiListener apiListenerFanUnFan = new IApiListener() {
//                @Override
//                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
//                    if (condition.equals(Constants.FAN_STATUS)) {
//                        try {
//                            mSuccess_fan = "success";
//                            mFans_count = mFans_count + 1;
//                            //
//                            ChatDataConvertModel chatDataConvertModel = new ChatDataConvertModel();
//                            chatDataConvertModel._id = offlineOrCategoryListFilter.get(position).get_id();
//                            chatDataConvertModel.isFan = true;
//                            Common.getInstance().addFanUnFanChatDataConvertModel(chatDataConvertModel);
//                            //
//                            viewHolder.fanOrunfanBtn.setText("Fanned");
//                            offlineOrCategoryListFilter.get(position).setFan(true);
//                            notifyDataSetChanged();
//                            mSuccess_fan = "";
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else if (condition.equals(Constants.UN_FAN_STATUS)) {
//                        try {
//                            viewHolder.fanOrunfanBtn.setText("Fan");
//                            mSuccess_fan = "";
//                            mFans_count = mFans_count - 1;
//                            ChatDataConvertModel chatDataConvertModel = new ChatDataConvertModel();
//                            chatDataConvertModel._id = offlineOrCategoryListFilter.get(position).get_id();
//                            chatDataConvertModel.isFan = false;
//                            Common.getInstance().addFanUnFanChatDataConvertModel(chatDataConvertModel);
//                            offlineOrCategoryListFilter.get(position).setFan(false);
//                            notifyDataSetChanged();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
//                }
//            };
//
//            viewHolder.fanOrunfanBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    clickedPosition = position;
//                    FanUnFanData fanUnFanData = new FanUnFanData(context, offlineOrCategoryListFilter.get(position).get_id(), 0.0, offlineOrCategoryListFilter.get(position).getFirstName(), offlineOrCategoryListFilter.get(position).getLastName(), offlineOrCategoryListFilter.get(position).getAvtar_imgPath(), offlineOrCategoryListFilter.get(position).getProfession());
//                    Common.getInstance().getFanCredits(context, viewHolder.fanOrunfanBtn.getText().toString().equals("Fan"), fanUnFanData, apiListenerFanUnFan);
//                }
//            });
//
//            IApiListener iApiListenerFollowUnFollow = new IApiListener() {
//                @Override
//                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
//                    if (condition.equals(ApiClient.FOLLOW_CELEB)) {
//                        try {
//                            viewHolder.followorUnfollowBtn.setText("UnFollow");
//                            Common.getInstance().cusToast(context, "You are now a follower of " + offlineOrCategoryListFilter.get(position).getFirstName() + " " + offlineOrCategoryListFilter.get(position).getLastName());
//                            if (categoryCelebsFragment != null) {
//                                viewHolder.followorUnfollowBtn.setVisibility(View.VISIBLE);
//                                viewHolder.progressBarFollow.setVisibility(View.GONE);
//                                offlineOrCategoryListFilter.get(position).setFollower(true);
//                                notifyDataSetChanged();
//                                //
//                               /* CelebrityBaseFragment celebrityBaseFragment = CelebrityBaseFragment.getInstance();
//                                if (celebrityBaseFragment != null) {
//                                    celebrityBaseFragment.updateFanFollowStatus(null, true, offlineOrCategoryListFilter.get(position).get_id());
//                                }*/
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else if (condition.equals(ApiClient.UNFOLLOW_CELEB)) {
//                        try {
//                            viewHolder.followorUnfollowBtn.setText("Follow");
//                            Common.getInstance().cusToast(context, "You have stopped following " + offlineOrCategoryListFilter.get(position).getFirstName() + " " + offlineOrCategoryListFilter.get(position).getLastName());
//                            offlineOrCategoryListFilter.get(position).setFollower(true);
//                            notifyDataSetChanged();
//                            if (categoryCelebsFragment != null) {
//                                viewHolder.followorUnfollowBtn.setVisibility(View.VISIBLE);
//                                viewHolder.progressBarFollow.setVisibility(View.GONE);
//                                offlineOrCategoryListFilter.get(position).setFollower(false);
//                                notifyDataSetChanged();
//                                /*CelebrityBaseFragment celebrityBaseFragment = CelebrityBaseFragment.getInstance();
//                                if (celebrityBaseFragment != null) {
//                                    celebrityBaseFragment.updateFanFollowStatus(null, false, offlineOrCategoryListFilter.get(position).get_id());
//                                }*/
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
//                    viewHolder.followorUnfollowBtn.setVisibility(View.VISIBLE);
//                    viewHolder.progressBarFollow.setVisibility(View.GONE);
//                    Toast.makeText(context, "please check your network and try again", Toast.LENGTH_SHORT).show();
//                }
//            };
//
//            viewHolder.followorUnfollowBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (CommonAccessPermissionOfCeleb.fanFollowPermissonAvailabilty(context, false, false)) {
//                        viewHolder.followorUnfollowBtn.setVisibility(View.GONE);
//                        viewHolder.progressBarFollow.setVisibility(View.VISIBLE);
//                        clickedPosition = position;
//                        Common.getInstance().followUnFollow(context, viewHolder.followorUnfollowBtn.getText().toString().equals("Follow"), offlineOrCategoryListFilter.get(position).get_id(), false, iApiListenerFollowUnFollow);
//                    }
//                }
//            });
//
//            viewHolder.celeb_profile_img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        conformationPopup(position
//                                , offlineOrCategoryListFilter.get(position).getFirstName() + " " +
//                                        offlineOrCategoryListFilter.get(position).getLastName()
//                                , ApiClient.BASE_URL + offlineOrCategoryListFilter.get(position).getAvtar_imgPath()
//                                , offlineOrCategoryListFilter.get(position).getProfession(), viewHolder.fanOrunfanBtn);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//            viewHolder.celeb_name_main.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    conformationPopup(position
//                            , offlineOrCategoryListFilter.get(position).getFirstName() + " " +
//                                    offlineOrCategoryListFilter.get(position).getLastName()
//                            , ApiClient.BASE_URL + offlineOrCategoryListFilter.get(position).getAvtar_imgPath()
//                            , offlineOrCategoryListFilter.get(position).getProfession(), viewHolder.fanOrunfanBtn);
//
//                }
//            });
//            viewHolder.celeb_profession.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    conformationPopup(position
//                            , offlineOrCategoryListFilter.get(position).getFirstName() + " " +
//                                    offlineOrCategoryListFilter.get(position).getLastName()
//                            , ApiClient.BASE_URL + offlineOrCategoryListFilter.get(position).getAvtar_imgPath()
//                            , offlineOrCategoryListFilter.get(position).getProfession(), viewHolder.fanOrunfanBtn);
//
//                }
//            });
//
//            if (!isGrid) {
//                viewHolder.arrowimg.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        conformationPopup(position
//                                , offlineOrCategoryListFilter.get(position).getFirstName() + " " +
//                                        offlineOrCategoryListFilter.get(position).getLastName()
//                                , ApiClient.BASE_URL + offlineOrCategoryListFilter.get(position).getAvtar_imgPath()
//                                , offlineOrCategoryListFilter.get(position).getProfession(), viewHolder.fanOrunfanBtn);
//                    }
//                });
//            }
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//
//        return rController == RController.LOADING ?
//                10 : offlineOrCategoryListFilter.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
//    }
//
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//
//        private ImageView celeb_profile_img;
//        private TextView celeb_name_main;
//        private TextView celeb_profession;
//        private Button fanOrunfanBtn;
//        private ProgressBar progressBarFan, progressBarFollow;
//        private Button followorUnfollowBtn;
//        private ImageView arrowimg;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//
//            celeb_profile_img = (ImageView) itemView.findViewById(R.id.celeb_profile_img);
//            celeb_name_main = (TextView) itemView.findViewById(R.id.celeb_name_main);
//            celeb_profession = (TextView) itemView.findViewById(R.id.celeb_profession);
//            fanOrunfanBtn = (Button) itemView.findViewById(R.id.fanOrunfanBtn);
//            followorUnfollowBtn = (Button) itemView.findViewById(R.id.followorUnfollowBtn);
//            progressBarFollow = (ProgressBar) itemView.findViewById(R.id.progressBarFollow);
//            progressBarFan = (ProgressBar) itemView.findViewById(R.id.progressBarFan);
//
//            if (!isGrid) {
//                arrowimg = (ImageView) itemView.findViewById(R.id.arrowimg);
//            }
//
//        }
//
//    }
//
//    private void conformationPopup(final int position
//            , String profilename, String image, String role, Button fanOrunfanBtnLocal) {
//        ImageView profileIcon, messageIcon, callIcon, videoCallIcon;
//        ImageView imageViewProfilePic;
//
//        promoDialog = new Dialog(categoryCelebsFragment.getContext(), R.style.PauseDialog);
//        promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        promoDialog.setContentView(R.layout.profile_popup_action_items);
//        promoDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
//        noBtn = promoDialog.findViewById(R.id.profile_follow);
//        yesBtn = promoDialog.findViewById(R.id.profile_view);
//        take_photo_txt = promoDialog.findViewById(R.id.profile_role);
//        mCeleb_Name = promoDialog.findViewById(R.id.profile_name);
//        close_popup = promoDialog.findViewById(R.id.close_popup);
//        textViewFullProfile = promoDialog.findViewById(R.id.textViewFullProfile);
//        textViewProf = promoDialog.findViewById(R.id.textViewProf);
//
//        profileIcon = promoDialog.findViewById(R.id.profileIcon);
//        messageIcon = promoDialog.findViewById(R.id.messageIcon);
//        callIcon = promoDialog.findViewById(R.id.callIcon);
//        videoCallIcon = promoDialog.findViewById(R.id.videoCallIcon);
//        imageViewProfilePic = promoDialog.findViewById(R.id.imageViewProfilePic);
//
//        if (profilename != null) {
//            try {
//                mCeleb_Name.setText(Common.convertCaseSensitive(profilename));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//
//
//        if (role != null && !role.isEmpty()) {
//            textViewProf.setText(role);
//            textViewProf.setBackgroundColor(context.getResources().getColor(R.color.skyblueNew));
//
//            take_photo_txt.setText(role);
//
//        } else {
//            textViewProf.setBackgroundColor(context.getResources().getColor(R.color.transparent));
//        }
//
//
//        if (image != null && !image.isEmpty()) {
//            Glide.with(context).load(image).diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .error(R.drawable.ic_profile_square_pleace_holder)
//                    .into(imageViewProfilePic);
//        } else {
//            imageViewProfilePic.setImageResource(R.drawable.ic_profile_square_pleace_holder);
//        }
//
//        promoDialog.show();
//
//        profileIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                promoDialog.dismiss();
//                navigatingToProfile(position);
//            }
//        });
//        textViewFullProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                promoDialog.dismiss();
//                navigatingToProfile(position);
//            }
//        });
//        messageIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                promoDialog.dismiss();
//                navigatingChatScreen(position);
//            }
//        });
//
//        callIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                promoDialog.dismiss();
//
//                AudioVideoApiCalls.getInstance().checkCelebAvaialabiltyForCall(context,
//                        offlineOrCategoryListFilter.get(position).get_id(),
//                        Constants.AUDIO_CALL);
//
//            }
//        });
//
//        videoCallIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                promoDialog.dismiss();
//                AudioVideoApiCalls.getInstance().checkCelebAvaialabiltyForCall(context,
//                        offlineOrCategoryListFilter.get(position).get_id(),
//                        Constants.VIDEO_CALL);
//            }
//        });
//
//        yesBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                promoDialog.dismiss();
//                navigatingToProfile(position);
//            }
//        });
//
//        noBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                promoDialog.dismiss();
//            }
//        });
//
//        close_popup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                promoDialog.dismiss();
//            }
//        });
//
//    }
//
//    private void navigatingChatScreen(int position) {
//        ChatDataConvertModel chatDataConvertModel = new ChatDataConvertModel();
//        try {
//            chatDataConvertModel._id = offlineOrCategoryListFilter.get(position).get_id();
//            chatDataConvertModel.firstName = offlineOrCategoryListFilter.get(position).getFirstName();
//            chatDataConvertModel.lastName = offlineOrCategoryListFilter.get(position).getLastName();
//            chatDataConvertModel.avtar_imgPath = offlineOrCategoryListFilter.get(position).getAvtar_imgPath();
//            chatDataConvertModel.isCeleb = offlineOrCategoryListFilter.get(position).isCeleb();
//            chatDataConvertModel.message = "";
//            chatDataConvertModel.receiverId = offlineOrCategoryListFilter.get(position).get_id();
//            chatDataConvertModel.senderId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
//            chatDataConvertModel.createdAt = "";
//            chatDataConvertModel.counter = 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Common.getInstance().openChatTabsActivity(context, chatDataConvertModel);
//    }
//
//
//    private void navigatingToProfile(int position) {
//
//        if (offlineOrCategoryListFilter.get(position).getFirstName() != null && !offlineOrCategoryListFilter.get(position).getFirstName().isEmpty()) {
//            //
//            Intent profileIntent = new Intent(context, HelperActivity.class);
//            profileIntent.putExtra(Constants.FRAGMENT_KEY, 8029);
//            profileIntent.putExtra(Constants.FRAGMENT_TITLE, "Celeb Profile");
//            profileIntent.putExtra("ClASS_NAME", "OFFLINE");
//            profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            if (offlineOrCategoryListFilter.get(position).get_id() != null && !offlineOrCategoryListFilter.get(position).get_id().isEmpty()) {
//                profileIntent.putExtra("CelebId", offlineOrCategoryListFilter.get(position).get_id());
//            } else {
//                profileIntent.putExtra("CelebId", "");
//            }
//
//            if (offlineOrCategoryListFilter.get(position).isOnline()) {
//                profileIntent.putExtra("celeb_status", "online");
//            } else {
//                profileIntent.putExtra("celeb_status", "offline");
//            }
//
//
//            profileIntent.putExtra("CelebId", offlineOrCategoryListFilter.get(position).get_id());
//
//            if (offlineOrCategoryListFilter.get(position).getFirstName() != null && !offlineOrCategoryListFilter.get(position).getFirstName().isEmpty()) {
//                profileIntent.putExtra("PROFILE_NAME", offlineOrCategoryListFilter.get(position).getFirstName() + " " + offlineOrCategoryListFilter.get(position).getLastName());
//            } else {
//                profileIntent.putExtra("PROFILE_NAME", "null");
//            }
//
//            if (offlineOrCategoryListFilter.get(position).getAvtar_imgPath() != null && !offlineOrCategoryListFilter.get(position).getAvtar_imgPath().isEmpty()) {
//                profileIntent.putExtra("Imaage", ApiClient.BASE_URL + offlineOrCategoryListFilter.get(position).getAvtar_imgPath());
//            } else {
//                profileIntent.putExtra("Imaage", "");
//            }
//
//            if (offlineOrCategoryListFilter.get(position).getProfession() != null && !offlineOrCategoryListFilter.get(position).getProfession().isEmpty()) {
//                profileIntent.putExtra("proffession", offlineOrCategoryListFilter.get(position).getProfession());
//            } else {
//                profileIntent.putExtra("proffession", "");
//            }
//
//
//            if (offlineOrCategoryListFilter.get(position).getAboutMe() != null) {
//                profileIntent.putExtra("ABOUT", offlineOrCategoryListFilter.get(position).getAboutMe());
//            } else {
//
//            }
//            context.startActivity(profileIntent);
//        }
//
//    }
//
//
//    public int getClickedPosition() {
//        return clickedPosition;
//    }
//
//    public void updateItem(CelebProfileCompleteData celebProfileCompleteData) {
//        offlineOrCategoryListFilter.remove(clickedPosition);
//        offlineOrCategoryListFilter.add(clickedPosition, celebProfileCompleteData);
//        notifyDataSetChanged();
//    }
//
//
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString().trim();
//                if (charString.isEmpty()) {
//                    offlineOrCategoryListFilter = offlineOrCategoryList;
//                } else {
//                    offlineOrCategoryListFilter = (ArrayList<CelebProfileCompleteData>) (ArrayList<?>) Common.getInstance().getFilteredListOfSearchObject(offlineOrCategoryList, charString, 0);
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = offlineOrCategoryListFilter;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                try {
//                    offlineOrCategoryListFilter = (ArrayList<CelebProfileCompleteData>) filterResults.values;
//                    if (offlineOrCategoryListFilter != null && offlineOrCategoryListFilter.size() > 0) {
//                        notifyDataSetChanged();
//                    }
//                    if (categoryCelebsFragment != null) {
//                        categoryCelebsFragment.showNoResultsFound(offlineOrCategoryListFilter);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//    }
//
//}
