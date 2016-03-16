package com.bijoyskochar.smartcart.views;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bijoyskochar.smartcart.R;
import com.bijoyskochar.smartcart.items.OrderItem;
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
    public void populate(OrderItem data) {
        super.populate(data);

        title.setText(data.title);
        weight.setText(data.getWeight());
        price.setText(data.getPrice());
        quantity.setText(LocaleManager.toString(data.quantity));

        if (!data.picture.isEmpty()) {
            ImageLoaderManager.displayImage(context, data.picture, thumbnail);
        }
    }
}
