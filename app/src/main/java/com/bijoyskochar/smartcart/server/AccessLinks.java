package com.bijoyskochar.smartcart.server;

/**
 * Access links
 * Created by BijoySingh on 3/22/2016.
 */
public class AccessLinks {
    public static String getServerUrl() {
        return "http://smartcart.thecodershub.com";
    }

    public static String getCreateOrderLink(String chip) {
        return getServerUrl() + "/order/make_order/" + chip;
    }

    public static String getOrderItems(String order) {
        return getServerUrl() + "/api/order/" + order + "/";
    }

    public static String setOrderCancelled(String order) {
        return getServerUrl() + "/api/order/" + order + "/set_cancelled/";
    }

    public static String setOrderCompleted(String order) {
        return getServerUrl() + "/api/order/" + order + "/set_completed/";
    }

    public static String setChangedItem(Integer item) {
        return getServerUrl() + "/api/item/" + item + "/update_item/";
    }
}
