package info.dkapp.flow.utils;

import android.content.Context;

import info.dkapp.flow.retrofitcall.SessionManager;

/**
 *
 **/

public class CommonApis {

    public static boolean getCelebGivenPermissions(Context context) {
        boolean isFullAccess = false;
        if (checkCelebPermissionRequest(context, SessionManager.getInstance().getKeyValue(SessionManager.KEY_SUB_MANAGER_ID, ""))) {
            isFullAccess = true;
        } else {
            isFullAccess = false;
        }

        return isFullAccess;
    }

    private static boolean checkCelebPermissionRequest(Context context, String managerId) {
        boolean isFullAccess = false;

        isFullAccess = true;

        return isFullAccess;
    }


}
