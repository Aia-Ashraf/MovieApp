
package com.example.aiaa.movieapp1.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ArticleTable")
public class Article implements Parcelable {

    @SerializedName("author")
    private String mAuthor;
    @SerializedName("content")
    private String mContent;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("publishedAt")
    private String mPublishedAt;
//    @SerializedName("source")
//    private Source mSource;

    @SerializedName("title")
    @Expose
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    private String mTitle;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("urlToImage")
    private String mUrlToImage;

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        mPublishedAt = publishedAt;
    }

//    public Source getSource() {
//        return mSource;
//    }
//
//    public void setSource(Source source) {
//        mSource = source;
//    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        mUrlToImage = urlToImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mAuthor);
        dest.writeString(this.mContent);
        dest.writeString(this.mDescription);
        dest.writeString(this.mPublishedAt);
//        dest.writeParcelable((Parcelable) this.mSource, flags);
        dest.writeString(this.mTitle);
        dest.writeString(this.mUrl);
        dest.writeString(this.mUrlToImage);
    }

    public Article() {
    }

    protected Article(Parcel in) {
        this.mAuthor = in.readString();
        this.mContent = in.readString();
        this.mDescription = in.readString();
        this.mPublishedAt = in.readString();
//        this.mSource = in.readParcelable(Source.class.getClassLoader());
        this.mTitle = in.readString();
        this.mUrl = in.readString();
        this.mUrlToImage = in.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
