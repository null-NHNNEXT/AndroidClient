package com.goznauk.projectnull.app.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.goznauk.projectnull.app.Entity.ResponseResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Henry on 2015. 5. 18..
 */
public class DeleteArticle {

    private AsyncHttpClient client = new AsyncHttpClient();
    private String articleId;
    private Context context;

    public DeleteArticle(Context context, String articleId){
        this.context = context;
        this.articleId = articleId;
    }

    public void execute(final OnResponseListener onResponseListener) {
        final Response response = new Response();

        // TODO : get Article by RESTful APIs
        try {
            SharedPreferences pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = pref.getString("token","nothing");
            Log.i("test",articleId+"!!!!");
            client.addHeader("Authorization", token);
            client.delete("http://125.209.193.18:8888/api/post/" + articleId, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i("test","Deleteing article Succeed!");
                    onResponseListener.onResponse(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("test","Deleteing article failed");
                    onResponseListener.onResponse(response);
                }
            });


        } catch (Exception e) {
            Log.i("test", "error : " + e);
        }


    }
}
