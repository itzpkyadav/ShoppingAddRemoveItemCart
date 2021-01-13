package com.mohit.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mohit.shopping.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements OnAdapterItemListener {

    private static final String TAG = "MainActivity";

    public static final int ADD_ITEM_REQUEST = 1;

    private ActivityMainBinding binding;
    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            startActivityForResult(intent, ADD_ITEM_REQUEST);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);

        final ItemAdapter adapter = new ItemAdapter(this);
        binding.recyclerView.setAdapter(adapter);

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel.getAllItems().observe(this, adapter::setItems);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                itemViewModel.delete(adapter.getItemAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddItemActivity.EXTRA_ITEM);
            String price = data.getStringExtra(AddItemActivity.EXTRA_PRICE);
            int count = data.getIntExtra(AddItemActivity.EXTRA_COUNT, 1);

            Item item = new Item(title, Integer.parseInt(price), count);
            itemViewModel.insert(item);

            Toast.makeText(this, "Item saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_items:
                itemViewModel.deleteAllItems();
                Toast.makeText(this, "All Items deleted", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAdapterItemClick(boolean isAdd, Item item) {
        int count = item.getCount();
        if (isAdd) {
            Log.d(TAG, "onAdapterItemClick: Add");
            count++;
            item.setCount(count);
            itemViewModel.update(item);
        } else {
            Log.d(TAG, "onAdapterItemClick: Subtract");
            count--;
            item.setCount(count);
            if (count > 0)
                itemViewModel.update(item);
            else
                itemViewModel.delete(item);

        }
    }
}