package vn.fmobile.spinthewheel.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.fmobile.spinthewheel.database.WheelDatabase;
import vn.fmobile.spinthewheel.model.Wheel;
import vn.fmobile.spinthewheel.R;
import vn.fmobile.spinthewheel.adapter.HistoryAdapter;
import vn.fmobile.spinthewheel.model.History;
import vn.fmobile.spinthewheel.others.Memory;
import vn.fmobile.spinthewheel.others.OnItemClickListener;

public class HistoryActivity extends AppCompatActivity {

    CardView cvHisName;
    RecyclerView rcvHistory;
    HistoryAdapter adapter;
    List<History> historyList;
    LinearLayoutManager linearLayoutManager;
    int wheelId = 0;
    WheelDatabase database;
    Wheel wheel;
    TextView tvNotifyEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initUI();

        //Toast.makeText(this, "wheel id home:"+ Memory.wheelId , Toast.LENGTH_SHORT).show();
        historyList = database.historyDAO().getHistoryFromDatabase(wheelId);
        checkListIsEmpty();
        adapter.setData(historyList, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                askToDeleteItem(historyList.get(position));
            }
        });
        rcvHistory.setLayoutManager(linearLayoutManager);
        rcvHistory.setAdapter(adapter);


    }

    private void initUI() {
        linearLayoutManager = new LinearLayoutManager(this);
        rcvHistory = findViewById(R.id.rcv_history);
        adapter = new HistoryAdapter();
        historyList = new ArrayList<>();
        database = WheelDatabase.getInstance(HistoryActivity.this);
        wheelId = Memory.wheelId;
        tvNotifyEmpty = findViewById(R.id.tv_notify_empty);
        tvNotifyEmpty.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.item_delete_history) {
            askToDeleteAll();

        }
        return super.onOptionsItemSelected(item);
    }

    private void askToDeleteAll() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
        builder.setCancelable(true);
        builder.setTitle("X??a l???ch s???");
        builder.setMessage("B???n c?? mu???n ti???p t???c x??a to??n b??? l???ch s??? quay kh??ng?");


        builder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("X??a", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.historyDAO().deleteAll(wheelId);
                historyList.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(HistoryActivity.this, "X??a th??nh c??ng!", Toast.LENGTH_SHORT).show();
                checkListIsEmpty();
            }
        });


        builder.show();
    }

    private void askToDeleteItem(History history) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
        builder.setCancelable(true);
        builder.setTitle("X??a l???ch s???");
        builder.setMessage("B???n c?? mu???n ti???p t???c x??a m???c n??y kh???i l???ch s??? quay kh??ng?");


        builder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("X??a", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.historyDAO().deleteHistoryItem(history);
                historyList.remove(history);
                adapter.notifyDataSetChanged();
                Toast.makeText(HistoryActivity.this, "X??a th??nh c??ng!", Toast.LENGTH_SHORT).show();
                checkListIsEmpty();
            }
        });


        builder.show();
    }

    private void checkListIsEmpty() {
        if (historyList.size() == 0) {
            tvNotifyEmpty.setVisibility(View.VISIBLE);
        } else tvNotifyEmpty.setVisibility(View.GONE);

    }
}