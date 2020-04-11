package info.dkapp.flow.utils.facecrop;

import android.graphics.Bitmap;
import android.util.Log;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.request.BasePostprocessor;
import info.dkapp.flow.appmanagers.feed.models.FacePoint;


import javax.annotation.Nullable;

import java.util.List;

/**
 * Created by User on 24-08-2018.
 **/

public class FaceCenterCropNew extends BasePostprocessor {

    protected int width, height;
    List<FacePoint> facePointList;

    public FaceCenterCropNew(int width, int height, List<FacePoint> facePointList)
    {
        this.width = width;
        this.height = height;
        this.facePointList = facePointList;
    }


    public FaceCenterCropNew() {
        super();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public CloseableReference<Bitmap> process(Bitmap sourceBitmap, PlatformBitmapFactory bitmapFactory) {
        Log.e("CloseableReference", true + "");
        return super.process(sourceBitmap, bitmapFactory);
    }

    @Override
    public void process(Bitmap destBitmap, Bitmap sourceBitmap) {
        Log.e("Process", true + "");
        super.process(destBitmap, sourceBitmap);
    }

    @Override
    public void process(Bitmap bitmap) {
        Log.e("ProcessSample", true + "");
        super.process(bitmap);
    }

    @Nullable
    @Override
    public CacheKey getPostprocessorCacheKey() {
        Log.e("CacheKey", true + "");
        return super.getPostprocessorCacheKey();
    }
}
