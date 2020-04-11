package info.sumantv.flow.stories;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import info.sumantv.flow.R;
import info.sumantv.flow.stories.fragments.StoriesFragment;

public class DeleteStoryDailog extends BottomSheetDialogFragment {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.llDelete)
    LinearLayout llDelete;
    @BindView(R.id.ivProfileImage)
    CircleImageView ivProfileImage;
    String storyId;

    public DeleteStoryDailog() {
    }

    public void setData(String storyId) {
        this.storyId = storyId;
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_notification_delete_bottom, null);
        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ButterKnife.bind(this, contentView);


        llDelete.setOnClickListener(view -> {
            dismiss();
            if (StoriesFragment.getInstance() != null) {
                StoriesFragment.getInstance().storyDelete(storyId);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        getDialog().setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                dimiss_dailog();
                return true;
            } else return false;
        });

        getDialog().setOnCancelListener(dialog -> dimiss_dailog());
    }

    private void dimiss_dailog() {
        if (StoriesFragment.getInstance() != null) {
            StoriesFragment.getInstance().resumeVideo();
        }
        dismiss();
    }


}
