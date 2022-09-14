# Lesson 18 | Data Storage - Continuation

Created: September 9, 2022 2:12 PM

## Static files

If you want to save a static file in your application at compile time, save the file in your project **res/raw/** or **assets/** directory.

We cannot modify the static data when the application is running!

To access the static files in **res/raw** folder, we use **application.resources.openRawResource()** passing the **R.raw.<filename>** resource ID

To access the static files in **assets/** , we use **application.assets.open("<filename>")**.

These methods return an **Input Stream**. We can then decode it with **BufferedReader**.

![Untitled](Lesson%2018%20Data%20Storage%20-%20Continuation%2081c0a7c927ec415282e650f8fdee5e7d/Untitled.png)

## Shared storage

Shared storage can be used for user data that can or should be **accessible** **to** **other** **apps** and saved **even** if the user **uninstalls** **your** **app**.