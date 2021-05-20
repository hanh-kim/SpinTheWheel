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
import vn.fmobile.spinthewheel.WheelApdapter;
import vn.fmobile.spinthewheel.database.WheelDatabase;
import vn.fmobile.spinthewheel.model.Wheel;

public class HomeActivity extends AppCompatActivity {

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
        WheelDatabase.getInstance(this);
        WheelDatabase.getInstance(this);
        for (int i = 0; i < 5; i++) {
            Wheel wheel = new Wheel();
            wheel.title = "Lucky wheel " + (i + 1);
            wheel.amount = 5 + i;
            wheelList.add(wheel);
        }


        for (int i = 0; i < 10; i++) {
            WheelItem item = new WheelItem();
            if (i % 2 == 0) {
               // item.title = "hanh" + (i + 1);
                item.secondaryText="hanh"+(i+1);
                item.backgroundColor = Color.RED;
                item.textColor = Color.YELLOW;
            } else {
                //item.title = "canh" + (i + 1);
                item.secondaryText="canh"+(i+1);
                item.backgroundColor = Color.GREEN;
                item.textColor = Color.YELLOW;
            }
            wheelItemList.add(item);

        }

        wheelApdapter.setData(wheelList, wheelItemList);
        wheelApdapter.setOnItemClickListener(new WheelApdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(HomeActivity.this, SpinActivity.class));
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(wheelApdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SpinActivity.class));
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public void initUI() {
        recyclerView = findViewById(R.id.rcv_wheel);
        btnAdd = findViewById(R.id.fab_add);
        wheelItemList = new ArrayList<>();
        wheelList = new ArrayList<>();
        wheelApdapter = new WheelApdapter();
    }
}