package info.sumantv.flow.ipoll.viewholders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;

import info.sumantv.flow.R;

/**
 * Created by User on 31-07-2018.
 **/

public class OptionsViewHolder extends RecyclerView.ViewHolder
{
    @BindView(R.id.imgOption)
    public ImageView imgOption;

    @BindView(R.id.tvOption)
    public TextView tvOption;

    public OptionsViewHolder(View itemView)
    {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
