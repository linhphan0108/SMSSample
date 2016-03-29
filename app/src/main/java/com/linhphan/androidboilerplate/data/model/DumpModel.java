package com.linhphan.androidboilerplate.data.model;

/**
 * Created by linhphan on 11/13/15.
 */
public class DumpModel {
    private String mImagePath;
    private String mWebUrl;

    public DumpModel() {
    }

    public DumpModel(String path, String url) {
        this.mImagePath = path;
        this.mWebUrl = url;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String path) {
        this.mImagePath = path;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public void setWebUrl(String url) {
        this.mWebUrl = url;
    }
}
