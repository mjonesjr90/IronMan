package com.malcomjones.ironman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.millennialmedia.MMException;
import com.millennialmedia.MMLog;
import com.millennialmedia.MMSDK;
import com.millennialmedia.UserData;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable debug logging
        MMLog.setLogLevel(Log.VERBOSE);

        // Set any known data about the user
        UserData userData = new UserData().setAge(27).setGender(UserData.Gender.MALE);
        try {
            MMSDK.setUserData(userData);
        } catch (MMException e) {
            // This exception is only thrown if MMSDK.initialize was not previously called
            Log.e(TAG, "The MMSDK was not initialized");
        }


        // Create a listview and populate using array_btns and simple_list_item_1
        // Each item launches it's respective Activity
        ListView ad_type_list = (ListView) findViewById(R.id.lv);

        ad_type_list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.array_btns)));

        ad_type_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                switch(pos){
                    case 0: startActivity(new Intent(MainActivity.this, BannerActivity.class)); break;
                    case 1: startActivity(new Intent(MainActivity.this, MRECActivity.class)); break;
                    case 2: startActivity(new Intent(MainActivity.this, InterstitialActivity.class)); break;
                    case 3: startActivity(new Intent(MainActivity.this, NativeActivity.class)); break;
                    case 4: startActivity(new Intent(MainActivity.this, MoPubBannerActivity.class)); break;
                    case 5: startActivity(new Intent(MainActivity.this, DFPBannerActivity.class)); break;
                    case 6: startActivity(new Intent(MainActivity.this, SuperAuctionBannerActivity.class)); break;
                    case 7: startActivity(new Intent(MainActivity.this, SuperAuctionInterstitialActivity.class)); break;
                    case 8: startActivity(new Intent(MainActivity.this, SuperAuctionBannerMoPubActivity.class)); break;
                    case 9: startActivity(new Intent(MainActivity.this, SuperAuctionMRECMoPubActivity.class)); break;
                    case 10: startActivity(new Intent(MainActivity.this, SuperAuctionBannerDFPActivity.class)); break;
                }
            }
        });
    }
}
