# Lesson 13 | Internal Sensors

Created: September 2, 2022 2:06 PM

## Intro

The Android platform supports three main categories of sensors:

- Motion sensors
- Environmental sensors
- • Position sensors

Any Input/Output capabilities can be transformed into a sensor:

## Sensor Framework

The sensor framework includes the following four main classes and interfaces

![Untitled](Lesson%2013%20Internal%20Sensors%20b0693967ca71454aaded308e5c1d5439/Untitled.png)

![Untitled](Lesson%2013%20Internal%20Sensors%20b0693967ca71454aaded308e5c1d5439/Untitled%201.png)

## Manifest

Remember to add the appropriate text in the manifest. e.g for the magnetometer:

![Untitled](Lesson%2013%20Internal%20Sensors%20b0693967ca71454aaded308e5c1d5439/Untitled%202.png)

## Initialization

![Untitled](Lesson%2013%20Internal%20Sensors%20b0693967ca71454aaded308e5c1d5439/Untitled%203.png)

## Monitor events

Monitor system events best practises:

- Don’t block the onSensorChanged() method
- To avoid to waste system resources and battery power, choose
a delivery rate that is suitable for your application or use-case
when registering the event listener with
**registerListener()** method.

### Events listeners

![Untitled](Lesson%2013%20Internal%20Sensors%20b0693967ca71454aaded308e5c1d5439/Untitled%204.png)

![Untitled](Lesson%2013%20Internal%20Sensors%20b0693967ca71454aaded308e5c1d5439/Untitled%205.png)

### Register/unregister event listeners

![Untitled](Lesson%2013%20Internal%20Sensors%20b0693967ca71454aaded308e5c1d5439/Untitled%206.png)

## Coordinate system

Some sensor return the data in the standard 3-axis coordinate system

![Untitled](Lesson%2013%20Internal%20Sensors%20b0693967ca71454aaded308e5c1d5439/Untitled%207.png)

## Motion sensors

Two of the Motion Sensors are always hardware-based

- accelerometer
- gyroscope

Three of the Motion Sensors can be either hardware-based or
software-based

- gravity
- linear acceleration
- rotation vector

## Position Sensors

Two of the Position Sensors are always hardware-based

- geomagnetic field sensor
- proximity sensor
- The accelerometer is also used as one of the Position Sensors.

## Environment Sensors

All four Environment Sensors are always hardware-based

- relative ambient humidity
- illuminance
- ambient pressure
- ambient temperature