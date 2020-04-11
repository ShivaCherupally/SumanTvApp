//package info.celebkonnect.flow.userflow.Util;
//
//import android.annotation.SuppressLint;
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.os.Bundle;
//import android.support.v4.app.DialogFragment;
//import android.support.v4.app.FragmentManager;
//import android.view.View;
//import android.widget.DatePicker;
//import android.widget.TextView;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
///**
// */
//
//@SuppressLint("ValidFragment")
//public class DatePickerFragmentDailoge extends DialogFragment
//        implements DatePickerDialog.OnDateSetListener {
//    FragmentManager fragmentManager;
//    TextView selectedDate;
//
//    public DatePickerFragmentDailoge(FragmentManager fragmentManager, TextView selectedDate) {
//        this.fragmentManager = fragmentManager;
//        this.selectedDate = selectedDate;
//
//    }
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the current date as the default date in the picker
//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
////			dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
//        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        return dialog;
//    }
//
//    public void onDateSet(DatePicker view, int year, int month, int day) {
//        Date date = new Date(year, month, day);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM");
//        String sectiondate = simpleDateFormat.format(date.getTime()) + "-" + year;//simDf.format(c.getTime());
//        selectedDate.setText("");
//        selectedDate.setVisibility(View.VISIBLE);
//        selectedDate.setText(sectiondate);

//        // Do something with the date chosen by the user
//
//    }
//}
