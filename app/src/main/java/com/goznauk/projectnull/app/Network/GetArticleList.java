package com.goznauk.projectnull.app.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Entity.Writer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;


import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by goznauk on 2015. 4. 4..
 */
public class GetArticleList {
    String initialId;
    Context context;
    ArrayList<Article> articles;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public GetArticleList(Context context, String initialId) {
        //initialId는 fetch시에는 -1로하여 처음임을 알리는 용도로 사용하며
        //append시에는 finalId로 하여

        this.initialId = initialId;
        this.context = context;
    }


    public void execute(final OnResponseListener OnResponseListener) {
        final Response response = new Response();

        // TODO : if initialId = -1 -> get latest
        if(initialId == "getLatest") {
            SharedPreferences pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = pref.getString("token","nothing");
            client.addHeader("Authorization", token);
            client.get("http://125.209.193.18:8888/api/list/7777/all", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    articles = new ArrayList<Article>();

                    String jsonData = new String(responseBody);
                    Log.i("test", "article list data : " + responseBody);

                    try {
                        JSONObject jsonObject = new JSONObject(jsonData);
                        String articleArrJson = jsonObject.getString("result");
                        Log.i("test", articleArrJson);
                        JSONArray jsonArr = new JSONArray(articleArrJson);

                        //article파싱
                        for(int i = 0 ; i< jsonArr.length() ; i++){
                            JSONObject articleJson = jsonArr.getJSONObject(i);
                            String articleId = articleJson.getString("_id");
                            String title = articleJson.getString("title");
                            String penName = articleJson.getString("penName");
                            String contents = articleJson.getString("contents");
                            String timeStamp = articleJson.getString("ts");
                            String image = articleJson.getString("image");

                            Log.i("test", articleId + title + penName + contents + timeStamp + image);
                            Article article = new Article(articleId,title, contents, penName, timeStamp, image);
                            articles.add(article);
                            //댓글 파싱하는 부분. 나중에 완성할 것.
//                            String commentJson = articleJson.getString("comments");
//                            JSONArray commentArr = new JSONArray(commentJson);
//
//
//                            for(int j = 0 ; j < commentArr.length() ; j++){
//
//                            }
                        }

                    }catch(Exception e){
                        Log.e("test","json parsing error" + e);
                    }
                    initialId = articles.get(articles.size()-1).getArticleId();
                    Log.i("test",""+articles.size()+"!");
                    Log.i("test","initial ID : " + initialId);
                    response.add("initialId", initialId);
                    response.add("articles", articles);

                    OnResponseListener.onResponse(response);


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("test", "getArticleList from server failed");
                }
            });
        }else{
        // TODO : if initialId != -1 -> initialId를 url param으로 보내고 이전 게시물 10개 받아오
            SharedPreferences pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = pref.getString("token","nothing");
            client.get("http://125.209.193.18:8888/api/list/12314/all/before/" + initialId, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String jsonData = new String(responseBody);
                    Log.i("test", "여기다!!!!" + jsonData);
                    Log.i("test", "initalId" + initialId);

                    try {
                        JSONObject jsonObject = new JSONObject(jsonData);
                        String articleArrJson = jsonObject.getString("result");
                        Log.i("test", articleArrJson);
                        JSONArray jsonArr = new JSONArray(articleArrJson);

                        //article파싱
                        for(int i = 0 ; i< jsonArr.length() ; i++){
                            JSONObject articleJson = jsonArr.getJSONObject(i);
                            String articleId = articleJson.getString("_id");
                            String title = articleJson.getString("title");
                            String penName = articleJson.getString("penName");
                            String contents = articleJson.getString("contents");
                            String timeStamp = articleJson.getString("ts");
                            String image = articleJson.getString("image");

                            Log.i("test", articleId + title + penName + contents + timeStamp + image);
                            Article article = new Article(articleId,title, contents, penName, timeStamp, image);
                            articles.add(article);
                            //댓글 파싱하는 부분. 나중에 완성할 것.
//                            String commentJson = articleJson.getString("comments");
//                            JSONArray commentArr = new JSONArray(commentJson);
//
//
//                            for(int j = 0 ; j < commentArr.length() ; j++){
//
//                            }
                            Log.e("test","" + articles.size()+"!!");
                            initialId = articles.get(articles.size()-1).getArticleId();
                            Log.i("test","initial ID : " + initialId);
                        }

                    }catch(Exception e){
                        Log.e("test","json parsing error" + e);
                    }

                    response.add("initialId", initialId);
                    response.add("articles", articles);

                    OnResponseListener.onResponse(response);

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

//        articles.add(new Article("articleId1","title","content", "nickName","2011-12-12","profile.png"));
//        articles.add(new Article("articleId2","title","content", "nickName","2011-12-12","profile.png"));
//        articles.add(new Article("articleId3","title","content", "nickName","2011-12-12","profile.png"));
//        articles.add(new Article("articleId4","title","content", "nickName","2011-12-12","profile.png"));
//        articles.add(new Article("articleId5","title","content", "nickName","2011-12-12","profile.png"));

//        response.add("initialId", initialId);
//        response.add("articles", articles);
//
//        OnResponseListener.onResponse(response);
    }
}
