package info.sumantv.flow.ipoll.dialogs.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;

import info.sumantv.flow.R;
import info.sumantv.flow.ipoll.interfaces.dialogs.custom.ICustomAlertDialog;

/**
 * Created by User on 12-08-2018.
 **/

public class CustomAlertDialog extends Dialog {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvDetails)
    TextView tvDetails;

    @BindView(R.id.tvYes)
    TextView tvYes;

    @BindView(R.id.tvNo)
    TextView tvNo;

    String title,details;
    Context context;
    ICustomAlertDialog iCustomAlertDialog;
    public CustomAlertDialog(@NonNull Context context, ICustomAlertDialog iCustomAlertDialog, String title, String details) {
        super(context);
        this.context = context;
        this.iCustomAlertDialog = iCustomAlertDialog;
        this.title = title;
        this.details = details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alert);
        ButterKnife.bind(this);

        tvTitle.setText(title);
        tvDetails.setText(details);

        tvNo.setOnClickListener(v -> {
            this.dismiss();
            iCustomAlertDialog.doNegativeAction();
        });

        tvYes.setOnClickListener(v -> {
            this.dismiss();
            iCustomAlertDialog.doPositiveAction();
        });
    }
}
