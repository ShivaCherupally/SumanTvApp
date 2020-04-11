package info.sumantv.flow.userflow.Util;

import android.graphics.Bitmap;

/**
 * Created by Shiva on 2/27/2018.
 */

public class ImagesData {
    private Bitmap bitmapImage;

    public Bitmap getBitmapImage() {
        return bitmapImage;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public ImagesData(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }
}
