package info.dkapp.flow.celebflow.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Button;


import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.interfaces.activities.IUpdateAppDialog;

/**
 * Created by User on 17-07-2018.
 **/

public class UpdateAppDialog extends Dialog
{
    Context context;
    Button btUpdate,btCancel;
    IUpdateAppDialog iUpdateAppDialog;
    boolean isForce;

    public UpdateAppDialog(@NonNull Context context, IUpdateAppDialog iUpdateAppDialog, boolean isForce) {
        super(context);
        this.context = context;
        this.iUpdateAppDialog = iUpdateAppDialog;
        this.isForce = isForce;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_app_dialog);

        initialize();
        setUp();
    }

    private void initialize()
    {
        btUpdate = findViewById(R.id.btUpdate);
        btCancel = findViewById(R.id.btCancel);
    }

    private void setUp()
    {
        btCancel.setVisibility(isForce?View.GONE:View.VISIBLE);

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iUpdateAppDialog.updateNow();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                iUpdateAppDialog.doLater();

            }
        });
    }

}
