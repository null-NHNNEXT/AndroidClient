package com.goznauk.projectnull.app.View;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Henry on 2015. 4. 8..
 */
public class TypefaceFactory {

    private static TypefaceFactory typefaceFactory;
    private Context context;

    private TypefaceFactory(Context context){
        this.context = context;
    };
    public static TypefaceFactory getTypefaceFactory(Context context){
        if(typefaceFactory == null){
            typefaceFactory = new TypefaceFactory(context);
        }
        return typefaceFactory;
    }

    public Typeface getTypeface(String typeface){
        if(typeface == "SDCrayon") {
            Typeface SDCrayon = Typeface.createFromAsset(context.getAssets(), "fonts/sdcrayon.ttf");
            return SDCrayon;
        }
        return Typeface.DEFAULT;
    }
}
