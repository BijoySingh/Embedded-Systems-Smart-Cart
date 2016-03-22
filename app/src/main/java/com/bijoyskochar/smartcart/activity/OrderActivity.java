package com.bijoyskochar.smartcart.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bijoyskochar.smartcart.R;
import com.bijoyskochar.smartcart.adapters.OrderAdapter;
import com.bijoyskochar.smartcart.items.OrderItem;
import com.bijoyskochar.smartcart.server.Access;
import com.bijoyskochar.smartcart.server.AccessIds;
import com.bijoyskochar.smartcart.server.AccessLinks;
import com.github.bijoysingh.starter.server.AccessItem;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends ActivityBase {

    public static final String ORDER_ID = "ORDER_ID";
    public static final Integer TIME_REPEAT = 5000;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    OrderAdapter adapter;
    List<OrderItem> items;
    String order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        order = getIntent().getStringExtra(ORDER_ID);

        setupRecyclerView();
        refreshList(new ArrayList<OrderItem>());
        setupHandler();
    }

    public void refreshList(List<OrderItem> order) {
        items = new ArrayList<>();
        items.addAll(order);
        adapter.notifyDataSetChanged();
    }

    public void setupHandler() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                requestItem();
                handler.postDelayed(this, TIME_REPEAT);
            }
        });
    }

    public void requestItem() {
        Access access = new Access(context);
        access.get(new AccessItem(AccessLinks.getOrderItems(order),
                null, AccessIds.GET_ORDER, false).setActivity(this));
    }

    public List<OrderItem> getValues() {
        return items;
    }

    public void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new OrderAdapter(this);
        recyclerView.setAdapter(adapter);
    }

}
