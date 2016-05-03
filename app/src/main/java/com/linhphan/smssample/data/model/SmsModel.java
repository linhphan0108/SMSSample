package com.linhphan.smssample.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.linhphan.androidboilerplate.data.model.BaseModel;

/**
 * Created by linh on 30/03/2016.
 */
public class SmsModel extends BaseModel implements Parcelable{
    private int mLangId;
    private int mCatId;
    private String mContent;
    private String mTranslation;
    private boolean mStared;

    //=========== constructors =====================================================================
    public SmsModel() {
    }

    public SmsModel(SmsModel model) {
        if (model != null) {
            this.mLangId = model.getLangId();
            this.mCatId = model.getCatId();
            this.mContent = model.getContent();
            this.mTranslation = model.getTranslation();
            this.mStared = model.isStared();
        }
    }

    protected SmsModel(Parcel in) {
        mLangId = in.readInt();
        mCatId = in.readInt();
        mContent = in.readString();
        mTranslation = in.readString();
        mStared = in.readByte() != 0;
    }

    //=========== setters and getters ==============================================================
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

    //========= inherited methods ==================================================================
    @Override
    public String objectToJson() {
        return null;
    }

    @Override
    public <T extends BaseModel> T jsonToObject(String json) {
        return null;
    }

    //======= implemented methods ==================================================================

    //=== start parcelable's methods
    public static final Creator<SmsModel> CREATOR = new Creator<SmsModel>() {
        @Override
        public SmsModel createFromParcel(Parcel in) {
            return new SmsModel(in);
        }

        @Override
        public SmsModel[] newArray(int size) {
            return new SmsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mLangId);
        dest.writeInt(mCatId);
        dest.writeString(mContent);
        dest.writeString(mTranslation);
        dest.writeByte((byte) (mStared ? 1 : 0));
    }
    //=== end parcelable's methods
}
