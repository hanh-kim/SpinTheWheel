package vn.fmobile.spinthewheel.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "WHEEL")
public class Wheel {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public int amount;

}
