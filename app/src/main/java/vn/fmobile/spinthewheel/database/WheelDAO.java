package vn.fmobile.spinthewheel.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vn.fmobile.spinthewheel.model.Wheel;

@Dao
public interface WheelDAO {

    @Insert
    void addWheelIntoDatabase(Wheel wheel);

    @Delete
    void deleteWheelInDatabase(Wheel wheel);

    @Query("DELETE FROM WHEEL WHERE isActive = :isActive")
    void deleteWheelIsActive(int isActive);

    @Query("DELETE FROM WHEEL")
    void deleteAllWheelInDatabase();

    @Query("SELECT * FROM WHEEL")
    List<Wheel> getAllWheelFromDatabase();

    @Query("SELECT * FROM WHEEL WHERE id =:id")
    Wheel getWheelFromDatabase(int id);

    @Update(entity = Wheel.class)
    void updateWheel(Wheel wheel);


}
