package vn.fmobile.spinthewheel.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import vn.fmobile.spinthewheel.adapter.ItemPreviewAdapter;
import vn.fmobile.spinthewheel.database.WheelDatabase;
import vn.fmobile.spinthewheel.model.Item;
import vn.fmobile.spinthewheel.model.Wheel;
import vn.fmobile.spinthewheel.others.Memory;
import vn.fmobile.spinthewheel.others.OnChangeColor;
import vn.fmobile.spinthewheel.R;
import vn.fmobile.spinthewheel.others.OnItemClickListener;

public class CustomizeWheelActivity extends AppCompatActivity {

    Slider sliderRound;
    TextView tvCountSpinTimes, tvDelete;
    List<WheelItem> wheelItemList;
    WheelView wheelView;
    ImageView icAdd;
    EditText edtWheelTitle;
    List<Item> itemList;
    RecyclerView rcvItem;
    ItemPreviewAdapter adapter;
    Bundle bundle;
    WheelDatabase database;
    Wheel wheel;
    int wheelId =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_wheel);
        initUI();

        itemList = database.wheelItemDAO().getWheelItemFromDatabase(wheel.id);
        edtWheelTitle.setText(wheel.title);

        sliderRound.setValue((float) wheel.round);
        tvCountSpinTimes.setText(wheel.round + "x");

        // show list item
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CustomizeWheelActivity.this);
        rcvItem.setLayoutManager(linearLayoutManager);

        adapter.setData(itemList, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showCustomizeDialog(position);

            }
        });

        rcvItem.setAdapter(adapter);

        // wheel pre
        showPreviewWheel(itemList);

        sliderRound.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                tvCountSpinTimes.setText((int) value + "x");
                saveWheelIntoDatabase(wheel);
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

    private void initUI() {
        wheelView = findViewById(R.id.wv_preview);
        icAdd = findViewById(R.id.ic_add_item);
        sliderRound = findViewById(R.id.slider_spin_times);
        tvCountSpinTimes = findViewById(R.id.tv_set_round);
        tvDelete = findViewById(R.id.tv_delete_wheel);
        rcvItem = findViewById(R.id.rcv_pre_item);
        adapter = new ItemPreviewAdapter();
        edtWheelTitle = findViewById(R.id.edt_title);
        wheelItemList = new ArrayList<>();
        database = WheelDatabase.getInstance(CustomizeWheelActivity.this.getApplicationContext());

        wheelId = Memory.wheelId;
        wheel = database.wheelDAO().getWheelFromDatabase(wheelId);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.item_done) {
            if (itemList.size()==0){
                Toast.makeText(this, "Vòng quay trống! Hãy thêm các ô cho vòng quay.", Toast.LENGTH_SHORT).show();
            }else {

                saveWheelIntoDatabase(wheel);
                Intent intent = new Intent(CustomizeWheelActivity.this, SpinActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("wheel",wheel);
                intent.putExtras(bundle);
                startActivity(intent);
                this.finish();
            }
          //  this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPreviewWheel(List<Item> itemList) {
        List<WheelItem> wheelItemList = new ArrayList<>();
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

        // set data for wheel
        wheelView.setData(wheelItemList);
    }

    private void chooseColor(OnChangeColor changeColor) {

        ColorPicker colorPicker = new ColorPicker(CustomizeWheelActivity.this);
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                //  colorPicker.dismissDialog();
            }

            @Override
            public void onCancel() {

            }
        }).addListenerButton("Chọn", new ColorPicker.OnButtonListener() {
            @Override
            public void onClick(View v, int position, int color) {
                if (color == 0) {
                    Toast.makeText(CustomizeWheelActivity.this, "Bạn chưa chọn màu", Toast.LENGTH_SHORT).show();
                    return;
                }
                changeColor.setBackgroundColor(color);
                changeColor.setTextColor(color);
                colorPicker.dismissDialog();

            }
        }).disableDefaultButtons(true)
                //  .setDefaultColorButton(Color.parseColor("#f84c44"))
                .setColumns(5)
                .setTitle("Chọn màu")
                .setRoundColorButton(true)
                .setDismissOnButtonListenerClick(true)
                .show();


    }

    private void showAddDialog() {

        CardView cvBgPre, cvSetTextColor, cvSetBgColor;
        TextView tvNamePre, tvError;
        EditText edtName;
        Button btnSave, btnCancel, btnDelete;
        String bgC = "#F8ED8C";
        if (itemList.size()%2==0){
            bgC = "#FAD66B";
        }

        String txtC = "#101010";
        Item item = new Item();
        item.wheelId = wheel.id;
        item.backgroundColor = Color.parseColor(bgC);
        item.textColor = Color.parseColor(txtC);


        AlertDialog.Builder builder = new AlertDialog.Builder(CustomizeWheelActivity.this);
        View view = getLayoutInflater().inflate(R.layout.layout_dialog_add_item, null);

        // init views
        cvBgPre = view.findViewById(R.id.card_preview_bg_item);
        cvSetBgColor = view.findViewById(R.id.card_set_bg_color);
        tvNamePre = view.findViewById(R.id.tv_preview_item_title);
        tvError = view.findViewById(R.id.tv_error);
        cvSetTextColor = view.findViewById(R.id.card_set_text_color);
        edtName = view.findViewById(R.id.edt_item_title);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnSave = view.findViewById(R.id.btn_save);
        builder.setView(view);
        builder.setCancelable(false);

        cvBgPre.setBackgroundColor(Color.parseColor(bgC));
        cvSetBgColor.setBackgroundColor(Color.parseColor(bgC));

        AlertDialog alertDialog = builder.create();

        // logic code
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvNamePre.setText(s.toString().trim());
                tvError.setVisibility(View.INVISIBLE);
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtName.getText().toString().trim();
                if (TextUtils.isEmpty(title)){
                    tvError.setVisibility(View.VISIBLE);
                    return;
                }
                tvError.setVisibility(View.INVISIBLE);
                item.title = title;
                itemList.add(item);
                database.wheelItemDAO().insertItemToDatabase(item);
                showPreviewWheel(itemList);
                adapter.notifyDataSetChanged();
                alertDialog.cancel();
            }
        });

        cvSetBgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseColor(new OnChangeColor() {
                    @Override
                    public void setBackgroundColor(int color) {
                        String col = "#" + Integer.toHexString(color);
                        cvSetBgColor.setBackgroundColor(Color.parseColor(col));
                        cvBgPre.setBackgroundColor(Color.parseColor(col));
                        item.backgroundColor = Color.parseColor(col);
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
                        String col = "#" + Integer.toHexString(color);
                        cvSetTextColor.setBackgroundColor(Color.parseColor(col));

                    }

                    @Override
                    public void setTextColor(int color) {
                        String col = "#" + Integer.toHexString(color);
                        tvNamePre.setTextColor(Color.parseColor(col));
                        item.textColor = Color.parseColor(col);
                    }
                });
            }
        });

        alertDialog.show();
    }

    private void showCustomizeDialog(int position) {

        Item item = itemList.get(position);
        CardView cvBgPre, cvSetTextColor, cvSetBgColor;
        TextView tvNamePre, tvError;
        EditText edtName;

        Button btnSave, btnCancel, btnDelete;


        AlertDialog.Builder builder = new AlertDialog.Builder(CustomizeWheelActivity.this);
        View view = getLayoutInflater().inflate(R.layout.layout_dialog_reset_item, null);

        cvBgPre = view.findViewById(R.id.card_preview_bg_item);
        cvSetBgColor = view.findViewById(R.id.card_set_bg_color);
        tvNamePre = view.findViewById(R.id.tv_preview_item_title);
        cvSetTextColor = view.findViewById(R.id.card_set_text_color);
        edtName = view.findViewById(R.id.edt_item_title);
        tvError = view.findViewById(R.id.tv_error);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnSave = view.findViewById(R.id.btn_save);
        btnDelete = view.findViewById(R.id.btn_delete);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();

        // setup data
        edtName.setText(item.title);
        tvNamePre.setText(item.title);
        tvNamePre.setTextColor(item.textColor);
        cvBgPre.setBackgroundColor(item.backgroundColor);
        cvSetTextColor.setBackgroundColor(item.textColor);
        cvSetBgColor.setBackgroundColor(item.backgroundColor);


        // Set title
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvNamePre.setText(s.toString().trim());
                tvError.setVisibility(View.INVISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {
                saveWheelIntoDatabase(wheel);
            }
        });


        // btn on click listener
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtName.getText().toString().trim();
                if (TextUtils.isEmpty(title)){
                    tvError.setVisibility(View.VISIBLE);
                    return;
                }
                tvError.setVisibility(View.INVISIBLE);
                item.title = title;
                database.wheelItemDAO().updateItem(item);
                showPreviewWheel(itemList);
                adapter.notifyDataSetChanged();

                alertDialog.cancel();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.remove(item);
                database.wheelItemDAO().deleteItemInDatabase(item);
                showPreviewWheel(itemList);
                adapter.notifyDataSetChanged();

                alertDialog.cancel();
            }
        });

        //..........

        cvSetBgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseColor(new OnChangeColor() {
                    @Override
                    public void setBackgroundColor(int color) {
                        String col = "#" + Integer.toHexString(color);
                        cvSetBgColor.setBackgroundColor(Color.parseColor(col));
                        cvBgPre.setBackgroundColor(Color.parseColor(col));
                        item.backgroundColor = Color.parseColor(col);
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
                        String col = "#" + Integer.toHexString(color);
                        cvSetTextColor.setBackgroundColor(Color.parseColor(col));

                    }

                    @Override
                    public void setTextColor(int color) {
                        String col = "#" + Integer.toHexString(color);
                        tvNamePre.setTextColor(Color.parseColor(col));
                        item.textColor = Color.parseColor(col);
                    }
                });
            }
        });

        alertDialog.show();
    }


    private void saveWheelIntoDatabase(Wheel wheel) {
        String wheelTitle = edtWheelTitle.getText().toString().trim();
        if (wheelTitle.isEmpty()){
            wheel.title = "U-Spin";
        }else  wheel.title = wheelTitle;
        wheel.round = (int) sliderRound.getValue();
        database.wheelDAO().updateWheel(wheel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}