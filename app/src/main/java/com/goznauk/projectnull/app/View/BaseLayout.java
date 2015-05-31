package com.goznauk.projectnull.app.View;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.LinearLayout;


public class BaseLayout extends LinearLayout {
    public BaseLayout(Context context, int layoutId) {
        super(context);
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutId, this);
    }
}
