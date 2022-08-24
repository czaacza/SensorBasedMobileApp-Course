# Lesson 03 | Networking and Multithreading

Created: August 23, 2022 10:17 AM

# Networking communication in Java/Kotlin

Network communication is included in java/kotlin language.

Digital communication in its nature is a stream of zeros and ones (bites)

![Zrzut ekranu 2022-08-23 o 10.22.19.png](Lesson%2003%20Networking%20and%20Multithreading%20b2aac26c6e6641e68038d12c25bc3574/Zrzut_ekranu_2022-08-23_o_10.22.19.png)

Information stream chain

![Zrzut ekranu 2022-08-23 o 10.23.21.png](Lesson%2003%20Networking%20and%20Multithreading%20b2aac26c6e6641e68038d12c25bc3574/Zrzut_ekranu_2022-08-23_o_10.23.21.png)

**InputStream** - Gets the lowest code (bit code) and changes it into Byte stream

**Buffer** - temporary container for the returned information, it speeds up the process of requesting and sending the information. We can use them when we want to recieve the information in smaller parts (ex. in some time cycles)

## Using networking capabilities

### How to fetch the data from the api?

1. **Create a URL object instance**
    
    val myURL = URL(”htttps://www.a.com/data.txt”)
    
2. **Create URLConnection instance**
    
    val myConn = myURL.openConnection()
    
3. **Get the input stream**
    
    val iStream =myConn.getInputStream()
    
4. **Read the stream with reader**
    
    val allText = iStream.bufferedReader().use(it.readText()))
    
    val result = StringBuilder()
    
    result.append(allText)
    
    val str = result.toString()
    

**Remember to add the internet permission in the manifest**

<uses-permission android:name="android.permission.INTERNET" />

## Network Availability

**Connectivity Manager** class answers queries about the state of network connectivity. It also notifies applications when network connectivity changes 

([https://developer.android.com/reference/android/net/ConnectivityManager](https://developer.android.com/reference/android/net/ConnectivityManager)) 

**isDefaultNetworkActive()** – method returns Boolean value whether the data network is currently active or not

### Network Security

- Use TSL/SSL traffic
- Use HTTPS protocol instead of HTTP
- See [https://developer.android.com/training/articles/security-ssl](https://developer.android.com/training/articles/security-ssl)

### Do not run time consuming operation in the main thread!

Remember not to block the main UI thread because the application does not then response to the user input.

Use:

- Multithreading + Handler()
- Coroutines - the newest way to handle multithreading - it is not connected with system

## What happens when Android app module is started?

a new process is created

Main thread is created

onCreate() method gets executed

Looper with message queue is set up

# Threads

A **Thread** is a programming implementation of parallels. They are used in creating applications that perform multiple tasks in a manner that appears simultaneous.

All the threads share **the same memory**. It’s different to processes, where each process uses its own part of memory.

## Kotlin threads

We have an extension function for creating threads

thread() {

println("${Thread.currentThread()} has run.")

}

When using threads, we need to know how to **synchronize** them.

The synchronization of threads prevents the threads to interrupt each other. So it prevents the situation when one thread makes an operation on the same memory (variable) when the previous thread hasn’t still ended its work.

We use synchronize when 2 or more threads work for the same memory (variable).

To use synchronization we use **@Synchronized** tag

# Android Handler

….

## Reading List

- Java/Kotlin processes and threads
- – http://docs.oracle.com/javase/tutorial/essential/concurrency/procthread.
html
- – http://docs.oracle.com/javase/tutorial/essential/concurrency/threads.html
- – http://docs.oracle.com/javase/tutorial/essential/concurrency/sync.html
- – Multithreading and Kotlin. I’ve been wanting to follow up on my... | by
Korhan Bircan | Medium
    - Networking
- – http://developer.android.com/training/basics/network-ops/
connecting.html
- – (http://developer.android.com/training/basics/network-ops/
managing.html)