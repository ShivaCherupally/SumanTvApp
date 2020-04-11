package info.dkapp.flow.splashandintroscreens.TutorialScreens;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Shiva on 2/20/2018.
 */

public class TutorialData implements Serializable {

    @SerializedName("_id")
    private String _id;

    @SerializedName("scrn_img_path")
    private String scrn_img_path;

    private Bitmap bitmapImage;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getScrn_img_path() {
        return scrn_img_path;
    }

    public void setScrn_img_path(String scrn_img_path) {
        this.scrn_img_path = scrn_img_path;
    }

    public TutorialData(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public Bitmap getBitmapImage() {
        return bitmapImage;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }
}
