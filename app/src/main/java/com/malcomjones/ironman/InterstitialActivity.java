package com.malcomjones.ironman;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.millennialmedia.InterstitialAd;
import com.millennialmedia.MMException;

/**
 * This activity makes a conventional interstitial request to the ONE Mobile platform
 */
public class InterstitialActivity extends AppCompatActivity {

    public static final String PLACEMENT_ID = "interstitial";
    public static final String TAG = InterstitialActivity.class.getSimpleName();

    //Interstitial Ad declared
    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

        FlurryAgent.logEvent("Requested an Interstitial");

        final View loadButton = findViewById(R.id.load);
        final View showButton = findViewById(R.id.show);

        try {
            showButton.setEnabled(true);
            interstitialAd = InterstitialAd.createInstance(PLACEMENT_ID);

            interstitialAd.setListener(new InterstitialAd.InterstitialListener() {
                @Override
                public void onLoaded(InterstitialAd interstitialAd) {
                    Toast.makeText(getApplicationContext(), "Ad loaded.", Toast.LENGTH_SHORT).show();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            loadButton.setEnabled(false);
                            showButton.setEnabled(true);
                        }
                    });
                    Log.i(TAG, "Interstitial Ad loaded.");
                }


                @Override
                public void onLoadFailed(InterstitialAd interstitialAd,
                                         InterstitialAd.InterstitialErrorStatus errorStatus) {

                    Toast.makeText(getApplicationContext(), "Ad load failed.", Toast.LENGTH_SHORT).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

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

        } catch (MMException e) {
            Log.e(TAG, "Error creating interstitial ad", e);
            // abort loading ad
        }
    }

    public void loadAd(View v){
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
