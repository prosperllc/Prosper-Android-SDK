/*
* Copyright 2015, Prosper Funding, LLC, All Right Reserved.
*/

package com.prosper.prosperborrowersdksample.app;

import android.app.Application;
import android.util.Log;

import com.prosper.androidsdk.external.ProsperConfig;
import com.prosper.prosperborrowersdksample.BuildConfig;


public class ProsperSampleApp extends Application {

    private static final String TAG = ProsperSampleApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        ProsperConfig.CLIENT_ID_PROVIDED = BuildConfig.PROSPER_CLIENT_ID;
        ProsperConfig.CLIENT_SECRET_PROVIDED = BuildConfig.PROSPER_CLIENT_SECRET;
        ProsperConfig.REF_AC = BuildConfig.PROSPER_REF_AC;
        ProsperConfig.REF_MC = BuildConfig.PROSPER_REF_MC;


        /**
         * Initialize the Prosper SDK, Successful Initialization will get the Credit Score Ranges, Loan Purpose Categories, and
         * Accepted Employment statuses.
         */
        ProsperConfig.init(getApplicationContext(), new ProsperConfig.InitCallback() {
            @Override
            public void success() {
                Log.i(TAG, "init success");
            }

            @Override
            public void failure(String s) {
                Log.i(TAG, "init failure");
            }
        });
    }

}
