package com.bijoyskochar.smartcart.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bijoyskochar.smartcart.R;

/**
 * Base Activity
 * Created by BijoySingh on 3/15/2016.
 */
public class ActivityBase extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }
}
