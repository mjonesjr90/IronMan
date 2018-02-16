package com.malcomjones.aolsample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.millennialmedia.BidRequestErrorStatus;
import com.millennialmedia.BidRequestListener;
import com.millennialmedia.InlineAd;

/**
 * This activity makes a super auction banner requestBid call to the ONE Mobile platform
 *
 * It passes this bid to MoPub as a keyword upon recieving a bid
 *
 * If not bid is returned, a conventional MoPub call is made
 */

public class SuperAuctionBannerDFPActivity extends Activity{

    private static final String TAG = "SADFPBannerActivity";
    private static final String PLACEMENT_ID = "banner_homescreen_sa";
    private PublisherAdView adContainer;
    private PublisherAdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_super_auction_dfp);
        adContainer = findViewById(R.id.banner_container_dfp);

        try {

            Log.v(TAG, "REQUESTING BID");
            InlineAd.InlineAdMetadata inlineMetadata = new InlineAd.InlineAdMetadata();
            inlineMetadata.setAdSize(InlineAd.AdSize.BANNER);
            InlineAd.requestBid(PLACEMENT_ID, inlineMetadata, new BidRequestListener() {
                @Override
                public void onRequestSucceeded(String bidPrice) {
                    // set the bid price as a keyword on the 3rd party SDK ad request
//                        hasBid = true;
                    adRequest = new PublisherAdRequest.Builder()
                            .addCustomTargeting("sa", bidPrice)
                            .build();
                    // DFP LoadAd must run on the main UI thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adContainer.loadAd(adRequest);
                        }
                    });

                    Log.e(TAG, "Passed bid of: " + bidPrice);
                }

                @Override
                public void onRequestFailed(BidRequestErrorStatus errorStatus) {
                    // error handling here - make a regular MoPub request
//                        hasBid = false;
                    adRequest = new PublisherAdRequest.Builder().build();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adContainer.loadAd(adRequest);
                        }
                    });
                    Log.e(TAG, "Failed bid");
                }
            });

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

                            Toast.makeText(SuperAuctionBannerDFPActivity.this, "Request Failed Called", Toast.LENGTH_LONG).show();
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
