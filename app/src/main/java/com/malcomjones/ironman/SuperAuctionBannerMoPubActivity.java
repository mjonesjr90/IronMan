package com.malcomjones.ironman;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.millennialmedia.BidRequestErrorStatus;
import com.millennialmedia.BidRequestListener;
import com.millennialmedia.InlineAd;
import com.millennialmedia.MMException;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;

/**
 * This activity makes a super auction banner requestBid call to the ONE Mobile platform
 *
 * It passes this bid to MoPub as a keyword upon recieving a bid
 *
 * If not bid is returned, a conventional MoPub call is made
 */

public class SuperAuctionBannerMoPubActivity extends Activity implements BannerAdListener{

    private static final String ADUNIT_ID = "cb6f59e1c36347a4a7c3c3a4947c1659";
    private static final String TAG = "SABannerActivity";
    private static final String PLACEMENT_ID = "banner_homescreen_sa";
    private MoPubView moPubView;
    private View loadButton;
    private View reloadButton;
    private boolean hasBid = false;
    String bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_super_auction_mopub);

        FlurryAgent.logEvent("Requested a Super Auction MoPub Banner");

        loadButton = findViewById(R.id.load_sa_banner);
        reloadButton = findViewById(R.id.reload_sa_banner);
        loadButton.setEnabled(false);
        reloadButton.setEnabled(false);

        moPubView = findViewById(R.id.banner_container_sa);
        moPubView.setBannerAdListener(this);
        moPubView.setAdUnitId(ADUNIT_ID);
        requestBidPrice();
    }

    // Sent when the banner has successfully retrieved an ad.
    @Override
    public void onBannerLoaded(MoPubView banner){
        Toast.makeText(getApplicationContext(), "Banner successfully loaded.", Toast.LENGTH_SHORT).show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadButton.setEnabled(false);
                reloadButton.setEnabled(true);
            }
        });
    }

    // Sent when the banner has failed to retrieve an ad. You can use the MoPubErrorCode value to diagnose the cause of failure.
    @Override
    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode){
        Toast.makeText(getApplicationContext(), "Banner load failed.", Toast.LENGTH_SHORT).show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadButton.setEnabled(true);
            }
        });
    }

    // Sent when the user has tapped on the banner.
    @Override
    public void onBannerClicked(MoPubView banner){
        Toast.makeText(getApplicationContext(), "Banner clicked.", Toast.LENGTH_SHORT).show();
    }

    // Sent when the banner has just taken over the screen.
    @Override
    public void onBannerExpanded(MoPubView banner){
        Toast.makeText(getApplicationContext(), "Banner expanded.", Toast.LENGTH_SHORT).show();
    }

    // Sent when an expanded banner has collapsed back to its original size.
    @Override
    public void onBannerCollapsed(MoPubView banner){
        Toast.makeText(getApplicationContext(), "Banner collapsed.", Toast.LENGTH_SHORT).show();
    }

    public void loadAdSA(View v){
        requestBidPrice();
        findViewById(R.id.load_sa_banner).setEnabled(false);
    }

    private void requestBidPrice(){
        Log.v(TAG, "REQUESTING BID");
        InlineAd.InlineAdMetadata inlineMetadata = new InlineAd.InlineAdMetadata();
        inlineMetadata.setAdSize(InlineAd.AdSize.BANNER);
            try {
                InlineAd.requestBid(PLACEMENT_ID, inlineMetadata, new BidRequestListener() {
                    @Override
                    public void onRequestSucceeded(String bidPrice) {
                        // set the bid price as a keyword on the 3rd party SDK ad request
//                        hasBid = true;
                        moPubView.setKeywords("sa:" + bidPrice); //this gets sent as the q parameter
                        moPubView.loadAd();
                        bid = bidPrice;
                        Log.e(TAG, "Passed bid of: " + bidPrice);
                    }

                    @Override
                    public void onRequestFailed(BidRequestErrorStatus errorStatus) {
                        // error handling here - make a regular MoPub request
//                        hasBid = false;
                        moPubView.setAdUnitId(ADUNIT_ID);
                        moPubView.loadAd();
                        Log.e(TAG, "Failed bid");
                    }
                });
            } catch (MMException e) {
                Log.e(TAG, "Error getting bid", e);
                // abort loading ad
            }
    }

    @Override
    public void onDestroy(){
        moPubView.destroy();
        super.onDestroy();
    }
}
