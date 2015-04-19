package com.goznauk.projectnull.app;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.goznauk.projectnull.app.Controller.ArticleListFragment;


public class MainActivity extends ActionBarActivity {

    //ArticleListFragment를 생성하여 Main Activity에 붙임.
    //Main Activity는 Container역할만 하고 View는 Fragment에서 담당함.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ArticleListFragment())
                    .commit();
        }

    }


}
