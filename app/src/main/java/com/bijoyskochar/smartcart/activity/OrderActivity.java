package com.bijoyskochar.smartcart.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bijoyskochar.smartcart.R;
import com.bijoyskochar.smartcart.adapters.OrderAdapter;
import com.bijoyskochar.smartcart.items.OrderInformation;
import com.bijoyskochar.smartcart.items.OrderItem;
import com.bijoyskochar.smartcart.server.Access;
import com.bijoyskochar.smartcart.server.AccessIds;
import com.bijoyskochar.smartcart.server.AccessLinks;
import com.github.bijoysingh.starter.Functions;
import com.github.bijoysingh.starter.server.AccessItem;
import com.github.bijoysingh.starter.util.TimestampManager;

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
    TextView store;
    TextView created;
    TextView notifications;
    OrderInformation orderInfo;
    ImageView moneyLimit;

    Integer setMoneyLimit = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        order = getIntent().getStringExtra(ORDER_ID);

        totalPrice = (TextView) findViewById(R.id.total_price);
        store = (TextView) findViewById(R.id.store);
        created = (TextView) findViewById(R.id.created);
        moneyLimit = (ImageView) findViewById(R.id.money_filter);
        notifications = (TextView) findViewById(R.id.notifications);

        moneyLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneyFilter();
            }
        });

        setupRecyclerView();
    }

    public void updateNotifications() {
        notifications.setVisibility(View.GONE);
        String notification = "";
        Double weight = 0.0;
        for (OrderItem item : orderInfo.items) {
            weight += item.sku.weight * item.quantity;
        }
        Double cartWeight = orderInfo.cart_weight;
        Double topLimit = 1.2 * weight;
        Double bottomLimit = 0.8 * weight;
        if (weight > 0.001 && cartWeight > topLimit) {
            notifications.setVisibility(View.VISIBLE);
            notification += "MORE CONTENT IN YOUR CART. ";
        } else if (weight > 0.001 && cartWeight < bottomLimit) {
            notifications.setVisibility(View.VISIBLE);
            notification += "LESS CONTENT IN YOUR CART. ";
        }

        Double price = 0.0;
        for (OrderItem item : orderInfo.items) {
            price += item.sku.price * item.quantity;
        }

        if (price > setMoneyLimit) {
            notifications.setVisibility(View.VISIBLE);
            notification += "YOU CROSSED YOUR MONEY LIMIT. ";
        }

        notifications.setText(notification);
    }

    public void moneyFilter() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Set Limit");
        alertDialog.setMessage("Would you like to set a warning limit for your order?");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        alertDialog.setView(dialogView);

        final EditText input = (EditText) dialogView.findViewById(R.id.price);
        input.setText("" + setMoneyLimit);

        alertDialog.setPositiveButton("SET",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setMoneyLimit = Integer.parseInt(input.getText().toString());
                    }
                });

        alertDialog.setNegativeButton("ADD",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    public void refreshList(OrderInformation order) {
        orderInfo = order;
        adapter.notifyDataSetChanged();

        Double price = 0.0;
        for (OrderItem item : orderInfo.items) {
            price += item.sku.price * item.quantity;
        }
        totalPrice.setText("\u20B9" + price);
        store.setText(orderInfo.chip.shop.name);
        created.setText("Last Updated " + TimestampManager.getTimestampItem(order.created).getTimeString(true));
        updateNotifications();
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
    public void onBackPressed() {
        new AlertDialog.Builder(context)
                .setTitle("Quit Order?")
                .setMessage("Are you sure you would like to leave this order?")
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Access access = new Access(context);
                        access.get(new AccessItem(AccessLinks.setOrderCancelled(order),
                                null, AccessIds.SET_CANCELLED, false));
                        finish();
                    }
                })
                .show();
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
