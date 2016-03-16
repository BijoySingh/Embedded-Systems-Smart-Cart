package com.bijoyskochar.smartcart.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.bijoyskochar.smartcart.R;

public class LoginActivity extends ActivityBase {

    View enter;
    EditText pinCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enter = findViewById(R.id.enter);
        pinCode = (EditText) findViewById(R.id.pin_code);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}