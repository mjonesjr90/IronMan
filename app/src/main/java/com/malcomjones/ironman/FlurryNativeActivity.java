package com.malcomjones.ironman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.flurry.android.ads.FlurryAdErrorType;
import com.flurry.android.ads.FlurryAdNative;
import com.flurry.android.ads.FlurryAdNativeListener;
import com.flurry.android.ads.FlurryAdTargeting;

public class FlurryNativeActivity extends AppCompatActivity {

    private FlurryAdNative nativeFlurryAd = null;
    private String adSpaceName = "test_native_detailcard";
    private LinearLayout adLayout;
    private static final String TAG = FlurryNativeActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_flurry);

        adLayout = (LinearLayout) findViewById(R.id.adContainerLayout);
        nativeFlurryAd = new FlurryAdNative(this, adSpaceName);

        //Listen for callbacks
        nativeFlurryAd.setListener(new FlurryAdNativeListener() {
            @Override
            public void onFetched(FlurryAdNative flurryAdNative) {
                parseAssets(flurryAdNative);

                //This enables impression and click tracking
                flurryAdNative.setTrackingView(adLayout);
            }

            @Override
            public void onShowFullscreen(FlurryAdNative flurryAdNative) {

            }

            @Override
            public void onCloseFullscreen(FlurryAdNative flurryAdNative) {

            }

            @Override
            public void onAppExit(FlurryAdNative flurryAdNative) {

            }

            @Override
            public void onClicked(FlurryAdNative flurryAdNative) {

            }

            @Override
            public void onImpressionLogged(FlurryAdNative flurryAdNative) {

            }

            @Override
            public void onExpanded(FlurryAdNative flurryAdNative) {

            }

            @Override
            public void onCollapsed(FlurryAdNative flurryAdNative) {

            }

            @Override
            public void onError(FlurryAdNative flurryAdNative, FlurryAdErrorType flurryAdErrorType, int i) {
                if (flurryAdErrorType.equals(FlurryAdErrorType.FETCH)) {
                    Log.i(TAG, "onFetchFailed " + i);
                    // you can deploy internal logic to determine whether to  fetch another ad here or not.
                    // do not fetch more than x times in a row
                }
            }
        });



    }

    public void parseAssets(FlurryAdNative flurryAdNative){
        if(flurryAdNative.getAsset("headline") != null) {
            flurryAdNative.getAsset("headline").loadAssetIntoView(findViewById(R.id.newsTitle));
            Log.d(TAG, "pulled headline");
        }

        if (flurryAdNative.getAsset("summary") != null) {
            flurryAdNative.getAsset("summary").loadAssetIntoView(findViewById(R.id.newsSummary));
            Log.d(TAG, "pulled summary");
        }

        if (flurryAdNative.getAsset("source") != null) {
            flurryAdNative.getAsset("source").loadAssetIntoView(findViewById(R.id.sponsoredPublisher));
            Log.d(TAG, "pulled source");
        }

        if (flurryAdNative.getAsset("secHqBrandingLogo") != null) {
            flurryAdNative.getAsset("secHqBrandingLogo").loadAssetIntoView(findViewById(R.id.sponsoredImage));
            Log.d(TAG, "pulled branding logo");
        }

        flurryAdNative.getAsset("secHqImage").loadAssetIntoView(findViewById(R.id.mainImage));
        Log.d(TAG, "pulled main image");
//        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        //Turn on test mode for the ad unit
        FlurryAdTargeting adTargeting = new FlurryAdTargeting();
        adTargeting.setEnableTestAds(true);
        nativeFlurryAd.setTargeting(adTargeting);

        //Fetch the native ad
        nativeFlurryAd.fetchAd();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        nativeFlurryAd.destroy();
    }
}
