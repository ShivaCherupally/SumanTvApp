package info.sumantv.flow.userflow.Util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class DateUtil {
    public static final int SECOND_MILLIS = 1000;

    public static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;

    public static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;

    public static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static Runnable mStatusCheckerr = null;

    public static final String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July",
            "Aug", "Sept", "Oct", "Nov", "Dec"};
    private static Object currentDateAndTimeInUTC;

    public static String dateToEpoch(String enteredDate) {
        long epoch = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd");
        try {
            Date date = format.parse(enteredDate);
            System.out.println(date);
            epoch = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return epoch + "";
    }

    public static String dateOnlyAccess(String enteredDate) {
        long epoch = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd");
        try {
            Date date = format.parse(enteredDate);
            System.out.println(date);
            epoch = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return epoch + "";
    }


    public static long dob(String enteredDate) {
        long epoch = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(enteredDate);
            System.out.println(date);
            epoch = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return epoch;
    }


    public static String ageCal(String enteredDate) {
        long epoch = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd");
        try {
            Date date = format.parse(enteredDate);
            System.out.println(date);
            epoch = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return epoch + "";
    }


    public static String epochToDate(long epoch) {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date(epoch));
    }

    public static String fetchGraphDate(long epoch) {
        return new SimpleDateFormat("MMM yy").format(new Date(epoch));
    }


    public static String colepochToDate(long epoch) {
        return new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(new Date(epoch));
    }

    public static long dateTimeToEpoch(String enteredDate) {
        long epoch = 0;
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'"); //2018-11-21T12:37:25.213Z_Time
        format.setTimeZone(TimeZone.getTimeZone("UTC")); //"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
//        sp_start.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = format.parse(enteredDate);
            System.out.println(date);
            epoch = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return epoch;
    }

    public static long dateTimeToEpochOtherFormat(String enteredDate) {
        long epoch = 0;
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); //2018-11-21T12:37:25.213Z_Time
        format.setTimeZone(TimeZone.getTimeZone("UTC")); //"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
//        sp_start.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = format.parse(enteredDate);
            System.out.println(date);
            epoch = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return epoch;
    }


    public static long colTime(String enteredDate) {

        long epoch = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date date = format.parse(enteredDate);
            System.out.println(date);
            epoch = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return epoch;
    }


    public static long timeequlality(String enteredDate) {
        enteredDate.replace("AM", "am");
        enteredDate.replace("PM", "pm");
        Log.e("entereddate", enteredDate + "");
        long epoch = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
        try {
            Date date = format.parse(enteredDate);
            System.out.println(date);
            epoch = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return epoch;
    }

    public static String coldateC(String enteredDate) {
        long epoch = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        try {
            Date date = format.parse(enteredDate);
            System.out.println(date);
            epoch = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return epoch + "";
    }

    public static String currentDate() {
        String updateDate = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simDf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            updateDate = simDf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateDate;
    }

    public static double mintsTohoursCovert(String minutes) {
        return Double.parseDouble(minutes) / 60.0;
    }

    public static String mintsToCoverthoursMin(String minutes) {
        double hoursAndMin = Double.parseDouble(minutes) / 60.0;
        String[] strarray = String.format("%.2f", hoursAndMin).split("\\.");
        return String.valueOf(Integer.parseInt(strarray[0]) + " Hrs "
                + Integer.parseInt(strarray[1]) + " Min").toString();
    }


    public static String currentTime() {
        String currentTime = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        try {
            currentTime = simpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentTime;
    }

    public static String serverSentDateChange(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
            String bar = serverDate.toString();
            serverDate = bar.substring(0, 10);
            SimpleDateFormat simDf = new SimpleDateFormat("dd-MM-yyyy");
            Date yourDate = simDf.parse(serverDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourDate);
            calendar.get(Calendar.YEAR); //Day of the Year :)
            calendar.get(Calendar.DATE); //Day of the day :)
            int orderMonth = calendar.get(Calendar.MONTH) + 1;
            String monthNamee = monthName[calendar.get(Calendar.MONTH)];
            changeFormatDate = calendar.get(Calendar.DATE)
                    + "-" + monthNamee
                    + "-" + calendar.get(Calendar.YEAR);
            Log.e("changedate", changeFormatDate + "");
            //2018-04-16T05:16:40.769Z

        } catch (Exception e) {

        }
        return changeFormatDate;
    }

    public static String serverSentTimeChange(String servertime) {
        Log.e("serverDate", servertime);
        String chnagesTime = "";
        try {
            String output = servertime.toString().replace("T", " ");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date now = sdf.parse(output); //new Date();
            chnagesTime = sdf.format(now);
            System.out.println(chnagesTime);
            return chnagesTime;
        } catch (Exception e) {

        }
        return chnagesTime;
    }

    public static String currentdateandTime() {
        String chnagesTime = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date now = new Date();
            chnagesTime = sdf.format(now);
            System.out.println(chnagesTime);
            return chnagesTime;
        } catch (Exception e) {

        }
        return chnagesTime;
    }


    public static String currentdateandTimeInUTC() {
        String chnagesTime = "";
        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date now = new Date();
            chnagesTime = format.format(now);
            System.out.println(chnagesTime);
            return chnagesTime;
        } catch (Exception e) {

        }
        return chnagesTime;
    }

    //

    public static boolean compareEpoicTimes(final String endTime, final String ongoingTime) {
        //2018-11-22T03:30:36.392Z
        //2018-11-22T03:29:55.392Z

        Log.e("endTime", endTime + "_Time"); //2018-11-22T03:20:49.870Z_Time
        Log.e("ongoingTime", ongoingTime + "_Time"); ///2018-11-22T03:19:59.870Z_Time //2018-11-20T08:07:31.011Z //2018-11-22T03:29:55.392Z

        boolean callExpiretime = false;
        long endTimeEpoc = DateUtil.dateTimeToEpochOtherFormat(endTime);
        long ongoingTimeEpoc = DateUtil.dateTimeToEpochOtherFormat(ongoingTime);

        Log.e("Call_Inti", endTimeEpoc + "_Call");
        Log.e("Handler_Time", ongoingTimeEpoc + "_Call");

        if (ongoingTimeEpoc <= endTimeEpoc) {
            callExpiretime = false;
        } else {
            callExpiretime = true;
        }

        return callExpiretime;
    }



    /*
    public static String serverSentDateChange(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd");
            Date yourDate = simDf.parse(serverDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourDate);
            calendar.get(Calendar.YEAR); //Day of the Year :)
            calendar.get(Calendar.DATE); //Day of the day :)
            int orderMonth = calendar.get(Calendar.MONTH) + 1;
            changeFormatDate = calendar.get(Calendar.DATE)
                    + "-" + orderMonth
                    + "-" + calendar.get(Calendar.YEAR);
            Log.e("changedate", changeFormatDate + "");

        } catch (Exception e) {

        }
        return changeFormatDate;
    }*/


    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime()
                    - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static String getTimeAgo(long time) {
        long now = (long) 0.0;
        if (time < 1000000000000L) {
            time *= 1000;
        }
        try {
            //1515502800000
            //1515065235277

            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String currentDateTime = DateUtil.currentdateandTime();
            Date currentDateTimedate = simDf.parse(currentDateTime);
            now = currentDateTimedate.getTime();
        } catch (Exception e) {

        }


//        long now = currentmillSec;// System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }
        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";  //ss
        }
    }

    public static String getTimeLeft(long time) {
        long now = (long) 0.0;
        if (time < 1000000000000L) {
            time *= 1000;
        }
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String currentDateTime = DateUtil.currentdateandTime();
            Date currentDateTimedate = simDf.parse(currentDateTime);
            now = currentDateTimedate.getTime();
        } catch (Exception e) {
        }
        final long diff = time - now;
        if (time > now || time <= 0) {
            if (diff > 48 * HOUR_MILLIS) {
                return diff / DAY_MILLIS + " days Left";
            } else if (diff > 24 * HOUR_MILLIS) {
//                return diff / HOUR_MILLIS + " hours Left";
                return diff / HOUR_MILLIS + " hours Left";
            } else if (diff > 90 * MINUTE_MILLIS) {
                return diff / HOUR_MILLIS + " hours Left";
//                return "an hour Left";
            } else if (diff > 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " minutes Left";
//                return diff / MINUTE_MILLIS + " minutes Left";
            } else if (diff > 2 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " minutes left";
                //return "a minute left";
            } else {
                return diff / DAY_MILLIS + " days Left";
            }
//            return null;
        } else {
            return diff / DAY_MILLIS + " days Left";
        }
        // TODO: localize

    }

    public static String serverSentDateConvertTime(String serverDate) {
        SimpleDateFormat sp_start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        DateFormat dateFormat_start = new SimpleDateFormat("hh:mm a");
        Date start_date = null;
        try {
            start_date = sp_start.parse(serverDate);
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        String outPut_start_time = dateFormat_start.format(start_date);
        return outPut_start_time;
    }


    public static String serverSentDateInHoursAndMin(String serverDate) {
        SimpleDateFormat sp_start = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
        //
        //2018-04-27T07:10:13.804Z
        sp_start.setTimeZone(TimeZone.getTimeZone("UTC"));
//        DateFormat dateFormat_start = new SimpleDateFormat("hh:mm a");
        DateFormat dateFormat_start = new SimpleDateFormat("hh:mm");
        Date start_date = null;
        try {
            start_date = sp_start.parse(serverDate);
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        String outPut_start_time = dateFormat_start.format(start_date);
        return outPut_start_time;
    }

    public static String serverSentDateInHoursAndMinAndSec(String serverDate) {
//        SimpleDateFormat sp_start = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
        SimpleDateFormat sp_start = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        //
        //2018-04-27T07:10:13.804Z
        sp_start.setTimeZone(TimeZone.getTimeZone("UTC"));
//        DateFormat dateFormat_start = new SimpleDateFormat("hh:mm a");
//        DateFormat dateFormat_start = new SimpleDateFormat("hh:mm:ss");
        DateFormat dateFormat_start = new SimpleDateFormat("HH:mm:ss");
        Date start_date = null;
        try {
            start_date = sp_start.parse(serverDate);
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        String outPut_start_time = dateFormat_start.format(start_date);
        return outPut_start_time;
    }

    public static String serverSentDateConvertTimeInSchedule(String serverDate) {
        //2018-04-16T07:52:36.500Z
//        SimpleDateFormat sp_start = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
        SimpleDateFormat sp_start = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sp_start.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateFormat dateFormat_start = new SimpleDateFormat("hh:mm a");
        Date start_date = null;
        try {
            start_date = sp_start.parse(serverDate);
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        String outPut_start_time = dateFormat_start.format(start_date);
        Log.e("outPutTime", outPut_start_time);
        return outPut_start_time;
    }

    public static String scheduleDateConvertTimeInSchedule(String serverDate) {
        //2018-04-16T07:52:36.500Z
        SimpleDateFormat sp_start = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
        sp_start.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateFormat dateFormat_start = new SimpleDateFormat("hh:mm");
        Date start_date = null;
        try {
            start_date = sp_start.parse(serverDate);
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        String outPut_start_time = dateFormat_start.format(start_date);
        Log.e("outPutTime", outPut_start_time);
        return outPut_start_time;
    }

    public static String getOnlyYear(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
//            String bar = serverDate.toString();
//            serverDate = bar.substring(0, 10);
//            SimpleDateFormat simDf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(serverDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourDate);
            calendar.get(Calendar.YEAR); //Day of the Year :)
            calendar.get(Calendar.DATE); //Day of the day :)
            int orderMonth = calendar.get(Calendar.MONTH) + 1;
            String monthNamee = monthName[calendar.get(Calendar.MONTH)];
            changeFormatDate = calendar.get(Calendar.YEAR) + "";
            Log.e("changedate", changeFormatDate + "");

        } catch (Exception e) {

        }
        return changeFormatDate;
    }

    public static String getMonthAndDate(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(serverDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourDate);
            calendar.get(Calendar.YEAR); //Day of the Year :)
            calendar.get(Calendar.DATE); //Day of the day :)
            String monthNamee = monthName[calendar.get(Calendar.MONTH)];
            changeFormatDate = calendar.get(Calendar.DATE)
                    + "-" + monthNamee;
            Log.e("changedate", changeFormatDate + "");
        } catch (Exception e) {

        }
        return changeFormatDate;
    }


    public static String getMonAndDate(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
//            String bar = serverDate.toString();
//            serverDate = bar.substring(0, 10);
//            SimpleDateFormat simDf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(serverDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourDate);
            calendar.get(Calendar.YEAR); //Day of the Year :)
            calendar.get(Calendar.DATE); //Day of the day :)
            int orderMonth = calendar.get(Calendar.MONTH) + 1;
            String monthNamee = monthName[calendar.get(Calendar.MONTH)];
            changeFormatDate = calendar.get(Calendar.YEAR) + "";
            Log.e("changedate", changeFormatDate + "");

        } catch (Exception e) {

        }
        return changeFormatDate;
    }


    public static String getDurationInBetweenStartAndEnd(String startTime, String endTime) {
        String durationTime = "";
        String minutes = null;
        String hours = null;
        SimpleDateFormat format_ = new SimpleDateFormat("HH:mm");//"HH:mm:ss"
        try {
            Date d1_ = format_.parse(startTime);
            Date d2_ = format_.parse(endTime);
            long difference = d2_.getTime() - d1_.getTime();
            Log.e("differenceTime", difference + "");
            minutes = String.valueOf((int) ((difference / (1000 * 60)) % 60));
            hours = String.valueOf((int) ((difference / (1000 * 60 * 60)) % 24));

            if (minutes.equals("0")) {
                if (hours.equals("1")) {
                    durationTime = "60" + " Min";
//                        holder.textView_duration.setText(" " + "60" + " " + " min");
                } else {
                    durationTime = minutes + " Min";
//                        holder.textView_duration.setText(" " + minutes + " " + " min");
                }
            } else if (minutes.equals("1")) {
                if (hours.equals("1")) {
                    durationTime = "61" + " Min";
//                        holder.textView_duration.setText(" " + "61" + " " + " min");
                } else {
                    durationTime = minutes + " Min";
//                        holder.textView_duration.setText(" " + minutes + " " + " min ");
                }

            } else {
                durationTime = minutes + " Min";
//                    holder.textView_duration.setText(" " + minutes + " " + " min ");
            }

        } catch (Exception e) {
        }

        return durationTime;
    }

    public static String getDurationInBetweenStartAndEndInMin(String startTime, String endTime) {
        String MinDiffernece = "";
        SimpleDateFormat format_ = new SimpleDateFormat("HH:mm:ss");//"HH:mm:ss"
        try {
            Date d1_ = format_.parse(startTime);
            Date d2_ = format_.parse(endTime);
            long difference = d2_.getTime() - d1_.getTime();
            Log.e("differenceTime", difference + "");
            MinDiffernece = String.valueOf(TimeUnit.MILLISECONDS.toMinutes(difference)) + " Mins";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return MinDiffernece;
    }

    public static String getFeedDateAndTme(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(serverDate);
            String currentdateandTimeinUtc = simDf.format(yourDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourDate);
            calendar.get(Calendar.YEAR); //Day of the Year :)
            calendar.get(Calendar.DATE); //Day of the day :)
            String monthNamee = monthName[calendar.get(Calendar.MONTH)];
            changeFormatDate = monthNamee + " " + calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.YEAR);
//            changeFormatDate = calendar.get(Calendar.DATE)
//                    + "-" + monthNamee + "-" + calendar.get(Calendar.YEAR);
            Log.e("changedate", changeFormatDate + "");
        } catch (Exception e) {

        }
        return changeFormatDate;
    }

    public static String getPaymentGatewayDate(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'", Locale.US);
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(serverDate);
            String currentdateandTimeinUtc = simDf.format(yourDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourDate);
            calendar.get(Calendar.YEAR); //Day of the Year :)
            calendar.get(Calendar.DATE); //Day of the day :)
            String monthNamee = monthName[calendar.get(Calendar.MONTH)];
            changeFormatDate = monthNamee + " " + calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.YEAR);
//            changeFormatDate = calendar.get(Calendar.DATE)
//                    + "-" + monthNamee + "-" + calendar.get(Calendar.YEAR);
            Log.e("changedate", changeFormatDate + "");
        } catch (Exception e) {

        }
        return changeFormatDate;
    }

    public static String getPaymentGatewayTime(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'", Locale.US);
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(serverDate);
            String currentdateandTimeinUtc = simDf.format(yourDate);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            String currentDateTimeString = sdf.format(yourDate);
            changeFormatDate = currentDateTimeString;
        } catch (Exception e) {

        }
        return changeFormatDate;
    }


    public static String getCompleteDate(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(serverDate);
            String currentdateandTimeinUtc = simDf.format(yourDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourDate);
            calendar.get(Calendar.YEAR); //Day of the Year :)
            calendar.get(Calendar.DATE); //Day of the day :)
            String monthNamee = monthName[calendar.get(Calendar.MONTH)];
            changeFormatDate = calendar.get(Calendar.DATE)
                    + " " + monthNamee + " " + calendar.get(Calendar.YEAR);
            Log.e("changedate", changeFormatDate + "");
        } catch (Exception e) {

        }
        return changeFormatDate;
    }


    public static String getCompleteYearPrefession(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(serverDate);
            String currentdateandTimeinUtc = simDf.format(yourDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourDate);
            calendar.get(Calendar.YEAR); //Day of the Year :)
            changeFormatDate = String.valueOf(calendar.get(Calendar.YEAR));
            Log.e("changedate", changeFormatDate + "");
        } catch (Exception e) {

        }
        return changeFormatDate;
    }

    //Check new
    /*public static String getCompleteDateMyAuditions(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(serverDate);
            String currentdateandTimeinUtc = simDf.format(yourDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourDate);
            calendar.get(Calendar.YEAR); //Day of the Year :)
            calendar.get(Calendar.DATE); //Day of the day :)
            String monthNamee = monthName[calendar.get(Calendar.MONTH)];
            changeFormatDate = calendar.get(Calendar.DATE) - 1
                    + " " + monthNamee + " " + calendar.get(Calendar.YEAR);
            Log.e("changedate", changeFormatDate + "");
        } catch (Exception e) {

        }
        return changeFormatDate;
    }*/


    public static String getDateAndMonthWithUTC(String dateUTC) {
        Log.e("dateINUTC", dateUTC);
        Date finalDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        //"yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            finalDate = format.parse(dateUTC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (finalDate == null) {
            return "NA";
        }
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        return simpleDateFormat.format(finalDate);
    }

    public static String getCompleteDateForAudition(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(serverDate);
            String currentdateandTimeinUtc = simDf.format(yourDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourDate);
            calendar.get(Calendar.YEAR); //Day of the Year :)
            calendar.get(Calendar.DATE); //Day of the day :)
            //  String monthNamee = calendar.get(Calendar.MONTH);
            changeFormatDate = calendar.get(Calendar.DATE) - 1
                    + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR);
            Log.e("changedate", changeFormatDate + "");
        } catch (Exception e) {

        }
        return changeFormatDate;
    }

    public static String getCompleteDateMyAuditions(String dateUTC) {
        Log.e("serverDatetime", dateUTC);
        Date finalDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            finalDate = format.parse(dateUTC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (finalDate == null) {
            return "NA";
        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        return simpleDateFormat.format(finalDate);
    }


    public static String getFeedTime(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            //, Locale.US);
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(serverDate);
            String currentdateandTimeinUtc = simDf.format(yourDate);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            String currentDateTimeString = sdf.format(yourDate);
            changeFormatDate = currentDateTimeString;
        } catch (Exception e) {

        }
        return changeFormatDate;
    }

    public static String getServerTimeInUTC(String serverDate) {
        String changeFormatDate = "";
        SimpleDateFormat sp_start = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US);
        sp_start.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date yourDate = sp_start.parse(serverDate);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            String currentDateTimeString = sdf.format(yourDate);
            changeFormatDate = currentDateTimeString;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return changeFormatDate;
    }

    public static String getServerCompleteTimeInUTC(String serverDate) {
        String changeFormatDate = "";
        SimpleDateFormat sp_start = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sp_start.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date yourDate = sp_start.parse(serverDate);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            String currentDateTimeString = sdf.format(yourDate);
            changeFormatDate = currentDateTimeString;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return changeFormatDate;
    }


    public static String getTimeinAmOrPM(String serverDate) {
        SimpleDateFormat sp_start = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateFormat dateFormat_start = new SimpleDateFormat("hh:mm a");
        Date start_date = null;
        try {
            start_date = sp_start.parse(serverDate);
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        String outPut_start_time = dateFormat_start.format(start_date);
        return outPut_start_time;
    }

    public static String getDateMonthNameandYear(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
            String bar = serverDate.toString();
            serverDate = bar.substring(0, 10);
            SimpleDateFormat simDf = new SimpleDateFormat("dd-MM-yyyy");
            Date yourDate = simDf.parse(serverDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourDate);
            calendar.get(Calendar.YEAR); //Day of the Year :)
            calendar.get(Calendar.DATE); //Day of the day :)
            int orderMonth = calendar.get(Calendar.MONTH) + 1;
            String monthNamee = monthName[calendar.get(Calendar.MONTH)];
            changeFormatDate = calendar.get(Calendar.DATE)
                    + "-" + monthNamee
                    + "-" + calendar.get(Calendar.YEAR);
            Log.e("changedate", changeFormatDate + "");
            //2018-04-16T05:16:40.769Z

        } catch (Exception e) {

        }
        return changeFormatDate;
    }


    public static String getCurrentDateAndTimeInUTC() {
        Date date = new Date();
        SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
        simDf.format(date);
        String currentdateandTimeinUtcc = simDf.format(date);
        Log.v("CurrentdateAndTimeINUTC", currentdateandTimeinUtcc + "");
        return currentdateandTimeinUtcc;
    }


    public static String getCurrentLocalTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("Z");
        String localTime = date.format(currentLocalTime);
        return localTime;
    }

    public static boolean compareLocalAndServerUTCTimes(String currentDateAndTimeInUTC, String ServerDateAndTimeInUTC) {
        boolean futureTime = false;
        long serverEndDateAndTimeEpoc = DateUtil.dateTimeToEpoch(ServerDateAndTimeInUTC);
        long currentDateAndTimeEpoc = DateUtil.dateTimeToEpoch(currentDateAndTimeInUTC);

        if (serverEndDateAndTimeEpoc <= currentDateAndTimeEpoc) {
            futureTime = false;
        } else {
            futureTime = true;
        }

        return futureTime;
    }


    public static String dateAndTimeConvertStardard(String from_datetime) {
        String from_formattedDate = "";
        SimpleDateFormat fromdateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fromdateformate.setTimeZone(TimeZone.getDefault());

        Date fromdate = null;
        try {
            fromdate = fromdateformate.parse(from_datetime);    //yyyy-MM-dd'T'HH:mm:ss.SSSZ
            SimpleDateFormat from_formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            from_formatter.setTimeZone(TimeZone.getDefault());
            from_formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            from_formattedDate = from_formatter.format(fromdate);
            Log.v("formatedate", "from" + from_formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return from_formattedDate;
    }

    public static long selcteddateAndTimeEpoic(String dateAndTime) {
        long datetimeMils = 0;

            /*SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            //2018-08-08T06:00:30.412Z
            //2018-08-08T05:43:43.449Z
            simDf.setTimeZone(TimeZone.getDefault());
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            dateAndTime = simDf.format(dateAndTime);
            Log.v("formateInUTC", "from" + dateAndTime);

            Date yourDate = simDf.parse(dateAndTime);
            Calendar startTime = Calendar.getInstance();
            startTime.setTime(yourDate);
            datetimeMils = startTime.getTimeInMillis();*/


        Log.e("serverDate", dateAndTime);
        String changeFormatDate = "";
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            //2018-08-08T05:43:43.449Z
            //, Locale.US);
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(dateAndTime);
            Calendar startTime = Calendar.getInstance();
            startTime.setTime(yourDate);
            datetimeMils = startTime.getTimeInMillis();

            String currentdateandTimeinUtc = simDf.format(yourDate);
            Log.v("formateInUTC", "from" + currentdateandTimeinUtc);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return datetimeMils;

    }

    public static String extraTimeAddedToServerUTCtime(String callInitiateTime, int incrementedTimeCount) {
        String extraAddedEndTimeInUTC = null;
        try {
            SimpleDateFormat sp_start = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sp_start.setTimeZone(TimeZone.getTimeZone("UTC"));
            if (callInitiateTime != null) {
                Date date = sp_start.parse(callInitiateTime); //2018-11-20T08:07:31.011Z
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.MINUTE, incrementedTimeCount);

                extraAddedEndTimeInUTC = sp_start.format(calendar.getTime()); //2018-11-20T08:10:31.011Z

                Log.e("extraAddedEndTime", extraAddedEndTimeInUTC);
            }
        } catch (Exception e) {
        }

        return extraAddedEndTimeInUTC;
    }

    public static String idleTimeAddingtoServerTime(String extraAddedEndTimeInUTC) {


        String dataa = null;
        Handler mHandler = new Handler(Looper.getMainLooper());
        mStatusCheckerr = new Runnable() {
            @Override
            public void run() {
                try {
                    //
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mHandler.postDelayed(mStatusCheckerr, 1000);
                }
            }
        };
//To stop loop
        mHandler.removeCallbacks(mStatusCheckerr);
//To start loop
        mStatusCheckerr.run();
        return dataa;
    }


    public static void currentDateSet(EditText dateEt) {
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(calendar1.getTime());
        dateEt.setText(formattedDate);
    }


    public static String currentTimeSubtract(long timespan) {
        long totalSeconds = timespan / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    public static String getCurrentDataTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }


    //Based on old year
    public static void getYearList() {
        ArrayList<String> yearList = new ArrayList<>();
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        Integer totalCount = Integer.parseInt(formatYear.format(Calendar.getInstance().getTime())) - 2003; //mention here start year

        for (int i = 0; i < totalCount; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -(totalCount - i));
            yearList.add(formatYear.format(cal.getTime()));
        }
        yearList.add(formatYear.format(Calendar.getInstance().getTime()));

        Log.e("yearsList", yearList.toString());
        Collections.reverse(yearList);


//        for (int i = 0; i < yearList.size(); i++) {
//            Log.e("yearData", yearList.toString() + "_Check");
//        }
    }


    public static String getMonthandDataYear(String serverDate) {
        Log.e("serverDate", serverDate);
        String changeFormatDate = "";
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(serverDate);
            String currentdateandTimeinUtc = simDf.format(yourDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourDate);
            calendar.get(Calendar.YEAR); //Day of the Year :)
            calendar.get(Calendar.DATE); //Day of the day :)
            String monthNamee = monthName[calendar.get(Calendar.MONTH)];
            changeFormatDate = calendar.get(Calendar.DATE) + " " + monthNamee + " " + calendar.get(Calendar.YEAR);
            Log.e("changedate", changeFormatDate + "");
        } catch (Exception e) {

        }
        return changeFormatDate;
    }

    public static String getDurationTime(String startTime, String endTime) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        long diff = 0;
        try {
            diff = format.parse(endTime).getTime() - format.parse(startTime).getTime();
            long seconds_ = diff / 1000;
            long minutes_ = seconds_ / 60;
            long hours_ = minutes_ / 60;
            long days = hours_ / 24;
            long remaingMinutes = minutes_ % 60;
            String minsString = "", hoursString = "";
            if (hours_ > 0) {
                if (hours_ == 1) {
                    hoursString = hours_ + " hr ";
                } else {
                    hoursString = hours_ + " hrs ";
                }
            }
            if (remaingMinutes > 0) {
                if (remaingMinutes == 1) {
                    minsString = remaingMinutes + " min ";
                } else {
                    minsString = remaingMinutes + " mins ";
                }
            }


            if (!minsString.isEmpty() && !hoursString.isEmpty()) {
//                holder.scheduleDurationtxt.setText(hoursString + minsString);
                return hoursString + minsString;
            } else if (!hoursString.isEmpty()) {
                return hoursString;
//                holder.scheduleDurationtxt.setText(hoursString);
            } else if (!minsString.isEmpty()) {
//                holder.scheduleDurationtxt.setText(minsString);
                return minsString;
            } else {
                return "";
            }


        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


}
