package com.malcomjones.ironman;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.millennialmedia.BidRequestErrorStatus;
import com.millennialmedia.BidRequestListener;
import com.millennialmedia.InlineAd;
import com.millennialmedia.MMException;

/**
 * Instance of Super Auction without using MoPub
 *
 * This can be a base for publishers who have their own SSP
 */
public class SuperAuctionMRECActivity extends Activity{

    private static final String TAG = "SABasicMRECActivity";
    private static final String PLACEMENT_ID = "mrec_sa";
    private View loadButton;
    private View reloadButton;
    private InlineAd inlineAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrec_super_auction);

        FlurryAgent.logEvent("Requested a Super Auction MREC");

        loadButton = findViewById(R.id.load_sa_banner);
        reloadButton = findViewById(R.id.reload_sa_banner);
        loadButton.setEnabled(false);
        reloadButton.setEnabled(false);

        requestBidPrice();
    }

    public void loadAdSA(View v){
        requestBidPrice();
        findViewById(R.id.load_sa_banner).setEnabled(false);
    }

    private void requestBidPrice(){
        InlineAd.InlineAdMetadata inlineMetadata = new InlineAd.InlineAdMetadata();
        inlineMetadata.setAdSize(InlineAd.AdSize.BANNER);
        try{
            InlineAd.requestBid(PLACEMENT_ID, inlineMetadata, new BidRequestListener() {
                @Override
                public void onRequestSucceeded(String bidPrice) {
                    // set the bid price as a keyword on the 3rd party SDK ad request
                    Log.d(TAG, "Passed bid of: " + bidPrice);

                    //Auction logic can be implemented here; call back to ONE Mobile SDK if we win
                    Log.v(TAG,"Requesting banner for placement: " + PLACEMENT_ID);
                    requestBanner(PLACEMENT_ID);
                }
                @Override
                public void onRequestFailed(BidRequestErrorStatus errorStatus) {
                    // error handling here - make a regular MoPub request
                    Log.v(TAG, "Failed bid");

                    //Request another bid, or call and serve a bid from your SSP
                    Log.v(TAG, "Requesting a new bid price");
                    //requestBidPrice();
                }
            });
        } catch(MMException e) {
            Log.e(TAG, "Error getting bid", e);
            // abort loading ad
        }


        //inlineAd.request(inlineAdMetadata);
    }

    private void requestBanner(String placement){
        final View adContainer = findViewById(R.id.mrec_container_sa);

        try {

            // NOTE: The ad container argument passed to the createInstance call should be the
            // view container that the ad content will be injected into.

            inlineAd = InlineAd.createInstance(placement, (ViewGroup) adContainer);

            inlineAd.setListener(new InlineAd.InlineListener() {
                @Override
                public void onRequestSucceeded(InlineAd inlineAd) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            adContainer.setVisibility(View.VISIBLE);
                            loadButton.setEnabled(false);
                            reloadButton.setEnabled(true);
                        }
                    });

                    Log.i(TAG, "Inline Ad loaded.");
                }


                @Override
                public void onRequestFailed(InlineAd inlineAd, final InlineAd.InlineErrorStatus errorStatus) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadButton.setEnabled(true);
                            Toast.makeText(SuperAuctionMRECActivity.this, "Request Failed Called: " + errorStatus.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                    Log.i(TAG, errorStatus.toString());
                    finish();
                }


                @Override
                public void onClicked(InlineAd inlineAd) {

                    Log.i(TAG, "Inline Ad clicked.");
                }


                @Override
                public void onResize(InlineAd inlineAd, int width, int height) {

                    Log.i(TAG, "Inline Ad starting resize.");
                }


                @Override
                public void onResized(InlineAd inlineAd, int width, int height, boolean toOriginalSize) {

                    Log.i(TAG, "Inline Ad resized.");
                }


                @Override
                public void onExpanded(InlineAd inlineAd) {

                    Log.i(TAG, "Inline Ad expanded.");
                }


                @Override
                public void onCollapsed(InlineAd inlineAd) {

                    Log.i(TAG, "Inline Ad collapsed.");
                }


                @Override
                public void onAdLeftApplication(InlineAd inlineAd) {

                    Log.i(TAG, "Inline Ad left application.");
                }
            });

            requestAd();


        } catch (MMException e) {
            Log.e(TAG, "Error creating inline ad", e);
            // abort loading ad
        }
    }

    private void requestAd(){
        if (inlineAd != null) {
            // set a refresh rate of 30 seconds that will be applied after the first request
            //inlineAd.setRefreshInterval(30000);

            // The InlineAdMetadata instance is used to pass additional metadata to the server to
            // improve ad selection
            final InlineAd.InlineAdMetadata inlineAdMetadata = new InlineAd.InlineAdMetadata().
                    setAdSize(InlineAd.AdSize.BANNER);

            inlineAd.request(inlineAdMetadata);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
