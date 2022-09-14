# Lesson 16 | Data Storage

Created: September 7, 2022 2:06 PM

## Data Storage on Android

- Shared Preferences
- Internal Storage
- External Storage
- SQLite Room Database
- Media
- Storage Access Framework
- Content providers
- Network Connection
- Data Backup
- FileProvider API

## File

You can store files directly on the mobile device.

To read data from a file, Kotlin inherit standard Java classes like **java.io.file** or **BufferedInputStream**.

It also adds similar i/o methods, like **byteInputStream()** and **readLine()**.

## Byte Streams

A stream is a sequence of data.

We use **inputStream** and **outputStream** to read/write binary data to the files.

## Character Streams

If we are working with files, we should use **readers** and **writers** to read and write information.

## Stream Chaining

We use stream chaining by connecting a few stream classes together. 

Related chaining classes:

**FileReader and FileWriter**
For reading from or writing to files.

**BufferedReader and BufferedWriter**
For buffered reading/writing to reduce disk access for more efficiency.

**FileInputStream and FileOutputStream**
For reading streams from or writing streams to files.

**InputStreamReader and OutputStreamWriter**
Provide a bridge between byte and character streams.
The only purpose of these classes is to convert byte data into character-based data according to a specified (or the platform default) encoding.

## Internal Storage

Each application has its own internal storage to save files. We use it if we want no other application to access our files. 

It is specified by an operating system, therefore we can only use the following commands:

Write to file: **openFileOutput()**

Read from file: **openFileInput()**

Example:

![Untitled](Lesson%2016%20Data%20Storage%20d0b9950088f144ceb01a5881e95faaff/Untitled.png)

The destination of saved file is predefined by the OS.

## External Storage

External storage is a place for which every other applications have access to