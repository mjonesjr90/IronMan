package com.malcomjones.aolsample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

/**
 * Instance of serving a conventional banner ad with DFP
 *
 * https://developers.google.com/mobile-ads-sdk/docs/dfp/android/quick-start
 *
 */
public class DFPBannerActivity extends Activity{

    private static final String TAG = "DFPBannerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_dfp);
        final PublisherAdView adContainer = findViewById(R.id.banner_container_dfp);
        try {

            PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
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

                            Toast.makeText(DFPBannerActivity.this, "Request Failed Called", Toast.LENGTH_LONG).show();
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
