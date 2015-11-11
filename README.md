# Prosper Borrower SDK for Android Integration Guide
# Overview
The Prosper Borrower SDK is a native SDK that allows your users to apply for a loan within your app in a seamless and convenient way.


With the Prosper Borrower SDK, you can refer your customers to receive loan offers through Prosper. After receiving the loan offer, the customer can select that offer, create a Prosper account, and complete a loan application immediately. After the loan application has been completed, Prosper initiates loan verification and generates a loan listing for funding by Prosper investors. 

# Prosper Borrower SDK requirements
The Prosper Borrower SDK supports Android 3.0 API level 11 and higher versions. 

The SDK uses the Google Places service to auto-complete addresses when your user starts entering information. If you would like to use this time-saving functionality, you'll need to get a Google Places API key for your app here - https://developers.google.com/places/

This release contains the following:
 * **ProsperBorrowerSDK** – the library containing the ProsperBorrowerSDK distribution that you can integrate into your app. 

 * **ProsperBorrowerSDKSample** – a sample app that demonstrates how to invoke the Prosper Borrower SDK from an app to get loan offers and apply for a Prosper loan.  


## Before you get started...
There's just a couple of things you need to do before you can go live with the Prosper Borrower SDK. It's not much, but it'll make your experience easier.

1. Get client keys from Prosper.
  Just contact us at mobileSDK@prosper.com and request keys for testing the SDK with your app.

  We'll reply shortly with the following keys, that you'll need when integrating and testing the SDK with your app. The keys work on both the Android and iOS versions of the Prosper Borrower SDK.
   *  Client_Id
   *  Client_Secret
   *  Ref_AC
   *  Ref_MC

  The first two keys are tied to your app when making API calls. They are used to authenticate your app. 
The last two keys associate your app as the referring partner when we generate a Prosper loan listing. Without these keys, you won't get very far. We'll tell you where to set them a little later in this guide.

2. Follow the instructions for installing and integrating the SDK in this guide. 

3. Test your integration on the Prosper Sandbox environment.
  
  The keys you received in step 1 above work on the Prosper Sandbox environment. All of your initial testing will happen on our Sandbox environment, which is a mirror of our Production environment.   
  
  When everything is working well end to end, drop us another email with a request for Production keys to go live! This new set of keys are configured to work on the Prosper Production environment. Again, these keys will work for both the Android and iOS versions of the Prosper Borrower SDK.

4. Update your new Production keys in the SDK settings to go live on the Production environment.




# SDK User Flows

## User flow one: Prosper Borrower SDK collects user information

The diagram below shows the flow between your app and the Prosper Borrower SDK throughout the loan offer and application process. In this flow, your app does not collect user information required to generate loan offers through Prosper. Instead, your app launches the Borrower SDK, leaving user information collection and loan offer display and selection to the Prosper Borrower SDK.

