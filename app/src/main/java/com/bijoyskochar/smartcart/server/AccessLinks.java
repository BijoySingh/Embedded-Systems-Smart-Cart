package com.bijoyskochar.smartcart.server;

/**
 * Access links
 * Created by BijoySingh on 3/22/2016.
 */
public class AccessLinks {
    public static String getServerUrl() {
        return "http://smartcart.thecodershub.com/";
    }

    public static String getOrderItems(String order) {
        return getServerUrl() + "api/order/" + order + "/get_items/";
    }
}
