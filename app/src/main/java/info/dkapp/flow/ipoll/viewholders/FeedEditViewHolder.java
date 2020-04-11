package info.dkapp.flow.ipoll.viewholders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

import info.dkapp.flow.R;

/**
 * Created by User on 20-08-2018.
 **/

public class FeedEditViewHolder extends RecyclerView.ViewHolder
{
    @BindView(R.id.imgFeed)
    public SimpleDraweeView imgFeed;

    @BindView(R.id.imgMediaType)
    public ImageView imgMediaType;

    // feed caption
    @BindView(R.id.etFeedCaption)
    public EditText etFeedCaption;

    @BindView(R.id.imgClose)
    public ImageView imgClose;

    @BindView(R.id.imgCrop)
    public ImageView imgCrop;


    public FeedEditViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
