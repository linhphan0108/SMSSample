package com.linhphan.smssample.data.model;

import com.linhphan.androidboilerplate.data.model.BaseModel;

/**
 * Created by linh on 30/03/2016.
 */
public class CatAndLangModel extends BaseModel {
    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    @Override
    public String toString() {
        return "id: "+ String.valueOf(mId) + ", name: "+ mName;
    }
}
