package info.dkapp.flow.ipoll.adapters.feeds;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.dkapp.flow.appmanagers.feed.models.Media;
import info.dkapp.flow.bottommenu.customviews.images.CollageView;
import info.dkapp.flow.databaseutil.appstart.AppController;

import info.dkapp.flow.R;
import info.dkapp.flow.utils.Utility;

import java.util.List;

public class FeedViewPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    List<Media> mediaList;
    int itemPosition;
    FeedsAdapter feedsAdapter;

    public FeedViewPagerAdapter(Context context,List<Media> mediaList,int itemPosition,FeedsAdapter feedsAdapter) {
        this.context = context;
        this.mediaList = mediaList;
        this.itemPosition = itemPosition;
        this.feedsAdapter = feedsAdapter;
    }
    @Override
    public int getCount() {
        return mediaList.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container,final int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_feed_view_pager, container,false);
        CollageView collageView = itemView.findViewById(R.id.collageView);
        collageView.addMedia(mediaList, mediaList.get(position), Utility.getScreenWidth(AppController.getInstance().getCurrentRegisteredActivity()), true,true);
        collageView.setCollageItemClickListener(mediaPosition -> {
            feedsAdapter.collageViewClick(mediaList,itemPosition,position);
        });
        container.addView(itemView);
        itemView.setTag("View"+position);
        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
