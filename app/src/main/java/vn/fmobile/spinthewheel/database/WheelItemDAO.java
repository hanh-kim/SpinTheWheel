package vn.fmobile.spinthewheel.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vn.fmobile.spinthewheel.model.Item;

@Dao
public interface WheelItemDAO {

    @Query("SELECT * FROM WHEEL_ITEM")
    List<Item> getAllItemFromDatabase();

    @Query("SELECT * FROM WHEEL_ITEM WHERE wheelId = :wheelId")
    List<Item> getWheelItemFromDatabase(int wheelId);

    @Query("DELETE FROM WHEEL_ITEM")
    void deleteAllItemInDatabase();

    @Query("DELETE FROM WHEEL_ITEM WHERE wheelId = :wheelId")
    void deleteAllItemInDatabase(int wheelId);

    @Insert
    void insertItemToDatabase(Item item);

    @Delete
    void deleteItemInDatabase(Item item);

    @Update(entity = Item.class)
    void updateItem(Item item);
}
