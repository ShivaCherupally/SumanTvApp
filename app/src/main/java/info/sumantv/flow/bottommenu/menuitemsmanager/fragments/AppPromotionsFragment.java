//package info.celebkonnect.flow.bottommenu.menuitemsmanager.fragments;
//
//import android.app.ProgressDialog;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.*;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.DataSource;
//import com.bumptech.glide.load.engine.GlideException;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.target.Target;
//import com.google.gson.Gson;
//import info.celebkonnect.flow.celebflow.R;
//import info.celebkonnect.flow.managermodule.switchingprofiles.CommonAccessPermissionOfCeleb;
//import info.celebkonnect.flow.retrofitcall.*;
//import info.celebkonnect.flow.userflow.Util.Common;
//import okhttp3.MediaType;
//import okhttp3.RequestBody;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import pl.droidsonroids.gif.GifImageView;
//import retrofit2.Call;
//
//public class AppPromotionsFragment extends Fragment implements IApiListener {
//    private Button promtioncode_btn, generatecode_btn;
//    private TextView promotionalcode;
//    String tag_json_obj = "json_obj_req";
//    String tag_json_arry = "json_array_req";
//    private ProgressDialog progressDialog;
//    String code_generated;
//    private int referralCreditValue;
//
//    private LinearLayout share_code;
//    String refferalCode = "";
//
//    String mediaUrl1, mediaUrl2, mediaTitle1, mediaTitle2;
//    private GifImageView app_promotionimg;
//    private int i = 0;
//    private ProgressBar mApppromotions_Progressbar;
//    IApiListener iApiListener;
//
//    public static AppPromotionsFragment newInstance(String param1, String param2) {
//        AppPromotionsFragment fragment = new AppPromotionsFragment();
//
//        return fragment;
//    }
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.app_promotion_screen, null);
//        iApiListener = this;
//        promotionalcode = (TextView) root.findViewById(R.id.promotional_code);
//        promtioncode_btn = (Button) root.findViewById(R.id.invite_code_btn);
//        generatecode_btn = (Button) root.findViewById(R.id.generate_code_btn);
//        share_code = (LinearLayout) root.findViewById(R.id.share_code);
//        app_promotionimg = (GifImageView) root.findViewById(R.id.app_promotionimg);
//        mApppromotions_Progressbar = (ProgressBar) root.findViewById(R.id.apppromotions_progressbar);
////        oneImag = (ProportionalImageView) itemView.root.findViewById(R.id.oneImag);
//
//        getAppPromotionImages();
//        PrmoCodeCheck();
//
//
//        promtioncode_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (CommonAccessPermissionOfCeleb.appPromotionPermissonAvailabilty(getContext(),
//                        false, false)) {
//                    if (promotionalcode.getText().toString() != null && !promotionalcode.getText().toString().isEmpty()) {
//                        Common.shareSocialNetwork(getActivity(), "" +
//                                promotionalcode.getText().toString(), "APP_PROMOTION");
//                    } else {
//                        Toast.makeText(getActivity(), "Please Generate Code", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//
//            }
//        });
//
//        generatecode_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String memberId = SessionManager.userLogin.userId;
//                if (SessionManager.userLogin.isCeleb != null) {
//                    if (SessionManager.userLogin.isCeleb) {
//                        referralCreditValue = 250;
//                    } else {
//                        referralCreditValue = 150;
//                    }
//                } else {
//                    referralCreditValue = 150;
//                }
//                String member_name = SessionManager.userLogin.firstName;
//
//                JSONObject generate_code = new JSONObject();
//                try {
//                    generate_code.put("memberId", memberId);
//                    generate_code.put("referralCreditValue", referralCreditValue);
//                    generate_code.put("createdBy", member_name);
//                    GeneratePromoCode(generate_code);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        return root;
//    }
//
//    private void PrmoCodeCheck() {
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<ApiResponseModel> call = apiInterface.GET(ApiClient.GET_REFFERAL_CODE + SessionManager.userLogin.userId);
//        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(),call,ApiClient.GET_REFFERAL_CODE,false,iApiListener,false));
//    }
//
//    private void textchange() {
//
//        if (i % 2 == 0) {
//            promotionalcode.setText(code_generated);
//        } else {
//            promotionalcode.setText(code_generated);
//        }
//    }
//
//
//    private void getAppPromotionImages() {
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<ApiResponseModel> call = apiInterface.GET(ApiClient.APP_PROMOTION_IMAGES);
//        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(),call,ApiClient.APP_PROMOTION_IMAGES,
//                false,iApiListener,false));
//    }
//
//    private void GeneratePromoCode(final JSONObject generate_code) {
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),generate_code.toString());
//        Call<ApiResponseModel> call = apiInterface.POST(ApiClient.POST_REFFERAL_CODE,requestBody);
//        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(),call,ApiClient.POST_REFFERAL_CODE,true,iApiListener,false));
//    }
//
//    @Override
//    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
//        if(condition.equals(ApiClient.GET_REFFERAL_CODE)){
//            JSONArray jsonArray = null;
//            try {
//                jsonArray = new JSONArray(new Gson().toJson(apiResponseModel.data));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (jsonArray != null && jsonArray.length() != 0) {
//
//                for (int k = 0; k < jsonArray.length(); k++) {
//                    try {
//                        JSONObject jsonObject = jsonArray.getJSONObject(0);
//                        if (jsonObject.getString("memberCode") != null) {
//                            promtioncode_btn.setVisibility(View.VISIBLE);
//                            code_generated = jsonObject.getString("memberCode");
//
//                            textchange();
//                            Animation startAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.blinking_animation);
//                            promotionalcode.startAnimation(startAnimation);
//                            share_code.setVisibility(View.VISIBLE);
//                        } else {
//                            generatecode_btn.setVisibility(View.VISIBLE);
//                        }
//                        Glide.with(getActivity())
//                                .load(ApiClient.BASE_URL + mediaUrl1)
//                                .listener(new RequestListener<Drawable>() {
//                                    @Override
//                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                        mApppromotions_Progressbar.setVisibility(View.GONE);
//                                        return false;
//                                    }
//
//                                    @Override
//                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                        mApppromotions_Progressbar.setVisibility(View.GONE);
//                                        return false;
//                                    }
//                                })
//                                .into(app_promotionimg);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            } else {
//                generatecode_btn.setVisibility(View.VISIBLE);
//            }
//        } else if(condition.equals(ApiClient.APP_PROMOTION_IMAGES)){
//            try {
//                JSONArray response = new JSONArray(new Gson().toJson(apiResponseModel.data));
//                JSONObject jsonObject = response.getJSONObject(0);
//                JSONArray mediaData = jsonObject.getJSONArray("media");
//
//                for (int i = 0; i < mediaData.length(); i++) {
//                    JSONObject jsonObject1 = mediaData.getJSONObject(i);
//                    if (i == 0) {
//                        mediaUrl1 = jsonObject1.getString("mediaUrl");
//                        mediaTitle1 = jsonObject1.getString("mediaTitle");
//                    } else {
//                        mediaUrl2 = jsonObject1.getString("mediaUrl");
//                        mediaTitle2 = jsonObject1.getString("mediaTitle");
//                    }
//                }
//                //mApppromotions_Progressbar.setVisibility(View.GONE);
//                //Common.getInstance().setGlideImage(getActivity(), ApiClient.BASE_URL + mediaUrl1, app_promotionimg);
//                Glide.with(getActivity())
//                        .load(ApiClient.BASE_URL + mediaUrl1)
//                        .listener(new RequestListener<Drawable>() {
//                            @Override
//                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                mApppromotions_Progressbar.setVisibility(View.GONE);
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                mApppromotions_Progressbar.setVisibility(View.GONE);
//                                return false;
//                            }
//                        })
//                        .into(app_promotionimg);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if(condition.equals(ApiClient.POST_REFFERAL_CODE)){
//            try {
//                JSONObject response = new JSONObject(new Gson().toJson(apiResponseModel.data));
//                refferalCode = response.getString("memberCode");
//                generatecode_btn.setVisibility(View.GONE);
//                promotionalcode.setText(refferalCode);
//                promtioncode_btn.setVisibility(View.VISIBLE);
//                share_code.setVisibility(View.VISIBLE);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
//        if(condition.equals(ApiClient.GET_REFFERAL_CODE)){
//            generatecode_btn.setVisibility(View.VISIBLE);
//        } else if(condition.equals(ApiClient.APP_PROMOTION_IMAGES)){
//            generatecode_btn.setVisibility(View.VISIBLE);
//        } else if(condition.equals(ApiClient.POST_REFFERAL_CODE)){
//            //
//        }
//    }
//
//}
