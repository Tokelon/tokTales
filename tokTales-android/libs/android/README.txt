These files can be included so that Eclipse can resolve them as dependencies for Android.


#android
--sdk\platforms\android-26
android.jar [android.jar]


#appcompat-v7 
--sdk\extras\android\m2repository\com\android\support\appcompat-v7
appcompat-v7-26.0.0-alpha1.aar -> \classes.jar [android-appcompat-v7-classes.jar]


#support-v4
--sdk\extras\android\m2repository\com\android\support\support-v4
support-v4-24.1.1.aar -> \libs\internal_impl-24.1.1.jar [android-support-v4-internal.jar]
support-v4-24.1.1.aar -> \classes.jar [android-support-v4-classes.jar]
