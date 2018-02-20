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
import com.millennialmedia.InlineAd.AdSize;
import com.millennialmedia.MMException;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;

public class SuperAuctionMRECMoPubActivity extends Activity implements BannerAdListener{

    private static final String ADUNIT_ID = "05578692429143438eefab671730329d";
    private static final String TAG = "MRECActivity";
    private static final String PLACEMENT_ID = "mrec_sa";
    private MoPubView moPubView;
    private View loadButton;
    private View reloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrec_super_auction_mopub);

        FlurryAgent.logEvent("Requested a Super Auction MoPub MREC");

        loadButton = findViewById(R.id.load_sa_mrec);
        reloadButton = findViewById(R.id.reload_sa_mrec);
        loadButton.setEnabled(false);
        reloadButton.setEnabled(false);

        moPubView = findViewById(R.id.mrec_container_sa);
        moPubView.setBannerAdListener(this);
        requestBidPrice();
    }

    // Sent when the banner has successfully retrieved an ad.
    @Override
    public void onBannerLoaded(MoPubView banner){
        Toast.makeText(getApplicationContext(), "MREC successfully loaded.", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getApplicationContext(), "MREC load failed.", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getApplicationContext(), "MREC clicked.", Toast.LENGTH_SHORT).show();
    }

    // Sent when the banner has just taken over the screen.
    @Override
    public void onBannerExpanded(MoPubView banner){
        Toast.makeText(getApplicationContext(), "MREC expanded.", Toast.LENGTH_SHORT).show();
    }

    // Sent when an expanded banner has collapsed back to its original size.
    @Override
    public void onBannerCollapsed(MoPubView banner){
        Toast.makeText(getApplicationContext(), "MREC collapsed.", Toast.LENGTH_SHORT).show();
    }

    public void loadAdSA(View v){
        requestBidPrice();
        findViewById(R.id.load_sa_mrec).setEnabled(false);
    }

    private void requestBidPrice(){
        InlineAd.InlineAdMetadata inlineMetadata = new InlineAd.InlineAdMetadata();
        inlineMetadata.setAdSize(AdSize.MEDIUM_RECTANGLE);
        try{
            InlineAd.requestBid(PLACEMENT_ID, inlineMetadata, new BidRequestListener() {
                @Override
                public void onRequestSucceeded(String bidPrice) {
                    // set the bid price as a keyword on the 3rd party SDK ad request
                    moPubView.setAdUnitId(ADUNIT_ID);
                    moPubView.setKeywords("sa:"+bidPrice); //this gets sent as the q parameter
                    moPubView.loadAd();
                    Log.e(TAG, "Passed bid of: " + bidPrice);
                }
                @Override
                public void onRequestFailed(BidRequestErrorStatus errorStatus) {
                    // error handling here - make a regular MoPub request
                    moPubView.setAdUnitId(ADUNIT_ID);
                    moPubView.loadAd();
                    Log.e(TAG, "Failed bid");
                }
            });
        } catch(MMException e) {
            Log.e(TAG, "Error getting bid", e);
            // abort loading ad
        }


        //inlineAd.request(inlineAdMetadata);
    }

    @Override
    public void onDestroy(){
        moPubView.destroy();
        super.onDestroy();
    }
}
