# Lesson 12 | Bluetooth Beacons

Created: September 1, 2022 9:06 AM

## Bluetooth Tech side

- **Universal Wireless Standard -** specifies how the things are done, thanks for that, devices from different producers can communicate with each other
- **Small** and **cheap** Low Power Wireless
    1. 2.4 GHz ISM band (license free - everybody can use it)
    2. 79 radio channels of 1 MHz wide - the channel set action is automatic. 
    3. Low energy version uses only 40 channels.
- **Adaptive Frequency Hopping** - the channels (frequencies) to which we are connected are being changed all the time. This is useful when our channels have some noises. If the system finds an error, it adapts its behaviour to the situation.
- **Secure** (AES encryption)
- Native Bluetooth support to Android 2013

## Bluetooth Network Topology

![Untitled](Lesson%2012%20Bluetooth%20Beacons%20c2d3ac801a044bc2a8ea7f27d1d304e6/Untitled.png)

Master can request when he want to receive the data. This means that the slaves can sleep for most of the time - it saves energy.

## Beacon Tech side

Beacons are Bluetooth LE-transmitters that send some information or advertising data that can be accepted by the smartphones and tablets within the range of the transmitter. Their task is simple—serially send data packages (advertisement packets)

![Untitled](Lesson%2012%20Bluetooth%20Beacons%20c2d3ac801a044bc2a8ea7f27d1d304e6/Untitled%201.png)

## UUID

A universally unique identifier is a 128-bit randomly selected number. They are nothing more than just unique 128-bit numbers: 

`75BEB663-74FC-4871-9737-AD184157450E`

# Android Bluetooth

Android provides Bluetooth API to perform the following different operations

- Scan for Bluetooth devices
- Get a list of paired devices
- Connect to other devices through service discovery

Android provides **BluetoothAdapter** class to communicate with Bluetooth

An object of this class can be created by calling the static method:

![Untitled](Lesson%2012%20Bluetooth%20Beacons%20c2d3ac801a044bc2a8ea7f27d1d304e6/Untitled%202.png)

## Permissions

You need to give static permission to the Android application to use the Bluetooth interface

In order to allow an Android device to search nearby Bluetooth devices, manifest must contain right to allow give location information to outside

![Untitled](Lesson%2012%20Bluetooth%20Beacons%20c2d3ac801a044bc2a8ea7f27d1d304e6/Untitled%203.png)

It is also mandatory to setup runtime permissions by the user (asked only at the first time you run the application)

![Untitled](Lesson%2012%20Bluetooth%20Beacons%20c2d3ac801a044bc2a8ea7f27d1d304e6/Untitled%204.png)

## Finding BLE Devices

Finding bluetooth devices is totally asynchronous operation. That is why we use the coroutines for that.

![Untitled](Lesson%2012%20Bluetooth%20Beacons%20c2d3ac801a044bc2a8ea7f27d1d304e6/Untitled%205.png)

![Untitled](Lesson%2012%20Bluetooth%20Beacons%20c2d3ac801a044bc2a8ea7f27d1d304e6/Untitled%206.png)

### Callback function

This callback is called if the scan finds a BLE device

![Untitled](Lesson%2012%20Bluetooth%20Beacons%20c2d3ac801a044bc2a8ea7f27d1d304e6/Untitled%207.png)

## Connect with composable

You can use LiveData to transfer information from the ViewModel to the Composable function

![Untitled](Lesson%2012%20Bluetooth%20Beacons%20c2d3ac801a044bc2a8ea7f27d1d304e6/Untitled%208.png)

## Reading List

- Bluetooth technical specification
[http://www.bluetooth.org](http://www.bluetooth.org/)
- Good explanation for Bluetooth broadcast advertisement
[https://www.novelbits.io/bluetooth-low-energy-advertisements-part-1/](https://www.novelbits.io/bluetooth-low-energy-advertisements-part-1/)
- Bluetooth in Android
[https://www.tutorialspoint.com/android/android_bluetooth.htm](https://www.tutorialspoint.com/android/android_bluetooth.htm%E2%80%93) [https://developer.android.com/guide/topics/connectivity/bluetooth/bleoverview](https://developer.android.com/guide/topics/connectivity/bluetooth/bleoverview)
[https://punchthrough.com/android-ble-guide/](https://punchthrough.com/android-ble-guide/)
- Eddystone beacon
[https://github.com/google/eddystone](https://github.com/google/eddystone)