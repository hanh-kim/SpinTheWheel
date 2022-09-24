package vn.fmobile.spinthewheel.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rubikstudio.library.WheelView;
import rubikstudio.library.model.WheelItem;
import vn.fmobile.spinthewheel.R;
import vn.fmobile.spinthewheel.database.WheelDatabase;
import vn.fmobile.spinthewheel.model.History;
import vn.fmobile.spinthewheel.model.Item;
import vn.fmobile.spinthewheel.model.Wheel;
import vn.fmobile.spinthewheel.others.CurrentDateTime;
import vn.fmobile.spinthewheel.others.Memory;

public class SpinActivity extends AppCompatActivity {

    TextView tvResult, tvWheelTitle;
    Button btnReset;
    WheelView wheelView;
    ImageView icCenter;
    List<WheelItem> wheelItemList;
    List<Item> itemList;
    Bundle bundle;
    WheelDatabase database;
    Wheel wheel;
    int wheelId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);
        initUI();
        tvWheelTitle.setText(wheel.title);
        setDataForWheel(itemList);

        btnReset.setOnClickListener(v -> {
            itemList = database.wheelItemDAO().getWheelItemFromDatabase(wheel.id);
            setDataForWheel(itemList);
            tvResult.setText(null);
            findViewById(R.id.main_layout).setBackgroundColor(Color.parseColor("#C6C5C5"));
        });

        icCenter.setOnClickListener(v -> {

            if (itemList.size() < 2) {
                Toast.makeText(SpinActivity.this, getString(R.string.toast_add_one_cell_to_continue), Toast.LENGTH_SHORT).show();
                return;
            }
            int index = getRandomIndex();
            wheelView.startWheelWithTargetIndex(index);
            wheel.amount = wheel.amount + 1;
            database.wheelDAO().updateWheel(wheel);
        });

        wheelView.setOnClickListener(v -> {
            int index = getRandomIndex();
            wheelView.startWheelWithTargetIndex(index);
        });

        wheelView.setOnRoundItemSelectedListener(index -> {
            String title = wheelItemList.get(index).secondaryText;
            int bgColor = wheelItemList.get(index).backgroundColor;
            int txtColor = wheelItemList.get(index).textColor;
            tvResult.setText("" + title + "");
            tvResult.setShadowLayer(50, 1, 1, Color.RED);
            findViewById(R.id.main_layout).setBackgroundColor(bgColor);
            History history = new History();
            history.wheelId = wheelItemList.get(index).idWheel;
            history.name = title;
            history.bgColor = bgColor;
            history.textColor = txtColor;
            history.dateTime = CurrentDateTime.getCurrentDate() + " " + CurrentDateTime.getCurrentTime();
            database.historyDAO().insertHistoryToDatabase(history);

            notificationResult(history, index);

        });

    }

    private int getRandomIndex() {
        Random random = new Random();
        return random.nextInt(wheelItemList.size() - 1);
    }

    private void initUI() {
        tvResult = findViewById(R.id.tv_result);
        tvWheelTitle = findViewById(R.id.tv_wheel_title);
        btnReset = findViewById(R.id.btn_reset);
        wheelView = findViewById(R.id.wheel_view);
        icCenter = findViewById(R.id.ic_spin_center);
        database = WheelDatabase.getInstance(SpinActivity.this.getApplicationContext());
        tvWheelTitle.setTextColor(Color.WHITE);
        tvWheelTitle.setShadowLayer(5, 1, 1, Color.BLACK);
        wheelId = Memory.wheelId;
        wheel = database.wheelDAO().getWheelFromDatabase(wheelId);
        itemList = database.wheelItemDAO().getWheelItemFromDatabase(wheel.id);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spin_option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.item_customize) {
            startActivity(new Intent(SpinActivity.this, CustomizeWheelActivity.class));

        } else if (itemId == R.id.item_history) {
            startActivity(new Intent(SpinActivity.this, HistoryActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

//    private void getData() {
//        bundle = getIntent().getExtras();
//        wheel = (Wheel) bundle.getSerializable("wheel");
//        itemList = database.wheelItemDAO().getWheelItemFromDatabase(wheel.id);
//    }

    private void notificationResult(History history, int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SpinActivity.this);
        View view = getLayoutInflater().inflate(R.layout.layout_notify_result, null);
        TextView tvResult = view.findViewById(R.id.tv_notify_result);
        Button btnHide = view.findViewById(R.id.btn_delete);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        builder.setView(view);
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();

        tvResult.setText(history.name);
        tvResult.setTextColor(history.textColor);
        tvResult.setBackgroundColor(history.bgColor);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.remove(index);
                setDataForWheel(itemList);
                alertDialog.dismiss();
            }
        });


        alertDialog.show();

    }

    public void setDataForWheel(List<Item> itemList) {
        wheelItemList = new ArrayList<>();
        for (Item item : itemList) {
            WheelItem wheelItem = new WheelItem();

            wheelItem.id = item.id;
            wheelItem.idWheel = item.wheelId;
            wheelItem.icon = item.icon;
            wheelItem.secondaryText = item.title;
            wheelItem.backgroundColor = item.backgroundColor;
            wheelItem.textColor = item.textColor;
            wheelItemList.add(wheelItem);

        }
        wheelView.setData(wheelItemList);
        wheelView.setRound(wheel.round);

        /*wheelView.setLuckyWheelBackgrouldColor(0xff0000ff);
        wheelView.setLuckyWheelTextColor(0xffcc0000);
        wheelView.setLuckyWheelCenterImage(getResources().getDrawable(R.drawable.icon));
        wheelView.setLuckyWheelCursorImage(R.drawable.ic_cursor);*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SpinActivity.this.finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //   Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
        itemList = database.wheelItemDAO().getWheelItemFromDatabase(wheel.id);
        setDataForWheel(itemList);

    }
}