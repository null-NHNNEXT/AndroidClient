package com.goznauk.projectnull.app.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by goznauk on 2015. 3. 27..
 */
public class Article implements Parcelable{
    private String articleId;
    private String title;
    private String contents;
    private String penName;
    private String timeStamp;
    private String image;
    private ArrayList<Comment> comments;

    public Article(){};

    public Article(String articleId, String title, String contents,
                        String penName, String timeStamp, String image, ArrayList<Comment> comments) {
        this.articleId = articleId;
        this.title = title;
        this.penName = penName;
        this.contents = contents;
        this.timeStamp = timeStamp;
        this.image = image;
        this.comments = comments;
    }
    public Article(Parcel in){
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(articleId);
        dest.writeString(title);
        dest.writeString(penName);
        dest.writeString(contents);
        dest.writeString(timeStamp);
        dest.writeString(image);
        dest.writeTypedList(comments);
    }

    public void readFromParcel(Parcel in){
        comments = new ArrayList<Comment>();
        articleId = in.readString();
        title = in.readString();
        penName = in.readString();
        contents = in.readString();
        timeStamp = in.readString();
        image = in.readString();
        in.readTypedList(comments, Comment.CREATOR);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Object createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Article[size];
        }
    };

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
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
