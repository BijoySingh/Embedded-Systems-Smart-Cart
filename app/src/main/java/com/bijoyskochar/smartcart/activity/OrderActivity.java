package com.bijoyskochar.smartcart.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bijoyskochar.smartcart.R;
import com.bijoyskochar.smartcart.adapters.OrderAdapter;
import com.bijoyskochar.smartcart.items.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends ActivityBase {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    OrderAdapter adapter;
    List<OrderItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setupRecyclerView();
        refreshList();
    }

    public void refreshList() {
        items = new ArrayList<>();
        items.add(new OrderItem("Shakti Bhog Aata", 1000, 60.0, 2));
        items.add(new OrderItem("Britania Rusk", 200, 25.0, 1));
        items.add(new OrderItem("Alpenlibe", 20, 10.0, 5));
        items.add(new OrderItem("Tang Orange", 300, 120.0, 1));
        adapter.notifyDataSetChanged();
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
