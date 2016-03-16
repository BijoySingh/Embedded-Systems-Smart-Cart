package com.bijoyskochar.smartcart.items;

import com.github.bijoysingh.starter.json.JsonField;
import com.github.bijoysingh.starter.json.JsonModel;

import org.json.JSONObject;

/**
 * Order Json Item
 * Created by BijoySingh on 3/15/2016.
 */
public class OrderItem extends JsonModel {
    @JsonField
    public Integer id;

    @JsonField
    public String title;

    @JsonField
    public Integer weight;

    @JsonField
    public Double price;

    @JsonField
    public String picture;

    @JsonField
    public Integer quantity;

    public OrderItem(JSONObject json) throws Exception {
        super(json);
    }

    public OrderItem(String response) throws Exception {
        super(response);
    }

    public OrderItem(String title, Integer weight, Double price, Integer quantity) {
        this.title = title;
        this.weight = weight;
        this.price = price;
        this.quantity = quantity;
    }

    public String getPrice() {
        return "\u20B9" + price;
    }

    public String getWeight() {
        if (weight > 1000) {
            return (weight / 1.0) + "kg";
        } else {
            return weight + "g";
        }
    }
}
