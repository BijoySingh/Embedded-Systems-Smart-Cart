package com.bijoyskochar.smartcart.items;

import com.github.bijoysingh.starter.json.JsonField;
import com.github.bijoysingh.starter.json.JsonModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BijoySingh on 3/23/2016.
 */
public class OrderInformation extends JsonModel implements Serializable {
    @JsonField
    public Integer id;

    public ChipItem chip;

    @JsonField
    public Integer cart_weight;

    @JsonField
    public String created;

    public List<OrderItem> items;

    public OrderInformation(JSONObject json) throws Exception {
        super(json);
        chip = new ChipItem(json.getJSONObject("chip"));
        items = new ArrayList<>();
        JSONArray array = json.getJSONArray("items");
        for (Integer position = 0; position < array.length(); position++) {
            OrderItem item = new OrderItem(array.getJSONObject(position));
            items.add(item);
        }
    }
}
