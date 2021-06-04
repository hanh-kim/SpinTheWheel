package vn.fmobile.spinthewheel.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "HISTORY")
public class History {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int wheelId;
    public String name;
    public int bgColor;
    public int textColor;

    public String dateTime;

    public History() {
    }

    public History(int id, String name, int bgColor, int textColor, String dateTime) {
        this.id = id;
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.name = name;
        this.dateTime = dateTime;
    }
}
