package com.malcomjones.ironman;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.millennialmedia.BidRequestErrorStatus;
import com.millennialmedia.BidRequestListener;
import com.millennialmedia.InterstitialAd;
import com.millennialmedia.MMException;

/**
 * This activity makes a conventional interstitial request to the ONE Mobile platform
 */
public class SuperAuctionInterstitialActivity extends AppCompatActivity {

    public static final String PLACEMENT_ID = "interstitial_static_sa";
    public static final String TAG = SuperAuctionInterstitialActivity.class.getSimpleName();
    private View loadButton;
    private View showButton;

    //Interstitial Ad declared
    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_super_auction);

        FlurryAgent.logEvent("Requested a Super Auction Interstitial");

        loadButton = findViewById(R.id.load);
        showButton = findViewById(R.id.show);
        loadButton.setEnabled(true);
        showButton.setEnabled(false);
        requestBidPrice();
    }

    private void requestBidPrice(){
        InterstitialAd.InterstitialAdMetadata interstitialAdMetadataMetadata = new InterstitialAd.InterstitialAdMetadata();
        try{
            InterstitialAd.requestBid(PLACEMENT_ID, interstitialAdMetadataMetadata, new BidRequestListener() {
                @Override
                public void onRequestSucceeded(String bidPrice) {
                    // set the bid price as a keyword on the 3rd party SDK ad request
                    Log.v(TAG, "Passed bid of: " + bidPrice);

                    //Auction logic can be implemented here; call back to ONE Mobile SDK if we win
                    Log.v(TAG,"Requesting interstitial for placement: " + PLACEMENT_ID);
                    requestInterstitial(PLACEMENT_ID);
                }
                @Override
                public void onRequestFailed(BidRequestErrorStatus errorStatus) {
                    // error handling here - make a regular MoPub request
                    Log.v(TAG, "Failed bid");

                    //Request another bid, or call and serve a bid from your SSP
                    Log.v(TAG, "Requesting a new bid price");
                    requestBidPrice();
                }
            });
        } catch(MMException e) {
            Log.e(TAG, "Error getting bid", e);
            // abort loading ad
        }
    }

    private void requestInterstitial(String placement){
        try {
            interstitialAd = InterstitialAd.createInstance(placement);
            Log.v(TAG,"Creating an interstitial instance");
            interstitialAd.setListener(new InterstitialAd.InterstitialListener() {
                @Override
                public void onLoaded(InterstitialAd interstitialAd) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Ad loaded.", Toast.LENGTH_SHORT).show();
                            loadButton.setEnabled(false);
                            showButton.setEnabled(true);
                        }
                    });
                    Log.i(TAG, "Interstitial Ad loaded.");
                }


                @Override
                public void onLoadFailed(InterstitialAd interstitialAd,
                                         InterstitialAd.InterstitialErrorStatus errorStatus) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Ad load failed.", Toast.LENGTH_SHORT).show();
                            loadButton.setEnabled(true);
                            showButton.setEnabled(false);
                        }
                    });
                    Log.i(TAG, "Interstitial Ad load failed.");
                }


                @Override
                public void onShown(InterstitialAd interstitialAd) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            loadButton.setEnabled(true);
                            showButton.setEnabled(false);
                        }
                    });
                    Log.i(TAG, "Interstitial Ad shown.");
                }


                @Override
                public void onShowFailed(InterstitialAd interstitialAd,
                                         InterstitialAd.InterstitialErrorStatus errorStatus) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            loadButton.setEnabled(true);
                            showButton.setEnabled(false);
                        }
                    });
                    Log.i(TAG, "Interstitial Ad show failed.");
                }


                @Override
                public void onClosed(InterstitialAd interstitialAd) {

                    Log.i(TAG, "Interstitial Ad closed.");
                }


                @Override
                public void onClicked(InterstitialAd interstitialAd) {

                    Log.i(TAG, "Interstitial Ad clicked.");
                }


                @Override
                public void onAdLeftApplication(InterstitialAd interstitialAd) {

                    Log.i(TAG, "Interstitial Ad left application.");
                }


                @Override
                public void onExpired(InterstitialAd interstitialAd) {

                    Log.i(TAG, "Interstitial Ad expired.");
                }
            });

            loadButton.setEnabled(true);

        } catch (MMException e) {
            Log.e(TAG, "Error creating interstitial ad", e);
            // abort loading ad
        }
    }

    public void loadAd(View v){
//        requestBidPrice();
        if (interstitialAd != null) {
            interstitialAd.load(this, null);
            Log.i(TAG, "Interstitial loaded");
            findViewById(R.id.load).setEnabled(false);
            findViewById(R.id.show).setEnabled(true);
        }
    }

    public void showAd(View v){
        // Check that the ad is ready.
        if (interstitialAd.isReady()) {
            // Show the Ad using the display options you configured.
            try {
                interstitialAd.show(this);
            } catch (MMException e) {
                Log.i(TAG, "Unable to show interstitial ad content, exception occurred");
                e.printStackTrace();
            }

        } else {
            Log.w(TAG, "Unable to show interstitial. Ad not loaded.");
        }
    }

}
