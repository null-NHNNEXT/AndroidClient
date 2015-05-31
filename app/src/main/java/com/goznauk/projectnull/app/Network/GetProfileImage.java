package com.goznauk.projectnull.app.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.goznauk.projectnull.app.Entity.ResponseResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by Henry on 2015. 5. 31..
 */
public class GetProfileImage {
    private final Context context;
    private static AsyncHttpClient client = new AsyncHttpClient();

    public HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return false;
        }
    }

    public GetProfileImage(Context context){
        this.context = context;
    }
    public void execute(final OnResponseListener onResponseListener) {
        final Response response = new Response();

        SharedPreferences pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        final String fileName = pref.getString("UUID","nothing");
        final File filePath = new File(context.getFilesDir().getPath() + "/" + fileName);


        // TODO : get Article by RESTful APIs
        try {
            String token = pref.getString("token","nothing");
            client.addHeader("Authorization", token);
            client.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");

            client.get("http://125.209.193.18/image/profile/" + fileName, new FileAsyncHttpResponseHandler(context) {
                @Override
                public void onSuccess(int statusCode, Header[] headers, File file) {
                    file.renameTo(filePath);
                    Log.i("test", "이미지 가져왔음");
         //           response.add("file", file);
                    onResponseListener.onResponse(response);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                    Log.i("test", "못가져옴");
                    Log.w("onfail", ""+statusCode+"/"+headers);
                    onResponseListener.onResponse(response);

                }
            });



        } catch (Exception e) {
            Log.e("error", "Server error");
        }


    }


}
