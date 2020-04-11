package info.sumantv.flow.utils.RecyclerUtil;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataAdapter;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.utils.CommonSkeletonAdapter;

public class CommonRecycler {

    public static boolean setNoInternetOrServerDown(Context mContext, RecyclerView recyclerViewCommon, boolean serverDown) {
        recyclerViewCommon.removeAllViews();
        if (!serverDown) {
            recyclerViewCommon.setAdapter(new EmptyDataAdapter(mContext, Constants.UH_OH, Constants.PLEASE_CHECK_INTERNET, R.drawable.ic_network, 0));
        } else {
            recyclerViewCommon.setAdapter(new EmptyDataAdapter(mContext, Constants.SOMETHING_WENT_WRONG_SERVER, Constants.DRAG_TO_RETRY, R.drawable.ic_network, 0));
        }
        return false;
    }

    public static void setSkelltonView(Context mContext, RecyclerView recyclerViewCommon, boolean verticalAccess, boolean firstTime) {

        if (firstTime) {
            if (verticalAccess) {
                recyclerViewCommon.setLayoutManager(new LinearLayoutManager(mContext));
            } else {
                recyclerViewCommon.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            }
            CommonSkeletonAdapter commonSkeletonAdapter = new CommonSkeletonAdapter(RController.LOADING);
            recyclerViewCommon.setAdapter(commonSkeletonAdapter);
        }

    }


    public static void setSkelltonViewOther(Context mContext, RecyclerView recyclerViewCommon, boolean verticalAccess, boolean firstTime, boolean otherScreens) {

        if (firstTime) {
            if (verticalAccess) {
                recyclerViewCommon.setLayoutManager(new LinearLayoutManager(mContext));
            } else {
                recyclerViewCommon.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            }
            CommonSkeletonAdapter commonSkeletonAdapter = new CommonSkeletonAdapter(RController.LOADING, otherScreens);
            recyclerViewCommon.setAdapter(commonSkeletonAdapter);
        }

    }


}
