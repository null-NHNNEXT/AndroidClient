package com.goznauk.projectnull.app.Network;

import com.goznauk.projectnull.app.Entity.Article;

/**
 * Created by goznauk on 2015. 4. 4..
 */
public class SubmitArticleDetail {
    Article article;

    // if articleId = 0, new article
    public SubmitArticleDetail(Article article) {
        this.article = article;
    }

    public void execute(OnResponseListener OnResponseListener) {
        Response response = new Response();

        if(article.getArticleId() == 0) {
            // Write Mode
        } else {
            // Edit Mode
        }

        // TODO : get Article by RESTful APIs
        try {

            response.add("result", "200");
        } catch (Exception e) {
            // if Error occurs
            e.printStackTrace();
            response.add("result", "404...");
        }

        OnResponseListener.onResponse(response);
    }
}
