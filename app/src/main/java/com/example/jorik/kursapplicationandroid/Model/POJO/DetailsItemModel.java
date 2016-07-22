package com.example.jorik.kursapplicationandroid.Model.POJO;

import android.graphics.drawable.Drawable;

/**
 * Created by jorik on 20.07.16.
 */

public class DetailsItemModel {

    public DetailsItemModel(Drawable image, String value) {
        this.image = image;
        this.value = value;
    }

    Drawable image;
    String value;

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
