package com.example.imageprocessing;

import android.graphics.Bitmap;

public class Kernel {

    private Bitmap bitmap;

    private Kernel(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    private Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
