package com.mohit.shopping;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Item.class}, version = 1)
public abstract class ItemDatabase extends RoomDatabase {

    private static ItemDatabase instance;

    public abstract ItemDao itemDao();

    public static synchronized ItemDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ItemDatabase.class
                    , "item_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsynTask(instance)
                    .execute();
        }
    };

    private static class PopulateDbAsynTask extends AsyncTask<Void, Void, Void> {

        private ItemDao itemDao;

        private PopulateDbAsynTask(ItemDatabase db) {
            itemDao = db.itemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.insert(new Item("Apple", 10, 1));
            itemDao.insert(new Item("Mango", 20, 2));
            itemDao.insert(new Item("Banana", 30, 3));
            return null;
        }
    }
}
