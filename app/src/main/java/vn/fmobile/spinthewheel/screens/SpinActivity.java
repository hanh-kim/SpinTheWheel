package vn.fmobile.spinthewheel.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rubikstudio.library.WheelView;
import rubikstudio.library.model.WheelItem;
import vn.fmobile.spinthewheel.R;
import vn.fmobile.spinthewheel.model.LuckyItem;

public class SpinActivity extends AppCompatActivity {

    TextView tvResult, tvWheelTitle;
    Button btnSpin;
    WheelView wheelView;
    ImageView iccenter;


    List<WheelItem> wheelItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);
        initUI();
        tvWheelTitle.setText("Còn cái nịt!");


        for (int i=0; i<20; i++){
            WheelItem item = new WheelItem();
            if(i%2==0){
                //item.title = "hanh"+(i+1);
                item.secondaryText="hanhkkkk"+(i+1);
                item.backgroundColor = Color.RED;
                item.textColor = Color.YELLOW;
            }else {
               // item.title = "canh"+(i+1);
                item.secondaryText="c"+(i+1);
                item.backgroundColor = Color.GREEN;
                item.textColor = Color.WHITE;
            }

            wheelItemList.add(item);

        }



        wheelView.setData(wheelItemList);
        wheelView.setRound(3);

        /*wheelView.setLuckyWheelBackgrouldColor(0xff0000ff);
        wheelView.setLuckyWheelTextColor(0xffcc0000);
        wheelView.setLuckyWheelCenterImage(getResources().getDrawable(R.drawable.icon));
        wheelView.setLuckyWheelCursorImage(R.drawable.ic_cursor);*/

        btnSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = getRandomIndex();
                wheelView.startWheelWithTargetIndex(index);
            }
        });

        iccenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = getRandomIndex();

                wheelView.startWheelWithTargetIndex(index);
            }
        });

        wheelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = getRandomIndex();

                wheelView.startWheelWithTargetIndex(index);
            }
        });

        wheelView.setOnRoundItemSelectedListener(new WheelView.RoundItemSelectedListener() {
            @Override
            public void onRoundItemSelected(int index) {
                String title = wheelItemList.get(index).secondaryText;
                int bgColor = wheelItemList.get(index).backgroundColor;
                tvResult.setText(title);
                findViewById(R.id.main_layout).setBackgroundColor(bgColor);
            }
        });

    }

    private int getRandomIndex() {
        Random random = new Random();
        return random.nextInt(wheelItemList.size()-1);
    }

    private void initUI() {
        tvResult = findViewById(R.id.tv_result);
        tvWheelTitle = findViewById(R.id.tv_wheel_title);
        btnSpin = findViewById(R.id.btn_spin);
        wheelView = findViewById(R.id.wheel_view);
        wheelItemList = new ArrayList<>();
        iccenter = findViewById(R.id.ic_spin_center);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spin_option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.item_customize){
            startActivity(new Intent(SpinActivity.this, CustomizeWheelActivity.class));

        }else if (itemId == R.id.item_history){
            startActivity(new Intent(SpinActivity.this, HistoryActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}