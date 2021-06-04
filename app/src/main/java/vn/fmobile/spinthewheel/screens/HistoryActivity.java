package vn.fmobile.spinthewheel.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.fmobile.spinthewheel.others.CurrentDateTime;
import vn.fmobile.spinthewheel.R;
import vn.fmobile.spinthewheel.adapter.HistoryAdapter;
import vn.fmobile.spinthewheel.model.History;

public class HistoryActivity extends AppCompatActivity {

    CardView cvHisName;
    RecyclerView rcvHistory;
    HistoryAdapter adapter;
    List<History> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initUI();
        historyList.add(new History(1,"Hanh",Color.GREEN,Color.BLACK,
                CurrentDateTime.getCurrentDate()+" "+CurrentDateTime.getCurrentTime()));
        historyList.add(new History(2,"Canh",Color.RED,Color.BLACK,
                CurrentDateTime.getCurrentDate()+" "+CurrentDateTime.getCurrentTime()));
        historyList.add(new History(3,"Nhat",Color.YELLOW,Color.BLACK,
                CurrentDateTime.getCurrentDate()+" "+CurrentDateTime.getCurrentTime()));
        historyList.add(new History(4,"Dang",Color.GREEN,Color.BLACK,
                CurrentDateTime.getCurrentDate()+" "+CurrentDateTime.getCurrentTime()));
        adapter.setData(historyList);
        LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this);
        rcvHistory.setLayoutManager(linearLayoutManager);

        rcvHistory.setAdapter(adapter);

    }

    private void initUI() {
        rcvHistory = findViewById(R.id.rcv_history);
//        cvHisName = findViewById(R.id.cv_his_name);
//        cvHisName.setBackgroundColor(Color.YELLOW);
        adapter = new HistoryAdapter();
        historyList = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.item_delete_history){
           askToDelete();

        }
        return super.onOptionsItemSelected(item);
    }

    private void askToDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Xóa lịch sử");
        builder.setMessage("Bạn có muốn tiếp tục xóa lịch quay không?");


        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                historyList.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(HistoryActivity.this, "Delete button was clicked!", Toast.LENGTH_SHORT).show();
            }
        });


        builder.show();
    }
}