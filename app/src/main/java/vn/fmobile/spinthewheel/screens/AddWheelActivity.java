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
import vn.fmobile.spinthewheel.model.Item;
import vn.fmobile.spinthewheel.others.OnChangeColor;
import vn.fmobile.spinthewheel.R;
import vn.fmobile.spinthewheel.others.OnItemClickListener;

public class AddWheelActivity extends AppCompatActivity implements View.OnClickListener {
    Slider sliderRound;
    TextView tvCountSpinTimes, tvCancel;
    List<WheelItem> wheelItemList;

    WheelView wheelView;
    ImageView icAdd;
    List<Item> itemList;
    RecyclerView rcvItem;
    ItemPreviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wheel);

        // initialize views
        initUI();

        // action on click to view
        icAdd.setOnClickListener(this::onClick);
        tvCancel.setOnClickListener(this::onClick);

        // data item preview
        for (int i = 0; i < 20; i++) {
            Item item = new Item();
            if (i % 2 == 0) {
                //item.title = "hanh"+(i+1);
                item.title = "hanh " + (i + 1);
                item.backgroundColor = Color.RED;
                item.textColor = Color.BLACK;
            } else {
                // item.title = "canh"+(i+1);
                item.title = "kim " + (i + 1);
                item.backgroundColor = Color.GREEN;
                item.textColor = Color.BLACK;
            }

            itemList.add(item);

        }


        // data wheel preview
        for (int i = 0; i < 30; i++) {
            WheelItem item = new WheelItem();
            if (i % 2 == 0) {
                //item.title = "hanh"+(i+1);
                item.secondaryText = "hanhkkkk" + (i + 1);
                item.backgroundColor = Color.RED;
                item.textColor = Color.BLACK;
            } else {
                // item.title = "canh"+(i+1);
                item.secondaryText = "c" + (i + 1);
                item.backgroundColor = Color.GREEN;
                item.textColor = Color.BLACK;
            }

            wheelItemList.add(item);

        }

        // set data for wheel
        wheelView.setData(wheelItemList);

        // on change the round number
        sliderRound.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                tvCountSpinTimes.setText((int) value + "x");
            }
        });


        // show list item
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddWheelActivity.this);
        rcvItem.setLayoutManager(linearLayoutManager);

        adapter.setData(itemList, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showCustomizeDialog(position);

            }
        });

        rcvItem.setAdapter(adapter);


    }

    private void chooseColor(OnChangeColor changeColor) {

        ColorPicker colorPicker = new ColorPicker(AddWheelActivity.this);
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
                if (color==0){
                    Toast.makeText(AddWheelActivity.this, "Bạn chưa trọn màu", Toast.LENGTH_SHORT).show();
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

    private void initUI() {
        wheelView = findViewById(R.id.wv_preview);
        icAdd = findViewById(R.id.ic_add_item);
        sliderRound = findViewById(R.id.slider_spin_times);
        tvCountSpinTimes = findViewById(R.id.tv_set_round);
        tvCancel = findViewById(R.id.tv_cancel);
        rcvItem = findViewById(R.id.rcv_pre_item);
        adapter = new ItemPreviewAdapter();
        itemList = new ArrayList<>();

        wheelItemList = new ArrayList<>();
        sliderRound.setValue((float) 5.0);
        tvCountSpinTimes.setText((int) sliderRound.getValue() + "x");

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
            startActivity(new Intent(AddWheelActivity.this, SpinActivity.class));
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == icAdd) {
            showAddDialog();
        } else if (v == tvCancel) {
            finish();
        }
    }

    private void showAddDialog() {
        CardView cvBgPre, cvSetTextColor, cvSetBgColor;
        TextView tvNamePre;
        EditText edtName;

        Button btnSave, btnCancel, btnDelete;


        AlertDialog.Builder builder = new AlertDialog.Builder(AddWheelActivity.this);
        View view = getLayoutInflater().inflate(R.layout.layout_dialog_add_item, null);

        cvBgPre = view.findViewById(R.id.card_preview_bg_item);
        cvSetBgColor = view.findViewById(R.id.card_set_bg_color);
        tvNamePre = view.findViewById(R.id.tv_preview_name_item);
        cvSetTextColor = view.findViewById(R.id.card_set_text_color);
        edtName = view.findViewById(R.id.edt_item_name);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnSave = view.findViewById(R.id.btn_save);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();

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

        btnSave.setOnClickListener(new View.OnClickListener() {
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
                        String col = "#" + Integer.toHexString(color);
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
                        String col = "#" + Integer.toHexString(color);
                        cvSetTextColor.setBackgroundColor(Color.parseColor(col));

                    }

                    @Override
                    public void setTextColor(int color) {
                        String col = "#" + Integer.toHexString(color);
                        tvNamePre.setTextColor(Color.parseColor(col));
                    }
                });
            }
        });

        alertDialog.show();
    }

    private void showCustomizeDialog(int position) {

        Item item = itemList.get(position);
        CardView cvBgPre, cvSetTextColor, cvSetBgColor;
        TextView tvNamePre;
        EditText edtName;

        Button btnSave, btnCancel, btnDelete;


        AlertDialog.Builder builder = new AlertDialog.Builder(AddWheelActivity.this);
        View view = getLayoutInflater().inflate(R.layout.layout_dialog_reset_item, null);

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
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                Toast.makeText(AddWheelActivity.this, "Add item successfully!", Toast.LENGTH_SHORT).show();
                alertDialog.cancel();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddWheelActivity.this, "Delete item successfuly!", Toast.LENGTH_SHORT).show();
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
                    }
                });
            }
        });

        alertDialog.show();
    }

}