package com.goznauk.projectnull.app.Model;

import android.util.Log;
import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Network.GetArticleList;
import com.goznauk.projectnull.app.Network.Response;
import com.goznauk.projectnull.app.Network.OnResponseListener;

import java.util.ArrayList;

/**
 * Created by goznauk on 2015. 3. 28..
 */
public class ArticleListModel extends BaseModel {
    public static final int LOADING = 0;
    public static final int DONE = 1;
    public static final int ERROR = -1;

    public static final int NUM_INIT = 20;
    public static final int NUM_APPEND = 10;

    public static final int isINITIAL = -1;

    private int status;

    int initialId;
    int finalId;

    private ArrayList<Article> articles;

    public ArticleListModel() {
        articles = new ArrayList<Article>();
    }

    public void append() {
        status = LOADING;
        update();

        new GetArticleList(finalId, NUM_APPEND).execute(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                try {
                    @SuppressWarnings("unchecked Cast")
                    ArrayList<Article> newArticles = (ArrayList<Article>) response.get("articles");
                    int newFinalId = (Integer) response.get("finalId");

                    for (Article article : newArticles) {
                        articles.add(article);
                        Log.i("???", article.getArticleId() + " : " + article.getTestText());
                    }
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


    public void fetch() {
        status = LOADING;
        update();

        articles = new ArrayList<Article>();
        new GetArticleList(isINITIAL, NUM_INIT).execute(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                try {
                    @SuppressWarnings("unchecked Cast")
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
