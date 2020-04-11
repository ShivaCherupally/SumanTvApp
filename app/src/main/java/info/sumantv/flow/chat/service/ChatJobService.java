package info.sumantv.flow.chat.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.Log;
import info.sumantv.flow.userflow.Util.Common;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ChatJobService extends JobService {
    private static final String TAG = "ChatServiceJob";
    public static Integer jobRequestCode = 123;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        doBackgroundWork(params);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scheduleJob(getApplicationContext()); // reschedule the job
        }*/
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, ChatJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(1 * 1000); // wait at least
        builder.setOverrideDeadline(3 * 1000); // maximum delay
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        assert jobScheduler != null;
        int resultCode = jobScheduler.schedule(builder.build());
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

    public void cancelJob(Context context) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        assert scheduler != null;
        scheduler.cancel(jobRequestCode);
        Log.d(TAG, "Job cancelled");
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(() -> {
            Common.getInstance().doBackgroundWortHere();
            try {
                Thread.sleep(ChatService.serviceRunningTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(TAG, "Job finished");
            jobFinished(params, false);
        }).start();
    }
}
