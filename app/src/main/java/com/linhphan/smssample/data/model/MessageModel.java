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

    public MessageModel() {
    }

    public MessageModel(MessageModel model) {
        if (model != null) {
            this.mLangId = model.getLangId();
            this.mCatId = model.getCatId();
            this.mContent = model.getContent();
            this.mTranslation = model.getTranslation();
            this.mStared = model.isStared();
        }
    }

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

    @Override
    public String objectToJson() {
        return null;
    }

    @Override
    public <T extends BaseModel> T jsonToObject(String json) {
        return null;
    }
}
