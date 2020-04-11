package info.dkapp.flow.userflow.Util;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;


import info.dkapp.flow.R;
import info.dkapp.flow.splashandintroscreens.TutorialScreens.TutorialData;

/**
 * Created by Shiva on 2/27/2018.
 */

public class CustomListViewAdapter extends BaseAdapter {

    Context context;
    List<TutorialData> rowItems;

    public CustomListViewAdapter(Context context, List<TutorialData> items) {
        this.context = context;
        this.rowItems = items;
    }

    private class ViewHolder {
        ImageView imageView;
    }

    public int getCount() {
        return rowItems.size();
    }

    public Object getItem(int position) {
        return rowItems.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.intro_slide1, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.sliderlayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TutorialData rowItem = (TutorialData) getItem(position);
        holder.imageView.setImageBitmap(rowItem.getBitmapImage());

        return convertView;
    }
}
