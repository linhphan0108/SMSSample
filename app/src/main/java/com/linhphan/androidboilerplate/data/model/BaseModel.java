package com.linhphan.androidboilerplate.data.model;

import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by linh on 30/03/2016.
 */
public abstract class BaseModel<T> implements IJsonable<T>, Comparator<T>, Parcelable{
    protected int mId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }
}
