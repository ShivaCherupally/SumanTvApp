package info.sumantv.flow.bottommenu.celebrityprofile;

import android.app.Activity;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataAdapter;
import info.sumantv.flow.bottommenu.generic.EmptyDataNewAdapter;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Utility;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONObject;
import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FragmentCelebSchedules extends Fragment implements IFragment, IApiListener {
    private CoordinatorLayout coordinator_layout;
    RecyclerView rvChatList;
    CelebSchedulesListAdapter celebSchedulesListAdapter;
    ArrayList<CelebScheduleModel> arrayListData;
    IApiListener iApiListener;
    String CELEB_ID = "";

    public FragmentCelebSchedules() {
        // Required empty public constructor
    }

    public static FragmentCelebSchedules newInstance(String param1, String param2) {
        FragmentCelebSchedules fragment = new FragmentCelebSchedules();
        Bundle args = new Bundle();
        args.putString("CELEB_ID", param1);
        args.putString("empty", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Common.getInstance().setFragmentNewChatList(this);
        if (getArguments() != null) {
            CELEB_ID = getArguments().getString("CELEB_ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_celeb_schedules, container, false);
        iApiListener = this;
        arrayListData = new ArrayList<>();
        initializeViews(root);
        getData();
        return root;
    }

    private void initializeViews(View root) {
        coordinator_layout = root.findViewById(R.id.coordinator_layout);
        rvChatList = root.findViewById(R.id.rvChatList);
        rvChatList.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
    }

    private void getData() {
        celebSchedulesListAdapter = new CelebSchedulesListAdapter(null, activity(), RController.LOADING);
        rvChatList.setAdapter(celebSchedulesListAdapter);
        if (!Common.checkInternetConnection(getContext())) {
            rvChatList.removeAllViews();
            rvChatList.setAdapter(new EmptyDataAdapter(getActivity(), Constants.UH_OH, Constants.SEEMS_LIKE_NO_INTRNET,
                    R.drawable.ic_network, 0));
            return;
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("memberId", CELEB_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Call<ApiResponseModel> call = apiInterface.POST(ApiClient.GET_CELEB_SCHEDULES, requestBody);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_CELEB_SCHEDULES, false, iApiListener, false));
    }

    private void setChatListAdapter(ArrayList<CelebScheduleModel> arrayListData) {
        try {
            if (arrayListData.size() > 0) {
                celebSchedulesListAdapter = new CelebSchedulesListAdapter(arrayListData, activity(), RController.LOADED);
                rvChatList.setAdapter(celebSchedulesListAdapter);
            } else {
                rvChatList.setAdapter(new EmptyDataAdapter(activity(),
                        Constants.SORRY, Constants.NO_SCHEDULES_AVAILABLE, R.drawable.ic_no_upcoming_events, 6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        Utility.showSnackBar(activity(), coordinator_layout, snackBarText, type);
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_CELEB_SCHEDULES)) {
            try {
                Type type = new TypeToken<ArrayList<CelebScheduleModel>>() {
                }.getType();
                arrayListData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                setChatListAdapter(arrayListData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_CELEB_SCHEDULES)) {
//            rvChatList.setAdapter(new EmptyDataAdapter(activity(), enumConstants == EnumConstants.NO_NETWORK ? Constants.NO_INTERNET : Constants.SOMETHING_WENT_WRONG, R.drawable.ic_network, 2));
            rvChatList.setAdapter(new EmptyDataNewAdapter(getActivity(), Constants.SOMETHING_WENT_WRONG_SERVER, Constants.DRAG_TO_RETRY,
                    R.drawable.ic_no_upcoming_events));
        }
    }
}
