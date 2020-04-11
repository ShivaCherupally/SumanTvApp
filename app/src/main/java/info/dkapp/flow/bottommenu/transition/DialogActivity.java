package info.dkapp.flow.bottommenu.transition;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.transition.ArcMotion;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.retrofitcall.*;
import info.dkapp.flow.userflow.Util.Common;


public class DialogActivity extends AppCompatActivity {


    @BindView(R.id.llClose)
    LinearLayout llClose;

    @BindView(R.id.llTalentProfile)
    LinearLayout llTalentProfile;

    @BindView(R.id.ivUserImage)
    ImageView imageView;

    @BindView(R.id.imCall)
    ImageView imCall;

    @BindView(R.id.imFavorite)
    ImageView imFavorite;

    @BindView(R.id.imMessage)
    ImageView imMessage;

    @BindView(R.id.tvweight)
    TextView tvweight;

    @BindView(R.id.tvHeight)
    TextView tvHeight;

    @BindView(R.id.tvStartAge)
    TextView tvStartAge;

    @BindView(R.id.tvEndAge)
    TextView tvEndAge;

    @BindView(R.id.tvAddress)
    TextView tvAddress;

    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @BindView(R.id.tvSkills)
    TextView tvSkills;

    @BindView(R.id.ivCancel)
    ImageView ivCancel;

    @BindView(R.id.tvDob)
    TextView tvDob;

    String profilepic, profilename, location, dob, mobileNumber, id, skills, auditionProfileID,
            startHeight, startWeight, memberId, userId;
    Boolean hasBeenFavorited;
    Integer height, weight;

    TextView tvViewProfile;
    ProgressDialog progressDialog;

    Integer talentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        ButterKnife.bind(this);

        tvViewProfile = findViewById(R.id.tvViewProfile);

        setUp();

        Bundle bundle = getIntent().getExtras();


        // tvDob.setText("Age Range         : ");
        try {
            tvStartAge.setText(": " + bundle.getInt("ageStart") + "-" + bundle.getInt("ageEnd"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        memberId = bundle.getString("id");

//       if (dontshowcorrrectage.equals(false))
//       {
//
//           if (bundle.getString("dob") != null && !bundle.getString("dob").isEmpty()) {
//               dob = bundle.getString("dob");
//               tvStartAge.setText(DateUtil.getMonthAndDate(dob) + " " + DateUtil.getOnlyYear(dob));
//           }
//
//       }
//       else
//       {
//
//           tvDob.setText("Age Range         : ");
//           tvStartAge.setText(bundle.getInt("ageStart")+"-"+bundle.getInt("ageEnd"));
//       }


        if (bundle.getString("profilepic") != null && !bundle.getString("profilepic").isEmpty()) {
            profilepic = bundle.getString("profilepic");
            Glide.with(this)
                    .load(profilepic)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_profile_square_pleace_holder)
                    .into(imageView);
        }
        if (bundle.getString("id") != null && !bundle.getString("id").isEmpty()) {
            id = bundle.getString("id");
        }
        if (bundle.getString("memberId") != null && !bundle.getString("memberId").isEmpty()) {
            userId = bundle.getString("memberId");
        }
        if (bundle.getString("profilename") != null && !bundle.getString("profilename").isEmpty()) {
            profilename = bundle.getString("profilename");
//            tvUserName.setText(profilename);

            tvUserName.setText(Common.convertCaseSensitive(profilename));
        }

        talentPosition = bundle.getInt("talentPosition");

        if (bundle.getString("skills") != null) {
            skills = bundle.getString("skills");

            String lenth = String.valueOf(skills.split(",").length);

            if (lenth.equals("1")) {
                tvSkills.setText(skills);
            } else {
                String firstSkill = skills.split(",")[0];
                tvSkills.setText(firstSkill + " +...");
            }

        }

        if (bundle.getBoolean("hasBeenFavorited")) {
            imFavorite.setImageResource(R.drawable.ic_fav_blue);
        } else {
            imFavorite.setImageResource(R.drawable.ic_fev_unfan);
        }

        if (bundle.getString("location") != null && !bundle.getString("location").isEmpty()) {
            location = bundle.getString("location");
            tvAddress.setText(": " + Common.convertCaseSensitive(location));
        }
//        if (bundle.getString("dob") != null && !bundle.getString("dob").isEmpty()) {
//            dob = bundle.getString("dob");
//            tvStartAge.setText(DateUtil.getMonthAndDate(dob) + " " + DateUtil.getOnlyYear(dob));
//        }
        if (bundle.getString("startHeight") != null && !bundle.getString("startHeight").isEmpty()) {
            startHeight = bundle.getString("startHeight");
            // tvHeight.setText(height +  " Cms");
        }
        //  if (bundle.getInt("height") != null && !bundle.getInt("height").isEmpty()) {
        height = bundle.getInt("height");

        Log.e("nhdhdh", "dia " + height);

        //  }

        if (startHeight == null) {
            startHeight = String.valueOf(0);  // tvHeight
            tvHeight.setText(": " + height + " Cms" + " (" + Common.convertCentimeterToHeight(Double.valueOf(height)) + ")");

        } else {

            tvHeight.setText(": " + height + " Cms" + " (" + Common.convertCentimeterToHeight(Double.valueOf(height)) + ")");
        }


        if (bundle.getString("startWeight") != null && !bundle.getString("startWeight").isEmpty()) {
            startWeight = bundle.getString("startWeight");
            //   tvweight.setText(startWeight+" - "+weight + " Kgs");
        }

        //  if (bundle.getString("weight") != null && !bundle.getString("weight").isEmpty()) {
        weight = bundle.getInt("weight");

        Log.e("nhdhdh", "dia " + weight);

        if (startWeight == null) {
            startWeight = String.valueOf(0);
            tvweight.setText(": " + weight + " Kgs" + " (" + Common.convertKgsToPounds(Double.valueOf(weight)) + " lbs)");
        } else {
            tvweight.setText(": " + weight + " Kgs" + " (" + Common.convertKgsToPounds(Double.valueOf(weight)) + " lbs)");
        }

        //  tvweight.setText(weight + " Kgs");
        //    }
        if (bundle.getString("mobileNumber") != null && !bundle.getString("mobileNumber").isEmpty()) {
            mobileNumber = bundle.getString("mobileNumber");
        }

        auditionProfileID = Common.getInstance().IsNullReturnValue(bundle.getString("auditionProfileID", ""), "");
        if (auditionProfileID.equals("0") || auditionProfileID.trim().isEmpty()) {
            imFavorite.setVisibility(View.GONE);
        }

        setupSharedEelementTransitions1();

        View.OnClickListener noChange = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };

        View.OnClickListener dismissListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        };

