package com.bijoyskochar.smartcart.server;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.bijoyskochar.smartcart.activity.OrderActivity;
import com.bijoyskochar.smartcart.activity.ReadNFCActivity;
import com.bijoyskochar.smartcart.items.OrderInformation;
import com.github.bijoysingh.starter.Functions;
import com.github.bijoysingh.starter.server.AccessItem;
import com.github.bijoysingh.starter.server.AccessManager;

import org.json.JSONObject;

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
                OrderInformation order = new OrderInformation(new JSONObject(s));
                ((OrderActivity) accessItem.activity).refreshList(order);
            } else if (accessItem.type.equals(AccessIds.ADD_ORDER)
                    && accessItem.activity != null) {
                JSONObject json = new JSONObject(s);
                Integer order = json.getInt("order");

                if (order != -1) {
                    ((ReadNFCActivity) accessItem.activity).startNextActivity(order);
                } else {
                    ((ReadNFCActivity) accessItem.activity).invalidChip();
                }
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
