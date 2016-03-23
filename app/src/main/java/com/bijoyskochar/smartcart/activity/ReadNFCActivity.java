package com.bijoyskochar.smartcart.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.bijoyskochar.smartcart.R;
import com.bijoyskochar.smartcart.server.Access;
import com.bijoyskochar.smartcart.server.AccessIds;
import com.bijoyskochar.smartcart.server.AccessLinks;
import com.github.bijoysingh.starter.server.AccessItem;
import com.github.bijoysingh.starter.util.ResourceManager;

public class ReadNFCActivity extends AppCompatActivity {
    Context context;
    NfcAdapter nfcAdapter;
    TextView textViewInfo;
    PendingIntent pendingIntent;
    IntentFilter[] intentFiltersArray;
    String[][] techListsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_nfc);
        context = this;
        textViewInfo = (TextView) findViewById(R.id.info);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
            finish();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this,
                    "NFC NOT Enabled!",
                    Toast.LENGTH_LONG).show();
            finish();
        }

        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter tag = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        intentFiltersArray = new IntentFilter[]{tag,};
        techListsArray = new String[][]{new String[]{
                NfcV.class.getName(),
                NfcF.class.getName(),
                NfcA.class.getName(),
                NfcB.class.getName()}};
    }

    public void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    public void onResume() {
        super.onResume();

        Intent intent = getIntent();
        onNewIntent(intent);

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
    }

    public void onNewIntent(Intent intent) {
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag == null) {
                textViewInfo.setText("INVALID TAG");
            } else {
                String tagInfo = "";
                byte[] tagId = tag.getId();
                for (int i = 0; i < tagId.length; i++) {
                    tagInfo += Integer.toHexString(tagId[i] & 0xFF);
                }
                textViewInfo.setText(tagInfo);
                sendTagToServer(tagInfo.trim().toUpperCase());
            }
        }
    }

    public void sendTagToServer(String tagId) {
        Access access = new Access(context);
        access.get(new AccessItem(AccessLinks.getCreateOrderLink(tagId), null,
                AccessIds.ADD_ORDER, false).setActivity(this));
    }

    public void startNextActivity(final Integer orderId) {
        textViewInfo.setBackgroundColor(ResourceManager.getColor(this, R.color.acceptColor));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra(OrderActivity.ORDER_ID, orderId.toString());
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
