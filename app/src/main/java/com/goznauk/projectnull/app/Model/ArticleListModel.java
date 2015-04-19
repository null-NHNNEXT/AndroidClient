package com.goznauk.projectnull.app.Model;

import android.util.Log;
import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Network.GetArticleList;
import com.goznauk.projectnull.app.Network.Response;
import com.goznauk.projectnull.app.Network.OnResponseListener;

import java.util.ArrayList;


public class ArticleListModel extends BaseModel {
    //status pattern
    public static final int LOADING = 0;
    public static final int DONE = 1;
    public static final int ERROR = -1;

    //처음 row의 갯수와 append를 눌렀을 때 증가 되는 row의 갯수
    public static final int NUM_INIT = 20;
    public static final int NUM_APPEND = 10;

    public static final int isINITIAL = -1;

    private int status;

    int initialId;
    int finalId;

    private ArrayList<Article> articles;

    public ArticleListModel() {
        //ArticleListModel이 초기화 될 때 Article들을 담을 ArrayList를 생성한다.
        articles = new ArrayList<Article>();
    }

    public void append() {
        //append를 눌렀을 때의의 처리.
        //먼저 status를 LOADING으로 전환.
        status = LOADING;
        //view를 update한다. update의 구현부는 view에 있다.
        update();

        //Network의 GetArticleList객체를 생성하고, OnResponseListener를 구현한다.
        new GetArticleList(finalId, NUM_APPEND).execute(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                try {
                    @SuppressWarnings("unchecked Cast")
                    //response.get("articles"로 ArrayList<Article>타입의 데이터를 가져온다.
                    ArrayList<Article> newArticles = (ArrayList<Article>) response.get("articles");

                    //newFinalId를 가져온다.
                    int newFinalId = (Integer) response.get("finalId");

                    //기존의 데이터인 articles에 새로 추가된 newArticles를 추가한다.
                    for (Article article : newArticles) {
                        articles.add(article);
                        Log.i("???", article.getArticleId() + " : " + article.getTitle());
                    }
                    //finalId를 newFinalId로 갱신한다.
                    finalId = newFinalId;

                    //status를 바꾸고 view를 다시 갱신한다.
                    status = DONE;
                    update();
                } catch (Exception e) {
                    //error일 경우 status를 바꾸고 그에 맞는 view를 갱신한다.
                    status = ERROR;
                    update();
                }
            }
        });
    }


    public void fetch() {
        //status를 LOADING으로 바꾸고 view갱신.
        status = LOADING;
        update();
        Log.i("test","!!!!?");


        //articles = new ArrayList<Article>();

        //Network의 GetArticleList객체를 생성하고, OnResponseListener를 구현한다.
        new GetArticleList(isINITIAL, NUM_INIT).execute(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                try {
                    ArrayList<Article> newArticles = (ArrayList<Article>) response.get("articles");
                    int newInitialId = (Integer) response.get("initialId");
                    int newFinalId = (Integer) response.get("finalId");
                    for (Article article : newArticles) {
                        articles.add(article);
                    }
                    initialId = newInitialId;
                    finalId = newFinalId;

                    status = DONE;
                    update();
                } catch (Exception e) {
                    status = ERROR;

                    update();
                }
            }
        });

    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public int getStatus() {
        return status;
    }
}
