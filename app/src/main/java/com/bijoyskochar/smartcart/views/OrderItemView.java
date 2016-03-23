package com.bijoyskochar.smartcart.views;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bijoyskochar.smartcart.R;
import com.bijoyskochar.smartcart.activity.ItemActivity;
import com.bijoyskochar.smartcart.items.OrderItem;
import com.bijoyskochar.smartcart.server.AccessLinks;
import com.github.bijoysingh.starter.recyclerview.RVHolder;
import com.github.bijoysingh.starter.util.ImageLoaderManager;
import com.github.bijoysingh.starter.util.LocaleManager;

/**
 * Order Item View
 * Created by BijoySingh on 3/15/2016.
 */
public class OrderItemView extends RVHolder<OrderItem> {

    TextView title;
    TextView weight;
    TextView price;
    EditText quantity;
    ImageView thumbnail;

    public OrderItemView(Context context, View itemView) {
        super(context, itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        weight = (TextView) itemView.findViewById(R.id.weight);
        price = (TextView) itemView.findViewById(R.id.price);
        quantity = (EditText) itemView.findViewById(R.id.quantity);
        thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
    }

    @Override
    public void populate(final OrderItem data) {
        super.populate(data);

        title.setText(data.sku.title);
        weight.setText(data.getWeight());
        price.setText(data.getPrice());
        quantity.setText(LocaleManager.toString(data.quantity));

        if (data.sku.picture != null
                && !data.sku.picture.contentEquals("null")
                && !data.sku.picture.isEmpty()) {
            ImageLoaderManager.displayImage(context, AccessLinks.getServerUrl() + data.sku.picture, thumbnail);
        } else {
            thumbnail.setImageResource(R.drawable.placeholder);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemActivity.class);
                intent.putExtra(ItemActivity.ORDER_ITEM, data);
                context.startActivity(intent);
            }
        });
    }
}
