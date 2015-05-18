package com.goznauk.projectnull.app.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import com.goznauk.projectnull.app.Entity.Article;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

public class GetArticleDetail {
    int articleId;
    Article selectedArticle = new Article();
    Context context;

    public GetArticleDetail(Context context, int articleId) {
        this.articleId = articleId;
        this.context = context;
    }
    private static AsyncHttpClient client = new AsyncHttpClient();

    public void execute(OnResponseListener onResponseListener) {
        Response response = new Response();

        // TODO : get Article by RESTful APIs
        try {
            SharedPreferences pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = pref.getString("token","nothing");
            client.addHeader("Authorization", token);
            client.get("http://127.0.0.1:7777/post/"+articleId, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String jsonData = new String(responseBody);
                    Log.i("test", "article list data : " + responseBody);

                    Gson gson = new Gson();
                    selectedArticle = gson.fromJson(jsonData, Article.class);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });

            //
        } catch (Exception e) {
            // if article not exists...
            e.printStackTrace();
            response.add("article", selectedArticle);
        }

        onResponseListener.onResponse(response);
    }
}
