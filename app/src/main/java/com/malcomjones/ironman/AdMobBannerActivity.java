package com.malcomjones.ironman;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Instance of serving a conventional banner ad with AdMob
 *
 * https://developers.google.com/mobile-ads-sdk/docs/dfp/android/quick-start
 *
 */
public class AdMobBannerActivity extends Activity{

    private static final String TAG = "AdMobBannerActivity";
    private static final String ADMOB_ID = "ca-app-pub-8846682542849881~9544092692";
    private static final String TEST_ADMOB_ID = "ca-app-pub-3940256099942544~3347511713";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_admob);

        MobileAds.initialize(this, ADMOB_ID);
        Log.i(TAG, "Initialized AdMob");

        FlurryAgent.logEvent("Request an AdMob Banner");

        final AdView adContainer = findViewById(R.id.banner_container_admob);

        try {

            AdRequest adRequest = new AdRequest.Builder().build();
            adContainer.loadAd(adRequest);

            adContainer.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            adContainer.setVisibility(View.VISIBLE);
                        }
                    });

                    Log.i(TAG, "Inline Ad loaded.");
                }


                @Override
                public void onAdFailedToLoad(int errorCode) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(AdMobBannerActivity.this, "Request Failed Called", Toast.LENGTH_LONG).show();
                        }
                    });
                    finish();
                }


                @Override
                public void onAdOpened()  {

                    Log.i(TAG, "Inline Ad clicked.");
                }

                @Override
                public void onAdClosed() {

                    Log.i(TAG, "Inline Ad closed.");
                }


                @Override
                public void onAdLeftApplication(){

                    Log.i(TAG, "Inline Ad left application.");
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Error creating inline ad", e);
            // abort loading ad
        }
    }
}
