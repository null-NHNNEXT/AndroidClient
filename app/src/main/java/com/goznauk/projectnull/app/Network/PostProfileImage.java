package com.goznauk.projectnull.app.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.format.DateUtils;
import android.util.Log;

import com.goznauk.projectnull.app.Entity.Comment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Henry on 2015. 6. 1..
 */
public class PostProfileImage {

    private AsyncHttpClient client = new AsyncHttpClient();
    private Context context;
    private String filePath;

    public PostProfileImage(Context context, String filePath){
        this.context = context;
        this.filePath = filePath;

    }

    public void execute(final OnResponseListener onResponseListener) {
        final Response response = new Response();
        RequestParams params = new RequestParams();

        SharedPreferences pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        final String UUID = pref.getString("UUID","nothing");

        try {
            params.put("file", new File(filePath));
        }catch (FileNotFoundException e){
            Log.e("test", "File path error");
        }
        // TODO : get Article by RESTful APIs
        try {
            pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = pref.getString("token","nothing");
            Log.i("test", UUID);
            client.addHeader("Authorization", token);
            Log.i("upload", "start" + Calendar.getInstance().getTimeInMillis());
            client.post("http://125.209.193.18/image/profile/" + UUID, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i("test", "Image upload succeed");
                    Log.i("upload", "fin" + Calendar.getInstance().getTimeInMillis());
                    onResponseListener.onResponse(response);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    onResponseListener.onResponse(response);

                }
            });


        } catch (Exception e) {
            Log.i("test", "error : " + e);
        }

    }
}
