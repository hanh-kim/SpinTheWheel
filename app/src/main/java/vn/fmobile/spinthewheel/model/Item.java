package vn.fmobile.spinthewheel.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import rubikstudio.library.model.WheelItem;

@Entity(tableName = "WHEEL_ITEM")
public class Item {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int wheelId;
    public String title;
    public String secondaryTitle;
    public int icon;
    public int backgroundColor;
    public int textColor;

}
