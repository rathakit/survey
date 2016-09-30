# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/rathakit.nop/Library/Android/sdk/tools/proguard/proguard-android.txt
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

# Retrofit 2.X
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# OKIO
-dontwarn okio.**

# Picasso
-dontwarn com.squareup.okhttp.**

####### [ButterKnife - START] ######
# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(...); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }
####### [ButterKnife - END] ######

# Fixes the warning of "Ignoring InnerClasses attribute for an anonymous inner class
# that doesn't come with an associated EnclosingMethod attribute." while using Proguard.
-keepattributes EnclosingMethod