package com.malcomjones.ironman;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.millennialmedia.MMSDK;

/**
 * Created by majones95 on 5/1/18.
 */

public class GDPRConsentDialog extends DialogFragment {
    private static final String TAG = "GDPR Consent Dialog";
    private static final String TEST_CONSENT = "BOMgSHzOMgSHzAAABAENAC____ABkAAABA";

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        // Use the Builder class for convenient dialog construction
        Builder builder = new Builder(getActivity());
        builder.setMessage(R.string.gdpr_confirmation)
                .setTitle(R.string.gdpr_consent)
                .setPositiveButton(R.string.consent_true, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Consent given
                        MMSDK.setConsentData(MMSDK.IAB_CONSENT_KEY, TEST_CONSENT);
                        Toast.makeText(getActivity(), "Consent was given", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "Consent was given");

                    }
                })
                .setNegativeButton(R.string.consent_false, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Consent not given
                        MMSDK.clearConsentData();
                        Toast.makeText(getActivity(), "Consent was NOT given", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "Consent was NOT given");
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
