/*
* Copyright 2015, Prosper Funding, LLC, All Right Reserved.
*/

package com.prosper.prosperborrowersdksample;

import com.prosper.androidsdk.external.ProsperConfig;
import com.prosper.androidsdk.external.rest.model.offer.PMIListedOffers;
import com.prosper.androidsdk.external.rest.model.prospect.PMIBorrowerInfo;
import com.prosper.widget.ui.ProspectOffersService;
import com.prosper.widget.ui.ProsperActivity;
import com.prosper.widget.ui.ProsperIntent;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private PMIBorrowerInfo borrowerInfo;
    public static final String BORROWER_INFO = "borrower_info";
    public static final String OFFERS = "offers";
    public static final String ARGS = "extras";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button bankFlow = (Button) findViewById(R.id.bankFlow);
        Button getOffers = (Button) findViewById(R.id.getOffers);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sample App");
        actionBar.setDisplayHomeAsUpEnabled(false);


        /**
         * OnClickListener that builds the borrower object and calls ProspectOfferService
         */
        getOffers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                borrowerInfo =
                        new PMIBorrowerInfo.PMIBorrowerBuilder()
                                .setLoanAmount(9000)
                                .setLoanPurpose(ProsperConfig.LoanPurpose.RV)
                                .setCreditScore(780)
                                .setEmploymentStatusId(ProsperConfig.EmploymentStatusId.OTHER)
                                .setAnnualIncome(95000)
                                .setEmail("nano" + System.nanoTime() + "@prosper12.stg")
                                .setFirstName("Mary")
                                .setLastName("Hopkins")
                                .setAddress1("912 PineLand Ave")
                                .setAddress2("APT 33")
                                .setCity("Hinesville")
                                .setState("GA")
                                .setZip("31313")
                                .setDateOfBirth("04/28/1984")
                                .build();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Get Offers..");
                alertDialogBuilder
                        .setMessage(
                                "Your personal and financial information will be passed to Prosper to give you the lowest interest offers.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final ProgressDialog progressDialog =
                                ProgressDialog.show(MainActivity.this, "Getting offers", "Please wait...", true);
                        progressDialog.setCancelable(false);
                        ProspectOffersService.getInstance().getOffers(borrowerInfo, new ProsperConfig.OffersCallback() {

                            @Override
                            public void success(PMIListedOffers offers) {
                                Log.d(TAG, "OFFERS SUCCESS");
                                Log.d(TAG, "OFFERS RESPONSE = " + offers.getOffers().get(0).getApr());
                                progressDialog.dismiss();
                                Bundle args = new Bundle();
                                args.putParcelable(BORROWER_INFO, borrowerInfo);
                                args.putParcelable(OFFERS, offers);
                                Intent intent = new Intent(MainActivity.this, OffersActivity.class);
                                intent.putExtra(ARGS, args);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "Offers Success", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void failure(String s) {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Offers Failed" + "\t" + s, Toast.LENGTH_LONG).show();
                            }

                        });
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        bankFlow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent prosperIntent = ProsperIntent.getCollectUserInfoIntent(MainActivity.this);
                startActivityForResult(prosperIntent, ProsperActivity.LOAN_APPLICATION_REQUEST);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
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
            Toast.makeText(MainActivity.this, "APPLICATION CANCELED", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "APPLICATION ABANDONED", Toast.LENGTH_LONG).show();
        }

    }

}
