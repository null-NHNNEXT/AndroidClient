package com.goznauk.projectnull.app.Entity;

/**
 * Created by goznauk on 2015. 3. 27..
 */
public class Article {
    private int articleId;
    private String title;
    private String content;
    private Writer writer;
    private String timeStamp;
    private String imageId;
    private String contentsDigest;

    public Article(){};
    public Article(int articleId, String title, String contentsDigest,
                        Writer writer, String timeStamp, String imageId) {
        this.articleId = articleId;
        this.title = title;
        this.writer = writer;
        this.contentsDigest = contentsDigest;
        this.timeStamp = timeStamp;
        this.imageId = imageId;
    }



    //getter & setter
    public int getArticleId() {
        return articleId;
    }
    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContents(String contents) {
        this.content = contents;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public static Article getDummy() {
        return new Article(123, "title","content",new Writer(),"timeStamp","photoId");
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentsDigest() {
        return contentsDigest;
    }

    public void setContentsDigest(String contentsDigest) {
        this.contentsDigest = contentsDigest;
    }
}
