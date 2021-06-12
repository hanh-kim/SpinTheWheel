package vn.fmobile.spinthewheel.screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import rubikstudio.library.WheelView;
import rubikstudio.library.model.WheelItem;
import vn.fmobile.spinthewheel.R;
import vn.fmobile.spinthewheel.adapter.WheelApdapter;
import vn.fmobile.spinthewheel.database.WheelDatabase;
import vn.fmobile.spinthewheel.model.Wheel;
import vn.fmobile.spinthewheel.others.Memory;
import vn.fmobile.spinthewheel.others.OnItemClickListener;

public class HomeActivity extends AppCompatActivity {

    WheelDatabase database;
    RecyclerView recyclerView;
    FloatingActionButton btnAdd;

    List<Wheel> wheelList;
    List<WheelItem> wheelItemList;
    WheelView wheelView;
    WheelApdapter adapter;

    TextView tvNotifyEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        checkListIsEmpty();


        adapter.setData(database,wheelList);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Memory.wheelId = wheelList.get(position).id;
                Intent intent = new Intent(HomeActivity.this, SpinActivity.class);

//                Bundle bundle = new Bundle();
//                bundle.putSerializable("wheel", wheelList.get(position));
//                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        adapter.onSettingAndDeleteListener(new WheelApdapter.OnSettingAndDeleteListener() {
            @Override
            public void onSettingListener(Wheel wheel, int position) {
                Memory.wheelId = wheelList.get(position).id;
                Intent intent = new Intent(HomeActivity.this, CustomizeWheelActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("wheel", wheelList.get(position));
//                intent.putExtras(bundle);
                startActivity(intent);

            }

            @Override
            public void onDeleteListener(Wheel wheel, int position) {
                askToDeleteItem(wheel,position);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wheel wheel = new Wheel();
                wheel.title = "";
                wheel.round = 5;
                wheel.amount = 0;
                wheel.isActive = 0;
                database.wheelDAO().addWheelIntoDatabase(wheel);
                startActivity(new Intent(HomeActivity.this, AddWheelActivity.class));
            }
        });


    }

    public void initUI() {
        recyclerView = findViewById(R.id.rcv_wheel);
        btnAdd = findViewById(R.id.fab_add);
        tvNotifyEmpty = findViewById(R.id.tv_notify_empty);
        tvNotifyEmpty.setVisibility(View.GONE);
        wheelItemList = new ArrayList<>();

        adapter = new WheelApdapter();
        database = WheelDatabase.getInstance(HomeActivity.this.getApplicationContext());
        database.wheelDAO().deleteWheelIsActive(0);
        wheelList = database.wheelDAO().getAllWheelFromDatabase();

    }

    private void checkListIsEmpty(){
     //   wheelList = database.wheelDAO().getAllWheelFromDatabase();
        if (wheelList.size()==0){
            tvNotifyEmpty.setVisibility(View.VISIBLE);
        }else  tvNotifyEmpty.setVisibility(View.GONE);

    }

    private void askToDeleteItem(Wheel wheel,int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Xóa vòng quay");
        builder.setMessage("Bạn có muốn tiếp tục xóa vòng quay này không?");


        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                database.wheelItemDAO().deleteAllItemInDatabase(wheel.id);
                database.wheelDAO().deleteWheelInDatabase(wheel);

                wheelList.remove(position);
                adapter.notifyDataSetChanged();
                checkListIsEmpty();

                Toast.makeText(HomeActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            }
        });


        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        wheelList = database.wheelDAO().getAllWheelFromDatabase();
        adapter.notifyDataSetChanged();

    }
}