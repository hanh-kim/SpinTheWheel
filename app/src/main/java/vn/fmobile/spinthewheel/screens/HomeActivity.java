package vn.fmobile.spinthewheel.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

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
    WheelApdapter wheelApdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();


        wheelApdapter.setData(database);
        wheelApdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Memory.wheelId = wheelList.get(position).id;
                Intent intent = new Intent(HomeActivity.this, SpinActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("wheel", wheelList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);

                startActivity(intent);
            }
        });
        wheelApdapter.onSettingAndDeleteListener(new WheelApdapter.OnSettingAndDeleteListener() {
            @Override
            public void onSettingListener(Wheel wheel, int position) {
                Memory.wheelId = wheelList.get(position).id;
                Intent intent = new Intent(HomeActivity.this, CustomizeWheelActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("wheel", wheelList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);

                startActivity(intent);
            }

            @Override
            public void onDeleteListener(Wheel wheel, int position) {
                database.wheelDAO().deleteWheelInDatabase(wheel);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(wheelApdapter);

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
        wheelItemList = new ArrayList<>();

        wheelApdapter = new WheelApdapter();
        database = WheelDatabase.getInstance(HomeActivity.this.getApplicationContext());
        database.wheelDAO().deleteWheelIsActive(0);
        wheelList = database.wheelDAO().getAllWheelFromDatabase();

    }
}