package com.goznauk.projectnull.app.Network;

import android.util.Log;
import com.goznauk.projectnull.app.Entity.Article;

import java.util.ArrayList;

/**
 * Created by goznauk on 2015. 4. 4..
 */
public class GetArticleList {
    int initialId;
    int num;

    public GetArticleList(int initialId, int num) {
        this.initialId = initialId;
        this.num = num;
    }

    public void execute(OnResponseListener OnResponseListener) {
        Response response = new Response();
        ArrayList<Article> articles = new ArrayList<Article>();

        // TODO : if initialId = -1 -> get latest
        if(initialId == -1) {
            initialId = 1001;
            response.add("initialId", initialId);
        }


        // TODO : get Articles num or lower(if not exists)



        // DUMMY Example
        for(int i = 0; i < num; i++) {
            articles.add(new Article((initialId + i), "ID: " + (initialId + i)));
            Log.i("getting", initialId + "/" + i);
        }

        response.add("finalId", initialId+num);
        response.add("articles", articles);

        OnResponseListener.onResponse(response);
    }
}
