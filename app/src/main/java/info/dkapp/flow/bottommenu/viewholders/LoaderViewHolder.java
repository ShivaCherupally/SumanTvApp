package info.dkapp.flow.bottommenu.viewholders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import info.dkapp.flow.R;
import pl.droidsonroids.gif.GifImageView;

public class LoaderViewHolder extends RecyclerView.ViewHolder
{

    public LinearLayout llParent;

    public LinearLayout llInside;

    public ProgressBar progressBar;

    public TextView tvTitle;

    public GifImageView gifimage;

    public TextView tvContent;

    public LoaderViewHolder(View itemView) {
        super(itemView);
        llParent = itemView.findViewById(R.id.llParent);
        llInside = itemView.findViewById(R.id.llInside);
        progressBar = itemView.findViewById(R.id.progressBar);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvContent = itemView.findViewById(R.id.tvContent);
        gifimage = itemView.findViewById(R.id.gifimage);

    }
}
