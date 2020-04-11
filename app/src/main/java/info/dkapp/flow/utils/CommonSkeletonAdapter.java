package info.dkapp.flow.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import info.dkapp.flow.R;
import info.dkapp.flow.bottommenu.constants.RController;
import info.dkapp.flow.bottommenu.viewholders.SkeletonViewHolder;

/**
 * Created by Chenna on 14-05-2018.
 */

public class CommonSkeletonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public RController rController;
    boolean otherpage;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        if (otherpage) {
            layout = R.layout.skeleton_photo_media;
        } else {
            layout = R.layout.skeleton_my_celebitem;
        }

        return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderGlobal, int position) {

    }

    public CommonSkeletonAdapter(RController rController) {
        this.rController = rController;
    }

    public CommonSkeletonAdapter(RController rController, boolean other) {
        this.rController = rController;
        this.otherpage = other;
    }


    @Override
    public int getItemCount() {
        return 10;
    }


}

