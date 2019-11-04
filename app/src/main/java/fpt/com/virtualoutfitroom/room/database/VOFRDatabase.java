package fpt.com.virtualoutfitroom.room.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.room.dao.AccountDAO;
import fpt.com.virtualoutfitroom.room.dao.OrderDAO;

@Database(entities = {OrderItemEntities.class, AccountItemEntities.class},exportSchema = false,version = VOFRDatabase.DATABASE_VERSION)
public abstract class VOFRDatabase extends RoomDatabase {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "unideli-database";
    private static VOFRDatabase INSTANCE;
    public abstract OrderDAO orderDAO();
    public abstract AccountDAO accountDao();

    public static VOFRDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VOFRDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VOFRDatabase.class, DATABASE_NAME)
                            .build();
                }
            }

        }
        return INSTANCE;
    }

}
