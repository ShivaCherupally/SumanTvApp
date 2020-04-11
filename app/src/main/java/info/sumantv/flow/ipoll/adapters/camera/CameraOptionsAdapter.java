package info.sumantv.flow.ipoll.adapters.camera;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;



import java.util.List;

import info.sumantv.flow.bottommenu.models.IPollOption;

import info.sumantv.flow.R;
import info.sumantv.flow.ipoll.interfaces.other.IOptionsAdapter;
import info.sumantv.flow.ipoll.viewholders.OptionsViewHolder;
import info.sumantv.flow.utils.Utility;

/**
 * Created by User on 31-07-2018.
 **/

public class CameraOptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    List<IPollOption> list;
    Context context;
    IOptionsAdapter iCameraOptionsAdapter;
    int width;


    public CameraOptionsAdapter(Context context, List<IPollOption> list, IOptionsAdapter iCameraOptionsAdapter)
    {
        this.list = list;
        this.context = context;
        this.iCameraOptionsAdapter = iCameraOptionsAdapter;
        width = Utility.getScreenWidth(context) - Utility.dpSize(context,10);

        if(list.size()>0)
        width = list.size()<5 ? width/list.size() : Utility.dpSize(context,100);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new OptionsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position)
    {
        if(holder instanceof OptionsViewHolder)
        {
            OptionsViewHolder optionsViewHolder = (OptionsViewHolder) holder;
            optionsViewHolder.tvOption.setText(list.get(position).name);
            optionsViewHolder.imgOption.setImageResource(list.get(position).image);
            optionsViewHolder.itemView.setOnClickListener(v -> {iCameraOptionsAdapter.optionClick(list.get(position),position);});
            optionsViewHolder.itemView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
