package vn.fmobile.spinthewheel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.fmobile.spinthewheel.R;
import vn.fmobile.spinthewheel.model.History;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    List<History> historyList;

    public void setData(List<History> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        holder.tvIndex.setText(String.valueOf(historyList.get(position).id));
        holder.tvName.setText(historyList.get(position).name);
        holder.tvName.setTextColor(historyList.get(position).textColor);
        holder.tvName.setBackgroundColor(historyList.get(position).bgColor);
        holder.tvDateTime.setText(historyList.get(position).dateTime);
    }

    @Override
    public int getItemCount() {
        if (historyList.size() > 0) {
            return historyList.size();
        }
        return 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex, tvName, tvDateTime;
        CardView cvName;


        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIndex = itemView.findViewById(R.id.tv_his_index);
            tvName = itemView.findViewById(R.id.tv_his_name);
            tvDateTime = itemView.findViewById(R.id.tv_his_data_time);
            cvName = itemView.findViewById(R.id.cv_his_name);
        }
    }

}
