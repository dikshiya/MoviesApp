package com.example.moviesonn.models;

import com.example.moviesonn.Utils.Utils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Trailer implements Serializable {
    private static final String TRAILER_URL_PREFIX = "https://img.youtube.com/vi/%1$s/sddefault.jpg";

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    public String getKeyWithPrefix() {
        if (Utils.isnotNullorEmpty(key)) return String.format(TRAILER_URL_PREFIX, key);
        else return key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
