package com.bijoyskochar.smartcart.adapters;

import android.app.Activity;
import android.content.Context;

import com.bijoyskochar.smartcart.R;
import com.bijoyskochar.smartcart.activity.OrderActivity;
import com.bijoyskochar.smartcart.items.OrderItem;
import com.bijoyskochar.smartcart.views.OrderItemView;
import com.github.bijoysingh.starter.recyclerview.RVAdapter;

import java.util.List;

/**
 * Order Adapter
 * Created by BijoySingh on 3/16/2016.
 */
public class OrderAdapter extends RVAdapter<OrderItem, OrderItemView> {

    OrderActivity activity;
    public OrderAdapter(OrderActivity activity) {
        super(activity, R.layout.item_view, OrderItemView.class);
        this.activity = activity;
    }

    @Override
    public List<OrderItem> getValues() {
        return activity.getValues();
    }
}
