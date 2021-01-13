package com.mohit.shopping;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {

    private ItemDao itemDao;
    private LiveData<List<Item>> allItems;

    public ItemRepository(Application application) {
        ItemDatabase database = ItemDatabase.getInstance(application);
        itemDao = database.itemDao();
        allItems = itemDao.getAllItem();
    }

    public void insert(Item item) {
        new InsertItemAsyncTask(itemDao).execute(item);
    }

    public void update(Item item) {
        new UpdateItemAsyncTask(itemDao).execute(item);
    }

    public void delete(Item item) {
        new DeleteItemAsyncTask(itemDao).execute(item);
    }

    public void deleteAllItems() {
        new DeleteAllItemAsyncTask(itemDao).execute();
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    private static class InsertItemAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao itemDao;

        private InsertItemAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.insert(items[0]);
            return null;
        }
    }

    private static class UpdateItemAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao itemDao;

        private UpdateItemAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.update(items[0]);
            return null;
        }
    }

    private static class DeleteItemAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao itemDao;

        private DeleteItemAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.delete(items[0]);
            return null;
        }
    }

    private static class DeleteAllItemAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao itemDao;

        private DeleteAllItemAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.deleteAllItems();
            return null;
        }
    }
}
