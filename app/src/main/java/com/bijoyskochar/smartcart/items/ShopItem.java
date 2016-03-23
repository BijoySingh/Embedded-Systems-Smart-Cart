package com.bijoyskochar.smartcart.items;

import com.github.bijoysingh.starter.json.JsonField;
import com.github.bijoysingh.starter.json.JsonModel;

import org.json.JSONObject;

/**
 * Created by BijoySingh on 3/23/2016.
 */
public class ShopItem extends JsonModel {
    @JsonField
    public Integer id;

    @JsonField
    public String name;

    public ShopItem(String response) throws Exception {
        super(response);
    }

    public ShopItem(JSONObject json) throws Exception {
        super(json);
    }
}
