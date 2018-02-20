package com.malcomjones.ironman;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;

/**
 * This activity makes a conventional MoPub banner request to the MoPub platform
 */

public class MoPubBannerActivity extends Activity implements BannerAdListener{

    private static final String ADUNIT_ID = "cb6f59e1c36347a4a7c3c3a4947c1659";
    private MoPubView moPubView;
    private View loadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_mopub);
        loadButton = findViewById(R.id.load_mopub_banner);
        loadButton.setEnabled(false);

        moPubView = findViewById(R.id.banner_container_mopub);
        moPubView.setAdUnitId(ADUNIT_ID);
        moPubView.loadAd();
        moPubView.setBannerAdListener(this);

    }

    // Sent when the banner has successfully retrieved an ad.
    @Override
    public void onBannerLoaded(MoPubView banner){
        Toast.makeText(getApplicationContext(), "Banner successfully loaded.", Toast.LENGTH_SHORT).show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadButton.setEnabled(false);
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

    public void loadAdMoPub(View v){
        moPubView.loadAd();
        moPubView.setBannerAdListener(this);
        findViewById(R.id.load_mopub_banner).setEnabled(false);
    }

    @Override
    public void onDestroy(){
        moPubView.destroy();
        super.onDestroy();
    }
}
