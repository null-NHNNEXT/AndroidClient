package com.goznauk.projectnull.app.Network;

import android.util.Log;

import com.google.gson.Gson;
import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Entity.ResponseResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Henry on 2015. 4. 28..
 */
public class GetToken {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private RequestParams params;
    private ResponseResult authResult;
    private String errorCode;

    public GetToken(RequestParams params){
        this.params =  params;
    }

    public void execute(final OnResponseListener onResponseListener) {
        final Response response = new Response();

        // TODO : get Article by RESTful APIs
        try {
            client.post("http://125.209.193.18:8888/api/auth/register", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    //응답으로 토큰을 받아옴
                    String jsonData = new String(responseBody);

                    try {
                        JSONObject jsonObject = new JSONObject(jsonData);
                        String token = jsonObject.getString("result");
                        Log.i("test", "token :" + token);

                        response.add("token", token);
                        onResponseListener.onResponse(response);

                    }catch(Exception e){
                        Log.e("test","json parsing error" + e);
                    }
                    //


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

//                    Log.i("test","Auth Failed");
//                    String jsonData = new String(responseBody);
//                    Gson gson = new Gson();
//
//                    //닉네임이 중복이거나 게시판 코드가 없을 경우에 관한 에러코드
//                    authResult = new Gson().fromJson(jsonData, ResponseResult.class);
//                    response.add("error", authResult.getErrorCode());
//                    onResponseListener.onResponse(response);
                }
            });


        } catch (Exception e) {
            Log.e("error", "Server error");
        }


    }


}
