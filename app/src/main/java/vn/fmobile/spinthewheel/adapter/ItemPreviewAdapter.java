package vn.fmobile.spinthewheel.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.fmobile.spinthewheel.R;
import vn.fmobile.spinthewheel.model.Item;
import vn.fmobile.spinthewheel.others.OnItemClickListener;


public class ItemPreviewAdapter extends RecyclerView.Adapter<ItemPreviewAdapter.ItemViewHolder> {

    List<Item> itemList;
    OnItemClickListener onItemClickListener;

    public void setData( List<Item> itemList, OnItemClickListener onItemClickListener){
        this.itemList = itemList;
        this.onItemClickListener = onItemClickListener;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_wheel_items_preview, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPreviewAdapter.ItemViewHolder holder, int position) {
        // set title
        holder.tvItem.setText(itemList.get(position).title);

        // set background color
        holder.tvItem.setBackgroundColor(itemList.get(position).backgroundColor);

        // set title color
        holder.tvItem.setTextColor(itemList.get(position).textColor);


        holder.tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (itemList.size() > 0){
            return itemList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_pre_item);

        }
    }
}
