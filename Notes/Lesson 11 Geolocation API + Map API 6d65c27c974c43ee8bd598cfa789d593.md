# Lesson 11 | Geolocation API + Map API

Created: August 31, 2022 2:02 PM

# Geolocation APIs

## Intro

Since mobile users take their phones almost everywhere, adding location awareness to your app offers users a more contextual experience

## Android Location Strategies

**Global Navigation Satellite System** (GNSS) and 

S**atellite-Based Augmentation Systems** (SBAS): GPS, GLONASS, Galileo, BeiDou, NavIC, QZSS

They are:

- **very precise** (about 3 to 15 meters, 1 meter with Galileo, L5
band GPS receiver up to 30 centimeters)
- provides geolocation and time information anywhere on or near
the Earth (e.g. flying, middle of ocean,…)
- works in offline mode (and in airplane mode)
- only works outdoor or close to window (can even have problems
e.g. in a street with big building, in deep forest, high rocks,…
- can be turned off by user
- consumes more battery
- **can be slow**
- takes time to get location when turned on (up to 1 minute)

**Network based: Cell-ID, Wi-Fi**

- **fast**
- low battery consumption (no extra to normal network tasks)
- **varying in precision** (about 10 to 300 meters)
- is almost always available (except in airplane mode, in the middle of the ocean,…)
- works inside building

## Android Location VS Google Play API

### **android.location**

- native in Android framework

### Google Play services location APIs (recommended)

- automates tasks such as location provider choice or power management
- adds activity detection
- requires network conneciton between device and Google services

## Application permissions

Specify App Permissions in your manifest:

`ACCESS_COARSE_LOCATION` (network based location)
`ACCESS_FINE_LOCATION` (GPS (include network) based location)

If target Android 5.0 (API level 21) or higher, then also: 

`<uses-feature android:name="android.hardware.location.gps" />`

`<!-- and/or -->`

`<uses-feature android:name="android.hardware.location.network"/>`

If target Android 10 (API 29) or higher and want to access location data when the app is running in foreground, you must declare a foreground service type of location

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled.png)

If target Android 10 (API 29) or higher and want to track location when app is in background, then add permission 

`ACCESS_BACKGROUND_LOCATION`

For Android >= 6 (API 23), you need to request permissions at run time2 (or catch SecurityException)

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled%201.png)

For Android >= 11 (API level 30), if you request at run time the foreground and the background location permissions at the same time, the system ignores the request and doesn’t grant your app either permission. Request the permissions separately when first used in app!

## android.location

Implements android.location.LocationManager and override the method onLocationChanged() to get the current location of the device.

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled%202.png)

To request location updates, we use the following function:

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled%203.png)

## android.location.Geocoder

**Geocoding** is the process of transforming a street address or other location description into a (latitude, longitude) coordinates.

**Reverse geocoding** - transforming coordinates into location descriptions such as street address

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled%204.png)

## Google Play services location APIs

To implement it, you need to add Google Play Services to the project structure 

To get the latest location:

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled%205.png)

To request location change:

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled%206.png)

Google play services offer features such as:

- Create and monitor geofences
- Activity Recognition API
- Place API

More:
[https://developers.google.com/location-context/](https://developers.google.com/location-context/)

# Maps APIs

Maps APIs allow you to add map to you application. They handle:

- access to Maps servers and downloading tiles
- map display
- touch gestures
- add markers, polygons and overlays

etc.

## osmdroid

It uses Open Street Map data: 

### Use

Add mavenCentral repository to your project build.gradle

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled%207.png)

Add osmdroid dependencies to your app build.gradle

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled%208.png)

In most cases, you will have to set the following authorizations in your AndroidManifest.xml:

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled%209.png)

Make a compose map

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled%2010.png)

Create ids.xml in res/values

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled%2011.png)

To make our map more cleaner, we should add a **Lifecycle observer**

Add map properties

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled%2012.png)

Add a composable function showing the map

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled%2013.png)

Add markers/draw lines

![Untitled](Lesson%2011%20Geolocation%20API%20+%20Map%20API%206d65c27c974c43ee8bd598cfa789d593/Untitled%2014.png)

### OSMBonusPack

Using OSMBonusPack8 that complements osmdroid

- Routes and Directions
- Points of Interests (directory services)
- Marker Clustering
- Support for KML and GeoJSON content
- GroundOverlay