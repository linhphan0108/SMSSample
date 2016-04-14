package com.linhphan.androidboilerplate.data.model;

/**
 * Created by linh on 30/03/2016.
 */
public abstract class BaseModel {
    protected int mId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public abstract String objectToJson();
    public abstract <T extends BaseModel> T jsonToObject(String json);

}
