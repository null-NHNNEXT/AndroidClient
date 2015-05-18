package com.goznauk.projectnull.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


public class SplashActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                //splash activity에서 토큰이 있는지 여부를 체크한다.
                boolean tokenExistOrNot = tokenChecking();
                if(tokenExistOrNot == true) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else{
                    startActivity(new Intent(getApplicationContext(), AuthActivity.class));
                }
            }
        }, 2500);
    }

    private boolean tokenChecking(){
        SharedPreferences pref = getSharedPreferences("auth", MODE_PRIVATE);
        String token = pref.getString("token","nothing");

        if(token == "nothing")
            return false;
        else return true;
    }

}
