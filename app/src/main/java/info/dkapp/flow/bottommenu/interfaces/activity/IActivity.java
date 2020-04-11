package info.dkapp.flow.bottommenu.interfaces.activity;

import android.app.Activity;

/**
 * Created by Chenna on 16-08-2018.
 */

public interface IActivity {


    public void changeTitle(String title);

    public void showSnackBar(String snackBarText, int type);

    public Activity activity();

}
