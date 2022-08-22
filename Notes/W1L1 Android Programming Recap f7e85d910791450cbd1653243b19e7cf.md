# W1L1 | Android Programming Recap

Created: August 22, 2022 5:25 PM
Tags: android

## **Some history**

Android was first developed by Andy Rubin. He made a system for mobile devices and sold it to Google at 2005. The first version was Android was introduced on 2007 - the same year Apple released the first iPhone. The first application development language was Java. It was the first mobile system including touch-screen after iOS.

**Every app runs with their own process -** A problem with one app doesn’t hurt the others!

For every new android platform version, google updates the API level by 1.

Current android platform version: **13**, API version: **33**

## **Android programming language - Kotlin**

Java code is being compiled by JVM, it produces the binary code then understood by ARM.

Kotlin code is able to use the same architecture of JVM. This makes it very easy to switch between Java and Kotlin. We can use the same libraries - however, the languages differ in their syntax.

After some time, Google adopted Kotlin as a preferred android development language.

## **Android studio**

You can connect your phone with Android Studio using a USB cable and setting ‚USB Debugging’ on my phone.

By clicking the ‚Logical’ tab, I can see all the information about what’s going on on my phone at the moment.

## **Application structure**

- **Manifest** - XML file that describes the name, icons, permissions. It describes the general information about the application.
- **Source code**
- **Resources** All the information separated from the source code
- **Build.gradle** The application creates it when running the application

When we run our app, the Android Studio will **generate** some files:

- R
- BuildConfig

**Never** manually edit these files!

## **Gradle**

An advanced build toolkit to compile app resources and source code and packages.

## **APK** - Android application package

A zip file containing manifest, source code, resources, compiled classes (.dix), assets and certificates

### **Each application:**

- Runs in its own process
- Runs in its own VM - isolation from other apps
- Has its own unique (linux) user ID - protection of app’s files/databases

### **Build APK**

Click Gradle on the right side of the IDE

Expand apps

## **Application components**

**Why android app uses components?**

Because thanks to that, one android application is able to use other application’s components

Android applications do not have only one entry point like a single main() function.

We are **not** calling any main function!

**Types of components**

- Activities - represents a single screen with a UI
- Services - handles processing at the background, no UI
- Broadcast Receivers - responds to system-wide broadcast announcements. It listens to the device’s operating system.
- Content Providers - access and management of other app’s data. It gives the content to other applications.

## **Activities**

An activity represents a visual UI for one functionality

The concept of activity appeared because mobile screens are not as big as desktop system screens. That is a reason why application functionalities must be divided to separate, smaller units called activities.

Example (query application):

- One activity display a list of questions
- Second activity displays an answer from at a time
- Third activity is reserved to change settings

These activities work together to form a UI, each activity is **independent** from the others.

## **Compose**

A new way to create UIs called Compose

With compose, an app use a single-activity architecture.

All the components extend a class of **View.** - It is a @Composable element (function)

Each View is a rectangle.

**Composable pros:**

- All the code is written in Kotlin.
- Less code
- Declarative API
- Better reusability
- State is explicit and **passed** to the composable function. Then, as app state changes, your UI updates.

**Compose - Layout**

We don’t have to specify a XML layout, we can just put it in the Kotlin code.

@Composable

Fun UserInfo(user: User) {

Column {					// Lambda expression - a Kotlin shortcut for calling a function only one time

Text(user.name)

Text(user.role)

}

}

**androidx.compose.material**  views are: Text(), Button(), TextField() etc.

**Compose - style**

We can **style** our materials like text etc. by adding a style function as a argument like that: Text(firstArg,  **myStyle**)

**Compose - alignment and arrangement**

We can add alignments and arrangements to out views f.ex:

HorizontalAlignment = Alignment.CenterHorizontally

modifier = Modifier

.fillMaxHeight()

.fillMaxWidth()

**Compose - State example**

We can ‚remember’ a variable by adding a remember **keyword**.

Fun MainView(){

val count = remember { mutableStateOf(0) }

Column (

modifier = Modifier.clickable { count.value++ }

)

{

Text(count.value.toString())

}

}

## **Activity Lifecycle**

When we start the application.

The program launcher tries to find the activity tag inside of the manifest.

When it finds it, it runs the main activity.

The first thing when running the activity: it runs the onCreate() function.

### **Lifecycle functions:**

onCreate()

onStart()

onPause() - a function that runs when the activity is not visible. (For example when someone calls us while the game is still running)

onStop() - the activity is finishing or is being destroyed by the system

onDestroy()

onRestart() - when user navigates into the app

### **Activity Source Code**

ComponentActivity class has a default method onCreate().

**Resources**

Externalize resources (images, string etc.) from your application code, so that you can maintain them independently.

**Values**

XML files that can be compiled into many kinds of resource

**Drawable**

Generał concept for a graphic that can be drawn into the UI

**Mipmap**

Drawable files…

## **UI Events Lambdas**

An event listener is an interface that contains a single callback method (similar to javascript DOM Events)

## **Reading list:**

Jemerov, Isakova ‚Kotlin in Action’, Manning,2017

## Assignment

Make an android application:

- Half of the screen is colored
- There is a text
- There is an input text field
- There is a button
- When button is clicked - change the text to another one
- When the input text field has a value - change text to Hello “{value}”

Based on the listeners shown on the lesson