package com.goznauk.projectnull.app.Model;

import android.content.Context;

import com.goznauk.projectnull.app.Network.OnResponseListener;
import com.goznauk.projectnull.app.Network.Response;
import com.goznauk.projectnull.app.Network.SubmitArticleDetail;
import com.loopj.android.http.RequestParams;

/**
 * Created by Henry on 2015. 4. 8..
 */
public class ArticleEditModel extends BaseModel {

    public static final int LOADING = 0;
    public static final int DONE = 1;
    public static final int ERROR = -1;

    private Context context;
    private int status;

    public ArticleEditModel(Context context){
        this.context = context;
    }


    //새로운 포스트를 작성할 때의 처리
    public void fetch(final int postStatus, final String title, final String content){
        status = LOADING;
        RequestParams requestParams = new RequestParams();
        requestParams.put("title", title);
        requestParams.put("contents", content);

        //일단 image는 null로 보내두고 나중에 처리
        requestParams.put("image", "null");

        new SubmitArticleDetail(context,postStatus, title, content).executeForNewPost(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                try {



                    status = DONE;
                    update();
                } catch (Exception e) {
                    status = ERROR;

                    update();
                }
            }
        });

    }

    //기존 포스트를 수정할 때의 처리
    public void fetch(int postStatus, String articleId, String title, String content){

        status = LOADING;

        new SubmitArticleDetail(context, postStatus, articleId, title, content).executeFormerPost(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                try {



                    status = DONE;
                    update();
                } catch (Exception e) {
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
