package com.goznauk.projectnull.app.Model;

import android.content.Context;

import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Network.*;


public class ArticleDetailModel extends BaseModel {
    public static final int LOADING = 0;
    public static final int DONE = 1;
    public static final int ERROR = -1;

    private Context context;
    private int status;


    /*
     * New Article : articleId = 0
     * Read Article : articleId > 0
     * Edit Article : articleId < 0
     */
    int articleId;

    private Article article;

    public ArticleDetailModel(Context context, int articleId) {
        this.articleId = articleId;
        this.context = context;
    }

    public void fetch() {
        status = LOADING;
        update();

        new GetArticleDetail(context, articleId).execute(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                try {

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
