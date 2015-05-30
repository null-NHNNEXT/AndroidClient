package com.goznauk.projectnull.app.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.goznauk.projectnull.app.Entity.Comment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * Created by Henry on 2015. 5. 29..
 */
public class SaveComment {

    private AsyncHttpClient client = new AsyncHttpClient();
    private Comment comment;
    private Context context;
    private String articleId;

    public SaveComment(Context context, String articleId, Comment comment){
        this.context = context;
        this.articleId = articleId;
        this.comment = comment;
    }

    public void execute(final OnResponseListener onResponseListener) {
        final Response response = new Response();
        RequestParams params = new RequestParams();
        params.put("contents", comment.getContents());

        // TODO : get Article by RESTful APIs
        try {
            SharedPreferences pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = pref.getString("token","nothing");
            client.addHeader("Authorization", token);
            client.post("http://125.209.193.18:8888/api/post/" + articleId + "/comment",params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i("test", "Saveing Comment Succeed!");

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("test", "Saving Comment article failed");

                }
            });


        } catch (Exception e) {
            Log.i("test", "error : " + e);
        }

        onResponseListener.onResponse(response);
    }

}
