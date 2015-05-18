package com.goznauk.projectnull.app.Model;

import android.content.Context;
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

    private int status;
    private Context context;

    //initialId 멤버변수를 가지고 있으면서 list관리
    String initialId;


    private ArrayList<Article> articles;

    public ArticleListModel(Context context) {
        //ArticleListModel이 초기화 될 때 Article들을 담을 ArrayList를 생성한다.
        articles = new ArrayList<Article>();
        this.context = context;
    }

    public void append() {
        //append를 눌렀을 때의의 처리.
        //먼저 status를 LOADING으로 전환.
        status = LOADING;
        //view를 update한다. update의 구현부는 view에 있다.
        update();

        //Network의 GetArticleList객체를 생성하고, OnResponseListener를 구현한다.
        new GetArticleList(context, initialId).execute(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                try {
                    @SuppressWarnings("unchecked Cast")
                    //response.get("articles"로 ArrayList<Article>타입의 데이터를 가져온다.
                    ArrayList<Article> newArticles = (ArrayList<Article>) response.get("articles");

                    //newFinalId를 가져온다.
                    String newInitialId = (String) response.get("initialId");

                    //기존의 데이터인 articles에 새로 추가된 newArticles를 추가한다.
                    for (Article article : newArticles) {
                        articles.add(article);
                        Log.i("???", article.getArticleId() + " : " + article.getTitle());
                    }
                    //finalId를 newFinalId로 갱신한다.
                    initialId = newInitialId;

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
        new GetArticleList(context, "getLatest").execute(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                try {
                    ArrayList<Article> newArticles = (ArrayList<Article>) response.get("articles");
                    String newInitialId = (String) response.get("initialId");

                    for (Article article : newArticles) {
                        articles.add(article);
                    }
                    initialId = newInitialId;

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
