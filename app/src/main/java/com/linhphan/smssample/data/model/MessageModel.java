package com.linhphan.smssample.data.model;

import com.linhphan.androidboilerplate.data.model.BaseModel;

/**
 * Created by linh on 30/03/2016.
 */
public class MessageModel extends BaseModel {
    private int mLangId;
    private int mCatId;
    private String mContent;
    private String mTranslation;
    private boolean mStared;

    public int getLangId() {
        return mLangId;
    }

    public void setLangId(int langId) {
        this.mLangId = langId;
    }

    public int getCatId() {
        return mCatId;
    }

    public void setCatId(int catId) {
        this.mCatId = catId;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public String getTranslation() {
        return mTranslation;
    }

    public void setTranslation(String translation) {
        this.mTranslation = translation;
    }

    public boolean isStared() {
        return mStared;
    }

    public void setStared(boolean stared) {
        this.mStared = stared;
    }
}
