package com.goznauk.projectnull.app.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.goznauk.projectnull.app.Entity.Article;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;

/**
 * Created by goznauk on 2015. 4. 4..
 */
public class SubmitArticleDetail {
    private int postStatus;
    private String title;
    private String content;
    private String articleId;
    private AsyncHttpClient client = new AsyncHttpClient();
    private Context context;

    // if articleId = 0, new article
    public SubmitArticleDetail(Context context, int postStatus, String title, String content) {
        this.postStatus = postStatus;
        this.title = title;
        this.content = content;
        this.context = context;

    }

    public SubmitArticleDetail(Context context, int postStatus, String articleId, String title, String content) {
        this.postStatus = postStatus;
        this.title = title;
        this.content = content;
        this.articleId = articleId;
        this.context = context;
    }

    public void executeForNewPost(OnResponseListener OnResponseListener) {
        Response response = new Response();

        RequestParams params = new RequestParams();
        params.put("title", title);
        params.put("contents", content);
        params.put("image", "null");


        SharedPreferences pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        String token = pref.getString("token","nothing");
        client.addHeader("Authorization", token);
        client.post("http://125.209.193.18:8888/api/post", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String res = new String(responseBody);
                Log.i("test", "POST Succeed : " + res);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String res = new String(responseBody);
                Log.i("test", "POST failed : " + res);
            }

        });

        OnResponseListener.onResponse(response);
    }

    public void executeFormerPost(OnResponseListener OnResponseListener) {
        Response response = new Response();

        RequestParams params = new RequestParams();
        params.put("title", title);
        params.put("contents", content);
        params.put("image", "null");


        SharedPreferences pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        String token = pref.getString("token","nothing");
        client.addHeader("Authorization", token);
        client.put("http://125.209.193.18:8888/api/post/" + articleId, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });



        OnResponseListener.onResponse(response);
    }
}
