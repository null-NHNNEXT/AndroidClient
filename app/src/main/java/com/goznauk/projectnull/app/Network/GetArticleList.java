package com.goznauk.projectnull.app.Network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Entity.Writer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;


import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by goznauk on 2015. 4. 4..
 */
public class GetArticleList {
    int initialId;
    int num;
    ArrayList<Article> articles;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public GetArticleList(int initialId, int num) {
        //initialId는 fetch시에는 -1로하여 처음임을 알리는 용도로 사용하며
        //append시에는 finalId로 하여

        this.initialId = initialId;
        this.num = num;
    }


    public void execute(OnResponseListener OnResponseListener) {
        Response response = new Response();

        // TODO : if initialId = -1 -> get latest
        if(initialId == -1) {
            client.get("http://127.0.0.1:7777/list?initialId="+initialId, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String jsonData = new String(responseBody);
                    Log.i("test", "article list data : " + responseBody);

                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Article>>(){}.getType();
                    articles = gson.fromJson(jsonData, listType);

                    initialId = articles.get(0).getArticleId();

                    //초기 num을 파라미터로 하여 일정 갯수의 article을 받아오지만,
                    //article의 갯수가 num보다 적을 경우가 있을 수 있으므로 num갱신
                    num = articles.size();




                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("test", "getArticleList from server failed");
                }
            });
        }else{
        // TODO : if initialId != -1 이전 article가져오기

            client.get("http://127.0.0.1:7777/list?initialId="+initialId, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String jsonData = new String(responseBody);
                    Log.i("test", "article list data : " + responseBody);

                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Article>>(){}.getType();
                    articles = gson.fromJson(jsonData, listType);

                    initialId = articles.get(0).getArticleId();
                    num = articles.size();




                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("test", "getArticleList from server failed");
                }
            });



        }


        // TODO : get Articles num or lower(if not exists)
        if(articles == null){
            articles = new ArrayList<Article>();
        }

        //test
        articles.add(new Article(0,"title","contestsDigest", new Writer(100,"nickname"),"time", "imageId"));

        response.add("initialId", initialId);
        response.add("finalId", initialId+num-1);
        response.add("articles", articles);

        OnResponseListener.onResponse(response);
    }
}
