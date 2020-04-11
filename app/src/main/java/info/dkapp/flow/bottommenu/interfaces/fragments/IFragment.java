package info.dkapp.flow.bottommenu.interfaces.fragments;

import android.app.Activity;

/**
 * Created by Chenna on 10-08-2018.
 */

public interface IFragment
{
    public void changeTitle(String title);
    public void showSnackBar(String snackBarText, int type);
    public Activity activity();
}
