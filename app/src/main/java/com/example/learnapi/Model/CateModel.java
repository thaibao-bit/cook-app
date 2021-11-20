package com.example.learnapi.Model;

public class CateModel {
    int id;
    String category;
    int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CateModel(int id, String category, int count) {
        this.id = id;
        this.category = category;
        this.count = count;
    }
}
