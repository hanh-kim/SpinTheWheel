package vn.fmobile.spinthewheel.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

import petrov.kristiyan.colorpicker.ColorPicker;
import rubikstudio.library.WheelView;
import rubikstudio.library.model.WheelItem;
import vn.fmobile.spinthewheel.OnChangeColor;
import vn.fmobile.spinthewheel.R;

public class CustomizeWheelActivity extends AppCompatActivity {

    Slider sliderRound;
    TextView tvCountSpinTimes, tvDelete;
    List<WheelItem> wheelItemList;
    WheelView wheelView;
    ImageView icAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_wheel);
        initUI();

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

        icAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
        
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomizeWheelActivity.this, "Deleting", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initUI(){
       wheelView = findViewById(R.id.wv_preview);
       icAdd = findViewById(R.id.ic_add_item);
       sliderRound = findViewById(R.id.slider_spin_times);
       tvCountSpinTimes = findViewById(R.id.tv_set_round);
       tvDelete = findViewById(R.id.tv_delete_wheel);

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
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void  chooseColor(OnChangeColor changeColor) {

        ColorPicker colorPicker = new ColorPicker(CustomizeWheelActivity.this);
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {

            }

            @Override
            public void onCancel() {

            }
        }).addListenerButton("Ok", new ColorPicker.OnButtonListener() {
            @Override
            public void onClick(View v, int position, int color) {
                changeColor.setBackgroundColor(color);
                changeColor.setTextColor(color);
                colorPicker.dismissDialog();

            }
        }).disableDefaultButtons(true)
                .setDefaultColorButton(Color.parseColor("#f84c44"))
                .setColumns(5)
                .setTitle("Chọn màu")
                .setRoundColorButton(true)
                .setDismissOnButtonListenerClick(true)
                .show();



    }

    private void showAddDialog() {
        CardView cvBgPre, cvSetTextColor, cvSetBgColor;
        TextView tvNamePre;
        EditText edtName;

        Button btnSave, btnCancel, btnDelete;


        AlertDialog.Builder builder = new AlertDialog.Builder(CustomizeWheelActivity.this);
        View view = getLayoutInflater().inflate(R.layout.layput_dialog_reset_item,null);

        cvBgPre = view.findViewById(R.id.card_preview_bg_item);
        cvSetBgColor = view.findViewById(R.id.card_set_bg_color);
        tvNamePre = view.findViewById(R.id.tv_preview_name_item);
        cvSetTextColor = view.findViewById(R.id.card_set_text_color);
        edtName = view.findViewById(R.id.edt_item_name);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnSave = view.findViewById(R.id.btn_save);
        btnDelete = view.findViewById(R.id.btn_delete);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog  alertDialog = builder.create();

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvNamePre.setText(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        cvSetBgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseColor(new OnChangeColor() {
                    @Override
                    public void setBackgroundColor(int color) {
                        String col = "#"+Integer.toHexString(color);
                        cvSetBgColor.setBackgroundColor(Color.parseColor(col));
                        cvBgPre.setBackgroundColor(Color.parseColor(col));
                    }

                    @Override
                    public void setTextColor(int color) {

                    }
                });

            }
        });

        cvSetTextColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseColor(new OnChangeColor() {
                    @Override
                    public void setBackgroundColor(int color) {
                        String col = "#"+Integer.toHexString(color);
                        cvSetTextColor.setBackgroundColor(Color.parseColor(col));

                    }

                    @Override
                    public void setTextColor(int color) {
                        String col = "#"+Integer.toHexString(color);
                        tvNamePre.setTextColor(Color.parseColor(col));
                    }
                });
            }
        });



        alertDialog.show();
    }
}