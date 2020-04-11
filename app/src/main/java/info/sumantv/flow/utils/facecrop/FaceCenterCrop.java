package info.sumantv.flow.utils.facecrop;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.sumantv.flow.appmanagers.feed.models.FacePoint;
import info.sumantv.flow.utils.Logger;

import java.util.List;

/**
 * Created by User on 24-08-2018.
 **/

public class FaceCenterCrop extends BasePostprocessor {

    protected int width, height;
    List<FacePoint> facePointList;

    public FaceCenterCrop(int width, int height, List<FacePoint> facePointList) {
        this.width = width;
        this.height = height;
        this.facePointList = facePointList;
    }

    @Override
    public CloseableReference<Bitmap> process(Bitmap sourceBitmap, PlatformBitmapFactory bitmapFactory) {
        if (width == 0 || height == 0) {
            return super.process(sourceBitmap, bitmapFactory);
        }
        /*if(facePointList == null || facePointList.size() <= 0) {
            //sourceBitmap = Bitmap.createBitmap(sourceBitmap, 0,0,width, height);
            return super.process(sourceBitmap, bitmapFactory);
        }*/
        float scaleX = (float) width / sourceBitmap.getWidth();
        float scaleY = (float) height / sourceBitmap.getHeight();
        if (scaleX != scaleY) {
            Bitmap.Config config = sourceBitmap.getConfig() != null ? sourceBitmap.getConfig() : Bitmap.Config.ARGB_8888;
            CloseableReference<Bitmap> bitmapRef = bitmapFactory.createBitmap(width, height, config);
            try {

                Bitmap destBitmap = bitmapRef.get();
                float scale = Math.max(scaleX, scaleY);
                float left = 0f;
                float top = 0f;
                float scaledWidth = width, scaledHeight = height;
                PointF focusPoint = new PointF();
                detectFace(sourceBitmap, focusPoint);
                Log.d("focusPointFresco", focusPoint.x+" @ "+focusPoint.y+" @ "+focusPoint.toString());
                if (scaleX < scaleY) {
                    scaledWidth = scale * sourceBitmap.getWidth();
                    float faceCenterX = scale * focusPoint.x;
                    left = getLeftPoint(width, scaledWidth, faceCenterX);
                    Logger.d("SCALED X",""+faceCenterX);
                } else {
                    scaledHeight = scale * sourceBitmap.getHeight();
                    float faceCenterY = scale * focusPoint.y;
                    top = getTopPoint(height, scaledHeight, faceCenterY);
                    Logger.d("SCALED Y",""+faceCenterY);
                }
                Logger.d("SCALED HEIGHT",scaledHeight + " WIDTH " + scaledWidth + " TOP " + top + " LEFT " + left);
                RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);
                Canvas canvas = new Canvas(destBitmap);
                canvas.drawBitmap(sourceBitmap, null, targetRect, null);
                return CloseableReference.cloneOrNull(bitmapRef);
            } catch (Exception e) {
                e.printStackTrace();
                return super.process(sourceBitmap, bitmapFactory);
            } finally {
                CloseableReference.closeSafely(bitmapRef);
            }
        } else {
            return super.process(sourceBitmap, bitmapFactory);
        }
    }

    private PointF detectFace(Bitmap bitmap, PointF centerOfAllFaces) {
        if(facePointList != null && facePointList.size() > 0) {
            final int totalFaces = facePointList.size();
            float sumX = 0f;
            float sumY = 0f;
            for (int i = 0; i < totalFaces; i++) {
                PointF faceCenter = new PointF();
                faceCenter = getFaceCenter(facePointList.get(i), faceCenter);
                sumX = sumX + faceCenter.x;
                sumY = sumY + faceCenter.y;
            }
            centerOfAllFaces.set(sumX / totalFaces, sumY / totalFaces);
            try {
                Log.d("facePointLIST", new Gson().toJson(facePointList,new TypeToken<List<FacePoint>>(){}.getType()));
                Log.d("facePointOfAllFaces", centerOfAllFaces.x +" @ "+centerOfAllFaces.y);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return centerOfAllFaces;
        } else if(bitmap.getWidth() >= bitmap.getHeight()){
            float x = (float)(bitmap.getWidth()/2 - bitmap.getHeight()/2);
            centerOfAllFaces.set(x,0); // crop from middle for rectangle images
            return centerOfAllFaces;
        } else {
            centerOfAllFaces.set(0,0); // crop from top left
            return centerOfAllFaces;
        }
    }

    private PointF getFaceCenter(FacePoint face, PointF center) {
        float x = face.x;
        float y = face.y;
        float width = face.width;
        float height = face.height;
        center.set(x + (width / 2), y + (height / 2));
        //Logger.d("FACE CENTER X","" + (x + (width / 2)));
        //Logger.d("FACE CENTER Y",""  + (y + (height / 2)));
        return center; // face center in original bitmap
    }

    /*private float getTopPoint(int height, float scaledHeight, float faceCenterY) {
        if (faceCenterY <= height / 2) { // Face is near the top edge
            return 0f;
        } else if ((scaledHeight - faceCenterY) <= height / 2) { // face is near bottom edge
            return height - scaledHeight;
        } else {
            return (height / 2) - faceCenterY;
        }
    }

    private float getLeftPoint(int width, float scaledWidth, float faceCenterX) {
        if (faceCenterX <= width / 2) { // face is near the left edge.
            return 0f;
        } else if ((scaledWidth - faceCenterX) <= width / 2) {  // face is near right edge
            return (width - scaledWidth);
        } else {
            return (width / 2) - faceCenterX;
        }
    }*/

    private float getTopPoint(int height, float scaledHeight, float faceCenterY) {
        if (faceCenterY <= height / 2) { // Face is near the top edge
            return 0f;
        } else if ((scaledHeight - faceCenterY) <= height / 2) { // face is near bottom edge
            if(width % faceCenterY > 1) {
                return -(scaledHeight - height)/2;
            } else {
                return (height - scaledHeight);
            }
        } else {
            if(faceCenterY%height > 1) {
                return ((height / 2) - faceCenterY)/2;
            } else {
                return (height / 2) - faceCenterY;
            }
        }
    }

    private float getLeftPoint(int width, float scaledWidth, float faceCenterX) {
        Logger.d("LEFT POINTS","" + width +" " + scaledWidth + " "+ faceCenterX);
        if (faceCenterX <= width / 2) { // face is near the left edge.
            return 0f;
        }
        if ((scaledWidth - faceCenterX) <= width / 2) {  // face is near right edge
            if(width % faceCenterX >=1) {
                if(faceCenterX - width < 100) {
                    return -(scaledWidth - width)/2;
                } else {
                    return (width - scaledWidth);
                }
            } else {
                return (width - scaledWidth);
            }
        } else {
            if(faceCenterX%width > 1) {
                return ((width / 2) - faceCenterX)/2;
            } else {
                return (width / 2) - faceCenterX;
            }
        }
    }

    /*private PointF detectFace(Bitmap bitmap, PointF centerOfAllFaces) {
        final int totalFaces = facePointList.size();
        if (totalFaces > 0) {
            float sumX = 0f;
            float sumY = 0f;
            for (int i = 0; i < totalFaces; i++) {
                PointF faceCenter = new PointF();
                faceCenter = getFaceCenter(facePointList.get(i), faceCenter);
                sumX = sumX + faceCenter.x;
                sumY = sumY + faceCenter.y;
            }
            centerOfAllFaces.set(sumX / totalFaces, sumY / totalFaces);
            return centerOfAllFaces;
        }
        centerOfAllFaces.set(bitmap.getWidth() / 2, bitmap.getHeight() / 2); // center crop
        return centerOfAllFaces;
    }

    private PointF getFaceCenter(FacePoint face, PointF center) {
        float x = face.x;
        float y = face.y;
        float width = face.width;
        float height = face.height;
        center.set(x + (width / 2), y + (height / 2));
        //Logger.d("FACE CENTER X","" + (x + (width / 2)));
        //Logger.d("FACE CENTER Y",""  + (y + (height / 2)));
        return center; // face center in original bitmap
    }

    private float getTopPoint(int height, float scaledHeight, float faceCenterY) {
        if (faceCenterY <= height / 2) { // Face is near the top edge
            return 0f;
        } else if ((scaledHeight - faceCenterY) <= height / 2) { // face is near bottom edge
            return height - scaledHeight;
        } else {
            return (height / 2) - faceCenterY;
        }
    }

    private float getLeftPoint(int width, float scaledWidth, float faceCenterX) {
        if (faceCenterX <= width / 2) { // face is near the left edge.
            return 0f;
        } else if ((scaledWidth - faceCenterX) <= width / 2) {  // face is near right edge
            return (width - scaledWidth);
        } else {
            return (width / 2) - faceCenterX;
        }
    }*/
}
