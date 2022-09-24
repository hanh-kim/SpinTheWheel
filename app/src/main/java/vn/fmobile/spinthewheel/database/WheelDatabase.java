package vn.fmobile.spinthewheel.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import vn.fmobile.spinthewheel.model.History;
import vn.fmobile.spinthewheel.model.Item;
import vn.fmobile.spinthewheel.model.Wheel;

@Database(entities = {Item.class, Wheel.class, History.class}, version = 1)
public abstract class WheelDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "u_spin_wheel.db";
    private static WheelDatabase instance;

    public static synchronized WheelDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), WheelDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }


    public abstract WheelDAO wheelDAO();

    public abstract WheelItemDAO wheelItemDAO();

    public abstract HistoryDAO historyDAO();


}
