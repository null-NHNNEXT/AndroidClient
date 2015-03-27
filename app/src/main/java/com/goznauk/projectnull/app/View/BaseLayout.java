package com.goznauk.projectnull.app.View;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.goznauk.projectnull.app.R;

/**
 * Created by goznauk on 2015. 3. 27..
 */
public class BaseLayout extends LinearLayout {
    public BaseLayout(Context context, int layoutId) {
        super(context);
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutId, this);
    }
}
