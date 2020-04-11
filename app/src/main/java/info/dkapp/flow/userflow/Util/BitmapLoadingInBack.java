package info.dkapp.flow.userflow.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by abhishek on 30-01-2018.
 */

public class BitmapLoadingInBack extends AsyncTask<Integer, Void, Bitmap> {

    String bitmapUrl;
    Context context;
    ProgressDialog progressDialog;

    public BitmapLoadingInBack(String bitmapUrl, Context context) {
        this.bitmapUrl = bitmapUrl;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    protected Bitmap doInBackground(Integer... integers) {
        try {
            URL url = new URL(bitmapUrl);
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inJustDecodeBounds=true;
            options.inSampleSize = 3;
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
            //Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        progressDialog.cancel();
//        Common.getBitmapForShare(bitmap, context);
        Common.getLocalBitmapUri(bitmap, context);
//        SharedPrefsUtil
    }
}
