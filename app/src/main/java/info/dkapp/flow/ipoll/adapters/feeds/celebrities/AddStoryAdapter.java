package info.dkapp.flow.ipoll.adapters.feeds.celebrities;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import info.dkapp.flow.R;

/**
 * Created by User on 28-08-2018.
 **/

public class AddStoryAdapter extends RecyclerView.ViewHolder {

    @BindView(R.id.addImage)
    public CircleImageView addImage;

    @BindView(R.id.rLCreateStore)
    public RelativeLayout rLCreateStore;


    public AddStoryAdapter(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
