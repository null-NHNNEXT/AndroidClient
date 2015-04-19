package com.goznauk.projectnull.app.Entity;

/**
 * Created by Henry on 2015. 4. 17..
 */
public class Writer {
    private int id;
    private String nickname;

    public Writer(int id, String nickname){
        this.id = id;
        this.nickname = nickname;
    }
    public Writer(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
