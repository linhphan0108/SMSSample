package com.linhphan.androidboilerplate.api.Parser;

import com.linhphan.androidboilerplate.api.BaseDownloadWorker;
import com.linhphan.androidboilerplate.data.model.DumpModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by linhphan on 11/13/15.
 */
public class DumpParser implements IParser {
    @Override
    public Object parse(Object data, BaseDownloadWorker.ResponseCodeHolder responseCode) {
        if (data == null)
            return null;
        ArrayList<DumpModel> resultList = null;

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject((String) data);
            if (jsonObject.has("result")) {
                resultList = new ArrayList<>();
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int i=0; i < jsonArray.length(); i++){
                        JSONObject ob = jsonArray.getJSONObject(i);
                        DumpModel model = new DumpModel();
                        model.setImagePath(ob.getString("image_path"));
                        model.setWebUrl(ob.getString("url"));
                        resultList.add(model);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
