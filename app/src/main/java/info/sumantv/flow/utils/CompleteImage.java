package info.sumantv.flow.utils;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.facebook.drawee.drawable.ProgressBarDrawable;


import info.sumantv.flow.R;
import info.sumantv.flow.commonuses.zoomable.ZoomableDraweeView;
import info.sumantv.flow.userflow.Util.Common;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteImage extends Fragment {
    @BindView(R.id.ZoomableDraweeViewImg)
    ZoomableDraweeView simpleDraweeView;

    @BindView(R.id.pbImageLoading)
    ProgressBar pbImageLoading;

    @BindView(R.id.imgClose)
    ImageView imgClose;


    String imageUrl = "";

    public CompleteImage() {
        // Required empty public constructor
    }

    public static CompleteImage newInstance(String imageUrl) {
        CompleteImage fragment = new CompleteImage();
        Bundle args = new Bundle();
        args.putString("imageUrl", imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.completeimage_frag, container, false);
        ButterKnife.bind(this, itemView);
        imageLoad();

        imgClose.setOnClickListener(view -> getActivity().finish());

        return itemView;
    }

    private void imageLoad() {
        if (getArguments() != null) {
            imageUrl = getArguments().getString("imageUrl");
        }
        pbImageLoading.getProgressDrawable().setColorFilter(getResources().getColor(R.color.sky_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        simpleDraweeView.getHierarchy().setProgressBarImage(new ProgressBarDrawable() {
            @Override
            protected boolean onLevelChange(int level) {
                //level 10,000 means fully downloaded
                pbImageLoading.setProgress(level / 100);
                if (level / 100 == 100) {
                    pbImageLoading.setVisibility(View.GONE);
                }
                return true;
            }
        });
        Common.getInstance().setSingleImage(simpleDraweeView, imageUrl);
    }
}
