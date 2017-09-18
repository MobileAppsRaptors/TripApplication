package com.example.admin.tripapplication.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.beardedhen.androidbootstrap.BootstrapButton;

public class BootstrapButtonIcon extends BootstrapButton {

    public BootstrapButtonIcon(Context context) {
        super(context);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fontawesome.ttf");
        setTypeface(tf);
    }

    public BootstrapButtonIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fontawesome.ttf");
        setTypeface(tf);
    }

    public BootstrapButtonIcon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fontawesome.ttf");
        setTypeface(tf);
    }


}
