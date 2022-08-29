# Lesson 07 | Data Storage

Created: August 26, 2022 2:04 PM

## Room SQLite Database

We can create local project databases on the device, which is private for our application.

They are stored on the device (or emulator) in     /data/data/<package>/databases

▶ Room provides an abstraction layer over SQLite and consit of major components					
▶  @Database extends RoomDatabase, provide access to the
database

▶  @Dao contains the methods used for accessing the database
(CRUD operations)

▶  @Entity represents a table (and relations) within the database

![Untitled](Lesson%2007%20Data%20Storage%206f1602a3423a4b80994f09d115c8e40c/Untitled.png)

## @Entity

data class with @Entity annotation

![Untitled](Lesson%2007%20Data%20Storage%206f1602a3423a4b80994f09d115c8e40c/Untitled%201.png)

## @Entity with relation

![Untitled](Lesson%2007%20Data%20Storage%206f1602a3423a4b80994f09d115c8e40c/Untitled%202.png)

## @Dao

![Untitled](Lesson%2007%20Data%20Storage%206f1602a3423a4b80994f09d115c8e40c/Untitled%203.png)

## @Database

![Untitled](Lesson%2007%20Data%20Storage%206f1602a3423a4b80994f09d115c8e40c/Untitled%204.png)

## LiveData and ViewModel

LiveData is an observable data holder class

- ▶ LiveData is lifecycle-aware, it will only updates app
    
    component observers that are in an active lifecycle state
    
- ▶ ViewModel is a class that is responsible for preparing and
managing the data for a Composable function or an Activity
or a Fragment.

To use them, we need to add following dependencies:

![Untitled](Lesson%2007%20Data%20Storage%206f1602a3423a4b80994f09d115c8e40c/Untitled%205.png)

## ViewModel implementation

![Untitled](Lesson%2007%20Data%20Storage%206f1602a3423a4b80994f09d115c8e40c/Untitled%206.png)

![Untitled](Lesson%2007%20Data%20Storage%206f1602a3423a4b80994f09d115c8e40c/Untitled%207.png)

## ViewModel use (inserting user to db)

![Untitled](Lesson%2007%20Data%20Storage%206f1602a3423a4b80994f09d115c8e40c/Untitled%208.png)

More examples available in the **W1D5 lesson presentation**.