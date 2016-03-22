package com.bijoyskochar.smartcart.items;

import com.github.bijoysingh.starter.json.JsonField;
import com.github.bijoysingh.starter.json.JsonModel;

import org.json.JSONObject;

/**
 * Order Json Item

     "id": 2,
     "sku": {
         "id": 3,
         "title": "Kurkure Twisters",
         "weight": 35,
         "price": 15,
         "rf_id": 198,
         "picture": null
     },
     "quantity": 7,
     "order": 1

 * Created by BijoySingh on 3/15/2016.
 */
public class OrderItem extends JsonModel {

    @JsonField
    public Integer id;

    @JsonField
    public Integer quantity;

    public SKUItem sku;

    public OrderItem(JSONObject json) throws Exception {
        super(json);
        sku = new SKUItem(json.getJSONObject("sku"));
    }

    public String getPrice() {
        return "\u20B9" + sku.price;
    }

    public String getWeight() {
        if (sku.weight > 1000) {
            return (sku.weight / 1.0) + "kg";
        } else {
            return sku.weight + "g";
        }
    }
}