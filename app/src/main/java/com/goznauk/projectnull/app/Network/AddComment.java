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
public class AddComment {

    private AsyncHttpClient client = new AsyncHttpClient();
    private String articleId;
    private Context context;
    private String comment;

    public AddComment(Context context, String articleId, String comment){
        this.articleId = articleId;
        this.context = context;
        this.comment = comment;
    }

    public void execute(final OnResponseListener onResponseListener) {
        final Response response = new Response();

        // TODO : get Article by RESTful APIs
        try {
            SharedPreferences pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = pref.getString("token","nothing");
            client.addHeader("Authorization", token);
            client.post("http://125.209.193.18:8888/api/post/" + articleId + "/comment", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i("test", "Add article Succeed!");

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("test","Add article failed");

                }
            });


        } catch (Exception e) {
            Log.i("test", "error : " + e);
        }


    }
}
