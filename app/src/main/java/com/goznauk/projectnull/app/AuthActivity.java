package com.goznauk.projectnull.app;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.goznauk.projectnull.app.Network.GetToken;
import com.goznauk.projectnull.app.Network.OnResponseListener;
import com.goznauk.projectnull.app.Network.Response;
import com.loopj.android.http.RequestParams;

import org.apache.http.client.protocol.RequestAddCookies;

import java.util.UUID;

public class AuthActivity extends Activity implements View.OnClickListener{

    private Button boardIdInputButton;
    private EditText penname;
    private EditText boardId;
    private String deviceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        boardIdInputButton = (Button)findViewById(R.id.board_id_input_button);
        penname = (EditText)findViewById(R.id.penname);
        boardId = (EditText)findViewById(R.id.board_id);
        deviceId = UUID.randomUUID().toString();

        boardIdInputButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.board_id_input_button:

                RequestParams params = new RequestParams();
                params.put("penName", penname.getText());
                params.put("boardId", boardId.getText());
                params.put("writerId", deviceId);

                new GetToken(params).execute(new OnResponseListener(){
                    @Override
                    public void onResponse(Response response) {
                        try {

                            if((String) response.get("error") == null) {

                                String token = (String) response.get("token");
                                savePreference(token);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }else{

                                throw new Exception();
                            }
                        } catch (Exception e) {
                            //네트워크 연결 실패시에 처리
                            Log.i("test", "Token 발행 실패");
                        }
                    }
                });

        }

    }

    private void savePreference(String token){
            SharedPreferences pref = getSharedPreferences("auth", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("token", token);
            editor.commit();
    }
}
