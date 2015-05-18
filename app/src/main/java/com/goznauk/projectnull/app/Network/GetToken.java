package com.goznauk.projectnull.app.Network;

import android.util.Log;

import com.google.gson.Gson;
import com.goznauk.projectnull.app.Entity.ResponseResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

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

                    Log.i("test","Auth Succeed");
                    //응답으로 토큰을 받아옴
                    String jsonData = new String(responseBody);

                    Log.i("test", jsonData);
                    authResult = new Gson().fromJson(jsonData, ResponseResult.class);
                    Log.i("test", authResult.getToken());
                    response.add("token", authResult.getToken());
                    onResponseListener.onResponse(response);
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
