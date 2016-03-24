package com.bijoyskochar.smartcart.items;

import com.github.bijoysingh.starter.json.JsonField;
import com.github.bijoysingh.starter.json.JsonModel;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by BijoySingh on 3/23/2016.
 */
public class ChipItem extends JsonModel implements Serializable {
    @JsonField
    public Integer id;

    @JsonField
    public String tag;

    public ShopItem shop;

    public ChipItem(JSONObject json) throws Exception {
        super(json);
        shop = new ShopItem(json.getJSONObject("shop"));
    }

}
