package com.example.guyazran.learninglistview2;

/**
 * Created by guyazran on 8/10/15.
 */
public class ListItem {
    String item;
    String subItem;
    int image;

    public ListItem(String item, String subItem, int image) {
        this.item = item;
        this.subItem = subItem;
        this.image = image;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getSubItem() {
        return subItem;
    }

    public void setSubItem(String subItem) {
        this.subItem = subItem;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
