# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/alatta/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#
#-keep class com.prosper.androidsdk.**{ *; }
#-keep class retrofit.** { *; }
#-keep class com.squareup.okhttp.** { *; }
#-keep interface com.squareup.okhttp.** { *; }
#-dontwarn okio.**
#-dontwarn com.adobe.**
#-dontwarn com.google.**

-keep class com.prosper.androidsdk.external.** { *; }
-keep class retrofit.** { *; }
-keep class retrofit.http.** { *; }
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class com.google.android.gms.** { *; }


-keepattributes Signature
-dontwarn okio.**
-dontwarn retrofit.**
-dontwarn com.adobe.**
-dontwarn com.google.android.gms.**