package info.sumantv.flow.ipoll.dialogs.custom;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.appmanagers.feed.models.KonectData;
import info.sumantv.flow.ModelClass.ContractCreditsData;
import info.sumantv.flow.bottommenu.interfaces.fragments.IKonectDailogClick;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.celebflow.constants.Permission;
import info.sumantv.flow.chat.models.ChatDataConvertModel;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Utility;
import retrofit2.Call;

/**
 * Created by Uday Kumay Vurukonda on 23-Aug-19.
 * <p>
 * Mr.Psycho
 */
public class KonectCelebDailog extends BottomSheetDialogFragment implements View.OnClickListener, IApiListener {

    @BindView(R.id.tvAvabCredits)
    TextView tvAvabCredits;

    @BindView(R.id.iVClose)
    ImageView iVClose;

    @BindView(R.id.iVProfilePic)
    SimpleDraweeView iVProfilePic;

    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @BindView(R.id.tvUserProf)
    TextView tvUserProf;

    @BindView(R.id.LLchat)
    LinearLayout LLchat;

    @BindView(R.id.LLvideo)
    LinearLayout LLvideo;

    @BindView(R.id.LLaudio)
    LinearLayout LLaudio;

    @BindView(R.id.tvAudioCredits)
    TextView tvAudioCredits;

    @BindView(R.id.tvVideoCredits)
    TextView tvVideoCredits;

    @BindView(R.id.tvChatCredits)
    TextView tvChatCredits;

    @BindView(R.id.optionforCallsLayout)
    LinearLayout optionforCallsLayout;

    @BindView(R.id.callOptionLayout)
    LinearLayout callOptionLayout;

    @BindView(R.id.callnowlayout)
    LinearLayout callnowlayout;

    @BindView(R.id.booknowlayout)
    LinearLayout booknowlayout;

    @BindView(R.id.imageType)
    ImageView imageType;


    IKonectDailogClick iKonectDailogClick;
    Context context;
    KonectData konectData;
    IApiListener iApiListener;
    ApiInterface apiInterface;

    ContractCreditsData contractCreditsData;

    String callType = "";

    public KonectCelebDailog() {
    }

    public void setData(KonectData konectData, IKonectDailogClick iKonectDailogClick) {
        this.konectData = konectData;
        this.iKonectDailogClick = iKonectDailogClick;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iApiListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.konect_celeb_dialog, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        setup();
        return view;
    }

    private void setup() {

        if (SessionManager.userLogin.mainCredits != null) {
            tvAvabCredits.setText("" + Common.getInstance().roundTwoDecimalPlaces(SessionManager.userLogin.mainCredits));
        }
        if (Common.getInstance().IsNullReturnValue(konectData.avtar_imgPath, "").length() > 0) {
            iVProfilePic.setImageURI(Uri.parse(Constants.MEDIA_BASE_URL + konectData.avtar_imgPath));
        } else {
            Glide.with(context).load(R.drawable.ic_grey_celebkonect_logo).into(iVProfilePic);
        }
        String userName = konectData.firstName + " " + konectData.lastName;
        if (userName != null && !userName.isEmpty()) {
            tvUserName.setText(Character.toUpperCase(userName.charAt(0)) + userName.substring(1));
        }
        if (konectData.profession != null && !konectData.profession.isEmpty()) {
            tvUserProf.setText(konectData.profession);
        }
        if (konectData.isScheduleStatus) {
            callOptionLayout.setVisibility(View.VISIBLE);
            optionforCallsLayout.setVisibility(View.GONE);
            if (konectData.serviceType.equalsIgnoreCase("video")) {
                imageType.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_video));
                imageType.setColorFilter(ContextCompat.getColor(getActivity(), R.color.video_icon_color), android.graphics.PorterDuff.Mode.SRC_IN);
            } else {
                imageType.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_audio));
                imageType.setColorFilter(ContextCompat.getColor(getActivity(), R.color.audio_icon_color), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        } else {
            callOptionLayout.setVisibility(View.GONE);
            optionforCallsLayout.setVisibility(View.VISIBLE);
        }
        if (!Common.isCelebStatus(getActivity())) {
            getContracts(konectData._id);
        }


        iVClose.setOnClickListener(this);
        LLaudio.setOnClickListener(this);
        LLvideo.setOnClickListener(this);
        LLchat.setOnClickListener(this);
        iVProfilePic.setOnClickListener(this);

        callnowlayout.setOnClickListener(this);
        booknowlayout.setOnClickListener(this);
    }

    private void getContracts(String _id) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getContracts(ApiClient.GET_CONTRACTS_BY_MEMBER_ID + _id);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.GET_CONTRACTS_BY_MEMBER_ID, true, iApiListener, false));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iVClose:
                dismiss();
                break;
            case R.id.LLaudio:


                if (!Utility.checkPermissionRequest(Permission.RECORD, getActivity())) {
                    Utility.raisePermissionRequest(Permission.RECORD, getActivity());
                } else {
                    dismiss();
                }
                break;
            case R.id.LLvideo:
                if (!Utility.checkPermissionRequest(Permission.CAMERA, getActivity())) {
                    Utility.raisePermissionRequest(Permission.CAMERA, getActivity());
                } else if (!Utility.checkPermissionRequest(Permission.RECORD, getActivity())) {
                    Utility.raisePermissionRequest(Permission.RECORD, getActivity());
                } else {
                   // konnectCalltoCeleb(Constants.VIDEO_CALL);
                    dismiss();
                    // konnectCalltoCeleb(Constants.AUDIO_CALL);
                }
                break;
            case R.id.LLchat:
                dismiss();

                    ChatDataConvertModel chatDataConvertModel = new ChatDataConvertModel();
                    try {
                        chatDataConvertModel._id = konectData._id;
                        chatDataConvertModel.firstName = konectData.firstName;
                        chatDataConvertModel.lastName = konectData.lastName;
                        chatDataConvertModel.avtar_imgPath = konectData.avtar_imgPath;
                        chatDataConvertModel.aboutMe = "";
                        chatDataConvertModel.profession = konectData.profession;
                        chatDataConvertModel.isCeleb = konectData.isCeleb;
                        chatDataConvertModel.message = "";
                        chatDataConvertModel.receiverId = konectData._id;
                        chatDataConvertModel.senderId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
                        chatDataConvertModel.createdAt = "";
                        chatDataConvertModel.counter = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Common.getInstance().openChatTabsActivity(getActivity(), chatDataConvertModel);

                break;
            case R.id.iVProfilePic:
                Common.getInstance().openProfileScreen(getActivity(), konectData._id);
                break;

            case R.id.callnowlayout:
                dismiss();

                /*AudioVideoApiCalls.getInstance().checkCelebAvaialabiltyForCall(getActivity(), konectData._id,
                        callType);*/
                iKonectDailogClick.IKonectDailogClick(konectData);
                break;
            case R.id.booknowlayout:
                Common.getInstance().openScheduleListFragment(context, konectData._id);
                break;
        }
    }



    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_CONTRACTS_BY_MEMBER_ID)) {
            Type type = new TypeToken<ContractCreditsData>() {
            }.getType();
            contractCreditsData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
            if (contractCreditsData != null) {
                tvAudioCredits.setText(contractCreditsData.audio + " per min");
                tvVideoCredits.setText(contractCreditsData.video + " per min");
                tvChatCredits.setText(contractCreditsData.chat + " per msg");
                Log.e("scheduelsSta", contractCreditsData.schedules + "");
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

    }
}
