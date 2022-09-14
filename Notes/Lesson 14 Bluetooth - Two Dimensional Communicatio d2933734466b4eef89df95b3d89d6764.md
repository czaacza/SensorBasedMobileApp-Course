# Lesson 14 | Bluetooth - Two Dimensional Communication

Created: September 5, 2022 9:08 AM

## Bluetooth architecture

A peripheral can only be connected to one central device (such as a mobile phone) at a time

![Untitled](Lesson%2014%20Bluetooth%20-%20Two%20Dimensional%20Communicatio%20d2933734466b4eef89df95b3d89d6764/Untitled.png)

If one peripheral device wants to communicate with other peripheral device, he need to use the central device to do that.

## Tech side

### Bluetooth architecture is based on layered protocol.

- **HCI** is often used in external Bluetooth device communication
    
    ( Bluetooth module ↔ Processor module )
    
- **GATT** and **GATT** Profiles makes it easy to provide services and data to clients.

### Robustness

- Adaptive fast **frequency** **hopping**
    
    ( selects channels and changes them all the time )
    
- **Forward** **Error** **Correction** (FEC)
    
    ( if we have some transmission errors, we are still able to decide which message to send )
    
- **Fast** **Acknowledge** (ACK) to the sender

### Total time to send data is typically 50ms

- Some special hardware can reach 6ms
- The new Bluetooth 5.2 LC3 codes can reach 5ms

### Bluetooth documents (bluetooth.com)

- Specifies the **core** **specification**

We can think about **attributes** (ATT) as a **data**.

## Generic Attribute Profiles (GATT)

- specifies how the tech is used, and what is the purpose of the data
- How different parts of the spec shall be used to fulfill a desired function
- e.g: battery level detector, heart rate monitor

GATT is based on the sever/client relationship.

**peripheral - GATT Server**

**phone - GATT Client**

All transactions are started by the **master** device, the **GATT Client**, which receives response from the **slave** device, the **GATT Server**

When establishing a connection, the peripheral will **suggest** a '**Connection Interval**' to the central device, and the central device will try to reconnect every connection interval to see if any new data is available, etc.

![Untitled](Lesson%2014%20Bluetooth%20-%20Two%20Dimensional%20Communicatio%20d2933734466b4eef89df95b3d89d6764/Untitled%201.png)

## GATT transactions

Gatt transactions are based on high-level objects called **Profiles, Services** and **Characteristics**.

- HTTP communication - URLs and ports
- Bluetooth communication - services and characteristics

![Untitled](Lesson%2014%20Bluetooth%20-%20Two%20Dimensional%20Communicatio%20d2933734466b4eef89df95b3d89d6764/Untitled%202.png)

### Profiles

A Profile doesn't actually exist on the BLE peripheral itself, it's simple a **pre-defined collection of Services** that has been compiled by either the Bluetooth SIG or by the peripheral designers

The Heart Rate Profile for example, combines the **Heart Rate Service** and the **Device Information Service**

### Services

Services are used to break data up into logic entities and contain specific chunks of data called characteristics.

A service can be distinguished by its unique numeric ID - **UUID**

E.g: Hearth rate Service contains up to 3 characteristics from which one is mandatory.

### Characteristics

The lowest level concept in GATT transactions is the Characteristic which encapsulates a single data point.

Similar to Services, each Characteristics distinguishes itself via **UUID**.

E.g: **Heart Rate Measurement characteristic** is **mandatory** for the Hearth Rate Services, and uses a UUID of 0x2A37

## Indication and Notification

They are commands that could be send through the **attribute** **(ATT) protocol**.

BLE standard define two ways to transfer data from the server to the client - Notification and Indication.

**Notification** **doesn’t need acknowledged**, so they are faster. However, the server does not know if the message reached the client.

**Indication needs acknowledge** to communicated. The client sends a confirmation message back to the server, so it knows what message has reached the client.

![Untitled](Lesson%2014%20Bluetooth%20-%20Two%20Dimensional%20Communicatio%20d2933734466b4eef89df95b3d89d6764/Untitled%203.png)

## Making a connection in Android

From the Bluetooth LE scan, you will have the **ScanResult** object.

**ScanResult** object contains a field **BluetoothDevice**.

This object can be used to make a connection to the device.

![Untitled](Lesson%2014%20Bluetooth%20-%20Two%20Dimensional%20Communicatio%20d2933734466b4eef89df95b3d89d6764/Untitled%204.png)

When the connection is established, the method **onConnectionStateChange()** with the status STATE_CONNECTED is called.

Then it is possible to see what kind of services are available from connected device by calling **discoverServices()** method.

![Untitled](Lesson%2014%20Bluetooth%20-%20Two%20Dimensional%20Communicatio%20d2933734466b4eef89df95b3d89d6764/Untitled%205.png)

When you have found the service you are interested in, it is time to ask notifications from there.

You can search through the services by searching for their UUID.

![Untitled](Lesson%2014%20Bluetooth%20-%20Two%20Dimensional%20Communicatio%20d2933734466b4eef89df95b3d89d6764/Untitled%206.png)

**setCharacteristicsNotification()** asks the client to start sending notifications as intervals.