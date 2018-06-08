package com.malcomjones.ironman;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.millennialmedia.MMSDK;

/**
 * Created by majones95 on 5/1/18.
 */

public class GDPRConsentRequiredDialog extends DialogFragment {
    private static final String TAG = "GDPR Consent Req Dialog";

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new Builder(getActivity());
        builder.setMessage(R.string.gdpr_requirement)
                .setTitle(R.string.gdpr_consent)
                .setPositiveButton(R.string.eu_true, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Consent Required
                        MMSDK.setConsentRequired(true);
                        Log.i(TAG, "Consent is required for ad requests");
                        DialogFragment gdpr_consent_dialog = new GDPRConsentDialog();
                        gdpr_consent_dialog.show(getFragmentManager(), "gdpr consent dialog");

                    }
                })
                .setNegativeButton(R.string.eu_false, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Consent not required
                        MMSDK.setConsentRequired(false);
                        Log.i(TAG, "Consent is NOT required for ad requests");
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
