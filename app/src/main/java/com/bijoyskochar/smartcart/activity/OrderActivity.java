package com.bijoyskochar.smartcart.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bijoyskochar.smartcart.R;
import com.bijoyskochar.smartcart.adapters.OrderAdapter;
import com.bijoyskochar.smartcart.items.OrderInformation;
import com.bijoyskochar.smartcart.items.OrderItem;
import com.bijoyskochar.smartcart.server.Access;
import com.bijoyskochar.smartcart.server.AccessIds;
import com.bijoyskochar.smartcart.server.AccessLinks;
import com.github.bijoysingh.starter.server.AccessItem;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends ActivityBase {

    public static final String ORDER_ID = "ORDER_ID";
    public static final Integer TIME_REPEAT = 2000;

    Boolean paused = false;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    OrderAdapter adapter;
    String order;
    TextView totalPrice;
    OrderInformation orderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        order = getIntent().getStringExtra(ORDER_ID);

        totalPrice = (TextView) findViewById(R.id.total_price);
        setupRecyclerView();
    }

    public void refreshList(OrderInformation order) {
        orderInfo = order;
        adapter.notifyDataSetChanged();

        Double price = 0.0;
        for (OrderItem item : orderInfo.items) {
            price += item.sku.price * item.quantity;
        }
        totalPrice.setText("\u20B9" + price);
    }

    public void setupHandler() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!paused) {
                    requestItem();
                    handler.postDelayed(this, TIME_REPEAT);
                }
            }
        });
    }

    public void requestItem() {
        Access access = new Access(context);
        access.get(new AccessItem(AccessLinks.getOrderItems(order),
                null, AccessIds.GET_ORDER, false).setActivity(this));
    }

    public List<OrderItem> getValues() {
        if (orderInfo != null && orderInfo.items != null) {
            return orderInfo.items;
        }

        return new ArrayList<>();
    }

    public void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new OrderAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        paused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        paused = false;
        setupHandler();
    }
}
