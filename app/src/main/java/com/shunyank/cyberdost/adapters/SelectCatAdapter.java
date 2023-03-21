package com.shunyank.cyberdost.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shunyank.cyberdost.R;
import com.shunyank.cyberdost.models.ScamCatModel;

import java.util.ArrayList;

public class SelectCatAdapter extends RecyclerView.Adapter<SelectCatAdapter.Viewholder> {
    ArrayList<ScamCatModel> data;
    Context context;

    public interface SelectCatCallback{
        void onDataChange();
    }
    public SelectCatAdapter(Context context, ArrayList<ScamCatModel> data){
        this.data = data;
        this.context = context;
    }
    SelectCatCallback selectCatCallback;

    public void setSelectCatCallback(SelectCatCallback selectCatCallback) {
        this.selectCatCallback = selectCatCallback;
    }

    @NonNull
    @Override
    public SelectCatAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scam_cat_item_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectCatAdapter.Viewholder holder, int position) {
            int pos = position;
            holder.textView.setText(data.get(position).getName());
            if(data.get(position).isSelected()){
                holder.mainLayout.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.main_color)));
            }else {
                holder.mainLayout.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.grey)));
            }
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(data.get(pos).isSelected()){
                        data.get(pos).setSelected(false);
                        notifyItemChanged(pos);
                    }else {
                        data.get(pos).setSelected(true);
                        notifyItemChanged(pos);
                    }
                    selectCatCallback.onDataChange();
                }
            });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView textView;
        ConstraintLayout mainLayout;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.scam_cat_name_textview);
            mainLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
