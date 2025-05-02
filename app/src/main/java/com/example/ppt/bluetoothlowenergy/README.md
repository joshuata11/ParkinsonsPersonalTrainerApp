# BLEScanner

This is the part of the application that searches for blueooth devices, connects to the device, and recevies the data from the senors.

This is done using Bluetooth Low Energy from Android.

You can read all about it here https://developer.android.com/develop/connectivity/bluetooth/ble/ble-overview

For receving the data we need to define channels that the sensors are going to be sending data to. 

This is done in the sensors_enums package.

When a change is detected on these characteristics the data is read by the app and written to a file if the checkbox is checked. 


# BLEDeviceData

This class is just an object that holds information about everything related to Bluetooth. 

This is done so we can access information about the Bluetooth from any class. We need to do this because in Kotlin, if you are trying to access data in a class, its not gurranted that you will get the same instance that you are actually trying to reference.

For example if you connect to a device and want to access the name of that device, you would think you could just use BLEScanner().getName(). But this is not gurranted to work becuase this may create a new instance where the device information is not stored.

This is why we use the object class BLEDeviceData. This data can be accessed anywhere. It just needs to be set first. 
