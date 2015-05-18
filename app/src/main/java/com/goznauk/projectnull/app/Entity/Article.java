package com.goznauk.projectnull.app.Entity;

import java.util.ArrayList;

/**
 * Created by goznauk on 2015. 3. 27..
 */
public class Article {
    private String articleId;
    private String title;
    private String contents;
    private String penName;
    private String timeStamp;
    private String image;
    private ArrayList<Comment> comments;

    public Article(){};

    public Article(String articleId, String title, String contents,
                        String penName, String timeStamp, String image) {
        this.articleId = articleId;
        this.title = title;
        this.penName = penName;
        this.contents = contents;
        this.timeStamp = timeStamp;
        this.image = image;
    }


    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
