package com.goznauk.projectnull.app.Model;

import android.content.Context;
import android.util.Log;

import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Network.GetArticleList;
import com.goznauk.projectnull.app.Network.GetProfileImage;
import com.goznauk.projectnull.app.Network.OnResponseListener;
import com.goznauk.projectnull.app.Network.PostProfileImage;
import com.goznauk.projectnull.app.Network.Response;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Henry on 2015. 4. 10..
 */
public class SettingModel extends BaseModel{

    private Context context;

    public static final int LOADING = 0;
    public static final int DONE = 1;
    public static final int ERROR = -1;

    private int status;

    private String imgPath = "";
    private File profilePhoto;

    public SettingModel(Context context){
        this.context = context;
    }
    public void fetch() {
        status = LOADING;
        update();

        new GetProfileImage(context).execute(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                Log.i("lll", "(get)onResponse");
                try {
                    profilePhoto = (File) response.get("file");

                    status = DONE;
                    update();
                } catch (Exception e) {
                    status = ERROR;
                    update();
                }
            }
        });
    }

    public void upload(String filePath){
        status = LOADING;
        update();
        new PostProfileImage(context, filePath).execute(new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                Log.i("lll", "(post)onResponse");
                try {
                    new GetProfileImage(context).execute(new OnResponseListener() {
                        @Override
                        public void onResponse(Response response) {
                            Log.i("lll", "(get)onResponse");
                            try {
                            //    profilePhoto = (File) response.get("file");
                                status = DONE;
                                update();
                            } catch (Exception e) {
                                status = ERROR;
                                update();
                            }
                        }
                    });
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


    public File getProfilePhoto() { return profilePhoto; }
}
