package com.goznauk.projectnull.app.Network;

import com.goznauk.projectnull.app.Entity.Article;

import java.util.ArrayList;

/**
 * Created by goznauk on 2015. 4. 4..
 */
public class GetArticleDetail {
    int articleId;

    public GetArticleDetail(int articleId) {
        this.articleId = articleId;
    }

    public void execute(OnResponseListener OnResponseListener) {
        Response response = new Response();


        // TODO : get Article by RESTful APIs
        try {
            Article article = new Article(articleId, "TESTTT" + articleId);
            response.add("article", article);
        } catch (Exception e) {
            // if article not exists...
            e.printStackTrace();
            response.add("article", null);
        }

        OnResponseListener.onResponse(response);
    }
}
