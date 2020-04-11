package info.sumantv.flow.bottommenu.menuitemsmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.menuitemsmanager.IMenuItemAdapter;
import info.sumantv.flow.bottommenu.menuitemsmanager.modelclasses.MenuItems;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.chat.socket.ChatSocket;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.SocketForAppUtill;
import info.sumantv.flow.utils.Utility;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MenuItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;
    private ArrayList<MenuItems> dataSet;
    ApiInterface apiInterface;
    private Activity activity;
    private CircleImageView mUserProfileImage;
    String credits, schdule_refereal_Credits, memeber_referal_credits, celeb_referral_credits, celeb_referal_Id = null;
    private Context mContext;
    private IMenuItemAdapter iMenuItemAdapter;
    static String Divider = "Divider";


    public MenuItemAdapter(Activity activity, ArrayList<MenuItems> strings, IMenuItemAdapter iMenuItemAdapter) {
        this.activity = activity;
        this.dataSet = strings;
        this.iMenuItemAdapter = iMenuItemAdapter;
    }

    public MenuItemAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homemenufragment, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            //Inflating header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_header, parent, false);
            mContext = parent.getContext();
            return new HeaderViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_footer, parent, false);
            return new FooterViewHolder(itemView);
        } else return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (mUserProfileImage != null) {
                if (SessionManager.userLogin.profilePic != null
                        && !SessionManager.userLogin.profilePic.isEmpty()) {
                    Glide.with(activity)
                            .load(ApiClient.BASE_URL + SessionManager.userLogin.profilePic)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_profile_square_pleace_holder)
                            .into(mUserProfileImage);
                } else {
//                Glide.with(activity).load(R.drawable.ic_profile_square_pleace_holder).into(mUserProfileImage);
                    mUserProfileImage.setImageResource(R.drawable.ic_profile_square_pleace_holder);
                }
            }


        } catch (Exception e) {

        }

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            getCreditsBalance(headerHolder);
            headerHolder.llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        navigatingToProfilePage();
                }
            });


            headerHolder.swicthAccountIconMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Common.getInstance().managerOwnProfileBack(mContext);
                    Common.getInstance().switchOwnProfile(mContext, false);
                }
            });

            headerHolder.nav_Credits_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        navigatetoaddCreditspage(view);


                }
            });
            headerHolder.ibLiveStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Common.clickedHolder = headerHolder;
                    //
                    IApiListener iApiListener = new IApiListener() {
                        @Override
                        public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                            try {
                                Boolean liveStatus;
                                JSONObject response = new JSONObject(new Gson().toJson(apiResponseModel.data));
                                liveStatus = response.optBoolean("online", false);
                                updateLiveStatusData(liveStatus);
                                if (liveStatus) {
                                    SocketForAppUtill.getInstance().onlineCelebEmit();

                                    Toast.makeText(mContext, "Live status is ON. Now you are available for members to Chat/Call.", Toast.LENGTH_LONG).show();
                                } else {
                                    SocketForAppUtill.getInstance().onlineCelebEmit();
                                    Toast.makeText(mContext, "Live status is OFF. Now you are not available for members to Chat/Call.", Toast.LENGTH_LONG).show();
                                }
                                setLiveStatusButton(headerHolder.ibLiveStatus, liveStatus);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                            //Common.getInstance().cusToast(Utility.getContext(), apiResponseModel.message);
                        }
                    };
                    Common.getInstance().updateLiveStatusAPI(mContext, iApiListener);
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.textView_menu_version.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(activity, "You clicked at Footer View", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder instanceof ItemViewHolder) {
            final int finalPosition = position > 0 ? position - 1 : position;
            MenuItems menuItems = dataSet.get(finalPosition);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.divideview.setVisibility(View.GONE);
            //
            if(menuItems.getItemName().equals(Divider)){
                itemViewHolder.divideview.setVisibility(View.VISIBLE);
                itemViewHolder.listitemLayout.setVisibility(View.GONE);
            } else {
                itemViewHolder.divideview.setVisibility(View.GONE);
                itemViewHolder.listitemLayout.setVisibility(View.VISIBLE);
                //
                itemViewHolder.textViewName.setText(menuItems.getItemName());
                itemViewHolder.imageViewIcon.setImageResource(menuItems.getImage());
                itemViewHolder.listitemLayout.setOnClickListener(view -> {
                    iMenuItemAdapter.onClick(dataSet.get(finalPosition), position);
                });
            }
            try {
                if((dataSet.size() > finalPosition+1 && dataSet.get(finalPosition+1).getItemName().equals(Divider)) || finalPosition+1 == dataSet.size()){
                    itemViewHolder.viewLine.setVisibility(View.GONE);
                } else {
                    itemViewHolder.viewLine.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //
            switch (menuItems.getItemName()) {
                case "Credits":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_credit));
                    break;
                case "Preferences":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_preferences));
                    break;
                case "Celebrities":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_celebrities));
                    break;
                case "Transactions":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_transaction));
                    break;
                case "Fans & Followers":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_fan_follows));
                    break;
                case "Invite a friend":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_invite_friend));
                    break;
                case "Auditions":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_auditions));
                    break;
                case "Settings":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_settings));
                    break;
                case "Content":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_content));
                    break;
                case "Calendar":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_calender));
                    break;
                case "Search Manager":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_search_manager));
                    break;
                case "Clients":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_clients));
                    break;
                case "Back to Own Profile":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_preferences));
                    break;
                case "About us":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_about));
                    break;
                case "Help":
                    itemViewHolder.ivBackground.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.menu_bg_help));
                    break;
            }
        }
        if (position == dataSet.size() + 1) {
            int paddingToBottom = mContext.getResources().getDimensionPixelSize(R.dimen._48sdp);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, paddingToBottom);
            holder.itemView.setLayoutParams(layoutParams);
            //holder.itemView.setPadding(0, 0, 0, paddingToBottom);
        } else {
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 0);
            holder.itemView.setLayoutParams(layoutParams);
            //holder.itemView.setPadding(0, 0, 0, 0);
        }
    }

    public void updateLiveStatusData(Boolean liveStatus) {
        try {
            if (liveStatus) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_LIVE_STATUS,"TRUE");
                //
                ChatSocket.getInstance().emitOnline(Utility.getContext());
            } else {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_LIVE_STATUS,"FALSE");
                //
                ChatSocket.getInstance().screenStatusEmit(false);
            }
            updateLiveStatus(liveStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLiveStatus(Boolean liveStatus) {
        try {
            Log.d("LiveStatusResult", liveStatus.toString());
            if (Common.clickedHolder instanceof HeaderViewHolder) {
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) Common.clickedHolder;
                headerViewHolder.ibLiveStatus.setEnabled(true);
                headerViewHolder.ibLiveStatus.setClickable(true);
                setLiveStatusButton(headerViewHolder.ibLiveStatus, liveStatus);
            }
            /*if(liveStatus){
                Common.getInstance().cusToast(Utility.getContext(), "Live Status : ON");
            } else {
                Common.getInstance().cusToast(Utility.getContext(), "Live Status : OFF");
            }*/
            Common.isLiveStatusEmitRunning = false;
            Common.getInstance().closeProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setLiveStatusButton(ImageButton imageButton, Boolean liveStatus) {
        if (liveStatus) {
            imageButton.setImageResource(R.drawable.switch_on);
        } else {
            imageButton.setImageResource(R.drawable.switch_off);
        }
//        SocketForAppUtill.getInstance().liveLogStatusEmit(liveStatus);
    }

    private void navigatetoaddCreditspage(View view) {
        Intent intent = new Intent(view.getContext(), HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "Credits");
        intent.putExtra("className", "BottomMenuActivity");
        intent.putExtra(Constants.FRAGMENT_KEY, 8034);// CreditsRecharge
        view.getContext().startActivity(intent);
    }

    private void navigatingToProfilePage() {
        Intent intent = new Intent(activity, HelperActivity.class);
        intent.putExtra("title", "My Profile");
        intent.putExtra(Constants.FRAGMENT_KEY, 8002);// EditProfileFragment
        activity.startActivity(intent);
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == dataSet.size() + 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return dataSet.size() + 2;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView mUserName, role, nav_Credit_balanc, mReveral_Credit_Balance;
        public String mUserNameTxt;

        private RelativeLayout mReferal_Layout;
        LinearLayout livestautslayout,llParent;
        private Button nav_Credits_add;
        private ImageView swicthAccountIconMenu;
        private ImageButton ibLiveStatus;

        public HeaderViewHolder(View view) {
            super(view);
            mUserName = (TextView) view.findViewById(R.id.name);
            role = (TextView) view.findViewById(R.id.role);
            mUserProfileImage = (CircleImageView) view.findViewById(R.id.profile_imageView);
            nav_Credit_balanc = (TextView) view.findViewById(R.id.nav_credit_balance);
            mReveral_Credit_Balance = (TextView) view.findViewById(R.id.referal_credits);
            mReferal_Layout = (RelativeLayout) view.findViewById(R.id.referal_layout);
            nav_Credits_add = (Button) view.findViewById(R.id.nav_add_credits);
            swicthAccountIconMenu = (ImageView) view.findViewById(R.id.swicthAccountIconMenu);
            livestautslayout = (LinearLayout) view.findViewById(R.id.livestautslayout);
            llParent = (LinearLayout) view.findViewById(R.id.llParent);
            ibLiveStatus = (ImageButton) view.findViewById(R.id.ibLiveStatus);


           if (Common.isCelebStatus(mContext)) {
                swicthAccountIconMenu.setVisibility(View.GONE);
                ibLiveStatus.setVisibility(View.VISIBLE);
                livestautslayout.setVisibility(View.VISIBLE);
                setLiveStatusButton(ibLiveStatus, Common.getInstance().isOnlineStatus());
            } else {
                ibLiveStatus.setVisibility(View.GONE);
                livestautslayout.setVisibility(View.GONE);
            }

        }
    }
    private class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView textView_menu_version;

        public FooterViewHolder(View view) {
            super(view);
            textView_menu_version = (TextView) view.findViewById(R.id.textView_menu_version);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageViewIcon,ivBackground;
        LinearLayout listitemLayout, divideview;
        View viewLine;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textView_item_menu);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView_item_menu);
            this.ivBackground = (ImageView) itemView.findViewById(R.id.ivBackground);
            this.listitemLayout = (LinearLayout) itemView.findViewById(R.id.listitemLayout);
            this.divideview = (LinearLayout) itemView.findViewById(R.id.divideview);
            this.viewLine = (View) itemView.findViewById(R.id.viewLine);
        }
    }


    public void getCreditsBalance(HeaderViewHolder headerHolder) {
        try {
            String First_name = SessionManager.userLogin.firstName;
            String Last_name = SessionManager.userLogin.lastName;
            headerHolder.mUserName.setText(Common.getInstance().convertFirstLetterToCapital(First_name) + " " + Last_name);



                if (SessionManager.userLogin.mainCredits != null) {
                    Double credit_double = SessionManager.userLogin.mainCredits;
                    DecimalFormat twoDecimal = new DecimalFormat("0.00");
                    if (credit_double != null) {
                        headerHolder.nav_Credit_balanc.setText("Credit Balance: " + twoDecimal.format(credit_double));
                    } else {
                        headerHolder.nav_Credit_balanc.setText("Credit Balance: 0.00");
                    }
                }
                if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_REFERRAL_CREDITS,0.0) != null) {
                    headerHolder.mReveral_Credit_Balance.setText("Referral Credits: " +
                            SessionManager.getInstance().getKeyValue(SessionManager.KEY_REFERRAL_CREDITS,0.0));
                } else {
                    headerHolder.mReveral_Credit_Balance.setText("Referral Credits: 0");
                }



        } catch (Exception E) {
            E.printStackTrace();
        }
    }
}
