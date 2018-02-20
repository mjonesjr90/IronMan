package com.malcomjones.aolsample;

import android.app.Application;
import android.util.Log;

import com.millennialmedia.AppInfo;
import com.millennialmedia.MMException;
import com.millennialmedia.MMSDK;

/**
 * Created by majones95 on 6/6/17.
 */

public class AOLSample extends Application {
    private static final String TAG = "SubclassActivity";
    private static final String SITE_ID = "studytime";

    /**
     * Initialize the SDK and set the SiteID
     *
     * appInfo can also call setCoppa(boolean c) and setMediator(String m)
     *
     * throw an exception if the initialization fails
     */
    @Override
    public void onCreate() {
        super.onCreate();

        try{
            MMSDK.initialize(this);

            //Set Site ID
            AppInfo appInfo = new AppInfo();
            appInfo.setSiteId(SITE_ID);
            MMSDK.setAppInfo(appInfo);

        } catch (MMException e){
            Log.e(TAG, "SDK didn't initialize", e);
        }

    }
}
