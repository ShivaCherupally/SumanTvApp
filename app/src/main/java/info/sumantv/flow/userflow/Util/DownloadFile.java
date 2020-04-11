//package info.celebkonnect.flow.userflow.Util;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.os.Environment;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.cardinalcommerce.shared.userinterfaces.ProgressDialog;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URL;
//import java.net.URLConnection;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import info.celebkonnect.flow.utils.Utility;
//
//public class DownloadFile extends AsyncTask<String, String, String> {
//
//    private static final String TAG = "DownloadFile";
//    private ProgressDialog progressDialog;
//    private String fileName;
//    private String folder;
//    private boolean isDownloaded;
//
//    /**
//     * Before starting background thread
//     * Show Progress Bar Dialog
//     */
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        Common.getInstance().showProgressDialog(Utility.getContext());
//    }
//
//    /**
//     * Downloading file in background thread
//     */
//    @Override
//    protected String doInBackground(String... f_url) {
//        int count;
//        try {
//            URL url = new URL(f_url[0]);
//            URLConnection connection = url.openConnection();
//            connection.connect();
//            // getting file length
//            int lengthOfFile = connection.getContentLength();
//
//            // input stream to read file - with 8k buffer
//            InputStream input = new BufferedInputStream(url.openStream(), 8192);
//
////            String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//            //Extract file name from URL
//            fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1, f_url[0].length());
//
//            //Append timestamp to file name
////            fileName = timestamp + "_" + fileName;
//
////            if (Common.getInstance().checkImageExtension(Utility.getContext(), url))
//            //External directory path to save file
//            folder = Environment.getExternalStorageDirectory() + File.separator + "Celebkonect" + File.separator + "Videos/";
//
//            Common.getInstance().addImageWaterMark(fileName);
//
//            //Create androiddeft folder if it does not exist
//            File directory = new File(folder);
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
//
//            // Output stream to write file
//            OutputStream output = new FileOutputStream(folder + fileName);
//
//            byte data[] = new byte[1024];
//
//            long total = 0;
//
//            while ((count = input.read(data)) != -1) {
//                total += count;
//                output.write(data, 0, count);
//            }
//
//            // flushing output
//            output.flush();
//
//            // closing streams
//            output.close();
//            input.close();
//            return "Downloaded at: " + folder + fileName;
//
//        } catch (Exception e) {
//            Log.e("Error: ", e.getMessage());
//        }
//        return "Something went wrong";
//    }
//
//    /**
//     * Updating progress bar
//     */
//    protected void onProgressUpdate(String... progress) {
//        // setting progress percentage
////        progressDialog.setProgress(Integer.parseInt(progress[0]));
//
//    }
//
//
//    @Override
//    protected void onPostExecute(String message) {
//        Common.getInstance().closeProgressDialog();
//        // Display File path after downloading
//        Log.d("saveDir", message);
//        Toast.makeText(Utility.getContext(), "Video downloaded successfully", Toast.LENGTH_LONG).show();
//    }
//}
//