![User flow diagram for when the SDK collects user information](https://github.com/prosperllc/Prosper-Android-SDK/blob/master/doc-images/ProsperBorrowerSDKFlowDiagramNoOfferInfo.png)


## User flow two: Your app collects user information
The diagram below shows the flow between your app and the Prosper Borrower SDK throughout the loan offer and application process. In this flow, your app collects user information required to generate loan offers through Prosper. You pass this user information to the Prosper Borrower SDK. Prosper generates a list of personalized loan offers you can present to the user. You then launch the Borrower SDK when the user selects from the list of personalized loan offers. 

![User flow diagram for when your app collects user information](https://github.com/prosperllc/Prosper-Android-SDK/blob/master/doc-images/ProsperBorrowerSDKFlowDiagramNoComp.png)

# Consumer experience in your app with the Prosper SDK

The following screenshots show the flow of the Prosper Borrower SDK on Android. The first image represents a partner app displaying a list of personalized loan offers to a customer. Once the customer selects a loan offer, your app launches the Prosper Borrower SDK, allowing the customer to complete the loan application.

![Prosper Borrower SDK for Android screenshots](https://github.com/prosperllc/Prosper-Android-SDK/blob/master/doc-images/AndroidScreenshots.png)

Here's the Prosper Borrower SDK in action on the Android platform.

![Demo] (https://github.com/prosperllc/Prosper-Android-SDK/blob/master/doc-images/android.gif)


# Installing the Prosper Borrower SDK and configuring your project

**To include the Prosper Borrower SDK in your project:**

1.  Download the ProsperBorrowerAndroidSDK library, unzip and place the “prosper” directory in your local Maven repository in the file system path at $HOME/.m2/com/ repository.

  If you do not already have a local maven repository, you can create a new .m2 repository and place the “prosper” directory within .m2/com/ .

2.  Modify your project’s build.gradle file to include local Maven repositories by adding the following lines:

    ```
   repositories {     
     mavenLocal()
   }
  ```
3.  Add client keys provided by Prosper to your app’s build.gradle file.     

  When invoked, the Prosper Borrower SDK will use these keys to authenticate your client. These keys also associate your client as the referring partner when we generate a Prosper loan listing.
  
   *  Client_Id
   *  Client_Secret
   *  Ref_AC
   *  Ref_MC

  
  **Note:** Optionally, you can add your Google Places key if you want the Prosper Borrower SDK to auto-complete addresses that users enter to speed data entry. You can get a Google Places API key here - https://developers.google.com/places/


    ```
   android {
   compileSdkVersion 23
   buildToolsVersion "23.0.1"
    
   defaultConfig {
     applicationId "com.prosper.prosperborrowersdksample"
     minSdkVersion 11
     targetSdkVersion 23
       /* Add your values here */
        buildConfigField "String", "PROSPER_CLIENT_ID", "\"obtainedFromPropser\""
        buildConfigField "String", "PROSPER_CLIENT_SECRET", "\"obtainedFromProsper\""
        buildConfigField "String", "PROSPER_REF_AC", "\"obtainedFromProsper\""
        buildConfigField "String", "PROSPER_REF_MC", "\"obtainedFromProsper\""
       /* Add your Google Places API credentials. Assists in auto-complete 
          when users enter a street address. */
       resValue "string", "google_api_name", "com.google.android.geo.API_KEY"
       resValue "string", "google_api_key", "\"obtainedFromGoogle\""
     } 
   }  
   ```

4.  Add the following dependencies to your project’s build.gradle file: 

  ```
  dependencies { 
     compile (name: 'com.prosper.widget:borrowerwidget:1.0@aar') {        
     transitive = true;     
    }  
 }   
  ```

5.  Add required Prosper Borrower SDK permissions within your manifest file:

  ```
   <uses-permission android:name="android.permission.INTERNET" />
   <uses-permission android:name="android.permission.READ_PHONE_STATE" /> 
   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
  ```

6.  Define the ProsperActivity class in your manifest file:

  ```
   <activity android:name="com.prosper.widget.ui.ProsperActivity" android:label="Prosper" android:theme="@style/Prosper.AppTheme" tools:replace="theme" />
  ```

7.  Add the Prosper.AppTheme to your style.xml file:

  ```
  <style name = "Prosper.AppTheme" parent="Base.Theme.AppCompat.Light.DarkActionBar">
   <item name="colorPrimary">@color/prosperPrimaryColor</item>   
   <item name="colorPrimaryDark">@color/prosperPrimaryColorDark</item>
   <item name="colorAccent">@color/prosperAccentColor</item>
   <item name="android.windowContentOverlay">@null</item></style>
  ```
8.  Perform a gradle sync in your project to pick up your gradle.build file updates.

##Configure ProGuard for Prosper Borrower SDK files
If you are using the ProGuard tool for optimization and obfuscation of your project, you must update the ProGuard configuration with rules to ensure Prosper Borrower SDK classes are handled correctly.

Add the following lines to your ProGuard configuration:

```
 -keep class com.prosper.androidsdk.external.** { *; } 
 -keep class retrofit.** { *; }
 -keep class com.squareup.okhttp.** { *; }
 -keep interface com.squareup.okhttp.** { *; }
 
 -dontwarn okio.** 
 -dontwarn retrofit.** 
 -dontwarn com.adobe.**
```

##Configure your Prosper client id and secret
When you became a registered referral partner with Prosper, you should have received a client id and client secret in your welcome email. You should have also received two other codes from Prosper (REF_AC and REF_MC) that represent partner referral identity when a loan listing is generated.  

The Prosper Borrower SDK contains an SDK configuration file named ProsperConfig.java. You must set your Client_Id, Client_Secret, and partner referral codes within this config file. 

When launched, the Prosper Borrower SDK will use the settings in this config file to authenticate your client. These settings also associate your client as the referring partner when we generate a Prosper loan listing.

```
ProsperConfig.CLIENT_ID_PROVIDED = BuildConfig.PROSPER_CLIENT_ID;
ProsperConfig.CLIENT_SECRET_PROVIDED = BuildConfig.PROSPER_CLIENT_SECRET;
ProsperConfig.REF_AC = BuildConfig.PROSPER_REF_AC;
ProsperConfig.REF_MC = BuildConfig.PROSPER_REF_MC;
```


#Initialize the Prosper Borrower SDK
Initialize the Prosper Borrower SDK in your Application.OnCreate() method. 

```
    ProsperConfig.init(getApplicationContext(), new ProsperConfig.InitCallback() {         
            @Override
            public void success() {
               Log.d(TAG, "init success");             
            }              

            @Override
            public void failure(String errorMessage) {
               Log.d(TAG, "init failure");
            }
        });
```

#Get Prosper loan offers for a user

To generate personalized loan offers for a user, Prosper requires 13 pieces of information about the loan applicant: 
 * Loan Amount
 * Loan Purpose
 * Self-Reported Credit Score
 * First Name
 * Last Name
 * Date Of Birth (DOB)
 * Email
 * Street Address
 * City
 * State
 * Zip
 * Employment Status
 * Annual Income

##Select the user flow that best matches your development needs.

There are two user flows to choose from, described previously:
 * Prosper Borrower SDK collects user information – You either cannot or do not want to collect the above information from users within your app. You are OK with relinquishing control to the Prosper Borrower SDK for collecting this information, and displaying the personalized loan offers to the user. 

  In this case, you will initialize the Prosper Borrower SDK, calling the static method ProsperIntent.getCollectUserInfoIntent(context) . The Prosper Borrower SDK takes care of the rest.

 * Your app collects user information – You may want to have tight control over how (and how many) loan offers are presented to your users. Prosper provides the ProspectOffersService API to get a list of personalized loan offers you can present to your users. 

  In this case, you will collect the information above, construct a PMIBorrowerInfo object with this information, and pass the object in a call to the ProspectOffersService API. You can then display one or more personalized loan offers returned by the API within your app. When a user selects the offer, you will launch the SDK with that selected loan offer. The Prosper Borrower SDK will take care of the rest.







##Prosper Borrower SDK collects user information
If you cannot or do not want to collect the 13 pieces of user information to generate Prosper loan offers, you can launch the Borrower SDK, allowing Prosper to collect this information, and present the loan offers to the user.

Just invoke the Prosper Borrower SDK registering an intent, passing a null value for borrowerInfo, and 0 for the userSelectedOffer.

```
Intent prosperIntent = ProsperIntent.getCollectUserInfoIntent(MainActivity.this);
startActivityForResult(prosperIntent, ProsperActivity.LOAN_APPLICATION_REQUEST);
```

The Prosper Borrower SDK will then take control, guiding the user through several screens, collecting the 13 pieces of information, requesting that the user create a Prosper account, and presenting a set of pre-approved personalized loan offers the user can select from. 

###User selects and completes the Prosper loan offer
Pre-approved loan offers generated by the Prosper Borrower SDK require user consent and user acceptance of the Prosper Marketplace Terms and Conditions for the loan to become an active listing for funding. 

Once the loan offer is selected, the Prosper Borrower SDK guides the user through the process, displaying Prosper Marketplace Terms and Conditions and requiring consent and acceptance as part of the user experience.







##Your app collects user information
The Prosper Borrower SDK provides an API to get a list of personalized loan offers for your users. Getting a list of loan offers is a two-step process.

###Step 1: Collect loan applicant information

To generate the loan offers, Prosper requires 13 pieces of user information about the loan applicant:
 * Loan Amount
 * Loan Purpose
 * Self-Reported Credit Score
 * First Name
 * Last Name
 * Date Of Birth (DOB)
 * Email
 * Street Address
 * City
 * State
 * Zip
 * Employment Status
 * Annual Income

Once you collect this information from your user, you construct a PMIBorrowerInfo object for the loan applicant. 

```
PMIBorrowerInfo borrowerInfo = new PMIBorrowerInfo.PMIBorrowerBuilder()
   .setLoanAmount(20000)
   .setLoanPurpose(ProsperConfig.LoanPurpose.DEBT_CONSOLIDATION)
   .setCreditScore(760)
   .setEmploymentStatusId(ProsperConfig.EmploymentStatusId.EMPLOYED)
   .setAnnualIncome(95000)
   .setEmail("user@gmail.com")
   .setFirstName("Walter")
   .setLastName("White")
   .setAddress1("3828 Piermont Dr ")
   .setCity("Albuquerque")
   .setState("NM")
   .setZip("87112")
   .setDateOfBirth("09/07/1969")
   .build();
```
**Note:** For a full list of values that can be entered for LoanPurpose and EmploymentStatus, refer to the ProsperConfig.java file bundled with the Prosper Borrower SDK.

###Step 2: Call the ProspectOffersService to get loan offers

```
   ProspectOffersService.getInstance().getoffers(borrowerInfo, new ProsperConfig.OffersCallback() {
      @Override
      public void success(PMIListedOffers offers) {
        Log.d(TAG, "Got offers");
      }

      @Override
      public void failure(String s) {
            Log.d(TAG, "Failed to get offers");
      }

       });
  }
```

OffersCallback returns an Offers list object. The list contains different loan offers that the user has been pre-approved for. These offers range between different interest, APRs and terms. It is up to your development team to determine the offers you want displayed to the user.

To access a particular offer by index, you can do something like this in the OffersCallback success:

```
  @Override                 
    public void success(PMIListedOffers offers) {
     PMIOffer offer = offers.getOffers().get(0);
    } 
```

###Invoke the Prosper Borrower SDK and complete the loan offer###
Pre-approved loan offers generated by the Prosper Borrower SDK require user consent and user acceptance of the Prosper Marketplace Terms and Conditions for the loan to become an active listing for funding. 

The Prosper Borrower SDK guides the user through the process, displaying Prosper Marketplace Terms and Conditions and requiring consent and acceptance as part of the user experience. You can invoke the Prosper Borrower SDK when a user selects an offer by registering an intent:

```
Intent prosperIntent = ProsperIntent.getUserInfoProvidedIntent(MainActivity.this, borrowerInfo, userSelectedOffer);
startActivityForResult(prosperIntent, ProsperActivity.LOAN_APPLICATION_REQUEST);
```


In the above intent, the userSelectedOffer is the LoanOfferId object, which is the loan offer your user selects from the loan offers list.


#Activity result codes
The ProsperActivity will return the following result codes:

| Code  | Description |
| ------------- | ------------- |
| Activity.RESULT_OK  | The user successfully completed the loan application process with Prosper.  |
| Activity.RESULT_CANCELLED  | The user cancelled the loan application process.  |
| ProsperActivity.RESULT_TIMED_OUT  | The user session timed out due to inactivity.  |


#Sample app
A sample app can be found in the ProsperBorrowerSDKSample folder. The sample app demonstrates how you can integrate the Prosper Borrower SDK into your own app. 

You can use the instructions within this guide to see how the SDK works. Remember to request app keys before trying out the sample app. You'll need them to run the sample app.


#Contact us
Do you have any questions, comments for us, or problems integrating the Prosper Borrower SDK into your app? 

Contact us at mobileSDK@prosper.com


#Legal notices
1.1 Confidential Information. The integrator of the SDK (“Integrator”) and their respective affiliates, directors, officers, employees, authorized representatives, agents and advisors shall keep confidential all information concerning proprietary business procedures, products, services, operations, software development kits (“SDKs”), marketing materials, fees, policies or plans and all Nonpublic Personal Information that is received or obtained from Prosper Marketplace Inc. (“Prosper”) prior to or during the integration relationship, whether such information is oral or written, and whether or not labeled as confidential (collectively “Confidential Information”). “Nonpublic Personal Information” shall include all personally identifiable financial information and any list, description or other grouping of consumers, and publicly available information pertaining to them, that is derived using any personally identifiable financial information that is not publicly available, and shall further include all “nonpublic personal information” as defined by federal regulations implementing the Gramm-Leach-Bliley Act, as amended from time to time. “Personally identifiable financial information” means any information a consumer provides to the Integrator or Prosper in order to obtain a financial product or service, any information Integrator or Prosper otherwise obtains about a consumer in connection with providing a financial product or service to that consumer, and any information about a consumer resulting from any transaction involving a financial product or service between the Integrator or Prosper and a consumer. Personally identifiable information may include, without limitation, a consumer's first and last name, physical address, zip code, email address, phone number, social security number, birth date, and any other information that itself identifies or when tied to the above information, may identify a consumer.

1.2 Use of Confidential Information. For as long as Confidential Information is in possession of the Integrator, the Integrator shall take reasonable steps, at least substantially equivalent to the steps it takes to protect its own proprietary information, to prevent the use, duplications or disclosure of Confidential Information, other than, by or to its employees or agents who are directly involved in integrating the Prosper SDKs and who are apprised of their obligations required by these Legal Notices and directed by the Integrator to treat such information confidentially, or except as required by law or by a supervising regulatory agency of the integrator. The Integrator shall not disclose, share, rent, sell or transfer to any third party any Confidential Information. The parties shall use Confidential Information only as necessary to perform the integration of the Prosper SDKs.  The Integrator shall treat any Nonpublic Personal Information that it receives from the other party in a manner that is fully compliant with the disclosing party's obligations under Title V of the Gramm-Leach-Bliley Act and any implementing regulations thereunder, including but not limited to applicable limits on the use, disclosure, storage, safeguarding and destruction of Nonpublic Personal Information.  In addition, to the extent that the Integrator receives Nonpublic Personal Information, the Integrator shall maintain commercially reasonable data security and disaster recovery protections that at the least are consistent with industry standards for the consumer lending industry.

1.3 Return of Information; Indemnity. Upon the termination or expiration of the integration of the Prosper SDKs, the Integrator shall promptly return all Confidential Information received in connection with the integration, or shall promptly destroy any materials containing such information (and any copies, extracts, and summaries thereof) and shall provide Prosper with written confirmation of such return or destruction upon request. In the event the Integrator discovers that Confidential Information has been used in an unauthorized manner or disclosed in violation of the Legal Notices, the Integrator discovering the unauthorized use or disclosure shall immediately notify Prosper of such event, and the Integrator of the Prosper SDKs shall indemnify and hold Prosper harmless from all claims, damage, liability, costs and expenses (including court costs and reasonable attorneys' fees) arising or resulting from the unauthorized use or disclosure. In addition, Prosper shall be entitled to all other remedies available at law or equity, including injunctive relief. 

2.1 Ownership.  Notwithstanding any other language or information within the SDK Integration Guide, the Integrator acknowledges and agrees that Prosper shall retain sole and exclusive ownership of the Prosper SDKs and related materials and all intellectual property rights therein.  Prosper acknowledges and agrees that the Integrator shall retain sole and exclusive ownership of the integrator’s background technology that existed prior to integration and all intellectual property rights therein.

2.2 Reservation of Rights.  Prosper and Integrator shall continue to own all rights, title and interest in and to its patents, know-how, trade secrets, software, trademarks and all other intellectual property, subject only to the license rights expressly granted herein.

3.1 SDK API Key and Secret Security.   When using the Prosper SDKs, the Integrator shall follow these specified essential security protocols.

3.1.1  The Integrator shall only use the API key and secret provided to you by Prosper; specifically, the Integrator shall not communicate with the SDK using keys or secrets that are not authorized by Prosper for your use.

3.1.2 THE INTEGRATOR IS SOLEY RESPONSIBLE FOR MAINTAINING ADEQUATE SECURITY AND CONTROL OF ANY API KEY AND SECRET COMBINATION PROVIDED TO YOU.  BECAUSE THE KEY AND SECRET ENSURE AUTHORIZED COMMUNICATIONS BETWEEN THE INTEGRATOR AND PROSPER, THE INTEGRATOR MUST PROTECT THE KEY AND SECRET, ALLOWING ONLY AUTHORIZED AND AUTHENTICATED ENTITIES, SUCH AS INDIVIDUALS, APIS, CODE, ETC., TO ACCESS THE KEY AND SECRET.

4.1 Indemnification by Integrator of Prosper SDKs.  Integrator shall indemnify, defend and hold Prosper harmless from and against any Claim that is attributable to or arises from (i) Integrator’s violation of any state or Federal law, rule or regulation, or any other illegal or actionable act or omission by or on behalf of Integrator; (ii) Integrator’s breach of any material obligation owed to Prosper; and (iii) any acts or omissions by Integrator, its employees or its agents, in connection with Integrator’s marketing efforts or efforts related to the protection of data privacy or security keys.  Integrator agrees to promptly pay and fully satisfy any and all losses, judgments or expenses, including, without limitation, costs of settlement and attorneys’ fees incurred or sustained, or reasonably likely to be incurred or sustained by Prosper as a result of any claims of the types described above.

4.2 Procedures.  Prosper shall: (i) promptly notify the Integrator in writing of any losses for which Prosper seeks indemnification; (ii) provide reasonable cooperation to the Integrator and its legal representatives in the investigation of any matter which is the subject of indemnification; and (iii) permit Integrator to have full control over the defense and settlement of any matter subject to indemnification; provided, however, that the Integrator shall not enter into any settlement that affects Prosper’s rights or interests without Prosper’s prior written consent, which shall not be unreasonably withheld or delayed.  Prosper shall have the right to participate in the defense at its expense.

