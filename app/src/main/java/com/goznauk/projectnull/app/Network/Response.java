package com.goznauk.projectnull.app.Network;

import java.util.HashMap;

/**
 * Created by goznauk on 2015. 4. 4..
 */
public class Response {
    private HashMap<String, Object> data;

    public Response() {
        data = new HashMap<String, Object>();
    }

    public void add(String key, Object object) {
        data.put(key, object);
    }

    public Object get(String key) throws Exception {
        try {
            return data.get(key);
        } catch (Exception e) {
            throw new Exception("Response ERROR");
        }
    }
}
