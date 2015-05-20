package com.goznauk.projectnull.app.Model;

import android.content.Context;
import android.util.Log;

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



    public ArticleDetailModel(Context context) {

        this.context = context;
    }

    public void deleteArticle(String articleId) {
//        status = LOADING;
//        update();
//
//        new GetArticleDetail(context, articleId).execute(new OnResponseListener() {
//            @Override
//            public void onResponse(Response response) {
//                try {
//
//                    Article newArticle = (Article) response.get("article");
//
//                    article = newArticle;
//
//                    status = DONE;
//                    update();
//                } catch (Exception e) {
//                    status = ERROR;
//                    update();
//                }
//            }
//        });

        status = LOADING;
        update();

        new DeleteArticle(context, articleId).execute(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                    try{

                       status = DONE;
                        update();

                    }catch(Exception e){
                        status = ERROR;
                        update();

                    }
                }

        });
    }




    public int getStatus() {
        return status;
    }
}
