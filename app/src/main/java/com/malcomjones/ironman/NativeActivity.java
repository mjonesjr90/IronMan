package com.malcomjones.ironman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.millennialmedia.MMException;
import com.millennialmedia.NativeAd;
import com.millennialmedia.NativeAd.ComponentName;
import com.millennialmedia.NativeAd.NativeErrorStatus;
import com.millennialmedia.NativeAd.NativeListener;

public class NativeActivity extends AppCompatActivity {

    private static final String TAG = NativeActivity.class.getSimpleName();
    private static final String PLACEMENT_ID = "native";
    private NativeAd nativeAd;
    private LinearLayout adContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);

        try{
            nativeAd = NativeAd.createInstance(PLACEMENT_ID, NativeAd.NATIVE_TYPE_INLINE);

            nativeAd.setListener(new NativeListener() {
                @Override
                public void onLoaded(final NativeAd nativeAd) {
                    Log.i(TAG, "Native ad loaded");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                View nativeAdView = nativeAd.inflateLayout(NativeActivity.this, new int[]{R.layout.native_ad});

                                //Make sure you have the populated layout
                                if (nativeAd != null) {
                                    adContainer = (LinearLayout) findViewById(R.id.native_container);

                                    //Attach the layout to view heirarchy
                                    adContainer.addView(nativeAdView);
                                }
                            } catch (MMException e) {
                                Log.e(TAG, "Native layout could not be inflated");
                            }
                        }
                    });
                }

                @Override
                public void onLoadFailed(NativeAd nativeAd, NativeErrorStatus nativeErrorStatus) {

                }

                @Override
                public void onClicked(NativeAd nativeAd, ComponentName componentName, int i) {

                }

                @Override
                public void onAdLeftApplication(NativeAd nativeAd) {

                }

                @Override
                public void onExpired(NativeAd nativeAd) {

                }
            });

            requestAd();

        } catch (MMException e) {
            Log.e(TAG, "Error creating native ad", e);
        }
    }

    private void requestAd(){

        if (nativeAd != null) {
            try {
                nativeAd.load(this, null);
            } catch (MMException e) {
                e.printStackTrace();
            }
        }
    }
}
