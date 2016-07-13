package com.k1.mvprxandroidsample.model;

import com.google.gson.annotations.SerializedName;

/**
 * Simple Model class that represents data layer
 * in MVP Android Architecture
 * Created by K1 on 7/12/16.
 */
public class Gist {
    @SerializedName("url")
    String url;

    @SerializedName("forks_url")
    String forksUrl;

    @SerializedName("commits_url")
    String commitsUrl;

    @SerializedName("id")
    String id;

    @SerializedName("git_pull_url")
    String pullUrl;

    @SerializedName("git_push_url")
    String pushUrl;

    @SerializedName("html_url")
    String htmlUrl;

    @SerializedName("public")
    Boolean isPublic;

    @Override
    public String toString() {
        return "Gist{" +
                "url='" + url + '\'' +
                ", forksUrl='" + forksUrl + '\'' +
                ", commitsUrl='" + commitsUrl + '\'' +
                ", id='" + id + '\'' +
                ", pullUrl='" + pullUrl + '\'' +
                ", pushUrl='" + pushUrl + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }
}
