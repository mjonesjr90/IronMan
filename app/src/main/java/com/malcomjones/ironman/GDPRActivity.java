package com.malcomjones.ironman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.millennialmedia.InlineAd;
import com.millennialmedia.MMException;

/**
 * This activity makes a conventional banner request to the ONE Mobile platform
 */

public class GDPRActivity extends AppCompatActivity {

    private static final String TAG = "BannerActivity";
    private static final String PLACEMENT_ID = "banner_homescreen";
    private InlineAd inlineAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        FlurryAgent.logEvent("Requested a Banner");

        final View adContainer = findViewById(R.id.gdpr_banner_container);

        try {

            // NOTE: The ad container argument passed to the createInstance call should be the
            // view container that the ad content will be injected into.

            inlineAd = InlineAd.createInstance(PLACEMENT_ID, (ViewGroup) adContainer);

            inlineAd.setListener(new InlineAd.InlineListener() {
                @Override
                public void onRequestSucceeded(InlineAd inlineAd) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            adContainer.setVisibility(View.VISIBLE);
                        }
                    });

                    Log.i(TAG, "Inline Ad loaded.");
                }


                @Override
                public void onRequestFailed(InlineAd inlineAd, final InlineAd.InlineErrorStatus errorStatus) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        Toast.makeText(GDPRActivity.this, "Request Failed Called: " + errorStatus.toString(), Toast.LENGTH_LONG).show();
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
}


