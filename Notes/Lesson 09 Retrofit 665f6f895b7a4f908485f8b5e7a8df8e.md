# Lesson 09 | Retrofit

Created: August 30, 2022 9:04 AM

# Introduction

Today’s mobile applications are Internet dependent - web services are used for supporting mobile applications functions. 

### Web Service

Web Service - self contained and self describing application components that can be used by other applications and can be communicated by using open protocols

## JSON Parsing

JSON - JavaScript Object Notation is a lightweight data-interchange format used in Web Services.

- It is easy for humans to read and write
- It is easy for machines to parse and generate
- It is gaining popularity against XML (another data-interchange format)

### GSON Library

Android library used to convert Java Objects into their JSON representation (Serialization) and vice versa (Deserialization)

vav

### JSON Parsing using GSON

![Untitled](Lesson%2009%20Retrofit%20665f6f895b7a4f908485f8b5e7a8df8e/Untitled.png)

![Untitled](Lesson%2009%20Retrofit%20665f6f895b7a4f908485f8b5e7a8df8e/Untitled%201.png)

## Generating Java class from JSON text

We use special converters to generate Java class from JSON text, such as websites, Android Studio plugins etc.

# Retrofit

It is a REST CLient for Android and Java/Kotlin by Square

It makes relatively easy to retrieve and upload JSON (or other structured data) via a REST based web service. 

It turn our **REST API** into Kotlin/Java **interface**. So we can use normal Kotlin methods to make Web operation such as GET/POST/DELETE etc.

It uses **annotations** which can add new utilities to Kotlin programming language.

It uses **coroutines**.

## REST

Representational State Transfer (REST) compliant web services allow the requesting systems to access and manipulate textual representations of web resources by using a **uniform** and predefined set of **stateless** operations.

Stateless operations mean that all the requests to the Web Service are independent. The state is not predefined, instead, every request includes all the info about the data.

## LiveData

It is an Android Architectural Component which implements **observer** pattern (it is an observable data holder class) It is **lifecycle-aware** (it knows the observer’s state, so if it is not visible, LiveData will not inform them about its change)

![Untitled](Lesson%2009%20Retrofit%20665f6f895b7a4f908485f8b5e7a8df8e/Untitled%202.png)

On top of that, LiveData handles the synchronization between threads (or coroutines).

## ViewModel

![Untitled](Lesson%2009%20Retrofit%20665f6f895b7a4f908485f8b5e7a8df8e/Untitled%203.png)

## LiveData in Jetpack Compose

…

## Retrofit implementation

- **First define the data model (only the fields we are interested in are needed)**
- **Add the following dependencies:**
    
    ![Untitled](Lesson%2009%20Retrofit%20665f6f895b7a4f908485f8b5e7a8df8e/Untitled%204.png)
    
- **Create Retrofit instance using builder of the Retrofit class:**
    
    ![Untitled](Lesson%2009%20Retrofit%20665f6f895b7a4f908485f8b5e7a8df8e/Untitled%205.png)
    
    We use Factory instead of calling the constructors so retrofit is able to create many instances of the same object faster and using less memory.
    
- **Define server API (HTTPS methods) interface**
    
    ![Untitled](Lesson%2009%20Retrofit%20665f6f895b7a4f908485f8b5e7a8df8e/Untitled%206.png)
    
    This way, we can turn our Kotlin functions such as userName(”Mati Czacza”) into the web service requests.
    

## Final retrofit implementation

### API:

![Untitled](Lesson%2009%20Retrofit%20665f6f895b7a4f908485f8b5e7a8df8e/Untitled%207.png)

### ViewModel

![Untitled](Lesson%2009%20Retrofit%20665f6f895b7a4f908485f8b5e7a8df8e/Untitled%208.png)

### UI

![Untitled](Lesson%2009%20Retrofit%20665f6f895b7a4f908485f8b5e7a8df8e/Untitled%209.png)