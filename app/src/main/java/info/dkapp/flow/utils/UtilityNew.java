package info.dkapp.flow.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import info.dkapp.flow.bottommenu.models.IPollOption;

import info.dkapp.flow.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Chenna on 10-08-2018.
 */

public class UtilityNew {


    public static String Calendar(Context context) {

        final String[] date = {""};
        Calendar mcurrentDate = Calendar.getInstance();
        int day, month, year;
        DatePickerDialog datepickerdialog;
        year = mcurrentDate.get(Calendar.YEAR);
        month = mcurrentDate.get(Calendar.MONTH);
        day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        datepickerdialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
//                        from_date_text.setText(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day));
                int month_k = selectedmonth + 1;
                ArrayList<String> fulldate = new ArrayList<String>();
                String[] months = {"", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "July"
                        , "Aug", "Sep", "Oct", "Nov", "Dec"};

                fulldate.add(String.valueOf(selectedday));
                fulldate.add(months[month_k]);
                fulldate.add(String.valueOf(selectedyear));


                date[0] = String.valueOf(fulldate.get(0)) + " " + String.valueOf(fulldate.get(1)) + " " + String.valueOf(fulldate.get(2));

            }
        }, year, month, day);

        // TODO Hide Past Date Here
        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis());

        // TODO Hide Future Date Here
        //  mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datepickerdialog.show();

        return date[0];

    }

    public static Spannable seeMore(String description, boolean checkble, String viewmore) {

        Spannable wordtoSpan = null;
        String getDescription1;
        if (checkble) {
            if (description.length() >= 100) {
                description = description.substring(0, 100) + viewmore;

                wordtoSpan = new SpannableString(description);

                wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 100, description.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            } else {
                getDescription1 = description + viewmore;
                wordtoSpan = new SpannableString(description);

                wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), description.length(), getDescription1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else {

            getDescription1 = description + viewmore;
            wordtoSpan = new SpannableString(getDescription1);

            wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), description.length(), getDescription1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        return wordtoSpan;
    }

    public static boolean isNetworkAvailable(Activity activity) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                return false;
                /* aka, do nothing */
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void showSnackBar(Context activity, CoordinatorLayout linearLayout, String text, int type) {
        if (linearLayout == null || text == null || activity == null) {
            return;
        }
        Snackbar snackBar = Snackbar.make(linearLayout, text, Snackbar.LENGTH_SHORT);
        TextView txtMessage = (TextView) snackBar.getView().findViewById(R.id.snackbar_text);
        txtMessage.setTextColor(ContextCompat.getColor(activity, R.color.white));
        if (type == 2)
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.black));
        else if (type == 1)
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.app_snack_bar_true));
        else {
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.status_color_red));
        }
        snackBar.show();
    }

    public static void showSnackBarLong(Context activity, CoordinatorLayout linearLayout, String text, int type) {
        if (linearLayout == null || text == null || activity == null) {
            return;
        }
        Snackbar snackBar = Snackbar.make(linearLayout, text, Snackbar.LENGTH_LONG);
        TextView txtMessage = (TextView) snackBar.getView().findViewById(R.id.snackbar_text);
        txtMessage.setTextColor(ContextCompat.getColor(activity, R.color.white));
        if (type == 2)
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.black));
        else if (type == 1)
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.app_snack_bar_true));
        else {
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.status_color_red));
        }
        snackBar.show();
    }

    public static void showSnackBar(Context activity, LinearLayout linearLayout, String text, int type) {
        if (linearLayout == null || text == null || activity == null) {
            return;
        }
        Snackbar snackBar = Snackbar.make(linearLayout, text, Snackbar.LENGTH_SHORT);
        TextView txtMessage = (TextView) snackBar.getView().findViewById(R.id.snackbar_text);
        txtMessage.setTextColor(ContextCompat.getColor(activity, R.color.white));
        if (type == 2)
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.black));
        else if (type == 1)
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.app_snack_bar_true));
        else {
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.status_color_red));
        }
        snackBar.show();
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static List<IPollOption> generateFeedUserOptions() {
        List<IPollOption> list = new ArrayList<>();
        IPollOption i1 = new IPollOption();
        i1.name = "Edit";
        i1.image = R.drawable.ic_edit;

        IPollOption i2 = new IPollOption();
        i2.name = "Delete";
        i2.image = R.drawable.ic_delete;

//        IPollOption i3 = new IPollOption();
//        i3.name = "Hide";
//        i3.image = R.drawable.ic_visibility_off;

        list.add(i1);
        list.add(i2);
//        list.add(i3);

        return list;
    }

    public static int dpSize(Context context, int sizeInDp) {

        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (sizeInDp * scale + 0.5f);

    }
    public static int dpSize(Context context, float sizeInDp) {

        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (sizeInDp * scale + 0.5f);

    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
