package vn.fmobile.spinthewheel.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

import rubikstudio.library.WheelView;
import rubikstudio.library.model.WheelItem;
import vn.fmobile.spinthewheel.R;
import vn.fmobile.spinthewheel.WheelApdapter;
import vn.fmobile.spinthewheel.model.Wheel;

public class CustomizeWheelActivity extends AppCompatActivity {

   Slider sliderRound;
   TextView tvCountSpinTimes;

    List<WheelItem> wheelItemList;
    WheelView wheelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_wheel);
        initUIs();

        for (int i=0; i<30; i++){
            WheelItem item = new WheelItem();
            if(i%2==0){
                //item.title = "hanh"+(i+1);
                item.secondaryText="hanhkkkk"+(i+1);
                item.backgroundColor = Color.RED;
                item.textColor = Color.BLACK;
            }else {
                // item.title = "canh"+(i+1);
                item.secondaryText="c"+(i+1);
                item.backgroundColor = Color.GREEN;
                item.textColor = Color.BLACK;
            }

            wheelItemList.add(item);

        }

        wheelView.setData(wheelItemList);

        sliderRound.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull  Slider slider, float value, boolean fromUser) {
                tvCountSpinTimes.setText((int)value+"x");
            }
        });

    }

    private void initUIs(){
       wheelView = findViewById(R.id.wv_preview);
       sliderRound = findViewById(R.id.slider_spin_times);
       tvCountSpinTimes = findViewById(R.id.tv_set_round);

        wheelItemList = new ArrayList<>();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.item_done){
            startActivity(new Intent(CustomizeWheelActivity.this, SpinActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}