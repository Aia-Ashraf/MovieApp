
package com.example.aiaa.movieapp1.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class KoraList implements Parcelable {

    @SerializedName("articles")
    private List<Article> mArticles;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("totalResults")
    private Long mTotalResults;

    public List<Article> getArticles() {
        return mArticles;
    }

    public void setArticles(List<Article> articles) {
        mArticles = articles;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Long getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(Long totalResults) {
        mTotalResults = totalResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.mArticles);
        dest.writeString(this.mStatus);
        dest.writeValue(this.mTotalResults);
    }

    public KoraList() {
    }

    protected KoraList(Parcel in) {
        this.mArticles = new ArrayList<Article>();
        in.readList(this.mArticles, Article.class.getClassLoader());
        this.mStatus = in.readString();
        this.mTotalResults = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<KoraList> CREATOR = new Parcelable.Creator<KoraList>() {
        @Override
        public KoraList createFromParcel(Parcel source) {
            return new KoraList(source);
        }

        @Override
        public KoraList[] newArray(int size) {
            return new KoraList[size];
        }
    };
}
