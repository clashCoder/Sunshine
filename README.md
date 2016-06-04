# Sunshine
An Android app that is completed while taking the free Udacity course [Developing Android Apps: Android Fundamentals] (https://www.udacity.com/course/developing-android-apps--ud853). This version of Sunshine uses the OpenWeatherMap API to view a 14 day forecast of a US city given it's zipcode. 

##Motivation
The main purpose of this project was to get a good foundation in Android Development. Hopefully more projects shall be made in the future!!!

##Installation
Install Android Studio if you do not have it already installed. Check out the [android developer website](https://developer.android.com/studio/index.html) for instructions on how to insall Android Studio on your operating system. 

Download this repo:

> git clone https://github.com/clashCoder/Sunshine.git

Checkout the following branch **6.01_Notifications**. You can do so by typing in the following command (within the directory that you downloaded this repo):

> git checkout 6.01_Notifications

Obtain a Weather Map API Key from [openweathermap.org](http://openweathermap.org/api). This Key is necessary to have access to the OpenWeatherMap API and thus necessary to be able to see weather data in the app.

Open Android Studio and import the code into Android Studio. Open the file SunshineSyncAdapter.java. You will insert the API Key obtained previously within this java file. Within onPerformSync(...), find the String **appid** and initialize **appid** to your API Key.  

Use Android Studio to install the app on an Android device or on an Android emulator. 

View Sunshine!!!

##License
This version of Sunshine is licensed under the [Apache License](LICENSE).

##Comments
Feel free to comment on any bugs that are found in the app (will be much appreciated) and on any improvements that could be made to the app that will improve it.