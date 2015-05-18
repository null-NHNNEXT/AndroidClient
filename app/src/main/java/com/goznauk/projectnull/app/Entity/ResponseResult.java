package com.goznauk.projectnull.app.Entity;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Henry on 2015. 5. 10..
 */
public class ResponseResult {
    @SerializedName("result") public String result;
    @SerializedName("error") public String error;

    public String getToken(){
        return result;
    }

    public String getErrorCode(){
        return error;
    }
}
