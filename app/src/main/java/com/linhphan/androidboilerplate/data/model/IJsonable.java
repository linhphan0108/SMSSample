package com.linhphan.androidboilerplate.data.model;

/**
 * Created by linh on 5/5/2016.
 */
public interface IJsonable<T> {
    /**
     * parse a json string to an object
     * @param jsonString to be parsed
     * @return a instance of T
     */
    public T fromJsonString(String jsonString);

    /**
     * parse a instance of T to a json string
     * @return a json string.
     */
    public String toJsonString();
}
