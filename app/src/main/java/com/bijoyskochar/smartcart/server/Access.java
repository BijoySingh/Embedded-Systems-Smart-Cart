package com.bijoyskochar.smartcart.server;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.bijoyskochar.smartcart.activity.OrderActivity;
import com.bijoyskochar.smartcart.items.OrderItem;
import com.github.bijoysingh.starter.server.AccessItem;
import com.github.bijoysingh.starter.server.AccessManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by BijoySingh on 3/22/2016.
 */
public class Access extends AccessManager {

    public Access(Context context) {
        super(context);
    }

    @Override
    public Map<String, String> getAuthenticationData() {
        return null;
    }

    @Override
    public void handleGetResponse(AccessItem accessItem, String s) {
        Log.d(Access.class.getSimpleName(), s);

        try {
            if (accessItem.type.equals(AccessIds.GET_ORDER)
                    && accessItem.activity != null) {
                List<OrderItem> items = new ArrayList<>();
                JSONObject json = new JSONObject(s);
                JSONArray array = json.getJSONArray("items");
                for (Integer position = 0; position < array.length(); position++) {
                    OrderItem item = new OrderItem(array.getJSONObject(position));
                    items.add(item);
                }
                ((OrderActivity) accessItem.activity).refreshList(items);
            }
        } catch (Exception exception) {
            Log.e(Access.class.getSimpleName(), exception.getMessage(), exception);
        }
    }

    @Override
    public void handleSendResponse(AccessItem accessItem, JSONObject jsonObject) {

    }

    @Override
    public void handleGetError(AccessItem accessItem, VolleyError volleyError) {

    }

    @Override
    public void handleSendError(AccessItem accessItem, VolleyError volleyError) {

    }
}
