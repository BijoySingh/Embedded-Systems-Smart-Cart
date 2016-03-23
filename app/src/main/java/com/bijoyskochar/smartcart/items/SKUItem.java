package com.bijoyskochar.smartcart.items;

import com.github.bijoysingh.starter.json.JsonField;
import com.github.bijoysingh.starter.json.JsonModel;

import org.json.JSONObject;

import java.io.Serializable;

/**
     "id": 3,
     "title": "Kurkure Twisters",
     "weight": 35,
     "price": 15,
     "rf_id": 198,
     "picture": null

 * Created by BijoySingh on 3/22/2016.
 */
public class SKUItem extends JsonModel implements Serializable {

    @JsonField
    public Integer id;

    @JsonField
    public String title;

    @JsonField
    public Double weight;

    @JsonField
    public Double price;

    @JsonField
    public String rfid;

    @JsonField
    public String picture;

    public SKUItem(JSONObject json) throws Exception {
        super(json);
    }

    public SKUItem(String response) throws Exception {
        super(response);
    }

}
