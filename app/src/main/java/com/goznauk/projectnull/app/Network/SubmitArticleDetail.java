package com.goznauk.projectnull.app.Network;

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

    // if articleId = 0, new article
    public SubmitArticleDetail(int postStatus, String title, String content) {
        this.postStatus = postStatus;
        this.title = title;
        this.content = content;


    }

    public SubmitArticleDetail(int postStatus, String articleId, String title, String content) {
        this.postStatus = postStatus;
        this.title = title;
        this.content = content;
        this.articleId = articleId;

    }

    public void executeForNewPost(OnResponseListener OnResponseListener) {
        Response response = new Response();

        RequestParams params = new RequestParams();
        params.put("title", title);
        params.put("contents", content);
        params.put("image", "null");

        client.post("http://125.209.193.18:8888/api/post/12314", params, new AsyncHttpResponseHandler() {
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

        client.put("http://125.209.193.18:8888/api/post/12314/" + articleId, params, new AsyncHttpResponseHandler() {
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
