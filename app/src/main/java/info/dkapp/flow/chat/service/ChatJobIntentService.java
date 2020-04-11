package info.dkapp.flow.chat.service;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.JobIntentService;
import android.util.Log;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ChatJobIntentService extends JobIntentService {
    private static final String TAG = "ChatServiceJobIntent";
    public static Integer jobRequestCode = 123;


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "enqueueWork started");
        enqueueWork(getApplicationContext(), intent);
    }

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, ChatJobService.class, jobRequestCode, work);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "All work complete");
    }
}
