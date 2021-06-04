package vn.fmobile.spinthewheel.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import vn.fmobile.spinthewheel.model.Item;

@Dao
public interface WheelItemDAO {

    @Query("SELECT * FROM Item")
    List<Item> getAllItemFromDatabase();

    @Query("DELETE FROM Item")
    void deleteAllItemInDatabase();

    @Insert
    void insertItemToDatabase(Item item);

    @Delete
    void deleteItemInDatabase(Item item);
}
