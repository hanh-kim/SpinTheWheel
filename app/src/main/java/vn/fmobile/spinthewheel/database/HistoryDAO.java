package vn.fmobile.spinthewheel.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import vn.fmobile.spinthewheel.model.History;


@Dao
public interface HistoryDAO {

    @Insert
    void insertHistoryToDatabase(History history);

    @Delete
    void deleteHistoryItem(History history);

    @Query("DELETE FROM HISTORY WHERE wheelId = :wheelId")
    void deleteAll(int wheelId);

    @Query("SELECT * FROM HISTORY")
    List<History> getHistoryFromDatabase();

    @Query("SELECT * FROM HISTORY WHERE wheelId = :wheelId")
    List<History> getHistoryFromDatabase(int wheelId);
}
