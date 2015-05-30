package com.goznauk.projectnull.app.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Henry on 2015. 5. 17..
 */
public class Comment implements Parcelable{
    String writer;
    String contents;

    public Comment(String wrtier, String contents){
        super();
        this.writer = wrtier;
        this.contents = contents;
    }
    public Comment(String contents){
        this.contents = contents;
    }
    public Comment(Parcel in){
        super();
        readFromParcel(in);
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(writer);
        dest.writeString(contents);
    }

    public void readFromParcel(Parcel in){
        writer = in.readString();
        contents = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Object createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Comment[size];
        }
    };
}
