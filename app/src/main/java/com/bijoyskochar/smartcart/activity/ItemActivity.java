package com.bijoyskochar.smartcart.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bijoyskochar.smartcart.R;
import com.bijoyskochar.smartcart.items.OrderItem;
import com.bijoyskochar.smartcart.server.Access;
import com.bijoyskochar.smartcart.server.AccessIds;
import com.bijoyskochar.smartcart.server.AccessLinks;
import com.github.bijoysingh.starter.server.AccessItem;
import com.github.bijoysingh.starter.util.ImageLoaderManager;
import com.github.bijoysingh.starter.util.LocaleManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemActivity extends AppCompatActivity {

    public static final String ORDER_ITEM = "ORDER_ITEM";

    Context context;

    ImageView picture;
    TextView title;
    TextView price;
    TextView quantity;
    TextView totalPrice;
    TextView weight;

    ImageView decrease;
    ImageView increase;
    TextView done;

    OrderItem data;
    Integer submitQuantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        context = this;

        picture = (ImageView) findViewById(R.id.picture);
        title = (TextView) findViewById(R.id.title);
        price = (TextView) findViewById(R.id.price);
        quantity = (TextView) findViewById(R.id.quantity);
        totalPrice = (TextView) findViewById(R.id.total_price);
        weight = (TextView) findViewById(R.id.weight);
        done = (TextView) findViewById(R.id.done);
        decrease = (ImageView) findViewById(R.id.decrease);
        increase = (ImageView) findViewById(R.id.increase);

        data = (OrderItem) getIntent().getSerializableExtra(ORDER_ITEM);
        submitQuantity = data.quantity;

        setValues();
    }

    public void setValues() {
        title.setText(data.sku.title);
        weight.setText(data.getWeight());
        price.setText(data.getPrice());
        quantity.setText(LocaleManager.toString(data.quantity));

        if (data.sku.picture != null
                && !data.sku.picture.contentEquals("null")
                && !data.sku.picture.isEmpty()) {
            ImageLoaderManager.displayImage(this, AccessLinks.getServerUrl() + data.sku.picture, picture);
        } else {
            picture.setImageResource(R.drawable.placeholder);
        }

        totalPrice.setText("\u20B9" + (data.sku.price * data.quantity) + "/-");

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitQuantity = Math.max(0, submitQuantity - 1);
                quantity.setText(submitQuantity.toString());
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitQuantity = submitQuantity + 1;
                quantity.setText(submitQuantity.toString());
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("quantity", submitQuantity);
                map.put("order", data.order);

                Access access = new Access(context);
                access.send(new AccessItem(AccessLinks.setChangedItem(data.id),
                        null, AccessIds.CHANGE_ITEM, false), map);
                finish();
            }
        });
    }
}
