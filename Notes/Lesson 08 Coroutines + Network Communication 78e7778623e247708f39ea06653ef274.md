# Lesson 08 | Coroutines + Network Communication

Created: August 29, 2022 8:58 AM

## Problem description

Because network communication takes a long time, we have to do it using the other threads or a new way - **Coroutines**.

Coroutines are much more lightweight than threads. Moreover, in contrast to multithreading, coroutines can **suspend** the functions and **switch** between different threads. However, these have also disadvantages - their functionalities are not protected from each other at any point.

## Asynchrony

The coroutines operations are considered to be **asynchronous** - there is no synchronized clock that dictates the time in which the messages can be sent and received. The two elements have to establish the correct way of communication.

Using the coroutines we can make our asynchronous code look like synchronized. Using them, we don’t have to worry about handlers - callback functions.

## Implementation

![Untitled](Lesson%2008%20Coroutines%20+%20Network%20Communication%2078e7778623e247708f39ea06653ef274/Untitled.png)

It looks like the withContext() function will block the main thread. In reality, **it doesn’t block the main Thread!** It only blocks the following coroutine. What happens in the code is the function get() does some time consuming operations on another thread. The coroutine waits for it to return the value to the result variable and suspends its work and the main thread can run. After the value is returned, the next operations of the coroutine are executed.

## withContext()

This function does the same as combination of async() and await() functions. It creates a new coroutine and deals with the synchronization.

![Untitled](Lesson%2008%20Coroutines%20+%20Network%20Communication%2078e7778623e247708f39ea06653ef274/Untitled%201.png)

### launch() + withContext() example

![Untitled](Lesson%2008%20Coroutines%20+%20Network%20Communication%2078e7778623e247708f39ea06653ef274/Untitled%202.png)

## async() + await()

If we have more than one operation to do in the same time (f.ex: 2 web API calls) it is better to use **async() + await()** instead of withContext()

![Untitled](Lesson%2008%20Coroutines%20+%20Network%20Communication%2078e7778623e247708f39ea06653ef274/Untitled%203.png)

By using **async()** function, we can make more than one operation and make then asynchronised (make then work in the same time). 

## Network Debugging

Android Studio uses a very elaborate **Network inspector** which can be used to analyze our application’s runtime behaviour. It works only for **http** and **https** protocols.