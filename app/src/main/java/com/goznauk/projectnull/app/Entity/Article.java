package com.goznauk.projectnull.app.Entity;

/**
 * Created by goznauk on 2015. 3. 27..
 */
public class Article {
    private int articleId;
    private String testText;

    public Article(int articleId, String testText) {
        this.articleId = articleId;
        this.testText = testText;
    }


    public String getTestText() {
        return testText;
    }

    public void setTestText(String testText) {
        this.testText = testText;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public static Article getDummy() {
        return new Article(123, "aaa");
    }
}
