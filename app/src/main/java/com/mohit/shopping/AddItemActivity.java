package com.mohit.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mohit.shopping.databinding.ActivityAddItemBinding;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";

    public static final String EXTRA_ITEM = "EXTRA_TITLE";
    public static final String EXTRA_PRICE = "EXTRA_PRICE";
    public static final String EXTRA_COUNT = "EXTRA_COUNT";

    private ActivityAddItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Item");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                saveItem();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveItem() {
        String item = binding.editTextTitle.getText().toString();
        String price = binding.editTextPrice.getText().toString();
        int count = 1;

        if (item.trim().isEmpty() || price.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a item and price", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_ITEM, item);
        intent.putExtra(EXTRA_PRICE, price);
        intent.putExtra(EXTRA_COUNT, count);

        setResult(RESULT_OK, intent);
        finish();
    }
}