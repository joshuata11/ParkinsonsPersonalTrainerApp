####### INSTALLATION 

In order to install the app to your phone there are a few steps you need to take


1. Download Android studio https://developer.android.com/studio

2. Clone the main branch of this repository either using GitHub Desktop, or from using git command lines git clone https://github.com/joshuata11/ParkinsonsPersonalTrainerApp.git

3. Plug your Android phone into your computer and make sure to enable USB debugging in your phones settings

4. Once the project is open in android studio make sure Android studio recognizes your phone by looking in the top right near the run button. It should say the name of your phone.

5. You should then hit the run button and the app will download to your phone


####### APP USE

1. When first opening the app you will be prompted to create an account, enter your login information and the create an account

2. You will then be brought to the home page and you need to enable location settings and allow bluetooth

3. MAKE SURE TO HAVE BLUETOOTH ENABLED BEOFORE YOU OPEN THE APP OR IT MAY CRASH

4. You can then connect to the sensors VIA the settings page and under the sensor settings button

5. Hit connect sensor and your device should find the PPT sensors in your area, if not try to turn Blueooth on and off again, sometimes it cannot find the device in the short amount of time it scans.

6. Once connected to the app you will start collecting data, to save this data to a file you need go to a workout VIA the workout page.

7. Once you select a workout, you can check the button that says "Start Collecting Data" once this is checked the data from the sensors will be written to a file

8. When you uncheck the check box the file will be saved into your phones internal storage located in Download/mySensorData


##LOGIN SYSTEM

If you forget your login information you can just open the app info on your device and clear the storage and the cache, this will prompt you to make a new account.

Currently the app does not have a proper "Account System" VIA a database, the account is for your single device and can prevent others from using the app without the proper information on your phone. 

An actual account system should be created in future development 

   
