package com.goznauk.projectnull.app.Model;

import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Network.*;

import java.util.ArrayList;

/**
 * Created by goznauk on 2015. 3. 28..
 */
public class SingleArticleModel extends BaseModel {
    public static final int LOADING = 0;
    public static final int DONE = 1;
    public static final int ERROR = -1;

    private int status;


    /*
     * New Article : articleId = 0
     * Read Article : articleId > 0
     * Edit Article : articleId < 0
     */
    int articleId;

    private Article article;

    public SingleArticleModel(int articleId) {
        this.articleId = articleId;
    }

    public void submit() {
        status = LOADING;
        update();

        if(article == null) {
            status = ERROR;
            update();
            return;
        }

        new SubmitArticleDetail(article).execute(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                try {
                    @SuppressWarnings("unchecked Cast")
                    String result = (String) response.get("result");

                    if(result.equals("200")) {
                        status = DONE;
                        update();
                    } else if (1!=1) {
                        // if it is not successful result
                        status = ERROR;
                        update();
                    } else {
                        // if it is not expected result
                        status = ERROR;
                        update();
                    }

                } catch (Exception e) {
                    status = ERROR;
                    update();
                }
            }
        });

        new GetArticleDetail(articleId).execute(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                try {
                    @SuppressWarnings("unchecked Cast")
                    Article newArticle = (Article) response.get("article");
                    article = newArticle;

                    status = DONE;
                } catch (Exception e) {
                    status = ERROR;
                }
            }
        });
    }

    public void fetch() {
        status = LOADING;
        update();

        new GetArticleDetail(articleId).execute(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                try {
                    @SuppressWarnings("unchecked Cast")
                    Article newArticle = (Article) response.get("article");

                    article = newArticle;

                    status = DONE;
                    update();
                } catch (Exception e) {
                    status = ERROR;
                    update();
                }
            }
        });
    }

    public Article getArticle() {
        return article;
    }

    public int getStatus() {
        return status;
    }
}
