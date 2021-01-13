package com.mohit.shopping;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_table")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @ColumnInfo(name = "price")
    private int price;

    @ColumnInfo(name = "count")
    private int count;

    public Item(String name, int price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
