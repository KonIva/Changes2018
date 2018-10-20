package com.example.alvaf1.changes2018;

import java.util.ArrayList;

public class SaveListSingleton {

    private int scrollPosition;

    public ArrayList<Item> items;

    private Item item;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int total;


    private static SaveListSingleton instance = new SaveListSingleton();

    private SaveListSingleton() {
    }

    public static SaveListSingleton getInstance() {
        if (instance == null) {
            instance = new SaveListSingleton();
        }
        return instance;
    }
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }
}