        llClose.setOnClickListener(dismissListener);


        //  llClose.findViewById(R.id.llClose).setOnClickListener(dismissListener);

        llClose.findViewById(R.id.llClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ivCancel.setOnClickListener(view -> {
            dismiss();
        });

    }

    private void setFavouriteIcon(Boolean status) {
        try {
            if (status != null && status) {
                imFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_blue));
            } else {
                imFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_role_stroke));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUp() {
        imFavorite.setOnClickListener(view -> {
                if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_EDIT_VALUE, "").equalsIgnoreCase("yes")) {

                    // setFavoriteUnFavourite();
                } else {




            }

        });

        tvViewProfile.setOnClickListener(view -> {
            //  Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_TITLE, "Audition Profile");
            intent.putExtra(Constants.FRAGMENT_KEY, 8049);// Audition InnerProfile
            intent.putExtra("id", userId);
            intent.putExtra("pName", profilename);
            intent.putExtra("pUserPic", profilepic);
            intent.putExtra("pCountry", location);
            intent.putExtra("pSkills", skills);

            Log.e("hdhfhdfhdf", "dialoge " + id);
            startActivity(intent);
            this.finish();


        });

        imCall.setOnClickListener(v -> {
                if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_EDIT_VALUE, "").equalsIgnoreCase("yes")) {
                    String PhoneNum = mobileNumber;
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + Uri.encode(PhoneNum)));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callIntent);
                } else {
                        String PhoneNum = mobileNumber;
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + Uri.encode(PhoneNum)));
                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(callIntent);

                }
        });

        imMessage.setOnClickListener(v -> {


                if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_EDIT_VALUE, "").equalsIgnoreCase("yes")) {
                    Uri uri = Uri.parse("smsto:" + mobileNumber);
                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                    it.putExtra("sms_body", "");
                    startActivity(it);
                } else {
                    Uri uri = Uri.parse("smsto:" + mobileNumber);
                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                    it.putExtra("sms_body", "");
                    startActivity(it);
                }
        });

    }





    public void setupSharedEelementTransitions1() {
        ArcMotion arcMotion = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            arcMotion = new ArcMotion();
            arcMotion.setMinimumHorizontalAngle(50f);
            arcMotion.setMinimumVerticalAngle(50f);

        }

        Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        MorphFabToDialog sharedEnter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            sharedEnter = new MorphFabToDialog();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedEnter.setPathMotion(arcMotion);
            sharedEnter.setInterpolator(easeInOut);
        }

        MorphDialogToFab sharedReturn = new MorphDialogToFab();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedReturn.setPathMotion(arcMotion);
            sharedReturn.setInterpolator(easeInOut);

        }

        if (imageView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                sharedEnter.addTarget(imageView);
                sharedReturn.addTarget(imageView);

            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(sharedEnter);
            getWindow().setSharedElementReturnTransition(sharedReturn);
        }
    }

    /**
     * setupSharedEelementTransitions2
     **/
    public void setupSharedEelementTransitions2() {
        ArcMotion arcMotion = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            arcMotion = new ArcMotion();
            arcMotion.setMinimumHorizontalAngle(50f);
            arcMotion.setMinimumVerticalAngle(50f);
        }

        Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        //ontainer.getHeight()
        MorphTransition sharedEnter = new MorphTransition(ContextCompat.getColor(this, R.color.fab_background_color),
                ContextCompat.getColor(this, R.color.dialog_background_color), 100, getResources().getDimensionPixelSize(R.dimen.dialog_corners), true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedEnter.setPathMotion(arcMotion);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sharedEnter.setInterpolator(easeInOut);
        }

        MorphTransition sharedReturn = new MorphTransition(ContextCompat.getColor(this, R.color.dialog_background_color),
                ContextCompat.getColor(this, R.color.fab_background_color), getResources().getDimensionPixelSize(R.dimen.dialog_corners), 100, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedReturn.setPathMotion(arcMotion);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sharedReturn.setInterpolator(easeInOut);
        }

        if (imageView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                sharedEnter.addTarget(imageView);
                sharedReturn.addTarget(imageView);

            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(sharedEnter);
            getWindow().setSharedElementReturnTransition(sharedReturn);

        }
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    public void dismiss() {
        setResult(Activity.RESULT_CANCELED);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }
    }

}
