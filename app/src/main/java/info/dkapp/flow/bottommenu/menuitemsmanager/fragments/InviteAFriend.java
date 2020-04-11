package info.dkapp.flow.bottommenu.menuitemsmanager.fragments;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.bottommenu.menuitemsmanager.modelclasses.InviteInfo;

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
import retrofit2.Call;


/**
 * A simple {@link Fragment} subclass.
 */

public class InviteAFriend extends Fragment implements IFragment, IApiListener, View.OnClickListener {

    @BindView(R.id.btInviteFriend)
    Button btInviteFriend;

    @BindView(R.id.btCopyReferralCode)
    Button btCopyReferralCode;

    @BindView(R.id.tvReferralCode)
    TextView tvReferralCode;

    @BindView(R.id.tvNote)
    TextView tvNote;

    @BindView(R.id.LLUser_ref)
    LinearLayout LLUser_ref;

    @BindView(R.id.LLCeleb_ref)
    LinearLayout LLCeleb_ref;

    @BindView(R.id.iVServerPic)
    ImageView iVServerPic;

    IApiListener iApiListener;


    public InviteAFriend() {
        // Required empty public constructor
    }

    public static InviteAFriend newInstance(String param1) {
        InviteAFriend fragment = new InviteAFriend();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_invite_afriend, container, false);
        ButterKnife.bind(this, root);
        iApiListener = this;

        getInviteInfo();

        btInviteFriend.setOnClickListener(this::onClick);
        btCopyReferralCode.setOnClickListener(this::onClick);


        String videocall = "<font color='#000000'><b>Video Call</b></font>";
        String sourceString = "*Note These credits can be used only for " + videocall + " with you.";
        tvNote.setText(Html.fromHtml(sourceString));

        return root;
    }

    private void getInviteInfo() {
        String refferalUrl = ApiClient.GET_REFFERAL_CODE + SessionManager.userLogin.userId
                + "/" + Common.getInstance().getLoginStatus(activity()) + "/"
                + Common.getInstance().getSizeName(activity()) + "/" + "android";

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(refferalUrl);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.GET_REFFERAL_CODE, true, iApiListener, false));
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {

    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_REFFERAL_CODE)) {
            Type typeInfo = new TypeToken<InviteInfo>() {
            }.getType();
            InviteInfo inviteInfo = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), typeInfo);
            if (inviteInfo != null) {
                Log.e("inviteCode", inviteInfo.memberCode + "");
                Log.e("inviteimage", inviteInfo.inviteimage + "");
                tvReferralCode.setText(inviteInfo.memberCode);
                iVServerPic.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

                GlideToVectorYou.init();
                GlideToVectorYou.justLoadImage(activity(),
                        Uri.parse(ApiClient.BASE_URL + inviteInfo.inviteimage), iVServerPic);
            }
        }
    }


    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_REFFERAL_CODE)) {
            Toast.makeText(getActivity(), Constants.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btInviteFriend) {
            if (tvReferralCode.getText().toString() != null && !tvReferralCode.getText().toString().isEmpty()) {
                Common.shareSocialNetwork(getActivity(), "" +
                        tvReferralCode.getText().toString(), "APP_PROMOTION");
            } else {
                Toast.makeText(getActivity(), "Please Generate Code", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.btCopyReferralCode) {
            ClipboardManager clipboard = (ClipboardManager) activity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("text", tvReferralCode.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getActivity(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
        }
    }
}
