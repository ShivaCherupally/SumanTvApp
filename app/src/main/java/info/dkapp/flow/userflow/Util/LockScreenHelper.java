package info.dkapp.flow.userflow.Util;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

public class LockScreenHelper {
    private static final String TAG = "LockScreenHelper";

    /**
     * Determine if the screen is on and the device is unlocked;
     * i.e. the user will see what is going on in the main activity.
     *
     * @param context Context
     * @return boolean
     */
    public static boolean isScreenUnlocked(Context context) {
        if (!isInteractive(context)) {
            Log.i(TAG, "device is NOT interactive");
            return false;
        } else {
            Log.i(TAG, "device is interactive");
        }

        if (!isDeviceProvisioned(context)) {
            Log.i(TAG, "device is not provisioned");
            return true;
        }

        Object keyguardService = context.getSystemService(Context.KEYGUARD_SERVICE);
        return !((KeyguardManager) keyguardService).inKeyguardRestrictedInputMode();
    }

    /**
     * @return Whether the screen of the device is interactive (screen may or may not be locked at the time).
     */
    @SuppressWarnings("deprecation")
    public static boolean isInteractive(Context context) {
        PowerManager manager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            return manager.isInteractive();
        } else {
            return manager.isScreenOn();
        }
    }

    /**
     * @return Whether the device has been provisioned (0 = false, 1 = true).
     * On a multiuser device with a separate system user, the screen may be locked as soon as this
     * is set to true and further activities cannot be launched on the system user unless they are
     * marked to show over keyguard.
     */
    private static boolean isDeviceProvisioned(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return true;
        }

        if (context == null) {
            return true;
        }

        if (context.getContentResolver() == null) {
            return true;
        }

        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.DEVICE_PROVISIONED, 0) != 0;
    }
}
