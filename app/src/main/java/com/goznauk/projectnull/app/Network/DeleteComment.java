package com.goznauk.projectnull.app.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by Henry on 2015. 5. 20..
 */
public class DeleteComment {

    private AsyncHttpClient client = new AsyncHttpClient();
    private String articleId;
    private Context context;

    public DeleteComment(Context context, String articleId){
        this.articleId = articleId;
        this.context = context;
    }

    public void execute(final OnResponseListener onResponseListener) {
        final Response response = new Response();

        // TODO : get Article by RESTful APIs
        try {
            SharedPreferences pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = pref.getString("token","nothing");
            client.addHeader("Authorization", token);
            client.delete("http://125.209.193.18:8888/api/post/" + articleId + "/comment", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i("test", "Deleteing comment Succeed!");

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("test","Deleteing article failed");

                }
            });


        } catch (Exception e) {
            Log.i("test", "error : " + e);
        }


    }
}
