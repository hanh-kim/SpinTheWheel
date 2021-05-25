package vn.fmobile.spinthewheel.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import rubikstudio.library.model.WheelItem;

@Entity(tableName = "WHEEL_ITEM")
public class LuckyItem{

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int idWheel;
    public String title;
    public String secondaryText;
    public int icon;
    public int backgroundColor;
    public int textColor;


    public LuckyItem() {
        super();
    }
}
