package com.linhphan.smssample.data.model;

import com.linhphan.androidboilerplate.data.model.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * this class is used to wrap an instance of {@link MessageModel}
 * besides, it includes some fields more for sending an message.
 * such as id, destination phone number, etc,..
 * Created by linh on 13/04/2016.
 */
public class MessageWrapper extends MessageModel implements Serializable{

    private static final long serialVersionUID = 99L;

    private long mDue;//==
    private String mError;//== log any error during sending message
    private String mDestinationPhoneNumber;
    private int mPartsSize;//== the message content will be divided to multiple parts
    private int mSentTrack;
    private int mDeliveredTrack;

    //========== constructors ======================================================================
    public MessageWrapper() {
        super();
    }

    public MessageWrapper(MessageModel model) {
        this.setId(model.getId());
        this.setCatId(model.getCatId());
        this.setLangId(model.getLangId());
        this.setContent(model.getContent());
        this.setTranslation(model.getContent());
        this.setStared(model.isStared());
    }

    //========== setters and getters  ==============================================================
    public String getError() {
        return mError;
    }

    public void setError(String error) {
        this.mError = error;
    }

    public int getPartsSize() {
        return mPartsSize;
    }

    public void setPartsSize(int partsSize) {
        this.mPartsSize = partsSize;
        mSentTrack = partsSize;
        mDeliveredTrack = partsSize;
    }

    public String getDestinationPhoneNumber() {
        return mDestinationPhoneNumber;
    }

    public void setDestinationPhoneNumber(String destinationPhoneNumber) {
        this.mDestinationPhoneNumber = destinationPhoneNumber;
    }

    public long getDue() {
        return mDue;
    }

    public void setDue(long due) {
        this.mDue = due;
    }

    public int getSentTrack() {
        return mSentTrack;
    }

    public int getDeliveredTrack() {
        return mDeliveredTrack;
    }

    public void decreaseSentTrack(){
        mSentTrack--;
    }

    public void decreaseDeliveredTrack(){
        mDeliveredTrack--;
    }

    /**
     * the session id is compounded from due time and the phone number of destination.
     */
    public String getSessionId(){
        return String.valueOf(mDue) + "_" + mDestinationPhoneNumber;
    }

    @Override
    public String objectToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mId", mId);
            jsonObject.put("mCatId", getCatId());
            jsonObject.put("mLangId", getLangId());
            jsonObject.put("mContent", getContent());
            jsonObject.put("mTranslation", getTranslation());
            jsonObject.put("mStared", isStared());
            jsonObject.put("mDue", mDue);
            jsonObject.put("mError", mError);
            jsonObject.put("mDestinationPhoneNumber", mDestinationPhoneNumber);
            jsonObject.put("mPartsSize", mPartsSize);
            jsonObject.put("mSsentTrack", mSentTrack);
            jsonObject.put("deliveredTrack", mDeliveredTrack);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseModel> T jsonToObject(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            this.setId(jsonObject.optInt("mId"));
            this.setCatId(jsonObject.optInt("mCatId"));
            this.setLangId(jsonObject.optInt("mLangId"));
            this.setContent(jsonObject.optString("mContent"));
            this.setTranslation(jsonObject.optString("mTranslation"));
            this.setStared(jsonObject.optBoolean("mStared", false));
            this.setDue(jsonObject.optLong("mDue"));
            this.setError(jsonObject.optString("mError"));
            this.setDestinationPhoneNumber(jsonObject.optString("mDestinationPhoneNumber"));
            this.setPartsSize(jsonObject.optInt("mPartsSize"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (T) this;
    }
}
