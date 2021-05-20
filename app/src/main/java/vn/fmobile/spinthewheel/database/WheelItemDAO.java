package vn.fmobile.spinthewheel.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import vn.fmobile.spinthewheel.model.LuckyItem;

@Dao
public interface WheelItemDAO {

    @Query("SELECT * FROM WHEEL_ITEM")
    List<LuckyItem> getAllItemFromDatabase();

    @Query("DELETE FROM WHEEL_ITEM")
    void deleteAllItemInDatabase();

    @Insert
    void insertItemToDatabase(LuckyItem item);

    @Delete
    void deleteItemInDatabase(LuckyItem item);
}
