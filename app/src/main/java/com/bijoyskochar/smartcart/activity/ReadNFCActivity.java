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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bijoyskochar.smartcart.R;
import com.bijoyskochar.smartcart.server.Access;
import com.bijoyskochar.smartcart.server.AccessIds;
import com.bijoyskochar.smartcart.server.AccessLinks;
import com.bijoyskochar.smartcart.utils.Preferences;
import com.github.bijoysingh.starter.server.AccessItem;
import com.github.bijoysingh.starter.util.ImageLoaderManager;
import com.github.bijoysingh.starter.util.ResourceManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReadNFCActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    Context context;
    NfcAdapter nfcAdapter;
    TextView textViewInfo;
    PendingIntent pendingIntent;
    IntentFilter[] intentFiltersArray;
    String[][] techListsArray;
    TextView logOut;

    TextView googleName;
    CircleImageView googleProfile;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_nfc);
        context = this;

        Preferences preferences = new Preferences(context);

        if (!preferences.isLoggedIn()) {
            loginActivity();
            return;
        }

        textViewInfo = (TextView) findViewById(R.id.info);
        googleName = (TextView) findViewById(R.id.google_name);
        logOut = (TextView) findViewById(R.id.log_out);
        googleProfile = (CircleImageView) findViewById(R.id.google_profile);

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

        googleName.setText(preferences.getName());
        ImageLoaderManager.displayImage(context, preferences.getPicture(), googleProfile);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {

                            @Override
                            public void onResult(Status status) {
                                new Preferences(context).logout();
                                loginActivity();
                            }
                        });
            }
        });
    }

    public void loginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        finish();
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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

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

    public void invalidChip() {
        textViewInfo.setBackgroundColor(ResourceManager.getColor(this, R.color.secondaryText));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }
}
