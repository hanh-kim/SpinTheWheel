package vn.fmobile.spinthewheel.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import vn.fmobile.spinthewheel.model.Wheel;

@Dao
public interface WheelDAO {

    @Insert
    void insertWheelToDatabase(Wheel wheel);

    @Delete
    void deleteWheelInDatabase(Wheel wheel);

    @Query("DELETE FROM WHEEL")
    void deleteAllWheelInDatabase();

    @Query("SELECT * FROM WHEEL")
    List<Wheel> getAllWheelFromDatabase();


}
