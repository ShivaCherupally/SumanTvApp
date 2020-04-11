package info.dkapp.flow.bottommenu.viewholders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import info.dkapp.flow.R;


/**
 * Created by Chenna Rao on 24-08-2018.
 **/

public class OptionsViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgOption;
    public TextView tvOption;

    public OptionsViewHolder(View itemView) {
        super(itemView);
        imgOption = itemView.findViewById(R.id.imgOption);
        tvOption = itemView.findViewById(R.id.tvOption);
    }
}
