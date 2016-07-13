package com.k1.mvprxandroidsample.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by K1 on 7/13/16.
 */
public class Config {

    @SerializedName("filename")
    String fileName;

    @SerializedName("type")
    String type;

    @SerializedName("language")
    String languag;

    @SerializedName("raw_url")
    String rawUrl;

    @SerializedName("size")
    double size;
}
