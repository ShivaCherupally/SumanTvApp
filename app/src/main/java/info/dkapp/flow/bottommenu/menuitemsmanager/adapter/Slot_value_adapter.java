package info.dkapp.flow.bottommenu.menuitemsmanager.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import info.dkapp.flow.bottommenu.menuitemsmanager.ISlot_value;
import info.dkapp.flow.bottommenu.menuitemsmanager.Slot_value_model;

import info.dkapp.flow.R;

import java.util.ArrayList;

/**
 * Created by Uday Kumay Vurukonda on 16-Apr-19.
 * <p>
 * Mr.Psycho
 */
public class Slot_value_adapter extends RecyclerView.Adapter<Slot_value_adapter.MyViewHolder> {

    private ArrayList<Slot_value_model> values_list;
    ISlot_value iSlot_value;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_value;


        public MyViewHolder(View view) {
            super(view);
            tv_value = (TextView) view.findViewById(R.id.tv_value);

        }
    }


    public Slot_value_adapter(Context context,ArrayList<Slot_value_model> values_list, ISlot_value iSlot_value) {
        this.values_list = values_list;
        this.iSlot_value = iSlot_value;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_slot, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_value.setText(""+values_list.get(position).value);
        if (values_list.get(position).isSelect){
            holder.tv_value.setBackground(context.getResources().getDrawable(R.drawable.submit_rectangle_for_settings));
            holder.tv_value.setTextColor(context.getResources().getColor(R.color.white));
        }else {
            holder.tv_value.setBackground(context.getResources().getDrawable(R.drawable.submit_rectangle_for_settings_empty));
            holder.tv_value.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.tv_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iSlot_value.onClick(values_list.get(position).value, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values_list.size();
    }
}
