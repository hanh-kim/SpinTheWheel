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

    @Query("DELETE FROM HISTORY")
    void deleteAll();

    @Query("SELECT * FROM HISTORY")
    List<History> getHistoryFromDatabase();
}
