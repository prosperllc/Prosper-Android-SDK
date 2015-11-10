/*
* Copyright 2015, Prosper Funding, LLC, All Right Reserved.
*/

package com.prosper.prosperborrowersdksample;

import com.prosper.androidsdk.external.rest.model.offer.PMIListedOffers;
import com.prosper.androidsdk.external.rest.model.offer.PMIOffer;
import com.prosper.androidsdk.external.rest.model.prospect.PMIBorrowerInfo;
import com.prosper.widget.ui.ProsperActivity;
import com.prosper.widget.ui.ProsperIntent;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class OffersActivity extends Activity implements View.OnClickListener {

    private static final String TAG = OffersActivity.class.getSimpleName();
    private PMIBorrowerInfo borrowerInfo;
    private PMIListedOffers pmiListedOffers;
    private int userSelectedOffer;
    private Button selectedPrimaryOffer;
    private Button selectedOtherOffer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle args = getIntent().getBundleExtra(MainActivity.ARGS);

        if (args != null) {
             borrowerInfo = args.getParcelable(MainActivity.BORROWER_INFO);
             pmiListedOffers = args.getParcelable(MainActivity.OFFERS);
        }

        // Pass the first offer from the list of offers for test purposes
        final PMIOffer offer = pmiListedOffers.getOffers().get(0);
        userSelectedOffer = offer.getLoanOfferId();
        selectedPrimaryOffer = (Button) findViewById(R.id.selected_offer);
        selectedOtherOffer = (Button) findViewById(R.id.selected_offer2);

        selectedPrimaryOffer.setOnClickListener(this);
        selectedOtherOffer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (userSelectedOffer == 0) {
            Intent prosperIntent = ProsperIntent.getCollectUserInfoIntent(OffersActivity.this);
            startActivityForResult(prosperIntent, ProsperActivity.LOAN_APPLICATION_REQUEST);
        } else {
            /**
             * Call Prosper Intent with borrowerInfo object you created by collecting user information and userSelectedOffer id
             */
            Intent prosperIntent =
                    ProsperIntent.getUserInfoProvidedIntent(OffersActivity.this, borrowerInfo, userSelectedOffer);
            startActivityForResult(prosperIntent, ProsperActivity.LOAN_APPLICATION_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Congratulations!!");
            alertDialogBuilder.setMessage("You have successfully applied for a Prosper loan.").setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(OffersActivity.this, "APPLICATION CANCELED", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(OffersActivity.this, "APPLICATION ABANDONED", Toast.LENGTH_LONG).show();
        }

    }
}
