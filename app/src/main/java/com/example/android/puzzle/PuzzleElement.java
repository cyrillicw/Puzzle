package com.example.android.puzzle;

import android.graphics.drawable.Drawable;

public class PuzzleElement {
    private Drawable color;
    private int id;

    public PuzzleElement(Drawable color, int id) {
        this.color = color;
        this.id = id;
    }

    public Drawable getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public void setColor(Drawable color) {
        this.color = color;
    }
}
