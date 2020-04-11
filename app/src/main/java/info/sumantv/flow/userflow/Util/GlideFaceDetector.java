package info.sumantv.flow.userflow.Util;

import android.content.Context;
import com.google.android.gms.vision.face.FaceDetector;

public class GlideFaceDetector {

    private static volatile FaceDetector faceDetector;
    private static Context mContext;

    public static Context getContext() {
        if (mContext == null) {
            throw new RuntimeException("Initialize GlideFaceDetector by calling GlideFaceDetector.initialize(context).");
        }
        return mContext;
    }

    public static void initialize(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        mContext = context.getApplicationContext();
    }

    private static void initDetector() {
        if (faceDetector == null) {
            synchronized ((GlideFaceDetector.class)) {
                if (faceDetector == null) {
                    faceDetector = new
                            FaceDetector.Builder(getContext())
                            .setTrackingEnabled(false)
                            .build();
                }
            }
        }
    }

    public static FaceDetector getFaceDetector() {
        initDetector();
        return faceDetector;
    }

    public static void releaseDetector() {
        if (faceDetector != null) {
            faceDetector.release();
            faceDetector = null;
        }
        mContext = null;
    }
}
