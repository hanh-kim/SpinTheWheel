package vn.fmobile.spinthewheel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rubikstudio.library.WheelView;
import rubikstudio.library.model.WheelItem;
import vn.fmobile.spinthewheel.R;
import vn.fmobile.spinthewheel.model.Wheel;
import vn.fmobile.spinthewheel.others.OnItemClickListener;

public class WheelApdapter extends RecyclerView.Adapter<WheelApdapter.WheelViewHolder>{
    List<Wheel> wheelList;
    List<WheelItem> wheelItemList;
    OnItemClickListener onItemClickListener;



    public void setData(List<Wheel> wheels, List<WheelItem> wheelItems){
        this.wheelList = wheels;
        this.wheelItemList = wheelItems;
        notifyDataSetChanged();
    }

    public  void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnSettingAndDeleteListener{
        void onSettingListener(Wheel wheel,int position);
        void onDeleteListener(Wheel wheel,int position);
    }
//    public interface OnItemClickListener {
//        void onItemClick(View view, int position);
//    }




    @NonNull
    @Override
    public WheelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wheel_row,parent,false);
        return new WheelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  WheelApdapter.WheelViewHolder holder, int position) {
        holder.tvTitle.setText(wheelList.get(position).title);
        holder.tvAmount.setText("Spins: "+wheelList.get(position).amount);

        holder.wheelView.setData(wheelItemList);
        holder.wheelView.setRound(0);

        holder.setOnItemClickListener(onItemClickListener);

        holder.icSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Setting", Toast.LENGTH_SHORT).show();
            }
        });

        holder.icDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Delete", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        if (wheelList.size()>0){
            return  wheelList.size();
        }
        return 0;
    }

    public  class WheelViewHolder extends RecyclerView.ViewHolder {
        WheelView wheelView;
        TextView tvTitle, tvAmount;
        ImageView icSetting, icDelete;

        OnItemClickListener onItemClickListener;


        public void setOnItemClickListener(OnItemClickListener onItemClickListener){
            this.onItemClickListener = onItemClickListener;

        }

        public WheelViewHolder(@NonNull  View itemView) {
            super(itemView);
            wheelView = itemView.findViewById(R.id.wheel_view_avatar);
            tvTitle = itemView.findViewById(R.id.tv_wheel_title);
            tvAmount = itemView.findViewById(R.id.tv_spin_amount);
            icDelete = itemView.findViewById(R.id.ic_delete);
            icSetting = itemView.findViewById(R.id.ic_setting);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(itemView,getAdapterPosition());
                }
            });


        }

    }
}
