package com.test.cesar.amkprueba.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Cesar on 26/08/2017.
 */

public class Category {

    private String name;
    private Drawable image;

    public Category(String name, Drawable image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
